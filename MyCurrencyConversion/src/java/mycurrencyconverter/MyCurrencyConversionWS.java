/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mycurrencyconverter;

import docwebservices.CurrencyConversionWSService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;
import javax.xml.ws.WebServiceRef;
import org.codehaus.jackson.map.ObjectMapper;
import org.netbeans.xml.schema.currencyconversions.ConversionRate;
import org.netbeans.xml.schema.currencyconversions.RatesObject;

/**
 *
 * @author steppy
 */
@WebService(serviceName = "MyCurrencyConversionWS")
@Stateless()
public class MyCurrencyConversionWS {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/TahaCurrencyConvertor/CurrencyConversionWSService.wsdl")
    private CurrencyConversionWSService service;

    /**
     * Web service operation
     * @param oldCurrency
     * @param newCurrency
     * @return 
     */
    @WebMethod(operationName = "Get_Conversion_Rate")
    public double Get_Conversion_Rate(@WebParam(name = "oldCurrency") String oldCurrency, @WebParam(name = "newCurrency") String newCurrency) {
        // Creates the correct string to append for the API request and a default conversion rate
        String currencyString = oldCurrency + "_" + newCurrency;
        double conversionRate = 1.0;
        // Returns the default conversion rate if both currencies are the same
        if (!oldCurrency.equals(newCurrency)) {
            // Makes the actual API "get" request
            String apiResponse = API_Request(currencyString);
            // Gets a usable value from the API
            conversionRate = Parse_Response(apiResponse, currencyString);
            // Goes through the different contingencies based on bad results
            // Otherwise marshals the API response
            if (conversionRate == -1.0) {
                // Unmarshals the relevant API's conversion rate
                conversionRate = Get_Rate_From_File(currencyString);
                if (conversionRate == -1.0) {
                    // Load from constant values in a web service
                    conversionRate = getConversionRate(oldCurrency, newCurrency);
                }
            }
            else {
                Update_Conversion_Rates(currencyString, conversionRate);
            }
        }
        return conversionRate;
    }
    
    private String API_Request(String currencyString) {
        // Creates the correct API request URL including loading the API key
        String url = "https://free.currconv.com/api/v7/convert?compact=ultra&apiKey=";
        url = url + Load_API_Key() + "&q=" + currencyString;
        String response = null;
        // Attempts to make the connection and get the raw API response as a String
        try {
            URL apiURL = new URL(url);
            HttpURLConnection apiConnection = (HttpURLConnection) apiURL.openConnection();
            // Builds the response into a usable string
            response = Get_API_Response(apiConnection);
            
        } catch (MalformedURLException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return response;
    }
    
    private String Get_API_Response(HttpURLConnection apiConnection) {
        String strResponse = null;
        try {
            // Only reads from the connection if the request is successful
            if (apiConnection.getResponseCode() == 200) {
                BufferedReader responseReader = new BufferedReader(new InputStreamReader(apiConnection.getInputStream()));
                // Turns the response into a String
                String inputString;
                StringBuilder jsonResponse = new StringBuilder();
                
                while ((inputString = responseReader.readLine()) != null) {
                    jsonResponse.append(inputString).append('\n');
                }
                responseReader.close();
            
                strResponse = jsonResponse.toString();
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return strResponse;
    }
    
    private double Parse_Response(String jsonString, String currencyString) {
        double conversionRate = -1.0;
        // Prevents trying to access null value
        if (jsonString == null) {
            return conversionRate;
        }
        
        // Uses the ObjectMapper class to read from the JSON response
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Double> responseMap;
        try {
            // Translates the JSON data into a usable Double
            responseMap = (Map<String, Double>) 
                            objectMapper.readValue(jsonString, HashMap.class);
            conversionRate = responseMap.get(currencyString);
        } catch (IOException ex) {
            System.out.println(ex);
        }
        
        return conversionRate;
    }

    private String Load_API_Key() {
        // Initialises variable to prevent context issues
        String apiKey = null;
        try {
            apiKey = Files.readAllLines(Paths.get("/home/steppy/Documents/SCCC-Coursework/CurrencyConverterAPI-Key.txt")).get(0);
        } catch (IOException e) {
            System.out.println("File not found");
        }
        return apiKey;
    }

    private double getConversionRate(java.lang.String arg0, java.lang.String arg1) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        docwebservices.CurrencyConversionWS port = service.getCurrencyConversionWSPort();
        return port.getConversionRate(arg0, arg1);
    }
    
    private synchronized List<ConversionRate> Load_Rates_From_XML() {
        // Specifies the file name and creates a new object to unmarshal into
        String fileName = "/home/steppy/Documents/SCCC-Coursework/Conversion-Rates.xml";
        RatesObject ratesList = new RatesObject();
        
        try {
            javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(ratesList.getClass().getPackage().getName());
            javax.xml.bind.Unmarshaller unmarshaller = jaxbCtx.createUnmarshaller();
            ratesList = (RatesObject) unmarshaller.unmarshal(new java.io.File(fileName)); //NOI18N
        } catch (javax.xml.bind.JAXBException ex) {
           System.out.println(ex);
        }
        
        // Converts the unmarshaled object into a more usable list
        return ratesList.getRates();
    }
    
    private synchronized void Save_Rates_To_XML(RatesObject ratesList) {
        // Specifies the file name and marshals the conversion rates
        String fileName = "/home/steppy/Documents/SCCC-Coursework/Conversion-Rates.xml";
        
        try {            
            javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(ratesList.getClass().getPackage().getName());
            javax.xml.bind.Marshaller marshaller = jaxbCtx.createMarshaller();
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8"); //NOI18N
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(ratesList, (new java.io.File(fileName)));
        } catch (javax.xml.bind.JAXBException ex) {
             System.out.println(ex);
        }
    }
    
    private void Update_Conversion_Rates(String currString, double convRate) {
        // Unmarshals the existing conversion rates
        List<ConversionRate> ratesList = Load_Rates_From_XML();
        RatesObject newRatesList = new RatesObject();
        
        // Iterates and updates the list if required
        boolean listContainsRate = false;
        for (int i = 0; i < ratesList.size(); i++) {
            if (ratesList.get(i).getConversion().equals(currString)) {
                ratesList.get(i).setRate(convRate);
                listContainsRate = true;
            }
        }
        // Adds a new conversion rate if not already in the list
        if (!listContainsRate) {
            ConversionRate newRate = new ConversionRate();
            newRate.setConversion(currString);
            newRate.setRate(convRate);
            ratesList.add(newRate);
        }
        // Adds all of the updated rates to the new object for marshaling
        newRatesList.getRates().addAll(ratesList);
        
        // Marshals the new rates
        Save_Rates_To_XML(newRatesList);
    }
    
    private double Get_Rate_From_File(String desiredRate) {
        // Unmarshals the existing rates
        List<ConversionRate> ratesList = Load_Rates_From_XML();
        double loadedRate = -1.0;
        // If a rate is already in the XML then returns the correct value
        for (int i = 0; i < ratesList.size(); i++) {
            if (ratesList.get(i).getConversion().equals(desiredRate)) {
                loadedRate = ratesList.get(i).getRate();
            }
        }
        return loadedRate;
    }
    
}

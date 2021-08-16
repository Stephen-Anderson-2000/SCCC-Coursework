/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StockPriceWS;

import static com.sun.jmx.mbeanserver.Util.cast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.ejb.Stateless;
import javax.xml.ws.WebServiceRef;
import org.codehaus.jackson.map.ObjectMapper;
import org.me.sharesbroker.SharesBrokerWS_Service;
import org.netbeans.xml.schema.shares.Share;
import org.netbeans.xml.schema.shares.SharePrice;

/**
 *
 * @author steppy
 */
@WebService(serviceName = "UpdateStockPricesWS")
@Stateless()
public class UpdateStockPricesWS {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/SharesBrokerWS/SharesBrokerWS.wsdl")
    private SharesBrokerWS_Service service;

    /**
     * Web service operation
     */
    @WebMethod(operationName = "Update_Stock_Prices")
    public void Update_Stock_Prices() {
        // Unmarshals the shares data into a more usable format
        List<Share> sharesList = loadSharesFromXML();
        
        String url = Make_URL_String(sharesList);
        
        String apiResponse = API_Request(url);
        
        // Only tries to alter the information if the API request was succesful
        if (apiResponse != null) {
            Map<String, HashMap> myMap = (Map<String, HashMap>) JSON_String_To_Map(apiResponse).get("quoteResponse");
            // Prevents nullpointer exception that can occur if required field is missing from response
            if (!myMap.isEmpty()) {
                try {
                    // Casts the response into an arraylist that should contain all of the share prices
                    ArrayList<Object> quotesArray = cast(myMap.get("result"));
                    // Same logic as previous if statement
                    if (!quotesArray.isEmpty()) {
                        // Uses the fetched information to update the share prices
                        Update_List(sharesList, quotesArray);
                    }
                } catch (ClassCastException ex) {
                    System.out.println(ex);
                }
            }
        }
        
        // Marshals the potentially altered share objects
        saveSharesToXML(sharesList);
    }
    
    private String Make_URL_String(List<Share> sharesList) {
        String symbols = "";
        
        // Creates the correct substring to append to the url for the API request
        for (int i = 0; i < sharesList.size(); i++) {
            symbols += sharesList.get(i).getCompanySymbol() + "%2C";
        }
        // Removes the last few characters that denote another symbol from the substring
        if (symbols.endsWith("%2C")) { 
            symbols = symbols.substring(0, symbols.length() - 3); 
        }

        return "https://apidojo-yahoo-finance-v1.p.rapidapi.com/market/v2/get-quotes?region=US&symbols=" + symbols;
    }
    
    private String API_Request(String url) {
        String response = null;
        try {
            // Prevents an empty API request
            if (!url.endsWith("=")) {
                URL apiURL = new URL(url);
                HttpURLConnection apiConnection = (HttpURLConnection) apiURL.openConnection();
                // Adds the required API properties to authenticate the user
                apiConnection.setRequestProperty("x-rapidapi-key", Load_API_Key());
                apiConnection.setRequestProperty("x-rapidapi-host", "apidojo-yahoo-finance-v1.p.rapidapi.com");
                response = Get_API_Response(apiConnection);
                // Just uses the existing response to prevent using up my quota of API calls
                //response = Load_Saved_Data();
            }
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
            // Only continues if the request is accepted and returned correctly
            if (apiConnection.getResponseCode() == 200) {
                BufferedReader responseReader = new BufferedReader(new InputStreamReader(apiConnection.getInputStream()));
            
                // Builds a new string from the API response
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
    
    private String Load_API_Key() {
        String apiKey = null;
        try {
            apiKey = Files.readAllLines(Paths.get("/home/steppy/Documents/SCCC-Coursework/X-RapidAPI-Key.txt")).get(0);
        } catch (IOException ex) {
            System.out.println("File not found");
        }
        return apiKey;
    }
    
    /*
    private String Load_Saved_Data() {
        // An exact copy of Load_API_Key to get the already requested and saved shares data
        // Used for testing the services without using up the API request quota
        String loadedData = null;
        try {
            loadedData = Files.readAllLines(Paths.get("/home/steppy/Documents/SCC/Coursework/API-Response.txt")).get(0);
        } catch (IOException ex) {
            System.out.println("File not found");
        }
        return loadedData;
    }
    */
    
    private Map<String, HashMap> JSON_String_To_Map(String jsonString) {
        ObjectMapper objectMapper = new ObjectMapper();
        // Uses Jackson ObjectMapper to create a Map from a raw JSON string
        Map<String, HashMap> map = null;
        try {
            map = objectMapper.readValue(jsonString, HashMap.class);
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return map;
    }
    
    private void Update_List(List<Share> sharesList, ArrayList<Object> quotesArray) {
        if (quotesArray.size() == sharesList.size()) {
            for (int i = 0; i < quotesArray.size(); i++) {
                // Casts the LinkedHashMap quote (created from the JSON string) into an explicit object
                LinkedHashMap quote = cast(quotesArray.get(i));
                // Accesses the quote HashMap and casts the relevant object before updating the share
                double price = cast(quote.get("regularMarketPrice"));
                sharesList.get(i).setSharePrice(updateSharePrice(sharesList.get(i).getSharePrice(), price));
            }
        }
    }

    private java.util.List<org.netbeans.xml.schema.shares.Share> loadSharesFromXML() {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        org.me.sharesbroker.SharesBrokerWS port = service.getSharesBrokerWSPort();
        return port.loadSharesFromXML();
    }
    
    private void saveSharesToXML(java.util.List<org.netbeans.xml.schema.shares.Share> arg0) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        org.me.sharesbroker.SharesBrokerWS port = service.getSharesBrokerWSPort();
        port.saveSharesToXML(arg0);
    }

    private SharePrice updateSharePrice(org.netbeans.xml.schema.shares.SharePrice arg0, double arg1) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        org.me.sharesbroker.SharesBrokerWS port = service.getSharesBrokerWSPort();
        return port.updateSharePrice(arg0, arg1);
    }

}

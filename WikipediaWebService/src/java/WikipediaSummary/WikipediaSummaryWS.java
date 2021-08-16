/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WikipediaSummary;

import static com.sun.jmx.mbeanserver.Util.cast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author steppy
 */
@WebService(serviceName = "WikipediaSummaryWS")
@Stateless()
public class WikipediaSummaryWS {

    /**
     * Web service operation
     * @param wikipediaTitle
     * @return 
     */
    @WebMethod(operationName = "Get_Wikipedia_Summary")
    public String Get_Wikipedia_Summary(@WebParam(name = "wikipediaTitle") String wikipediaTitle) {
        String wikiAPIResponse = API_Request(wikipediaTitle);
        String wikiSummary = Parse_Response(wikiAPIResponse);
        return wikiSummary;
    }

    
    private String API_Request(String wikipediaTitle) {
        // Creates the correct API request URL
        String url = "https://en.wikipedia.org/w/api.php?action=query&prop=extracts&indexpageids&format=json&exsentences=5&exintro=&explaintext=&exsectionformat=plain&titles="; 
        url = url + wikipediaTitle;
        
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
    
    private String Parse_Response(String jsonString) {
        String wikiSummary = null;
        // Prevents trying to access null value
        if (jsonString == null) {
            return wikiSummary;
        }
        
        // Only tries to access the map if a valid URL gave a useful response
        Map<String, HashMap> responseMap = JSON_String_To_Map(jsonString).get("query");
        if (!responseMap.isEmpty()) {
                try {
                    // Casts the response into an arraylist that should contain the required index value
                    ArrayList<String> pageIDs = cast(responseMap.get("pageids"));
                    // Finds the index ID of the Wikipedia page response
                    String pageID = pageIDs.get(0);
                    // Creates a new map that can be indexed to get the API response
                    Map<String, HashMap> extractMap = cast(responseMap.get("pages"));
                    wikiSummary = cast(extractMap.get(pageID).get("extract"));
                } catch (ClassCastException ex) {
                    System.out.println(ex);
                }
            }
        return wikiSummary;
    }
    
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
    
}

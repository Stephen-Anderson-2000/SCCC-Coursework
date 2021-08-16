/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.me.sharesbroker;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.ejb.Stateless;
import SharesPackage.Share;
import SharesPackage.SharePrice;
import SharesPackage.SharesList;

/**
 *
 * @author steppy
 */
@WebService(serviceName = "SharesBrokerWS")
@Stateless()
public class SharesBrokerWS {
    static final String sharesFile = "/home/steppy/Documents/SCC/Coursework/availableShares.xml";

/**
     * Web service operation
     * @return currentShares
     */
    @WebMethod(operationName = "Load_Shares_From_XML")
    public synchronized List<Share> Load_Shares_From_XML() {
        // Load list of shares data from availableShares.xml
        SharesList sharesList = new SharesList();
        
        try {
            javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(sharesList.getClass().getPackage().getName());
            javax.xml.bind.Unmarshaller unmarshaller = jaxbCtx.createUnmarshaller();
            sharesList = (SharesList) unmarshaller.unmarshal(new java.io.File(sharesFile)); //NOI18N
        } catch (javax.xml.bind.JAXBException ex) {
            // XXXTODO Handle exception
            java.util.logging.Logger.getLogger("global").log(java.util.logging.Level.SEVERE, null, ex); //NOI18N
        }
        
        return sharesList.getShares();
    } // Loads the list of shares data from availableShares.xml for use
    
    /**
     * Web service operation
     * @param newShares
     */
    @WebMethod(operationName = "Save_Shares_To_XML")
    public synchronized void Save_Shares_To_XML(List<Share> newShares) {
        SharesList sharesList = new SharesList();
        sharesList.getShares().addAll(newShares);
        
        try {            
            javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(sharesList.getClass().getPackage().getName());
            javax.xml.bind.Marshaller marshaller = jaxbCtx.createMarshaller();
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8"); //NOI18N
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(sharesList, (new java.io.File(sharesFile)));
        } catch (javax.xml.bind.JAXBException ex) {
            // XXXTODO Handle exception
            java.util.logging.Logger.getLogger("global").log(java.util.logging.Level.SEVERE, null, ex); //NOI18N
        }
        
    } // Saves list of shares data to availableShares.xml

    /**
     * Web service operation
     * @param numOfShares
     * @param companyID
     */
    @WebMethod(operationName = "Purchase_Shares")
    public synchronized boolean Purchase_Shares(int numOfShares, String companyID) {
        // Allows a user to purchase shares and updates the data where appropriate
        // Remember to handle race conditions
        boolean purchased = false;
        if (numOfShares > 0) {
            List<Share> availableShares = Load_Shares_From_XML();
            
            Iterator it = availableShares.iterator();
            Share nextShare;
            while(it.hasNext()) {
                nextShare = (Share) it.next();
                if (nextShare.getCompanySymbol().equals(companyID))
                {
                    if (nextShare.getAvailableShares() >= numOfShares) {
                        try {
                            GregorianCalendar myCalendar = new GregorianCalendar();
                            myCalendar.setTime(new Date());
                            XMLGregorianCalendar lastUpdated = 
                                    DatatypeFactory.newInstance().
                                            newXMLGregorianCalendar(myCalendar);
                            nextShare.getSharePrice().setLastUpdated(lastUpdated);
                            int newSharesNum = nextShare.getAvailableShares() - numOfShares;
                            nextShare.setAvailableShares(newSharesNum);
                            purchased = true;
                        }
                        catch (DatatypeConfigurationException e)
                        {
                            System.out.println(e);
                        }
                    }
                }
            }
            Save_Shares_To_XML(availableShares);
        }
        return purchased;
    }

    /**
     * Web service operation
     * @param numOfShares
     * @param companyID
     */
    @WebMethod(operationName = "Sell_Shares")
    public void Sell_Shares(int numOfShares, String companyID) {
        // Allows a user to sell shares back (should I track the shares they have?)
        // Handle race conditions?
        if (numOfShares > 0) {
            List<Share> availableShares = Load_Shares_From_XML();
            
            Iterator it = availableShares.iterator();
            Share nextShare;
            while(it.hasNext()) {
                nextShare = (Share) it.next();
                if (nextShare.getCompanySymbol().equals(companyID))
                {
                    try {
                        GregorianCalendar myCalendar = new GregorianCalendar();
                        myCalendar.setTime(new Date());
                        XMLGregorianCalendar lastUpdated = DatatypeFactory.
                                newInstance().newXMLGregorianCalendar(myCalendar);
                        nextShare.getSharePrice().setLastUpdated(lastUpdated);
                        int newSharesNum = nextShare.getAvailableShares() + numOfShares;
                        nextShare.setAvailableShares(newSharesNum);
                    }
                    catch (DatatypeConfigurationException e)
                    {
                        System.out.println(e);
                    }
                }
            }
            Save_Shares_To_XML(availableShares);
        }
    }

    /**
     * Web service operation
     * @param companyName
     * @param companyID
     * @param sharesAvailable
     * @param wikipediaName
     * @param currency
     * @param value
     */
    @WebMethod(operationName = "Add_New_Company")
    public synchronized void Add_New_Company(String companyName, String companyID,
                                int sharesAvailable, String wikipediaName, 
                                String currency, double value) {
        // Allows to add a new company using a GUI
        
        SharePrice newSharePrice = new SharePrice();
        newSharePrice.setCurrency(currency);
        newSharePrice.setValue(value);
        
        try {
            GregorianCalendar myCalendar = new GregorianCalendar();
            myCalendar.setTime(new Date());
            XMLGregorianCalendar lastUpdated = DatatypeFactory.newInstance().newXMLGregorianCalendar(myCalendar);
            newSharePrice.setLastUpdated(lastUpdated);
        }
        catch (DatatypeConfigurationException e)
        {
            System.out.println(e);
        }
        
        Share newShare = new Share();
        newShare.setCompanyName(companyName);
        newShare.setCompanySymbol(companyID);
        newShare.setWikipediaName(wikipediaName);
        newShare.setAvailableShares(sharesAvailable);
        newShare.setSharePrice(newSharePrice);

        List<Share> sharesList = Load_Shares_From_XML();
        sharesList.add(newShare);
        Save_Shares_To_XML(sharesList);
    }

    /**
     * Web service operation
     * @param sharePrice
     * @param newValue
     * @return 
     */
    @WebMethod(operationName = "Update_SharePrice")
    public SharePrice Update_SharePrice(SharePrice sharePrice, double newValue) {
        //TODO write your implementation code here:
        sharePrice.setValue(newValue);
        try {
            GregorianCalendar myCalendar = new GregorianCalendar();
            myCalendar.setTime(new Date());
            XMLGregorianCalendar lastUpdated = DatatypeFactory.newInstance().newXMLGregorianCalendar(myCalendar);
            sharePrice.setLastUpdated(lastUpdated);
        }
        catch (DatatypeConfigurationException e)
        {
            System.out.println(e);
        }
        return sharePrice;
    }
}

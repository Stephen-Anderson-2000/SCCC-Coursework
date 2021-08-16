This is a new repository specifically for my Service Centric & Cloud Computing coursework assignment. 

The project creates a version of a shares brokering service to teach me how to create a central process that clients can connect to and use.
It utilises GlassFish to run the processes in order to allow for seamless local hosting. 

The project also requires the use of some APIs for extra information on companies, currency conversion and live stock price updates.
The existing repository was only ever intended to be kept private and so had the API keys saved and tracked in .txt files.
To use this project the keys will need to be supplied and saved as such:
    CurrencyConverterAPI-Key.txt              (Requesting from "https://free.currconv.com/api/v7")
    X-RapidAPI-Key.txt                        (Requesting from "https://apidojo-yahoo-finance-v1.p.rapidapi.com/market/v2")
    

Due to my inability to understand how Java builds when dealing with relative paths, there are currently some hardcoded filepaths.
They can be found in the following files:
	MyCurrencyConversion/src/java/mycurrencyconverter/MyCurrencyConversionWS.java
	SharesBrokeringWebService/src/java/org/me/sharesbroker/SharesBrokerWS.java
	UpdateStocks/src/java/StockPriceWS/UpdateStockPricesWS.java

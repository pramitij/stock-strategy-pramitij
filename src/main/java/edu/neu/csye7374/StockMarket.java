package edu.neu.csye7374;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StockMarket {


    private static StockMarket instance;
    private List<StockAPI> stocksList= new ArrayList<>();
    private List<StockAPI> stocksTraded=new ArrayList<>();


    private StockMarket() {
        super();
        instance=null;
    }

    public static StockMarket getInstance() {
        if (instance == null) {
            instance = new StockMarket();
        }
        return instance;
    }

    public List<StockAPI> getStocksList() {
        return stocksList;
    }

    public void setStocksList(List<StockAPI> stocksList) {
        this.stocksList = stocksList;
    }

    public List<StockAPI> getStocksTraded() {
        return stocksTraded;
    }

    public void setStocksTraded(List<StockAPI> stocksTraded) {
        this.stocksTraded = stocksTraded;
    }

    //Add StockAPI
    public void addStock(StockAPI someStock) {
        getStocksList().add(someStock);
        //System.out.println("Added stock: "+someStock.getName());

    }
    //Remove StockAPI objects
    public void removeStock(StockAPI someStock) {
        getStocksList().remove(someStock);
        //System.out.println("Removed stock: "+someStock.getName());
    }

    public void addTrade(StockAPI someStock)
    {
        stocksTraded.add(someStock);
    }

    public void showTrades()
    {
        System.out.println("Traded stocks Now...");
        for (StockAPI t : stocksTraded)
        {System.out.println(t.getName()+":"+t.getTypeTrade()+" at price:"+t.getPrice()+" at timeStamp="+ new Timestamp(System.currentTimeMillis()));}
    }

    //Show all stocks traded on this market (e.g., StockAPI state like price, metric, etc.)
    public void showStocks()
    {
        System.out.println("StockAPI Market Now...");
        for (StockAPI someStock : getStocksList())
        {System.out.println(someStock.toString());}
    }

    public void trade(StockAPI someStock, String metricType)
    {
        int metric=0;

        if(metricType=="AbstractBullMetricStrategy")
            metric=new AbstractBullMetricStrategy().calculateMetric();
        else if(metricType=="AbstractBearMetricStrategy")
            metric=new AbstractBearMetricStrategy().calculateMetric();
        else if(metricType=="AbstractBearMetricStrategySingleton")
            metric= AbstractBearMetricStrategySingleton.getInstance().calculateMetric();
        else
            metric= AbstractBullMetricStrategySingleton.getInstance().calculateMetric();

        if (metric>0)
        {
            if (someStock.getBid() >= someStock.getPrice() )  //buy positive performing stock
            {
                StockAPI tradeBuyStock = new StockAPI(someStock.getName(),someStock.getPrice(),someStock.getDesc(),someStock.getBid(),someStock.getTypeTrade());
                tradeBuyStock.setTypeTrade("Buying");
                addTrade(tradeBuyStock);
            }
        }
        else // sell negative performing stock
        {
            StockAPI tradeSellStock = new StockAPI(someStock.getName(),someStock.getPrice(),someStock.getDesc(),someStock.getBid(),someStock.getTypeTrade());
            tradeSellStock.setTypeTrade("Selling");
            addTrade(tradeSellStock);
        }

        someStock.setPrice(someStock.getPrice()+metric);
    }

    public static void demo() {
        //creating market
        StockMarket SM = StockMarket.getInstance();
        System.out.println("Stocks: ");
        SM.showStocks();
        System.out.println("TradedStocks: ");
        SM.showTrades();

        //Creating Factory object to get Stocks Factory Objects
        AbstractStockFactory abstractStockFactory1=new classAStockFactory();
        AbstractStockFactory abstractStockFactory2=new classBStockFactory();

        //Getting Singleton Factory StockAPI Objects
        AbstractStockFactory abstractStockFactory3= classAStockSingletonFactory.getInstance(); // Lazy Singleton Implementation of classAStockFactory
        AbstractStockFactory abstractStockFactory4= classBStockSingletonFactory.getInstance(); // Early Singleton Implementation of classBStockFactory



        //Creating StockAPI Objects using getObject Method
        StockAPI s1 =abstractStockFactory1.getObject("Apple",230.0,"Apple Common StockAPI",0.0,null);
        StockAPI s2= abstractStockFactory1.getObject("Netflix",178.50,"Netflix Common StockAPI",0.0,null);
        StockAPI s3= abstractStockFactory3.getObject("Meta Group", 97.50, "Meta Common StockAPI", 0.0, null);
        StockAPI s4 =abstractStockFactory2.getObject("Tesla",170.0,"Tesla Common Share",0.0,null);
        StockAPI s5=abstractStockFactory2.getObject("Disney",444.63,"Disney Common StockAPI",0.0,null);
        StockAPI s6=abstractStockFactory4.getObject("Amazon", 510.50, "Amazon Common StockAPI", 0, null);


        SM.addStock(s1);
        SM.addStock(s2);
        SM.addStock(s3);
        SM.addStock(s4);
        SM.addStock(s5);
        SM.addStock(s6);


        //viewing stocks
        System.out.println("First display of stock market");
        SM.showStocks();
        System.out.println("--------------------------------------------------------------------");

        // simulation of trading on s1
        for (int i=0; i<6; i++)
        {
            double start = s1.getPrice();
            double end = s1.getPrice();
            double random = new Random(System.currentTimeMillis()+1900).nextDouble();
            double bid_now = start + (random * (end - start));
            System.out.println("Bidding on DeliveryFirmStocks: "+s1.getName()+" "+bid_now);
            s1.setBid(bid_now);
            System.out.println("After trade on DeliveryFirmStocks of stock market");
            SM.trade(s1, "BullMarketMetricStrategy");
            SM.showTrades();
            System.out.println();
        }

        System.out.println("--------------------------------------------------------------------");

        // simulation of trading on s2
        for (int i=0; i<6; i++)
        {
            double start = s2.getPrice();
            double end = s2.getPrice(); //// Implementation of AbstractBearMetricStrategy
            double random = new Random(System.currentTimeMillis()+1700).nextDouble();
            double bid_now = start + (random * (end - start));
            System.out.println("Bidding on SoftwareFirmStocks: "+s2.getName()+" "+bid_now);
            s2.setBid(bid_now);
            System.out.println("After trade on SoftwareFirmStocks of stock market");
            SM.trade(s2,"BearMarketMetricStrategy" );
            SM.showTrades();
            System.out.println();
        }
        System.out.println("--------------------------------------------------------------------");

        // simulation of trading on s3
        for (int i=0; i<6; i++)
        {
            double start = s3.getPrice();
            double end = s3.getPrice();
            double random = new Random(System.currentTimeMillis()+1500).nextDouble();
            double bid_now = start + (random * (end - start));
            System.out.println("Bidding on DeliveryFirmStocks: "+s3.getName()+" "+bid_now);
            s1.setBid(bid_now);
            System.out.println("After trade on DeliveryFirmStocks of stock market");
            SM.trade(s3, "BullMarketMetricStrategySingleton");
            SM.showTrades();
            System.out.println();
        }
        System.out.println("--------------------------------------------------------------------");

        // simulation of trading on s4
        for (int i=0; i<6; i++)
        {
            double start = s4.getPrice();
            double end = s4.getPrice();
            double random = new Random(System.currentTimeMillis()+1300).nextDouble();
            double bid_now = start + (random * (end - start));
            System.out.println("Bidding on DeliveryFirmStocks: "+s4.getName()+" "+bid_now);
            s1.setBid(bid_now);
            System.out.println("After trade on DeliveryFirmStocks of stock market");
            SM.trade(s4, "BearMarketMetricStrategySingleton");
            SM.showTrades();
            System.out.println();
        }
        System.out.println("--------------------------------------------------------------------");


        // simulation of trading on s5
        for (int i=0; i<6; i++)
        {
            double start = s5.getPrice();
            double end = s5.getPrice();
            double random = new Random(System.currentTimeMillis()+1100).nextDouble();
            double bid_now = start + (random * (end - start));
            System.out.println("Bidding on DeliveryFirmStocks: "+s5.getName()+" "+bid_now);
            s1.setBid(bid_now);
            System.out.println("After trade on DeliveryFirmStocks of stock market");
            SM.trade(s5, "BullMarketMetricStrategy");
            SM.showTrades();
            System.out.println();
        }
        System.out.println("--------------------------------------------------------------------");

        // simulation of trading on s6
        for (int i=0; i<6; i++)
        {
            double start = s6.getPrice();
            double end = s6.getPrice();
            double random = new Random(System.currentTimeMillis()+900).nextDouble();
            double bid_now = start + (random * (end - start));
            System.out.println("Bidding on DeliveryFirmStocks: "+s6.getName()+" "+bid_now);
            s1.setBid(bid_now);
            System.out.println("After trade on DeliveryFirmStocks of stock market");
            SM.trade(s6, "BearMarketMetricStrategy");
            SM.showTrades();
            System.out.println();
        }
        System.out.println("--------------------------------------------------------------------");

        System.out.println("End of day Display of stock market");
        System.out.println();
        SM.showStocks();
        System.out.println("End of day trades executed");
        System.out.println();
        SM.showTrades();
    }
}

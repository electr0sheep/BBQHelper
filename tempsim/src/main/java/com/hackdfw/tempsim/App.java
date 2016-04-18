package com.hackdfw.tempsim;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Hello world!
 *
 */
public class App 
{
	private static final String SERVER_URL = "http://localhost:8080/bbq/dataposter";
	private static Map<Integer,Double> currentClientValue = new HashMap<Integer, Double>();
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	private static final NumberFormat formatter = new DecimalFormat("#0.00");   
	
	private static final double MAX_TEMP = 450;
	private static final double MIN_TEMP = 0;
    public static void main( String[] args ) throws InterruptedException, UnirestException
    {
    	System.out.println("Starting the request");
        Gson gson = new Gson();
        
        while(true){
        	TempRequest req = new TempRequest();
        	int clientid = getRandomClient();
        	req.setClient_id(clientid);
        	req.setValue(getRandomValue(clientid));
        	req.setTimestamp(getTimeStamp());
        	String reqstr = gson.toJson(req);
        	System.out.println(reqstr);
        	RequestData(reqstr);
        	Thread.sleep(3000);
        }
    }
    
    private static void RequestData(String data) throws UnirestException{
    	HttpResponse<JsonNode> jsonResponse = Unirest.post(SERVER_URL)
    			  .header("accept", "application/json")
    			  .body(data)
    			  .asJson();
    	System.out.println("------------");
    	System.out.println(jsonResponse);
    	System.out.println("-------------");
    }
    
    private static int getRandomClient(){
    	Random r = new Random();
    	int Low = 1;
    	int High = 2;
    	int Result = r.nextInt(High-Low) + Low;
    	return Result;
    }
    
    private static double getRandomValue(int clientid){
    	Random r = new Random();
    	if(!currentClientValue.containsKey(clientid)){
    		currentClientValue.put(clientid, MIN_TEMP);
    	}
    	double start = currentClientValue.get(clientid);
    	double end = start+5;
    	double random = new Random().nextDouble();
    	double result = start + (random * (end - start));
    	result = Double.valueOf(formatter.format(result));
    	if(result>MAX_TEMP){
    		currentClientValue.put(clientid, MIN_TEMP);	
    	}
    	else{
    		currentClientValue.put(clientid, result);
    	}
    	return result;
    }
    
    private static String getTimeStamp(){
    	return sdf.format(new Date());
    }
}

/**
    Copyright 2014-2015 Amazon.com, Inc. or its affiliates. All Rights Reserved.

    Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance with the License. A copy of the License is located at

        http://aws.amazon.com/apache2.0/

    or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package helloworld;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.*;
import java.io.*;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;

public class HelloWorldSpeechlet implements Speechlet {
    private static final Logger log = LoggerFactory.getLogger(HelloWorldSpeechlet.class);
    
    String[][] crops = {{"Cabbage","75","65","4 weeks before last frost","When heads are solid but not yet cracking","11"},
    		{"Cauliflower","75","65","4 weeks before last frost","When head is compact but before curd opens and becomes ricey","11"}};
    
    @Override
    public void onSessionStarted(final SessionStartedRequest request, final Session session)
            throws SpeechletException {
        log.info("onSessionStarted requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());
    }

    @Override
    public SpeechletResponse onLaunch(final LaunchRequest request, final Session session)
            throws SpeechletException {
        log.info("onLaunch requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());
        return getWelcomeResponse();
    }

    @Override
    public SpeechletResponse onIntent(final IntentRequest request, final Session session)
            throws SpeechletException{
        log.info("onIntent requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());

        Intent intent = request.getIntent();
        String intentName = (intent != null) ? intent.getName() : null;

		if ("CropInfo".equals(intentName)){
			return getCropInfo();
		}
		else if ("WeatherData".equals(intentName)){
			try{
				return  getWeatherData();
		    }
		    catch(IOException ex){
		        System.out.println ("IOError");
		        throw new SpeechletException("Invalid Intent");
		    }
		}
		else if ("WhatToPlant".equals(intentName)){
			return whatToPlant();
		}
		else if ("CropComplexCabbage".equals(intentName)) {
            return cropComplexCabbage();
		}
		else if ("CropComplexCauliflower".equals(intentName)) {
            return cropComplexCauliflower();
		}
		else if ("AMAZON.HelpIntent".equals(intentName)) {
            return getHelpResponse();
        } 
		else if ("WhenToHarvestCabbage".equals(intentName)){
			return whenToHarvestCabbage();
		}
		else {
            throw new SpeechletException("Invalid Intent");
        }
    }

    @Override
    public void onSessionEnded(final SessionEndedRequest request, final Session session)
            throws SpeechletException {
        log.info("onSessionEnded requestId={}, sessionId={}", request.getRequestId(),
                session.getSessionId());
        // any cleanup logic goes here
    }

    private SpeechletResponse getWelcomeResponse() {
        String speechText = "Welcome to the Farming guide. You can ask about the weather, ask what crops you should grow, or ask when a certain crop should be harvested.";

        // Create the Simple card content.
        SimpleCard card = new SimpleCard();
        card.setTitle("HelloWorld");
        card.setContent(speechText);

        // Create the plain text output.
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        // Create reprompt
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(speech);

        return SpeechletResponse.newAskResponse(speech, reprompt, card);
    }
    
    private SpeechletResponse whenToHarvestCabbage(){
    	int i = 0;
    	String speechText = "It takes " + crops[i][5] + " weeks on average to grow Cabbage. " 
    			+ "You can also harvest based on the look of Cabbage. "
    			+ " You can harvest " + crops[i][0] + crops[i][4];

        // Create the Simple card content.
        SimpleCard card = new SimpleCard();
        card.setTitle("CropResponse");
        card.setContent(speechText);

        // Create the plain text output.
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        return SpeechletResponse.newTellResponse(speech, card);
    }
	
	private SpeechletResponse getCropInfo() {
		String speechText = "Okay. So you want to know about a vegetable. This vegetable has these attributes";

        // Create the Simple card content.
        SimpleCard card = new SimpleCard();
        card.setTitle("CropResponse");
        card.setContent(speechText);

        // Create the plain text output.
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        return SpeechletResponse.newTellResponse(speech, card);
	}
	
	private SpeechletResponse cropComplexCabbage(){
		String response = "";
		int i = 0;
		
		response = "You can grow " + crops[i][0] 
				+ " best when the Temperature is " + crops[i][2] 
				+ " , and the humidity is greater than 50 percent. "
				+ "You should plant this crop when the temperature is " + crops[i][1] + "."
				+ " and preferably " + crops[i][3] + "."
				+ " You can harvest " + crops[i][0] + crops[i][4];
		
		String speechText = response;
		
		// Create the Simple card content.
        SimpleCard card = new SimpleCard();
        card.setTitle("Cabbage");
        card.setContent(speechText);

        // Create the plain text output.
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        return SpeechletResponse.newTellResponse(speech, card);
	}
	
	private SpeechletResponse cropComplexCauliflower(){
		String response = "";
		int i = 1;
		
		response = "You can grow " + crops[i][0] 
				+ " best when the Temperature is " + crops[i][2] 
				+ " ,and the humidity is greater than 50 percent"
				+ "You should plant this crop when the temperature is " + crops[i][1] + "."
				+ " and preferably " + crops[i][3] + "."
				+ " You can harvest " + crops[i][0] + crops[i][4];
		
		
		String speechText = response;
		
		// Create the Simple card content.
        SimpleCard card = new SimpleCard();
        card.setTitle("Cauliflower");
        card.setContent(speechText);

        // Create the plain text output.
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        return SpeechletResponse.newTellResponse(speech, card);
	}
	
	private SpeechletResponse whatToPlant(){
		
		String JSONData = "{\"coord\":{\"lon\":-82.46,\"lat\":27.95},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"base\":\"cmc stations\",\"main\":{\"temp\":300.41,\"pressure\":1017,\"humidity\":79,\"temp_min\":297.59,\"temp_max\":302.59},\"wind\":{\"speed\":1.5},\"clouds\":{\"all\":1},\"dt\":1469283629,\"sys\":{\"type\":1,\"id\":680,\"message\":0.0033,\"country\":\"US\",\"sunrise\":1469270892,\"sunset\":1469319853},\"id\":4174757,\"name\":\"Tampa\",\"cod\":200}{\"coord\":{\"lon\":-82.46,\"lat\":27.95},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04d\"}],\"base\":\"stations\",\"main\":{\"temp\":305.23,\"pressure\":1020,\"humidity\":70,\"temp_min\":304.15,\"temp_max\":307.59},\"visibility\":16093,\"wind\":{\"speed\":4.1,\"deg\":80},\"clouds\":{\"all\":75},\"dt\":1470069511,\"sys\":{\"type\":1,\"id\":680,\"message\":0.0489,\"country\":\"US\",\"sunrise\":1470048790,\"sunset\":1470097118},\"id\":4174757,\"name\":\"Tampa\",\"cod\":200}";
		
		//create JSON object to pull data
		JSONObject obj = new JSONObject(JSONData);
		
		String response = "";
		
		//weather data variables
		String description = (obj.getJSONArray("weather").getJSONObject(0).getString("description"));
		double humidity = (obj.getJSONObject("main").getDouble("humidity"));
		double temp = (obj.getJSONObject("main").getDouble("temp"));
		double trueTemp = (((temp  - 273) * 9/5) + 32);
		int finalTemp = (int)trueTemp;
		double minTemp = (obj.getJSONObject("main").getDouble("temp_min"));
		int minTempFinal = (int)minTemp;
		double maxTemp = (obj.getJSONObject("main").getDouble("temp_max"));
		int maxTempFinal = (int)maxTemp;
		double windSpeed = (obj.getJSONObject("wind").getDouble("speed"));
		int trueSpeed = (int)windSpeed;
		
		//if 1, fair time, 2 good time, 3 great time
		int count = 0;
		boolean germinate = false;
		boolean plant = false;
		boolean humidityBool = false;
		boolean windBool = false;
		
		String[] goodCrops = new String[2];
		
		for(int i = 0; i < 2; i++){
			count = 0;
			if((Integer.parseInt(crops[i][1]) > minTempFinal) && (Integer.parseInt(crops[i][1]) < maxTempFinal)){
				count++;
				germinate = true;
			}
			if((Integer.parseInt(crops[i][2]) > minTempFinal) && (Integer.parseInt(crops[i][2]) < maxTempFinal)){
				count++;
				plant = true;
			}
			if(humidity > 50.0){
				count++;
				humidityBool = true;
			}
			if(count > 0){
				goodCrops[i] = crops[i][0];
			}
			if(windSpeed < 5.0)
				windBool = true;
		}
		
		for(int i = 0; i < 2; i++){
			if(goodCrops[i] != "")
				response = response + goodCrops[i] + ","; 
		}
		
		String speechText = "Today is a good day to plant " + response + ". This is because ";
		
		if(germinate == true)
			speechText += "The germination temperature is optimal,";
		if(plant == true)
			speechText += "The growing temperature is optimal,";
		if(humidityBool == true)
			speechText += "The humidity is optimal,";
		if(windBool == true)
			speechText += "And the wind speed is fairly low.";
		else
			speechText += "However the wind speed is fairly high.";
		
        // Create the Simple card content.
        SimpleCard card = new SimpleCard();
        card.setTitle("WeatherData");
        card.setContent(speechText);

        // Create the plain text output.
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        return SpeechletResponse.newTellResponse(speech, card);
	}
	
	private SpeechletResponse getWeatherData() throws IOException{
		String cityName = "Tampa";
		String id = "4174757";
		String response = "";
		String key = "5e69bf5a40e191d0d02594b8452ead22";
		URL call = new URL("http://api.openweathermap.org/data/2.5/weather?id=" + id + "&APPID=" + key);
		
		URLConnection connection = call.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine;

		while ((inputLine = in.readLine()) != null){
			response += inputLine;
		}
		
		//api response
		String JSONData = response; //"{\"coord\":{\"lon\":-82.46,\"lat\":27.95},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"base\":\"cmc stations\",\"main\":{\"temp\":300.41,\"pressure\":1017,\"humidity\":79,\"temp_min\":297.59,\"temp_max\":302.59},\"wind\":{\"speed\":1.5},\"clouds\":{\"all\":1},\"dt\":1469283629,\"sys\":{\"type\":1,\"id\":680,\"message\":0.0033,\"country\":\"US\",\"sunrise\":1469270892,\"sunset\":1469319853},\"id\":4174757,\"name\":\"Tampa\",\"cod\":200}{\"coord\":{\"lon\":-82.46,\"lat\":27.95},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04d\"}],\"base\":\"stations\",\"main\":{\"temp\":305.23,\"pressure\":1020,\"humidity\":70,\"temp_min\":304.15,\"temp_max\":307.59},\"visibility\":16093,\"wind\":{\"speed\":4.1,\"deg\":80},\"clouds\":{\"all\":75},\"dt\":1470069511,\"sys\":{\"type\":1,\"id\":680,\"message\":0.0489,\"country\":\"US\",\"sunrise\":1470048790,\"sunset\":1470097118},\"id\":4174757,\"name\":\"Tampa\",\"cod\":200}";
		
		//create JSON object to pull data
		JSONObject obj = new JSONObject(JSONData);
		
		//weather data variables
		String description = (obj.getJSONArray("weather").getJSONObject(0).getString("description"));
		double humidity = (obj.getJSONObject("main").getDouble("humidity"));
		double temp = (obj.getJSONObject("main").getDouble("temp"));
		double trueTemp = (((temp  - 273) * 9/5) + 32);
		int finalTemp = (int)trueTemp;
		double minTemp = (obj.getJSONObject("main").getDouble("temp_min"));
		double maxTemp = (obj.getJSONObject("main").getDouble("temp_max"));
		double windSpeed = (obj.getJSONObject("wind").getDouble("speed"));
		int trueSpeed = (int)windSpeed;
		
		String speechText = "Today it is " + description + ". The humidity is " + Double.toString(humidity) + " percent . The current temperature is " + Double.toString(finalTemp) + "degrees and the wind speed is " + Double.toString(trueSpeed) + "miles per hour";// + " with a high of " Double.toString(maxTemp) + " and a low of " + Double.toString(minTemp);
		
        // Create the Simple card content.
        SimpleCard card = new SimpleCard();
        card.setTitle("WeatherData");
        card.setContent(speechText);

        // Create the plain text output.
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        return SpeechletResponse.newTellResponse(speech, card);
	}

    private SpeechletResponse getHelpResponse() {
        String speechText = "Here are some example phrases: What is the weather, What should I grow, How Do I grow cabbage";

        // Create the Simple card content.
        SimpleCard card = new SimpleCard();
        card.setTitle("HelloWorld");
        card.setContent(speechText);

        // Create the plain text output.
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        // Create reprompt
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(speech);

        return SpeechletResponse.newAskResponse(speech, reprompt, card);
    }
}

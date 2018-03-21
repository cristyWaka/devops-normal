package com.jenkins.utils;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DogsOperations {
	
	/**
	 * Returns a random dog image url
	 * Using the endpoint https://dog.ceo/api/breeds/image/random
	 * 
	 * Return: URL String similar to 'https://dog.ceo/api/img/maltese/n02085936_8350.jpg'
	 * as a result of parsing the message field of the dog
	 */
	public String getRandomDogImage() {
		// Get JSON Object form REST API (Use JSONObject from org.json already imported on POM file)
		JSONObject obj = NetUtils.get("https://dog.ceo/api/breeds/image/random");
		
		// Get image field
		String image = "";
		try{
			image = obj.getString("message");
		}
		catch(JSONException e) {
			e.printStackTrace();
		}
		// Return image field
		return image;
	}
	
	/**
	 * Returns an ArrayList of available breeds
	 * Using the endpoint https://dog.ceo/api/breeds/list
	 * 
	 */
	public ArrayList<String> getBreedList() {
		// Get JSON Object form REST API (Use JSONObject from org.json already imported on POM file)
		JSONObject obj = NetUtils.get("https://dog.ceo/api/breeds/list");
		
		// Get breeds field (get and transform JSONArray into ArrayList<String>)
		ArrayList<String> breeds = new ArrayList<>();
		try{
			JSONArray breedArray = obj.getJSONArray("message");
			for (int i=0; i<breedArray.length(); i++) {
				breeds.add(breedArray.getString(i));
			} 
		}
		catch(JSONException e) {
			e.printStackTrace();
		}
		// Return list of breeds
		return breeds;
	}
}

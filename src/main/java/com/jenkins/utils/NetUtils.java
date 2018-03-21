package com.jenkins.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class NetUtils {
	
	public static JSONObject get(String path) {
		return get(path, new HashMap<String, Object>());
	}
	
	public static JSONObject get(String path , Map<String, Object> params) {
		URL url;
		JSONObject obj = null;
		try {
			url = new URL(path + getParams(params));
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			obj = getJSONRequestOutput(con);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	public JSONObject post(String path) {
		return post(path, new HashMap<String, Object>());
	}
	
	public JSONObject post(String path, Map<String, Object> params) {
		URL url;
		JSONObject obj = null;
		try {
			url = new URL(path);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			// Add request header
			con.setRequestMethod("POST");
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			con.setRequestProperty( "charset", StandardCharsets.UTF_8.toString());
			con.setDoOutput(true);		
			
			String postData = getParams(params);
			if (postData.length() > 0) {
				DataOutputStream wr = new DataOutputStream(con.getOutputStream());	
				byte[] dataBytes = postData.toString().getBytes(StandardCharsets.UTF_8.toString());
				wr.write(dataBytes);
				wr.flush();
				wr.close();
			}
			obj = getJSONRequestOutput(con);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	private static String getParams(Map<String, Object> params) throws UnsupportedEncodingException {
		StringBuilder paramsData = new StringBuilder();
		if(params != null) {
	        for (Map.Entry<String,Object> param : params.entrySet()) {
	            if (paramsData.length() != 0)
	            	paramsData.append('&');
	            else
	            	paramsData.append("/");
	            paramsData.append(URLEncoder.encode(param.getKey(), StandardCharsets.UTF_8.toString()));
	            paramsData.append('=');
	            paramsData.append(URLEncoder.encode(String.valueOf(param.getValue()), StandardCharsets.UTF_8.toString()));
	        }
		}
        return paramsData.toString();
	}
	
	private static JSONObject getJSONRequestOutput(HttpURLConnection con) throws IOException, JSONException {
		try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
			String inputLine;
			StringBuilder response = new StringBuilder();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}

			JSONTokener tokener = new JSONTokener(response.toString());
			JSONObject obj = new JSONObject(tokener);
			return obj;
		}
	}
}

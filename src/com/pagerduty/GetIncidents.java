/* GetIncidents class is used to lists the 
 * timestamp and incident numbers of last 10 resolved issue(You can change the limit value to change the number of incidents)
 * It makes a call to Pager Duty Incident Api to get the timestamp(in ISO 8601 Representation)
 * Needs java-json.jar to parse json output
 * Created: 6th June 2014
 * Author: Sagar Dhedia
 */

package com.pagerduty;
import java.net.HttpURLConnection;
import java.text.*;
import java.net.URL;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.json.*;

public class GetIncidents {

	// Url for demo
	public String url;
	public String token;
	public int count=0;
	public final int limit=10;
	
	public GetIncidents(String url,String token){
		this.url = url;
		this.token=token;
	}

	//Method to get the timestamps of last 10 resolved incidents
	public int getTimeStamp() throws IOException,JSONException{
		
		//List of parameters to add to request
		String param = "?status=resolved&limit="+limit+"&fields=incident_number,last_status_change_on&" +
				       "sort_by=resolved_on:desc";
		URL obj=null;
		HttpURLConnection con=null;

		//Create the url and connection object
		try {
			obj = new URL(url+param);
			con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Authorization"," Token token=" + token);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if(con.getResponseCode()!=200){
			System.out.println("Connection failed with Error Code:" + con.getResponseCode());
			return con.getResponseCode();
		}
		
		//Read the json response
		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
		String inputLine;
		String value=null;
		while ((inputLine = in.readLine()) != null) {
			value = inputLine;
		}
		in.close();

		//Parse the json object
		JSONObject jObject=null;
		try {
			jObject = new JSONObject(value);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		JSONArray array = jObject.getJSONArray("incidents");
		//Print the time stamps of recent incidents
		while(count<array.length()){
			System.out.println("Incident number:"  + array.getJSONObject(count).getInt("incident_number")	
					+ " Resolved on :" + array.getJSONObject(count).getString("last_status_change_on"));
			count++;
		}
		
		return 1;
	}

	public static void main(String[] args) throws IOException, JSONException{
		
		GetIncidents g = new GetIncidents("https://webdemo.pagerduty.com/api/v1/incidents","VxuRAAxQoTgTjbo7wmmG");
		g.getTimeStamp();
	}

 }

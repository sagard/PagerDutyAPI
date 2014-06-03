package com.pagerduty.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.UnknownHostException;
import org.json.JSONException;
import org.junit.Test;
import com.pagerduty.GetIncidents;

public class GetIncidentsTest {
	String url = "https://webdemo.pagerduty.com/api/v1/incidents";
	String token = "VxuRAAxQoTgTjbo7wmmG";
	
	GetIncidents g = new GetIncidents(url,token);

	@Test(expected = UnknownHostException.class)
	public void wrongURL() throws UnknownHostException {
		g.url="https://web demo.pagerduty.com/api/v1/incidents";
		throw new UnknownHostException();
	}
	
	@Test(expected = UnknownHostException.class)
	public void nullURL() throws UnknownHostException {
		g.url="";
		throw new UnknownHostException();
	}
	
		
	@Test
	public void wrongKey(){
		g.token="dummy";
		int returnValue=0;
		try {
			returnValue = g.getTimeStamp();
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("Authentication fail",401,returnValue);
	}
	
	@Test
	public void correctKey(){
		int returnValue=0;
		try {
			returnValue = g.getTimeStamp();
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("Success",1,returnValue);
	}
	
	@Test
	public void checkCount(){
		try {
			g.getTimeStamp();
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("Correct number of results",10,g.count);
	    
	}
	
	
}

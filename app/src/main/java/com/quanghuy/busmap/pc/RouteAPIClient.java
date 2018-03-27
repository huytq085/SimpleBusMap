package com.quanghuy.busmap.pc;

import android.util.Log;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.quanghuy.busmap.entity.Route;
import com.quanghuy.busmap.utils.JsonUtils;
import com.quanghuy.busmap.utils.RestAPIClient;

import okhttp3.Request;

public class RouteAPIClient extends RestAPIClient{
	private static final String USER_AGENT = "Mozilla/5.0";
	private static final String DOMAIN_PATTERN = "http://44eead29.ngrok.io/%s";
	private static final String GET_ROUTES_URL = "/api/route/getroutes";
	private static final String GET_ROUTE_URL = "/api/route/getroute?code=1";
	private static final String ADD_ROUTE_URL = "/api/route/addroute";
	private static final String UPDATE_ROUTE_URL = "/api/route/updateroute/";
	private static final String DELETE_ROUTE_URL = "/api/route/deleteroute/%s";
	private static final long pausedInMiliseconds = 501;
	public RouteAPIClient() {
		super();
	}
	

	public List<Route> getRoutes(){
		List<Route> routes = null;
		String url = String.format(DOMAIN_PATTERN, GET_ROUTES_URL);
		try {
			String res = get(url);
			Log.d("TEST", "getRoutes: " + res);
			if (res != null || !res.equals("null")){
				Route[] arrRoute = JsonUtils.decode(res, Route[].class);
				if (arrRoute.length > 0) {
					routes = Arrays.asList(arrRoute);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return routes;
	}

//	public Route getRoute(int code){
//		Route route = null;
//		String url = String.format(DOMAIN_PATTERN, GET_ROUTE_URL);
//		StringWriter writer = runRequest(new HttpGet(url), null);
//		if (writer != null) {
//			route = JsonUtils.decode(writer.toString(), Route.class);
//		}
//		return route;
//	}
//
//	public boolean addRoute(Route route) {
//		String url = String.format(DOMAIN_PATTERN, ADD_ROUTE_URL);
//		String data = JsonUtils.encode(route);
//		System.out.println(data);
//		StringWriter writer = runRequest(new HttpPost(url), data);
//		if (writer != null) {
//			return Boolean.valueOf(writer.toString());
//		}
//		return false;
//	}
//
//	public boolean updateRoute(Route route) {
//		String url = String.format(DOMAIN_PATTERN, UPDATE_ROUTE_URL);
//		String data = JsonUtils.encode(route);
//		System.out.println(data);
//		StringWriter writer = runRequest(new HttpPost(url), data);
//		if (writer != null) {
//			return Boolean.valueOf(writer.toString());
//		}
//		return false;
//	}
//
//	public boolean deleteRoute(int code) {
//		String url = String.format(DOMAIN_PATTERN, DELETE_ROUTE_URL);
//		StringWriter writer = runRequest(new HttpDelete(url), null);
//		if (writer != null){
//			return Boolean.valueOf(writer.toString());
//		}
//		return false;
//	}
//
//
//	public List<String> parseJsonList(String nodeName, String jsonStr) {
//		List<String> data = new ArrayList<String>();
//		ObjectMapper m = new ObjectMapper();
//		try {
//			JsonNode rootNode = m.readTree(jsonStr);
//			Iterator<JsonNode> items = rootNode.path(nodeName).elements();
//			while(items.hasNext()) {
//				JsonNode entry = items.next();
//				if(!entry.isTextual()) {
//					data.add(entry.toString());
//				} else {
//					data.add(entry.asText());
//				}
//			}
//		} catch (JsonProcessingException e) {
//			// TODO Auto-generated catch block
////			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
////			e.printStackTrace();
//		}
//		return data;
//	}


	public static void main(String[] args) {
		RouteAPIClient client = new RouteAPIClient();
		System.out.println(JsonUtils.encode(client.getRoutes()));
	}
	
}

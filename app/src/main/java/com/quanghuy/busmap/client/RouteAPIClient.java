package com.quanghuy.busmap.client;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.quanghuy.busmap.entity.Route;
import com.quanghuy.busmap.utils.JsonUtils;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RouteAPIClient extends RestAPIClient{
	private static final String DOMAIN_PATTERN = "http://busmap.somee.com/%s";
	private static final String GET_ROUTES_URL = "/api/route/getroutes";
	private static final String GET_ROUTE_URL = "/api/route/getroute?code=";
	private static final String ADD_ROUTE_URL = "/api/route/addroute";
	private static final String UPDATE_ROUTE_URL = "/api/route/updateroute/";
	private static final String DELETE_ROUTE_URL = "/api/route/deleteroute/";
	private static final String HEADER_CONTENT_TYPE = "application/json; charset=utf-8";
	private static final String TOKEN_TYPE = "bearer";
	public RouteAPIClient() {
		super(HEADER_CONTENT_TYPE);
	}

	public RouteAPIClient(String accessToken) {
		super(HEADER_CONTENT_TYPE, TOKEN_TYPE, accessToken);
	}
	
// Get all routes
	public ArrayList<Route> getRoutes(){
		List<Route> routes = null;
		String url = String.format(DOMAIN_PATTERN, GET_ROUTES_URL);
		Log.d("aaaaaaaaaaaaaa=======", "getRoutes: " + url);
		try {
//			get
			Response res = get(url);
			String resBody = res.body().string();
			if (!resBody.equals("null")){
				Route[] arrRoute = JsonUtils.decode(resBody, Route[].class);
				if (arrRoute != null && arrRoute.length > 0) {
					routes = Arrays.asList(arrRoute);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		ArrayList<Route> ret = new ArrayList<>(routes);
		return ret;
	}

	public Route getRoute(int code){
		Route route = null;
		String url = String.format(DOMAIN_PATTERN, GET_ROUTE_URL) + code;
		try {
			Response res = get(url);
			String resBody = res.body().string();
			if (!resBody.equals("null")) {
				route = JsonUtils.decode(resBody, Route.class);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return route;
	}

	public boolean addRoute(Route route) {
		String url = String.format(DOMAIN_PATTERN, ADD_ROUTE_URL);
		String data = JsonUtils.encode(route);
		try {
//post
			Response res = post(url, data);
			String resBody = res.body().string();
			Log.d("TEST", "data: " + data);
			Log.d("TEST", "addRoute: " + res);
			if (resBody.equals("true") || resBody.equals("false")){
				return Boolean.valueOf(resBody);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean updateRoute(Route route) {
		String url = String.format(DOMAIN_PATTERN, UPDATE_ROUTE_URL);
		String data = JsonUtils.encode(route);
		System.out.println(data);
		try {
			Response res = put(url, data);
			String resBody = res.body().string();
			Log.d("Test", "updateRoute: " + data);
			Log.d("Test", "updateRoute: " + resBody);
			if (resBody.equals("true") || resBody.equals("false")){
				return Boolean.valueOf(resBody);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean deleteRoute(int code) {
		String url = String.format(DOMAIN_PATTERN, DELETE_ROUTE_URL) + code;
		try {
			Response res = delete(url);
			String resBody = res.body().string();
			if (resBody.equals("true") || resBody.equals("false")){
				return Boolean.valueOf(resBody);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}


	public List<String> parseJsonList(String nodeName, String jsonStr) {
		List<String> data = new ArrayList<String>();
		ObjectMapper m = new ObjectMapper();
		try {
			JsonNode rootNode = m.readTree(jsonStr);
			Iterator<JsonNode> items = rootNode.path(nodeName).elements();
			while(items.hasNext()) {
				JsonNode entry = items.next();
				if(!entry.isTextual()) {
					data.add(entry.toString());
				} else {
					data.add(entry.asText());
				}
			}
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		return data;
	}


	public static void main(String[] args) {
		RouteAPIClient client = new RouteAPIClient();
		System.out.println(JsonUtils.encode(client.getRoutes()));
	}
	
}

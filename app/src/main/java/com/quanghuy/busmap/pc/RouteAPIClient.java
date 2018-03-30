package com.quanghuy.busmap.pc;

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
import com.quanghuy.busmap.utils.RestAPIClient;

public class RouteAPIClient extends RestAPIClient{
	private static final String DOMAIN_PATTERN = "http://busmap.somee.com/%s";
	private static final String GET_ROUTES_URL = "/api/route/getroutes";
	private static final String GET_ROUTE_URL = "/api/route/getroute?code=";
	private static final String ADD_ROUTE_URL = "/api/route/addroute";
	private static final String UPDATE_ROUTE_URL = "/api/route/updateroute/";
	private static final String DELETE_ROUTE_URL = "/api/route/deleteroute/";
	public RouteAPIClient() {
		super();
	}
	

	public ArrayList<Route> getRoutes(){
		List<Route> routes = null;
		String url = String.format(DOMAIN_PATTERN, GET_ROUTES_URL);
		try {
			String res = get(url);
			if (!res.equals("null")){
				Route[] arrRoute = JsonUtils.decode(res, Route[].class);
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
			String res = get(url);
			if (!res.equals("null")) {
				route = JsonUtils.decode(res, Route.class);
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
			String res = post(url, data);
			Log.d("TEST", "data: " + data);
			Log.d("TEST", "addRoute: " + res);
			if (res.equals("true") || res.equals("false")){
				return Boolean.valueOf(res);
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
			String res = put(url, data);
			Log.d("Test", "updateRoute: " + data);
			Log.d("Test", "updateRoute: " + res);
			if (res.equals("true") || res.equals("false")){
				return Boolean.valueOf(res);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean deleteRoute(int code) {
		String url = String.format(DOMAIN_PATTERN, DELETE_ROUTE_URL) + code;
		try {
			String res = delete(url);
			if (res.equals("true") || res.equals("false")){
				return Boolean.valueOf(res);
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

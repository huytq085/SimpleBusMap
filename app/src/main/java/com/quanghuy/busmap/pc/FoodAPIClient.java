package com.quanghuy.busmap.pc;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Array;
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

public class FoodAPIClient extends RestAPIClient{
	private static final String USER_AGENT = "Mozilla/5.0";
	private static final String DOMAIN_PATTERN = "http://localhost:3000/%s";
	private static final String GET_ROUTES_URL = "/api/route/getroutes";
	private static final String GET_ROUTE_URL = "/api/route/getroute?code=1";
	private static final String ADD_ROUTE_URL = "/api/route/addroute";
	private static final String UPDATE_ROUTE_URL = "/api/route/updateroute/";
	private static final String DELETE_ROUTE_URL = "/api/route/deleteroute/%s";
	private static final long pausedInMiliseconds = 501;
	public FoodAPIClient() {
		super();
	}
	
	private <T extends HttpRequestBase> StringWriter runRequest(T request, String data) {
		request.addHeader("content-type", "application/json;charset=UTF-8");
		request.addHeader("User-Agent", USER_AGENT);
		CloseableHttpResponse response = null;
		StringWriter writer = new StringWriter();
		try {
			String method = request.getMethod();
			if(HttpGet.METHOD_NAME.equals(method)) {
				response = get((HttpGet) request, pausedInMiliseconds);
				IOUtils.copy(response.getEntity().getContent(), writer, "UTF-8");
			} else if(HttpPost.METHOD_NAME.equals(method)) {
				response = post((HttpPost)request, data);
				IOUtils.copy(response.getEntity().getContent(), writer, "UTF-8");
			} else if(HttpPut.METHOD_NAME.equals(method)) {
				response = put((HttpPut) request, data);
				IOUtils.copy(response.getEntity().getContent(), writer, "UTF-8");
			} else if(HttpPatch.METHOD_NAME.equals(method)) {
				response = patch((HttpPatch) request, data);
				IOUtils.copy(response.getEntity().getContent(), writer, "UTF-8");
			} else if(HttpDelete.METHOD_NAME.equals(method)) {
				response = delete((HttpDelete)request);
				if (response.getEntity() != null){
					IOUtils.copy(response.getEntity().getContent(), writer, "UTF-8");
				} else {
					writer = null;
				}
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		} finally {
			String method = request.getMethod();
			if(HttpGet.METHOD_NAME.equals(method)) {
				close((HttpGet) request, response);
			} else if(HttpPost.METHOD_NAME.equals(method)) {
				close((HttpPost) request, response);
			} else if(HttpPut.METHOD_NAME.equals(method)) {
				close((HttpPut)request, response);
			} else if(HttpPatch.METHOD_NAME.equals(method)) {
				close((HttpPatch)request, response);
			} else if(HttpDelete.METHOD_NAME.equals(method)) {
				close((HttpDelete)request, response);
			}
		}
		return writer;
	}

	public List<Route> getRoutes(){
		List<Route> routes = null;
		String url = String.format(DOMAIN_PATTERN, GET_ROUTES_URL);
		StringWriter writer = runRequest(new HttpGet(url), null);
		if (writer != null) {
			Route[] arrRoute = JsonUtils.decode(writer.toString(), Route[].class);
			if (arrRoute.length > 0) {
				routes = Arrays.asList(arrRoute);
			}
		}
		return routes;
	}
	
	public Route getRoute(int code){
		Route route = null;
		String url = String.format(DOMAIN_PATTERN, GET_ROUTE_URL);
		StringWriter writer = runRequest(new HttpGet(url), null);
		if (writer != null) {
			route = JsonUtils.decode(writer.toString(), Route.class);
		}
		return route;
	}

	public boolean addRoute(Route route) {
		String url = String.format(DOMAIN_PATTERN, ADD_ROUTE_URL);
		String data = JsonUtils.encode(route);
		System.out.println(data);
		StringWriter writer = runRequest(new HttpPost(url), data);
		if (writer != null) {
			return Boolean.valueOf(writer.toString());
		}
		return false;
	}

	public boolean updateRoute(Route route) {
		String url = String.format(DOMAIN_PATTERN, UPDATE_ROUTE_URL);
		String data = JsonUtils.encode(route);
		System.out.println(data);
		StringWriter writer = runRequest(new HttpPost(url), data);
		if (writer != null) {
			return Boolean.valueOf(writer.toString());
		}
		return false;
	}

	public boolean deleteRoute(int code) {
		String url = String.format(DOMAIN_PATTERN, DELETE_ROUTE_URL);
		StringWriter writer = runRequest(new HttpDelete(url), null);
		if (writer != null){
			return Boolean.valueOf(writer.toString());
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

		
	}
	
}

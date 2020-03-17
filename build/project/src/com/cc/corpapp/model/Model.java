package com.cc.corpapp.model;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.cc.corpapp.Main;

public abstract class Model {
	
	private static final String url = "http://192.168.0.101/api/";
	private static final String version = "v1/";
	private static final int port = 3000;
	
	protected static String getRequest(String controller, Map<String, String> params, boolean skip_auth) {
		
		CloseableHttpClient client = getHttpClient();
		
		try {
			
			String final_url;
			
			if (!controller.contains("admins") && !controller.contains("delivery_executives"))
				final_url = Model.url + Model.version + controller;
			else
				final_url = Model.url + controller;
			
			URIBuilder uriBuilder = new URIBuilder(final_url).setPort(Model.port);
			
			if (params != null)
				params.forEach( (k, v) -> {
					uriBuilder.addParameter(k, v);
				});
			
			HttpGet get = new HttpGet(uriBuilder.build());
			
			if (!skip_auth)
				get.addHeader("Authorization", Main.getCurrentAdmin().getAuthToken());
			
			HttpResponse response = client.execute(get);
			
			String json = EntityUtils.toString(response.getEntity());
			
			return json;
			
		} catch(IOException | URISyntaxException e) {
			
			e.printStackTrace();
			
		} finally {
			
			Model.closeClient(client);
			
		}
		
		return null;
	}
	
	protected static String postRequest(String controller, Map<String, String> params, boolean skip_auth) {
		
		CloseableHttpClient client = getHttpClient();
		
		try {
			
			String final_url;
			
			if (!controller.contains("admins") && !controller.contains("delivery_executives"))
				final_url = Model.url + Model.version + controller;
			else
				final_url = Model.url + controller;
			
			URIBuilder uriBuilder = new URIBuilder(final_url).setPort(Model.port);
			
			List<NameValuePair> post_params = new ArrayList<>();
			
			if (params != null)
				params.forEach((k, v) -> {
					post_params.add(new BasicNameValuePair(k, v));
				});
			
			uriBuilder.setParameters(post_params);
			
			HttpPost post = new HttpPost(uriBuilder.build());
			
			if (!skip_auth)
				post.addHeader("Authorization", Main.getCurrentAdmin().getAuthToken());
			
			HttpResponse response = client.execute(post);
			
			String json = EntityUtils.toString(response.getEntity());
			
			return json;
			
		} catch(IOException | URISyntaxException e) {
			
			e.printStackTrace();
			
		} finally {
			
			Model.closeClient(client);
			
		}
		
		return null;
	}
	
	protected static String patchRequest(String controller, Map<String, String> params, boolean skip_auth) {
		
		CloseableHttpClient client = getHttpClient();
		
		try {
			
			String final_url;
			
			if (!controller.contains("admins") && !controller.contains("delivery_executives"))
				final_url = Model.url + Model.version + controller;
			else
				final_url = Model.url + controller;
			
			URIBuilder uriBuilder = new URIBuilder(final_url).setPort(Model.port);
			
			List<NameValuePair> post_params = new ArrayList<>();
			
			if (params != null)
				params.forEach((k, v) -> {
					post_params.add(new BasicNameValuePair(k, v));
				});
			
			uriBuilder.setParameters(post_params);
			
			HttpPatch post = new HttpPatch(uriBuilder.build());
			
			if (!skip_auth)
				post.addHeader("Authorization", Main.getCurrentAdmin().getAuthToken());
			
			HttpResponse response = client.execute(post);
			
			String json = EntityUtils.toString(response.getEntity());
			
			return json;
			
		} catch (IOException | URISyntaxException e) {
			
			e.printStackTrace();
			
		} finally {
			
			Model.closeClient(client);
			
		}
		
		return null;
	}
	
	protected static String deleteRequest(String controller, int id) {
		
		CloseableHttpClient client = getHttpClient();
		
		try {
			
			URIBuilder uriBuilder = new URIBuilder(Model.url + Model.version + controller + "/" + id).setPort(Model.port);
			
			HttpDelete delete = new HttpDelete(uriBuilder.build());
			
			delete.addHeader("Authorization", Main.getCurrentAdmin().getAuthToken());
			
			HttpResponse response = client.execute(delete);
			
			String json = EntityUtils.toString(response.getEntity());
			
			return json;
			
			
		} catch (IOException | URISyntaxException e) {
			
			e.printStackTrace();
			
		} finally {
			
			Model.closeClient(client);
			
		}
		
		return null;
	}
	
	protected static String deleteRequest(String controller, Map<String, String> params) {
		
		CloseableHttpClient client = getHttpClient();
		
		try {
			
			URIBuilder uriBuilder = new URIBuilder(Model.url + Model.version + controller).setPort(Model.port);
			
			List<NameValuePair> post_params = new ArrayList<>();
			
			if (params != null)
				params.forEach((k, v) -> {
					post_params.add(new BasicNameValuePair(k, v));
				});
			
			uriBuilder.setParameters(post_params);
			
			HttpDelete delete = new HttpDelete(uriBuilder.build());
			
			delete.addHeader("Authorization", Main.getCurrentAdmin().getAuthToken());
			
			HttpResponse response = client.execute(delete);
			
			String json = EntityUtils.toString(response.getEntity());
			
			return json;
			
			
		} catch (IOException | URISyntaxException e) {
			
			e.printStackTrace();
			
		} finally {
			
			Model.closeClient(client);
			
		}
		
		return null;
	}
	
	
	private static CloseableHttpClient getHttpClient() {
		return HttpClients.createDefault();
	}
	
	private static void closeClient(CloseableHttpClient client) {
		
		try {
			
			client.close();
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
	}

}

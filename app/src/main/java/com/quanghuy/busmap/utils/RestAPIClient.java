package com.quanghuy.busmap.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class RestAPIClient {
	private CloseableHttpClient httpClient;
	public RestAPIClient() {
		httpClient = HttpClients.createDefault();
	}
	public void close(){
        // When HttpClient instance is no longer needed,
        // shut down the connection manager to ensure
        // immediate deallocation of all system resources
		try {
			httpClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public String get(String url) {
		CloseableHttpResponse response = null;
		HttpGet httpGet = null;
		String result = "";
		try {
			httpGet = new HttpGet(url);
			response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity);
			EntityUtils.consume(entity);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
		return result;
	}

	public CloseableHttpResponse get(HttpGet httpGet) {
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpGet);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public CloseableHttpResponse get(HttpGet httpGet, long pausedInMiliseconds) {
		CloseableHttpResponse response = null;
		try {
			try {
				Thread.sleep(pausedInMiliseconds);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			response = httpClient.execute(httpGet);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}	
	
	public HttpGet newGet(String url) {
		return new HttpGet(url);
	}

	public String get(String url, String host, int port) {
		CloseableHttpResponse response = null;
		HttpGet httpGet = null;
		String result = "";
		try {
			HttpHost proxy = new HttpHost(host, port);
			httpGet = new HttpGet(url);
			
			response = httpClient.execute(proxy,httpGet);
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity);
			EntityUtils.consume(entity);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
		return result;
	}

	public String post(String url, String data) {
		CloseableHttpResponse response = null;
		HttpPost httpPost = null;
		String result = "";
		try {
			StringEntity stringEntity = new StringEntity(data);
			httpPost = new HttpPost(url);
			httpPost.setEntity(stringEntity);
			response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity);
			EntityUtils.consume(entity);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
		return result;
	}
	
	public CloseableHttpResponse post(HttpPost httpPost, String data) {
		CloseableHttpResponse response = null;
		try {
			StringEntity stringEntity = new StringEntity(data, "UTF-8");
			httpPost.setEntity(stringEntity);
			response = httpClient.execute(httpPost);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public CloseableHttpResponse post(HttpPost httpPost, StringEntity stringEntity) {
		CloseableHttpResponse response = null;
		try {
			httpPost.setEntity(stringEntity);
			response = httpClient.execute(httpPost);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}	
	
	public CloseableHttpResponse post(HttpPost httpPost, String data, long pausedInMiliseconds) {
		CloseableHttpResponse response = null;
		try {
			try {
				Thread.sleep(pausedInMiliseconds);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			StringEntity stringEntity = new StringEntity(data);
			httpPost.setEntity(stringEntity);
			response = httpClient.execute(httpPost);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public CloseableHttpResponse post(HttpPost httpPost, StringEntity stringEntity, long pausedInMiliseconds) {
		CloseableHttpResponse response = null;
		try {
			try {
				Thread.sleep(pausedInMiliseconds);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			httpPost.setEntity(stringEntity);
			response = httpClient.execute(httpPost);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}	

	public void close(HttpGet httpGet, CloseableHttpResponse response) {
		if (response != null) {
			HttpEntity entity = response.getEntity();
			try {
				EntityUtils.consume(entity);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	        }
		}
	}
	
	public void close(HttpPost httpPost, CloseableHttpResponse response) {
		if (response != null) {
			HttpEntity entity = response.getEntity();
			try {
				EntityUtils.consume(entity);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	        }
		}
	}
	
	public void close(HttpPut httpPut, CloseableHttpResponse response) {
		if (response != null) {
			HttpEntity entity = response.getEntity();
			try {
				EntityUtils.consume(entity);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	        }
		}
	}
	
	public void close(HttpPatch httpPatch, CloseableHttpResponse response) {
		if (response != null) {
			HttpEntity entity = response.getEntity();
			try {
				EntityUtils.consume(entity);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	        }
		}
	}
	
	public void close(CloseableHttpResponse response) {
		if (response != null) {
			HttpEntity entity = response.getEntity();
			try {
				EntityUtils.consume(entity);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	        }
		}
	}	
	
	public void close(HttpDelete httpDelete, CloseableHttpResponse response) {
		if (response != null) {
			HttpEntity entity = response.getEntity();
			try {
				EntityUtils.consume(entity);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	        }
		}
	}
	
	public CloseableHttpResponse patch(HttpPatch httpPatch, String data) {
		CloseableHttpResponse response = null;
		try {
			StringEntity stringEntity = new StringEntity(data);
			httpPatch.setEntity(stringEntity);
			response = httpClient.execute(httpPatch);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public CloseableHttpResponse put(HttpPut httpPut, String data) {
		CloseableHttpResponse response = null;
		try {
			StringEntity stringEntity = new StringEntity(data, "UTF-8");
			httpPut.setEntity(stringEntity);
			response = httpClient.execute(httpPut);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public CloseableHttpResponse put(HttpPut httpPut, String data, long pausedInMiliseconds) {
		CloseableHttpResponse response = null;
		try {
			try {
				Thread.sleep(pausedInMiliseconds);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
			StringEntity stringEntity = new StringEntity(data);
			httpPut.setEntity(stringEntity);
			response = httpClient.execute(httpPut);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public String put(String url, String data) {
		HttpPut httpPut = null;
		CloseableHttpResponse response = null;
		String result = "";
		try {
			StringEntity stringEntity = new StringEntity(data);
			httpPut = new HttpPut(url);
			httpPut.setEntity(stringEntity);
			response = httpClient.execute(httpPut);
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity);
			EntityUtils.consume(entity);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
		return result;
	}
	
	public String delete(String url) {
		CloseableHttpResponse response = null;
		HttpDelete httpDelete = null;
		String result = "";
		try {
			httpDelete = new HttpDelete(url);
			response = httpClient.execute(httpDelete);
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity);
			EntityUtils.consume(entity);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(httpDelete != null)
				httpDelete.releaseConnection();
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
		return result;		
	}
	
	public CloseableHttpResponse delete(HttpDelete httpDelete) {
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpDelete);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;		
	}
	
	public CloseableHttpResponse delete(HttpDelete httpDelete, long pausedInMiliseconds) {
		CloseableHttpResponse response = null;
		try {
			try {
				Thread.sleep(pausedInMiliseconds);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
			response = httpClient.execute(httpDelete);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;		
	}
}

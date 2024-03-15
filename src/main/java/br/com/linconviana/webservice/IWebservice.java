package br.com.linconviana.webservice;

import java.net.http.HttpResponse;

public interface IWebservice {

	public HttpResponse<String> buscarCep(String url);
	
	public HttpResponse<String> httpGet(String url);
	
	public HttpResponse<String> httpPost(String url, Object object);
	
	public HttpResponse<String> httpPut(String url, Object object);
	
	public HttpResponse<String> httpDelete(String url);
}

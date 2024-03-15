package br.com.linconviana.webservice;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import com.fasterxml.jackson.databind.ObjectMapper;

/// :: https://www.baeldung.com/java-9-http-client
/// :: https://openjdk.org/groups/net/httpclient/recipes.html
public class WebServiceMethods implements IWebservice {

	@Override
	public HttpResponse<String> buscarCep(String url) {

		try {

			// cliente HTTP
			HttpClient client = HttpClient.newHttpClient();

			// criar a requisição
			HttpRequest request = HttpRequest.newBuilder().GET().timeout(Duration.ofSeconds(10)).uri(URI.create(url))
					.build();

			// enviando uma solicitação

			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			return response;

		} catch (Exception e) {
			System.out.println("Erro ao buscar Cep! " + e.getMessage());
			return null;
		}
	}

	@Override
	public HttpResponse<String> httpGet(String url) {

		try {

			// cliente HTTP
			HttpClient client = HttpClient.newHttpClient();

			// criar a requisição
			HttpRequest request = HttpRequest.newBuilder()
					.GET().timeout(Duration.ofSeconds(10))
					.uri(URI.create(url))
					.build();

			// enviando uma solicitação

			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			return response;

		} catch (Exception e) {
			System.out.println("Erro ao buscar Cep! " + e.getMessage());
			return null;
		}
	}

	@Override
	public HttpResponse<String> httpPost(String url, Object object) {

		/// :: https://www.macoratti.net/19/07/net_apipub1.htm -> lista de API Publicas
		/// :: https://jsonplaceholder.typicode.com/ -> testar api Rest
		try {

			/// :: Serializar Object
			ObjectMapper objectMapper = new ObjectMapper();
			String requestBody = objectMapper.writeValueAsString(object);

			// cliente HTTP
			HttpClient client = HttpClient.newHttpClient();

			// criar a requisição
			HttpRequest request = HttpRequest.newBuilder()
					.POST(HttpRequest.BodyPublishers.ofString(requestBody))
					.timeout(Duration.ofSeconds(10))
					.uri(URI.create(url))
					.build();

			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			return response;

		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
			return null;
		}
	}

	@Override
	public HttpResponse<String> httpDelete(String url) {

		try {

			// cliente HTTP
			HttpClient client = HttpClient.newHttpClient();

			// criar a requisição
			HttpRequest request = HttpRequest.newBuilder()
					.DELETE()
					.timeout(Duration.ofSeconds(10))
					.uri(URI.create(url))
					.build();

			// enviando uma solicitação
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			return response;

		} catch (Exception e) {
			System.out.println("Erro ao buscar Cep! " + e.getMessage());
			return null;
		}
	}

	@Override
	public HttpResponse<String> httpPut(String url, Object object) {

		try {

			/// :: Serializar Object
			ObjectMapper objectMapper = new ObjectMapper();
			String requestBody = objectMapper.writeValueAsString(object);

			// cliente HTTP
			HttpClient client = HttpClient.newHttpClient();

			// criar a requisição
			HttpRequest request = HttpRequest.newBuilder()
					.PUT(HttpRequest
					.BodyPublishers.ofString(requestBody))
					.timeout(Duration.ofSeconds(10))
					.uri(URI.create(url))
					.build();

			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			return response;

		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
			return null;
		}
	}

}

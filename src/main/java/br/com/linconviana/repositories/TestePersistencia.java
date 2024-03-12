package br.com.linconviana.repositories;

import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;

import br.com.linconviana.entities.Cep;
import br.com.linconviana.entities.Empresa;
import br.com.linconviana.entities.Funcionario;
import br.com.linconviana.entities.RamoAtividade;
import br.com.linconviana.entities.TipoEmpresa;
import br.com.linconviana.utils.Message;

public class TestePersistencia {

	public static String URL_GET = "";
	public static void main(String[] args) {
		
		
		try {
			
			String meuCep = "12705-260";
			
			if((meuCep.contains("-") && meuCep.length() > 0 && meuCep.length() == 9) || (meuCep.length() > 0 && meuCep.length() == 8)) {
				
				URL_GET = "https://viacep.com.br/ws/" + meuCep + "/json/";
				
				// cliente HTTP
				HttpClient client = HttpClient.newHttpClient();
				
				// criar a requisição
				HttpRequest request = HttpRequest.newBuilder()
						.GET()
						.timeout(Duration.ofSeconds(10))
						.uri(URI.create(URL_GET))
						.build();
				
				// enviando uma solicitação
				
				HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
				
				//imprimir o conteúdo recebido
				System.out.println(response.statusCode());
				System.out.println(response.body());
				System.out.println(response.body());
				Object obj = response.body();
				System.out.println(obj);
				
				Gson gson = new Gson();
				
				/// :: Deserializar Object
				Cep objeto = gson.fromJson(response.body(), Cep.class);

				System.out.println(objeto.getLogradouro());
				
				/// :: Serializar Object
				String stringJson = gson.toJson(objeto);
				
			} else {
				System.out.println("Cep esta no formato invalido ou incompleto!");
			}
			
		} catch (Exception e) {
			System.out.println("Erro ao buscar Cep! " + e.getMessage());
		}

		/*EmpresaRepository empresaRepository =  new EmpresaRepository();
		RamoAtividadeRepository ramoAtividadeRepository = new RamoAtividadeRepository();
		
		RamoAtividade ramoAtividade = new RamoAtividade();
		ramoAtividade = ramoAtividadeRepository.findById(2L);
		
		ramoAtividade.setDescricao("Bebidas");
		
		ramoAtividade = ramoAtividadeRepository.save(ramoAtividade);
		
		List<RamoAtividade> listaRamoAtividade = ramoAtividadeRepository.findAll();
		
		Empresa empresa = new Empresa();
		empresa.setNomeFantasia("teste 4");
		empresa.setRazaoSocial("testerazao 4");
		empresa.setCnpj("10.456.789/0009-00");
		empresa.setTipoEmpresa(TipoEmpresa.MEI);
		empresa.setDataFundacao(new Date());
		empresa.setRamoAtividade(listaRamoAtividade.get(3));
		empresa.setFaturamento(new BigDecimal(1250.00));
		
		empresa = empresaRepository.save(empresa);
		
		List<Empresa> listaEmpresa = empresaRepository.findAll();*/
	}
}

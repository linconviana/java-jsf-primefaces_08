package br.com.linconviana.repositories;

import java.io.File;
import java.io.FileWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import com.google.gson.Gson;

import br.com.linconviana.entities.Cep;
import br.com.linconviana.entities.Empresa;
import br.com.linconviana.entities.RamoAtividade;
import br.com.linconviana.entities.TipoEmpresa;
import br.com.linconviana.entities.Xmlcep;
import br.com.linconviana.webservice.WebServiceMethods;


public class TestePersistencia {

	public static void main(String[] args) {
		
		/// :: Arquivos xml
		/// :: https://www.mballem.com/post/xml-trabalhando-com-jaxb/#:~:text=O%20processo%20usado%20para%20transformar,o%20Marshaller%20ou%20um%20Unmarshaller.
		//https://pt.stackoverflow.com/questions/227503/como-transformar-um-objeto-em-xml-no-formato-especificado
		//https://www.devmedia.com.br/serializando-objetos-java-em-xml-com-xstream/3647
		testarHttpPost();
		
		String meuCep = "12705-260";
		//String formato = "json";
		String formato = "xml";
		if((meuCep.contains("-") && meuCep.length() > 0 && meuCep.length() == 9) || (meuCep.length() > 0 && meuCep.length() == 8)) {
			getCep(meuCep, formato);	
		} else {
			System.out.println("Cep esta no formato invalido ou incompleto!");
		}
		
		EmpresaRepository empresaRepository =  new EmpresaRepository();
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
		
		List<Empresa> listaEmpresa = empresaRepository.findAll();
	}
	
	private static void getCep(String cep, String formato) {
		
		if(formato.toLowerCase().equals("json")) {			
			getCepJson(cep);
		} else {
			getCepXML(cep);
		}
	}
	
	private static void getCepJson(String cep) {
		
		String URL_GET = "https://viacep.com.br/ws/" + cep + "/json/";
		
		WebServiceMethods viacep = new WebServiceMethods();
		HttpResponse<String> response = viacep.buscarCep(URL_GET);
		
		if(response.statusCode() == 200) {
			
			Gson gson = new Gson();
			
			/// :: Deserializar Object
			Cep objeto = gson.fromJson(response.body(), Cep.class);
			
			System.out.println(objeto.getLogradouro());		
			
			/// :: Serializar Object
			String stringJson = gson.toJson(objeto);
			
			System.out.println(stringJson);	
		} else {
			System.out.println("Error: falha em buscar cep! Status Code: " + response.statusCode());	
		}
	}
	
	private static void getCepXML(String cep) {
		/// :: https://www.mballem.com/post/xml-trabalhando-com-jaxb/#:~:text=O%20processo%20usado%20para%20transformar,o%20Marshaller%20ou%20um%20Unmarshaller.
		/// :: https://pt.stackoverflow.com/questions/227503/como-transformar-um-objeto-em-xml-no-formato-especificado
		
		String URL_GET = "https://viacep.com.br/ws/" + cep + "/xml/";
		
		WebServiceMethods viacep = new WebServiceMethods();
		HttpResponse<String> response = viacep.buscarCep(URL_GET);
		
		if(response.statusCode() == 200) {
			
			try {
				
				System.out.println(response.body());
				
				/*File file = new File("src\\main\\resources\\student.xml");			
				JAXBContext context = JAXBContext.newInstance(Xmlcep.class);
				Unmarshaller unmarshaller = context.createUnmarshaller();			
				Xmlcep xmlcep = (Xmlcep) unmarshaller.unmarshal(file);			
				System.out.println(xmlcep.getLogradouro());*/
				
				/// :: Deserializar XML
				JAXBContext context = JAXBContext.newInstance(Xmlcep.class);
				Unmarshaller unmarshaller = context.createUnmarshaller();
				
				Xmlcep xmlcep = (Xmlcep) unmarshaller.unmarshal(new StringReader(response.body()));
				
				System.out.println(xmlcep.getLogradouro());
				
				/// :: Serializar XML
				final StringWriter out = new StringWriter();
				Marshaller marshaller = context.createMarshaller();
				marshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
				marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
				marshaller.marshal(xmlcep, out);
				String xmlString = out.toString();
				
				System.out.println(xmlString);
				
				/// :: Salvar arquivo XML
				Writer writer = new FileWriter("C:\\Users\\Lincon\\Desktop\\xmlcep.xml");
				marshaller.marshal(xmlcep, writer);
				writer.close();
				
				/// :: Lendo o arquivo xml salvo
				File file = new File("C:\\Users\\Lincon\\Desktop\\xmlcep.xml");
				Xmlcep xmlcepRead = (Xmlcep) unmarshaller.unmarshal(file);
				System.out.println(xmlcepRead.getLogradouro());
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
			} 
			
		} else {
			System.out.println("Error: falha em buscar cep! Status Code: " + response.statusCode());	
		}
	}
	
	private static void testarHttpPost() {
		
		/// :: Api publica de teste -> https://jsonplaceholder.typicode.com/
		WebServiceMethods viacep = new WebServiceMethods();
		
		String URL_GET = "https://jsonplaceholder.typicode.com/todos/2";
		//String URL_GET = "https://jsonplaceholder.typicode.com/todos";
		//String URL_GET = "https://jsonplaceholder.typicode.com/comments?postId=1";
		HttpResponse<String> responseGet = viacep.httpGet(URL_GET);
		System.out.println(responseGet.body());
		System.out.println(responseGet.statusCode());
		
		HttpResponse<String> responseDelete = viacep.httpDelete(URL_GET);
		System.out.println(responseDelete.body());
		System.out.println(responseDelete.statusCode());

		Object object = "{\r\n"
				+ "    \"userId\": 10,\r\n"
				+ "    \"id\": 181,\r\n"
				+ "    \"title\": \"ut cupiditate sequi aliquam fuga maiores\",\r\n"
				+ "    \"completed\": false\r\n"
				+ "  }";
		

		URL_GET = "https://jsonplaceholder.typicode.com/todos";
		HttpResponse<String> responsePost = viacep.httpPost(URL_GET, object);
		System.out.println(responsePost.statusCode());
		System.out.println(responsePost.body());
		
		URL_GET = "https://jsonplaceholder.typicode.com/todos/181";
		object = "{\r\n"
				+ "    \"userId\": 10,\r\n"
				+ "    \"id\": 181,\r\n"
				+ "    \"title\": \"Teste de update ut cupiditate sequi aliquam fuga maiores\",\r\n"
				+ "    \"completed\": true\r\n"
				+ "  }";
		
		HttpResponse<String> responsePut = viacep.httpPut(URL_GET, object);
		System.out.println(responsePut.statusCode());
		System.out.println(responsePut.body());

	}
}

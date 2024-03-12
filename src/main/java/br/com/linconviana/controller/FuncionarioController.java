package br.com.linconviana.controller;

import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import com.google.gson.Gson;

import br.com.linconviana.entities.Empresa;
import br.com.linconviana.entities.Cep;
import br.com.linconviana.entities.Funcionario;
import br.com.linconviana.entities.TipoFuncaoFuncionario;
import br.com.linconviana.service.EmpresaService;
import br.com.linconviana.service.FuncionarioService;
import br.com.linconviana.utils.Message;

@Named(value = "funcionarioMB")
@ViewScoped
public class FuncionarioController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private FuncionarioService service;

	@Inject
	private EmpresaService empresaService;

	private List<Empresa> listaEmpresa;

	@Inject
	private Funcionario funcionario;

	private List<Funcionario> listaFuncionarios;

	private String termoPesquisa;

	private String tipoCadastro = "SAVE";

	@PostConstruct
	public void listarTodos() {
		funcionario = new Funcionario();
		listaFuncionarios = service.findAll();
		listaEmpresa = empresaService.todasEmpresasBasico();
		tipoCadastro = "SAVE";
	}

	public void buscarCep() throws IOException, InterruptedException {

		///https://www.youtube.com/watch?v=28lQZKbjuTU - tabela com checkbox
		try {

			String meuCep = funcionario.getCep();

			if (meuCep != null && (meuCep.contains("-") && meuCep.length() > 0 && meuCep.length() == 9)
					|| (meuCep.length() > 0 && meuCep.length() == 8)) {

				String URL_GET = "https://viacep.com.br/ws/" + meuCep + "/json/";

				// cliente HTTP
				HttpClient client = HttpClient.newHttpClient();

				// criar a requisição
				HttpRequest request = HttpRequest.newBuilder().GET().timeout(Duration.ofSeconds(10))
						.uri(URI.create(URL_GET)).build();

				// enviando uma solicitação
				HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

				if (response.statusCode() == 200) {
					///https://cursos.alura.com.br/forum/topico-como-fazer-uma-deserializacao-json-personalizada-usando-o-vraptor-57949
					///https://pt.stackoverflow.com/questions/203874/serializando-e-deserializando-atributos-com-nomes-diferentes-dos-campos-json
					//https://www.devmedia.com.br/trabalhando-com-json-em-java-o-pacote-org-json/25480
					/// :: Deserializar Objeto
					Gson gson = new Gson();
					Cep cep = gson.fromJson(response.body(), Cep.class);

					funcionario.setLogradouro(cep.getLogradouro());
					funcionario.setBairro(cep.getBairro());
					funcionario.setCidade(cep.getLocalidade());
					funcionario.setUf(cep.getUf());
				} else {
					Message.error("Erro ao tentar buscar cep no servidor, Verifique sua conexão com a internet!");
				}
			} else {
				Message.error("Cep esta no formato invalido ou incompleto!");
			}

		} catch (Exception e) {
			Message.fatal("Erro ao buscar Cep!");
		}
	}

	public void pesquisar() {

		listaFuncionarios = service.pesquisar(termoPesquisa);

		if (listaFuncionarios.isEmpty()) {
			Message.error("Não existe um funcionario com o termo: ".concat(termoPesquisa));
		}

		/// :: Atualizar tabela e mensagem
		PrimeFaces.current().ajax().update(Arrays.asList("form:dataTable", "form:messagesTabela"));
	}
	
	public void changeTitleButton(String type) {

		if (type.equals("SAVE")) {
			tipoCadastro = "SAVE";
		} else {
			tipoCadastro = "UPDATE";
		}
	}

	public void excluir() {
		service.delete(funcionario);

		funcionario = new Funcionario();
		listarTodos();

		Message.info("Funcionário excluida com sucesso!");

		PrimeFaces.current().ajax().update(Arrays.asList("form:dataTable", "form:messagesTabela"));
	}

	public void salvar() {

		Long id = funcionario.getId();

		service.save(funcionario);

		funcionario = new Funcionario();
		listarTodos();

		if (id == null) {
			Message.info("Funcionário cadastrada com sucesso!");
		} else {
			Message.info("Funcionário atualizada com sucesso!");
		}

		/// :: Atualizar tabela e mensagem
		PrimeFaces.current().ajax().update(Arrays.asList("form:dataTable", "form:messagesTabela"));
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public List<Funcionario> getListaFuncionarios() {
		return listaFuncionarios;
	}

	public void setListaFuncionarios(List<Funcionario> listaFuncionarios) {
		this.listaFuncionarios = listaFuncionarios;
	}

	public String getTermoPesquisa() {
		return termoPesquisa;
	}

	public void setTermoPesquisa(String termoPesquisa) {
		this.termoPesquisa = termoPesquisa;
	}

	public String getTipoCadastro() {
		return tipoCadastro;
	}

	public void setTipoCadastro(String tipoCadastro) {
		this.tipoCadastro = tipoCadastro;
	}

	public TipoFuncaoFuncionario[] getTipoFuncao() {
		return TipoFuncaoFuncionario.values();
	}

	public List<Empresa> getListaEmpresa() {
		return listaEmpresa;
	}

	public void setListaEmpresa(List<Empresa> listaEmpresa) {
		this.listaEmpresa = listaEmpresa;
	}
}

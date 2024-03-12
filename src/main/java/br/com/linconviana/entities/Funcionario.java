package br.com.linconviana.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.br.CPF;

@Entity
@Table(name = "tb_funcionario")
public class Funcionario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	@Column(nullable = false, length = 100)
	private String nome;

	@NotEmpty
	@Column(nullable = false, length = 120)
	private String email;

	@NotEmpty
	@Column(nullable = false, length = 128)
	private String senha;

	@CPF
	@NotNull
	@Column(nullable = false)
	private String cpf;

	@NotNull
	@Column(name = "data_nascimento")
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;

	@NotNull
	@Column(nullable = false, length = 15)
	private String celular;

	@NotNull
	@Column(nullable = false, length = 9)
	private String cep;

	@NotNull
	@Column(nullable = false)
	private String logradouro;

	@NotNull
	@Column(nullable = false)
	private String numero;

	@NotNull
	@Column(nullable = false)
	private String bairro;

	@NotNull
	@Column(nullable = false)
	private String cidade;

	@NotNull
	@Column(nullable = false)
	private String uf;

	@NotNull
	@Column(precision = 10, scale = 2)
	private BigDecimal salario;

	/// :: Muitas funcionario podem ter uma empresa
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "empresa_id", nullable = false)
	private Empresa empresa;
	
	@NotNull
	@Column(name = "tipo_funcao_funcionario")
	private TipoFuncaoFuncionario tipoFuncaoFuncionario;
	
	public Funcionario() {}

	public Funcionario(Long id, String nome, String email, String senha, String cpf, Date dataNascimento,
			String celular, String cep, String logradouro, String numero, String bairro, String cidade, String uf,
			BigDecimal salario, Empresa empresa, TipoFuncaoFuncionario tipoFuncaoFuncionario) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
		this.celular = celular;
		this.cep = cep;
		this.logradouro = logradouro;
		this.numero = numero;
		this.bairro = bairro;
		this.cidade = cidade;
		this.uf = uf;
		this.salario = salario;
		this.empresa = empresa;
		this.tipoFuncaoFuncionario = tipoFuncaoFuncionario;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public BigDecimal getSalario() {
		return salario;
	}

	public void setSalario(BigDecimal salario) {
		this.salario = salario;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	
	public TipoFuncaoFuncionario getTipoFuncaoFuncionario() {
		return tipoFuncaoFuncionario;
	}

	public void setTipoFuncaoFuncionario(TipoFuncaoFuncionario tipoFuncaoFuncionario) {
		this.tipoFuncaoFuncionario = tipoFuncaoFuncionario;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Funcionario other = (Funcionario) obj;
		return Objects.equals(id, other.id);
	}	
}

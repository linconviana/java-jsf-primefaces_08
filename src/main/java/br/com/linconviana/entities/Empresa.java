package br.com.linconviana.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Tuple;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.br.CNPJ;

@Entity
@Table(name = "tb_empresa")
public class Empresa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	@Column(name = "nome_fantasia", nullable = false, length = 100)
	private String nomeFantasia;
	
	@NotEmpty
	@Column(name = "razao_social", nullable = false, length = 120)
	private String razaoSocial;
	
	@CNPJ
	@NotNull
	@Column(nullable = false)
	private String cnpj;
	
	@NotNull
	@Past
	@Column(name = "data_fundacao")
	@Temporal(TemporalType.DATE)
	private Date dataFundacao;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_empresa", nullable = false, length = 80)
	private TipoEmpresa tipoEmpresa;
	
	/// :: Muitas empresa podem ter um ramo de atividade
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ramo_atividade_id", nullable = false)
	private RamoAtividade ramoAtividade;
	
	@NotNull
	@Column(precision = 10, scale = 2)
	private BigDecimal faturamento;
	
	public Empresa() {}

	public Empresa(Long id, String nomeFantasia, String razaoSocial, String cnpj, Date dataFundacao,
			TipoEmpresa tipoEmpresa, RamoAtividade ramoAtividade, BigDecimal faturamento) {
		this.id = id;
		this.nomeFantasia = nomeFantasia;
		this.razaoSocial = razaoSocial;
		this.cnpj = cnpj;
		this.dataFundacao = dataFundacao;
		this.tipoEmpresa = tipoEmpresa;
		this.ramoAtividade = ramoAtividade;
		this.faturamento = faturamento;
	}

	public Empresa(Tuple tuple) {
		this.id = tuple.get(0, Long.class);
		this.nomeFantasia = tuple.get(1, String.class);		
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public Date getDataFundacao() {
		return dataFundacao;
	}

	public void setDataFundacao(Date dataFundacao) {
		this.dataFundacao = dataFundacao;
	}

	public TipoEmpresa getTipoEmpresa() {
		return tipoEmpresa;
	}

	public void setTipoEmpresa(TipoEmpresa tipoEmpresa) {
		this.tipoEmpresa = tipoEmpresa;
	}

	public RamoAtividade getRamoAtividade() {
		return ramoAtividade;
	}

	public void setRamoAtividade(RamoAtividade ramoAtividade) {
		this.ramoAtividade = ramoAtividade;
	}

	public BigDecimal getFaturamento() {
		return faturamento;
	}

	public void setFaturamento(BigDecimal faturamento) {
		this.faturamento = faturamento;
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
		Empresa other = (Empresa) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Empresa [id=" + id + "]";
	}		
}

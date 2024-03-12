package br.com.linconviana.report;

import java.io.IOException;

import net.sf.jasperreports.engine.JRException;

public abstract class ICreateReport {
	
	public abstract void gerarRelatorioCompilado(ModelReport model) throws JRException, IOException;
	
}

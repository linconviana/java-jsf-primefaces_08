package br.com.linconviana.report;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class CreateReportPDF extends ICreateReport {

	///https://www.youtube.com/watch?v=oTigbEyBelg
	//https://www.youtube.com/watch?v=P2DnxWFtC2s&t=1232s
	//https://www.youtube.com/watch?v=1RaSrkIVA-U
	//https://www.youtube.com/watch?v=PPRrfnLvQ1Y
	//https://www.youtube.com/watch?v=CQdbCXlskWw
	//https://www.youtube.com/watch?v=S9p-ytIHDII
	//https://www.youtube.com/watch?v=6eGnYTk9J6c
	private static final String JASPER_DIRETORIO = "src/main/resources/reports/";

	private InputStream stream;
	private ByteArrayOutputStream byteArray;
	private HttpServletResponse response;
	private FacesContext context;
	
	 
	public CreateReportPDF() {
		this.context = FacesContext.getCurrentInstance();
		this.response = (HttpServletResponse) context.getExternalContext().getResponse();
	}
	
	@Override
	public void gerarRelatorioCompilado(ModelReport model) throws JRException, IOException {
			
		String relativePath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(
				JASPER_DIRETORIO.concat(model.getFolder()).concat(model.getNomeRelatorio().concat(".jasper"))
		);
		

		File file = new File(relativePath);
		
		JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(model.getLista(), false);
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(file.getPath(), model.getParametros(), source);
		
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		
		response.addHeader("Content-disposition","attachment; filename=" + model.getNomeRelatorio().concat(".pdf"));
		//response.addHeader("Content-disposition","inline; filename=" + model.getNomeRelatorio().concat(".pdf"));
		
		ServletOutputStream stream = response.getOutputStream();
		
		JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
		
		FacesContext.getCurrentInstance().responseComplete();	
	}
	
	public void gerarRelatorioCompiladoo2(ModelReport model) throws JRException, IOException {
		
		String pathReport = JASPER_DIRETORIO.concat(model.getFolder()).concat(model.getNomeRelatorio().concat(".jasper"));	

		InputStream stream = CreateReportPDF.class.getResourceAsStream(pathReport);
		
		//stream = this.getClass().getResourceAsStream(pathReport);
		
		JasperReport report = (JasperReport) JRLoader.loadObject(stream);
		
		byteArray = new ByteArrayOutputStream();
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(report, model.getParametros(), new JRBeanCollectionDataSource(model.getLista()));
		
		JasperExportManager.exportReportToPdfStream(jasperPrint, byteArray);
		
		response.reset();
		response.setContentType("application/pdf");
		response.setContentLength(byteArray.size());
		response.setHeader("Content-disposition","attachment; filename=" + model.getNomeRelatorio().concat(".pdf")); // download
		//response.setHeader("Content-disposition","inline; filename=" + model.getNomeRelatorio().concat(".pdf")); // browser
		response.getOutputStream().write(byteArray.toByteArray());
		response.getOutputStream().flush();
		response.getOutputStream().close();
		
		context.responseComplete();
	}
	
	public void gerarRelatorioEditado(ModelReport model) throws JRException, IOException {
		///https://www.youtube.com/watch?v=yJoXaGeDXdM - netbeans
		String pathReport = JASPER_DIRETORIO.concat(model.getFolder()).concat(model.getNomeRelatorio().concat(".jasper"));	

		InputStream stream = CreateReportPDF.class.getResourceAsStream(pathReport);
			
		JasperReport report = JasperCompileManager.compileReport(stream);		
		
		JasperPrint jasperPrint = JasperFillManager.fillReport(report, model.getParametros(), new JRBeanCollectionDataSource(model.getLista()));
		
		JasperViewer.viewReport(jasperPrint, false);
		
		
	}
	
	public void gerarRelatorioEditadoJxml(ModelReport model) throws JRException, IOException {
		///https://www.youtube.com/watch?v=dET2WkH8dBo
		//https://www.youtube.com/watch?v=hda_c6maMz4
		String pathReport = JASPER_DIRETORIO.concat(model.getFolder()).concat(model.getNomeRelatorio().concat(".jrxml"));	
			
		JasperReport report = JasperCompileManager.compileReport(pathReport);
				
		JasperPrint jasperPrint = JasperFillManager.fillReport(report, model.getParametros(), new JRBeanCollectionDataSource(model.getLista()));
		
		JasperViewer.viewReport(jasperPrint, false);	
		
	}
}

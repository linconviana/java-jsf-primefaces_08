//package br.com.linconviana.utils;
//
//import java.io.IOException;
//import java.io.OutputStreamWriter;
//import java.io.PrintWriter;
//import java.util.Collections;
//import java.util.List;
//
//import javax.faces.FacesException;
//import javax.faces.component.UIComponent;
//import javax.faces.context.ExternalContext;
//import javax.faces.context.FacesContext;
//
//
//import org.primefaces.component.api.DynamicColumn;
//import org.primefaces.component.api.UIColumn;
//import org.primefaces.component.datatable.DataTable;
//import org.primefaces.component.datatable.export.DataTableExporter;
//import org.primefaces.component.export.ExportConfiguration;
//import org.primefaces.component.export.ExporterOptions;
//import org.primefaces.util.ComponentUtils;
//import org.primefaces.util.Constants;
//import org.primefaces.util.EscapeUtils;
//
//public class TextExporter extends DataTableExporter {
//    @Override
//    public void export(FacesContext context, DataTable table, String filename, boolean pageOnly, boolean selectionOnly,
//                       String encodingType, MethodExpression preProcessor, MethodExpression postProcessor, ExporterOptions options,
//                       MethodExpression onTableRender) throws IOException {
//
//        ExternalContext externalContext = context.getExternalContext();
//        configureResponse(externalContext, filename);
//        StringBuilder builder = new StringBuilder();
//
//        if (preProcessor != null) {
//            preProcessor.invoke(context.getELContext(), new Object[]{builder});
//        }
//
//        builder.append("" + table.getId() + "\n");
//
//        if (pageOnly) {
//            exportPageOnly(context, table, builder);
//        }
//        else if (selectionOnly) {
//            exportSelectionOnly(context, table, builder);
//        }
//        else {
//            exportAll(context, table, builder);
//        }
//
//        builder.append("" + table.getId() + "");
//
//        table.setRowIndex(-1);
//
//        if (postProcessor != null) {
//            postProcessor.invoke(context.getELContext(), new Object[]{builder});
//        }
//
//        OutputStream os = externalContext.getResponseOutputStream();
//        OutputStreamWriter osw = new OutputStreamWriter(os, encodingType);
//        PrintWriter writer = new PrintWriter(osw);
//        writer.write(builder.toString());
//        writer.flush();
//        writer.close();
//    }
//
//    @Override
//    public void export(FacesContext facesContext, List<String> clientIds, String outputFileName, boolean pageOnly, boolean selectionOnly,
//                       String encodingType, MethodExpression preProcessor, MethodExpression postProcessor, ExporterOptions options,
//                       MethodExpression onTableRender) throws IOException {
//
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//    @Override
//    public void export(FacesContext facesContext, String outputFileName, List<DataTable> tables, boolean pageOnly, boolean selectionOnly,
//                       String encodingType, MethodExpression preProcessor, MethodExpression postProcessor, ExporterOptions options,
//                       MethodExpression onTableRender) throws IOException {
//
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//    @Override
//    protected void preRowExport(DataTable table, Object document) {
//        ((StringBuilder) document).append("\t" + table.getVar() + "\n");
//    }
//
//    @Override
//    protected void postRowExport(DataTable table, Object document) {
//        ((StringBuilder) document).append("\t" + table.getVar() + "\n");
//    }
//
//    @Override
//    protected void exportCells(DataTable table, Object document) {
//        StringBuilder builder = (StringBuilder) document;
//        for (UIColumn col : table.getColumns()) {
//            if (col instanceof DynamicColumn) {
//                ((DynamicColumn) col).applyStatelessModel();
//            }
//
//            if (col.isRendered() && col.isExportable()) {
//                String columnTag = getColumnTag(col);
//                try {
//                    addColumnValue(builder, col.getChildren(), columnTag, col);
//                }
//                catch (IOException ex) {
//                    throw new FacesException(ex);
//                }
//            }
//        }
//    }
//
//    protected String getColumnTag(UIColumn column) {
//        String headerText = (column.getExportHeaderValue() != null) ? column.getExportHeaderValue() : column.getHeaderText();
//        UIComponent facet = column.getFacet("header");
//        String columnTag;
//
//        if (headerText != null) {
//            columnTag = headerText.toLowerCase();
//        }
//        else if (facet != null) {
//            columnTag = exportValue(FacesContext.getCurrentInstance(), facet).toLowerCase();
//        }
//        else {
//            throw new FacesException("No suitable xml tag found for " + column);
//        }
//
//        return EscapeUtils.forXmlTag(columnTag);
//    }
//
//    protected void addColumnValue(StringBuilder builder, List<UIComponent> components, String tag, UIColumn column) throws IOException {
//        FacesContext context = FacesContext.getCurrentInstance();
//
//        builder.append("\t\t" + tag + "");
//
//        if (column.getExportFunction() != null) {
//            builder.append(EscapeUtils.forXml(exportColumnByFunction(context, column)));
//        }
//        else {
//            for (UIComponent component : components) {
//                if (component.isRendered()) {
//                    String value = exportValue(context, component);
//                    if (value != null) {
//                        builder.append(EscapeUtils.forXml(value));
//                    }
//                }
//            }
//        }
//
//        builder.append("" + tag + "\n");
//    }
//
//    protected void configureResponse(ExternalContext externalContext, String filename) {
//        externalContext.setResponseContentType("text/plain");
//        externalContext.setResponseHeader("Expires", "0");
//        externalContext.setResponseHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
//        externalContext.setResponseHeader("Pragma", "public");
//        externalContext.setResponseHeader("Content-disposition", ComponentUtils.createContentDisposition("attachment", filename + ".txt"));
//        externalContext.addResponseCookie(Constants.DOWNLOAD_COOKIE, "true", Collections.<String, Object>emptyMap());
//    }
//}

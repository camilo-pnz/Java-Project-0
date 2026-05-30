package uts.edu.java.proyecto.controlador;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uts.edu.java.proyecto.modelo.Venta;
import uts.edu.java.proyecto.servicio.IVentaServicio;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/reportes")
public class ReporteControlador {

    @Autowired
    private IVentaServicio ventaServicio;

    @GetMapping("/ventas/pdf")
    public void exportarPDF(HttpServletResponse response) throws IOException, DocumentException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=ventas.pdf");

        List<Venta> ventas = ventaServicio.listar();

        Document document = new Document(PageSize.A4.rotate());
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        com.itextpdf.text.Font fontTitulo = FontFactory.getFont(
            FontFactory.HELVETICA_BOLD, 16);
        Paragraph titulo = new Paragraph("Historial de Ventas", fontTitulo);
        titulo.setAlignment(Element.ALIGN_CENTER);
        titulo.setSpacingAfter(20);
        document.add(titulo);

        PdfPTable tabla = new PdfPTable(6);
        tabla.setWidthPercentage(100);

        com.itextpdf.text.Font fontHeader = FontFactory.getFont(
            FontFactory.HELVETICA_BOLD, 11, BaseColor.WHITE);
        String[] headers = {"ID", "Fecha", "Usuario", "Subtotal", "Total", "Estado"};
        for (String h : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(h, fontHeader));
            cell.setBackgroundColor(new BaseColor(23, 162, 184));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(8);
            tabla.addCell(cell);
        }

        for (Venta v : ventas) {
            tabla.addCell(String.valueOf(v.getIdVenta()));
            tabla.addCell(v.getFechaVenta() != null ? v.getFechaVenta().toString() : "");
            tabla.addCell(v.getUsuario().getUsername());
            tabla.addCell(v.getSubtotal().toString());
            tabla.addCell(v.getTotal().toString());
            tabla.addCell(v.getEstado().toString());
        }

        document.add(tabla);
        document.close();
    }

    @GetMapping("/ventas/excel")
    public void exportarExcel(HttpServletResponse response) throws IOException {
        response.setContentType(
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=ventas.xlsx");

        List<Venta> ventas = ventaServicio.listar();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Ventas");

        CellStyle headerStyle = workbook.createCellStyle();
        org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Row headerRow = sheet.createRow(0);
        String[] cols = {"ID", "Fecha", "Usuario", "Subtotal", "Total", "Estado"};
        for (int i = 0; i < cols.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(cols[i]);
            cell.setCellStyle(headerStyle);
        }

        int rowNum = 1;
        for (Venta v : ventas) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(v.getIdVenta());
            row.createCell(1).setCellValue(
                v.getFechaVenta() != null ? v.getFechaVenta().toString() : "");
            row.createCell(2).setCellValue(v.getUsuario().getUsername());
            row.createCell(3).setCellValue(v.getSubtotal().doubleValue());
            row.createCell(4).setCellValue(v.getTotal().doubleValue());
            row.createCell(5).setCellValue(v.getEstado().toString());
        }

        for (int i = 0; i < cols.length; i++) {
            sheet.autoSizeColumn(i);
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
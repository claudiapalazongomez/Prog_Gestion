package es.studium.proggestion;

import java.awt.Button;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;

public class ListadoArticulos implements WindowListener, ActionListener
{
	Frame menuArticuloListado = new Frame("Listado Artículos");

	TextArea txaListado = new TextArea(6, 25);
	Button btnPdf = new Button("PDF");

	Conexion conexion = new Conexion();

	ListadoArticulos()
	{
		menuArticuloListado.setSize(300, 200);
		menuArticuloListado.setLayout(new FlowLayout());
		menuArticuloListado.addWindowListener(this);

		// Rellenar el TextArea
		conexion.rellenarListadoArticulo(txaListado);

		menuArticuloListado.add(txaListado);
		menuArticuloListado.add(btnPdf);

		btnPdf.addActionListener(this);
		menuArticuloListado.setLocationRelativeTo(null);
		menuArticuloListado.setResizable(false);
		menuArticuloListado.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{	
		if (e.getSource().equals(btnPdf))
		{

		String dest = "Listado_Artículos.pdf";

		try
		{
			// Inicializar PDF writer
			PdfWriter writer = new PdfWriter(dest);

			// Inicializar PDF document
			PdfDocument pdf = new PdfDocument(writer);

			// Inicializar documento
			Document document = new Document(pdf, PageSize.A4.rotate());
			document.setMargins(20, 20, 20, 20);

			// Crear fuente para el encabezado
			PdfFont fontHeader = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

			// Crear fuente para los datos
			PdfFont fontData = PdfFontFactory.createFont(StandardFonts.HELVETICA);

			// Crear tabla y configurar anchos de columna
			Table table = new Table(UnitValue.createPercentArray(new float[]
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1 }));
			table.setWidth(UnitValue.createPercentValue(100));

			// Agregar encabezados a la tabla
			table.addHeaderCell(new Cell().add(new Paragraph("ID").setFont(fontHeader).setBold()));
			table.addHeaderCell(new Cell().add(new Paragraph("Color").setFont(fontHeader).setBold()));
			table.addHeaderCell(new Cell().add(new Paragraph("Stock").setFont(fontHeader).setBold()));
			table.addHeaderCell(new Cell().add(new Paragraph("Referencia").setFont(fontHeader).setBold()));
			table.addHeaderCell(new Cell().add(new Paragraph("Descuento").setFont(fontHeader).setBold()));
			table.addHeaderCell(new Cell().add(new Paragraph("Precio").setFont(fontHeader).setBold()));
			table.addHeaderCell(new Cell().add(new Paragraph("Fecha de compra").setFont(fontHeader).setBold()));
			table.addHeaderCell(new Cell().add(new Paragraph("Track code").setFont(fontHeader).setBold()));
			table.addHeaderCell(new Cell().add(new Paragraph("ID Proveedor").setFont(fontHeader).setBold()));
			

			// Agregar datos a la tabla desde el TextArea
			String[] lines = txaListado.getText().split("\n");
			for (String line : lines)
			{
				String[] data = line.split(" - ");
				for (String item : data)
				{
					table.addCell(new Cell().add(new Paragraph(item).setFont(fontData)));
				}
			}

			// Agregar la tabla al documento
			document.add(table);

			// Cerrar documento
			document.close();

			// Abrir el PDF generado
			Desktop.getDesktop().open(new File(dest));
		} catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
		}
	}
	

	@Override
	public void windowOpened(WindowEvent e)
	{}
	@Override
	public void windowClosing(WindowEvent e)
	{
		menuArticuloListado.setVisible(false);
		
	}
	@Override
	public void windowClosed(WindowEvent e)
	{}
	@Override
	public void windowIconified(WindowEvent e)
	{}
	@Override
	public void windowDeiconified(WindowEvent e)
	{}
	@Override
	public void windowActivated(WindowEvent e)
	{}
	@Override
	public void windowDeactivated(WindowEvent e)
	{}

}

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

public class ListadoCompra implements WindowListener, ActionListener
{

	Frame menuListadoCompra = new Frame("Listado Compras");

	TextArea txaListado = new TextArea(6, 25);
	Button btnPdf = new Button("PDF");

	Conexion conexion = new Conexion();

	ListadoCompra()
	{
		menuListadoCompra.setSize(300, 200);
		menuListadoCompra.setLayout(new FlowLayout());
		menuListadoCompra.addWindowListener(this);

		// Rellenar el TextArea
		conexion.rellenarListadoCompra(txaListado);

		menuListadoCompra.add(txaListado);
		menuListadoCompra.add(btnPdf);

		btnPdf.addActionListener(this);
		menuListadoCompra.setLocationRelativeTo(null);
		menuListadoCompra.setResizable(false);
		menuListadoCompra.setVisible(true);
		txaListado.setEditable(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource().equals(btnPdf))
		{

		String dest = "Listado_Compras.pdf";

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
			{ 1, 1, 1, 1, 1, 1, 1 }));
			table.setWidth(UnitValue.createPercentValue(100));

			// Agregar encabezados a la tabla
			table.addHeaderCell(new Cell().add(new Paragraph("ID").setFont(fontHeader).setBold()));
			table.addHeaderCell(new Cell().add(new Paragraph("Fecha").setFont(fontHeader).setBold()));
			table.addHeaderCell(new Cell().add(new Paragraph("Cantidad").setFont(fontHeader).setBold()));
			table.addHeaderCell(new Cell().add(new Paragraph("ID Cliente").setFont(fontHeader).setBold()));
			table.addHeaderCell(new Cell().add(new Paragraph("DNI Cliente").setFont(fontHeader).setBold()));
			table.addHeaderCell(new Cell().add(new Paragraph("ID Artículo").setFont(fontHeader).setBold()));
			table.addHeaderCell(new Cell().add(new Paragraph("Referencia Artículo").setFont(fontHeader).setBold()));
			
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
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e)
	{
		menuListadoCompra.setVisible(false);
		
	}

	@Override
	public void windowClosed(WindowEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e)
	{
		// TODO Auto-generated method stub
		
	}

}

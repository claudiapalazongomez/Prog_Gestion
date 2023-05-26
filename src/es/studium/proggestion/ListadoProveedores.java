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

public class ListadoProveedores implements WindowListener, ActionListener
{
	Frame menuProveedoresListado = new Frame("Listado Proveedores");

	TextArea txaListado = new TextArea(6, 25);
	Button btnPdf = new Button("PDF");

	Conexion conexion = new Conexion();

	ListadoProveedores()
	{
		menuProveedoresListado.setSize(300, 200);
		menuProveedoresListado.setLayout(new FlowLayout());
		menuProveedoresListado.addWindowListener(this);

		// Rellenar el TextArea
		conexion.rellenarListadoProveedores(txaListado);

		menuProveedoresListado.add(txaListado);
		menuProveedoresListado.add(btnPdf);

		btnPdf.addActionListener(this);
		menuProveedoresListado.setLocationRelativeTo(null);
		menuProveedoresListado.setResizable(false);
		menuProveedoresListado.setVisible(true);
		txaListado.setEditable(false);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource().equals(btnPdf))
		{

			String dest = "Listado_Proveedores.pdf";

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
				float[] columnWidths =
				{ 1, 2, 2, 1 }; // Ajusta los valores según tus necesidades
				Table table = new Table(columnWidths);
				table.setWidth(UnitValue.createPercentValue(100));

				// Agregar encabezados a la tabla
				table.addHeaderCell(new Cell().add(new Paragraph("ID").setFont(fontHeader).setBold()));
				table.addHeaderCell(new Cell().add(new Paragraph("Nombre").setFont(fontHeader).setBold()));
				table.addHeaderCell(new Cell().add(new Paragraph("Dirección").setFont(fontHeader).setBold()));
				table.addHeaderCell(new Cell().add(new Paragraph("Teléfono").setFont(fontHeader).setBold()));

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
	}

	public void windowClosing(WindowEvent e)
	{
		menuProveedoresListado.setVisible(false);
	}

	public void windowClosed(WindowEvent e)
	{
	}

	public void windowIconified(WindowEvent e)
	{
	}

	public void windowDeiconified(WindowEvent e)
	{
	}

	public void windowActivated(WindowEvent e)
	{
	}

	public void windowDeactivated(WindowEvent e)
	{
	}
}

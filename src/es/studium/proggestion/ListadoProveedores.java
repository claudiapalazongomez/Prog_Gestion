package es.studium.proggestion;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class ListadoProveedores implements WindowListener, ActionListener
{
Frame menuProveedoresListado = new Frame("Listado Proveedores");
	
	TextArea txaListado = new TextArea(6,25);
	Button btnPdf = new Button("PDF");
	
	Conexion conexion = new Conexion();
	
	ListadoProveedores(){
		menuProveedoresListado.setSize(300,200);
		menuProveedoresListado.setLayout(new FlowLayout());
		menuProveedoresListado.addWindowListener(this);
		
		//Rellenar el TextArea
		conexion.rellenarListadoProveedores(txaListado);
		
		menuProveedoresListado.add(txaListado);
		menuProveedoresListado.add(btnPdf);
		
		
		btnPdf.addActionListener(this);
		menuProveedoresListado.setLocationRelativeTo(null);
		menuProveedoresListado.setResizable(false);
		menuProveedoresListado.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{	
	}

	@Override
	public void windowOpened(WindowEvent e)
	{}
	public void windowClosing(WindowEvent e)
	{
		menuProveedoresListado.setVisible(false);	
	}
	public void windowClosed(WindowEvent e)
	{}
	public void windowIconified(WindowEvent e)
	{}
	public void windowDeiconified(WindowEvent e)
	{}
	public void windowActivated(WindowEvent e)
	{}
	public void windowDeactivated(WindowEvent e)
	{}
}

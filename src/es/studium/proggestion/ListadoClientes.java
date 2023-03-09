package es.studium.proggestion;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class ListadoClientes implements WindowListener, ActionListener
{
Frame menuClientesListado = new Frame("Listado Clientes");
	
	TextArea txaListado = new TextArea(6,25);
	Button btnPdf = new Button("PDF");
	
	Conexion conexion = new Conexion();
	
	ListadoClientes(){
		menuClientesListado.setSize(300,200);
		menuClientesListado.setLayout(new FlowLayout());
		menuClientesListado.addWindowListener(this);
		
		//Rellenar el TextArea
		conexion.rellenarListadoClientes(txaListado);
		
		menuClientesListado.add(txaListado);
		menuClientesListado.add(btnPdf);
		
		
		btnPdf.addActionListener(this);
		menuClientesListado.setLocationRelativeTo(null);
		menuClientesListado.setResizable(false);
		menuClientesListado.setVisible(true);
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
		menuClientesListado.setVisible(false);	
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

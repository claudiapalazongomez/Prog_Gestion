package es.studium.proggestion;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class ListadoUsuarios implements WindowListener, ActionListener
{
Frame menuListadoUsuarios = new Frame("Listado Usuarios");
	
	TextArea txaListado = new TextArea(6,25);
	Button btnPdf = new Button("PDF");
	
	Conexion conexion = new Conexion();
	
	ListadoUsuarios(){
		menuListadoUsuarios.setSize(300,200);
		menuListadoUsuarios.setLayout(new FlowLayout());
		menuListadoUsuarios.addWindowListener(this);
		
		//Rellenar el TextArea
		conexion.rellenarListadoUsuarios(txaListado);
		
		menuListadoUsuarios.add(txaListado);
		menuListadoUsuarios.add(btnPdf);
		
		
		btnPdf.addActionListener(this);
		menuListadoUsuarios.setLocationRelativeTo(null);
		menuListadoUsuarios.setResizable(false);
		menuListadoUsuarios.setVisible(true);
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
		menuListadoUsuarios.setVisible(false);	
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

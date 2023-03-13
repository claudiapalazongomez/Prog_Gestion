package es.studium.proggestion;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class BajaCliente implements WindowListener, ActionListener
{
Frame menuBajaCliente = new Frame("Baja Cliente");
	
	Label lblElegir = new Label("Elegir el cliente a Eliminar:");
	Choice choClientes = new Choice();
	Button btnEliminar = new Button("Eliminar");
	Dialog dlgSeguro = new Dialog(menuBajaCliente, "¿Segur@?", true);
	Label lblConfirmar = new Label("¿Seguro de eliminar ");
	Button btnSi = new Button("Si");
	Button btnNo = new Button("No");
	Dialog dlgMensaje = new Dialog(menuBajaCliente, "Mensaje", true);
	Label lblMensaje = new Label("Cliente Eliminado");
	
	Conexion conexion = new Conexion();
	
	BajaCliente(){
		menuBajaCliente.setSize(220,220);
		menuBajaCliente.setLayout(new FlowLayout());
		menuBajaCliente.addWindowListener(this);
		
		menuBajaCliente.add(lblElegir);
		//Rellenar el Choice
		conexion.rellenarChoiceClientes(choClientes);
		menuBajaCliente.add(choClientes);
		btnEliminar.addActionListener(this);
		menuBajaCliente.add(btnEliminar);
		
		menuBajaCliente.setLocationRelativeTo(null);
		menuBajaCliente.setResizable(false);
		menuBajaCliente.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource().equals(btnEliminar)) {
			if(choClientes.getSelectedIndex()!=0){ //Si es Elegir cliente... no hace nada al ser el 0
				dlgSeguro.setLayout(new FlowLayout());
				dlgSeguro.setSize(265, 200);
				dlgSeguro.addWindowListener(this);
				
				lblConfirmar.setText("¿Seguro de eliminar "+choClientes.getSelectedItem()+"?");
				dlgSeguro.add(lblConfirmar);
				btnSi.addActionListener(this);
				dlgSeguro.add(btnSi);
				btnNo.addActionListener(this);
				dlgSeguro.add(btnNo);
				dlgSeguro.setResizable(false);
				dlgSeguro.setLocationRelativeTo(null);
				dlgSeguro.setVisible(true);
			}
		}
		else if(e.getSource().equals(btnNo)) {
			dlgSeguro.setVisible(false);
		}
		else if(e.getSource().equals(btnSi)) {
			String tabla[] = choClientes.getSelectedItem().split("-");
			int respuesta = conexion.eliminarCliente(tabla[0]);
			dlgMensaje.setLayout(new FlowLayout());
			dlgMensaje.setSize(200, 200);
			dlgMensaje.addWindowListener(this);
			if(respuesta==0) {
				lblMensaje.setText("Cliente Eliminado");
			}
			else {
				lblMensaje.setText("Error al Eliminar");
			}
			dlgMensaje.add(lblMensaje);
			dlgMensaje.setResizable(false);
			dlgMensaje.setLocationRelativeTo(null);
			dlgMensaje.setVisible(true);
		}
	}
	
	@Override
	public void windowOpened(WindowEvent e)
	{}
	public void windowClosing(WindowEvent e)
	{
		if(dlgSeguro.isActive()) {
			dlgSeguro.setVisible(false);
		}
		else {
			menuBajaCliente.setVisible(false);
		}	
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

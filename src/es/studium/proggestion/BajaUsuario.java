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

public class BajaUsuario implements WindowListener, ActionListener
{
Frame menuBajaUsuario = new Frame("Baja Usuario");
	
	Label lblElegir = new Label("Elegir el usuario a Eliminar:");
	Choice choUsuarios = new Choice();
	Button btnEliminar = new Button("Eliminar");
	Dialog dlgSeguro = new Dialog(menuBajaUsuario, "¿Segur@?", true);
	Label lblConfirmar = new Label("¿Seguro de eliminar ");
	Button btnSi = new Button("Si");
	Button btnNo = new Button("No");
	Dialog dlgMensaje = new Dialog(menuBajaUsuario, "Mensaje", true);
	Label lblMensaje = new Label("Usuario Eliminado");
	
	Conexion conexion = new Conexion();
	
	BajaUsuario(){
		menuBajaUsuario.setSize(220,220);
		menuBajaUsuario.setLayout(new FlowLayout());
		menuBajaUsuario.addWindowListener(this);
		
		menuBajaUsuario.add(lblElegir);
		//Rellenar el Choice
		conexion.rellenarChoiceUsuarios(choUsuarios);
		menuBajaUsuario.add(choUsuarios);
		btnEliminar.addActionListener(this);
		menuBajaUsuario.add(btnEliminar);
		
		menuBajaUsuario.setLocationRelativeTo(null);
		menuBajaUsuario.setResizable(false);
		menuBajaUsuario.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource().equals(btnEliminar)) {
			if(choUsuarios.getSelectedIndex()!=0){ //Si es Elegir usuario... no hace nada al ser el 0
				dlgSeguro.setLayout(new FlowLayout());
				dlgSeguro.setSize(200, 200);
				dlgSeguro.addWindowListener(this);
				
				lblConfirmar.setText("¿Seguro de eliminar "+choUsuarios.getSelectedItem()+"?");
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
			String tabla[] = choUsuarios.getSelectedItem().split("-");
			int respuesta = conexion.eliminarUsuario(tabla[0]);
			dlgMensaje.setLayout(new FlowLayout());
			dlgMensaje.setSize(200, 200);
			dlgMensaje.addWindowListener(this);
			if(respuesta==0) {
				lblMensaje.setText("Usuario Eliminado");
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
			menuBajaUsuario.setVisible(false);
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

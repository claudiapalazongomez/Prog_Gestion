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

public class BajaProveedor implements WindowListener, ActionListener
{
Frame menuBajaProveedor = new Frame("Baja Proveedor");
	
	Label lblElegir = new Label("Elegir el proveedor a Eliminar:");
	Choice choProveedores = new Choice();
	Button btnEliminar = new Button("Eliminar");
	Dialog dlgSeguro = new Dialog(menuBajaProveedor, "¿Segur@?", true);
	Label lblConfirmar = new Label("¿Seguro de eliminar ");
	Button btnSi = new Button("Si");
	Button btnNo = new Button("No");
	Dialog dlgMensaje = new Dialog(menuBajaProveedor, "Mensaje", true);
	Label lblMensaje = new Label("Proveedor Eliminado");
	
	Conexion conexion = new Conexion();
	
	BajaProveedor(){
		menuBajaProveedor.setSize(220,220);
		menuBajaProveedor.setLayout(new FlowLayout());
		menuBajaProveedor.addWindowListener(this);
		
		menuBajaProveedor.add(lblElegir);
		//Rellenar el Choice
		conexion.rellenarChoiceProveedores(choProveedores);
		menuBajaProveedor.add(choProveedores);
		btnEliminar.addActionListener(this);
		menuBajaProveedor.add(btnEliminar);
		
		menuBajaProveedor.setLocationRelativeTo(null);
		menuBajaProveedor.setResizable(false);
		menuBajaProveedor.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource().equals(btnEliminar)) {
			if(choProveedores.getSelectedIndex()!=0){ //Si es Elegir cliente... no hace nada al ser el 0
				dlgSeguro.setLayout(new FlowLayout());
				dlgSeguro.setSize(265, 200);
				dlgSeguro.addWindowListener(this);
				
				lblConfirmar.setText("¿Seguro de eliminar "+choProveedores.getSelectedItem()+"?");
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
			String tabla[] = choProveedores.getSelectedItem().split("-");
			int respuesta = conexion.eliminarProveedor(tabla[0]);
			dlgMensaje.setLayout(new FlowLayout());
			dlgMensaje.setSize(200, 200);
			dlgMensaje.addWindowListener(this);
			if(respuesta==0) {
				lblMensaje.setText("Proveedor Eliminado");
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
			menuBajaProveedor.setVisible(false);
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

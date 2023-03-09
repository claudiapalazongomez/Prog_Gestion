package es.studium.proggestion;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class NuevoCliente implements WindowListener, ActionListener
{
	Frame menuNuevoCliente = new Frame("Nuevo Cliente");

	Label lblAlta = new Label("Alta de Cliente");
	Label lblNombre = new Label("Nombre:");
	TextField txtNombre = new TextField(10);
	Label lblApellido1 = new Label("Primer Apellido:");
	TextField txtApellido1 = new TextField(10);
	Label lblApellido2 = new Label("Segundo Apellido:");
	TextField txtApellido2 = new TextField(10);
	Label lblDni = new Label("DNI:");
	TextField txtDni = new TextField(10);
	Label lblCorreo = new Label("Correo:");
	TextField txtCorreo = new TextField(10);
	Button btnAceptar = new Button("Aceptar");
	Button btnCancelar = new Button("Cancelar");

	Label lblMensaje = new Label("ALTA correcta");
	Dialog dlgMensaje = new Dialog(menuNuevoCliente, "Aviso", true);

	Conexion conexion = new Conexion();
	
	String verificarCamposUnicos = "";
	
	NuevoCliente()
	{
		menuNuevoCliente.setLayout(new FlowLayout());
		menuNuevoCliente.addWindowListener(this);

		menuNuevoCliente.add(lblAlta);

		menuNuevoCliente.add(lblNombre);
		menuNuevoCliente.add(txtNombre);
		menuNuevoCliente.add(lblApellido1);
		menuNuevoCliente.add(txtApellido1);
		menuNuevoCliente.add(lblApellido2);
		menuNuevoCliente.add(txtApellido2);
		menuNuevoCliente.add(lblDni);
		menuNuevoCliente.add(txtDni);
		menuNuevoCliente.add(lblCorreo);
		menuNuevoCliente.add(txtCorreo);
		
		btnAceptar.addActionListener(this);
		btnCancelar.addActionListener(this);
		menuNuevoCliente.add(btnAceptar);
		menuNuevoCliente.add(btnCancelar);

		menuNuevoCliente.setLocationRelativeTo(null);
		menuNuevoCliente.setResizable(false);
		menuNuevoCliente.setSize(100, 400);
		menuNuevoCliente.setVisible(true);
	}
	
	@Override
	public void windowOpened(WindowEvent e)
	{
	}

	public void windowClosing(WindowEvent e)
	{
		if(dlgMensaje.isActive()) {
			dlgMensaje.setVisible(false);
		}
		else {
		menuNuevoCliente.setVisible(false);
		}
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
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		// Nuevo Usuario
		if (e.getSource().equals(btnAceptar))
		{
			dlgMensaje.setLayout(new FlowLayout());
			dlgMensaje.setSize(300, 300);
			dlgMensaje.addWindowListener(this);
			
			// Si los campos están vacíos, evitar que se cree un usuario 
			if(txtNombre.getText().length()==0 || txtApellido1.getText().length()==0 || txtApellido2.getText().length()==0 || txtDni.getText().length()==0 || txtCorreo.getText().length()==0) { //nombre vacío
				lblMensaje.setText("Los campos están vacíos");
			}
			
			 
			else if(conexion.verificarCamposUnicos(txtDni.getText(), txtCorreo.getText())== true){ //enviamos el dni y el correo al método
				lblMensaje.setText("El DNI o el correo están duplicados");
			}
			
			
			else {
			// Dar de alta
			String sentencia = "INSERT INTO clientes VALUES (null, '" + txtNombre.getText() + "', "
					+ "'" + txtApellido1.getText() + "', '" + txtApellido2.getText() + "', '" 
					+ txtDni.getText() + "', '" + txtCorreo.getText() + "');";
			int respuesta = conexion.altaCliente(sentencia);
			
			if (respuesta != 0)
			{
				// Mostrar mensaje OK
				lblMensaje.setText("Error en Alta");
			}
			}
			dlgMensaje.add(lblMensaje);
			dlgMensaje.setResizable(false);
			dlgMensaje.setLocationRelativeTo(null);
			dlgMensaje.setVisible(true);
		}

		else if (e.getSource().equals(btnCancelar))
		{ // Se borra todo lo que has introducido y se pone el cursor en txtNombre
			txtNombre.setText("");
			txtApellido1.setText("");
			txtApellido2.setText("");
			txtDni.setText("");
			txtCorreo.setText("");
			txtNombre.requestFocus();
		}
	}
}

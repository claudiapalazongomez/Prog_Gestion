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

public class NuevoProveedor implements WindowListener, ActionListener
{
	Frame menuNuevoProveedor = new Frame("Nuevo Proveedor");

	Label lblAlta = new Label("Alta de Proveedor");
	Label lblNombre = new Label("Nombre:");
	TextField txtNombre = new TextField(10);
	Label lblDireccion = new Label("Dirección:");
	TextField txtDireccion = new TextField(10);
	Label lblTelefono = new Label("Teléfono:");
	TextField txtTelefono = new TextField(10);
	Button btnAceptar = new Button("Aceptar");
	Button btnLimpiar = new Button("Limpiar");

	Label lblMensaje = new Label("ALTA correcta");
	Dialog dlgMensaje = new Dialog(menuNuevoProveedor, "Aviso", true);

	Conexion conexion = new Conexion();
	
	String verificarCamposUnicos1 = "";
	
	NuevoProveedor()
	{
		menuNuevoProveedor.setLayout(new FlowLayout());
		menuNuevoProveedor.addWindowListener(this);

		menuNuevoProveedor.add(lblAlta);

		menuNuevoProveedor.add(lblNombre);
		menuNuevoProveedor.add(txtNombre);
		menuNuevoProveedor.add(lblDireccion);
		menuNuevoProveedor.add(txtDireccion);
		menuNuevoProveedor.add(lblTelefono);
		menuNuevoProveedor.add(txtTelefono);
		
		btnAceptar.addActionListener(this);
		btnLimpiar.addActionListener(this);
		menuNuevoProveedor.add(btnAceptar);
		menuNuevoProveedor.add(btnLimpiar);

		menuNuevoProveedor.setLocationRelativeTo(null);
		menuNuevoProveedor.setResizable(false);
		menuNuevoProveedor.setSize(100, 300);
		menuNuevoProveedor.setVisible(true);
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
		menuNuevoProveedor.setVisible(false);
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
			if(txtNombre.getText().length()==0 || txtDireccion.getText().length()==0 || txtTelefono.getText().length()==0) { //nombre vacío
				lblMensaje.setText("Los campos están vacíos");
			}
			
			 
			else if(conexion.verificarCamposUnicos1(txtTelefono.getText())== true){ //enviamos el dni y el correo al método
				lblMensaje.setText("El DNI o el correo están duplicados");
			}
			
			
			else {
			// Dar de alta
			String sentencia = "INSERT INTO proveedores VALUES (null, '" + txtNombre.getText() + "', "
					+ "'" + txtDireccion.getText() + "', '" + txtTelefono.getText() + "');";
			int respuesta = conexion.altaProveedor(sentencia);
			
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

		else if (e.getSource().equals(btnLimpiar))
		{ // Se borra todo lo que has introducido y se pone el cursor en txtNombre
			txtNombre.setText("");
			txtDireccion.setText("");
			txtTelefono.setText("");
			txtNombre.requestFocus();
		}
	}
}

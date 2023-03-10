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


public class NuevoUsuario implements WindowListener, ActionListener
{
	Frame menuNuevoUsuario = new Frame("Nuevo Usuario");

	Label lblAlta = new Label("Alta de Usuario");
	Label lblNombre = new Label("Nombre:");
	TextField txtNombre = new TextField(10);
	Label lblClave = new Label("Clave:");
	TextField txtClave = new TextField(10);
	Label lblClaveRepetir = new Label("Repetir clave:");
	TextField txtClaveRepetir = new TextField(10);
	Label lblCorreo = new Label("Correo:");
	TextField txtCorreo = new TextField(10);
	Button btnAceptar = new Button("Aceptar");
	Button btnLimpiar = new Button("Limpiar");

	Label lblMensaje = new Label("ALTA correcta");
	Dialog dlgMensaje = new Dialog(menuNuevoUsuario, "Aviso", true);

	Conexion conexion = new Conexion();
	
	int tipoUsuario = 1;

	NuevoUsuario()
	{
		menuNuevoUsuario.setLayout(new FlowLayout());
		menuNuevoUsuario.addWindowListener(this);

		menuNuevoUsuario.add(lblAlta);

		menuNuevoUsuario.add(lblNombre);
		menuNuevoUsuario.add(txtNombre);
		menuNuevoUsuario.add(lblClave);
		menuNuevoUsuario.add(txtClave);
		txtClave.setEchoChar('*');
		menuNuevoUsuario.add(lblClaveRepetir);
		menuNuevoUsuario.add(txtClaveRepetir);
		txtClaveRepetir.setEchoChar('*');
		menuNuevoUsuario.add(lblCorreo);
		menuNuevoUsuario.add(txtCorreo);
		
		btnAceptar.addActionListener(this);
		btnLimpiar.addActionListener(this);
		menuNuevoUsuario.add(btnAceptar);
		menuNuevoUsuario.add(btnLimpiar);

		menuNuevoUsuario.setLocationRelativeTo(null);
		menuNuevoUsuario.setResizable(false);
		menuNuevoUsuario.setSize(100, 350);
		menuNuevoUsuario.setVisible(true);
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
		menuNuevoUsuario.setVisible(false);
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
			dlgMensaje.setSize(200, 200);
			dlgMensaje.addWindowListener(this);
			
			// Si los campos están vacíos, evitar que se cree un usuario 
			if(txtNombre.getText().length()==0 || txtClave.getText().length()==0 || txtClaveRepetir.getText().length()==0 || txtCorreo.getText().length()==0) { //nombre vacío
				lblMensaje.setText("Los campos están vacíos");
			}
			
			// Comprobar claves
			else if(!txtClave.getText().equals(txtClaveRepetir.getText())) {
				lblMensaje.setText("Las claves no coinciden");
			}
			
			else if(conexion.verificarCamposUnicos0(txtNombre.getText())== true){ //enviamos el dni y el correo al método
				lblMensaje.setText("Este nombre de usuario ya existe");
			}
			
			else {
			// Dar de alta
			String sentencia = "INSERT INTO usuarios VALUES (null, '" + txtNombre.getText() + "', SHA2('"
					+ txtClave.getText() + "',256), '" + txtCorreo.getText() + "', " + tipoUsuario + ");";
			int respuesta = conexion.altaUsuario(sentencia);
			
			if (respuesta != 0)
			{
				// Mostrar mensaje OK
				lblMensaje.setText("Error en Alta");
			}
			else {
				lblMensaje.setText("Alta Correcta");
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
			txtClave.setText("");
			txtClaveRepetir.setText("");
			txtCorreo.setText("");
			txtNombre.requestFocus();
		}
	}
}

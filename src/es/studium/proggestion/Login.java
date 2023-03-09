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


public class Login implements WindowListener, ActionListener
{
	Frame ventanaLogin = new Frame("Login");
	Dialog dlgMensaje = new Dialog(ventanaLogin, "Error", true); //true= modal, es decir, no podemos interactuar con NADA hasta que no cerremos ese diálogo
	
	Label lblUsuario = new Label("Usuario:");
	Label lblClave = new Label("Clave:   ");
	Label lblMensaje = new Label("Credenciales incorrectas");
	
	TextField txtUsuario = new TextField(10);
	TextField txtClave = new TextField(10);
	
	Button btnAcceder = new Button("Acceder");
	
	Conexion conexion = new Conexion();
	
	int tipoUsuario;
	
	Login(){ //el constructor es un método que se llama igual que la clase
		ventanaLogin.setLayout(new FlowLayout());
		ventanaLogin.addWindowListener(this);
		
		ventanaLogin.add(lblUsuario);
		ventanaLogin.add(txtUsuario);
		ventanaLogin.add(lblClave);
		ventanaLogin.add(txtClave);
		txtClave.setEchoChar('*'); //para ocultar la clave
		btnAcceder.addActionListener(this);
		ventanaLogin.add(btnAcceder);
		
		
		ventanaLogin.setSize(220,130);
		ventanaLogin.setResizable(false);
		ventanaLogin.setLocationRelativeTo(null);
		ventanaLogin.setVisible(true);
	}
	
	@Override
	public void windowOpened(WindowEvent e)
	{}
	public void windowClosing(WindowEvent e)
	{
		if(dlgMensaje.isActive()) { 
			dlgMensaje.setVisible(false); 
			//se utiliza para asegurarse de que el diálogo no se cierre cuando se cierra la ventana principal
		}
		else {
			System.exit(0);
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
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource().equals(btnAcceder)) { 
			//Devuelve el usuario y la clave que el usuario ha metido
			String usuario = txtUsuario.getText(); 
			String clave = txtClave.getText();
			
			tipoUsuario = conexion.comprobarCredenciales(usuario, clave);
			
			//Credenciales correctas 
			if(tipoUsuario!=-1) {  
				new MenuPrincipal(tipoUsuario);
				ventanaLogin.setVisible(false);
			}
			//Credenciales incorrectas
			else {
				dlgMensaje.setLayout(new FlowLayout());
				dlgMensaje.addWindowListener(this);
				dlgMensaje.add(lblMensaje);
				dlgMensaje.setSize(230,80);
				dlgMensaje.setResizable(false);
				dlgMensaje.setLocationRelativeTo(null);
				dlgMensaje.setVisible(true);
			}
		}
	}
	
	public static void main(String[] args)
	{
		new Login(); //en el main solo creamos un objeto de la clase Login

	}
}

package es.studium.proggestion;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class ModificarUsuario implements WindowListener, ActionListener
{
	Frame ventana = new Frame("Editar Usuario");
	Dialog dlgEdicion = new Dialog(ventana, "Edición", true);
	Dialog dlgMensaje = new Dialog(ventana, "Mensaje", true);

	Label lblElegir = new Label("Elegir el usuario a editar:");

	Choice choUsuarios = new Choice();

	Button btnEditar = new Button("Editar");

	Conexion conexion = new Conexion();

	Label lblTitulo = new Label("------- Edición de Usuario -------");
	Label lblNombre = new Label("Nombre:");
	Label lblClave = new Label("Clave:");
	Label lblRepetir = new Label("Repetir Clave:");
	Label lblCorreo = new Label("Correo:");
	Label lblMensaje = new Label("Modificación de Usuario Correcta");

	TextField txtNombre = new TextField(10);
	TextField txtClave = new TextField(10);
	TextField txtRepetir = new TextField(10);
	TextField txtCorreo = new TextField(10);

	Button btnModificar = new Button("Modificar");
	Button btnCancelar = new Button("Cancelar");

	String idUsuario = "";

	ModificarUsuario()
	{
		ventana.setLayout(new FlowLayout());
		ventana.setSize(220,220);
		ventana.addWindowListener(this);

		ventana.add(lblElegir);
		// Rellenar el Choice
		conexion.rellenarChoiceUsuarios(choUsuarios);
		ventana.add(choUsuarios);
		btnEditar.addActionListener(this);
		ventana.add(btnEditar);

		ventana.setResizable(false);
		ventana.setLocationRelativeTo(null);
		ventana.setVisible(true);
	}

	@Override
	public void windowOpened(WindowEvent e){}
	@Override
	public void windowClosing(WindowEvent e)
	{
		if(dlgEdicion.isActive())
		{
			dlgEdicion.setVisible(false);
			ventana.setVisible(false);
		}
		else if (dlgMensaje.isActive())
		{
			dlgMensaje.setVisible(false);
		}
		else
		{
			ventana.setVisible(false);
		}
	}
	@Override
	public void windowClosed(WindowEvent e){}
	@Override
	public void windowIconified(WindowEvent e){}
	@Override
	public void windowDeiconified(WindowEvent e){}
	@Override
	public void windowActivated(WindowEvent e){}
	@Override
	public void windowDeactivated(WindowEvent e){}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource().equals(btnEditar))
		{
			if(choUsuarios.getSelectedIndex()!=0)
			{
				dlgEdicion.setLayout(new FlowLayout());
				dlgEdicion.setSize(220,220);
				dlgEdicion.addWindowListener(this);

				dlgEdicion.add(lblTitulo);
				dlgEdicion.add(lblNombre);
				dlgEdicion.add(txtNombre);
				dlgEdicion.add(lblClave);
				txtClave.setEchoChar('*');
				dlgEdicion.add(txtClave);
				dlgEdicion.add(lblRepetir);
				txtRepetir.setEchoChar('*');
				dlgEdicion.add(txtRepetir);
				dlgEdicion.add(lblCorreo);
				dlgEdicion.add(txtCorreo);

				btnModificar.addActionListener(this);
				btnCancelar.addActionListener(this);

				dlgEdicion.add(btnModificar);
				dlgEdicion.add(btnCancelar);

				dlgEdicion.setResizable(false);
				dlgEdicion.setLocationRelativeTo(null);

				String tabla[] = choUsuarios.getSelectedItem().split("-");
				String resultado = conexion.getDatosEdicion(tabla[0]);

				String datos[] = resultado.split("-");
				idUsuario = datos[0];
				txtNombre.setText(datos[1]);
				txtClave.setText("");
				txtRepetir.setText("");
				txtCorreo.setText(datos[3]);

				dlgEdicion.setVisible(true);
			}
		}
		else if(e.getSource().equals(btnModificar))
		{
			dlgMensaje.setLayout(new FlowLayout());
			dlgMensaje.setSize(220,220);
			dlgMensaje.addWindowListener(this);
			
			if(txtNombre.getText().length()==0||txtClave.getText().length()==0||txtRepetir.getText().length()==0||txtCorreo.getText().length()==0)
			{
				lblMensaje.setText("Los campos están vacíos");
			}
			// Comprobar las claves
			else if(!txtClave.getText().equals(txtRepetir.getText()))
			{
				lblMensaje.setText("Las claves no coinciden");
			}
			else
			{
				// Modificar
				String sentencia = "UPDATE usuarios SET nombreUsuario='"+txtNombre.getText()+"', claveUsuario = SHA2('"+txtClave.getText()+"',256), emailUsuario = '"+txtCorreo.getText()+"' WHERE idUsuario="+idUsuario+";";
				int respuesta = conexion.modificarUsuario(sentencia);
				if(respuesta!=0)
				{
					// Mostrar Mensaje Error
					lblMensaje.setText("Error en Modificación");
				}
			}

			dlgMensaje.add(lblMensaje);
			dlgMensaje.setResizable(false);
			dlgMensaje.setLocationRelativeTo(null);
			dlgMensaje.setVisible(true);
		}
		else if (e.getSource().equals(btnCancelar))
		{
			dlgEdicion.setVisible(false);
		}
	}
}

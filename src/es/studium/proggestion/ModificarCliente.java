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

public class ModificarCliente implements WindowListener, ActionListener
{
	Frame ventana = new Frame("Editar Cliente");
	Dialog dlgEdicion = new Dialog(ventana, "Edición", true);
	Dialog dlgMensaje = new Dialog(ventana, "Mensaje", true);

	Label lblElegir = new Label("Elegir el cliente a editar:");

	Choice choClientes = new Choice();

	Button btnEditar = new Button("Editar");

	Conexion conexion = new Conexion();

	Label lblTitulo = new Label("------- Edición de Cliente -------");
	Label lblNombre = new Label("Nombre:");
	Label lblApellido1 = new Label("Primer apellido:");
	Label lblApellido2 = new Label("Segundo apellido:");
	Label lblDni = new Label("DNI:");
	Label lblCorreo = new Label("Correo:");
	Label lblMensaje = new Label("Modificación de Cliente Correcta");

	TextField txtNombre = new TextField(10);
	TextField txtApellido1 = new TextField(10);
	TextField txtApellido2 = new TextField(10);
	TextField txtDni = new TextField(10);
	TextField txtCorreo = new TextField(10);

	Button btnModificar = new Button("Modificar");
	Button btnCancelar = new Button("Cancelar");

	String idCliente = "";

	ModificarCliente()
	{
		ventana.setLayout(new FlowLayout());
		ventana.setSize(220,220);
		ventana.addWindowListener(this);

		ventana.add(lblElegir);
		// Rellenar el Choice
		conexion.rellenarChoiceClientes(choClientes);
		ventana.add(choClientes);
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
			if(choClientes.getSelectedIndex()!=0)
			{
				dlgEdicion.setLayout(new FlowLayout());
				dlgEdicion.setSize(160,380);
				dlgEdicion.addWindowListener(this);

				dlgEdicion.add(lblTitulo);
				dlgEdicion.add(lblNombre);
				dlgEdicion.add(txtNombre);
				dlgEdicion.add(lblApellido1);
				dlgEdicion.add(txtApellido1);
				dlgEdicion.add(lblApellido2);
				dlgEdicion.add(txtApellido2);
				dlgEdicion.add(lblDni);
				dlgEdicion.add(txtDni);
				dlgEdicion.add(lblCorreo);
				dlgEdicion.add(txtCorreo);

				btnModificar.addActionListener(this);
				btnCancelar.addActionListener(this);

				dlgEdicion.add(btnModificar);
				dlgEdicion.add(btnCancelar);

				dlgEdicion.setResizable(false);
				dlgEdicion.setLocationRelativeTo(null);

				String tabla[] = choClientes.getSelectedItem().split("-");
				String resultado = conexion.getDatosEdicion2(tabla[0]);

				String datos[] = resultado.split("-");
				idCliente = datos[0];
				txtNombre.setText(datos[1]);
				txtApellido1.setText(datos[2]);
				txtApellido2.setText(datos[3]);
				txtDni.setText(datos[4]);
				txtCorreo.setText(datos[5]);

				dlgEdicion.setVisible(true);
			}
		}
		else if(e.getSource().equals(btnModificar))
		{
			dlgMensaje.setLayout(new FlowLayout());
			dlgMensaje.setSize(220,220);
			dlgMensaje.addWindowListener(this);
			
			if(txtNombre.getText().length()==0||txtApellido1.getText().length()==0||txtApellido2.getText().length()==0||txtDni.getText().length()==0||txtCorreo.getText().length()==0)
			{
				lblMensaje.setText("Los campos están vacíos");
			}
			else
			{
				// Modificar
				String sentencia = "UPDATE clientes SET nombreCliente='"+txtNombre.getText()
				+"', primerApellidoCliente='"+txtApellido1.getText()+"', segundoApellidoCliente='"
						+txtApellido2.getText()+"', dniCliente='"+txtDni.getText()+"', emailCliente='"
						+txtCorreo.getText()+"' WHERE idCliente="+idCliente+";";
				int respuesta = conexion.modificarCliente(sentencia);
				if(respuesta!=0)
				{
					// Mostrar Mensaje Error
					lblMensaje.setText("Error en Modificación");
				}
				else {
					lblMensaje.setText("Modificación Correcta");
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

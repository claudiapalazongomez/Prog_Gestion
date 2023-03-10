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

public class ModificarProveedor implements WindowListener, ActionListener
{
	Frame ventana = new Frame("Editar Proveedor");
	Dialog dlgEdicion = new Dialog(ventana, "Edición", true);
	Dialog dlgMensaje = new Dialog(ventana, "Mensaje", true);

	Label lblElegir = new Label("Elegir el proveedor a editar:");

	Choice choProveedores = new Choice();

	Button btnEditar = new Button("Editar");

	Conexion conexion = new Conexion();

	Label lblTitulo = new Label("------- Edición de Proveedor -------");
	Label lblNombre = new Label("Nombre:");
	Label lblDireccion = new Label("Dirección:");
	Label lblTelefono = new Label("Teléfono:");
	Label lblMensaje = new Label("Modificación de Proveedor Correcta");

	TextField txtNombre = new TextField(10);
	TextField txtDireccion = new TextField(10);
	TextField txtTelefono = new TextField(10);

	Button btnModificar = new Button("Modificar");
	Button btnCancelar = new Button("Cancelar");

	String idProveedor = "";

	ModificarProveedor()
	{
		ventana.setLayout(new FlowLayout());
		ventana.setSize(220,220);
		ventana.addWindowListener(this);

		ventana.add(lblElegir);
		// Rellenar el Choice
		conexion.rellenarChoiceProveedores(choProveedores);
		ventana.add(choProveedores);
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
			if(choProveedores.getSelectedIndex()!=0)
			{
				dlgEdicion.setLayout(new FlowLayout());
				dlgEdicion.setSize(200,380);
				dlgEdicion.addWindowListener(this);

				dlgEdicion.add(lblTitulo);
				dlgEdicion.add(lblNombre);
				dlgEdicion.add(txtNombre);
				dlgEdicion.add(lblDireccion);
				dlgEdicion.add(txtDireccion);
				dlgEdicion.add(lblTelefono);
				dlgEdicion.add(txtTelefono);

				btnModificar.addActionListener(this);
				btnCancelar.addActionListener(this);

				dlgEdicion.add(btnModificar);
				dlgEdicion.add(btnCancelar);

				dlgEdicion.setResizable(false);
				dlgEdicion.setLocationRelativeTo(null);

				String tabla[] = choProveedores.getSelectedItem().split("-");
				String resultado = conexion.getDatosEdicion3(tabla[0]);

				String datos[] = resultado.split("-");
				idProveedor = datos[0];
				txtNombre.setText(datos[1]);
				txtDireccion.setText(datos[2]);
				txtTelefono.setText(datos[3]);

				dlgEdicion.setVisible(true);
			}
		}
		else if(e.getSource().equals(btnModificar))
		{
			dlgMensaje.setLayout(new FlowLayout());
			dlgMensaje.setSize(220,250);
			dlgMensaje.addWindowListener(this);
			
			if(txtNombre.getText().length()==0||txtDireccion.getText().length()==0||txtTelefono.getText().length()==0)
			{
				lblMensaje.setText("Los campos están vacíos");
			}
			else
			{
				// Modificar
				String sentencia = "UPDATE proveedores SET nombreProveedor='"+txtNombre.getText()
				+"', direccionProveedor='"+txtDireccion.getText()+"', telefonoProveedor='"
						+txtTelefono.getText()+"' WHERE idProveedor="+idProveedor+";";
				int respuesta = conexion.modificarProveedor(sentencia);
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

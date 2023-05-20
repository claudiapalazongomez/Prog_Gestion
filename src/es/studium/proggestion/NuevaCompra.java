package es.studium.proggestion;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JPanel;

public class NuevaCompra implements WindowListener, ActionListener
{

	Frame menuNuevaCompra = new Frame("Nueva Compra");
	
	Label lblAlta = new Label("Alta de Compra");
	Label lblFecha = new Label("Fecha:");
	TextField txtFecha = new TextField(10);
	Label lblCantidad = new Label("Cantidad:");
	TextField txtCantidad = new TextField(10);
	Choice choIDCliente = new Choice();
	Label lblCliente = new Label("Cliente:");
	Choice choIDArticulo = new Choice();
	Label lblArticulo = new Label("Artículo:");
	
	Button btnAceptar = new Button("Aceptar");
	Button btnLimpiar = new Button("Limpiar");

	Label lblMensaje = new Label("ALTA correcta");
	Dialog dlgMensaje = new Dialog(menuNuevaCompra, "Aviso", true);

	Conexion conexion = new Conexion();
	
	NuevaCompra(){
		menuNuevaCompra.setLayout(new FlowLayout());
		menuNuevaCompra.addWindowListener(this);

		menuNuevaCompra.add(lblAlta);
		
		JPanel panelContenedor = new JPanel(new GridLayout(0, 1));
		menuNuevaCompra.add(panelContenedor);

		panelContenedor.add(lblCantidad);
		panelContenedor.add(txtCantidad);
		panelContenedor.add(lblFecha);
		panelContenedor.add(txtFecha);
		txtFecha.setText("DD/MM/AAAA");
		txtFecha.addFocusListener(new FocusAdapter()
		{
			// Si pincho en txtFecha, desaparece DD/MM/YYYY
			@Override
			public void focusGained(FocusEvent e)
			{
				if (txtFecha.getText().equals("DD/MM/AAAA"))
				{
					txtFecha.setText("");
				}
			}

			// Y si sigue vacío después de eso, vuelve a aparecer
			@Override
			public void focusLost(FocusEvent e)
			{
				if (txtFecha.getText().isEmpty())
				{
					txtFecha.setText("DD/MM/AAAA");
				}
			}
		});
		panelContenedor.add(lblCliente);
		conexion.rellenarChoiceClientes(choIDCliente);
		panelContenedor.add(choIDCliente);
		panelContenedor.add(lblArticulo);
		conexion.rellenarChoiceArticulos(choIDArticulo);
		panelContenedor.add(choIDArticulo);
		menuNuevaCompra.add(panelContenedor);
		
		btnAceptar.addActionListener(this);
		btnLimpiar.addActionListener(this);
		menuNuevaCompra.add(btnAceptar);
		menuNuevaCompra.add(btnLimpiar);

		menuNuevaCompra.setLocationRelativeTo(null);
		menuNuevaCompra.setResizable(false);
		menuNuevaCompra.setSize(200, 300);
		txtCantidad.requestFocus();
		menuNuevaCompra.setVisible(true);
	}

	@Override
	public void windowOpened(WindowEvent e)
	{	
	}

	@Override
	public void windowClosing(WindowEvent e)
	{
		if (dlgMensaje.isActive())
		{
			dlgMensaje.setVisible(false);
		} else
		{
			menuNuevaCompra.setVisible(false);
		}
		
	}

	@Override
	public void windowClosed(WindowEvent e)
	{	
	}

	@Override
	public void windowIconified(WindowEvent e)
	{	
	}

	@Override
	public void windowDeiconified(WindowEvent e)
	{	
	}

	@Override
	public void windowActivated(WindowEvent e)
	{	
	}

	@Override
	public void windowDeactivated(WindowEvent e)
	{	
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource().equals(btnAceptar))
		{
			dlgMensaje.setLayout(new FlowLayout());
			dlgMensaje.setSize(300, 300);
			dlgMensaje.addWindowListener(this);

			// Si los campos están vacíos, evitar que se cree un artículo
			if (txtCantidad.getText().length() == 0)
			{
				lblMensaje.setText("Los campos están vacíos");
			}
			else
			{
				// Almacenar la fecha en formato americano
	            String fechaEuropea = txtFecha.getText();
	            String fechaAmericana = convertirFechaFormatoAmericano(fechaEuropea);
				
				// Almacenar en una variable el ID del cliente
				String clienteSeleccionado = choIDCliente.getSelectedItem();
				String[] elementosCliente = clienteSeleccionado.split("-");
				String IDCliente = elementosCliente[0].trim(); // eliminar espacios en blanco alrededor del número
				int idCliente = Integer.parseInt(IDCliente);
				
				// Almacenar en una variable el ID del artículo
				String articuloSeleccionado = choIDArticulo.getSelectedItem();
				String[] elementosArticulo = articuloSeleccionado.split("-");
				String IDArticulo = elementosArticulo[0].trim(); // eliminar espacios en blanco alrededor del número
				int idArticulo = Integer.parseInt(IDArticulo);
				
				// Dar de alta
				String sentencia = "INSERT INTO comprar VALUES (null, '" + fechaAmericana + "', "
						+ "'" + txtCantidad.getText() + "', '" + idCliente + "', '" 
						+ idArticulo + "');";
				int respuesta = conexion.altaCompra(sentencia);
				
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

		} else if (e.getSource().equals(btnLimpiar))
		{
			// Se borra todo lo que has introducido y se pone el cursor en txtColor
			txtCantidad.setText("");
			txtFecha.setText("DD/MM/AAAA");
			// Obtener el valor de la primera opción del Choice desde la clase Conexion
	        String primeraOpcionCliente = conexion.getPrimeraOpcionCliente();
	        String primeraOpcionArticulo = conexion.getPrimeraOpcionArticulo();
	        // Establecer el valor seleccionado en el Choice
	        choIDCliente.select(primeraOpcionCliente);
	        choIDArticulo.select(primeraOpcionArticulo);
			txtCantidad.requestFocus();
		}
		
	}
	
	// Método para convertir una fecha en formato europeo (DD/MM/AAAA) al formato americano (MM/DD/YYYY)
		private String convertirFechaFormatoAmericano(String fechaEuropea) {
		    try {
		        SimpleDateFormat formatoEuropeo = new SimpleDateFormat("dd/MM/yyyy");
		        SimpleDateFormat formatoAmericano = new SimpleDateFormat("yyyy/MM/dd");
		        
		        Date fecha = formatoEuropeo.parse(fechaEuropea);
		        String fechaAmericana = formatoAmericano.format(fecha);
		        
		        return fechaAmericana;
		    } catch (ParseException e) {
		        e.printStackTrace();
		    }
		    
		    return "";
		}
}

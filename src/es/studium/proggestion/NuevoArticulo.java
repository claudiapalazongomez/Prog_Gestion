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

public class NuevoArticulo implements WindowListener, ActionListener
{
	Frame menuNuevoArticulo = new Frame("Nuevo Artículo");

	Label lblAlta = new Label("Alta de Artículo");
	Label lblColor = new Label("Color:");
	TextField txtColor = new TextField(10);
	Label lblStock = new Label("Stock:");
	TextField txtStock = new TextField(10);
	Label lblReferencia = new Label("Referencia:");
	TextField txtReferencia = new TextField(10);
	Label lblDescuento = new Label("Descuento:");
	TextField txtDescuento = new TextField(10);
	Label lblPrecio = new Label("Precio:");
	TextField txtPrecio = new TextField(10);
	Label lblFechaCompra = new Label("Fecha de la compra");
	TextField txtFechaCompra = new TextField(10);
	Label lblTrackCode = new Label("Track Code:");
	TextField txtTrackCode = new TextField(10);
	Choice choIDProveedor = new Choice();
	Label lblProveedor = new Label("Proveedor:");
	Button btnAceptar = new Button("Aceptar");
	Button btnLimpiar = new Button("Limpiar");

	Label lblMensaje = new Label("ALTA correcta");
	Dialog dlgMensaje = new Dialog(menuNuevoArticulo, "Aviso", true);

	Conexion conexion = new Conexion();

	int mouseX, mouseY;

	NuevoArticulo()
	{
		menuNuevoArticulo.setLayout(new FlowLayout());
		menuNuevoArticulo.addWindowListener(this);

		menuNuevoArticulo.add(lblAlta);

		JPanel panelContenedor = new JPanel(new GridLayout(0, 1));
		menuNuevoArticulo.add(panelContenedor);

		panelContenedor.add(lblColor);
		panelContenedor.add(txtColor);
		panelContenedor.add(lblStock);
		panelContenedor.add(txtStock);
		panelContenedor.add(lblReferencia);
		panelContenedor.add(txtReferencia);
		panelContenedor.add(lblDescuento);
		panelContenedor.add(txtDescuento);
		panelContenedor.add(lblPrecio);
		panelContenedor.add(txtPrecio);
		txtPrecio.setText("00.00");
		// Así le indicamos al usuario que el separador es con un punto y si desea
		// almacenar decimales, un máximo de 2
		txtPrecio.addFocusListener(new FocusAdapter()
		{
			// Si pincho en txtPrecio, desaparece 00.00
			@Override
			public void focusGained(FocusEvent ae)
			{
				if (txtPrecio.getText().equals("00.00"))
				{
					txtPrecio.setText("");
				}
			}

			// Y si sigue vacío después de eso, vuelve a aparecer
			@Override
			public void focusLost(FocusEvent ae)
			{
				if (txtPrecio.getText().isEmpty())
				{
					txtPrecio.setText("00.00");
				}
			}
		});
		panelContenedor.add(lblFechaCompra);
		panelContenedor.add(txtFechaCompra);
		txtFechaCompra.setText("DD/MM/AAAA");
		txtFechaCompra.addFocusListener(new FocusAdapter()
		{
			// Si pincho en txtFechaCompra, desaparece DD/MM/YYYY
			@Override
			public void focusGained(FocusEvent e)
			{
				if (txtFechaCompra.getText().equals("DD/MM/AAAA"))
				{
					txtFechaCompra.setText("");
				}
			}

			// Y si sigue vacío después de eso, vuelve a aparecer
			@Override
			public void focusLost(FocusEvent e)
			{
				if (txtFechaCompra.getText().isEmpty())
				{
					txtFechaCompra.setText("DD/MM/AAAA");
				}
			}
		});
		panelContenedor.add(lblTrackCode);
		panelContenedor.add(txtTrackCode);
		panelContenedor.add(lblProveedor);
		conexion.rellenarChoiceProveedores(choIDProveedor);
		panelContenedor.add(choIDProveedor);
		menuNuevoArticulo.add(panelContenedor);

		btnAceptar.addActionListener(this);
		btnLimpiar.addActionListener(this);
		menuNuevoArticulo.add(btnAceptar);
		menuNuevoArticulo.add(btnLimpiar);

		menuNuevoArticulo.setLocationRelativeTo(null);
		menuNuevoArticulo.setResizable(false);
		menuNuevoArticulo.setSize(200, 500);
		menuNuevoArticulo.setVisible(true);
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
			menuNuevoArticulo.setVisible(false);
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
			if (txtColor.getText().length() == 0 || txtStock.getText().length() == 0
					|| txtReferencia.getText().length() == 0 || txtTrackCode.getText().length() == 0)
			{
				lblMensaje.setText("Los campos están vacíos");
			}
			// Comprobar que el precio tiene un máximo de dos decimales
			else if (!validarDosDecimales(txtPrecio.getText()))
			{
				lblMensaje.setText("El precio debe tener como máximo dos decimales");
			} 
			else
			{
				// Almacenar la fecha en formato americano
	            String fechaEuropea = txtFechaCompra.getText();
	            String fechaAmericana = convertirFechaFormatoAmericano(fechaEuropea);
				
				// Almacenar en una variable el ID del proveedor
				String proveedorSeleccionado = choIDProveedor.getSelectedItem();
				String[] elementosProveedor = proveedorSeleccionado.split("-");
				String IDProveedor = elementosProveedor[0].trim(); // eliminar espacios en blanco alrededor del número
				int idProveedor = Integer.parseInt(IDProveedor);
				
				// Dar de alta
				String sentencia = "INSERT INTO articulos VALUES (null, '" + txtColor.getText() + "', "
						+ "'" + txtStock.getText() + "', '" + txtReferencia.getText() + "', '" 
						+ txtDescuento.getText() + "', '" + txtPrecio.getText() + "', '" + fechaAmericana + "', '" 
						+ txtTrackCode.getText() + "', '" + idProveedor + "');";
				int respuesta = conexion.altaArticulo(sentencia);
				
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
			txtColor.setText("");
			txtStock.setText("");
			txtReferencia.setText("");
			txtDescuento.setText("");
			txtPrecio.setText("00.00");
			txtFechaCompra.setText("DD/MM/AAAA");
			txtTrackCode.setText("");
			// Obtener el valor de la primera opción del Choice desde la clase Conexion
	        String primeraOpcion = conexion.getPrimeraOpcionProveedor();
	        // Establecer el valor seleccionado en el Choice
	        choIDProveedor.select(primeraOpcion);
			txtColor.requestFocus();
		}
	}

	// Método para validar que en el precio se introducen dos decimales
	private boolean validarDosDecimales(String precioCompra)
	{
		try
		{
			double precio = Double.parseDouble(precioCompra);
			int decimales = (int) ((precio % 1) * 100); // Obtiene la parte decimal multiplicada por 100 como un entero
			return decimales >= 0 && decimales <= 99; // Verifica que la parte decimal tenga exactamente uno o dos decimales
		} catch (NumberFormatException e)
		{
			return false;
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

package es.studium.proggestion;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


public class MenuPrincipal implements ActionListener, WindowListener
{
	Frame menuPrincipal = new Frame("Menú Principal");
	MenuBar barraMenu = new MenuBar();
	
	Menu menuUsuario = new Menu("Usuarios");
	Menu menuArticulos = new Menu("Artículos");
	Menu menuClientes = new Menu("Clientes");
	Menu menuComprar = new Menu("Compras");
	Menu menuProveedores = new Menu("Proveedores");
	
	MenuItem mniUsuarioNuevo = new MenuItem("Nuevo");
	MenuItem mniUsuarioListado = new MenuItem("Listado");
	MenuItem mniUsuarioBaja = new MenuItem("Baja");
	MenuItem mniUsuarioModificar = new MenuItem("Modificar");
	
	MenuItem mniArticuloNuevo = new MenuItem("Nuevo");
	MenuItem mniArticuloListado = new MenuItem("Listado");
	MenuItem mniArticuloBaja = new MenuItem("Baja");
	
	MenuItem mniClienteNuevo = new MenuItem("Nuevo");
	MenuItem mniClienteListado = new MenuItem("Listado");
	MenuItem mniClienteBaja = new MenuItem("Baja");
	MenuItem mniClienteModificar = new MenuItem("Modificar");
	
	MenuItem mniComprarNuevo = new MenuItem("Nuevo");
	MenuItem mniComprarListado = new MenuItem("Listado");
	MenuItem mniComprarBaja = new MenuItem("Baja");
	
	MenuItem mniProveedorNuevo = new MenuItem("Nuevo");
	MenuItem mniProveedorListado = new MenuItem("Listado");
	MenuItem mniProveedorBaja = new MenuItem("Baja");
	MenuItem mniProveedorModificar = new MenuItem("Modificar");
	
	Conexion conexion = new Conexion();
	String cadena = "Cierra login";
	
	int tipoUsuario;
	static String nombreUsuario; 
	
	MenuPrincipal(String usuario, int t){
		tipoUsuario = t;
		nombreUsuario = usuario; 
		menuPrincipal.setLayout(new FlowLayout());
		menuPrincipal.addWindowListener(this);
		menuPrincipal.setMenuBar(barraMenu);
		
		mniUsuarioNuevo.addActionListener(this);
		mniUsuarioListado.addActionListener(this);
		mniUsuarioBaja.addActionListener(this);
		mniUsuarioModificar.addActionListener(this);
		menuUsuario.add(mniUsuarioNuevo);
		if(tipoUsuario==0) {
			menuUsuario.add(mniUsuarioListado);
			menuUsuario.add(mniUsuarioBaja);
			menuUsuario.add(mniUsuarioModificar);
		}
		
		mniArticuloNuevo.addActionListener(this);
		mniArticuloListado.addActionListener(this);
		mniArticuloBaja.addActionListener(this);
		menuArticulos.add(mniArticuloNuevo);
		if(tipoUsuario==0) {
			menuArticulos.add(mniArticuloListado);
			menuArticulos.add(mniArticuloBaja);
		}
		
		mniClienteNuevo.addActionListener(this);
		mniClienteListado.addActionListener(this);
		mniClienteBaja.addActionListener(this);
		mniClienteModificar.addActionListener(this);
		menuClientes.add(mniClienteNuevo);
		if(tipoUsuario==0) {
			menuClientes.add(mniClienteListado);
			menuClientes.add(mniClienteBaja);
			menuClientes.add(mniClienteModificar);
		}
		
		mniComprarNuevo.addActionListener(this);
		mniComprarListado.addActionListener(this);
		mniComprarBaja.addActionListener(this);
		menuComprar.add(mniComprarNuevo);
		if(tipoUsuario==0) {
			menuComprar.add(mniComprarListado);
			menuComprar.add(mniComprarBaja);
		}
		
		mniProveedorNuevo.addActionListener(this);
		mniProveedorListado.addActionListener(this);
		mniProveedorBaja.addActionListener(this);
		mniProveedorModificar.addActionListener(this);
		menuProveedores.add(mniProveedorNuevo);
		if(tipoUsuario==0) {
			menuProveedores.add(mniProveedorListado);
			menuProveedores.add(mniProveedorBaja);
			menuProveedores.add(mniProveedorModificar);
		}
		
		barraMenu.add(menuUsuario);
		barraMenu.add(menuArticulos);
		barraMenu.add(menuClientes);
		barraMenu.add(menuComprar);
		barraMenu.add(menuProveedores);
		
		
		menuPrincipal.setLocationRelativeTo(null);
		menuPrincipal.setResizable(false);	
		menuPrincipal.setSize(400,400);
		menuPrincipal.setVisible(true);
	}
	
	@Override
	public void windowOpened(WindowEvent e)
	{}
	public void windowClosing(WindowEvent e)
	{
		conexion.apunteLog(nombreUsuario, cadena);
		System.exit(0);
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
							 	/*USUARIOS*/
		//Nuevo usuario
		if(e.getSource().equals(mniUsuarioNuevo)) {
			new NuevoUsuario();
		}
		
		//Listado usuario
		else if(e.getSource().equals(mniUsuarioListado)) {
			new ListadoUsuarios();
		}
		
		//Baja usuario
		else if(e.getSource().equals(mniUsuarioBaja)) {
			new BajaUsuario();
		}
		
		//Modificar usuario
		else if(e.getSource().equals(mniUsuarioModificar))
		{
			new ModificarUsuario();
		}
		
								/*CLIENTES*/
		//Nuevo cliente
		else if(e.getSource().equals(mniClienteNuevo)) {
			new NuevoCliente();
		}
		
		//Listado cliente
		else if(e.getSource().equals(mniClienteListado)) {
			new ListadoClientes();
		}
		
		//Baja cliente
		else if(e.getSource().equals(mniClienteBaja)) {
			new BajaCliente();
		}
		
		//Modificar cliente
		else if(e.getSource().equals(mniClienteModificar)) {
			new ModificarCliente();
		}
		
								/*PROVEEDORES*/
		//Nuevo proveedor
		else if(e.getSource().equals(mniProveedorNuevo)) {
			new NuevoProveedor();
		}
		
		//Listado proveedor
		else if(e.getSource().equals(mniProveedorListado)) {
			new ListadoProveedores();
		}
		
		//Baja proveedor
		else if(e.getSource().equals(mniProveedorBaja)) {
			new BajaProveedor();
		}
		
		//Modificar proveedor
		else if(e.getSource().equals(mniProveedorModificar)) {
			new ModificarProveedor();
		}
		
	}
}

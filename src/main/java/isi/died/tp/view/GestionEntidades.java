package isi.died.tp.view;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import isi.died.tp.app.Main;
import isi.died.tp.controller.GestionEntidadesController;
import isi.died.tp.controller.OpcionesMenu;

public class GestionEntidades {
	private static JFrame ventana;
	private static GestionEntidadesController controller;
	
	public GestionEntidades(JFrame ventana){
		this.controller = new GestionEntidadesController(ventana);
		this.ventana = ventana;
	}
	
	public static void mostrarMenu() {
		//config elementos panel
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		JLabel encabezado = new JLabel("Gestión de Entidades");
		JButton agregar = new JButton("Agregar"), modificar = new JButton("Modificar"), eliminar = new JButton("Eliminar"),
				volver = new JButton("Volver");
		JComboBox<String> opcionSeleccionada = new JComboBox<String>();
			
		//titulo
		constraints.insets=new Insets(5, 5, 100, 5);
		constraints.gridx=0;
		constraints.gridy=0;
		constraints.gridheight=1;
		constraints.gridwidth=8;
		constraints.anchor=GridBagConstraints.NORTH;
		encabezado.setFont(new Font(encabezado.getFont().getName(), encabezado.getFont().getStyle(), 40));
		panel.add(encabezado,constraints);
				
		constraints.fill=GridBagConstraints.HORIZONTAL;
				
		//comboBox seleccion
		constraints.insets=new Insets(0, 50, 0, 0);
		constraints.anchor=GridBagConstraints.CENTER;
		opcionSeleccionada.addItem("Seleccione");
		opcionSeleccionada.addItem("Insumo");
		opcionSeleccionada.addItem("Planta");
		opcionSeleccionada.addItem("Camion");
		opcionSeleccionada.addItem("Stock");
		opcionSeleccionada.addItem("Ruta");
		opcionSeleccionada.setSelectedItem("Seleccione");
		constraints.insets.set(0, 75, 5, 5);
		constraints.gridx = 6;
		constraints.gridy = 3;
		constraints.gridwidth=1;
		panel.add(opcionSeleccionada, constraints);
				
		//botones ocultos
		constraints.gridx=7;
		constraints.fill=GridBagConstraints.HORIZONTAL;
		constraints.insets.set(0, 80, 5, 70);		
		constraints.gridy=2;
		panel.add(agregar, constraints);
		agregar.setEnabled(false);
				
		constraints.gridy=3;
		panel.add(modificar, constraints);
		modificar.setEnabled(false);
				
		constraints.gridy=4;
		panel.add(eliminar, constraints);
		eliminar.setEnabled(false);
		
		//boton volver
		constraints.fill=GridBagConstraints.NONE;
		constraints.anchor=GridBagConstraints.SOUTH;
		constraints.insets.set(90, 10, 5, 5);
		constraints.gridx = 6;
		constraints.gridwidth = 2;
		constraints.gridy=10;
		panel.add(volver, constraints);
				
		//listener volver
		volver.addActionListener(a -> Main.mostrarInterfaz());
				
		//listener seleccion
		opcionSeleccionada.addActionListener (a -> {
			switch((String)opcionSeleccionada.getSelectedItem()){
			case "Seleccione":
				agregar.setEnabled(false);
				modificar.setEnabled(false);
				eliminar.setEnabled(false);
				break;
			default:
				agregar.setEnabled(true);
				modificar.setEnabled(true);
				eliminar.setEnabled(true);
				break;
			}
		});
				
		//listener agregarX
		agregar.addActionListener(a -> {
			switch((String)opcionSeleccionada.getSelectedItem()){
			case "Insumo":
				controller.opcion(OpcionesMenu.AGREGAR_INSUMO, ventana);
				break;
			case "Planta":
				controller.opcion(OpcionesMenu.AGREGAR_PLANTA, ventana);
				break;
			case "Camion":
				controller.opcion(OpcionesMenu.AGREGAR_CAMION, ventana);
				break;
			case "Stock":
				controller.opcion(OpcionesMenu.AGREGAR_STOCK, ventana);
				break;
			case "Ruta":
				controller.opcion(OpcionesMenu.AGREGAR_RUTA, ventana);
				break;
			}
		});
				
		//listener modificarX
		modificar.addActionListener(a -> {
			switch((String)opcionSeleccionada.getSelectedItem()){
			case "Insumo":
				controller.opcion(OpcionesMenu.MODIFICAR_INSUMO, ventana);
				break;
			case "Planta":
				controller.opcion(OpcionesMenu.MODIFICAR_PLANTA, ventana);
				break;
			case "Camion":
				controller.opcion(OpcionesMenu.MODIFICAR_CAMION, ventana);
				break;
			case "Stock":
				controller.opcion(OpcionesMenu.MODIFICAR_STOCK, ventana);
				break;
			case "Ruta":
				controller.opcion(OpcionesMenu.MODIFICAR_RUTA, ventana);
				break;
			}
		});
				
		//listener eliminarX
		eliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switch((String)opcionSeleccionada.getSelectedItem()){
				case "Insumo":
					controller.opcion(OpcionesMenu.ELIMINAR_INSUMO, ventana);
					break;
				case "Planta":
					controller.opcion(OpcionesMenu.ELIMINAR_PLANTA, ventana);
					break;
				case "Camion":
					controller.opcion(OpcionesMenu.ELIMINAR_CAMION, ventana);
					break;
				case "Stock":
					controller.opcion(OpcionesMenu.ELIMINAR_STOCK, ventana);
					break;
				case "Ruta":
					controller.opcion(OpcionesMenu.ELIMINAR_RUTA, ventana);
					break;
				}
			}
		});
				
		//manejo ventana
		ventana.setContentPane(panel);
		ventana.pack();
		ventana.setSize(800, 600);
		ventana.setLocationRelativeTo(null);
		ventana.setTitle("Gestión de Entidades");
		ventana.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		ventana.setVisible(true);
	}
}
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

import isi.died.tp.controller.GestionEntidadesController;
import isi.died.tp.controller.InsumoController;
import isi.died.tp.controller.MenuController;
import isi.died.tp.controller.OpcionesMenu;
import isi.died.tp.dao.InsumoDao;

public class GestionEntidades {
	private static JFrame ventana;
	private static GestionEntidadesController controller;
	
	public GestionEntidades(InsumoDao dao, JFrame ventana){
		this.controller = new GestionEntidadesController(dao, ventana);
		this.ventana = ventana;
	}
	
	public static void mostrarMenu() {
		//config elementos panel
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		JLabel encabezado = new JLabel("Gestión de Entidades");
		JButton agregar = new JButton("Agregar"), modificar = new JButton("Modificar"), eliminar = new JButton("Eliminar"), camino = new JButton("Agregar Camino");
		JComboBox<String> opcionSeleccionada = new JComboBox<String>();
		/*JMenuBar barraMenu;
		JMenu menu;
		JMenuItem menuItem;
				
		//barra arriba
		barraMenu = new JMenuBar();
		menu = new JMenu("Opciones");
		barraMenu.add(menu);
		menuItem = new JMenuItem("Información");
		menuItem.addActionListener(e -> info(ventana));
		menu.add(menuItem);
		menu.addSeparator();
		menuItem = new JMenuItem("Salir");
		menuItem.addActionListener(e->ventana.dispatchEvent(new WindowEvent(ventana, WindowEvent.WINDOW_CLOSING)));
		menu.add(menuItem);
		ventana.setJMenuBar(barraMenu);*/
				
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
		opcionSeleccionada.setSelectedItem("Seleccione");
		constraints.insets.set(0, 25, 5, 5);
		constraints.gridx = 6;
		constraints.gridy = 4;
		constraints.gridwidth=1;
		panel.add(opcionSeleccionada, constraints);
				
		//botones ocultos
		constraints.gridx=7;
		constraints.fill=GridBagConstraints.HORIZONTAL;
				
		constraints.gridy=2;
		panel.add(agregar, constraints);
		agregar.setEnabled(false);
				
		constraints.gridy=3;
		panel.add(modificar, constraints);
		modificar.setEnabled(false);
				
		constraints.gridy=4;
		panel.add(eliminar, constraints);
		eliminar.setEnabled(false);
				
		constraints.gridy=5;
		panel.add(camino, constraints);
		camino.setEnabled(false);
				
		//listener seleccion
		opcionSeleccionada.addActionListener (new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				switch((String)opcionSeleccionada.getSelectedItem()){
				case "Seleccione":
					agregar.setEnabled(false);
					modificar.setEnabled(false);
				    eliminar.setEnabled(false);
				    camino.setEnabled(false);
				    break;
				case "Insumo":
					agregar.setEnabled(true);
				    modificar.setEnabled(true);
				    eliminar.setEnabled(true);
				    camino.setEnabled(false);
				    break;
				case "Planta":
				    agregar.setEnabled(true);
				    modificar.setEnabled(true);
				    eliminar.setEnabled(true);
				    camino.setEnabled(true);
				    break;
				case "Camion":
				    agregar.setEnabled(true);
				    modificar.setEnabled(true);
				    eliminar.setEnabled(true);
				    camino.setEnabled(false);
				    break;
				case "Stock":
				    agregar.setEnabled(true);
				    modificar.setEnabled(true);
				    eliminar.setEnabled(true);
				    camino.setEnabled(false);
				    break;
				}
			}
		});
				
		//listener agregarX
		agregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
				}
			}
		});
				
		//listener modificarX
		modificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
				}
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
				}
			}
		});
				
		//listener eliminarX
		eliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switch((String)opcionSeleccionada.getSelectedItem()){
				case "Planta":
					controller.opcion(OpcionesMenu.AGREGAR_CAMINO_PLANTA, ventana);
					break;
				}
			}
		});
				
		//manejo ventana
		ventana.setContentPane(panel);
		ventana.pack();
		ventana.setSize(800, 600);
		ventana.setLocationRelativeTo(null);
		ventana.setTitle("Sistema de Gestión");
		ventana.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		ventana.setVisible(true);
	}
}
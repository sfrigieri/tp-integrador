package isi.died.tp.app;

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

import isi.died.tp.controller.MenuController;
import isi.died.tp.controller.OpcionesMenu;
import isi.died.tp.dao.InsumoDao;
import isi.died.tp.dao.InsumoDaoDefault;

public class Main {
	
	private static JFrame ventana;
	private static MenuController controller;


	public static void main(String[] args) {
		ventana = new JFrame();
		InsumoDao dao = new InsumoDaoDefault();
        controller = new MenuController(dao, ventana);
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
	        public void run() {
	        	mostrarInterfaz();
	        }
	    });
	}

	
	
	public static void mostrarInterfaz() {
		
		//config elementos panel
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		JLabel encabezado = new JLabel("Sistema de gestión logística");
		JButton agregar = new JButton("Agregar"), modificar = new JButton("Modificar"), eliminar = new JButton("Eliminar"), camino = new JButton("Agregar Camino");
		JComboBox<String> opcionSeleccionada = new JComboBox<String>();
		JMenuBar barraMenu;
		JMenu menu;
		JMenuItem menuItem;
		
		//barra arriba
		barraMenu = new JMenuBar();
		menu = new JMenu("Sistema");
	    barraMenu.add(menu);
	    menuItem = new JMenuItem("Información");
	    menuItem.addActionListener(e -> info(ventana));
	    menu.add(menuItem);
	    menu.addSeparator();
	    menuItem = new JMenuItem("Salir");
	    menuItem.addActionListener(e->ventana.dispatchEvent(new WindowEvent(ventana, WindowEvent.WINDOW_CLOSING)));
	    menu.add(menuItem);
	    ventana.setJMenuBar(barraMenu);
		
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
		
		
		//manejo ventana
		ventana.setContentPane(panel);
		ventana.pack();
		ventana.setSize(800, 600);
		ventana.setLocationRelativeTo(null);
		ventana.setTitle("Sistema de Gestión Logística");
		ventana.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
        ventana.setVisible(true);
	}



	private static void info (JFrame ventana) {
		JOptionPane.showConfirmDialog(ventana, "                 Trabajo Práctico Final DIED 2019\nFrigieri Santiago, Holub German y Hominal Williams", "Información", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
	}


}

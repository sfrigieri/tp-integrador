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
		JLabel encabezado1 = new JLabel("  Bienvenido al");
		JLabel encabezado2 = new JLabel("Sistema de Gestión");
		JButton gestionEntidades = new JButton("Gestión de Entidades"), gestionLogistica = new JButton("Gestión de Logística"), gestionEnvios = new JButton("Gestión General de Envíos");
		JMenuBar barraMenu;
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
	    ventana.setJMenuBar(barraMenu);
		
		//titulo
		constraints.insets=new Insets(5, 5, 5, 5);
		constraints.gridx=0;
		constraints.gridy=0;
		constraints.gridheight=1;
		constraints.gridwidth=8;
		constraints.anchor=GridBagConstraints.NORTH;
		encabezado1.setFont(new Font(encabezado1.getFont().getName(), encabezado1.getFont().getStyle(), 40));
		panel.add(encabezado1,constraints);
		constraints.gridy=1;
		constraints.insets.set(5, 5, 100, 5);
		encabezado2.setFont(new Font(encabezado1.getFont().getName(), encabezado1.getFont().getStyle(), 40));
		panel.add(encabezado2,constraints);
		
		//botones ocultos
		constraints.insets.set(0, 25, 20, 5);
		constraints.gridwidth=1;
		constraints.gridx=7;
		constraints.fill=GridBagConstraints.HORIZONTAL;
		
		constraints.gridy=3;
		panel.add(gestionEntidades, constraints);
		
		constraints.gridy=4;
		panel.add(gestionLogistica, constraints);
		
		constraints.gridy=5;
		panel.add(gestionEnvios, constraints);
		
		//listener gestionEntidades
		gestionEntidades.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.opcion(OpcionesMenu.GESTION_ENTIDADES, ventana);
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



	private static void info (JFrame ventana) {
		JOptionPane.showConfirmDialog(ventana, "                 Trabajo Práctico Final DIED 2019\nFrigieri Santiago, Holub German y Hominal Williams", "Información", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
	}


}

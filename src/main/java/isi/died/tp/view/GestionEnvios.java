package isi.died.tp.view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.JOptionPane;

import isi.died.tp.app.Main;
import isi.died.tp.controller.GestionEntidadesController;
import isi.died.tp.controller.GestionEnviosController;
import isi.died.tp.controller.OpcionesMenuEnvios;
import isi.died.tp.controller.PlantaController;
import isi.died.tp.model.Planta;
import isi.died.tp.view.table.PageRanksTableModel;

public class GestionEnvios {

	private static JFrame ventana;
	private static GestionEnviosController controller;


	public GestionEnvios(JFrame v) {
		ventana = v;
		controller = new GestionEnviosController(v);
	}

	public static void mostrarMenu() {

		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		JLabel encabezado = new JLabel("Gestión General de Envíos");
		JButton flujoMaximo = new JButton("Flujo Máximo de la Red"),
				pageRanks = new JButton("Page Ranks de Plantas"),
				mejorSeleccion = new JButton("Mejor Selección de Envío"),
				volver = new JButton("Volver");

		//titulo
		constraints.insets=new Insets(5, 5, 100, 5);
		constraints.gridx=0;
		constraints.gridy=0;
		constraints.gridheight=1;
		constraints.gridwidth=8;
		constraints.anchor=GridBagConstraints.NORTH;
		encabezado.setFont(new Font(encabezado.getFont().getName(), encabezado.getFont().getStyle(), 40));
		panel.add(encabezado,constraints);


		//botones ocultos
		constraints.insets.set(0, 170, 20, 0);
		constraints.gridwidth=1;
		constraints.gridx=6;
		constraints.fill=GridBagConstraints.HORIZONTAL;

		constraints.gridy=3;
		panel.add(flujoMaximo, constraints);

		constraints.gridy=4;
		panel.add(pageRanks, constraints);

		constraints.gridy=5;
		panel.add(mejorSeleccion, constraints);


		flujoMaximo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.opcion(OpcionesMenuEnvios.FLUJO_MAXIMO);
			}
		});

		mejorSeleccion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.opcion(OpcionesMenuEnvios.MEJOR_SELECCION_ENVIO);
			}
		});

		pageRanks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.opcion(OpcionesMenuEnvios.PAGE_RANKS);
			}
		});

		//boton volver
		constraints.fill=GridBagConstraints.NONE;
		constraints.anchor=GridBagConstraints.SOUTH;
		constraints.insets.set(50, 15, 5, 0);
		constraints.gridx = 6;
		constraints.gridwidth = 2;
		constraints.gridy=10;
		panel.add(volver, constraints);

		//listener volver
		volver.addActionListener(a -> Main.mostrarInterfaz());

		//manejo ventana
		ventana.setContentPane(panel);
		ventana.pack();
		ventana.setSize(800, 600);
		ventana.setLocationRelativeTo(null);
		ventana.setTitle("Gestión General de Envíos");
		ventana.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		ventana.setVisible(true);


	}

	private static Double getFactorAmortiguacion() {

		JTextField field = new JTextField(5);
		JPanel panel = new JPanel();
		panel.add(new JLabel("Factor de Transición (0-1): "));
		panel.add(field);
		int result = JOptionPane.showConfirmDialog(null, panel, "Probabilidad de Transición a otra Planta",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

		if (result == JOptionPane.OK_OPTION) {
			try {
				Double.parseDouble(field.getText());
				if (Double.valueOf(field.getText()) <= 1 && Double.valueOf(field.getText()) >= 0) {
					return Double.valueOf(field.getText());
				}else {
					JOptionPane.showConfirmDialog(ventana, "Valor ingresado fuera de rango.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
				} }
			catch(NumberFormatException nfex) {
				JOptionPane.showConfirmDialog(ventana, "El valor debe ser numérico.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			}


		} else {
			return -1.0;
		}

		return null;
	}

	public static void mostrarTablaPageRanks() {

		JFrame popup = new JFrame("Page Rank de Plantas en la Red Actual");
		JPanel panel = new JPanel(new GridBagLayout());
		JTable table;
		popup.setDefaultCloseOperation(WindowConstants. DISPOSE_ON_CLOSE);
		panel.setPreferredSize( new Dimension(500,500));

		if(!controller.existenPlantas()) {
			JOptionPane.showConfirmDialog(null,"Aún no se registran Plantas en el Sistema.","Acción Interrumpida",
					JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		else {
			if(!controller.existenRutas())
				JOptionPane.showConfirmDialog(null,"Aún no se registran Rutas en el Sistema.","Advertencia",
						JOptionPane.DEFAULT_OPTION,
						JOptionPane.WARNING_MESSAGE);
		Double factor = getFactorAmortiguacion();
		if(factor != null && factor >= 0) {	
			List<Planta> plantas = controller.getPlantasPageRank(factor);
				table = new PageRanksTableModel();
				((PageRanksTableModel) table).agregarDatos(plantas);
				table.setFillsViewportHeight(true);

				JScrollPane scroll = new JScrollPane(table);
			
				panel.add(scroll);
				popup.setContentPane(panel);
				popup.pack();
				popup.setLocationRelativeTo(ventana);
				popup.setVisible(true);
			}
		
		else 
			if(factor == null){
				return;
			}
			else if(factor == -1.0)
				mostrarMenu();
		
		}
	}

	public static void mostrarFlujoMaximo() {
		if(controller.hayCaminoDisponible()){	
			Integer flujo = controller.flujoMaximoRed();

			if(flujo == 0) {
				JOptionPane.showConfirmDialog(null,"No existe flujo disponible.","Advertencia",
						JOptionPane.DEFAULT_OPTION,
						JOptionPane.WARNING_MESSAGE);
			}else {
				JOptionPane.showConfirmDialog(null,"El Flujo Máximo posible es de: "+flujo+" Toneladas.","Flujo en la Red Actual",
						JOptionPane.DEFAULT_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
		else {
			JOptionPane.showConfirmDialog(null,"No hay caminos disponibles.","Error",
					JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE);
		}
	}


}

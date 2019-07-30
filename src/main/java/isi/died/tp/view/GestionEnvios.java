package isi.died.tp.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
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
import isi.died.tp.controller.GestionLogisticaController;
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
		JLabel infoExtra = new JLabel(), infoExtra2 = new JLabel();
		GridBagConstraints constraints = new GridBagConstraints();
		JPanel panel = new JPanel(new GridBagLayout());
		JTable table;
		popup.setDefaultCloseOperation(WindowConstants. DISPOSE_ON_CLOSE);
		panel.setPreferredSize( new Dimension(1000,350));

		if(!controller.existenPlantasAcopio()) {
			JOptionPane.showConfirmDialog(null,"El Sistema no registra ambas Plantas de Acopio.","Acción Interrumpida",
					JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		if(!controller.hayCaminoDisponible()) {
			JOptionPane.showConfirmDialog(null,"No se registran caminos entre las Plantas de Acopio.","Acción Interrumpida",
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
				constraints.insets=new Insets(5, 5, 20, 5);
				constraints.gridx=0;
				constraints.gridy=0;
				constraints.gridheight=1;
				constraints.gridwidth=3;
				constraints.weightx=1;

				List<Planta> plantas = controller.getPlantasPageRank(factor);
				table = new PageRanksTableModel();
				((PageRanksTableModel) table).agregarDatos(plantas);
				table.setFillsViewportHeight(true);

				JScrollPane scroll = new JScrollPane(table,
						JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
						JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				scroll.setPreferredSize( new Dimension(420,200));
				panel.add(scroll, constraints);


				constraints.insets.set(0,0,5,0);
				constraints.gridx=0;
				constraints.gridy=1;
				constraints.gridheight=1;
				constraints.gridwidth=6;
				constraints.anchor=GridBagConstraints.SOUTH;
				infoExtra.setFont(new Font(infoExtra.getFont().getName(), infoExtra.getFont().getStyle(), 11));
				infoExtra.setText("*El Page Rank refiere a la importancia de una Planta respecto a las demás."
						+ " Es proporcional a la cantidad de caminos que llegan a ella. ");
				panel.add(infoExtra,constraints);

				constraints.insets.set(0,0,5,0);
				constraints.gridx=0;
				constraints.gridy=2;
				constraints.gridheight=1;
				constraints.gridwidth=6;
				constraints.anchor=GridBagConstraints.SOUTH;
				infoExtra2.setFont(new Font(infoExtra.getFont().getName(), infoExtra.getFont().getStyle(), 11));
				infoExtra2.setText("*El Factor de Transición/Amortiguación representa la probabilidad que existe para, desde"
						+ " cada Planta, desplazarse a otra Planta por uno de los caminos disponibles.");
				panel.add(infoExtra2,constraints);
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
				JOptionPane.showConfirmDialog(null,"No se registra flujo disponible entre ambas Plantas de Acopio.","Advertencia",
						JOptionPane.DEFAULT_OPTION,
						JOptionPane.WARNING_MESSAGE);
			}else {
				mostrarGrafoFlujo(flujo);
			}
		}
		else {

			if(!controller.existenPlantasAcopio()) {
				JOptionPane.showConfirmDialog(null,"El Sistema no registra ambas Plantas de Acopio.","Acción Interrumpida",
						JOptionPane.DEFAULT_OPTION,
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			else
				JOptionPane.showConfirmDialog(null,"No se registran Caminos entre las Plantas de Acopio.","Error",
						JOptionPane.DEFAULT_OPTION,
						JOptionPane.ERROR_MESSAGE);
		}
	}

	private static void mostrarGrafoFlujo(int flujo) {
		JFrame popup = new JFrame("Flujo Máximo en la Red Actual");
		JPanel panel = new JPanel(new BorderLayout());
		popup.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		popup.setPreferredSize(new Dimension(800,450));
		panel.setPreferredSize( new Dimension(700,450));
		GestionEnviosController.grafoController.marcarFlujoActual();
		panel.add(GestionEnviosController.grafoPanel, BorderLayout.CENTER);
		popup.setContentPane(panel);
		popup.pack();
		popup.setLocationRelativeTo(ventana);
		popup.setVisible(true);
		popup.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
        		GestionEntidadesController.plantaController.resetFlujo();
            }
        } );

		JOptionPane.showConfirmDialog(null,"El Flujo Máximo entre ambas Plantas de Acopio es de: "+flujo+" Toneladas.","Flujo en la Red Actual",
				JOptionPane.DEFAULT_OPTION,
				JOptionPane.INFORMATION_MESSAGE);



	}
}
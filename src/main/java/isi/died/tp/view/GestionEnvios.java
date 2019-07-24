package isi.died.tp.view;

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
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.JOptionPane;

import isi.died.tp.app.Main;
import isi.died.tp.controller.GestionEntidadesController;
import isi.died.tp.controller.GestionEnviosController;
import isi.died.tp.controller.OpcionesMenu;
import isi.died.tp.model.Planta;
import isi.died.tp.view.table.PlantaTableModel;

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
		constraints.insets.set(0, 170, 20, 5);
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
				controller.opcion(OpcionesMenu.FLUJO_MAXIMO);
			}
		});

		mejorSeleccion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.opcion(OpcionesMenu.MEJOR_SELECCION_ENVIO);
			}
		});

		pageRanks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarTablaPageRanks();
			}
		});

		//boton volver
		constraints.fill=GridBagConstraints.NONE;
		constraints.anchor=GridBagConstraints.SOUTH;
		constraints.insets.set(50, 10, 5, 5);
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


	private static void mostrarTablaPageRanks() {
		JFrame popup = new JFrame("Page Rank de Plantas en la Red Actual");
		JPanel panel = new JPanel(new GridBagLayout());

		List<Planta> plantas = GestionEntidadesController.plantaController.generarPageRanks();

		if(plantas.isEmpty()) {
			JOptionPane.showConfirmDialog(null,"Aún no existen Plantas en el Sistema.","Acción Interrumpida",
			JOptionPane.DEFAULT_OPTION,
			JOptionPane.ERROR_MESSAGE);
		}else {

			PlantaTableModel tmodel = new PlantaTableModel(plantas);
			JTable table = new JTable(tmodel);
			GridBagConstraints constraints = new GridBagConstraints();
			table.setFillsViewportHeight(true);
			JScrollPane scroll = new JScrollPane(table);
			constraints.anchor=GridBagConstraints.CENTER;
			panel.add(scroll);
			popup.setContentPane(panel);
			popup.pack();
			popup.setLocationRelativeTo(ventana);
			popup.setVisible(true);
		}
	}


}

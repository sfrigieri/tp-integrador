package isi.died.tp.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

import isi.died.tp.controller.GestionEntidadesController;
import isi.died.tp.controller.GestionEnviosController;
import isi.died.tp.controller.GestionLogisticaController;
import isi.died.tp.controller.GrafoPlantaController;
import isi.died.tp.estructuras.Recorrido;
import isi.died.tp.model.Insumo;
import isi.died.tp.model.Planta;
import isi.died.tp.model.PlantaProduccion;
import isi.died.tp.view.GrafoPanel;

public class RedPlantasView {
	private static GestionLogisticaController glc;
	public static GrafoPlantaController grafoController;
	private static JFrame ventana;
	private static GrafoPanel grafoView;

	public RedPlantasView(JFrame v,GestionLogisticaController glcontroller ) {
		glc = glcontroller;
		ventana = v;
		grafoView = GestionLogisticaController.grafoPanel;
		grafoController = GestionLogisticaController.grafoController;
	}

	public void setRedPlantas(Boolean firstTime) {
		JPanel panel = new JPanel(new BorderLayout()), panelInterior = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		JComboBox<String> seleccionarInsumo = new JComboBox<String>();
		JLabel info = new JLabel(" ");
		JButton volver = new JButton("Volver"), mejorCamino = new JButton("Buscar Mejor Camino");
		List<Insumo> lista = new ArrayList<Insumo>();

		constraints.fill=GridBagConstraints.HORIZONTAL;

		lista.addAll(glc.listaInsumos());
		seleccionarInsumo.addItem("Seleccione");
		for (Insumo ins : lista) {
			if(ins.getStock() != null)
				seleccionarInsumo.addItem(Integer.toString(ins.getId()) + ": Desc: " + ins.getDescripcion()+ " | Tipo: General | Cant. Disponible: "+ins.getStock().getCantidad()+" ");
			else
				seleccionarInsumo.addItem(Integer.toString(ins.getId()) + ": Desc: " + ins.getDescripcion()+ " | Tipo: General | Cant. Disponible: 0 ");
		}
		lista.addAll(glc.listaInsumosLiquidos());
		for (Insumo ins : glc.listaInsumosLiquidos()) {
			if(ins.getStock() != null)
				seleccionarInsumo.addItem(Integer.toString(ins.getId()) + ": Desc: " + ins.getDescripcion() + " | Tipo: Líquido | Cant. Disponible: "+ins.getStock().getCantidad()+" ");
			else
				seleccionarInsumo.addItem(Integer.toString(ins.getId()) + ": Desc: " + ins.getDescripcion()+ " | Tipo: Líquido | Cant. Disponible: 0 ");
		}
		if(lista.isEmpty())
			seleccionarInsumo.setEnabled(false);

		seleccionarInsumo.setSelectedItem("Seleccione");
		constraints.gridx=0;
		constraints.gridy=2;

		panelInterior.add(seleccionarInsumo,constraints);

		List<Insumo> seleccionado = new ArrayList<Insumo>();
		seleccionarInsumo.addActionListener (a -> {
			switch(seleccionarInsumo.getSelectedIndex()){
			case 0:
				mejorCamino.setEnabled(false);
				seleccionado.clear();
				glc.refrescarGrafo();
				info.setText(" ");
				break;
			default:
				glc.refrescarGrafo();
				seleccionado.clear();
				if(glc.buscarFaltante(lista.get(seleccionarInsumo.getSelectedIndex()-1))) {
					info.setText("          El Sistema registra faltantes de Stock.");
					seleccionado.add(lista.get(seleccionarInsumo.getSelectedIndex()-1));
					mejorCamino.setEnabled(true);
				}else {
					info.setText(" ");
					mejorCamino.setEnabled(false);
				}
				break;
			}
		});
		constraints.gridx=1;
		constraints.gridy=2;
		mejorCamino.addActionListener(a -> {
			if(!glc.buscarMejorCamino(seleccionado.get(0)))
				JOptionPane.showConfirmDialog(ventana, "El Sistema no registra un Camino que incluya a todas las Plantas.", "Información",
						JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
			mejorCamino.setEnabled(false);
			seleccionado.clear();
		});
		constraints.insets.set(0, 20, 0,0);
		mejorCamino.setEnabled(false);
		volver.addActionListener(a -> GestionLogistica.mostrarMenu());
		constraints.anchor=GridBagConstraints.EAST;

		constraints.insets.set(0, 30, 0,0);
		panelInterior.add(mejorCamino,constraints);
		constraints.gridx=2;
		constraints.gridy=2;
		panelInterior.add(volver,constraints);
		constraints.anchor=GridBagConstraints.NORTHEAST;
		info.setForeground(Color.red);
		constraints.gridx=1;
		constraints.gridy=1;
		constraints.insets=new Insets(0, 0, 5, 0);
		panelInterior.add(info,constraints);
		panelInterior.setPreferredSize(new Dimension(900,50));


		grafoController.setPlantas();
		grafoController.setRutas();

		panel.add(new JLabel("Reubicar Plantas: Clic + Arrastrar  | Clic derecho para demás opciones."), BorderLayout.NORTH);
		panel.add(grafoView, BorderLayout.CENTER);
		panel.add(panelInterior, BorderLayout.SOUTH);
		ventana.setContentPane(panel);
		ventana.pack();
		ventana.setSize(900, 600);

		ventana.setVisible(true);

		if(firstTime && !lista.isEmpty())
			JOptionPane.showConfirmDialog(ventana, "Seleccione un Insumo para verificar si existen faltantes.", "Información", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
		else
			if(lista.isEmpty())
				JOptionPane.showConfirmDialog(ventana, "No se registran Insumos en el Sistema.", "Información",
						JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
	}

	public void buscarCaminos(Boolean firstTime) {
		JPanel panel = new JPanel(new BorderLayout()), panelInterior = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		JLabel info = new JLabel(" ");
		JComboBox<String> seleccionarP1 = new JComboBox<String>(), seleccionarP2 = new JComboBox<String>();
		JButton volver = new JButton("Volver"), buscarCaminos = new JButton("Buscar Caminos");
		List<Planta> lista = new ArrayList<Planta>();

		constraints.fill=GridBagConstraints.HORIZONTAL;

		lista.addAll(glc.listaPlantas());
		seleccionarP1.addItem("Seleccione");
		for (Planta p : lista) {
			seleccionarP1.addItem(Integer.toString(p.getId()) + " | Nombre: " + p.getNombre()+" ");				
		}

		if(lista.isEmpty())
			seleccionarP1.setEnabled(false);

		seleccionarP2.addItem("Seleccione");
		for (Planta p : lista) {	
			seleccionarP2.addItem(Integer.toString(p.getId()) + " | Nombre: " + p.getNombre()+" ");		
		}

		if(lista.isEmpty())
			seleccionarP2.setEnabled(false);

		seleccionarP1.setSelectedItem("Seleccione");
		constraints.gridx=0;
		constraints.gridy=2;
		panelInterior.add(seleccionarP1,constraints);

		constraints.gridx=1;
		constraints.insets.set(0,10, 0,0);
		panelInterior.add(seleccionarP2,constraints);

		List<Planta> seleccionadoP1 = new ArrayList<Planta>();
		List<Planta> seleccionadoP2 = new ArrayList<Planta>();
		seleccionarP1.addActionListener (a -> {
			switch(seleccionarP1.getSelectedIndex()){
			case 0:
				buscarCaminos.setEnabled(false);
				seleccionadoP1.clear();
				info.setText(" ");
				glc.refrescarGrafo();
				break;
			default:
				glc.refrescarGrafo();
				seleccionadoP1.clear();
				info.setText(" ");
				seleccionadoP1.add(lista.get(seleccionarP1.getSelectedIndex()-1));
				if(!seleccionadoP2.isEmpty() && !seleccionadoP1.get(0).equals(seleccionadoP2.get(0))) {
					buscarCaminos.setEnabled(true);
				}else {
					buscarCaminos.setEnabled(false);
				}
				break;
			}
		});

		seleccionarP2.addActionListener (a -> {
			switch(seleccionarP2.getSelectedIndex()){
			case 0:
				buscarCaminos.setEnabled(false);
				seleccionadoP2.clear();
				info.setText(" ");
				glc.refrescarGrafo();
				break;
			default:
				glc.refrescarGrafo();
				seleccionadoP2.clear();
				info.setText(" ");
				seleccionadoP2.add(lista.get(seleccionarP2.getSelectedIndex()-1));
				if(!seleccionadoP1.isEmpty() && !seleccionadoP2.get(0).equals(seleccionadoP1.get(0))) {
					buscarCaminos.setEnabled(true);
				}else {
					buscarCaminos.setEnabled(false);
				}
				break;
			}
		});

		buscarCaminos.addActionListener(a -> {
			List<List<JLabel>> caminos = glc.buscarCaminos(seleccionadoP1.get(0), seleccionadoP2.get(0));
			if(caminos.isEmpty())
				JOptionPane.showConfirmDialog(ventana, "El Sistema no registra Caminos que conecten ambas Plantas.", "Información",
						JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
			else {
				mostrarCaminosInfo(caminos);
				info.setText("   * Las Rutas en color BLANCO son aquellas asociadas a más de 1 Camino");
				buscarCaminos.setEnabled(false);
				//seleccionadoP1.clear();
				//seleccionadoP2.clear();
				//seleccionarP1.setSelectedItem("Seleccione");
				//seleccionarP2.setSelectedItem("Seleccione");
			}
		});

		buscarCaminos.setEnabled(false);
		constraints.insets.set(0, 20, 0,0);
		constraints.gridx=2;
		volver.addActionListener(a -> GestionLogistica.mostrarMenu());
		constraints.anchor=GridBagConstraints.EAST;

		constraints.insets.set(0, 30, 0,0);
		panelInterior.add(buscarCaminos,constraints);
		constraints.gridx=3;
		panelInterior.add(volver,constraints);
		panelInterior.setPreferredSize(new Dimension(900,30));
		
		info.setFont(new Font(info.getFont().getName(), info.getFont().getStyle(), 15));
		panel.add(info, BorderLayout.NORTH);

		grafoController.setPlantas();
		grafoController.setRutas();

		panel.add(grafoView, BorderLayout.CENTER);
		panel.add(panelInterior, BorderLayout.SOUTH);
		ventana.setContentPane(panel);
		ventana.pack();
		ventana.setSize(900, 600);

		ventana.setVisible(true);

		if(firstTime && !lista.isEmpty())
			JOptionPane.showConfirmDialog(ventana, "Seleccione Planta Inicial y Final para la búsqueda de Caminos.", "Información", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
		else
			if(lista.isEmpty())
				JOptionPane.showConfirmDialog(ventana, "No se registran Plantas en el Sistema.", "Información",
						JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);

	}

	private static void mostrarCaminosInfo(List<List<JLabel>> caminos) {
		JFrame popup = new JFrame("Información de Caminos Disponibles");
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		popup.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		popup.setPreferredSize(new Dimension(400,70+caminos.size()*20));
		panel.setPreferredSize( new Dimension(400,70+caminos.size()*20));
		int ycord = 2;
		constraints.gridx=2;
		constraints.gridy=1;
		panel.add(new JLabel("    Distancia       Duración      Peso Máximo"), constraints);
		for(List<JLabel> labels : caminos) {
			constraints.gridx=1;
			constraints.gridy=ycord;
			panel.add(labels.get(0), constraints);
			constraints.gridx=2;
			panel.add(labels.get(1), constraints);
			ycord++;
		}
		popup.setContentPane(panel);
		popup.pack();
		popup.setLocationRelativeTo(ventana);
		popup.setVisible(true);


	}
}

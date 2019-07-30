package isi.died.tp.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import isi.died.tp.controller.GestionLogisticaController;
import isi.died.tp.controller.GrafoPlantaController;
import isi.died.tp.model.Insumo;
import isi.died.tp.model.Planta;
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
		JButton volver = new JButton("Volver"), mejorCamino = new JButton("Buscar Mejor Camino para Envío");
		List<Insumo> lista = new ArrayList<Insumo>();

		constraints.fill=GridBagConstraints.HORIZONTAL;

		lista.addAll(glc.listaInsumos());
		seleccionarInsumo.addItem("Seleccione");
		for (Insumo ins : lista) {
			if(ins.getStock() != null)
				seleccionarInsumo.addItem(Integer.toString(ins.getId()) + ": Descripción: " + ins.getDescripcion()+ " | Tipo: General | Cantidad Disponible: "+ins.getStock().getCantidad()+" ");
			else
				seleccionarInsumo.addItem(Integer.toString(ins.getId()) + ": Descripción: " + ins.getDescripcion()+ " | Tipo: General | Cantidad Disponible: 0 ");
		}
		lista.addAll(glc.listaInsumosLiquidos());
		for (Insumo ins : glc.listaInsumosLiquidos()) {
			if(ins.getStock() != null)
				seleccionarInsumo.addItem(Integer.toString(ins.getId()) + ": Descripción: " + ins.getDescripcion() + " | Tipo: Líquido | Cantidad Disponible: "+ins.getStock().getCantidad()+" ");
			else
				seleccionarInsumo.addItem(Integer.toString(ins.getId()) + ": Descripción: " + ins.getDescripcion()+ " | Tipo: Líquido | Cantidad Disponible: 0 ");
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
				if(glc.buscarFaltante(lista.get(seleccionarInsumo.getSelectedIndex()-1))) {
					info.setText("          El Sistema registra faltantes de Stock.");
					seleccionado.add(lista.get(seleccionarInsumo.getSelectedIndex()-1));
					mejorCamino.setEnabled(true);
				}else {
					info.setText(" ");
					mejorCamino.setEnabled(false);
					seleccionado.clear();
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
		panelInterior.setPreferredSize(new Dimension(800,50));


		grafoController.setPlantas();
		grafoController.setRutas();

		panel.add(new JLabel("Reubicar Plantas: Clic + Arrastrar  | Clic derecho para demás opciones."), BorderLayout.NORTH);
		panel.add(grafoView, BorderLayout.CENTER);
		panel.add(panelInterior, BorderLayout.SOUTH);
		ventana.setContentPane(panel);
		ventana.pack();
		ventana.setSize(800, 600);

		ventana.setVisible(true);

		if(firstTime && !lista.isEmpty())
			JOptionPane.showConfirmDialog(ventana, "Seleccione un Insumo para verificar si existen faltantes.", "Información", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
		else
			if(lista.isEmpty())
			JOptionPane.showConfirmDialog(ventana, "No se registran Insumos en el Sistema.", "Información",
					JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
	}

}

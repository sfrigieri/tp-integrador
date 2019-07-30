package isi.died.tp.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
		JButton volver = new JButton("Volver");
		List<Insumo> lista = new ArrayList<Insumo>();
		
		constraints.fill=GridBagConstraints.HORIZONTAL;
		//constraints.anchor=GridBagConstraints.WEST;

		lista.addAll(glc.listaInsumos());
		seleccionarInsumo.addItem("Seleccione");
		for (Insumo ins : lista) {
			if(ins.getStock() != null)
			seleccionarInsumo.addItem(Integer.toString(ins.getId()) + " : Descripción:  " + ins.getDescripcion()+ "       |       Tipo: General       |       Cantidad Disponible: "+ins.getStock().getCantidad()+"       ");
			else
				seleccionarInsumo.addItem(Integer.toString(ins.getId()) + " : Descripción:  " + ins.getDescripcion()+ "       |       Tipo: General       |       Cantidad Disponible: 0       ");
		}
		lista.addAll(glc.listaInsumosLiquidos());
		for (Insumo ins : glc.listaInsumosLiquidos()) {
			if(ins.getStock() != null)
			seleccionarInsumo.addItem(Integer.toString(ins.getId()) + " : Descripción:  " + ins.getDescripcion() + "       |       Tipo: Líquido       |       Cantidad Disponible: "+ins.getStock().getCantidad()+"       ");
			else
				seleccionarInsumo.addItem(Integer.toString(ins.getId()) + " : Descripción:  " + ins.getDescripcion()+ "       |       Tipo: Líquido       |       Cantidad Disponible: 0       ");
		}
		
		seleccionarInsumo.setSelectedItem("Seleccione");
		panelInterior.add(seleccionarInsumo,constraints);
		
		
		seleccionarInsumo.addActionListener (a -> {
			switch(seleccionarInsumo.getSelectedIndex()){
			case 0:
				glc.refrescarGrafo();
				break;
			default:
				glc.buscarFaltante(lista.get(seleccionarInsumo.getSelectedIndex()-1));
				break;
			}
		});
		
		
		volver.addActionListener(a -> GestionLogistica.mostrarMenu());
		constraints.anchor=GridBagConstraints.EAST;

		constraints.insets.set(0, 240, 0,0);
		panelInterior.add(volver,constraints);
		panelInterior.setPreferredSize(new Dimension(800,30));


		grafoController.setPlantas();
		grafoController.setRutas();

		panel.add(new JLabel("Clic en cada Planta para desplazar. Clic derecho: demás opciones."), BorderLayout.NORTH);
		panel.add(grafoView, BorderLayout.CENTER);
		panel.add(panelInterior, BorderLayout.SOUTH);
		ventana.setContentPane(panel);
		ventana.pack();
		ventana.setSize(800, 600);

		ventana.setVisible(true);

		if(firstTime)
			JOptionPane.showConfirmDialog(ventana, "Seleccione un Insumo para verificar si existen faltantes.", "Información", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
	}

}

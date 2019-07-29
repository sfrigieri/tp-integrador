package isi.died.tp.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import isi.died.tp.controller.GestionLogisticaController;
import isi.died.tp.controller.GrafoPlantaController;
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
	
	public void setRedPlantas() {
		JPanel panel = new JPanel(new BorderLayout());
		//grafoController.crearVertice(300, 200,Color.BLUE,p);
		//grafoView.setAuxiliarOrigen(grafoController.buscarVertice(p));
		grafoController.setPlantas();
		grafoController.setRutas();
		//panel.add(new JLabel("Doble click: agregar nodo, Click secundario: ver opciones, Arrastrar y soltar: agregar arista."), BorderLayout.NORTH);
		panel.add(grafoView, BorderLayout.CENTER);
		
		ventana.setContentPane(panel);
		ventana.pack();
		ventana.setSize(800, 600);
		
        ventana.setVisible(true);
	}

}

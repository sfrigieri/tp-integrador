package isi.died.tp.controller;

import javax.swing.JFrame;

import isi.died.tp.controller.GrafoPlantaController;
import isi.died.tp.view.GrafoPanel;
import isi.died.tp.view.RedPlantasView;

public class GestionLogisticaController {
	
	public static GrafoPlantaController grafoController;
	public static GrafoPanel grafoPanel;
	private static RedPlantasView redPlantasView;
	private static JFrame framePadre;
	
	public GestionLogisticaController(JFrame v) {
		grafoController = new GrafoPlantaController(v, this);
		grafoPanel = new GrafoPanel(v);
		grafoController.setGrafoPanel(grafoPanel);
		redPlantasView = new RedPlantasView(v, this);
		framePadre = v;
	}
	

	public void opcion(OpcionesMenuLogistica op) {
		switch(op) {
		case GESTION_RED_PLANTAS: redPlantasView.setRedPlantas(); break;
		//case MEJOR_CAMINO_ENVIO: grafoPanel.; break;
		//case BUSCAR_CAMINOS: grafoPanel.; break;
		//case AGREGAR_PLANTA_PRODUCCION: grafoPanel.; break;
		//case AGREGAR_RUTA: grafoPanel.; break;
		}
	}


	
}

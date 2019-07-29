package isi.died.tp.controller;

import javax.swing.JFrame;

import isi.died.tp.controller.GrafoPlantaController;
import isi.died.tp.view.GrafoPanel;
import isi.died.tp.view.MejorSeleccionEnvioView;
import isi.died.tp.view.RedPlantasView;

public class GestionLogisticaController {
	
	public static GrafoPlantaController grafoController;
	public static GrafoPanel grafoPanel;
	private RedPlantasView redPlantasView;
	
	public GestionLogisticaController(JFrame v) {
		grafoPanel = new GrafoPanel(v);
		grafoController = new GrafoPlantaController(v);
		redPlantasView = new RedPlantasView(v, this);
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

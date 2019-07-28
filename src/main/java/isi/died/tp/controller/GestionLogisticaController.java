package isi.died.tp.controller;

import javax.swing.JFrame;

import isi.died.tp.controller.GrafoPlantaController;
import isi.died.tp.view.GrafoPanel;

public class GestionLogisticaController {

	public static GrafoPlantaController grafoController;
	public static GrafoPanel grafoPanel;
	
	public GestionLogisticaController(JFrame v) {
		grafoPanel = new GrafoPanel(v);
		grafoController = new GrafoPlantaController(grafoPanel);
	}
	

	public void opcion(OpcionesMenuLogistica op) {
		switch(op) {
		//case AGREGAR_PLANTA_PRODUCCION: grafoPanel.; break;
		//case AGREGAR_RUTA: grafoPanel.; break;
		}
	}


	
}

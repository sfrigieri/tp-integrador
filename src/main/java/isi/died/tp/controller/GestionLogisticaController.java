package isi.died.tp.controller;

import java.util.List;

import javax.swing.JFrame;

import isi.died.tp.controller.GrafoPlantaController;
import isi.died.tp.view.GrafoPanel;
import isi.died.tp.view.RedPlantasView;
import isi.died.tp.model.Insumo;
public class GestionLogisticaController {
	
	public static GrafoPlantaController grafoController;
	public static GrafoPanel grafoPanel;
	public static InsumoController ic;
	private static RedPlantasView redPlantasView;
	private static JFrame framePadre;
	private Boolean firstTime; 
	
	public GestionLogisticaController(JFrame v) {
		framePadre = v;
		firstTime = true;
		ic = GestionEntidadesController.insumoController;
	}
	

	public void opcion(OpcionesMenuLogistica op) {
		switch(op) {
		case GESTION_RED_PLANTAS: 
			grafoController = new GrafoPlantaController(framePadre, this);
			grafoPanel = new GrafoPanel(framePadre);
			grafoController.setGrafoPanel(grafoPanel);
			redPlantasView = new RedPlantasView(framePadre, this);
			redPlantasView.setRedPlantas(firstTime); 
			firstTime = false; 
			break;
		//case MEJOR_CAMINO_ENVIO: grafoPanel.; break;
		//case BUSCAR_CAMINOS: grafoPanel.; break;
		//case AGREGAR_PLANTA_PRODUCCION: grafoPanel.; break;
		//case AGREGAR_RUTA: grafoPanel.; break;
		}
	}


	public List<Insumo> listaInsumos() {
		return ic.listaInsumos();
	}


	public List<Insumo> listaInsumosLiquidos() {
		return ic.listaInsumosLiquidos();
	}


	public void refrescarGrafo() {
		grafoController.desmarcarPlantas();
	}


	public void buscarFaltante(Insumo insumo) {
		grafoController.marcarPlantas(insumo);
		
	}


	
}

package isi.died.tp.controller;

import java.util.List;

import javax.swing.JFrame;

import isi.died.tp.model.Camion;
import isi.died.tp.model.Planta;
import isi.died.tp.model.PlantaAcopio;
import isi.died.tp.model.StockAcopio;
import isi.died.tp.view.GestionEnvios;
import isi.died.tp.view.MejorSeleccionEnvioView;

public class GestionEnviosController {

	private static PlantaController pc;
	private static CamionController cc;
	private MejorSeleccionEnvioView mseView;
	
	public GestionEnviosController(JFrame ventana) {
		pc = GestionEntidadesController.plantaController;
		cc = GestionEntidadesController.camionController;
		mseView = new MejorSeleccionEnvioView(ventana, this);
	}


	public void opcion(OpcionesMenuEnvios op) {
		switch(op) {
		case FLUJO_MAXIMO: GestionEnvios.mostrarFlujoMaximo(); break;
		case PAGE_RANKS: GestionEnvios.mostrarTablaPageRanks(); break;
		case MEJOR_SELECCION_ENVIO: mseView.calcularMejorSeleccion(true); break;
		}
	}
	

	public Camion buscarCamion(int id) {
		return cc.buscarCamion(id);
	}

	public List<Camion> listaCamiones() {
		return cc.listaCamiones();
	}
	
	public List<StockAcopio> generarStockFaltante() {
		return pc.generarStockFaltante();
	}

	public List<Planta> getPlantasPageRank(Double factor) {
		List<Planta> plantas = pc.generarPageRanks(factor);
		return plantas;
	}
	
	
	public Boolean hayCaminoDisponible() {
		PlantaAcopio inicial = pc.buscarAcopioInicial();
		PlantaAcopio fin = pc.buscarAcopioFinal();
		if(inicial != null && fin != null) {
			//System.out.println(inicial.getNombre()+" "+fin.getNombre());
			if(pc.existeCamino(inicial, fin))
				return true;
		}	
		return false;
	}


	public Integer flujoMaximoRed() {
		return pc.flujoMaximoRed(pc.buscarAcopioInicial());
	}

}

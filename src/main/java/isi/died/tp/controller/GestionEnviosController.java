package isi.died.tp.controller;

import java.util.List;
import java.util.stream.Collectors;

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


	public Boolean existenPlantas() {
		return pc.existenPlantas();
	}


	public boolean existenCamiones() {
		return cc.existenCamiones();
	}



	public List<StockAcopio> generarMejorSeleccionEnvio(Camion camion, List<StockAcopio> stocks) {
		return pc.generarMejorSeleccionEnvio(camion, stocks);
	}


	public List<StockAcopio> generarStockFaltanteDisponible() {
		return pc.generarStockFaltanteDisponible();
	}

	public List<StockAcopio> stockRemanente(List<StockAcopio> stockDisponible, List<StockAcopio> stockResultado) {
		List<StockAcopio> listaAux = stockDisponible.stream().collect(Collectors.toList());
		for(StockAcopio s1 : stockDisponible) {
			for(StockAcopio s2 : stockResultado)
				if(s2.getInsumo().equals(s1.getInsumo()) && s2.getPlanta().equals(s1.getPlanta()))
					listaAux.remove(s1);
		}
		listaAux.sort((s1,s2) -> Double.valueOf((s1.getCantidad()*s1.getInsumo().getPeso()))
				.compareTo(Double.valueOf((s2.getCantidad()*s2.getInsumo().getPeso()))));
		return listaAux;
	}


	public double costoTotalEnvio(List<StockAcopio> mejorSeleccion) {
		double costoTotal = 0;
		for(StockAcopio s : mejorSeleccion)
			costoTotal+= s.getCantidad()*s.getInsumo().getCosto();
		
		costoTotal = Math.round(costoTotal);
		
		return costoTotal;
	}


	public double pesoTotalEnvio(List<StockAcopio> mejorSeleccion) {
		double pesoTotal = 0;
		for(StockAcopio s : mejorSeleccion)
			pesoTotal+= s.getCantidad()*s.getInsumo().getPeso();
		
		pesoTotal = Math.round(pesoTotal);
		
		return pesoTotal;
	}


	public boolean existenRutas() {
		return pc.existenRutas();
	}


}

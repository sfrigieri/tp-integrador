package isi.died.tp.controller;

import java.util.List;

import isi.died.tp.estructuras.Recorrido;
import isi.died.tp.model.*;
import isi.died.tp.service.PlantaService;

public class PlantaController {

	private PlantaService ps;


	public PlantaController(PlantaService ps) {
		super();
		this.ps = ps;
	}


	public Planta agregarPlanta(Planta planta) {
		ps.agregarPlanta(planta);
		return planta;
	}


	public List<Planta> listaPlantas() {
		return ps.listaPlantas();
	}

	public List<PlantaAcopio> listaPlantasAcopio() {
		return ps.listaPlantasAcopio();
	}

	public List<PlantaProduccion> listaPlantasProduccion() {
		return ps.listaPlantasProduccion();
	}

	public void eliminarPlanta(Planta planta) {
		ps.eliminarPlanta(planta);
	}

	public void editarPlanta(Planta planta) {
		ps.editarPlanta(planta);
	}

	public Planta buscarPlantaAcopio(int id) {
		return ps.buscarPlantaAcopio(id);
	}

	public Planta buscarPlantaProduccion(int id) {
		return ps.buscarPlantaProduccion(id);
	}

	// Item 4.2 a)
	public List<PlantaProduccion> buscarPlantasNecesitanInsumo(Insumo ins) {
		return ps.buscarPlantasNecesitanInsumo(ins);
	}


	// Item 4.2 b)
	public Recorrido mejorCaminoEnvio(List<PlantaProduccion> plantas) {
		return ps.mejorCaminoEnvio(plantas);
	}


	// Item 4.3
	public List<Recorrido> buscarCaminosInfo(Planta p1, Planta p2) {
		return ps.buscarCaminosInfo(p1,p2);
	}


	// Item 5.1
	public int flujoMaximoRed(Planta origen){
		return ps.flujoMaximoRed(origen);
	}
	
	public void resetFlujo() {
		ps.resetFlujo();
	}


	// Item 5.2
	public List<Planta> generarPageRanks(Double factor) {
		return ps.generarPageRanks(factor);
	}


	public PlantaAcopio buscarAcopioInicial() {
		return ps.buscarAcopioInicial();
	}

	public PlantaAcopio buscarAcopioFinal() {
		return ps.buscarAcopioFinal();
	}


	//Item 5.3.a "EL sistema debe mostrar una lista de los insumos faltantes y cantidad"
	public List<StockAcopio> generarStockFaltante() {
		return ps.generarStockFaltante();
	}

	//Item 5.3.c Si se procede a seleccionar camión y generar solución,
	//deben generarse los pedidos posibles con el stock disponible en PlantaAcopio inicial
	public List<StockAcopio> generarStockFaltanteDisponible() {
		return ps.generarStockFaltanteDisponible();
	}

	//Item 5.3.c
	public List<StockAcopio> generarMejorSeleccionEnvio(Camion camion, List<StockAcopio> listaDisponibles) {

		return ps.generarMejorSeleccionEnvio(camion, listaDisponibles);
	}


	public boolean existeCamino(Planta inicial, Planta fin) {
		return ps.existeCamino(inicial, fin);
	}


	public Boolean existenPlantas() {
		return ps.existenPlantas();
	}


	public boolean existenRutas() {
		return ps.existenRutas();
	}

}

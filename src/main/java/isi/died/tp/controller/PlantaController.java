package isi.died.tp.controller;

import java.util.List;

import isi.died.tp.model.*;
import isi.died.tp.service.*;

public class PlantaController {

	private PlantaService ps;

	
	public PlantaController(PlantaService ps, InsumoService is, StockService ss) {
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
	
	public void eliminarPlanta(Planta planta) {
		ps.eliminarPlanta(planta);

	}
	
	public Planta buscarPlantaAcopio(int id) {
		return ps.buscarPlantaAcopio(id);
	}
	
	public Planta buscarPlantaProduccion(int id) {
		return ps.buscarPlantaProduccion(id);
	}

	//Item 3.a "EL sistema debe mostrar una lista de los insumos faltantes y cantidad"
	public List<StockAcopio> generarStockFaltante() {
		return ps.generarStockFaltante();
	}
	
	//Item 3.c Si se procede a seleccionar camión y generar solución,
	//deben generarse los pedidos posibles con el stock disponible en PlantaAcopio inicial
	public List<StockAcopio> generarStockFaltanteDisponible() {
		return ps.generarStockFaltanteDisponible();
	}
	
	//Item 3.c
	public List<StockAcopio> generarMejorSeleccionEnvio(Camion camion, List<StockAcopio> listaDisponibles) {
		
		return ps.generarMejorSeleccionEnvio(camion, listaDisponibles);
	}

}

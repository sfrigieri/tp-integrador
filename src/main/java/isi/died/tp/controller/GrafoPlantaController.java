package isi.died.tp.controller;

import java.util.List;

import isi.died.tp.model.*;
import isi.died.tp.service.*;

public class GrafoPlantaController {

	private PlantaService ps;
	
	public GrafoPlantaController() {
		super();
		this.ps = new PlantaServiceDefault();
	}

	public List<StockAcopio> generarMejorSeleccionEnvio(Camion camion, List<StockAcopio> listaDisponibles) {
		
		return ps.generarMejorSeleccionEnvio(camion, listaDisponibles);
	}
	
}

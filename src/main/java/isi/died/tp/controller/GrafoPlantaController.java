package isi.died.tp.controller;

import java.util.List;

import isi.died.tp.model.*;
import isi.died.tp.service.*;

public class GrafoPlantaController {

	private GrafoPlantaService gps;
	
	public GrafoPlantaController() {
		super();
		this.gps = new GrafoPlantaServiceDefault();
	}

	public List<StockAcopio> generarMejorSeleccionEnvio(Camion camion, List<StockAcopio> listaDisponibles) {
		
		return gps.generarMejorSeleccionEnvio(camion, listaDisponibles);
	}
	
}

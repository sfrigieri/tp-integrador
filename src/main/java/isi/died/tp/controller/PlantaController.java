package isi.died.tp.controller;

import java.util.List;

import isi.died.tp.model.*;
import isi.died.tp.service.*;

public class PlantaController {

	private PlantaService ps;
	private InsumoService is;
	private StockService ss;
	
	public PlantaController(InsumoService is, StockService ss) {
		super();
		this.ps = new PlantaServiceDefault(is, ss);
		this.is = is;
		this.ss = ss;
	}

	public List<StockAcopio> generarMejorSeleccionEnvio(Camion camion, List<StockAcopio> listaDisponibles) {
		
		return ps.generarMejorSeleccionEnvio(camion, listaDisponibles);
	}
	
	
	
	public Planta agregarPlanta(Planta planta) {
		ps.agregarPlanta(planta);
		return planta;
	}
	
	
	public List<Planta> listaPlantas() {
		return ps.listaPlantas();
	}

	
	public void editarPlanta(Integer id, Planta planta) {
		ps.editarPlanta(id, planta);

	}

	
	public void eliminarPlanta(Planta planta) {
		ps.eliminarPlanta(planta);

	}

	
	public Planta buscarPlanta(Integer id) {
		return ps.buscarPlanta(id);
	}
}

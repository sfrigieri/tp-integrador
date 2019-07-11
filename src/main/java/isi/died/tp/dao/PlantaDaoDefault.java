package isi.died.tp.dao;

import isi.died.tp.dao.util.CsvSource;
import isi.died.tp.estructuras.*;
import isi.died.tp.model.*;

public class PlantaDaoDefault implements PlantaDao {

	private static Integer SECUENCIA_ID;
	private static GrafoPlanta GRAFO_PLANTA  = new GrafoPlanta();

	private CsvSource dataSource;

	public PlantaDaoDefault() {
		super();
	}
	
	@Override
	public int buscarProximoFlujoDisponible(Vertice<Planta> origen) {
		return GRAFO_PLANTA.buscarProximoFlujoDisponible(origen);
	}
	
	@Override
	public void resetFlujo() {
		GRAFO_PLANTA.resetFlujo();
	}
	
}

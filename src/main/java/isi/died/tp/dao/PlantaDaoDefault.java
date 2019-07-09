package isi.died.tp.dao;

import isi.died.tp.dao.util.CsvSource;
import isi.died.tp.estructuras.Grafo;
import isi.died.tp.model.Planta;

public class PlantaDaoDefault implements PlantaDao {

	private static Integer SECUENCIA_ID;
	private static Grafo<Planta> GRAFO_PLANTA  = new Grafo<Planta>();

	private CsvSource dataSource;

	public PlantaDaoDefault() {
		super();
	}
	
}

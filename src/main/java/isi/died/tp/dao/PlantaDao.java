package isi.died.tp.dao;

import isi.died.tp.estructuras.Vertice;
import isi.died.tp.model.Planta;

public interface PlantaDao {

	public int buscarProximoFlujoDisponible(Vertice<Planta> origen);

	public void resetFlujo();

}

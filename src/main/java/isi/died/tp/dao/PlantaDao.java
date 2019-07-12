package isi.died.tp.dao;

import java.util.List;

import isi.died.tp.estructuras.Vertice;
import isi.died.tp.model.Planta;
import isi.died.tp.model.PlantaAcopio;
import isi.died.tp.model.PlantaProduccion;

public interface PlantaDao {

	public int buscarProximoFlujoDisponible(Vertice<Planta> origen);

	public List<Planta> listaPlantas();

	public Planta findById(Integer id);

	public boolean existeArista(Integer idOrigen, Integer idDestino);

	public void agregarPlanta(Planta planta);

	public void resetFlujo();

	public List<PlantaAcopio> listaPlantasAcopio();

	public List<PlantaProduccion> listaPlantasProduccion();

	public void editarPlanta(Integer id, Planta planta);

	public void eliminarPlanta(Planta planta);



}

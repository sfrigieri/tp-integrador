package isi.died.tp.dao;

import java.util.List;

import isi.died.tp.estructuras.Arista;
import isi.died.tp.estructuras.Recorrido;
import isi.died.tp.estructuras.Ruta;
import isi.died.tp.estructuras.Vertice;
import isi.died.tp.model.Insumo;
import isi.died.tp.model.Planta;
import isi.died.tp.model.PlantaAcopio;
import isi.died.tp.model.PlantaProduccion;
import isi.died.tp.model.StockProduccion;
import isi.died.tp.service.RutaService;

public interface PlantaDao {

	public int buscarProximoFlujoDisponible(Vertice<Planta> origen);

	public List<Planta> listaPlantas();

	public void agregarPlanta(Planta planta);

	public void resetFlujo();

	public List<PlantaAcopio> listaPlantasAcopio();

	public List<PlantaProduccion> listaPlantasProduccion();

	public void editarPlanta(Planta planta);

	public void eliminarPlanta(Planta planta);

	public double generarNuevoPageRank(Planta p);

	public void resetPageRanks();

	public PlantaProduccion buscarPlantaProduccion(Integer id);

	public PlantaAcopio buscarPlantaAcopio(Integer id);

	public void setRutas(List<Arista<Planta>> lista);

	public List<Arista<Planta>> listaRutas();

	public void addInsumos(List<Insumo> lista);

	public PlantaAcopio buscarAcopioInicial();

	public List<Recorrido> buscarCaminosInfo(Planta p1, Planta p2);

	public Boolean necesitaInsumo(Integer id, Insumo ins);

	public PlantaAcopio buscarAcopioFinal();


}

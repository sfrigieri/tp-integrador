package isi.died.tp.service;

import java.util.List;

import isi.died.tp.estructuras.Vertice;
import isi.died.tp.model.Camion;
import isi.died.tp.model.Planta;
import isi.died.tp.model.PlantaProduccion;
import isi.died.tp.model.StockAcopio;

public interface PlantaService {

	public List<StockAcopio> generarMejorSeleccionEnvio(Camion camion, List<StockAcopio> listaDisponibles);

	public int flujoMaximoRed(Vertice<Planta> origen);

	public Planta agregarPlanta(Planta planta);

	public List<Planta> listaPlantas();

	public void editarPlanta(Integer id, Planta planta);

	public void eliminarPlanta(Planta planta);

	public Planta buscarPlanta(Integer id);

	public List<PlantaProduccion> listaPlantasProduccion();

	public List<StockAcopio> generarStockFaltante();

	public void setInsumoService(InsumoService insumoService);

	public void setStockService(StockService stockService);

	public List<StockAcopio> generarStockFaltanteDisponible();

}

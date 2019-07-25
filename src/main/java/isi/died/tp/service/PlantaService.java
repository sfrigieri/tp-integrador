package isi.died.tp.service;

import java.util.List;

import isi.died.tp.estructuras.Recorrido;
import isi.died.tp.estructuras.Ruta;
import isi.died.tp.model.*;

public interface PlantaService {

	public List<StockAcopio> generarMejorSeleccionEnvio(Camion camion, List<StockAcopio> listaDisponibles);

	public int flujoMaximoRed(Planta origen);

	public Planta agregarPlanta(Planta planta);

	public List<Planta> listaPlantas();

	public void editarPlanta(Planta planta);

	public void eliminarPlanta(Planta planta);

	public PlantaProduccion buscarPlantaProduccion(Integer id);
	
	public PlantaAcopio buscarPlantaAcopio(Integer id);

	public List<PlantaProduccion> listaPlantasProduccion();
	
	public List<PlantaAcopio> listaPlantasAcopio();

	public List<StockAcopio> generarStockFaltante();

	public void setInsumoService(InsumoService insumoService);

	public void setStockService(StockService stockService);

	public List<StockAcopio> generarStockFaltanteDisponible();

	public List<Planta> generarPageRanks(Double factor);

	public void setRutas(List<Ruta> lista);

	public void setRutaService(RutaService ss);

	public void addInsumos(List<Insumo> lista);

	public void setStocksProduccion(List<StockProduccion> lista);
	
	public List<Recorrido> buscarCaminosInfo(Planta p1,Planta p2);

	public PlantaAcopio buscarAcopioInicial();

	public PlantaAcopio buscarAcopioFinal();

	public List<PlantaProduccion> buscarPlantasNecesitanInsumo(Insumo ins);

	public Recorrido mejorCaminoEnvio(List<PlantaProduccion> plantas);

	public Boolean existeCamino(Planta p1, Planta p2);

	public Boolean existenPlantas();


}

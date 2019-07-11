package isi.died.tp.service;

import java.util.List;

import isi.died.tp.estructuras.Vertice;
import isi.died.tp.model.Camion;
import isi.died.tp.model.Planta;
import isi.died.tp.model.StockAcopio;

public interface PlantaService {

	public List<StockAcopio> generarMejorSeleccionEnvio(Camion camion, List<StockAcopio> listaDisponibles);

	public int flujoMaximoRed(Vertice<Planta> origen);

}

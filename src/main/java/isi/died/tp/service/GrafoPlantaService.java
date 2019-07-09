package isi.died.tp.service;

import java.util.List;

import isi.died.tp.model.Camion;
import isi.died.tp.model.StockAcopio;

public interface GrafoPlantaService {

	List<StockAcopio> generarMejorSeleccionEnvio(Camion camion, List<StockAcopio> listaDisponibles);

}

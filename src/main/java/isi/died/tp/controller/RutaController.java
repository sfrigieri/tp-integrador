package isi.died.tp.controller;

import isi.died.tp.estructuras.Ruta;
import isi.died.tp.estructuras.Vertice;
import isi.died.tp.model.Planta;
import isi.died.tp.service.PlantaService;
import isi.died.tp.service.RutaService;
import isi.died.tp.service.RutaServiceDefault;

public class RutaController {

private RutaService rutaService;
	
	public RutaController(PlantaService ps, RutaService rs) {
		this.rutaService = rs;
	}
	
	public Ruta agregarRuta(int idRuta, Planta plantaOrigen, Planta plantaFin, int distancia, double duracion, int pesoMax) {
		Vertice<Planta> vertPlantaOrigen = new Vertice<Planta>(plantaOrigen);
		Vertice<Planta> vertPlantaFin = new Vertice<Planta>(plantaFin);
		return this.agregarRuta(idRuta, vertPlantaOrigen, vertPlantaFin, distancia, duracion, pesoMax);
	}
	
	public Ruta agregarRuta(int idRuta, Vertice<Planta> PlantaOrigen, Vertice<Planta> PlantaFin, int distancia, double duracion, int pesoMax) {
		Ruta ruta = new Ruta(idRuta, PlantaOrigen, PlantaFin, distancia, duracion, pesoMax);

		rutaService.agregarRuta(ruta);
		return ruta;
	}
	
}

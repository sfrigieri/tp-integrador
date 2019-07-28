package isi.died.tp.controller;

import java.util.List;

import isi.died.tp.estructuras.Ruta;
import isi.died.tp.estructuras.Vertice;
import isi.died.tp.model.Planta;
import isi.died.tp.service.PlantaService;
import isi.died.tp.service.RutaService;
import isi.died.tp.service.RutaServiceDefault;

public class RutaController {

private RutaService rs;
	
	public RutaController(RutaService rs) {
		this.rs = rs;
	}
	
	public void agregarRuta(int idRuta, Planta plantaOrigen, Planta plantaFin, int distancia, double duracion, int pesoMax) {
		Vertice<Planta> vertPlantaOrigen = new Vertice<Planta>(plantaOrigen);
		Vertice<Planta> vertPlantaFin = new Vertice<Planta>(plantaFin);
		rs.agregarRuta(new Ruta(idRuta, vertPlantaOrigen, vertPlantaFin, distancia, duracion, pesoMax));
	}

	public List<Ruta> listaRutas() {
		return rs.listaRutas();
	}

	public void editarRuta(Ruta ruta) {
		rs.editarRuta(ruta);
	}

	public void eliminarRuta(Ruta ruta) {
		rs.eliminarRuta(ruta);
	}
	
	public void eliminarRuta(int id) {
		rs.eliminarRuta(rs.buscarRuta(id));
	}

	public Ruta buscarRuta(Integer id) {
		return rs.buscarRuta(id);
	}

	public void agregarRuta(Planta plantaOrigen, Planta plantaFin, int distancia, double duracion, int pesoMax) {
		Vertice<Planta> vertPlantaOrigen = new Vertice<Planta>(plantaOrigen);
		Vertice<Planta> vertPlantaFin = new Vertice<Planta>(plantaFin);
		rs.agregarRuta(new Ruta(vertPlantaOrigen, vertPlantaFin, distancia, duracion, pesoMax));
	}

	public boolean existeRuta(Planta inicio, Planta fin) {
		return rs.existeRuta(inicio, fin);
	}
}

package isi.died.tp.dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import isi.died.tp.dao.util.CsvSource;
import isi.died.tp.estructuras.*;
import isi.died.tp.model.*;

public class PlantaDaoDefault implements PlantaDao {

	private static Integer ULTIMO_ID_PROD;
	private static Integer ULTIMO_ID_ACOP;
	private static GrafoPlanta GRAFO_PLANTA  = new GrafoPlanta();

	private CsvSource dataSource;

	public PlantaDaoDefault() {
		dataSource = new CsvSource();
		if(GRAFO_PLANTA.esVacio())
			cargarGrafo();
		ULTIMO_ID_PROD = this.maxIdProd();
		ULTIMO_ID_ACOP = this.maxIdAcop();
	}
	
	private void cargarGrafo() {
		List<List<String>> plantasAcopio = dataSource.readFile("plantasAcopio.csv");
		for(List<String> fila : plantasAcopio) {
			PlantaAcopio actual = new PlantaAcopio();
			actual.loadFromStringRow(fila);
			GRAFO_PLANTA.addNodo(actual);
		}
		List<List<String>> plantasProduccion = dataSource.readFile("plantasProduccion.csv");
		for(List<String> fila : plantasProduccion) {
			PlantaProduccion actual = new PlantaProduccion();
			actual.loadFromStringRow(fila);
			GRAFO_PLANTA.addNodo(actual);
		}

 	}

	@Override
	public void setRutas(List<Arista<Planta>> lista) {
		GRAFO_PLANTA.setRutas(lista);
		
	}
	
	@Override
	public List<Arista<Planta>> listaRutas() {
		return GRAFO_PLANTA.getRutas();
	}

	
	
	@Override
	public PlantaProduccion buscarPlantaProduccion(Integer id) {
		for(Planta actual : GRAFO_PLANTA.listaVertices())
			if(actual instanceof PlantaProduccion && actual.getId() == id)
				return (PlantaProduccion) actual;
		return null;
	}
	
	
	@Override
	public PlantaAcopio buscarPlantaAcopio(Integer id) {
		for(Planta actual : GRAFO_PLANTA.listaVertices())
			if(actual instanceof PlantaAcopio && actual.getId() == id)
				return (PlantaAcopio) actual;
		return null;
	}
	
	private int maxIdProd() {

		int maxID = 0;
		
		for(Planta p : GRAFO_PLANTA.listaVertices()) {
			if(p instanceof PlantaProduccion && p.getId()>maxID)
				maxID = p.getId();
		}
		
		return maxID;
	}

	private int maxIdAcop() {

		int maxID = 0;
		
		for(Planta p : GRAFO_PLANTA.listaVertices()) {
			if(p instanceof PlantaAcopio && p.getId()>maxID)
				maxID = p.getId();
		}
		
		return maxID;
	}
	
	
	
	@Override
	public void agregarPlanta(Planta planta) {
	
		GRAFO_PLANTA.addNodo(planta);
		if(planta instanceof PlantaAcopio) {
			planta.setId(++ULTIMO_ID_ACOP);
			try {
				dataSource.agregarFilaAlFinal("plantasAcopio.csv", planta);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 
		else {
			planta.setId(++ULTIMO_ID_PROD);
			try {
				dataSource.agregarFilaAlFinal("plantasProduccion.csv", planta);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void editarPlanta(Planta planta) {
		Planta old = null;
		
		if(planta instanceof PlantaProduccion) {
			old = buscarPlantaProduccion(planta.getId());
			if (old != null)
				GRAFO_PLANTA.reemplazarNodo(old, planta);
			this.actualizarArchivoPlantasProduccion();
		}
		else {
			old = buscarPlantaAcopio(planta.getId());
			if (old != null)
				GRAFO_PLANTA.reemplazarNodo(old, planta);
			this.actualizarArchivoPlantasAcopio();
		}
	}
	
	
	
	
	@Override
	public void eliminarPlanta(Planta planta) {
		GRAFO_PLANTA.eliminarNodo(planta);
		if(planta instanceof PlantaAcopio)
			this.actualizarArchivoPlantasAcopio();
		else
			this.actualizarArchivoPlantasProduccion();
		
	}
	
	@Override
	public int buscarProximoFlujoDisponible(Vertice<Planta> origen) {
		return GRAFO_PLANTA.buscarProximoFlujoDisponible(origen);
	}
	
	@Override
	public void resetFlujo() {
		GRAFO_PLANTA.resetFlujo();
	}
	
	@Override
	public double generarNuevoPageRank(Planta p) {
		
		double sum = 0;
		int referencias;
		List<Vertice<Planta>> predecesores;
		
		predecesores = GRAFO_PLANTA.getPredecesores(new Vertice<Planta>(p));
		
		for(Vertice<Planta> pPred : predecesores) {
			referencias = GRAFO_PLANTA.gradoSalida(pPred);
			
			if(referencias != 0)
				sum+= pPred.getValor().getPageRank()/referencias;
		}
		
		//Factor amortigüación: 0.5
		return (1-0.5) + 0.5*sum;
	}




	@Override
	public void resetPageRanks() {
		for(Planta p : this.listaPlantas())
			p.setPageRank(1.0);
	}

	
	@Override
	public List<Planta> listaPlantas() {
		return GRAFO_PLANTA.listaVertices();
	}



	
	private void actualizarArchivoPlantasAcopio() {
		File archivoPlantasAcopio = new File("plantasAcopio.csv");
		archivoPlantasAcopio.delete();
		for(PlantaAcopio actual: this.listaPlantasAcopio()) {
			try {
				dataSource.agregarFilaAlFinal("plantasAcopio.csv", actual);
			} catch (IOException e) {
				e.printStackTrace();
				}
		
		}
	}
	
	@Override
	public List<PlantaAcopio> listaPlantasAcopio() {
		List<PlantaAcopio> plantas = new ArrayList<PlantaAcopio>();
		for(Planta actual : GRAFO_PLANTA.listaVertices()) {
			if(actual instanceof PlantaAcopio) plantas.add((PlantaAcopio) actual); 
		}
		return plantas;
	}
	
	
	private void actualizarArchivoPlantasProduccion() {
		File archivoPlantasProduccion = new File("plantasProduccion.csv");
		archivoPlantasProduccion.delete();
		for(PlantaProduccion actual: this.listaPlantasProduccion()) {
			try {
				dataSource.agregarFilaAlFinal("plantasProduccion.csv", actual);
			} catch (IOException e) {
				e.printStackTrace();
				}
		
		}
	}
	
	@Override
	public List<PlantaProduccion> listaPlantasProduccion() {
		List<PlantaProduccion> plantas = new ArrayList<PlantaProduccion>();
		for(Planta actual : GRAFO_PLANTA.listaVertices()) {
			if(actual instanceof PlantaProduccion) plantas.add((PlantaProduccion) actual); 
		}
		return plantas;
	}

	

	
	
}

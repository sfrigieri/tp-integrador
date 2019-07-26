package isi.died.tp.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import isi.died.tp.dao.util.CsvSource;
import isi.died.tp.estructuras.*;
import isi.died.tp.model.*;


public class PlantaDaoDefault implements PlantaDao {

	private static Integer ULTIMO_ID_PROD;
	private static Integer ULTIMO_ID_ACOP;
	private static GrafoPlanta GRAFO_PLANTA;

	private CsvSource dataSource;

	public PlantaDaoDefault() {
		GRAFO_PLANTA = new GrafoPlanta();
		dataSource = new CsvSource();
		if(GRAFO_PLANTA.esVacio())
			cargarGrafo();
		ULTIMO_ID_PROD = this.maxIdProd();
		ULTIMO_ID_ACOP = this.maxIdAcop();
	}

	private void cargarGrafo() {
		List<List<String>> plantasAcopio = null;
		try {
			plantasAcopio = dataSource.readFile("plantasAcopio.csv");
		} catch (FileNotFoundException e) {
			File archivo = new File("plantasAcopio.csv");
			try {
				archivo.createNewFile();

			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		if(plantasAcopio != null)
			for(List<String> fila : plantasAcopio) {
				PlantaAcopio actual = new PlantaAcopio();
				actual.loadFromStringRow(fila);
				GRAFO_PLANTA.addNodo(actual);
			}

		List<List<String>> plantasProduccion = null;
		try {
			plantasProduccion = dataSource.readFile("plantasProduccion.csv");
		} catch (FileNotFoundException e) {
			File archivo = new File("plantasProduccion.csv");
			try {
				archivo.createNewFile();

			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		if(plantasProduccion != null)
			for(List<String> fila : plantasProduccion) {
				PlantaProduccion actual = new PlantaProduccion();
				actual.loadFromStringRow(fila);
				GRAFO_PLANTA.addNodo(actual);
			}

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
	public void setRutas(List<Arista<Planta>> lista) {
		GRAFO_PLANTA.setRutas(lista);		
	}

	@Override
	public List<Arista<Planta>> listaRutas() {
		return GRAFO_PLANTA.getRutas();
	}

	@Override
	public PlantaAcopio buscarAcopioInicial() {
		for(PlantaAcopio p : this.listaPlantasAcopio()) {
			if(p.esOrigen())
				return p;
		}
		return null;
	}


	@Override
	public PlantaAcopio buscarAcopioFinal() {
		for(PlantaAcopio p : this.listaPlantasAcopio()) {
			if(!p.esOrigen())
				return p;
		}
		return null;
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
	public List<Recorrido> buscarCaminosInfo(Planta p1, Planta p2) {
		return GRAFO_PLANTA.buscarCaminosInfo(p1, p2);
	}

	@Override
	public Boolean existeCamino(Planta p1, Planta p2) {
		return GRAFO_PLANTA.existeCamino(p1, p2);
	}

	@Override
	public List<Planta> listaPlantas() {
		return GRAFO_PLANTA.listaVertices();
	}

	@Override
	public Boolean existenPlantas() {
		return GRAFO_PLANTA.existenPlantas();
	}



	@Override
	public List<PlantaAcopio> listaPlantasAcopio() {
		List<PlantaAcopio> plantas = new ArrayList<PlantaAcopio>();
		for(Planta actual : GRAFO_PLANTA.listaVertices()) {
			if(actual instanceof PlantaAcopio) plantas.add((PlantaAcopio) actual); 
		}
		return plantas;
	}


	@Override
	public List<PlantaProduccion> listaPlantasProduccion() {
		List<PlantaProduccion> plantas = new ArrayList<PlantaProduccion>();
		for(Planta actual : GRAFO_PLANTA.listaVertices()) {
			if(actual instanceof PlantaProduccion) plantas.add((PlantaProduccion) actual); 
		}
		return plantas;
	}

	@Override
	public Boolean necesitaInsumo(Integer id, Insumo ins) {
		PlantaProduccion p = null;
		p = this.buscarPlantaProduccion(id);
		if(p != null)
			return this.buscarPlantaProduccion(id).necesitaInsumo(ins);
		return null; 
	}

	@Override
	public void addInsumos(List<Insumo> lista) {
		if(!lista.isEmpty()) {
			PlantaAcopio plantaOrigen = this.buscarAcopioInicial();
			if(plantaOrigen != null)
				plantaOrigen.addInsumos(lista);
		}	
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
	public double generarNuevoPageRank(Planta p, Double factor) {

		double sum = 0;
		int referencias;
		List<Vertice<Planta>> predecesores;

		predecesores = GRAFO_PLANTA.getPredecesores(new Vertice<Planta>(p));

		for(Vertice<Planta> pPred : predecesores) {
			referencias = GRAFO_PLANTA.gradoSalida(pPred);

			if(referencias != 0)
				sum+= pPred.getValor().getPageRank()/referencias;
		}

		return (1-factor) + factor*sum;
	}




	@Override
	public void resetPageRanks() {
		for(Planta p : this.listaPlantas())
			p.setPageRank(1.0);
	}



	private void actualizarArchivoPlantasAcopio() {
		File archivoPlantasAcopio = new File("plantasAcopio.csv");
		archivoPlantasAcopio.delete();
		try {
			archivoPlantasAcopio.createNewFile();
			for(PlantaAcopio actual: this.listaPlantasAcopio()) {
				try {
					dataSource.agregarFilaAlFinal("plantasAcopio.csv", actual);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}



	private void actualizarArchivoPlantasProduccion() {
		File archivoPlantasProduccion = new File("plantasProduccion.csv");
		archivoPlantasProduccion.delete();
		try {
			archivoPlantasProduccion.createNewFile();
			for(PlantaProduccion actual: this.listaPlantasProduccion()) {
				try {
					dataSource.agregarFilaAlFinal("plantasProduccion.csv", actual);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}





}

package isi.died.tp.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import isi.died.tp.dao.util.CsvSource;
import isi.died.tp.estructuras.*;
import isi.died.tp.model.Planta;
import isi.died.tp.service.PlantaService;

public class RutaDaoDefault implements RutaDao {
	private static List<Ruta> LISTA_RUTAS  = new ArrayList<Ruta>();
	private static Integer ULTIMO_ID;
	private PlantaService ps;

	private CsvSource dataSource;

	public RutaDaoDefault(PlantaService ps) {
		this.ps = ps;
		dataSource = new CsvSource();
		if(LISTA_RUTAS.isEmpty())
			this.cargarListaRutas();

		ULTIMO_ID = this.maxID();
	}

	private int maxID() {

		int maxID = 0;

		for(Ruta ruta: LISTA_RUTAS) {
			if(ruta.getId()>maxID)
				maxID = ruta.getId();
		}

		return maxID;
	}


	public void cargarListaRutas() {

		List<List<String>> rutas = null;
		try {
			rutas = dataSource.readFile("rutas.csv");
		} catch (FileNotFoundException e) {
			File archivo = new File("rutas.csv");
			try {
				archivo.createNewFile();

			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		if(rutas != null)
			for(List<String> filaRuta : rutas) {
				Ruta aux = new Ruta();
				aux.loadFromStringRow(filaRuta);
				Planta p1, p2;
				if(filaRuta.get(1).matches("P"))
					p1 = ps.buscarPlantaProduccion(Integer.valueOf(filaRuta.get(2)));
				else
					p1 = ps.buscarPlantaAcopio(Integer.valueOf(filaRuta.get(2)));

				if(filaRuta.get(3).matches("P"))
					p2 = ps.buscarPlantaProduccion(Integer.valueOf(filaRuta.get(4)));
				else
					p2 = ps.buscarPlantaAcopio(Integer.valueOf(filaRuta.get(4)));
				if(p1 != null & p2 != null) {
					aux.setInicio(new Vertice<Planta>(p1));
					aux.setFin(new Vertice<Planta>(p2));
					LISTA_RUTAS.add(aux);
				}
			}
	}
	

	@Override
	public boolean existeRuta(Planta inicio, Planta fin) {
		for(Ruta r : LISTA_RUTAS) {
			if(r.getInicio().getValor().equals(inicio) && r.getFin().getValor().equals(fin))
				return true;
		}
		
		return false;
	}

	@Override
	public Ruta buscarRuta(Integer id) {
		for(Ruta actual : LISTA_RUTAS)
			if(actual.getId() == id)
				return actual;
		return null;
	}

	@Override
	public void agregarRuta(Ruta ruta) {
		ruta.setId(++ULTIMO_ID);
		LISTA_RUTAS.add(ruta);	
		try {
			dataSource.agregarFilaAlFinal("rutas.csv", ruta);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void editarRuta(Ruta ruta) {
		Ruta old = null;

		old = buscarRuta(ruta.getId());

		if(old != null)
			LISTA_RUTAS.remove(old);

		LISTA_RUTAS.add(ruta);
		this.actualizarArchivo();
	}


	@Override
	public void eliminarRuta(Ruta ruta) {
		LISTA_RUTAS.remove(ruta);
		this.actualizarArchivo();

	}

	@Override
	public List<Ruta> listaRutas() {
		return LISTA_RUTAS;
	}


	private void actualizarArchivo() {
		File archivoRutas = new File("rutas.csv");
		archivoRutas.delete();
		try {
			archivoRutas.createNewFile();
			for(Ruta actual: LISTA_RUTAS) {
				try {
					dataSource.agregarFilaAlFinal("rutas.csv", actual);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	//Si se elimina una planta, en el Grafo se eliminarán las rutas incidentes,
	//por lo que es necesario actualizar desde PlantaService la LISTA_RUTAS en RutaService
	@Override
	public void setRutas(List<Arista<Planta>> listaRutas) {
		LISTA_RUTAS.clear();

		for(Arista<Planta> ruta : listaRutas)
			LISTA_RUTAS.add((Ruta) ruta);

		this.actualizarArchivo();

	}

}

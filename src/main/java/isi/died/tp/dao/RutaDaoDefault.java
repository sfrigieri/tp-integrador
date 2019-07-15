package isi.died.tp.dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import isi.died.tp.dao.util.CsvSource;
import isi.died.tp.estructuras.Ruta;

public class RutaDaoDefault implements RutaDao {
	private static List<Ruta> LISTA_RUTAS  = new ArrayList<Ruta>();
	private static Integer ULTIMO_ID;
	
	private CsvSource dataSource;
	
	public RutaDaoDefault() {
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
		List<List<String>> rutas = dataSource.readFile("rutas.csv");
		for(List<String> filaRuta : rutas) {
			Ruta aux = new Ruta();
			aux.loadFromStringRow(filaRuta);
			LISTA_RUTAS.add(aux);
		}
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
		ruta.setId(ULTIMO_ID++);
		LISTA_RUTAS.add(ruta);	
		try {
			dataSource.agregarFilaAlFinal("rutas.csv", ruta);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void editarRuta(Integer id, Ruta ruta) {
		Ruta old = null;
		
		old = buscarRuta(id);
		
		if(old != null)
			LISTA_RUTAS.remove(old);
		
		LISTA_RUTAS.add(ruta);
		
		this.actualizarArchivo();
	}
	
	
	@Override
	public void eliminarRuta(Ruta ruta) {
		LISTA_RUTAS.remove(ruta);
		actualizarArchivo();
		
	}
	
	@Override
	public List<Ruta> listaRutas() {
		return LISTA_RUTAS;
	}
	

	private void actualizarArchivo() {
		File archivoRutas = new File("rutas.csv");
		archivoRutas.delete();
		for(Ruta actual: LISTA_RUTAS) {
			try {
				dataSource.agregarFilaAlFinal("rutas.csv", actual);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

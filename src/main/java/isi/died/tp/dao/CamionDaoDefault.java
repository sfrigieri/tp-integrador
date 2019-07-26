package isi.died.tp.dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import isi.died.tp.dao.util.CsvSource;
import isi.died.tp.model.Camion;

public class CamionDaoDefault implements CamionDao {
	private static List<Camion> LISTA_CAMIONES  = new ArrayList<Camion>();
	private static Integer ULTIMO_ID;
	
	private CsvSource dataSource;
	
	public CamionDaoDefault() {
		dataSource = new CsvSource();
		if(LISTA_CAMIONES.isEmpty())
			this.cargarListaCamiones();
		
		ULTIMO_ID = this.maxID();
	}
	
	private int maxID() {

		int maxID = 0;
		
		for(Camion camion: LISTA_CAMIONES) {
			if(camion.getId()>maxID)
				maxID = camion.getId();
		}
		
		return maxID;
	}
	
	
	public void cargarListaCamiones() {
		
		List<List<String>> camiones = dataSource.readFile("camiones.csv");
		for(List<String> filaCamion : camiones) {
			Camion aux = new Camion();
			aux.loadFromStringRow(filaCamion);
			LISTA_CAMIONES.add(aux);
		}
	}

	@Override
	public Camion buscarCamion(Integer id) {
		for(Camion actual : LISTA_CAMIONES)
			if(actual.getId() == id)
				return actual;
		return null;
	}
	
	@Override
	public void agregarCamion(Camion camion) {
		camion.setId(++ULTIMO_ID);
		LISTA_CAMIONES.add(camion);	
		try {
			dataSource.agregarFilaAlFinal("camiones.csv", camion);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void editarCamion(Camion camion) {
		Camion old = null;
		
		old = buscarCamion(camion.getId());
		
		if(old != null)
			LISTA_CAMIONES.remove(old);
		
		LISTA_CAMIONES.add(camion);
		this.actualizarArchivo();
	}
	
	
	@Override
	public void eliminarCamion(Camion camion) {
		LISTA_CAMIONES.remove(camion);
		this.actualizarArchivo();
		
	}
	
	@Override
	public List<Camion> listaCamiones() {
		List<Camion> listAux = LISTA_CAMIONES.stream().collect(Collectors.toList());
		listAux.sort((c1,c2) -> (Double.valueOf(c1.getCapacidad())).compareTo((Double.valueOf(c2.getCapacidad()))));
		return listAux;
	}
	

	private void actualizarArchivo() {
		File archivoCamiones = new File("camiones.csv");
		archivoCamiones.delete();
		try {
			archivoCamiones.createNewFile();
			for(Camion actual: LISTA_CAMIONES) {
				try {
					dataSource.agregarFilaAlFinal("camiones.csv", actual);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}

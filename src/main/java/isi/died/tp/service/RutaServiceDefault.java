package isi.died.tp.service;

import java.util.List;

import isi.died.tp.dao.RutaDao;
import isi.died.tp.dao.RutaDaoDefault;
import isi.died.tp.estructuras.Arista;
import isi.died.tp.estructuras.Ruta;
import isi.died.tp.model.Planta;

public class RutaServiceDefault implements RutaService {

	private RutaDao rutaDao;
	private PlantaService ps;

	public RutaServiceDefault(PlantaService ps) {
		super();
		this.rutaDao = new RutaDaoDefault(ps);
		this.ps = ps;
		//Cuando paso la lista, la otra lista tendrá una referencia a cada uno de los objetos.
		//Aunque elimine, modifique, etc objetos en esta lista, la otra seguirá apuntando a esos objetos
		//por lo que es necesario volver a actualizar la lista Completa.
		ps.setRutas(rutaDao.listaRutas());
		ps.setRutaService(this);
		System.out.println("Flujo Máximo: "+ps.flujoMaximoRed(ps.buscarAcopioInicial())+" Toneladas");
	}
	
	@Override
	public Ruta agregarRuta(Ruta ruta) {
		rutaDao.agregarRuta(ruta);	
		ps.setRutas(rutaDao.listaRutas());
		return ruta;
	}
	
	@Override
	public List<Ruta> listaRutas() {
		return rutaDao.listaRutas();
	}

	@Override
	public void editarRuta(Ruta ruta) {
		rutaDao.editarRuta(ruta);
		ps.setRutas(rutaDao.listaRutas());
	}

	@Override
	public void eliminarRuta(Ruta ruta) {
		rutaDao.eliminarRuta(ruta);
		ps.setRutas(rutaDao.listaRutas());
	}

	@Override
	public Ruta buscarRuta(Integer id) {
		return rutaDao.buscarRuta(id);
	}

	//Si se elimina una planta, en el Grafo se eliminarán las rutas incidentes,
	//por lo que es necesario actualizar desde PlantaService la LISTA_RUTAS en RutaService
	@Override
	public void setRutas(List<Arista<Planta>> listaRutas) {
		rutaDao.setRutas(listaRutas);
		
	}
}

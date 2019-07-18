package isi.died.tp.service;

import java.util.List;

import isi.died.tp.dao.RutaDao;
import isi.died.tp.dao.RutaDaoDefault;
import isi.died.tp.estructuras.Arista;
import isi.died.tp.estructuras.Ruta;
import isi.died.tp.model.Planta;

public class RutaServiceDefault implements RutaService {

	private RutaDao rutaDao;

	public RutaServiceDefault(PlantaService ps) {
		super();
		this.rutaDao = new RutaDaoDefault(ps);
		ps.setRutaService(this);
	}
	
	@Override
	public Ruta agregarRuta(Ruta ruta) {
		rutaDao.agregarRuta(ruta);
		return ruta;
	}
	
	@Override
	public List<Ruta> listaRutas() {
		return rutaDao.listaRutas();
	}

	@Override
	public void editarRuta(Ruta ruta) {
		rutaDao.editarRuta(ruta);

	}

	@Override
	public void eliminarRuta(Ruta ruta) {
		rutaDao.eliminarRuta(ruta);

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

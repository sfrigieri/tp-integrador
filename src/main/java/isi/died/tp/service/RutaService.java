package isi.died.tp.service;

import java.util.List;

import isi.died.tp.estructuras.Arista;
import isi.died.tp.estructuras.Ruta;
import isi.died.tp.model.Planta;

public interface RutaService {

	public Ruta agregarRuta(Ruta ruta);

	public List<Ruta> listaRutas();

	public void editarRuta(Ruta ruta);

	public void eliminarRuta(Ruta ruta);

	public Ruta buscarRuta(Integer id);

	public void setRutas(List<Arista<Planta>> listaRutas);

	public boolean existeRuta(Planta inicio, Planta fin);
}

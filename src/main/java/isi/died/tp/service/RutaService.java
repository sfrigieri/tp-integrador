package isi.died.tp.service;

import java.util.List;

import isi.died.tp.estructuras.Ruta;

public interface RutaService {

	public Ruta agregarRuta(Ruta ruta);

	public List<Ruta> listaRutas();

	public void editarRuta(Integer id, Ruta ruta);

	public void eliminarRuta(Ruta ruta);

	public Ruta buscarRuta(Integer id);
}

package isi.died.tp.dao;

import java.util.List;

import isi.died.tp.estructuras.Ruta;

public interface RutaDao {

	public void agregarRuta(Ruta ruta);

	public List<Ruta> listaRutas();

	public void editarRuta(Integer id, Ruta ruta);

	public void eliminarRuta(Ruta ruta);

	public Ruta buscarRuta(Integer id);
}

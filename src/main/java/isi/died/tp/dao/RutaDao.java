package isi.died.tp.dao;

import java.util.List;

import isi.died.tp.estructuras.Arista;
import isi.died.tp.estructuras.Ruta;
import isi.died.tp.model.Planta;

public interface RutaDao {

	public void agregarRuta(Ruta ruta);

	public List<Ruta> listaRutas();

	public void editarRuta(Ruta ruta);

	public void eliminarRuta(Ruta ruta);

	public Ruta buscarRuta(Integer id);

	public void setRutas(List<Arista<Planta>> listaRutas);
}

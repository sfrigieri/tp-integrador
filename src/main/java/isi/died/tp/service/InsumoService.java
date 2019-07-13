package isi.died.tp.service;

import java.util.List;

import isi.died.tp.model.Insumo;

public interface InsumoService {

	public Insumo agregarInsumo(Insumo insumo);

	public List<Insumo> listaInsumos();

	public void editarInsumo(Integer id, Insumo insumo);

	public void eliminarInsumo(Insumo insumo);

	public Insumo buscarInsumo(Integer id);

}

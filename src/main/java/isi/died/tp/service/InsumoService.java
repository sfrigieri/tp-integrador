package isi.died.tp.service;

import java.util.List;

import isi.died.tp.model.Insumo;
import isi.died.tp.model.InsumoLiquido;

public interface InsumoService {

	public Insumo agregarInsumo(Insumo insumo);

	public List<Insumo> listaInsumos();
	public List<InsumoLiquido> listaInsumosLiquidos();

	public void editarInsumoNoLiquido(Integer id, Insumo insumo);
	public void editarInsumoLiquido(Integer id, Insumo insumo);

	public void eliminarInsumo(Insumo insumo);

	public Insumo buscarInsumoNoLiquido(Integer id);
	public Insumo buscarInsumoLiquido(Integer id);

}

package isi.died.tp.dao;

import java.util.List;

import isi.died.tp.model.Insumo;
import isi.died.tp.model.InsumoLiquido;

public interface InsumoDao {

	public void agregarInsumo(Insumo insumo);

	public List<Insumo> listaInsumos();
	
	public List<InsumoLiquido> listaInsumosLiquidos();

	public void editarInsumo(Integer id, Insumo insumo);

	public void eliminarInsumo(Insumo insumo);

	public Insumo buscarInsumo(Integer id);
	

}

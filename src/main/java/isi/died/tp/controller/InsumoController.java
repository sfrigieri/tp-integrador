package isi.died.tp.controller;

import isi.died.tp.dao.InsumoDao;
import isi.died.tp.model.Insumo;
import isi.died.tp.model.InsumoLiquido;
import isi.died.tp.model.Unidad;

public class InsumoController {
	
	private InsumoDao insumoDAO;
	
	public InsumoController(InsumoDao dao) {
		this.insumoDAO = dao;
	}
	
	public Insumo agregarInsumo(int id, String descripcion, Unidad unidadDeMedida, double costo, double peso, boolean esRefrigerado) {
		Insumo insumo = new Insumo(id, descripcion, unidadDeMedida, costo, peso, esRefrigerado);

		insumoDAO.agregarInsumo(insumo);
		return insumo;
	}
	public Insumo agregarInsumo(int id, String descripcion, double costo, boolean esRefrigerado, double dens, double litros) {
		Insumo insumo = new InsumoLiquido(id, descripcion, costo, esRefrigerado, dens, litros);

		insumoDAO.agregarInsumo(insumo);
		return insumo;
	}
	
	

}

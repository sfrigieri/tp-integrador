package isi.died.tp.controller;


import java.util.List;

import isi.died.tp.model.*;
import isi.died.tp.service.*;

public class InsumoController {
	
	private InsumoService insumoService;
	
	public InsumoController(InsumoService insumoService) {
		this.insumoService =  insumoService;
	}
	
	public void agregarInsumo(int id, String descripcion, Unidad unidadDeMedida, double costo, double peso, boolean esRefrigerado) {
		Insumo insumo = new Insumo(id, descripcion, unidadDeMedida, costo, peso, esRefrigerado);
		insumoService.agregarInsumo(insumo);
	}
	
	public void agregarInsumo(int id, String descripcion, double costo, boolean esRefrigerado, double dens, double litros) {
		Insumo insumo = new InsumoLiquido(id, descripcion, costo, esRefrigerado, dens, litros);
		insumoService.agregarInsumo(insumo);
	}
	
	public List<Insumo> listaInsumos() {
		return insumoService.listaInsumos();
	}
	public List<InsumoLiquido> listaInsumosLiquidos() {
		return insumoService.listaInsumosLiquidos();
	}
	
	public void eliminarInsumoNoLiquido (int id) {
		Insumo insumo = buscarInsumoNoLiquido(id);
		insumoService.eliminarInsumo(insumo);
	}
	public void eliminarInsumoLiquido (int id) {
		Insumo insumo = buscarInsumoLiquido(id);
		insumoService.eliminarInsumo(insumo);
	}
	
	public Insumo buscarInsumoNoLiquido (int id) {
		return insumoService.buscarInsumoNoLiquido(id);
	}
	public Insumo buscarInsumoLiquido (int id) {
		return insumoService.buscarInsumoLiquido(id);
	}

	public void editarInsumoNoLiquido(Insumo insumo) {
		insumoService.editarInsumoNoLiquido(insumo);
	}
	public void editarInsumoLiquido(Insumo insumo) {
		insumoService.editarInsumoLiquido(insumo);
	}

}

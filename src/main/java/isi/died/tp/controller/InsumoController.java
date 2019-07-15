package isi.died.tp.controller;


import java.util.List;

import isi.died.tp.model.*;
import isi.died.tp.service.*;

public class InsumoController {
	
	private InsumoService insumoService;
	
	public InsumoController() {
		this.insumoService = new InsumoServiceDefault();
	}
	
	public Insumo agregarInsumo(int id, String descripcion, Unidad unidadDeMedida, double costo, double peso, boolean esRefrigerado) {
		Insumo insumo = new Insumo(id, descripcion, unidadDeMedida, costo, peso, esRefrigerado);

		insumoService.agregarInsumo(insumo);
		return insumo;
	}
	
	public Insumo agregarInsumo(int id, String descripcion, double costo, boolean esRefrigerado, double dens, double litros) {
		Insumo insumo = new InsumoLiquido(id, descripcion, costo, esRefrigerado, dens, litros);

		insumoService.agregarInsumo(insumo);
		return insumo;
	}
	
	public List<Insumo> listaInsumos() {
		return insumoService.listaInsumos();
	}
	
	public void eliminarInsumo (int id) {
		Insumo insumo = buscarInsumo(id);
		insumoService.eliminarInsumo(insumo);
	}
	
	public Insumo buscarInsumo (int id) {
		return insumoService.buscarInsumo(id);
	}

	public void editarInsumo(int id, Insumo insumo) {
		insumoService.editarInsumo(id, insumo);
	}

}

package isi.died.tp.controller;


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
	
	

}

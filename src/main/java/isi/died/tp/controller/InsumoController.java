package isi.died.tp.controller;


import java.util.Comparator;
import java.util.List;

import isi.died.tp.estructuras.ArbolBinarioBusqueda;
import isi.died.tp.model.*;
import isi.died.tp.service.*;

public class InsumoController {
	
	private InsumoService insumoService;
	private ArbolBinarioBusqueda<Insumo> arbolBB;
	private Comparator<Insumo> compNombreAsc= (i1,i2)-> i1.getDescripcion().compareTo(i2.getDescripcion());
	private Comparator<Insumo> compNombreDesc= (i1,i2)-> i2.getDescripcion().compareTo(i1.getDescripcion());
	private Comparator<Insumo> compCostoAsc= (i1,i2)-> i1.getCosto().intValue()- i2.getCosto().intValue();
	private Comparator<Insumo> compCostoDesc= (i1,i2)-> i2.getCosto().intValue()- i1.getCosto().intValue();
	private Comparator<Insumo> compStockAsc= (i1,i2)-> sc.calcularCantidadTotal(i1)- sc.calcularCantidadTotal(i2);
	private Comparator<Insumo> compStockDesc= (i1,i2)-> sc.calcularCantidadTotal(i2)- sc.calcularCantidadTotal(i1);
	private static StockController sc;
	
	public InsumoController(InsumoService insumoService) {
		this.insumoService =  insumoService;
		sc = GestionEntidadesController.stockController;
	}
	
	public void ordenarPor(OpcionesOrdenInsumo op) {
		List<Insumo> listaInsumos = this.listaInsumos();
		listaInsumos.addAll(this.listaInsumosLiquidos());
		
		for(Insumo ins : listaInsumos)
			arbolBB.agregarComp(ins);
		
	}
	public void agregarInsumo(Insumo ins) {
		insumoService.agregarInsumo(ins);
	}
	
	public void agregarInsumo(int id, String descripcion, Unidad unidadDeMedida, double costo, double peso, boolean esRefrigerado) {
		Insumo insumo = new Insumo(id, descripcion, unidadDeMedida, costo, peso, esRefrigerado);
		insumoService.agregarInsumo(insumo);
	}
	
	public void agregarInsumo(int id, String descripcion, double costo, boolean esRefrigerado, double dens, double peso) {
		Insumo insumo = new InsumoLiquido(id, descripcion, costo, esRefrigerado, dens, peso);
		insumoService.agregarInsumo(insumo);
	}
	
	public List<Insumo> listaInsumos() {
		return insumoService.listaInsumos();
	}
	public List<Insumo> listaInsumosLiquidos() {
		return insumoService.listaInsumosLiquidos();
	}
	
	public void eliminarInsumoNoLiquido (Insumo ins) {
		insumoService.eliminarInsumo(ins);
	}
	public void eliminarInsumoLiquido (Insumo ins) {
		insumoService.eliminarInsumo(ins);
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

package isi.died.tp.controller;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import isi.died.tp.estructuras.ArbolBinarioBusqueda;
import isi.died.tp.model.*;
import isi.died.tp.service.*;

public class InsumoController {

	private InsumoService insumoService;
	private Comparator<Insumo> compNombreAsc= (i1,i2)-> i1.getDescripcion().toLowerCase().compareTo(i2.getDescripcion().toLowerCase());
	private Comparator<Insumo> compNombreDesc= (i1,i2)-> i2.getDescripcion().toLowerCase().compareTo(i1.getDescripcion().toLowerCase());
	private Comparator<Insumo> compCostoAsc= (i1,i2)-> i1.getCosto().intValue()- i2.getCosto().intValue();
	private Comparator<Insumo> compCostoDesc= (i1,i2)-> i2.getCosto().intValue()- i1.getCosto().intValue();
	private Comparator<Insumo> compStockAsc= (i1,i2)->	i1.getCantidadTotal() - i2.getCantidadTotal();
	private Comparator<Insumo> compStockDesc= (i1,i2)-> i2.getCantidadTotal()- i1.getCantidadTotal();
	private static StockController sc;

	public InsumoController(InsumoService insumoService) {
		this.insumoService =  insumoService;
	}

	public List<Insumo> buscarConFiltros(String nombre, Integer stockMin, Integer stockMax, Double costoMin,
			Double costoMax) {
		ArbolBinarioBusqueda<Insumo> arbolBB;
		List<Insumo> listaInsumos = this.listaInsumos().stream().collect(Collectors.toList());
		listaInsumos.addAll(this.listaInsumosLiquidos().stream().collect(Collectors.toList()));
		List<Insumo> listaAux = new ArrayList<Insumo>();

		if(!listaInsumos.isEmpty()) {


			for(Insumo i : listaInsumos)
				sc.calcularCantidadTotal(i);

			Insumo ins1 = new Insumo(), ins2 = new Insumo();

			if(nombre != null) {
				ins1.setDescripcion(nombre);
				ins2.setDescripcion(nombre);

				arbolBB = new ArbolBinarioBusqueda<Insumo>(compNombreAsc);

				for(Insumo ins : listaInsumos)
					arbolBB.agregarComp(ins);

				listaAux = arbolBB.rangoComp(ins1, ins2);
			}
			else
				listaAux = listaInsumos;

			if(stockMin != null && stockMax != null) {
				ins1.setCantidadTotal(stockMin);
				ins2.setCantidadTotal(stockMax);

				arbolBB = new ArbolBinarioBusqueda<Insumo>(compStockAsc);

				if(!listaAux.isEmpty()) {

					for(Insumo ins : listaAux)
						arbolBB.agregarComp(ins);

					listaAux = arbolBB.rangoComp(ins1, ins2);
				}
			}

			if(costoMin != null && costoMax != null) {
				ins1.setCosto(costoMin);
				ins2.setCosto(costoMax);
				arbolBB = new ArbolBinarioBusqueda<Insumo>(compCostoAsc);

				if(!listaAux.isEmpty()) {
					for(Insumo ins : listaAux)
						arbolBB.agregarComp(ins);

					listaAux = arbolBB.rangoComp(ins1, ins2);

				}
			}


			

		}
		return listaAux;
	}


	public List<Insumo> ordenarPor(String tipoOrden, String orden, List<Insumo> lista) {
		ArbolBinarioBusqueda<Insumo> arbolBB = null;
		if(orden == "DESC") {
			if(tipoOrden == "NOMBRE")
				arbolBB = new ArbolBinarioBusqueda<Insumo>(compNombreDesc);
			else 
				if(tipoOrden == "COSTO")
					arbolBB = new ArbolBinarioBusqueda<Insumo>(compCostoDesc);
				else 
					if(tipoOrden == "CANTIDAD")
						arbolBB = new ArbolBinarioBusqueda<Insumo>(compStockDesc);
		}
		else {
			if(tipoOrden == "NOMBRE")
				arbolBB = new ArbolBinarioBusqueda<Insumo>(compNombreAsc);
			else 
				if(tipoOrden == "COSTO")
					arbolBB = new ArbolBinarioBusqueda<Insumo>(compCostoAsc);
				else 
					if(tipoOrden == "CANTIDAD")
						arbolBB = new ArbolBinarioBusqueda<Insumo>(compStockAsc);
		}	
		
		List<Insumo> listaAux = new ArrayList<Insumo>();
		
		if(arbolBB != null) {
			for(Insumo ins : lista)
				arbolBB.agregarComp(ins);
			
			listaAux = arbolBB.inOrden();
	}
		
		return listaAux;

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

	public void setStockController(StockController stockController) {
		sc = stockController;

	}



}

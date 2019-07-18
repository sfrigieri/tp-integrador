package isi.died.tp.model;

import java.util.List;

import isi.died.tp.dao.util.CsvRecord;

public abstract class Planta implements CsvRecord {

	protected int id;
	protected String nombre;
	protected Double pageRank;

	protected Planta(int id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.pageRank = 1.0;
	}

	protected Planta() {
		super();
		this.pageRank = 1.0;
	}

	public void setPageRank(Double pr) {
		this.pageRank = pr;
	}

	public Double getPageRank() {
		return this.pageRank;
	}

	public Boolean esOrigen() {
		return false;
	}

	public void setEsOrigen(Boolean valor) {

	}

	public String getNombre() {
		return this.nombre;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Insumo getInsumo(int IdIns) {
		return null;
	}

	public void addInsumo(Insumo ins) {
	}

	public Boolean necesitaInsumo(Insumo i) {
		return false;
	}

	public Integer cantidadNecesariaInsumo(Insumo i) {
		return 0;
	}

	public void removeInsumo(Insumo ins) {

	}



	public Double costoTotal() {
		return null;

	}


	public List<Insumo> stockEntre(Integer s1, Integer s2) {
		return null;

	}

	public void addStock(Stock stock) {

	}

	public void addStock(int id, int cantidad, int puntoPedido, Insumo ins, Planta planta) {

	}

	public Stock getStock(Insumo ins) {
		return null;

	}

	public Stock getStock(int id) {
		return null;

	}

	public Stock getStock(Stock s) {
		return null;
	}

	public void removeStock(Insumo ins) {

	}

	public void addInsumos(List<Insumo> lista) {


	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
	}

	@Override
	public void loadFromStringRow(List<String> datos) {

	}





}

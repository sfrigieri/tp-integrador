package isi.died.tp.model;

import java.util.ArrayList;
import java.util.List;

import isi.died.tp.dao.util.CsvRecord;

public class Insumo implements Comparable<Insumo>, CsvRecord{

	protected int id;
	protected String descripcion;
	protected Unidad unidadDeMedida;
	protected double costo;
	protected StockAcopio stock;
	protected double peso;
	protected boolean esRefrigerado;
	
	
	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}


	public void setStock(StockAcopio stock) {
		this.stock = stock;
	}
	
	public StockAcopio getStock() {
		return this.stock;
	}
	
	public Insumo() {}

	public Insumo(int id, double costo) {
		super();
		this.id = id;
		this.costo = costo;
		this.stock = null;
	}


	public Insumo(int id, String descripcion, Unidad unidadDeMedida, double costo, double peso,
			boolean esRefrigerado) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.unidadDeMedida = unidadDeMedida;
		this.costo = costo;
		this.stock = null;
		this.peso = peso;
		this.esRefrigerado = esRefrigerado;
	}




	public Insumo(int id) {
		super();
		this.id = id;
		this.stock = null;
	}


	@Override
	public int compareTo (Insumo i) {
		return (this.stock.getCantidad()-i.getStock().getCantidad());
	}
	

	public boolean hayStock(double cant) {
		return false;
	}

	public void actualizarStock(double cant) {
		
		
	}

	public Double getCosto() {
		return this.costo;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(costo);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + (esRefrigerado ? 1231 : 1237);
		result = prime * result + id;
		temp = Double.doubleToLongBits(peso);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((stock == null) ? 0 : stock.hashCode());
		result = prime * result + ((unidadDeMedida == null) ? 0 : unidadDeMedida.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Insumo other = (Insumo) obj;
		if (Double.doubleToLongBits(costo) != Double.doubleToLongBits(other.costo))
			return false;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (esRefrigerado != other.esRefrigerado)
			return false;
		if (id != other.id)
			return false;
		if (Double.doubleToLongBits(peso) != Double.doubleToLongBits(other.peso))
			return false;
		if (stock == null) {
			if (other.stock != null)
				return false;
		} else if (!stock.equals(other.stock))
			return false;
		if (unidadDeMedida != other.unidadDeMedida)
			return false;
		return true;
	}

	@Override
	public List<String> asCsvRow() {
		//int id, String descripcion, Unidad unidadDeMedida, double costo, double peso, boolean esRefrigerado
		List<String> lista = new ArrayList<String>();
		lista.add(this.id+"");
		lista.add(this.descripcion.toString());
		lista.add(this.unidadDeMedida.toString());
		lista.add(Double.toString(this.costo));
		lista.add(Double.toString(this.peso));
		lista.add(Boolean.toString(this.esRefrigerado));
		return lista;
	}

	@Override
	public void loadFromStringRow(List<String> datos) {
		try {
			this.id = Integer.valueOf(datos.get(0));
			this.descripcion = datos.get(1);
			this.unidadDeMedida = Unidad.valueOf(datos.get(2));
			this.costo = Double.valueOf(datos.get(3));
			this.peso = Double.valueOf(datos.get(4));
			this.esRefrigerado = Boolean.valueOf(datos.get(5)); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}




}

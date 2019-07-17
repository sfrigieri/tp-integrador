package isi.died.tp.model;

import isi.died.tp.dao.util.CsvRecord;

public abstract class Stock implements CsvRecord {

	protected int id;
	protected int cantidad;
	protected Insumo ins;
	protected Planta planta;
	
	protected Stock(int id, int cantidad, Insumo ins, Planta planta) {
		super();
		this.id = id;
		this.cantidad = cantidad;
		this.ins = ins;
		this.planta = planta;
	}
	
	protected Stock(int id, int cantidad) {
		super();
		this.id = id;
		this.cantidad = cantidad;
	}
	
	protected Stock(int id, Insumo ins, Planta planta) {
		super();
		this.id = id;
		this.cantidad = 0;
		this.ins = ins;
		this.planta = planta;
	}
	
	
	protected Stock() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	

	public Integer getCantidad() {
		return null;
	}
	
	public Insumo getInsumo() {
		return null;
	}
	
	public void setInsumo(Insumo buscarInsumo) {
		
		
	}
	
	public Integer getPuntoPedido() {
		return null;
	}

	public void setCantidad(int i) {
		this.cantidad = i;
		
	}
	
	public Planta getPlanta() {
		return null;
	}

	public void setPlanta(Planta planta) {

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cantidad;
		result = prime * result + id;
		result = prime * result + ((ins == null) ? 0 : ins.hashCode());
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
		Stock other = (Stock) obj;
		if (cantidad != other.cantidad)
			return false;
		if (id != other.id)
			return false;
		if (ins == null) {
			if (other.ins != null)
				return false;
		} else if (!ins.equals(other.ins))
			return false;
		return true;
	}
	
	
}

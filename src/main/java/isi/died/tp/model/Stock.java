package isi.died.tp.model;

public class Stock {

	private int id;
	private int cantidad;
	private int puntoPedido;
	private Insumo ins;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Integer getCantidad() {
		return this.cantidad;
	}
	
	public Insumo getInsumo() {
		return this.ins;
	}
	
	public Stock(int id, int cantidad, int puntoPedido, Insumo ins) {
		super();
		this.id = id;
		this.cantidad = cantidad;
		this.puntoPedido = puntoPedido;
		this.ins = ins;
	}

	public Integer getPuntoPedido() {
		return this.puntoPedido;
	}

	public Stock(int id, Insumo ins) {
		super();
		this.id = id;
		this.ins = ins;
		this.cantidad = 0;
		this.puntoPedido = 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		if (id != other.id)
			return false;
		return true;
	}


}

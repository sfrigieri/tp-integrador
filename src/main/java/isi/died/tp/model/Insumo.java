package isi.died.tp.model;

public class Insumo implements Comparable<Insumo>{

	protected int id;
	protected String descripcion;
	protected Unidad unidadDeMedida;
	protected double costo;
	protected int stock;
	protected double peso;
	protected boolean esRefrigerado;
	
	
	
	

	public Insumo(int id, double costo, int stock) {
		super();
		this.id = id;
		this.costo = costo;
		this.stock = stock;
	}


	public Insumo(int id, String descripcion, Unidad unidadDeMedida, double costo, int stock, double peso,
			boolean esRefrigerado) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.unidadDeMedida = unidadDeMedida;
		this.costo = costo;
		this.stock = stock;
		this.peso = peso;
		this.esRefrigerado = esRefrigerado;
	}




	public Insumo(int id, int stock) {
		super();
		this.id = id;
		this.stock = stock;
	}


	@Override
	public int compareTo (Insumo i) {
		return (this.stock-i.getStock());
	}
	
	public Integer getStock() {
		return this.stock;
	}

	public boolean hayStock(double cant) {
		return false;
	}

	public void actualizarStock(double cant) {
	
		
	}

	public Double getCosto() {
		return this.costo;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		long temp;
		temp = Double.doubleToLongBits(peso);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + stock;
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
		if (id != other.id)
			return false;
		if (Double.doubleToLongBits(peso) != Double.doubleToLongBits(other.peso))
			return false;
		if (stock != other.stock)
			return false;
		if (unidadDeMedida != other.unidadDeMedida)
			return false;
		return true;
	}
}

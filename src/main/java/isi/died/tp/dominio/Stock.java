package isi.died.tp.dominio;

public class Stock {

	private int id;
	private int cantidad;
	private int puntoPedido;
	private Insumo ins;
	
	public Integer getCantidad() {
		return this.cantidad;
	}
	
	public Insumo getInsumo() {
		return this.ins;
	}
	
	public Integer getPuntoPedido() {
		return this.puntoPedido;
	}
}

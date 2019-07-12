package isi.died.tp.model;

import java.util.Date;

public class Pedido {
	
	int id;
	StockAcopio stock;
	boolean entregado;
	Date fechaSolicitud;
	Date fechaEntrega;
	
	public Pedido(int id, StockAcopio stock, boolean entregado, Date fechaSolicitud, Date fechaEntrega) {
		this.id = id;
		this.stock = stock;
		this.entregado = entregado;
		this.fechaSolicitud = fechaSolicitud;
		this.fechaEntrega = fechaEntrega;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public StockAcopio getStock() {
		return stock;
	}

	public void setStock(StockAcopio stock) {
		this.stock = stock;
	}

	public boolean isEntregado() {
		return entregado;
	}

	public void setEntregado(boolean entregado) {
		this.entregado = entregado;
	}

	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public Date getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}
	
}

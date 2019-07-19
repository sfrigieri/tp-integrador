package isi.died.tp.estructuras;

import java.util.List;

import isi.died.tp.model.Planta;

class Recorrido {
	List<Ruta> recorrido;
	double duracionTotal;
	int pesoMax;
	int distanciaTotal;
	

	public Recorrido(List<Ruta> rec) {
		this.recorrido = rec;
		this.duracionTotal = 0;
		this.pesoMax = Integer.MAX_VALUE;
		this.distanciaTotal = 0;
	}


	public List<Ruta> getRecorrido() {
		return recorrido;
	}


	public void calcularInfo() {

		for(Ruta ruta : recorrido) {
			this.duracionTotal+= ruta.getDuracionViajeMin();
			if(ruta.getPesoMaxTon() < this.pesoMax)
				this.pesoMax = ruta.getPesoMaxTon();
			this.distanciaTotal+= ruta.getValor();
		}
	}

	
	public void setRecorrido(List<Ruta> recorrido) {
		this.recorrido = recorrido;
	}


	public double getDuracionTotal() {
		return duracionTotal;
	}


	public void setDuracionTotal(double duracionTotal) {
		this.duracionTotal = duracionTotal;
	}


	public int getPesoMax() {
		return pesoMax;
	}


	public void setPesoMax(int pesoMax) {
		this.pesoMax = pesoMax;
	}


	public int getDistanciaTotal() {
		return distanciaTotal;
	}


	public void setDistanciaTotal(int distanciaTotal) {
		this.distanciaTotal = distanciaTotal;
	}
	
}
package isi.died.tp.estructuras;

import isi.died.tp.model.Planta;

public class Ruta extends Arista<Planta> {

	private double duracionViajeMin;
	private int pesoMaxTon;
	private int pesoEnCursoTon;
	
	public Ruta(Vertice<Planta> ini, Vertice<Planta> fin, int distanciaKm, double duracionViaje, int pesoMax) {
		super(ini, fin, distanciaKm);
		this.duracionViajeMin = duracionViaje;
		this.pesoMaxTon = pesoMax;
		this.pesoEnCursoTon = 0;
	}

	@Override
	public double getDuracionViajeMin() {
		return duracionViajeMin;
	}

	@Override
	public void setDuracionViajeMin(double duracionViajeMin) {
		this.duracionViajeMin = duracionViajeMin;
	}

	@Override
	public int getPesoMaxTon() {
		return pesoMaxTon;
	}


	@Override
	public void setPesoMax(int pesoMaxTon) {
		this.pesoMaxTon = pesoMaxTon;
	}

	@Override
	public int getPesoEnCurso() {
		return pesoEnCursoTon;
	}

	@Override
	public void setPesoEnCurso(int pesoEnCursoTon) {
		this.pesoEnCursoTon = pesoEnCursoTon;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(duracionViajeMin);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(pesoEnCursoTon);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(pesoMaxTon);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ruta other = (Ruta) obj;
		if (Double.doubleToLongBits(duracionViajeMin) != Double.doubleToLongBits(other.duracionViajeMin))
			return false;
		if (Double.doubleToLongBits(pesoEnCursoTon) != Double.doubleToLongBits(other.pesoEnCursoTon))
			return false;
		if (Double.doubleToLongBits(pesoMaxTon) != Double.doubleToLongBits(other.pesoMaxTon))
			return false;
		return true;
	}



	
}

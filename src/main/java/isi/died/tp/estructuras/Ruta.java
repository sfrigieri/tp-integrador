package isi.died.tp.estructuras;

import isi.died.tp.model.Planta;

public class Ruta extends Arista<Planta> {

	private Double duracion;
	private Double pesoMax;
	
	public Ruta(Vertice<Planta> ini, Vertice<Planta> fin, Number val, Double duracion, Double pesoMax) {
		super(ini, fin, val);
		this.duracion = duracion;
		this.pesoMax = pesoMax;
	}

	
}

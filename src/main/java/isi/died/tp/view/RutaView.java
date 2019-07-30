package isi.died.tp.view;

import isi.died.tp.model.Planta;

public class RutaView extends AristaView<Planta>{

	public int id;
	private double duracionViajeMin;
	private int pesoMaxTon;
	private int pesoEnCursoTon;
	
	public RutaView(VerticeView<Planta> ini, VerticeView<Planta> fin, int distanciaKm, double duracionViaje, int pesoMax) {
		super(ini, fin, distanciaKm);
		this.duracionViajeMin = duracionViaje;
		this.pesoMaxTon = pesoMax;
		this.pesoEnCursoTon = 0;
	}
	
	public RutaView(int id, VerticeView<Planta> ini, VerticeView<Planta> fin, int distanciaKm, double duracionViaje, int pesoMax) {
		super(ini, fin, distanciaKm);
		this.duracionViajeMin = duracionViaje;
		this.pesoMaxTon = pesoMax;
		this.pesoEnCursoTon = 0;
		this.id = id;
	}

	public RutaView() {
		super(null, null, null);
		this.duracionViajeMin = 0;
		this.pesoMaxTon = 0;
		this.pesoEnCursoTon = 0;
		this.id = -1;
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
	
	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String etiqueta() {
		if(this.valor != null && this.duracionViajeMin != 0)
			return ""+this.valor+"Km., "+this.duracionViajeMin+"min.";
		else
			if(this.valor == null && this.duracionViajeMin != 0)
				return "Dist. no disponible, "+this.duracionViajeMin+"min.";
		
			return "Distancia y Duración no disponibles";	}

}

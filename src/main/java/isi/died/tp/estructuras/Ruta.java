package isi.died.tp.estructuras;

import java.util.ArrayList;
import java.util.List;

import isi.died.tp.dao.util.CsvRecord;
import isi.died.tp.model.Planta;
import isi.died.tp.model.PlantaProduccion;

public class Ruta extends Arista<Planta> implements CsvRecord {

	public int id;
	private double duracionViajeMin;
	private int pesoMaxTon;
	private int pesoEnCursoTon;
	
	public Ruta(Vertice<Planta> ini, Vertice<Planta> fin, int distanciaKm, double duracionViaje, int pesoMax) {
		super(ini, fin, distanciaKm);
		this.duracionViajeMin = duracionViaje;
		this.pesoMaxTon = pesoMax;
		this.pesoEnCursoTon = 0;
	}
	
	public Ruta(int id, Vertice<Planta> ini, Vertice<Planta> fin, int distanciaKm, double duracionViaje, int pesoMax) {
		super(ini, fin, distanciaKm);
		this.duracionViajeMin = duracionViaje;
		this.pesoMaxTon = pesoMax;
		this.pesoEnCursoTon = 0;
		this.id = id;
	}

	public Ruta() {}

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



	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(duracionViajeMin);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + id;
		result = prime * result + pesoEnCursoTon;
		result = prime * result + pesoMaxTon;
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
		if (id != other.id)
			return false;
		if (pesoEnCursoTon != other.pesoEnCursoTon)
			return false;
		if (pesoMaxTon != other.pesoMaxTon)
			return false;
		return true;
	}

	@Override
	public List<String> asCsvRow() {
		List<String> lista = new ArrayList<String>();
		lista.add(this.id+"");
		if(this.getInicio().getValor() instanceof PlantaProduccion)
			lista.add("P");
		else lista.add("A");
		lista.add(Integer.toString(this.getInicio().getValor().getId()));
		if(this.getFin().getValor() instanceof PlantaProduccion)
			lista.add("P");
		else lista.add("A");
		lista.add(Integer.toString(this.getFin().getValor().getId()));
		lista.add(Integer.toString(this.getValor()));
		lista.add(Double.toString(this.duracionViajeMin));
		lista.add(Integer.toString(this.pesoMaxTon));
		return lista;
	}

	@Override
	public void loadFromStringRow(List<String> datos) {
		try {
			this.id = Integer.valueOf(datos.get(0));
			this.setValor( Integer.valueOf(datos.get(5)));
			this.duracionViajeMin = Double.valueOf(datos.get(6));
			this.pesoMaxTon = Integer.valueOf(datos.get(7)); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	
}

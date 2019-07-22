package isi.died.tp.model;

import java.util.ArrayList;
import java.util.List;

import isi.died.tp.dao.util.CsvRecord;

public class Camion implements CsvRecord {

	private int id;
	private String marca;
	private String modelo;
	private String dominio;
	private int año;
	private double costoKm;
	private Boolean aptoLiquidos;
	private double capacidad;
	
	
	
	
	public void setId(int id) {
		this.id = id;
	}

	public Camion(int id, double costoKm, Boolean aptoLiquidos, double capacidadKg) {
		super();
		this.id = id;
		this.costoKm = costoKm;
		this.aptoLiquidos = aptoLiquidos;
		this.capacidad = capacidadKg;
	}

	public Camion(int id, String marca, String modelo, String dominio, int año, double costoKm,
			Boolean aptoLiquidos, double capacidadKg) {
		super();
		this.id = id;
		this.marca = marca;
		this.modelo = modelo;
		this.dominio = dominio;
		this.año = año;
		this.costoKm = costoKm;
		this.aptoLiquidos = aptoLiquidos;
		this.capacidad = capacidadKg;
	}
	
	public Camion() {
	
	}

	public int getId() {
		return id;
	}
	
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getDominio() {
		return dominio;
	}
	public void setDominio(String dominio) {
		this.dominio = dominio;
	}
	public int getAño() {
		return año;
	}
	public void setAño(int año) {
		this.año = año;
	}
	public double getCostoKm() {
		return costoKm;
	}
	public void setCostoKm(double costoKm) {
		this.costoKm = costoKm;
	}
	public Boolean getAptoLiquidos() {
		return aptoLiquidos;
	}
	public void setAptoLiquidos(Boolean aptoLiquidos) {
		this.aptoLiquidos = aptoLiquidos;
	}
	public double getCapacidad() {
		return capacidad;
	}
	public void setCapacidad(double capacidad) {
		this.capacidad = capacidad;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aptoLiquidos == null) ? 0 : aptoLiquidos.hashCode());
		result = prime * result + año;
		long temp;
		temp = Double.doubleToLongBits(capacidad);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(costoKm);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((dominio == null) ? 0 : dominio.hashCode());
		result = prime * result + id;
		result = prime * result + ((marca == null) ? 0 : marca.hashCode());
		result = prime * result + ((modelo == null) ? 0 : modelo.hashCode());
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
		Camion other = (Camion) obj;
		if (aptoLiquidos == null) {
			if (other.aptoLiquidos != null)
				return false;
		} else if (!aptoLiquidos.equals(other.aptoLiquidos))
			return false;
		if (año != other.año)
			return false;
		if (Double.doubleToLongBits(capacidad) != Double.doubleToLongBits(other.capacidad))
			return false;
		if (Double.doubleToLongBits(costoKm) != Double.doubleToLongBits(other.costoKm))
			return false;
		if (dominio == null) {
			if (other.dominio != null)
				return false;
		} else if (!dominio.equals(other.dominio))
			return false;
		if (id != other.id)
			return false;
		if (marca == null) {
			if (other.marca != null)
				return false;
		} else if (!marca.equals(other.marca))
			return false;
		if (modelo == null) {
			if (other.modelo != null)
				return false;
		} else if (!modelo.equals(other.modelo))
			return false;
		return true;
	}

	public void loadFromStringRow(List<String> datos) {
		try {
			this.id = Integer.valueOf(datos.get(0));
			this.marca = datos.get(1);
			this.modelo = datos.get(2);
			this.dominio = datos.get(3);
			this.año = Integer.valueOf(datos.get(4)); 
			this.costoKm = Double.valueOf(datos.get(5));
			this.aptoLiquidos = Boolean.valueOf(datos.get(6));
			this.capacidad = Double.valueOf(datos.get(7));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<String> asCsvRow() {
		List<String> lista = new ArrayList<String>();
		lista.add(this.id+"");
		lista.add(this.marca);
		lista.add(this.modelo);
		lista.add(this.dominio);
		lista.add(Integer.toString(this.año));
		lista.add(Double.toString(this.costoKm));
		lista.add(Boolean.toString(this.aptoLiquidos));
		lista.add(Double.toString(this.capacidad));
		return lista;
	}


	

	
	
	
}

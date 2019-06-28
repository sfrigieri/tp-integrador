package isi.died.tp.dominio;

import java.util.List;
import java.util.stream.Collectors;

public abstract class Planta {

		protected int id;
		protected String nombre;
		
		protected Insumo buscarInsumo(int idInsumo) {
			return null;
		}
		
		public Boolean necesitaInsumo(Insumo i) {
			return null;
		}
		
		public Integer cantidadNecesariaInsumo(Insumo i) {
			 return 0;
		}
		
		public String getNombre() {
			return this.nombre;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
		
		
		
		
}

package isi.died.tp.dominio;

import java.util.List;

public abstract class Planta {

		protected int id;
		protected String nombre;
		
		
		protected Planta(int id, String nombre) {
			super();
			this.id = id;
			this.nombre = nombre;
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
		
		public Insumo getInsumo(int IdIns) {
			return null;
		}
		
		public void addInsumo(Insumo ins) {
			}
		
		public Boolean necesitaInsumo(Insumo i) {
			return false;
		}
		
		public Integer cantidadNecesariaInsumo(Insumo i) {
			 return 0;
		}
		
		public void removeInsumo(Insumo ins) {
			
			}
		
		public void addPedido(Planta planta, int IdIns,double cant){
		
		}
		
		public Double costoTotal() {
			return null;
			
			}
		

		public List<Insumo> stockEntre(Integer s1, Integer s2) {
			return null;
			
			}

		public void addStock(int id, int cantidad, int puntoPedido, Insumo ins) {

			}
		
		public Stock getStock(Insumo ins) {
			return null;
			
			}
		
		public Stock getStock(int id) {
			return null;
			
			}
		
		public Stock getStock(Stock s) {
			return null;
		}
		
		public void removeStock(Insumo ins) {

			}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + id;
			result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
			return result;
		}

		
		
		
		
}

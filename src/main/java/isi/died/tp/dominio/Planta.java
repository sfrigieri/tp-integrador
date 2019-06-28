package isi.died.tp.dominio;

import java.util.List;
import java.util.stream.Collectors;

public class Planta {

		int id;
		private List<Pedido> listaDePedidos;
		private List<Stock> listaDeStock;
		private String nombre;
		
		public void nuevoPedido(int idInsumo,double cant){
			Insumo ins = this.buscarInsumo(idInsumo);
			if(ins.hayStock(cant)) {
			ins.actualizarStock(cant);
			listaDePedidos.add(new Pedido());
			}
		}

		private Insumo buscarInsumo(int idInsumo) {
			
			return null;
		}
		
		public String getNombre() {
			return this.nombre;
		}
		
		public Double costoTotal() {
			return listaDeStock.stream().distinct().mapToDouble(( e)-> e.getInsumo().getCosto()*e.getCantidad()).sum();
			
			}
		
	
		public List<Insumo> stockEntre(Integer s1, Integer s2) {
			return listaDeStock.stream().filter((s)  -> ( s.getCantidad()>=s1 && s.getCantidad()<=s2)).map((s)  -> ( s.getInsumo())).collect(Collectors.toList());
			
			}
		
		
		public Boolean necesitaInsumo(Insumo i) {
			for(Stock stock : this.listaDeStock) {
				if(stock.getInsumo().equals(i) && stock.getPuntoPedido()-stock.getCantidad()>0)
					return true;
			}
			 return false;
		}
}

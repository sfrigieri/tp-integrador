package isi.died.tp.dominio;

import java.util.List;

import isi.died.tp.estructuras.ArbolBinarioBusqueda;

public class Planta extends ArbolBinarioBusqueda <Insumo> {

		List<Pedido> listaDePedidos;
		List<Insumo> listaDeInsumos;
		
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
	
}

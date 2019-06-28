package isi.died.tp.dominio;

import java.util.List;

public class PlantaAcopio extends Planta {
	
	private List<Pedido> listaDePedidos;
	private List<Stock> listaDeInsumos;
	
	@Override
	public Insumo buscarInsumo(int idInsumo) {
		
		return null;
	}
	
	public void nuevoPedido(Planta planta, int idInsumo,double cant){
		Insumo ins = this.buscarInsumo(idInsumo);
		if(ins.hayStock(cant)) {
		ins.actualizarStock(cant);
		listaDePedidos.add(new Pedido(planta,ins,cant));
		}
	}

	public List<Pedido> getListaDePedidos() {
		return listaDePedidos;
	}

	public void setListaDePedidos(List<Pedido> listaDePedidos) {
		this.listaDePedidos = listaDePedidos;
	}

	public List<Stock> getListaDeInsumos() {
		return listaDeInsumos;
	}

	public void setListaDeInsumos(List<Stock> listaDeInsumos) {
		this.listaDeInsumos = listaDeInsumos;
	}

}

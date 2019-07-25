package isi.died.tp.view.table;

import javax.swing.table.AbstractTableModel;

import isi.died.tp.model.Planta;
import isi.died.tp.model.PlantaAcopio;

import java.util.List;

public class PageRanksTableModel extends AbstractTableModel {

	
	private List<Planta> plantas;
	
	private String[] columns = {"Tipo Planta","Nombre","Page Rank"};

	@Override
	public String getColumnName(int indice) {
		return this.columns[indice];
	}
	

	
	public PageRanksTableModel(List<Planta> plantas) {
		this.plantas = plantas;
	}


	public List<Planta> getPlantas(){
		return this.plantas;
	}

	@Override
	public int getRowCount() {
		return plantas.size();
	}

	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object valor = null;
		switch (columnIndex) {
		case 0:
			valor = this.plantas.get(rowIndex) instanceof PlantaAcopio?"Acopio":"Producción";
			break;
		case 1:
			valor = this.plantas.get(rowIndex).getNombre();
			break;
		case 2:
			valor = (Math.round(this.plantas.get(rowIndex).getPageRank()*Math.pow(10, 2)))/Math.pow(10, 2);
			break;
		default:
			System.out.println("Out of Index");
			valor = "undetermined";
			break;
		}
		return valor;
	}
	

}


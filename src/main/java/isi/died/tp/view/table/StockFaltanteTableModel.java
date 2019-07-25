package isi.died.tp.view.table;

import java.awt.Color;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import isi.died.tp.model.InsumoLiquido;
import isi.died.tp.model.StockAcopio;

public class StockFaltanteTableModel extends JTable {

	
	public StockFaltanteTableModel() {
	super(0,4);
	DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	centerRenderer.setHorizontalAlignment( JLabel.CENTER );
	this.getColumnModel().getColumn(0).setPreferredWidth(60);
	this.getColumnModel().getColumn(1).setPreferredWidth(30);
	this.getColumnModel().getColumn(2).setPreferredWidth(60);
	this.getColumnModel().getColumn(3).setPreferredWidth(20);
	this.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
	this.getColumnModel().getColumn(0).setHeaderValue("Planta");
	this.getColumnModel().getColumn(1).setHeaderValue("Tipo Insumo");
	this.getColumnModel().getColumn(2).setHeaderValue("Descripción");
	this.getColumnModel().getColumn(3).setHeaderValue("Cantidad");
	this.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
	this.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
	this.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
	this.setFillsViewportHeight(true);
	this.setBorder(new LineBorder(new Color(0, 0, 0)));
	this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	DefaultCellEditor editor = (DefaultCellEditor) this.getDefaultEditor(Object.class);
	editor.setClickCountToStart(10000);
	
	
	}
	
	public void  agregarDatos(List<StockAcopio> stockFaltante) {
	
	DefaultTableModel model = (DefaultTableModel) this.getModel();
	for (StockAcopio stock : stockFaltante) {
		String valorTipo = "";
		if (stock.getInsumo() instanceof InsumoLiquido)
			valorTipo += "Líquido";
		else
			valorTipo += "General";
		model.addRow(new Object[]{stock.getPlanta().getNombre(), valorTipo, stock.getInsumo().getDescripcion(), Integer.toString(stock.getCantidad())});
	}
	}
}

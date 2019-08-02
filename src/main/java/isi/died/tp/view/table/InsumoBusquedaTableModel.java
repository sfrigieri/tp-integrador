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

import isi.died.tp.model.Insumo;
import isi.died.tp.model.InsumoLiquido;
import isi.died.tp.model.StockAcopio;

public class InsumoBusquedaTableModel extends JTable {
	
	public InsumoBusquedaTableModel() {
	super(0,6);
	DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	centerRenderer.setHorizontalAlignment( JLabel.CENTER );
	DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
	leftRenderer.setHorizontalAlignment( JLabel.LEFT );
	DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
	rightRenderer.setHorizontalAlignment( JLabel.RIGHT );
	this.getColumnModel().getColumn(0).setPreferredWidth(20);
	this.getColumnModel().getColumn(1).setPreferredWidth(30);
	this.getColumnModel().getColumn(2).setPreferredWidth(70);
	this.getColumnModel().getColumn(3).setPreferredWidth(35);
	this.getColumnModel().getColumn(4).setPreferredWidth(35);
	this.getColumnModel().getColumn(5).setPreferredWidth(35);
	this.getColumnModel().getColumn(0).setHeaderValue("ID");
	this.getColumnModel().getColumn(1).setHeaderValue("Tipo Insumo");
	this.getColumnModel().getColumn(2).setHeaderValue("Nombre");
	this.getColumnModel().getColumn(3).setHeaderValue("Cantidad Total");
	this.getColumnModel().getColumn(4).setHeaderValue("Costo");
	this.getColumnModel().getColumn(5).setHeaderValue("Peso");
	this.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
	this.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
	this.getColumnModel().getColumn(2).setCellRenderer(leftRenderer);
	this.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
	this.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
	this.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
	this.setFillsViewportHeight(true);
	this.setBorder(new LineBorder(new Color(0, 0, 0)));
	this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	DefaultCellEditor editor = (DefaultCellEditor) this.getDefaultEditor(Object.class);
	editor.setClickCountToStart(10000);
	
	
	}
	
	public void  agregarDatos(List<Insumo> resultado) {
	
	DefaultTableModel model = (DefaultTableModel) this.getModel();
	for (Insumo ins : resultado) {
		String valorTipo = "";
		if (ins instanceof InsumoLiquido)
			valorTipo += "Líquido";
		else
			valorTipo += "General";
		model.addRow(new Object[]{ins.getId(), valorTipo, 
				ins.getDescripcion(), Integer.toString(ins.getCantidadTotal()),
				"$ "+Double.toString(Math.round(ins.getCosto())),
				Double.toString(Math.round(ins.getPeso()))+" Kg."});
	}
	}
	
}

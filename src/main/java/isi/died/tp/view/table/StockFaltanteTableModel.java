package isi.died.tp.view.table;

import java.awt.Color;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;

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
	}
	
}

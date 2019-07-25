package isi.died.tp.view.table;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;

public class CamionTableModel extends JTable {

	
	public CamionTableModel() {
	super(0,8);
	DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	centerRenderer.setHorizontalAlignment( JLabel.CENTER );
	this.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
	this.getColumnModel().getColumn(0).setPreferredWidth(5);
	this.getColumnModel().getColumn(1).setPreferredWidth(90);
	this.getColumnModel().getColumn(2).setPreferredWidth(90);
	this.getColumnModel().getColumn(3).setPreferredWidth(75);
	this.getColumnModel().getColumn(4).setPreferredWidth(15);
	this.getColumnModel().getColumn(5).setPreferredWidth(30);
	this.getColumnModel().getColumn(6).setPreferredWidth(50);
	this.getColumnModel().getColumn(7).setPreferredWidth(40);
	this.getColumnModel().getColumn(0).setHeaderValue("Id");
	this.getColumnModel().getColumn(1).setHeaderValue("Marca");
	this.getColumnModel().getColumn(2).setHeaderValue("Modelo");
	this.getColumnModel().getColumn(3).setHeaderValue("Dominio");
	this.getColumnModel().getColumn(4).setHeaderValue("Año");
	this.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
	this.getColumnModel().getColumn(5).setHeaderValue("Costo Km");
	this.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
	this.getColumnModel().getColumn(6).setHeaderValue("Apto Líquidos");
	this.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
	this.getColumnModel().getColumn(7).setHeaderValue("Capacidad");
	this.setFillsViewportHeight(true);
	this.setBorder(new LineBorder(new Color(0, 0, 0)));
	this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	
}

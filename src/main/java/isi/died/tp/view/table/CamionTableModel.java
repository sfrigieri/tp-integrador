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

import isi.died.tp.model.Camion;

public class CamionTableModel extends JTable {

	
	public CamionTableModel() {
	super(0,8);
	DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	centerRenderer.setHorizontalAlignment( JLabel.CENTER );
	DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
	leftRenderer.setHorizontalAlignment( JLabel.LEFT );
	DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
	rightRenderer.setHorizontalAlignment( JLabel.RIGHT );
	this.getColumnModel().getColumn(0).setPreferredWidth(5);
	this.getColumnModel().getColumn(1).setPreferredWidth(85);
	this.getColumnModel().getColumn(2).setPreferredWidth(85);
	this.getColumnModel().getColumn(3).setPreferredWidth(55);
	this.getColumnModel().getColumn(4).setPreferredWidth(10);
	this.getColumnModel().getColumn(5).setPreferredWidth(40);
	this.getColumnModel().getColumn(6).setPreferredWidth(42);
	this.getColumnModel().getColumn(7).setPreferredWidth(50);
	this.getColumnModel().getColumn(0).setHeaderValue("Id");
	this.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
	this.getColumnModel().getColumn(1).setHeaderValue("Marca");
	this.getColumnModel().getColumn(1).setCellRenderer(leftRenderer);
	this.getColumnModel().getColumn(2).setHeaderValue("Modelo");
	this.getColumnModel().getColumn(2).setCellRenderer(leftRenderer);
	this.getColumnModel().getColumn(3).setHeaderValue("Dominio");
	this.getColumnModel().getColumn(3).setCellRenderer(leftRenderer);
	this.getColumnModel().getColumn(4).setHeaderValue("Año");
	this.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
	this.getColumnModel().getColumn(5).setHeaderValue("Costo Km");
	this.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
	this.getColumnModel().getColumn(6).setHeaderValue("Apto Líquidos");
	this.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
	this.getColumnModel().getColumn(7).setHeaderValue("Capacidad");
	this.getColumnModel().getColumn(7).setCellRenderer(rightRenderer);
	this.setFillsViewportHeight(true);
	this.setBorder(new LineBorder(new Color(0, 0, 0)));
	this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	DefaultCellEditor editor = (DefaultCellEditor) this.getDefaultEditor(Object.class);
	editor.setClickCountToStart(10000);
	
	
	
	}
	
	public void  agregarDatos(List<Camion> camionesDisponibles) {
		
	DefaultTableModel model = (DefaultTableModel) this.getModel();
	for (Camion c : camionesDisponibles) {
		String valorApto = "";
		if (c.getAptoLiquidos())
			valorApto += "SI";
		else
			valorApto += "NO";
		model.addRow(new Object[]{Integer.toString(c.getId()), c.getMarca(), c.getModelo(),c.getDominio(),
				Integer.toString(c.getAño()),"$ "+Double.toString(c.getCostoKm()), valorApto,Double.toString(c.getCapacidad())+" Kg" });
	}
	}
	
}

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

public class MejorSeleccionEnvioTableModel extends JTable {
	
	public MejorSeleccionEnvioTableModel() {
	super(0,6);
	DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	centerRenderer.setHorizontalAlignment( JLabel.CENTER );
	DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
	leftRenderer.setHorizontalAlignment( JLabel.LEFT );
	DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
	rightRenderer.setHorizontalAlignment( JLabel.RIGHT );
	this.getColumnModel().getColumn(0).setPreferredWidth(60);
	this.getColumnModel().getColumn(1).setPreferredWidth(30);
	this.getColumnModel().getColumn(2).setPreferredWidth(60);
	this.getColumnModel().getColumn(3).setPreferredWidth(15);
	this.getColumnModel().getColumn(4).setPreferredWidth(35);
	this.getColumnModel().getColumn(5).setPreferredWidth(35);
	this.getColumnModel().getColumn(0).setHeaderValue("Planta Destino");
	this.getColumnModel().getColumn(1).setHeaderValue("Tipo Insumo");
	this.getColumnModel().getColumn(2).setHeaderValue("Descripción");
	this.getColumnModel().getColumn(3).setHeaderValue("Cantidad");
	this.getColumnModel().getColumn(4).setHeaderValue("Precio");
	this.getColumnModel().getColumn(5).setHeaderValue("Peso");
	this.getColumnModel().getColumn(0).setCellRenderer(leftRenderer);
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
	
	public void  agregarDatos(List<StockAcopio> stockResultado) {
	
	DefaultTableModel model = (DefaultTableModel) this.getModel();
	for (StockAcopio s : stockResultado) {
		String valorTipo = "";
		if (s.getInsumo() instanceof InsumoLiquido)
			valorTipo += "Líquido";
		else
			valorTipo += "General";
		model.addRow(new Object[]{s.getPlanta().getNombre(), valorTipo, 
				s.getInsumo().getDescripcion(), Integer.toString(s.getCantidad()),
				"$ "+Double.toString(Math.round(s.getInsumo().getCosto()*s.getCantidad())),
				Double.toString(Math.round(s.getCantidad()*s.getInsumo().getPeso()))+" Kg."});
	}
	}
	
}

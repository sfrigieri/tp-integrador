package isi.died.tp.view.table;

import javax.swing.DefaultCellEditor;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import isi.died.tp.model.Planta;
import isi.died.tp.model.PlantaAcopio;

import java.awt.Color;
import java.util.List;

public class PageRanksTableModel extends JTable {

	public PageRanksTableModel() {
		super(0,3);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
		leftRenderer.setHorizontalAlignment( JLabel.LEFT );
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment( JLabel.RIGHT );
		this.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		this.getColumnModel().getColumn(1).setCellRenderer(leftRenderer);
		this.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
		this.getColumnModel().getColumn(0).setPreferredWidth(40);
		this.getColumnModel().getColumn(1).setPreferredWidth(100);
		this.getColumnModel().getColumn(2).setPreferredWidth(10);
		this.getColumnModel().getColumn(0).setHeaderValue("Tipo Planta");
		this.getColumnModel().getColumn(1).setHeaderValue("Nombre");
		this.getColumnModel().getColumn(2).setHeaderValue("Page Rank");
		this.setFillsViewportHeight(true);
		this.setBorder(new LineBorder(new Color(0, 0, 0)));
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		DefaultCellEditor editor = (DefaultCellEditor) this.getDefaultEditor(Object.class);
		editor.setClickCountToStart(10000);
		this.setFillsViewportHeight(true);



	}

	public void  agregarDatos(List<Planta> plantas) {

		DefaultTableModel model = (DefaultTableModel) this.getModel();
		for (Planta p : plantas) {
			String valorTipo = "";
			if (p instanceof PlantaAcopio)
				valorTipo += "Acopio";
			else
				valorTipo += "Producción";
			model.addRow(new Object[]{valorTipo,p.getNombre(),Double.toString(Math.round((p.getPageRank()*Math.pow(10, 2)))/Math.pow(10, 2)) });
		}
	}


}


package isi.died.tp.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import isi.died.tp.controller.GestionEnviosController;
import isi.died.tp.model.Camion;
import isi.died.tp.model.InsumoLiquido;
import isi.died.tp.model.Planta;
import isi.died.tp.model.PlantaAcopio;
import isi.died.tp.model.PlantaProduccion;
import isi.died.tp.model.StockAcopio;

public class MejorSeleccionEnvioView {
	
	private static JFrame ventana;
	private GestionEnviosController gec;
	
	public MejorSeleccionEnvioView(JFrame v, GestionEnviosController gec) {
		ventana = v;
		this.gec = gec;
	}
	
	public void calcularMejorSeleccion(Boolean mostrarTablaStock) {
	JPanel panel = new JPanel(new GridBagLayout());
	GridBagConstraints constraints = new GridBagConstraints();
	JLabel encabezado = new JLabel("Insumos Faltantes"), errorSeleccion = new JLabel();
	JButton generarSolucion = new JButton("Generar Solución"), volver = new JButton("Volver"),
			cambiarTabla = new JButton("Ver Camiones Disponibles");
	JTable tabla;
	List<StockAcopio> stockFaltante = gec.generarStockFaltante();
	List<Camion> camionesDisponibles = gec.listaCamiones();

	DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	centerRenderer.setHorizontalAlignment( JLabel.CENTER );
	DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
	leftRenderer.setHorizontalAlignment( JLabel.LEFT );
	
	if (mostrarTablaStock) {
		tabla = new JTable(0,4);
		tabla.getColumnModel().getColumn(0).setPreferredWidth(60);
		tabla.getColumnModel().getColumn(1).setPreferredWidth(30);
		tabla.getColumnModel().getColumn(2).setPreferredWidth(60);
		tabla.getColumnModel().getColumn(3).setPreferredWidth(20);
		tabla.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		tabla.getColumnModel().getColumn(0).setHeaderValue("Planta");
		tabla.getColumnModel().getColumn(1).setHeaderValue("Tipo Insumo");
		tabla.getColumnModel().getColumn(2).setHeaderValue("Descripción");
		tabla.getColumnModel().getColumn(3).setHeaderValue("Cantidad");
		tabla.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		tabla.getColumnModel().getColumn(1).setCellRenderer(leftRenderer);
	} else {
		tabla = new JTable(0,8);
		tabla.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		tabla.getColumnModel().getColumn(0).setPreferredWidth(5);
		tabla.getColumnModel().getColumn(1).setPreferredWidth(90);
		tabla.getColumnModel().getColumn(2).setPreferredWidth(90);
		tabla.getColumnModel().getColumn(3).setPreferredWidth(75);
		tabla.getColumnModel().getColumn(4).setPreferredWidth(15);
		tabla.getColumnModel().getColumn(5).setPreferredWidth(30);
		tabla.getColumnModel().getColumn(6).setPreferredWidth(50);
		tabla.getColumnModel().getColumn(7).setPreferredWidth(40);
		tabla.getColumnModel().getColumn(0).setHeaderValue("Id");
		tabla.getColumnModel().getColumn(1).setHeaderValue("Marca");
		tabla.getColumnModel().getColumn(2).setHeaderValue("Modelo");
		tabla.getColumnModel().getColumn(3).setHeaderValue("Dominio");
		tabla.getColumnModel().getColumn(4).setHeaderValue("Año");
		tabla.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
		tabla.getColumnModel().getColumn(5).setHeaderValue("Costo Km");
		tabla.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
		tabla.getColumnModel().getColumn(6).setHeaderValue("Apto Líquidos");
		tabla.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
		tabla.getColumnModel().getColumn(7).setHeaderValue("Capacidad");
	}
	
	//tabla
	constraints.insets=new Insets(5, 5, 0, 5);
	tabla.setFillsViewportHeight(true);
	tabla.setBorder(new LineBorder(new Color(0, 0, 0)));
	tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	JScrollPane scroll = new JScrollPane(tabla,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	scroll.setPreferredSize(new Dimension(700, 197));
	
	
	constraints.gridx=1;
	constraints.gridy=1;
	constraints.gridheight=1;
	constraints.gridwidth=3;
	constraints.weightx=1;
	panel.add(scroll, constraints);
	
	//agregar datos tabla
	DefaultTableModel model = (DefaultTableModel) tabla.getModel();
	DefaultTableModel modeloDefecto = new DefaultTableModel() {
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};
	DefaultCellEditor editor = (DefaultCellEditor) tabla.getDefaultEditor(Object.class);
	editor.setClickCountToStart(10000);
	
	if (mostrarTablaStock) {
		cambiarTabla.setText("Insumos Faltantes");
		cambiarTabla.setText("Ver Camiones Disponibles");
		for (StockAcopio stock : stockFaltante) {
			String valorTipo = "";
			if (stock.getInsumo() instanceof InsumoLiquido)
				valorTipo += "Líquido";
			else
				valorTipo += "General";
			model.addRow(new Object[]{stock.getPlanta().getNombre(), valorTipo, stock.getInsumo().getDescripcion(), Integer.toString(stock.getCantidad())});
		}
	} else {
		encabezado.setText("Camiones Disponibles");
		cambiarTabla.setText("Ver Insumos Faltantes");
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

	//titulo
	constraints.gridx=0;
	constraints.gridy=0;
	constraints.gridheight=1;
	constraints.gridwidth=8;
	constraints.anchor=GridBagConstraints.NORTH;
	encabezado.setFont(new Font(encabezado.getFont().getName(), encabezado.getFont().getStyle(), 30));
	panel.add(encabezado,constraints);
	
	//botones
	constraints.gridy=3;
	constraints.fill=GridBagConstraints.NONE;
	constraints.anchor=GridBagConstraints.EAST;
	
	constraints.gridx=0;
	constraints.insets=new Insets(5, 5, 5, 250);
	volver.addActionListener(a -> GestionEnvios.mostrarMenu());
	panel.add(volver, constraints);
	
	constraints.anchor=GridBagConstraints.WEST;
	constraints.insets=new Insets(5, 250, 5, 5);
	constraints.gridx=2;
	generarSolucion.addActionListener(a -> {
		errorSeleccion.setText("");
		int numFila = tabla.getSelectedRow();
		if (numFila == -1 || mostrarTablaStock) {
			JOptionPane.showConfirmDialog(null,"Debes seleccionar un camión.","Error",
					JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE);
			return;
		} else {
			if (!mostrarTablaStock) {
				int idCamion = Integer.valueOf((String)tabla.getValueAt(numFila, 0));
				JOptionPane.showConfirmDialog(null,"El resultado estará sujeto al stock disponible.","Verificando Disponibilidad",
						JOptionPane.DEFAULT_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
				mostrarTablaMejorSeleccion(idCamion);
				}
			 else {
				 JOptionPane.showConfirmDialog(null,"Debes seleccionar un camión.","Error",
							JOptionPane.DEFAULT_OPTION,
							JOptionPane.ERROR_MESSAGE);
			}
		}
	});
	panel.add(generarSolucion,constraints);
	
	constraints.gridy=2;
	constraints.gridx=0;
	constraints.anchor=GridBagConstraints.CENTER;
	constraints.insets = new Insets(1,0,39,500);
	cambiarTabla.setPreferredSize(new Dimension(200, 25));
	cambiarTabla.addActionListener(a -> {
		if (cambiarTabla.getText() == "Ver Camiones Disponibles") {
			this.calcularMejorSeleccion(false);
		} else if (cambiarTabla.getText() == "Ver Insumos Faltantes"){
			this.calcularMejorSeleccion(true);
		}
	});
	panel.add(cambiarTabla, constraints);
	
	//error seleccion
	constraints.anchor=GridBagConstraints.NORTHWEST;
	constraints.insets.set(6,350,0,0);
	errorSeleccion.setPreferredSize(new Dimension(360, 16));
	constraints.gridx=3;
	constraints.gridy=2;
	errorSeleccion.setForeground(Color.red);
	panel.add(errorSeleccion,constraints);
	
	ventana.setContentPane(panel);
	ventana.pack();
	ventana.setSize(800, 600);
	ventana.setLocationRelativeTo(null);
	ventana.setTitle("Gestión General de Envíos");
	ventana.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	ventana.setVisible(true);
}
	
	public static void mostrarTablaMejorSeleccion(int idCamion) {};
}

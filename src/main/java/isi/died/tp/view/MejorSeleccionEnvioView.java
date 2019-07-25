package isi.died.tp.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;


import isi.died.tp.controller.GestionEnviosController;
import isi.died.tp.model.Camion;
import isi.died.tp.model.StockAcopio;
import isi.died.tp.view.table.CamionTableModel;
import isi.died.tp.view.table.StockFaltanteTableModel;

public class MejorSeleccionEnvioView {

	private static JFrame ventana;
	private GestionEnviosController gec;

	public MejorSeleccionEnvioView(JFrame v, GestionEnviosController gec) {
		ventana = v;
		this.gec = gec;
	}

	public void calcularMejorSeleccion(Boolean mostrarTablaStock) {
		
		if(!gec.existenPlantas()) {
			JOptionPane.showConfirmDialog(null,"Aún no existen Plantas en el Sistema.","Acción Interrumpida",
					JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if(!gec.existenCamiones()) {
			JOptionPane.showConfirmDialog(null,"Aún no existen Camiones en el Sistema.","Acción Interrumpida",
					JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if(gec.generarStockFaltante().isEmpty()) {
			JOptionPane.showConfirmDialog(null,"El Sistema no registra falta de Insumos.","Acción Interrumpida",
					JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		JLabel encabezado = new JLabel("Insumos Faltantes"), errorSeleccion = new JLabel();
		JButton generarSolucion = new JButton("Generar Solución"), volver = new JButton("Volver"),
				cambiarTabla = new JButton("Ver Camiones Disponibles");
		JTable tabla;
		List<StockAcopio> stockFaltante = gec.generarStockFaltante();
		List<Camion> camionesDisponibles = gec.listaCamiones();


		if (mostrarTablaStock) {
			tabla = new StockFaltanteTableModel();
			cambiarTabla.setText("Insumos Faltantes");
			cambiarTabla.setText("Ver Camiones Disponibles");
			((StockFaltanteTableModel) tabla).agregarDatos(stockFaltante);

		} else {
			tabla = new CamionTableModel();
			encabezado.setText("Camiones Disponibles");
			cambiarTabla.setText("Ver Insumos Faltantes");
			((CamionTableModel) tabla).agregarDatos(camionesDisponibles);

		}

		constraints.insets=new Insets(5, 5, 0, 5);
		JScrollPane scroll = new JScrollPane(tabla,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setPreferredSize(new Dimension(700, 197));

		constraints.gridx=1;
		constraints.gridy=1;
		constraints.gridheight=1;
		constraints.gridwidth=3;
		constraints.weightx=1;
		panel.add(scroll, constraints);




		//titulo
		constraints.insets.set(0,0,60,0);
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

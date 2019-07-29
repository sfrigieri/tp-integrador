package isi.died.tp.view;

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
import isi.died.tp.model.InsumoLiquido;
import isi.died.tp.model.StockAcopio;
import isi.died.tp.view.table.CamionTableModel;
import isi.died.tp.view.table.MejorSeleccionEnvioTableModel;
import isi.died.tp.view.table.StockFaltanteTableModel;

public class MejorSeleccionEnvioView {

	private static JFrame ventana;
	private static GestionEnviosController gec;

	public MejorSeleccionEnvioView(JFrame v, GestionEnviosController gecontroller) {
		ventana = v;
		gec = gecontroller;
	}

	public void calcularMejorSeleccion(Boolean mostrarTablaStock) {

		if(!gec.existenPlantas()) {
			JOptionPane.showConfirmDialog(null,"Aún no se registran Plantas en el Sistema.","Acción Interrumpida",
					JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		if(!gec.existenCamiones()) {
			JOptionPane.showConfirmDialog(null,"Aún no se registran Camiones en el Sistema.","Acción Interrumpida",
					JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		if(gec.generarStockFaltante().isEmpty()) {
			JOptionPane.showConfirmDialog(null,"El Sistema no registra falta de Insumos.","Acción Interrumpida",
					JOptionPane.DEFAULT_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		JLabel encabezado = new JLabel("Insumos Faltantes");
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


		constraints.insets.set(0,0,60,0);
		constraints.gridx=0;
		constraints.gridy=0;
		constraints.gridheight=1;
		constraints.gridwidth=8;
		constraints.anchor=GridBagConstraints.NORTH;
		encabezado.setFont(new Font(encabezado.getFont().getName(), encabezado.getFont().getStyle(), 30));
		panel.add(encabezado,constraints);


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
			int numFila = tabla.getSelectedRow();
			if (numFila == -1 || mostrarTablaStock) {
				JOptionPane.showConfirmDialog(null,"Debes seleccionar un Camión.","Error",
						JOptionPane.DEFAULT_OPTION,
						JOptionPane.ERROR_MESSAGE);
				return;
			} else {
				if (!mostrarTablaStock)
					mostrarTablaMejorSeleccion(Integer.valueOf((String)tabla.getValueAt(numFila, 0)));
				else
					JOptionPane.showConfirmDialog(null,"Debes seleccionar un Camión.","Error",
							JOptionPane.DEFAULT_OPTION,
							JOptionPane.ERROR_MESSAGE);
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


		ventana.setContentPane(panel);
		ventana.pack();
		ventana.setSize(800, 600);
		ventana.setLocationRelativeTo(null);
		ventana.setTitle("Gestión General de Envíos");
		ventana.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		ventana.setVisible(true);
	}

	public static void mostrarTablaMejorSeleccion(int idCamion) {


		List<StockAcopio> stocks = gec.generarStockFaltanteDisponible();
		if(stocks.isEmpty()){
			JOptionPane.showConfirmDialog(null,"En este momento no se registra Stock disponible en el Sistema.","Acción Interrumpida",
					JOptionPane.DEFAULT_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}	
		else {
			Camion camion = gec.buscarCamion(idCamion);
			if(camion == null){
				JOptionPane.showConfirmDialog(null,"El Camión seleccionado no está disponible.","Acción Interrumpida",
						JOptionPane.DEFAULT_OPTION,
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			else {
				if(!camion.getAptoLiquidos()) {
					JOptionPane.showConfirmDialog(null,"Camión seleccionado no apto para Insumos líquidos.","Advertencia",
							JOptionPane.DEFAULT_OPTION,
							JOptionPane.WARNING_MESSAGE);

					Boolean soloLiquidos = true;
					for(StockAcopio s : stocks) {
						if(!(s.getInsumo() instanceof InsumoLiquido))
							soloLiquidos = false;
					}

					if(soloLiquidos) {						
						JOptionPane.showConfirmDialog(null,"No se dispone de Stock apto para el Camión seleccionado.","Acción Interrumpida",
								JOptionPane.DEFAULT_OPTION,
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
				}

				JOptionPane.showConfirmDialog(null,"El resultado estará sujeto al Stock disponible.","Verificando Disponibilidad",
						JOptionPane.DEFAULT_OPTION,
						JOptionPane.INFORMATION_MESSAGE);

				List<StockAcopio> mejorSeleccion = gec.generarMejorSeleccionEnvio(camion, stocks);

				if(mejorSeleccion.isEmpty()) {
					JOptionPane.showConfirmDialog(null,"El Stock mínimo solicitado excede la Capacidad del Camión.","Acción Interrumpida",
							JOptionPane.DEFAULT_OPTION,
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				else {
					GridBagConstraints constraints = new GridBagConstraints();
					JLabel encabezado = new JLabel("Mejor Selección de Envío"), infoExtra = new JLabel(),
							encabezado2 = new JLabel("Stock Faltante Remanente");
					JFrame popup = new JFrame("Mejor selección disponible para Envío");
					JPanel panel = new JPanel(new GridBagLayout());
					JTable table, table2;
					popup.setDefaultCloseOperation(WindowConstants. DISPOSE_ON_CLOSE);
					panel.setPreferredSize( new Dimension(850,650));
					table = new MejorSeleccionEnvioTableModel();
					((MejorSeleccionEnvioTableModel) table).agregarDatos(mejorSeleccion);
					table.setFillsViewportHeight(true);
					JScrollPane scroll = new JScrollPane(table,
							JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
							JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
					scroll.setPreferredSize(new Dimension(650,200));




					constraints.insets=new Insets(5, 5, 30, 5);
					constraints.gridx=1;
					constraints.gridy=1;
					constraints.gridheight=1;
					constraints.gridwidth=3;
					constraints.weightx=1;
					panel.add(scroll, constraints);


					constraints.insets.set(0,0,60,0);
					constraints.gridx=0;
					constraints.gridy=0;
					constraints.gridheight=1;
					constraints.gridwidth=8;
					constraints.anchor=GridBagConstraints.NORTH;
					encabezado.setFont(new Font(encabezado.getFont().getName(), encabezado.getFont().getStyle(), 30));
					panel.add(encabezado,constraints);

					constraints.insets.set(0,0,5,0);
					constraints.gridx=0;
					constraints.gridy=0;
					constraints.gridheight=1;
					constraints.gridwidth=8;
					constraints.anchor=GridBagConstraints.SOUTH;
					infoExtra.setFont(new Font(infoExtra.getFont().getName(), infoExtra.getFont().getStyle(), 13));
					infoExtra.setText("Capacidad Camión: "+camion.getCapacidad()+" Kg."+
							"        Peso Stock Seleccionado: "+Double.toString(gec.pesoTotalEnvio(mejorSeleccion))+" Kg."+
							"        Costo Total: $"+Double.toString(gec.costoTotalEnvio(mejorSeleccion)));
					panel.add(infoExtra,constraints);


					constraints.insets.set(20,100,10,0);
					constraints.gridx=0;
					constraints.gridy=3;
					constraints.gridheight=1;
					constraints.gridwidth=8;
					constraints.anchor=GridBagConstraints.WEST;
					encabezado2.setFont(new Font(encabezado2.getFont().getName(), encabezado2.getFont().getStyle(), 20));
					panel.add(encabezado2,constraints);

					table2 = new MejorSeleccionEnvioTableModel();
					((MejorSeleccionEnvioTableModel) table2).agregarDatos(gec.stockRemanente(stocks, mejorSeleccion));
					//table2.setFillsViewportHeight(true);
					JScrollPane scroll2 = new JScrollPane(table2,
							JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
							JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
					scroll2.setPreferredSize(new Dimension(650,150));



					constraints.insets=new Insets(5, 100, 40, 5);
					constraints.gridx=1;
					constraints.gridy=4;
					constraints.gridheight=1;
					constraints.gridwidth=3;
					constraints.weightx=1;
					panel.add(scroll2, constraints);
					popup.setContentPane(panel);
					popup.pack();
					popup.setLocationRelativeTo(ventana);
					popup.setVisible(true);


				}


			}

		}

	}


}

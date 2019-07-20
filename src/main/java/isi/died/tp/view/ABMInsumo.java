package isi.died.tp.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import isi.died.tp.controller.InsumoController;
import isi.died.tp.model.Insumo;
import isi.died.tp.model.InsumoLiquido;
import isi.died.tp.model.Unidad;
import isi.died.tp.controller.StockController;

public class ABMInsumo {

	private InsumoController controller;
	private StockController sc;
	private JFrame ventana;
	
	public ABMInsumo(JFrame ventana,InsumoController ic, StockController sc){
		this.controller = ic;
		this.ventana = ventana;
		this.sc = sc;
	}
	
	public void agregarInsumo() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		JLabel errorDescripcion = new JLabel(), errorCosto = new JLabel(),
				errorPesoCantidad = new JLabel(), errorDensidad = new JLabel(),
				encabezado = new JLabel("Agregar Nuevo Insumo");
		JTextField descripcion = new JTextField(20), costo = new JTextField(20),
					pesoCantidad = new JTextField(20), densidad = new JTextField(20);
		JButton aceptar = new JButton("Aceptar"), volver = new JButton("Volver");
		JComboBox<Unidad> unidad = new JComboBox<Unidad>();
		final ButtonGroup checkboxRefrigerado = new ButtonGroup(), 
						  checkboxLiquido = new ButtonGroup();
		JCheckBox esLiquido = new JCheckBox("Es líquido"), noLiquido = new JCheckBox("No es líquido"),
				  esRefrigerado = new JCheckBox("Refrigerado"), noRefrigerado = new JCheckBox("No Refrigerado");
		checkboxLiquido.add(esLiquido);
		checkboxLiquido.add(noLiquido);
		checkboxRefrigerado.add(esRefrigerado);
		checkboxRefrigerado.add(noRefrigerado);
			
		//titulo
		constraints.insets=new Insets(5, 5, 40, 5);
		constraints.gridx=0;
		constraints.gridy=0;
		constraints.gridheight=1;
		constraints.gridwidth=8;
		constraints.anchor=GridBagConstraints.NORTH;
		encabezado.setFont(new Font(encabezado.getFont().getName(), encabezado.getFont().getStyle(), 40));
		panel.add(encabezado,constraints);
	
		//labels
		constraints.fill=GridBagConstraints.NONE;
		constraints.anchor=GridBagConstraints.EAST;
		constraints.insets.set(5, 5, 15, 5);
		constraints.gridx=1;
		constraints.gridwidth=1;
		
		constraints.gridy=2;
		panel.add(new JLabel("Descripción: "), constraints);
		
		constraints.gridy=3;
		panel.add(new JLabel("Costo ($): "), constraints);
		
		constraints.gridy=4;
		panel.add(new JLabel("Peso (Kg): "), constraints);

		constraints.gridy=5;
		panel.add(new JLabel("Densidad (Kg/m3): "), constraints);
		
		constraints.gridy=6;
		panel.add(new JLabel("Refrigeración: "), constraints);
		
		constraints.gridy=7;
		panel.add(new JLabel("Unidad: "), constraints);
		
		//checkboxs
		constraints.gridx=2;
		
		constraints.anchor=GridBagConstraints.EAST;
		constraints.gridy=1;
		panel.add(noLiquido, constraints);
		constraints.gridy=6;
		panel.add(noRefrigerado, constraints);
		constraints.anchor=GridBagConstraints.WEST;
		constraints.gridy=1;
		panel.add(esLiquido, constraints);
		constraints.gridy=6;
		panel.add(esRefrigerado, constraints);
		noLiquido.setSelected(true);
		noRefrigerado.setSelected(true);
		
		//campos de texto
		constraints.fill=GridBagConstraints.HORIZONTAL;
		constraints.anchor=GridBagConstraints.CENTER;
		
		constraints.gridy=2;
		panel.add(descripcion, constraints);

		constraints.gridy=3;
		panel.add(costo, constraints);
		
		constraints.gridy=4;
		panel.add(pesoCantidad, constraints);
		
		constraints.gridy=5;
		panel.add(densidad, constraints);
		densidad.setEnabled(false);
		
		constraints.gridy=7;
		unidad.addItem(Unidad.KILO);
		unidad.addItem(Unidad.METRO);
		unidad.addItem(Unidad.M2);
		unidad.addItem(Unidad.LITRO);
		unidad.setSelectedItem(Unidad.KILO);
		panel.add(unidad, constraints);
		
		//listener checkboxLiquido
		ItemListener esLiquidoListener = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				int estado = itemEvent.getStateChange();
				if (estado == ItemEvent.SELECTED) { //si es liquido-- 	 descrip, unidad(LITRO) , double costo, boolean esRef, double dens, double litros
					unidad.setSelectedItem(Unidad.LITRO);
					unidad.setEnabled(false);;
					densidad.setEnabled(true);
				} else {							//si no es liquido-- descrip, unidadDeMedida, double costo, boolean esRefrigerado,double peso
					unidad.setEnabled(true);
					unidad.setSelectedItem(Unidad.KILO);
					densidad.setEnabled(false);
				}
			}
		};
		esLiquido.addItemListener(esLiquidoListener);
		
		//botones
		constraints.insets.set(15,0,0,0);
		constraints.gridy=10;
		constraints.fill=GridBagConstraints.NONE;
		constraints.anchor=GridBagConstraints.EAST;
		
		constraints.gridx=2;
		volver.addActionListener(a -> GestionEntidades.mostrarMenu());
		panel.add(volver, constraints);
						
		constraints.gridx=1;
		aceptar.addActionListener(a -> {
			String valorDescripcion;
			Double valorCosto = null, valorPeso = null;
			Boolean valorRefrigeracion = false;
			Unidad valorUnidad;
			Double valorDensidad = null;
					
			errorDescripcion.setText("");
			errorCosto.setText("");
			errorPesoCantidad.setText("");
			errorDensidad.setText("");
			try {
				if(descripcion.getText().isEmpty()) {
					errorDescripcion.setText("Debe ingresar una descripción");
					return;
				}else {
					valorDescripcion = descripcion.getText();
				}
				if(costo.getText().isEmpty()) {
					errorCosto.setText("Debe ingresar un costo");
					return;
				}else {
					valorCosto = Double.parseDouble(costo.getText());
				}
				if(pesoCantidad.getText().isEmpty()) {
					if (!esLiquido.isSelected()) {
						errorPesoCantidad.setText("Debe ingresar un peso");
					} else {
						errorPesoCantidad.setText("Debe ingresar una cantidad");
					}
					return;
				}else{
					valorPeso = Double.parseDouble(pesoCantidad.getText());
					System.out.println(pesoCantidad.getText());
					System.out.println(Double.parseDouble(pesoCantidad.getText()));
				}
				if(esLiquido.isSelected() && densidad.getText().isEmpty()) {
					errorDensidad.setText("Debe ingresar una densidad");
					return;
				} else if (esLiquido.isSelected()){
					valorDensidad = Double.parseDouble(densidad.getText());
				}
						
				valorUnidad = (Unidad)unidad.getSelectedItem();
				valorRefrigeracion = esRefrigerado.isSelected();
						
				if(JOptionPane.showConfirmDialog(ventana, "¿Desea guardar el nuevo insumo con los datos ingresados?","Confirmación",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==0) {
					if (esLiquido.isSelected()) { //Sería valorLitros en lugar de valorPeso, el peso se calcula internamente
						controller.agregarInsumo(0, valorDescripcion, valorCosto, valorRefrigeracion, valorDensidad, valorPeso);
					} else {
						controller.agregarInsumo(0, valorDescripcion, valorUnidad, valorCosto, valorPeso, valorRefrigeracion);
					}
					JOptionPane.showConfirmDialog(ventana, "Insumo creado exitosamente.", "Información", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
					GestionEntidades.mostrarMenu();
				}					
						
			}catch(NumberFormatException nfex) {
				if(valorCosto == null) {
					JOptionPane.showConfirmDialog(ventana, "El campo Costo debe ser numérico.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);	
				} else {
					if(valorPeso == null) {
						if (esLiquido.isSelected()) {
							JOptionPane.showConfirmDialog(ventana, "El campo Cantidad debe ser numérico.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);	
						} else {
							JOptionPane.showConfirmDialog(ventana, "El campo Peso debe ser numérico.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);	
						}
					} else {
						if (valorDensidad == null) {
							JOptionPane.showConfirmDialog(ventana, "El campo Densidad debe ser numérico.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);	
						}
					}
				}
			}
			
		});
		panel.add(aceptar, constraints);
				
		constraints.anchor=GridBagConstraints.NORTHWEST;
		constraints.insets.set(5,0,3,0);
		constraints.gridx=3;
		constraints.gridy=2;
		errorDescripcion.setPreferredSize(new Dimension(230, 16));
		errorDescripcion.setForeground(Color.red);
		panel.add(errorDescripcion,constraints);
				
		constraints.gridy=3;
		errorCosto.setPreferredSize(new Dimension(230, 16));
		errorCosto.setForeground(Color.red);
		panel.add(errorCosto,constraints);
				
		constraints.gridy=4;
		errorPesoCantidad.setPreferredSize(new Dimension(230, 16));
		errorPesoCantidad.setForeground(Color.red);
		panel.add(errorPesoCantidad,constraints);
		
		constraints.gridy=5;
		errorDensidad.setPreferredSize(new Dimension(230, 16));
		errorDensidad.setForeground(Color.red);
		panel.add(errorDensidad,constraints);
				
		ventana.setContentPane(panel);
		ventana.pack();
		ventana.setSize(800, 600);
		ventana.setLocationRelativeTo(null);
		ventana.setTitle("Sistema de Gestión de Entidades");
		ventana.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		ventana.setVisible(true);
	}

	public void modificarInsumo() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		JLabel encabezado = new JLabel("Modificar Insumo");
		JButton aceptar = new JButton("Aceptar"), volver = new JButton("Volver");
		JComboBox<String> seleccionarInsumo = new JComboBox<String>();
		
		//titulo
		constraints.gridx=0;
		constraints.gridy=0;
		constraints.gridheight=1;
		constraints.gridwidth=8;
		constraints.anchor=GridBagConstraints.NORTH;
		encabezado.setFont(new Font(encabezado.getFont().getName(), encabezado.getFont().getStyle(), 40));
		panel.add(encabezado,constraints);
		
		//combobox seleccion
		constraints.fill=GridBagConstraints.HORIZONTAL;
		constraints.anchor=GridBagConstraints.CENTER;
		constraints.insets.set(25, 5, 25, 5);
		constraints.gridx=0;
		constraints.gridy=1;
		panel.add(seleccionarInsumo, constraints);
		
		for (Insumo ins : controller.listaInsumos())
			seleccionarInsumo.addItem(Integer.toString(ins.getId()) + ": " + ins.getDescripcion());
		for (Insumo ins : controller.listaInsumosLiquidos())
			seleccionarInsumo.addItem(Integer.toString(ins.getId()) + ": " + ins.getDescripcion() + " (L)");
		
		//botones
		constraints.gridy=2;
		constraints.fill=GridBagConstraints.NONE;
		constraints.anchor=GridBagConstraints.EAST;
		
		constraints.gridx=0;
		constraints.insets=new Insets(5, 5, 5, 15);
		volver.addActionListener(a -> GestionEntidades.mostrarMenu());
		panel.add(volver, constraints);
				
		constraints.anchor=GridBagConstraints.WEST;
		constraints.insets=new Insets(5, 15, 5, 5);
		constraints.gridx=0;
		aceptar.addActionListener(a -> {
			String stringInsumo = (String)seleccionarInsumo.getSelectedItem();
			String arr[] = stringInsumo.split(": ");
			Integer idSeleccionado = Integer.valueOf(arr[0]);
			String segParte[] = arr[1].split(" ");
			String ultimaPalabra = segParte[segParte.length-1];
			
			if (ultimaPalabra.equals("(L)")) {
				this.modificarInsumo((InsumoLiquido)controller.buscarInsumoLiquido(idSeleccionado));
			} else {
				this.modificarInsumo(controller.buscarInsumoNoLiquido(idSeleccionado));
			}
		});
		panel.add(aceptar,constraints);
		
		ventana.setContentPane(panel);
		ventana.pack();
		ventana.setSize(800, 600);
		ventana.setLocationRelativeTo(null);
		ventana.setTitle("Sistema de Gestión de Entidades");
		ventana.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		ventana.setVisible(true);
	}
	
	public void modificarInsumo(Insumo insumo) {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		JLabel encabezado = new JLabel("Modificar Insumo");
		JButton guardarCambios = new JButton("Guardar"), volver = new JButton("Volver");
		JLabel errorDescripcion = new JLabel(), errorCosto = new JLabel(), errorPesoCantidad = new JLabel(), errorDensidad = new JLabel();
		JTextField descripcion = new JTextField(20), costo = new JTextField(20), pesoCantidad = new JTextField(20), densidad = new JTextField(20);
		JComboBox<Unidad> unidad = new JComboBox<Unidad>();
		final ButtonGroup checkboxRefrigerado = new ButtonGroup(), 
						  checkboxLiquido = new ButtonGroup();
		JCheckBox esLiquido = new JCheckBox("Es líquido"), noLiquido = new JCheckBox("No es líquido"),
				  esRefrigerado = new JCheckBox("Refrigerado"), noRefrigerado = new JCheckBox("No Refrigerado");
		checkboxLiquido.add(esLiquido);
		checkboxLiquido.add(noLiquido);
		checkboxRefrigerado.add(esRefrigerado);
		checkboxRefrigerado.add(noRefrigerado);
		
		//titulo
		constraints.gridx=0;
		constraints.gridy=0;
		constraints.gridheight=1;
		constraints.gridwidth=8;
		constraints.anchor=GridBagConstraints.NORTH;
		encabezado.setFont(new Font(encabezado.getFont().getName(), encabezado.getFont().getStyle(), 40));
		panel.add(encabezado,constraints);
		
		//labels
		constraints.fill=GridBagConstraints.NONE;
		constraints.anchor=GridBagConstraints.EAST;
		constraints.insets.set(5, 5, 15, 5);
		constraints.gridx=1;
		constraints.gridwidth=1;
		
		constraints.gridy=2;
		panel.add(new JLabel("Descripción: "), constraints);
		
		constraints.gridy=3;
		panel.add(new JLabel("Costo ($): "), constraints);
		
		constraints.gridy=4;
		panel.add(new JLabel("Peso (Kg): "), constraints);

		constraints.gridy=5;
		panel.add(new JLabel("Densidad (Kg/m3): "), constraints);
		
		constraints.gridy=6;
		panel.add(new JLabel("Refrigeración: "), constraints);
		
		constraints.gridy=7;
		panel.add(new JLabel("Unidad: "), constraints);
		
		//checkboxs
		constraints.gridx=2;
		
		constraints.anchor=GridBagConstraints.EAST;
		constraints.gridy=1;
		panel.add(noLiquido, constraints);
		constraints.gridy=6;
		panel.add(noRefrigerado, constraints);
		constraints.anchor=GridBagConstraints.WEST;
		constraints.gridy=1;
		panel.add(esLiquido, constraints);
		constraints.gridy=6;
		panel.add(esRefrigerado, constraints);
		if (insumo instanceof InsumoLiquido) {
			esLiquido.setSelected(true);
			unidad.setSelectedItem(Unidad.LITRO);
			unidad.setEnabled(false);
			densidad.setEnabled(true);
			densidad.setText(((Double)((InsumoLiquido)insumo).getDensidad()).toString());
			pesoCantidad.setText(((Double)insumo.getPeso()).toString());
		} else {
			noLiquido.setSelected(true);
			densidad.setEnabled(false);
			pesoCantidad.setText(((Double)insumo.getPeso()).toString());
		}
		
		if(!insumo.getEsRefrigerado())
			noRefrigerado.setSelected(true);
		else
			esRefrigerado.setSelected(true);
		
		//campos de texto
		constraints.fill=GridBagConstraints.HORIZONTAL;
		constraints.anchor=GridBagConstraints.CENTER;
		
		constraints.gridy=2;
		descripcion.setText(insumo.getDescripcion());
		panel.add(descripcion, constraints);

		constraints.gridy=3;
		costo.setText(insumo.getCosto().toString());
		panel.add(costo, constraints);
		
		constraints.gridy=4;
		panel.add(pesoCantidad, constraints);
		
		constraints.gridy=5;
		panel.add(densidad, constraints);
		
		constraints.gridy=7;
		unidad.addItem(Unidad.KILO);
		unidad.addItem(Unidad.METRO);
		unidad.addItem(Unidad.M2);
		unidad.addItem(Unidad.LITRO);
		unidad.setSelectedItem(insumo.getUnidad());
		panel.add(unidad, constraints);
		
		//listener checkboxLiquido
		ItemListener esLiquidoListener = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				int estado = itemEvent.getStateChange();
				if (estado == ItemEvent.SELECTED) { //si es liquido-
					unidad.setSelectedItem(Unidad.LITRO);
					unidad.setEnabled(false);
					densidad.setEnabled(true);
				} else {							//si no es liquido-
					unidad.setEnabled(true);
					unidad.setSelectedItem(insumo.getUnidad());
					densidad.setEnabled(false);
				}
			}
		};
		esLiquido.addItemListener(esLiquidoListener);
		
		//botones
		constraints.gridy=8;
		constraints.fill=GridBagConstraints.NONE;
		
		constraints.anchor=GridBagConstraints.EAST;
		constraints.gridx=2;
		constraints.insets=new Insets(20, 5, 5, 15);
		volver.addActionListener(a -> this.modificarInsumo());
		panel.add(volver, constraints);
		
		//boton guardarCambios				
		constraints.anchor=GridBagConstraints.WEST;
		constraints.insets=new Insets(20, 35, 5, 0);
		constraints.gridx=1;
		guardarCambios.addActionListener(a -> {
			String valorDescripcion;
			Double valorCosto = null, valorPeso = null;
			Boolean valorRefrigeracion = false;
			Unidad valorUnidad;
			Double valorDensidad = null;
					
			errorDescripcion.setText("");
			errorCosto.setText("");
			errorPesoCantidad.setText("");
			errorDensidad.setText("");
			try {
				if(descripcion.getText().isEmpty()) {
					errorDescripcion.setText("Debe ingresar una descripción");
					return;
				}else {
					valorDescripcion = descripcion.getText();
				}
				if(costo.getText().isEmpty()) {
					errorCosto.setText("Debe ingresar un costo");
					return;
				}else {
					valorCosto = Double.parseDouble(costo.getText());
				}
				if(pesoCantidad.getText().isEmpty()) {
						errorPesoCantidad.setText("Debe ingresar un peso");
					return;
				}else{
					valorPeso = Double.parseDouble(pesoCantidad.getText());
				}
				if(esLiquido.isSelected() && densidad.getText().isEmpty()) {
					errorDensidad.setText("Debe ingresar una densidad");
					return;
				} else if (esLiquido.isSelected()){
					valorDensidad = Double.parseDouble(densidad.getText());
				}
						
				valorUnidad = (Unidad)unidad.getSelectedItem();
				valorRefrigeracion = esRefrigerado.isSelected();
						
				if(JOptionPane.showConfirmDialog(ventana, "¿Desea guardar los cambios?","Confirmación",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==0) {
					Insumo insumoNuevo; 
					if (esLiquido.isSelected()) {
						if(insumo.getStock() != null) {
							insumoNuevo = new InsumoLiquido(insumo.getId(),valorDescripcion,valorCosto,insumo.getStock(),valorRefrigeracion,valorDensidad,valorPeso);
							//Si modifico la planta en ABMPlanta, habrá que realizar la asignacion del nuevo objeto Planta
							// a sus insumos, si es PlantaAcopio, o a cada uno de sus stocks, si es PlantaProduccion.
							insumoNuevo.getStock().setInsumo(insumoNuevo);
						}else
							insumoNuevo = new InsumoLiquido(insumo.getId(),valorDescripcion,valorCosto,valorRefrigeracion,valorDensidad,valorPeso);
						
						
					} else {
						if(insumo.getStock() != null) {
							insumoNuevo = new Insumo(insumo.getId(),valorDescripcion,valorUnidad,valorCosto,insumo.getStock(),valorPeso,valorRefrigeracion);
							insumoNuevo.getStock().setInsumo(insumoNuevo);
						}
						else
							insumoNuevo = new Insumo(insumo.getId(),valorDescripcion,valorUnidad,valorCosto,valorPeso,valorRefrigeracion);
					}
					
					if (insumo instanceof InsumoLiquido) {
						if (insumoNuevo instanceof InsumoLiquido) {
							controller.editarInsumoLiquido(insumoNuevo);
						} else {
							controller.eliminarInsumoLiquido(insumo.getId());
							controller.agregarInsumo(insumoNuevo);
							//Como cambió el tipo de Insumo, es necesario actualizar el archivo de StocksAcopio
							if(insumoNuevo.getStock() != null)
								sc.editarStock(insumoNuevo.getStock());
						}
					} else {
						if (insumoNuevo instanceof InsumoLiquido) {
							controller.eliminarInsumoNoLiquido(insumo.getId());
							controller.agregarInsumo(insumoNuevo);
							if(insumoNuevo.getStock() != null)
								sc.editarStock(insumoNuevo.getStock());
						} else {
							controller.editarInsumoNoLiquido(insumoNuevo);
						}
					}
					JOptionPane.showConfirmDialog(ventana, "Insumo modificado exitosamente.", "Información", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
					GestionEntidades.mostrarMenu();
				}					
						
			}catch(NumberFormatException nfex) {
				if(valorCosto == null) {
					JOptionPane.showConfirmDialog(ventana, "El campo Costo debe ser numérico.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);	
				} else {
					if(valorPeso == null) {
						if (esLiquido.isSelected()) {
							JOptionPane.showConfirmDialog(ventana, "El campo Cantidad debe ser numérico.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);	
						} else {
							JOptionPane.showConfirmDialog(ventana, "El campo Peso debe ser numérico.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);	
						}
					} else {
						if (valorDensidad == null) {
							JOptionPane.showConfirmDialog(ventana, "El campo Densidad debe ser numérico.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);	
						}
					}
				}
			}
		});
		panel.add(guardarCambios,constraints);
		
		//errores
		constraints.anchor=GridBagConstraints.NORTHWEST;
		constraints.insets.set(5,0,3,0);
		constraints.gridx=3;
		constraints.gridy=2;
		errorDescripcion.setPreferredSize(new Dimension(230, 16));
		errorDescripcion.setForeground(Color.red);
		panel.add(errorDescripcion,constraints);
				
		constraints.gridy=3;
		errorCosto.setPreferredSize(new Dimension(230, 16));
		errorCosto.setForeground(Color.red);
		panel.add(errorCosto,constraints);
				
		constraints.gridy=4;
		errorPesoCantidad.setPreferredSize(new Dimension(230, 16));
		errorPesoCantidad.setForeground(Color.red);
		panel.add(errorPesoCantidad,constraints);
		
		constraints.gridy=5;
		errorDensidad.setPreferredSize(new Dimension(230, 16));
		errorDensidad.setForeground(Color.red);
		panel.add(errorDensidad,constraints);
		
		//ventana
		ventana.setContentPane(panel);
		ventana.pack();
		ventana.setSize(800, 600);
		ventana.setLocationRelativeTo(null);
		ventana.setTitle("Sistema de Gestión de Entidades");
		ventana.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		ventana.setVisible(true);
	}
	
	public void eliminarInsumo(boolean mostrarTablaNoLiquidos) {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		JLabel encabezado = new JLabel("Eliminar Insumo"), errorSeleccion = new JLabel();
		JButton eliminar = new JButton("Eliminar"), volver = new JButton("Volver"), cambiarTipoInsumo = new JButton("Ver Líquidos");
		JTable tablaInsumos = new JTable(0,6);
		List<Insumo> listaInsumosNoLiquidos = new ArrayList<Insumo>();
		List<Insumo> listaInsumosLiquidos = new ArrayList<Insumo>();
		listaInsumosNoLiquidos.addAll(controller.listaInsumos());
		listaInsumosLiquidos.addAll(controller.listaInsumosLiquidos());
		
		//tabla
		constraints.insets=new Insets(5, 5, 0, 5);
		tablaInsumos.setFillsViewportHeight(true);
		tablaInsumos.setBorder(new LineBorder(new Color(0, 0, 0)));
		tablaInsumos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane scroll = new JScrollPane(tablaInsumos,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setPreferredSize(new Dimension(500, 197));
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		tablaInsumos.setDefaultRenderer(String.class, centerRenderer);
		tablaInsumos.setDefaultRenderer(Integer.class, centerRenderer);
		DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
		leftRenderer.setHorizontalAlignment( JLabel.LEFT );
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment( JLabel.RIGHT );
		
		//centrar celdas tabla
		tablaInsumos.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		tablaInsumos.getColumnModel().getColumn(1).setCellRenderer(leftRenderer);
		for (int i=2; i<4; i++)
			tablaInsumos.getColumnModel().getColumn(i).setCellRenderer(rightRenderer);
		tablaInsumos.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
		tablaInsumos.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
		//tamaño y headers tabla
		tablaInsumos.getColumnModel().getColumn(0).setPreferredWidth(18);
		tablaInsumos.getColumnModel().getColumn(1).setPreferredWidth(180);
		tablaInsumos.getColumnModel().getColumn(2).setPreferredWidth(40);
		tablaInsumos.getColumnModel().getColumn(3).setPreferredWidth(55);
		tablaInsumos.getColumnModel().getColumn(4).setPreferredWidth(70);
		tablaInsumos.getColumnModel().getColumn(0).setHeaderValue("Id");
		tablaInsumos.getColumnModel().getColumn(1).setHeaderValue("Descripción");
		tablaInsumos.getColumnModel().getColumn(2).setHeaderValue("Costo");
		tablaInsumos.getColumnModel().getColumn(3).setHeaderValue("Peso");
		tablaInsumos.getColumnModel().getColumn(4).setHeaderValue("Unidad");
		tablaInsumos.getColumnModel().getColumn(5).setHeaderValue("Refrigeración");
		
		constraints.gridx=1;
		constraints.gridy=1;
		constraints.gridheight=1;
		constraints.gridwidth=3;
		constraints.weightx=1;
		panel.add(scroll, constraints);
		
		//agregar datos tabla
		DefaultTableModel model = (DefaultTableModel) tablaInsumos.getModel();
		if (mostrarTablaNoLiquidos) {
			for (Insumo insumo : listaInsumosNoLiquidos) {
				String refrig = "";
				if (insumo.getEsRefrigerado())
					refrig += "SI";
				else
					refrig += "NO";
				model.addRow(new Object[]{Integer.toString(insumo.getId()), insumo.getDescripcion(), Double.toString(insumo.getCosto()), Double.toString(insumo.getPeso()), insumo.getUnidad().toString(), refrig});
			}
		} else {
			cambiarTipoInsumo.setText("Ver No Líquidos");
			tablaInsumos.getColumnModel().getColumn(4).setHeaderValue("Densidad");
			for (Insumo insumo : listaInsumosLiquidos) {
				String refrig = "";
				if (insumo.getEsRefrigerado())
					refrig += "SI";
				else
					refrig += "NO";
				model.addRow(new Object[]{Integer.toString(insumo.getId()), insumo.getDescripcion(), Double.toString(insumo.getCosto()), Double.toString(insumo.getPeso()), Double.toString(((InsumoLiquido)insumo).getDensidad()), refrig});
			}
		}

		//titulo
		constraints.gridx=0;
		constraints.gridy=0;
		constraints.gridheight=1;
		constraints.gridwidth=8;
		constraints.anchor=GridBagConstraints.NORTH;
		encabezado.setFont(new Font(encabezado.getFont().getName(), encabezado.getFont().getStyle(), 40));
		panel.add(encabezado,constraints);
		
		//botones
		constraints.gridy=3;
		constraints.fill=GridBagConstraints.NONE;
		constraints.anchor=GridBagConstraints.EAST;
		
		constraints.gridx=0;
		constraints.insets=new Insets(5, 5, 5, 250);
		volver.addActionListener(a -> GestionEntidades.mostrarMenu());
		panel.add(volver, constraints);
		
		constraints.anchor=GridBagConstraints.WEST;
		constraints.insets=new Insets(5, 250, 5, 5);
		constraints.gridx=2;
		eliminar.addActionListener(a -> {
			errorSeleccion.setText("");
			int numFila = tablaInsumos.getSelectedRow();
			if (numFila == -1) {
				errorSeleccion.setText("Debes seleccionar un insumo");
				return;
			} else {
				int id = Integer.valueOf((String)tablaInsumos.getValueAt(numFila, 0));
				if(JOptionPane.showConfirmDialog(ventana, "¿Desea eliminar el insumo seleccionado?","Confirmación",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==0) {
					//Hacer lo mismo: llamar a eliminarStocksProduccion(stocks) en ABMPlanta si se elimina PlantaProduccion definitivamente
					if (cambiarTipoInsumo.getText() == "Ver Líquidos") {
						Insumo ins = controller.buscarInsumoNoLiquido(id);
						if(ins.getStock() != null)
							sc.eliminarStock(ins.getStock());
						
						controller.eliminarInsumoNoLiquido(id);
						this.eliminarInsumo(true);
					}
					else {
						Insumo ins = controller.buscarInsumoLiquido(id);
						if(ins.getStock() != null)
							sc.eliminarStock(ins.getStock());
						
						controller.eliminarInsumoLiquido(id);
						this.eliminarInsumo(false);
					}
				}
			}
		});
		panel.add(eliminar,constraints);
		
		constraints.gridy=2;
		constraints.gridx=1;
		constraints.anchor=GridBagConstraints.CENTER;
		constraints.insets = new Insets(1,5,39,375);
		cambiarTipoInsumo.setPreferredSize(new Dimension(130, 25));
		cambiarTipoInsumo.addActionListener(a -> {
			if (cambiarTipoInsumo.getText() == "Ver Líquidos") {
				cambiarTipoInsumo.setText("Ver No Líquidos");
				tablaInsumos.getColumnModel().getColumn(4).setHeaderValue("Densidad");
				for (int i=0; i<listaInsumosNoLiquidos.size(); i++) 
					model.removeRow(0);
				for (Insumo insumo : listaInsumosLiquidos) {
					String refrig = "";
					if (insumo.getEsRefrigerado())
						refrig += "SI";
					else
						refrig += "NO";
					model.addRow(new Object[]{Integer.toString(insumo.getId()), insumo.getDescripcion(), Double.toString(insumo.getCosto()), Double.toString(insumo.getPeso()), Double.toString(((InsumoLiquido)insumo).getDensidad()), refrig});
				}
				
			} else if (cambiarTipoInsumo.getText() == "Ver No Líquidos"){
				cambiarTipoInsumo.setText("Ver Líquidos");
				tablaInsumos.getColumnModel().getColumn(4).setHeaderValue("Unidad");
				for (int i=0; i<listaInsumosLiquidos.size(); i++) 
					model.removeRow(0);
				for (Insumo insumo : listaInsumosNoLiquidos) {
					String refrig = "";
					if (insumo.getEsRefrigerado())
						refrig += "SI";
					else
						refrig += "NO";
					model.addRow(new Object[]{Integer.toString(insumo.getId()), insumo.getDescripcion(), Double.toString(insumo.getCosto()), Double.toString(insumo.getPeso()), insumo.getUnidad().toString(), refrig});
				}
			}
		});
		panel.add(cambiarTipoInsumo, constraints);
		
		//error seleccion
		constraints.anchor=GridBagConstraints.NORTHWEST;
		constraints.insets.set(6,473,0,0);
		constraints.gridx=3;
		constraints.gridy=2;
		errorSeleccion.setPreferredSize(new Dimension(230, 16));
		errorSeleccion.setForeground(Color.red);
		panel.add(errorSeleccion,constraints);
		
		ventana.setContentPane(panel);
		ventana.pack();
		ventana.setSize(800, 600);
		ventana.setLocationRelativeTo(null);
		ventana.setTitle("Sistema de Gestión de Entidades");
		ventana.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		ventana.setVisible(true);
	}
	
}

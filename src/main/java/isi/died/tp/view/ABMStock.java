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
import isi.died.tp.controller.PlantaController;
import isi.died.tp.controller.StockController;
import isi.died.tp.model.Insumo;
import isi.died.tp.model.InsumoLiquido;
import isi.died.tp.model.Planta;
import isi.died.tp.model.PlantaAcopio;
import isi.died.tp.model.PlantaProduccion;
import isi.died.tp.model.Stock;
import isi.died.tp.model.StockAcopio;
import isi.died.tp.model.StockProduccion;

public class ABMStock {
	private StockController controller;
	private JFrame ventana;
	private PlantaController pc;
	private InsumoController ic;
	
	public ABMStock(JFrame ventana, StockController stockController, PlantaController plantaController, InsumoController insumoController){
		this.controller = stockController;
		this.ventana = ventana;
		this.pc = plantaController;
		this.ic = insumoController;
	}
	
	public void agregarStock() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		JLabel errorCantidad = new JLabel(), errorPuntoPedido = new JLabel(),
				encabezado = new JLabel("Agregar Nuevo Stock");
		JTextField cantidad = new JTextField(20), puntoPedido = new JTextField(20);
		JButton aceptar = new JButton("Aceptar"), volver = new JButton("Volver");
		JComboBox<String> listaPlantas = new JComboBox<String>(), listaInsumos = new JComboBox<String>();
		List<Planta> listaPlantasCreadas = pc.listaPlantas();
		List<Insumo> listaInsumosCreados = new ArrayList<Insumo>();
		listaInsumosCreados.addAll(ic.listaInsumos());
		listaInsumosCreados.addAll(ic.listaInsumosLiquidos());
		
		//titulo
		constraints.insets.set(0, 20, 145, 5);
		constraints.gridx=0;
		constraints.gridy=0;
		constraints.gridheight=1;
		constraints.gridwidth=8;
		constraints.anchor=GridBagConstraints.NORTH;
		encabezado.setFont(new Font(encabezado.getFont().getName(), encabezado.getFont().getStyle(), 30));
		panel.add(encabezado,constraints);
		
		//labels
		constraints.fill=GridBagConstraints.NONE;
		constraints.anchor=GridBagConstraints.EAST;
		constraints.insets.set(5, 155, 15, 5);
		constraints.gridx=1;
		constraints.gridwidth=1;
				
		constraints.gridy=1;
		panel.add(new JLabel("Planta: "), constraints);
		
		constraints.gridy=2;
		panel.add(new JLabel("Insumo: "), constraints);
		
		constraints.gridy=3;
		panel.add(new JLabel("Cantidad: "), constraints);
		
		constraints.gridy=4;
		panel.add(new JLabel("Punto de Pedido: "), constraints);
		
		//combobox listaPlantas
		constraints.insets.set(5, 5, 15, 5);
		constraints.fill=GridBagConstraints.HORIZONTAL;
		constraints.anchor=GridBagConstraints.CENTER;
		for (Planta planta : listaPlantasCreadas) {
			if (planta instanceof PlantaAcopio) {
				listaPlantas.addItem(planta.getId() + ": " + planta.getNombre() + " (A)");
			} else {
				listaPlantas.addItem(planta.getId() + ": " + planta.getNombre() + " (P)");
			}
		}
		listaPlantas.setSelectedIndex(0);
		constraints.insets.set(0, 5, 5, 5);
		constraints.gridx = 2;
		constraints.gridy = 1;
		constraints.gridwidth=1;
		panel.add(listaPlantas, constraints);	
		
		//combobox listaInsumos
		for (Insumo insumo : listaInsumosCreados) {
			if (insumo instanceof InsumoLiquido) {
				listaInsumos.addItem(insumo.getId() + ": " + insumo.getDescripcion() + " (L)");
			} else {
				listaInsumos.addItem(insumo.getId() + ": " + insumo.getDescripcion() + " (G)");
			}
		}
		listaInsumos.setSelectedIndex(0);
		constraints.gridy = 2;
		panel.add(listaInsumos, constraints);	
		
		//campos texto
		constraints.gridy = 3;
		panel.add(cantidad, constraints);
		
		constraints.gridy = 4;
		panel.add(puntoPedido, constraints);
		
		//listener tipo planta
		listaPlantas.addActionListener (a -> {
			String stringPlanta = (String)listaPlantas.getSelectedItem();
			String segPartePlantas[] = stringPlanta.split(" ");
			String tipoPlantaSeleccionada = segPartePlantas[segPartePlantas.length-1];
			if (tipoPlantaSeleccionada.equals("(A)")) {
				puntoPedido.setEnabled(false);
			} else {
				puntoPedido.setEnabled(true);
			}
		});
		
		//botones
		constraints.insets.set(75,0,0,0);
		constraints.gridy=10;
		constraints.fill=GridBagConstraints.NONE;
		constraints.anchor=GridBagConstraints.EAST;
		
		constraints.gridx=2;
		volver.addActionListener(a -> GestionEntidades.mostrarMenu());
		panel.add(volver, constraints);
		
		constraints.gridx=1;
		aceptar.addActionListener(a -> {
			String stringPlanta = (String)listaPlantas.getSelectedItem();
			String arrP[] = stringPlanta.split(": ");
			Integer idPlantaSeleccionada = Integer.valueOf(arrP[0]);
			String segPartePlantas[] = arrP[1].split(" ");
			String tipoPlantaSeleccionada = segPartePlantas[segPartePlantas.length-1];
			
			String stringInsumo = (String)listaInsumos.getSelectedItem();
			String arrI[] = stringInsumo.split(": ");
			Integer idInsumoSeleccionado = Integer.valueOf(arrI[0]);
			String segParteInsumos[] = arrI[1].split(" ");
			String tipoInsumoSeleccionado = segParteInsumos[segParteInsumos.length-1];
			
			Planta plantaAux = null;
			Insumo insumoAux;
			Integer cantidadAux = null;
			Integer ptoPedidoAux = null;

			errorCantidad.setText("");
			errorPuntoPedido.setText("");
			
			try {
				if (tipoPlantaSeleccionada.equals("(A)")) {
					plantaAux = pc.buscarPlantaAcopio(idPlantaSeleccionada);
				} else {
					plantaAux = pc.buscarPlantaProduccion(idPlantaSeleccionada);
				}
				
				if (tipoInsumoSeleccionado.equals("(L)")) {
					insumoAux = ic.buscarInsumoLiquido(idInsumoSeleccionado);
				} else {
					insumoAux = ic.buscarInsumoNoLiquido(idInsumoSeleccionado);
				}
				
				if (cantidad.getText().isEmpty()) {
					errorCantidad.setText("Debe ingresar una cantidad");
					return;
				} else {
					cantidadAux = Integer.parseInt(cantidad.getText());
				}
				
				if (puntoPedido.getText().isEmpty()) {
					errorPuntoPedido.setText("Debe ingresar un punto de pedido");
					return;
				} else {
					ptoPedidoAux = Integer.parseInt(puntoPedido.getText());
				}
				
				if(JOptionPane.showConfirmDialog(ventana, "¿Desea guardar la nueva planta con los datos ingresados?","Confirmación",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==0) {
					if (plantaAux instanceof PlantaAcopio) {
						Stock stockAux = new StockAcopio(0, cantidadAux, insumoAux, plantaAux);
						controller.agregarStock(stockAux);
					} else {
						Stock stockAux = new StockProduccion(0, cantidadAux, ptoPedidoAux, insumoAux, plantaAux);
						controller.agregarStock(stockAux);
					}
					
					if(JOptionPane.showConfirmDialog(ventana, "Stock creado exitosamente. \n¿Desea agregar otro stock?","Confirmación",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==0) {
						this.agregarStock();
					} else {
						GestionEntidades.mostrarMenu();
					}
				}
			} catch(NumberFormatException nfex) {
				if(cantidadAux == null) {
					JOptionPane.showConfirmDialog(ventana, "El campo Cantidad debe ser numérico.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);	
				} else {
					if (plantaAux instanceof PlantaProduccion && ptoPedidoAux == null) {
						JOptionPane.showConfirmDialog(ventana, "El campo Punto de pedido debe ser numérico.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);	
					}
				}
			}
			
		});
		panel.add(aceptar, constraints);
		
		//errores
		constraints.anchor=GridBagConstraints.NORTHWEST;
		constraints.insets.set(5,0,3,0);
		constraints.gridx=3;
				
		constraints.gridy=3;
		errorCantidad.setPreferredSize(new Dimension(230, 16));
		errorCantidad.setForeground(Color.red);
		panel.add(errorCantidad,constraints);
				
		constraints.gridy=4;
		errorPuntoPedido.setPreferredSize(new Dimension(230, 16));
		errorPuntoPedido.setForeground(Color.red);
		panel.add(errorPuntoPedido,constraints);
		
		//ventana
		ventana.setContentPane(panel);
		ventana.pack();
		ventana.setSize(800, 600);
		ventana.setLocationRelativeTo(null);
		ventana.setTitle("Sistema de Gestión de Entidades");
		ventana.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		ventana.setVisible(true);
	}
	
	public void modificarStock() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		JLabel encabezado = new JLabel("Modificar Stock");
		JButton aceptar = new JButton("Aceptar"), volver = new JButton("Volver");
		JComboBox<String> seleccionarStock = new JComboBox<String>();
		
		//titulo
		constraints.insets=new Insets(2, 20, 20, 5);
		constraints.gridx=0;
		constraints.gridy=0;
		constraints.gridheight=1;
		constraints.gridwidth=8;
		constraints.anchor=GridBagConstraints.NORTH;
		encabezado.setFont(new Font(encabezado.getFont().getName(), encabezado.getFont().getStyle(), 30));
		panel.add(encabezado,constraints);
		
		//combobox seleccion
		constraints.fill=GridBagConstraints.HORIZONTAL;
		constraints.anchor=GridBagConstraints.CENTER;
		constraints.insets.set(135, 5, 25, 5);
		constraints.gridx=0;
		constraints.gridy=1;
		panel.add(seleccionarStock, constraints);
		
		for (StockAcopio stock : controller.listaStocksAcopio()) {
			String item = "";
			item += Integer.toString(stock.getId()) + ": " + stock.getInsumo().getDescripcion() + " - Planta: " + stock.getPlanta().getNombre() + " (A)";
			seleccionarStock.addItem(item);
		}
		for (StockProduccion stock : controller.listaStocksProduccion()) {
			String item = "";
			item += Integer.toString(stock.getId()) + ": " + stock.getInsumo().getDescripcion() + " - Planta: " + stock.getPlanta().getNombre() + " (P)";
			seleccionarStock.addItem(item);
		}
		
		//botones
		constraints.gridy=8;
		constraints.fill=GridBagConstraints.NONE;
		
		constraints.anchor=GridBagConstraints.EAST;
		constraints.gridx=2;
		constraints.insets.set(105,50,0,0);
		volver.addActionListener(a -> GestionEntidades.mostrarMenu());
		panel.add(volver, constraints);
				
		constraints.anchor=GridBagConstraints.WEST;
		constraints.insets=new Insets(105, 0, 0, 50);
		constraints.gridx=0;
		aceptar.addActionListener(a -> {
			String stringStock = (String)seleccionarStock.getSelectedItem();
			String arr[] = stringStock.split(": ");
			Integer idSeleccionado = Integer.valueOf(arr[0]);
			String segParte[] = arr[2].split(" ");
			String ultimaPalabra = segParte[segParte.length-1];
			if (ultimaPalabra.equals("(A)")) {
				this.modificarStock((StockAcopio)controller.buscarStockAcopio(idSeleccionado));
			} else {
				this.modificarStock((StockProduccion)controller.buscarStockProduccion(idSeleccionado));
			}
		});
		panel.add(aceptar,constraints);
		
		//ventana
		ventana.setContentPane(panel);
		ventana.pack();
		ventana.setSize(800, 600);
		ventana.setLocationRelativeTo(null);
		ventana.setTitle("Sistema de Gestión de Entidades");
		ventana.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		ventana.setVisible(true);
	}
	
	public void modificarStock(Stock stockX) {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		JLabel encabezado = new JLabel("Modificar Stock");
		JButton guardarCambios = new JButton("Guardar"), volver = new JButton("Volver");
		JLabel errorCantidad = new JLabel(), errorPtoPedido = new JLabel();
		JTextField nombrePlanta = new JTextField(20), nombreInsumo = new JTextField(20),
				cantidadStock = new JTextField(20), puntoPedidoStock = new JTextField(20);
		
		//titulo
		constraints.gridx=0;
		constraints.gridy=0;
		constraints.gridheight=1;
		constraints.gridwidth=8;
		constraints.anchor=GridBagConstraints.NORTH;
		constraints.insets.set(0, 20, 125, 5);
		encabezado.setFont(new Font(encabezado.getFont().getName(), encabezado.getFont().getStyle(), 30));
		panel.add(encabezado,constraints);
		
		//labels
		constraints.fill=GridBagConstraints.NONE;
		constraints.anchor=GridBagConstraints.EAST;
		constraints.insets.set(5, 10, 15, 5);
		constraints.gridx=1;
		constraints.gridwidth=1;
				
		constraints.gridy=1;
		panel.add(new JLabel("Planta: "), constraints);
				
		constraints.gridy=2;
		panel.add(new JLabel("Insumo: "), constraints);
				
		constraints.gridy=3;
		panel.add(new JLabel("Cantidad: "), constraints);
		
		constraints.gridy=4;
		panel.add(new JLabel("Punto de pedido: "), constraints);
		
		//campos texto
		constraints.fill=GridBagConstraints.HORIZONTAL;
		constraints.anchor=GridBagConstraints.CENTER;
		constraints.insets.set(0, 5, 5, 5);
		constraints.gridy = 1;
		constraints.gridx = 2;
		String nombrePlantaAux = "";
		nombrePlantaAux += stockX.getPlanta().getNombre();
		if (stockX instanceof StockAcopio)
			nombrePlantaAux += " (A)";
		else
			nombrePlantaAux += " (P)";
		nombrePlanta.setText(nombrePlantaAux);
		panel.add(nombrePlanta, constraints);
		nombrePlanta.setEnabled(false);
		
		constraints.gridy=2;
		nombreInsumo.setText(stockX.getInsumo().getDescripcion());
		panel.add(nombreInsumo, constraints);
		nombreInsumo.setEnabled(false);
		
		constraints.gridy=3;
		cantidadStock.setText(Integer.toString(stockX.getCantidad()));
		panel.add(cantidadStock, constraints);
		
		constraints.gridy=4;
		if (stockX instanceof StockProduccion) {
			puntoPedidoStock.setText(Integer.toString(stockX.getPuntoPedido()));
		} else {
			puntoPedidoStock.setEnabled(false);
		}
		
		panel.add(puntoPedidoStock, constraints);
		
		//botones
		constraints.gridy=8;
		constraints.fill=GridBagConstraints.NONE;
		
		constraints.anchor=GridBagConstraints.EAST;
		constraints.gridx=2;
		constraints.insets.set(75,0,0,10);
		volver.addActionListener(a -> this.modificarStock());
		panel.add(volver, constraints);
		
		constraints.insets.set(75,200,0,0);
		constraints.gridx=1;
		guardarCambios.addActionListener(a -> {
			Integer valorCantidad = null;
			Integer valorPtoPedido = null;
			
			try {
				errorCantidad.setText("");
				errorPtoPedido.setText("");
				
				if(cantidadStock.getText().isEmpty()) {
					errorCantidad.setText("Debe ingresar una cantidad");
					return;
				} else {
					valorCantidad = Integer.parseInt(cantidadStock.getText());
				}
				
				if(stockX instanceof StockProduccion && puntoPedidoStock.getText().isEmpty()) {
					errorPtoPedido.setText("Debe ingresar un punto de pedido");
					return;
				} else if (stockX instanceof StockProduccion){
					valorPtoPedido = Integer.parseInt(puntoPedidoStock.getText());
				}
				
						
				if(JOptionPane.showConfirmDialog(ventana, "¿Desea guardar los cambios?","Confirmación",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==0) {
					if (stockX instanceof StockAcopio) {
						Stock stockNuevo = new StockAcopio(stockX.getId(), valorCantidad, stockX.getInsumo(), stockX.getPlanta());
						controller.editarStock(stockNuevo);
					} else {
						Stock stockNuevo = new StockProduccion(stockX.getId(), valorCantidad, valorPtoPedido, stockX.getInsumo(), stockX.getPlanta());
						controller.editarStock(stockNuevo);
					}
					JOptionPane.showConfirmDialog(ventana, "Stock modificado exitosamente.", "Información", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
					GestionEntidades.mostrarMenu();
				}
			} catch(NumberFormatException nfex) {
				if(valorCantidad == null) {
					JOptionPane.showConfirmDialog(ventana, "El campo Cantidad debe ser numérico.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);	
				} else {
					if (stockX instanceof StockProduccion && valorPtoPedido == null) {
						JOptionPane.showConfirmDialog(ventana, "El campo Punto de pedido debe ser numérico.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);	
					}
				}
			}
		});
		panel.add(guardarCambios,constraints);
		
		//errores
		constraints.anchor=GridBagConstraints.NORTHWEST;
		constraints.insets.set(5,0,3,0);
		constraints.gridx=3;
		constraints.gridy=3;
		errorCantidad.setPreferredSize(new Dimension(230, 16));
		errorCantidad.setForeground(Color.red);
		panel.add(errorCantidad,constraints);
		
		constraints.gridy=4;
		errorPtoPedido.setPreferredSize(new Dimension(230, 16));
		errorPtoPedido.setForeground(Color.red);
		panel.add(errorPtoPedido,constraints);
		
		//ventana
		ventana.setContentPane(panel);
		ventana.pack();
		ventana.setSize(800, 600);
		ventana.setLocationRelativeTo(null);
		ventana.setTitle("Sistema de Gestión de Entidades");
		ventana.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		ventana.setVisible(true);
	}
	
	public void eliminarStock(boolean mostrarTablaAcopio) {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		JLabel encabezado = new JLabel("Eliminar Stock"), errorSeleccion = new JLabel();
		JButton eliminar = new JButton("Eliminar"), volver = new JButton("Volver"), cambiarTipoStock = new JButton("Ver Stocks de Producción");
		JTable tablaStocks;
		List<Stock> listaStocksAcopio = new ArrayList<Stock>();
		List<Stock> listaStocksProduccion = new ArrayList<Stock>();
		listaStocksAcopio.addAll(controller.listaStocksAcopio());
		listaStocksProduccion.addAll(controller.listaStocksProduccion());
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
		leftRenderer.setHorizontalAlignment( JLabel.LEFT );
		
		if (mostrarTablaAcopio) {
			tablaStocks = new JTable(0,4);
			tablaStocks.getColumnModel().getColumn(0).setPreferredWidth(17);
			tablaStocks.getColumnModel().getColumn(1).setPreferredWidth(200);
			tablaStocks.getColumnModel().getColumn(2).setPreferredWidth(200);
			tablaStocks.getColumnModel().getColumn(3).setPreferredWidth(80);
		} else {
			tablaStocks = new JTable(0,5);
			tablaStocks.getColumnModel().getColumn(0).setPreferredWidth(17);
			tablaStocks.getColumnModel().getColumn(1).setPreferredWidth(180);
			tablaStocks.getColumnModel().getColumn(2).setPreferredWidth(180);
			tablaStocks.getColumnModel().getColumn(3).setPreferredWidth(80);
			tablaStocks.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
			tablaStocks.getColumnModel().getColumn(4).setPreferredWidth(120);
			tablaStocks.getColumnModel().getColumn(4).setHeaderValue("Punto de pedido");
			
		}
		
		//tabla
		constraints.insets=new Insets(5, 5, 0, 5);
		tablaStocks.setFillsViewportHeight(true);
		tablaStocks.setBorder(new LineBorder(new Color(0, 0, 0)));
		tablaStocks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane scroll = new JScrollPane(tablaStocks,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setPreferredSize(new Dimension(500, 197));
		
		//centrar celdas tabla
		tablaStocks.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		tablaStocks.getColumnModel().getColumn(1).setCellRenderer(leftRenderer);
		tablaStocks.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		//tamaño y headers tabla
		tablaStocks.getColumnModel().getColumn(0).setHeaderValue("Id");
		tablaStocks.getColumnModel().getColumn(1).setHeaderValue("Planta");
		tablaStocks.getColumnModel().getColumn(2).setHeaderValue("Insumo");
		tablaStocks.getColumnModel().getColumn(3).setHeaderValue("Cantidad");
		
		constraints.gridx=1;
		constraints.gridy=1;
		constraints.gridheight=1;
		constraints.gridwidth=3;
		constraints.weightx=1;
		panel.add(scroll, constraints);
		
		//agregar datos tabla
		DefaultTableModel model = (DefaultTableModel) tablaStocks.getModel();
		DefaultCellEditor editor = (DefaultCellEditor) tablaStocks.getDefaultEditor(Object.class);
		editor.setClickCountToStart(10000);
		
		if (mostrarTablaAcopio) {
			cambiarTipoStock.setText("Ver Stocks de Producción");
			for (Stock stock : listaStocksAcopio) {
				model.addRow(new Object[]{Integer.toString(stock.getId()), stock.getPlanta().getNombre(), stock.getInsumo().getDescripcion(), Integer.toString(stock.getCantidad())});
			}
		} else {
			cambiarTipoStock.setText("Ver Stocks de Acopio");
			for (Stock stock : listaStocksProduccion) {
				model.addRow(new Object[]{Integer.toString(stock.getId()), stock.getPlanta().getNombre(), stock.getInsumo().getDescripcion(), Integer.toString(stock.getCantidad()), Integer.toString(stock.getPuntoPedido())});
			}
		}

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
		volver.addActionListener(a -> GestionEntidades.mostrarMenu());
		panel.add(volver, constraints);
		
		constraints.anchor=GridBagConstraints.WEST;
		constraints.insets=new Insets(5, 250, 5, 5);
		constraints.gridx=2;
		eliminar.addActionListener(a -> {
			errorSeleccion.setText("");
			int numFila = tablaStocks.getSelectedRow();
			if (numFila == -1) {
				errorSeleccion.setText("Debes seleccionar un stock");
				return;
			} else {
				int id = Integer.valueOf((String)tablaStocks.getValueAt(numFila, 0));
				if (mostrarTablaAcopio) {
					StockAcopio stock = (StockAcopio)controller.buscarStockAcopio(id);
					
					if(JOptionPane.showConfirmDialog(ventana, "¿Desea eliminar el stock seleccionado?","Confirmación",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==0) {
						controller.eliminarStock(stock);
						this.eliminarStock(true);
					}
				} else {
					StockProduccion stock = (StockProduccion)controller.buscarStockProduccion(id);
					if(JOptionPane.showConfirmDialog(ventana, "¿Desea eliminar el stock seleccionado?","Confirmación",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==0) {
						controller.eliminarStock(stock);
						this.eliminarStock(false);
					}
				}
			}
		});
		panel.add(eliminar,constraints);
		
		constraints.gridy=2;
		constraints.gridx=1;
		constraints.anchor=GridBagConstraints.CENTER;
		constraints.insets = new Insets(1,5,39,305);
		cambiarTipoStock.setPreferredSize(new Dimension(200, 25));
		cambiarTipoStock.addActionListener(a -> {
			if (cambiarTipoStock.getText() == "Ver Stocks de Producción") {
				this.eliminarStock(false);
			} else if (cambiarTipoStock.getText() == "Ver Stocks de Acopio"){
				this.eliminarStock(true);
			}
		});
		panel.add(cambiarTipoStock, constraints);
		
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
		ventana.setTitle("Sistema de Gestión de Entidades");
		ventana.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		ventana.setVisible(true);
	}
}

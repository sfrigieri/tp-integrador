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

import isi.died.tp.controller.CamionController;
import isi.died.tp.model.Camion;

public class ABMCamion {
	private CamionController controller;
	private JFrame ventana;
	
	public ABMCamion(JFrame ventana, CamionController camionController){
		this.controller = camionController;
		this.ventana = ventana;
	}

	public void agregarCamion() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		JLabel errorMarca = new JLabel(), errorModelo = new JLabel(), errorDominio = new JLabel(), errorAnio = new JLabel(),
				 errorCosto = new JLabel(), errorCapacidad = new JLabel(),
				encabezado = new JLabel("Agregar Nuevo Camión");
		JTextField marca = new JTextField(20), modelo = new JTextField(20), dominio = new JTextField(20),
				anio = new JTextField(20), costoKm = new JTextField(20), capacidadKg = new JTextField(20);
		JButton aceptar = new JButton("Aceptar"), volver = new JButton("Volver");
		JCheckBox aptoLiquidos = new JCheckBox();
		
		//titulo
		constraints.insets.set(0, 20, 45, 5);
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
		constraints.insets.set(5, 45, 15, 5);
		constraints.gridx=1;
		constraints.gridwidth=1;
				
		constraints.gridy=1;
		panel.add(new JLabel("Marca: "), constraints);
		
		constraints.gridy=2;
		panel.add(new JLabel("Modelo: "), constraints);
	
		constraints.gridy=3;
		panel.add(new JLabel("Dominio: "), constraints);
		
		constraints.gridy=4;
		panel.add(new JLabel("Año: "), constraints);
		
		constraints.gridy=5;
		panel.add(new JLabel("Costo ($/Km): "), constraints);
		
		constraints.gridy=6;
		panel.add(new JLabel("Capacidad (Kg): "), constraints);
		
		constraints.gridy=7;
		panel.add(new JLabel("Es apto para líquidos: "), constraints);
		
		//campos texto
		constraints.insets.set(5, 5, 15, 5);
		constraints.fill=GridBagConstraints.HORIZONTAL;
		constraints.anchor=GridBagConstraints.CENTER;
		constraints.gridx = 2;	

		constraints.gridy = 1;
		panel.add(marca, constraints);
		
		constraints.gridy = 2;
		panel.add(modelo, constraints);
		
		constraints.gridy = 3;
		panel.add(dominio, constraints);
		
		constraints.gridy = 4;
		panel.add(anio, constraints);
		
		constraints.gridy = 5;
		panel.add(costoKm, constraints);
		
		constraints.gridy = 6;
		panel.add(capacidadKg, constraints);
		
		//checkbox aptoLiquidos
		constraints.fill=GridBagConstraints.NONE;
		constraints.anchor=GridBagConstraints.WEST;
		constraints.gridy = 7;
		panel.add(aptoLiquidos, constraints);
		
		//botones
		constraints.insets.set(25,0,0,0);
		constraints.gridy=10;
		constraints.fill=GridBagConstraints.NONE;
		constraints.anchor=GridBagConstraints.EAST;
		
		constraints.gridx=2;
		volver.addActionListener(a -> GestionEntidades.mostrarMenu());
		panel.add(volver, constraints);
		
		constraints.gridx=1;
		aceptar.addActionListener(a -> {
			String marcaAux;
			String modeloAux;
			String dominioAux;
			Integer anioAux = null;
			Double costoKmAux = null;
			Double capacidadKgAux = null;
			Boolean aptoLiquidosAux = false;

			errorMarca.setText("");
			errorModelo.setText("");
			errorDominio.setText("");
			errorAnio.setText("");
			errorCosto.setText("");
			errorCapacidad.setText("");
			
			try {
				if (marca.getText().isEmpty()) {
					errorMarca.setText("Debe ingresar una marca");
					return;
				} else {
					marcaAux = marca.getText();
				}
				
				if (modelo.getText().isEmpty()) {
					errorModelo.setText("Debe ingresar un modelo");
					return;
				} else {
					modeloAux = modelo.getText();
				}
				
				if (dominio.getText().isEmpty()) {
					errorDominio.setText("Debe ingresar un dominio");
					return;
				} else {
					dominioAux = dominio.getText();
				}
				
				if (anio.getText().isEmpty()) {
					errorAnio.setText("Debe ingresar un año");
					return;
				} else {
					anioAux = Integer.parseInt(anio.getText());
				}
				
				if (costoKm.getText().isEmpty()) {
					errorCosto.setText("Debe ingresar un costo");
					return;
				} else {
					costoKmAux = Double.parseDouble(costoKm.getText());
				}
				
				if (capacidadKg.getText().isEmpty()) {
					errorCapacidad.setText("Debe ingresar una capacidad");
					return;
				} else {
					capacidadKgAux = Double.parseDouble(capacidadKg.getText());
				}
				
				if (aptoLiquidos.isSelected())
					aptoLiquidosAux = true;
				
				if(JOptionPane.showConfirmDialog(ventana, "¿Desea guardar el nuevo camión con los datos ingresados?","Confirmación",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==0) {
					
					Camion camionAux = new Camion(0, marcaAux, modeloAux, dominioAux, anioAux, costoKmAux, aptoLiquidosAux, capacidadKgAux);
					controller.agregarCamion(camionAux);
					
					if(JOptionPane.showConfirmDialog(ventana, "Camión creado exitosamente. \n¿Desea agregar otro camión?","Confirmación",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==0) {
						this.agregarCamion();
					} else {
						GestionEntidades.mostrarMenu();
					}
				}
				
			} catch(NumberFormatException nfex) {
				if(anioAux == null) {
					JOptionPane.showConfirmDialog(ventana, "El campo año debe ser numérico.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);	
				} else {
					if (costoKmAux == null) {
						JOptionPane.showConfirmDialog(ventana, "El campo costo debe ser numérico.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);	
					} else {
						if (capacidadKgAux == null) {
							JOptionPane.showConfirmDialog(ventana, "El campo capacidad debe ser numérico.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		});
		panel.add(aceptar, constraints);
		
		//errores
		constraints.anchor=GridBagConstraints.NORTHWEST;
		constraints.insets.set(5,0,3,0);
		constraints.gridx=3;
				
		constraints.gridy=1;
		errorMarca.setPreferredSize(new Dimension(230, 16));
		errorMarca.setForeground(Color.red);
		panel.add(errorMarca,constraints);
				
		constraints.gridy=2;
		errorModelo.setPreferredSize(new Dimension(230, 16));
		errorModelo.setForeground(Color.red);
		panel.add(errorModelo,constraints);
		
		constraints.gridy=3;
		errorDominio.setPreferredSize(new Dimension(230, 16));
		errorDominio.setForeground(Color.red);
		panel.add(errorDominio,constraints);
		
		constraints.gridy=4;
		errorAnio.setPreferredSize(new Dimension(230, 16));
		errorAnio.setForeground(Color.red);
		panel.add(errorAnio,constraints);
		
		constraints.gridy=5;
		errorCosto.setPreferredSize(new Dimension(230, 16));
		errorCosto.setForeground(Color.red);
		panel.add(errorCosto,constraints);
		
		constraints.gridy=6;
		errorCapacidad.setPreferredSize(new Dimension(230, 16));
		errorCapacidad.setForeground(Color.red);
		panel.add(errorCapacidad,constraints);
		
		//ventana
		ventana.setContentPane(panel);
		ventana.pack();
		ventana.setSize(800, 600);
		ventana.setLocationRelativeTo(null);
		ventana.setTitle("Sistema de Gestión de Entidades");
		ventana.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		ventana.setVisible(true);
	}

	public void modificarCamion() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		JLabel encabezado = new JLabel("Modificar Camión");
		JButton aceptar = new JButton("Aceptar"), volver = new JButton("Volver");
		JComboBox<String> seleccionarCamion = new JComboBox<String>();
		
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
		panel.add(seleccionarCamion, constraints);
		
		for (Camion camion : controller.listaCamiones()) {
			String item = "";
			item += Integer.toString(camion.getId()) + ": " + camion.getMarca() + " " + camion.getModelo() + " " + Integer.toString(camion.getAño()) + " (" + camion.getDominio() + ")";
			seleccionarCamion.addItem(item);
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
			String stringCamion = (String)seleccionarCamion.getSelectedItem();
			String arr[] = stringCamion.split(": ");
			Integer idSeleccionado = Integer.valueOf(arr[0]);
			this.modificarCamion(controller.buscarCamion(idSeleccionado));
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
	
	public void modificarCamion(Camion camion) {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		JButton guardarCambios = new JButton("Guardar"), volver = new JButton("Volver");
		JLabel errorMarca = new JLabel(), errorModelo = new JLabel(), errorDominio = new JLabel(), errorAnio = new JLabel(),
				 errorCosto = new JLabel(), errorCapacidad = new JLabel(),
				encabezado = new JLabel("Modificar Camión");
		JTextField marca = new JTextField(20), modelo = new JTextField(20), dominio = new JTextField(20),
				anio = new JTextField(20), costoKm = new JTextField(20), capacidadKg = new JTextField(20);
		JCheckBox aptoLiquidos = new JCheckBox();
		
		//titulo
		constraints.gridx=0;
		constraints.gridy=0;
		constraints.gridheight=1;
		constraints.gridwidth=8;
		constraints.anchor=GridBagConstraints.NORTH;
		constraints.insets.set(0, 20, 45, 5);
		encabezado.setFont(new Font(encabezado.getFont().getName(), encabezado.getFont().getStyle(), 30));
		panel.add(encabezado,constraints);
		
		//labels
		constraints.fill=GridBagConstraints.NONE;
		constraints.anchor=GridBagConstraints.EAST;
		constraints.insets.set(5, 10, 15, 5);
		constraints.gridx=1;
		constraints.gridwidth=1;
				
		constraints.gridy=1;
		panel.add(new JLabel("Marca: "), constraints);
				
		constraints.gridy=2;
		panel.add(new JLabel("Modelo: "), constraints);
				
		constraints.gridy=3;
		panel.add(new JLabel("Dominio: "), constraints);
		
		constraints.gridy=4;
		panel.add(new JLabel("Año: "), constraints);
		
		constraints.gridy=5;
		panel.add(new JLabel("Costo ($/Km): "), constraints);
		
		constraints.gridy=6;
		panel.add(new JLabel("Capacidad (Kg): "), constraints);
		
		constraints.gridy=7;
		panel.add(new JLabel("Es apto para líquidos: "), constraints);
		
		//campos de texto
		constraints.insets.set(5, 5, 15, 5);
		constraints.fill=GridBagConstraints.HORIZONTAL;
		constraints.anchor=GridBagConstraints.CENTER;
		constraints.gridx = 2;	

		constraints.gridy = 1;
		marca.setText(camion.getMarca());
		panel.add(marca, constraints);
		
		constraints.gridy = 2;
		modelo.setText(camion.getModelo());
		panel.add(modelo, constraints);
		
		constraints.gridy = 3;
		dominio.setText(camion.getDominio());
		panel.add(dominio, constraints);
		
		constraints.gridy = 4;
		anio.setText(Integer.toString(camion.getAño()));
		panel.add(anio, constraints);
		
		constraints.gridy = 5;
		costoKm.setText(Double.toString(camion.getCostoKm()));
		panel.add(costoKm, constraints);
		
		constraints.gridy = 6;
		capacidadKg.setText(Double.toString(camion.getCapacidad()));
		panel.add(capacidadKg, constraints);
		
		//checkbox aptoLiquidos
		constraints.fill=GridBagConstraints.NONE;
		constraints.anchor=GridBagConstraints.WEST;
		constraints.gridy = 7;
		if (camion.getAptoLiquidos())
			aptoLiquidos.setSelected(true);
		panel.add(aptoLiquidos, constraints);
		
		//botones
		constraints.gridy=8;
		constraints.fill=GridBagConstraints.NONE;
		
		constraints.anchor=GridBagConstraints.EAST;
		constraints.gridx=2;
		constraints.insets.set(25,0,0,10);
		volver.addActionListener(a -> this.modificarCamion());
		panel.add(volver, constraints);
		
		constraints.insets.set(25,200,0,0);
		constraints.gridx=1;
		guardarCambios.addActionListener(a -> {
			String marcaAux;
			String modeloAux;
			String dominioAux;
			Integer anioAux = null;
			Double costoKmAux = null;
			Double capacidadKgAux = null;
			Boolean aptoLiquidosAux = false;

			errorMarca.setText("");
			errorModelo.setText("");
			errorDominio.setText("");
			errorAnio.setText("");
			errorCosto.setText("");
			errorCapacidad.setText("");
			
			try {
				if (marca.getText().isEmpty()) {
					errorMarca.setText("Debe ingresar una marca");
					return;
				} else {
					marcaAux = marca.getText();
				}
				
				if (modelo.getText().isEmpty()) {
					errorModelo.setText("Debe ingresar un modelo");
					return;
				} else {
					modeloAux = modelo.getText();
				}
				
				if (dominio.getText().isEmpty()) {
					errorDominio.setText("Debe ingresar un dominio");
					return;
				} else {
					dominioAux = dominio.getText();
				}
				
				if (anio.getText().isEmpty()) {
					errorAnio.setText("Debe ingresar un año");
					return;
				} else {
					anioAux = Integer.parseInt(anio.getText());
				}
				
				if (costoKm.getText().isEmpty()) {
					errorCosto.setText("Debe ingresar un costo");
					return;
				} else {
					costoKmAux = Double.parseDouble(costoKm.getText());
				}
				
				if (capacidadKg.getText().isEmpty()) {
					errorCapacidad.setText("Debe ingresar una capacidad");
					return;
				} else {
					capacidadKgAux = Double.parseDouble(capacidadKg.getText());
				}
				
				if (aptoLiquidos.isSelected())
					aptoLiquidosAux = true;
				
				if(JOptionPane.showConfirmDialog(ventana, "¿Desea guardar los cambios?","Confirmación",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==0) {
					Camion camionAux = new Camion(camion.getId(), marcaAux, modeloAux, dominioAux, anioAux, costoKmAux, aptoLiquidosAux, capacidadKgAux);
					controller.editarCamion(camionAux);
					
					JOptionPane.showConfirmDialog(ventana, "Camión modificado exitosamente.", "Información", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
					GestionEntidades.mostrarMenu();
				}
				
			} catch(NumberFormatException nfex) {
				if(anioAux == null) {
					JOptionPane.showConfirmDialog(ventana, "El campo año debe ser numérico.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);	
				} else {
					if (costoKmAux == null) {
						JOptionPane.showConfirmDialog(ventana, "El campo costo debe ser numérico.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);	
					} else {
						if (capacidadKgAux == null) {
							JOptionPane.showConfirmDialog(ventana, "El campo capacidad debe ser numérico.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
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
				
		constraints.gridy=1;
		errorMarca.setPreferredSize(new Dimension(230, 16));
		errorMarca.setForeground(Color.red);
		panel.add(errorMarca,constraints);
				
		constraints.gridy=2;
		errorModelo.setPreferredSize(new Dimension(230, 16));
		errorModelo.setForeground(Color.red);
		panel.add(errorModelo,constraints);
		
		constraints.gridy=3;
		errorDominio.setPreferredSize(new Dimension(230, 16));
		errorDominio.setForeground(Color.red);
		panel.add(errorDominio,constraints);
		
		constraints.gridy=4;
		errorAnio.setPreferredSize(new Dimension(230, 16));
		errorAnio.setForeground(Color.red);
		panel.add(errorAnio,constraints);
		
		constraints.gridy=5;
		errorCosto.setPreferredSize(new Dimension(230, 16));
		errorCosto.setForeground(Color.red);
		panel.add(errorCosto,constraints);
		
		constraints.gridy=6;
		errorCapacidad.setPreferredSize(new Dimension(230, 16));
		errorCapacidad.setForeground(Color.red);
		panel.add(errorCapacidad,constraints);
		
		//ventana
		ventana.setContentPane(panel);
		ventana.pack();
		ventana.setSize(800, 600);
		ventana.setLocationRelativeTo(null);
		ventana.setTitle("Sistema de Gestión de Entidades");
		ventana.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		ventana.setVisible(true);
	}
	
	public void eliminarCamion() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		JLabel encabezado = new JLabel("Eliminar Camión"), errorSeleccion = new JLabel();
		JButton eliminar = new JButton("Eliminar"), volver = new JButton("Volver");
		JTable tablaCamiones;
		List<Camion> listaCamiones = new ArrayList<Camion>();
		listaCamiones.addAll(controller.listaCamiones());
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
		leftRenderer.setHorizontalAlignment( JLabel.LEFT );
		
		tablaCamiones = new JTable(0,8);
		tablaCamiones.getColumnModel().getColumn(0).setPreferredWidth(17);
		tablaCamiones.getColumnModel().getColumn(1).setPreferredWidth(100);
		tablaCamiones.getColumnModel().getColumn(2).setPreferredWidth(100);
		tablaCamiones.getColumnModel().getColumn(3).setPreferredWidth(40);
		tablaCamiones.getColumnModel().getColumn(4).setPreferredWidth(100);
		tablaCamiones.getColumnModel().getColumn(5).setPreferredWidth(80);
		tablaCamiones.getColumnModel().getColumn(6).setPreferredWidth(100);
		tablaCamiones.getColumnModel().getColumn(7).setPreferredWidth(100);
		
		//tabla
		constraints.insets=new Insets(5, 5, 0, 5);
		tablaCamiones.setFillsViewportHeight(true);
		tablaCamiones.setBorder(new LineBorder(new Color(0, 0, 0)));
		tablaCamiones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane scroll = new JScrollPane(tablaCamiones,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setPreferredSize(new Dimension(700, 197));
		
		//centrar celdas tabla
		tablaCamiones.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		tablaCamiones.getColumnModel().getColumn(1).setCellRenderer(leftRenderer);
		tablaCamiones.getColumnModel().getColumn(2).setCellRenderer(leftRenderer);
		tablaCamiones.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		tablaCamiones.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
		tablaCamiones.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
		tablaCamiones.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
		tablaCamiones.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);
		//headers tabla
		tablaCamiones.getColumnModel().getColumn(0).setHeaderValue("Id");
		tablaCamiones.getColumnModel().getColumn(1).setHeaderValue("Marca");
		tablaCamiones.getColumnModel().getColumn(2).setHeaderValue("Modelo");
		tablaCamiones.getColumnModel().getColumn(3).setHeaderValue("Año");
		tablaCamiones.getColumnModel().getColumn(4).setHeaderValue("Dominio");
		tablaCamiones.getColumnModel().getColumn(5).setHeaderValue("Costo ($/Km)");
		tablaCamiones.getColumnModel().getColumn(6).setHeaderValue("Capacidad (Kg)");
		tablaCamiones.getColumnModel().getColumn(7).setHeaderValue("Apto para líquidos");
		
		constraints.gridx=1;
		constraints.gridy=1;
		constraints.gridheight=1;
		constraints.gridwidth=3;
		constraints.weightx=1;
		panel.add(scroll, constraints);
		
		//agregar datos tabla
		DefaultTableModel model = (DefaultTableModel) tablaCamiones.getModel();
		DefaultCellEditor editor = (DefaultCellEditor) tablaCamiones.getDefaultEditor(Object.class);
		editor.setClickCountToStart(10000);
		
		for (Camion camion : listaCamiones) {
			String soportaLiquidos = "";
			if (camion.getAptoLiquidos())
				soportaLiquidos += "SI";
			else
				soportaLiquidos += "NO";
			model.addRow(new Object[]{Integer.toString(camion.getId()), camion.getMarca(), camion.getModelo(), Integer.toString(camion.getAño()), camion.getDominio(), Double.toString(camion.getCostoKm()), Double.toString(camion.getCapacidad()), soportaLiquidos});
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
			int numFila = tablaCamiones.getSelectedRow();
			if (numFila == -1) {
				errorSeleccion.setText("Debes seleccionar un camión");
				return;
			} else {
				int id = Integer.valueOf((String)tablaCamiones.getValueAt(numFila, 0));
				Camion camion = controller.buscarCamion(id);
				
				if(JOptionPane.showConfirmDialog(ventana, "¿Desea eliminar el camión seleccionado?","Confirmación",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==0) {
					controller.eliminarCamion(camion);
					this.eliminarCamion();
				}
			}
		});
		panel.add(eliminar,constraints);
		
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

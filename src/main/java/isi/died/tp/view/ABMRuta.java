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

import isi.died.tp.controller.PlantaController;
import isi.died.tp.controller.RutaController;
import isi.died.tp.estructuras.Ruta;
import isi.died.tp.model.*;

public class ABMRuta {

	private RutaController controller;
	private PlantaController pc;
	private JFrame ventana;

	public ABMRuta(JFrame ventana, RutaController rc, PlantaController pc){
		this.controller = rc;
		this.pc = pc;
		this.ventana = ventana;
	}

	public void agregarRuta() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		JLabel encabezado = new JLabel("Agregar Nueva Ruta"), errorRuta = new JLabel()
				, errorDistancia = new JLabel(), errorPesoMax = new JLabel(), errorDuracion = new JLabel();
		JButton aceptar = new JButton("Aceptar"), volver = new JButton("Volver");
		JTextField tDistancia = new JTextField(20), tDuracion = new JTextField(20),
				tPesoMaximo = new JTextField(20);
		List<Planta> lista = new ArrayList<Planta>();

		JComboBox<String> seleccionarP1 = new JComboBox<String>(), seleccionarP2 = new JComboBox<String>();

		lista.addAll(pc.listaPlantas());
		seleccionarP1.addItem("Seleccione");
		for (Planta p : lista) {
			seleccionarP1.addItem(Integer.toString(p.getId()) + " | Nombre: " + p.getNombre()+" ");				
		}

		if(lista.isEmpty())
			seleccionarP1.setEnabled(false);

		seleccionarP2.addItem("Seleccione");
		for (Planta p : lista) {	
			seleccionarP2.addItem(Integer.toString(p.getId()) + " | Nombre: " + p.getNombre()+" ");		
		}

		if(lista.isEmpty())
			seleccionarP2.setEnabled(false);

		seleccionarP1.setSelectedItem("Seleccione");



		List<Planta> seleccionadoP1 = new ArrayList<Planta>();
		List<Planta> seleccionadoP2 = new ArrayList<Planta>();
		seleccionarP1.addActionListener (a -> {
			switch(seleccionarP1.getSelectedIndex()){
			case 0:
				aceptar.setEnabled(false);
				seleccionadoP1.clear();
				break;
			default:
				seleccionadoP1.clear();
				seleccionadoP1.add(lista.get(seleccionarP1.getSelectedIndex()-1));
				if(!seleccionadoP2.isEmpty() && !seleccionadoP1.get(0).equals(seleccionadoP2.get(0))) {
					aceptar.setEnabled(true);
				}else {
					aceptar.setEnabled(false);
				}
				break;
			}
		});

		seleccionarP2.addActionListener (a -> {
			switch(seleccionarP2.getSelectedIndex()){
			case 0:
				aceptar.setEnabled(false);
				seleccionadoP2.clear();
				break;
			default:
				seleccionadoP2.clear();
				seleccionadoP2.add(lista.get(seleccionarP2.getSelectedIndex()-1));
				if(!seleccionadoP1.isEmpty() && !seleccionadoP2.get(0).equals(seleccionadoP1.get(0))) {
					aceptar.setEnabled(true);
				}else {
					aceptar.setEnabled(false);
				}
				break;
			}
		});

		aceptar.setEnabled(false);

		//titulo
		constraints.insets.set(0, 20, 75, 5);
		constraints.gridx=0;
		constraints.gridy=0;
		constraints.gridheight=1;
		constraints.gridwidth=8;
		constraints.anchor=GridBagConstraints.NORTH;
		encabezado.setFont(new Font(encabezado.getFont().getName(), encabezado.getFont().getStyle(), 30));
		panel.add(encabezado,constraints);

		//labels
		constraints.fill=GridBagConstraints.HORIZONTAL;
		constraints.anchor=GridBagConstraints.CENTER;
		constraints.insets.set(5, 136, 15, 0);
		constraints.gridx=1;
		constraints.gridwidth=1;

		constraints.gridy=2;
		panel.add(new JLabel("Planta Inicio: "), constraints);

		constraints.gridy=3;
		panel.add(new JLabel("Planta Fin: "), constraints);

		constraints.gridy=4;
		panel.add(new JLabel("Distancia (Km): "), constraints);

		constraints.gridy=5;
		panel.add(new JLabel("Duracion (Min): "), constraints);

		constraints.gridy=6;
		panel.add(new JLabel("Peso Máximo (Ton): "), constraints);

		//botones
		constraints.insets.set(55,50,0,0);
		constraints.gridy=10;
		constraints.fill=GridBagConstraints.NONE;

		constraints.anchor=GridBagConstraints.EAST;
		panel.add(aceptar, constraints);

		//LOS COMBOBOX ACTIVAN EL BOTON ACEPTAR SOLO SI LA SELECCION DE PLANTAS ES CORRECTA
		aceptar.addActionListener(a -> {

			Integer distancia = null;
			Double duracion = null;
			Integer pesoMax = null;
			if(controller.existeRuta(seleccionadoP1.get(0),seleccionadoP2.get(0)))
				errorRuta.setText("Esta Ruta ya existe");
			else {
				errorRuta.setText("");
				errorDistancia.setText("");
				errorDuracion.setText("");
				errorPesoMax.setText("");
				try {

					if(tDistancia.getText().isEmpty()) {
						errorDistancia.setText("Debe ingresar una distancia");
						return;
					}else {
						distancia = Integer.parseInt(tDistancia.getText());
					}
					if(tDuracion.getText().isEmpty()) {
						errorDuracion.setText("Debe ingresar una duración");
						return;
					}else{
						duracion = Double.parseDouble(tDuracion.getText());
					}
					if(tPesoMaximo.getText().isEmpty()) {
						errorPesoMax.setText("Debe ingresar un peso máximo");
						return;
					} else {
						pesoMax = Integer.parseInt(tPesoMaximo.getText());
					}

					if(JOptionPane.showConfirmDialog(ventana, "¿Desea guardar la nueva ruta con los datos ingresados?","Confirmación",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==0) {
						controller.agregarRuta(0, seleccionadoP1.get(0), seleccionadoP2.get(0), distancia, duracion, pesoMax);
					}					

				}catch(NumberFormatException nfex) {
					if(distancia == null) {
						JOptionPane.showConfirmDialog(ventana, "El campo Distancia debe ser numérico.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);	
					} else {
						if(duracion == null) {
							JOptionPane.showConfirmDialog(ventana, "El campo Duracion debe ser numérico.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);	
						} else {
							if (pesoMax == null) {
								JOptionPane.showConfirmDialog(ventana, "El campo Peso Máximo debe ser numérico.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);	
							}
						}
					}
				}
			}
		});
		constraints.insets.set(55,190,0,90);
		constraints.anchor=GridBagConstraints.WEST;

		constraints.gridx=2;
		panel.add(volver, constraints);
		volver.addActionListener(a -> GestionEntidades.mostrarMenu());


		constraints.fill=GridBagConstraints.HORIZONTAL;
		constraints.anchor=GridBagConstraints.CENTER;
		constraints.gridx=2;
		constraints.insets.set(5, 5, 15, 90);
		constraints.gridy=2;

		panel.add(seleccionarP1, constraints);

		constraints.gridy=3;

		panel.add(seleccionarP2, constraints);

		//campos de texto
		constraints.fill=GridBagConstraints.HORIZONTAL;
		constraints.anchor=GridBagConstraints.CENTER;

		constraints.gridy=4;
		panel.add(tDistancia, constraints);
		constraints.gridy=5;
		panel.add(tDuracion, constraints);
		constraints.gridy=6;
		panel.add(tPesoMaximo, constraints);

		//errores
		//constraints.anchor=GridBagConstraints.NORTHWEST;
		constraints.gridx=3;
		constraints.gridy=3;
		constraints.insets.set(5, 0, 15, 0);
		
		errorRuta.setPreferredSize(new Dimension(160, 16));
		errorRuta.setForeground(Color.red);
		panel.add(errorRuta,constraints);
		
		constraints.gridy=4;
		errorDistancia.setPreferredSize(new Dimension(180, 16));
		errorDistancia.setForeground(Color.red);
		panel.add(errorDistancia,constraints);

		constraints.gridy=5;
		errorDuracion.setPreferredSize(new Dimension(180, 16));
		errorDuracion.setForeground(Color.red);
		panel.add(errorDuracion,constraints);

		constraints.gridy=6;
		errorPesoMax.setPreferredSize(new Dimension(180, 16));
		errorPesoMax.setForeground(Color.red);
		panel.add(errorPesoMax,constraints);

		ventana.setContentPane(panel);
		ventana.pack();
		ventana.setSize(800, 600);
		ventana.setLocationRelativeTo(null);
		ventana.setTitle("Gestión de Entidades");
		ventana.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		ventana.setVisible(true);
	}
	
	public void modificarRuta() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		JLabel encabezado = new JLabel("Modificar Ruta");
		JButton aceptar = new JButton("Aceptar"), volver = new JButton("Volver");
		JComboBox<String> seleccionarRuta = new JComboBox<String>();
		
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
		panel.add(seleccionarRuta, constraints);
		
		for (Ruta ruta : controller.listaRutas()) {
			String item = "";
			item += Integer.toString(ruta.getId()) + ": " + ruta.getInicio().getValor().getNombre() + " / " + ruta.getFin().getValor().getNombre();
			seleccionarRuta.addItem(item);
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
			String stringRuta = (String)seleccionarRuta.getSelectedItem();
			String arr[] = stringRuta.split(": ");
			Integer idSeleccionado = Integer.valueOf(arr[0]);
			this.modificarRuta(controller.buscarRuta(idSeleccionado));
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
	
	public void modificarRuta(Ruta ruta) {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		JButton guardarCambios = new JButton("Guardar"), volver = new JButton("Volver");
		JLabel errorDistancia = new JLabel(), errorPesoMax = new JLabel(), errorDuracion = new JLabel(),
				encabezado = new JLabel("Modificar Ruta");
		JTextField tDistancia = new JTextField(20), tDuracion = new JTextField(20), tPesoMaximo = new JTextField(20),
					tPlantaInicio = new JTextField(20), tPlantaFin = new JTextField(20);

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
		panel.add(new JLabel("Planta Inicio: "), constraints);

		constraints.gridy=2;
		panel.add(new JLabel("Planta Fin: "), constraints);

		constraints.gridy=3;
		panel.add(new JLabel("Distancia (Km): "), constraints);

		constraints.gridy=4;
		panel.add(new JLabel("Duracion (Min): "), constraints);

		constraints.gridy=5;
		panel.add(new JLabel("Peso Máximo (Ton): "), constraints);
		
		//campos de texto
		constraints.insets.set(5, 5, 15, 5);
		constraints.fill=GridBagConstraints.HORIZONTAL;
		constraints.anchor=GridBagConstraints.CENTER;
		constraints.gridx = 2;	

		constraints.gridy = 1;
		tPlantaInicio.setText(ruta.getInicio().getValor().getNombre());
		tPlantaInicio.setEnabled(false);
		panel.add(tPlantaInicio, constraints);
		
		constraints.gridy = 2;
		tPlantaFin.setText(ruta.getFin().getValor().getNombre());
		tPlantaFin.setEnabled(false);
		panel.add(tPlantaFin, constraints);
		
		constraints.gridy = 3;
		tDistancia.setText(Integer.toString(ruta.getValor()));
		panel.add(tDistancia, constraints);
		
		constraints.gridy = 4;
		tDuracion.setText(Double.toString(ruta.getDuracionViajeMin()));
		panel.add(tDuracion, constraints);
		
		constraints.gridy = 5;
		tPesoMaximo.setText(Integer.toString(ruta.getPesoMaxTon()));
		panel.add(tPesoMaximo, constraints);
		
		//botones
		constraints.gridy=8;
		constraints.fill=GridBagConstraints.NONE;
		
		constraints.anchor=GridBagConstraints.EAST;
		constraints.gridx=2;
		constraints.insets.set(25,0,0,10);
		volver.addActionListener(a -> this.modificarRuta());
		panel.add(volver, constraints);
		
		constraints.insets.set(25,200,0,0);
		constraints.gridx=1;
		guardarCambios.addActionListener(a -> {
			Integer distanciaAux = null;
			Double duracionAux = null;
			Integer pesoMaxAux = null;

			errorDistancia.setText("");
			errorDuracion.setText("");
			errorPesoMax.setText("");
			
			try {
				if (tDistancia.getText().isEmpty()) {
					errorDistancia.setText("Debe ingresar una distancia");
					return;
				} else {
					distanciaAux = Integer.parseInt(tDistancia.getText());
				}
				
				if (tDuracion.getText().isEmpty()) {
					errorDuracion.setText("Debe ingresar una duración");
					return;
				} else {
					duracionAux = Double.parseDouble(tDuracion.getText());
				}
				
				if (tPesoMaximo.getText().isEmpty()) {
					errorPesoMax.setText("Debe ingresar un peso máximo");
					return;
				} else {
					pesoMaxAux = Integer.parseInt(tPesoMaximo.getText());
				}
				
				if(JOptionPane.showConfirmDialog(ventana, "¿Desea guardar los cambios?","Confirmación",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==0) {
					Ruta rutaAux = new Ruta(ruta.getId(), ruta.getInicio(), ruta.getFin(), distanciaAux, duracionAux, pesoMaxAux);
					controller.editarRuta(rutaAux);
					
					JOptionPane.showConfirmDialog(ventana, "Ruta modificada exitosamente.", "Información", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
					GestionEntidades.mostrarMenu();
				}
				
			} catch(NumberFormatException nfex) {
				if(distanciaAux == null) {
					JOptionPane.showConfirmDialog(ventana, "El campo distancia debe ser numérico.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);	
				} else {
					if (duracionAux == null) {
						JOptionPane.showConfirmDialog(ventana, "El campo duración debe ser numérico.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);	
					} else {
						if (pesoMaxAux == null) {
							JOptionPane.showConfirmDialog(ventana, "El campo peso máximo debe ser numérico.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
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
		
		constraints.gridy=3;
		errorDistancia.setPreferredSize(new Dimension(230, 16));
		errorDistancia.setForeground(Color.red);
		panel.add(errorDistancia,constraints);
				
		constraints.gridy=4;
		errorDuracion.setPreferredSize(new Dimension(230, 16));
		errorDuracion.setForeground(Color.red);
		panel.add(errorDuracion,constraints);
		
		constraints.gridy=5;
		errorPesoMax.setPreferredSize(new Dimension(230, 16));
		errorPesoMax.setForeground(Color.red);
		panel.add(errorPesoMax,constraints);
		
		//ventana
		ventana.setContentPane(panel);
		ventana.pack();
		ventana.setSize(800, 600);
		ventana.setLocationRelativeTo(null);
		ventana.setTitle("Sistema de Gestión de Entidades");
		ventana.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		ventana.setVisible(true);
	}

	public void eliminarRuta() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		JLabel encabezado = new JLabel("Eliminar Ruta"), errorSeleccion = new JLabel();
		JButton eliminar = new JButton("Eliminar"), volver = new JButton("Volver");
		JTable tablaRuta;
		List<Ruta> listaRutas = new ArrayList<Ruta>();
		listaRutas.addAll(controller.listaRutas());
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
		leftRenderer.setHorizontalAlignment( JLabel.LEFT );
		
		tablaRuta = new JTable(0,6);
		tablaRuta.getColumnModel().getColumn(0).setPreferredWidth(22);
		tablaRuta.getColumnModel().getColumn(1).setPreferredWidth(200);
		tablaRuta.getColumnModel().getColumn(2).setPreferredWidth(200);
		tablaRuta.getColumnModel().getColumn(3).setPreferredWidth(85);
		tablaRuta.getColumnModel().getColumn(4).setPreferredWidth(80);
		tablaRuta.getColumnModel().getColumn(5).setPreferredWidth(120);
		
		//tabla
		constraints.insets=new Insets(5, 5, 0, 5);
		tablaRuta.setFillsViewportHeight(true);
		tablaRuta.setBorder(new LineBorder(new Color(0, 0, 0)));
		tablaRuta.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane scroll = new JScrollPane(tablaRuta,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setPreferredSize(new Dimension(500, 197));
		
		//centrar celdas tabla
		tablaRuta.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		tablaRuta.getColumnModel().getColumn(1).setCellRenderer(leftRenderer);
		tablaRuta.getColumnModel().getColumn(2).setCellRenderer(leftRenderer);
		tablaRuta.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		tablaRuta.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
		tablaRuta.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
		//headers tabla
		tablaRuta.getColumnModel().getColumn(0).setHeaderValue("Id");
		tablaRuta.getColumnModel().getColumn(1).setHeaderValue("Planta Inicio");
		tablaRuta.getColumnModel().getColumn(2).setHeaderValue("Planta Fin");
		tablaRuta.getColumnModel().getColumn(3).setHeaderValue("Distancia");
		tablaRuta.getColumnModel().getColumn(4).setHeaderValue("Duración");
		tablaRuta.getColumnModel().getColumn(5).setHeaderValue("Peso máximo");
		
		constraints.gridx=1;
		constraints.gridy=1;
		constraints.gridheight=1;
		constraints.gridwidth=3;
		constraints.weightx=1;
		panel.add(scroll, constraints);
		
		//agregar datos tabla
		DefaultTableModel model = (DefaultTableModel) tablaRuta.getModel();
		DefaultCellEditor editor = (DefaultCellEditor) tablaRuta.getDefaultEditor(Object.class);
		editor.setClickCountToStart(10000);
		
		for (Ruta ruta : listaRutas) 
			model.addRow(new Object[]{Integer.toString(ruta.getId()), ruta.getInicio().getValor().getNombre(), ruta.getFin().getValor().getNombre(), Integer.toString(ruta.getValor()), Double.toString(ruta.getDuracionViajeMin()), Integer.toString(ruta.getPesoMaxTon())});
		
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
			int numFila = tablaRuta.getSelectedRow();
			if (numFila == -1) {
				errorSeleccion.setText("Debes seleccionar una ruta");
				return;
			} else {
				int id = Integer.valueOf((String)tablaRuta.getValueAt(numFila, 0));
				Ruta ruta = controller.buscarRuta(id);
				
				if(JOptionPane.showConfirmDialog(ventana, "¿Desea eliminar la ruta seleccionada?","Confirmación",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==0) {
					controller.eliminarRuta(ruta);
					this.eliminarRuta();
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

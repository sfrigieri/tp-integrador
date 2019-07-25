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
import javax.swing.CellEditor;
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
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

import isi.died.tp.controller.PlantaController;
import isi.died.tp.controller.StockController;
import isi.died.tp.model.Insumo;
import isi.died.tp.model.InsumoLiquido;
import isi.died.tp.model.Planta;
import isi.died.tp.model.PlantaAcopio;
import isi.died.tp.model.PlantaProduccion;
import isi.died.tp.model.Stock;
import isi.died.tp.model.StockProduccion;
import isi.died.tp.model.Unidad;
import isi.died.tp.service.PlantaService;

public class ABMPlanta {
	private PlantaController controller;
	private JFrame ventana;
	private StockController sc;
	
	public ABMPlanta(JFrame ventana,PlantaController plantaController, StockController stockController){
		this.controller = plantaController;
		this.ventana = ventana;
		this.sc = stockController;
	}
	
	public void agregarPlanta() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		JLabel errorNombre = new JLabel(), errorTipo = new JLabel(), errorOrigen = new JLabel(),
				encabezado = new JLabel("Agregar Nueva Planta");
		JTextField nombrePlanta = new JTextField(20);
		JButton aceptar = new JButton("Aceptar"), volver = new JButton("Volver");
		JComboBox<String> tipoPlanta = new JComboBox<String>();
		JCheckBox esOrigen = new JCheckBox();
		List<PlantaAcopio> listaPlantasAcopio = controller.listaPlantasAcopio();
		
		//titulo
		constraints.insets.set(5, 5, 40, 5);
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
				
		constraints.gridy=1;
		panel.add(new JLabel("Tipo de Planta: "), constraints);
		
		constraints.gridy=2;
		panel.add(new JLabel("Nombre: "), constraints);
		
		constraints.gridy=3;
		panel.add(new JLabel("Es Origen: "), constraints);
		
		//combobox tipoPlanta
		constraints.fill=GridBagConstraints.HORIZONTAL;
		constraints.anchor=GridBagConstraints.CENTER;
		tipoPlanta.addItem("Acopio");
		tipoPlanta.addItem("Producción");
		tipoPlanta.setSelectedItem("Acopio");
		constraints.insets.set(0, 5, 5, 5);
		constraints.gridx = 2;
		constraints.gridy = 1;
		constraints.gridwidth=1;
		panel.add(tipoPlanta, constraints);	
		
		//campo texto nombre
		constraints.gridx = 2;
		constraints.gridy = 2;
		panel.add(nombrePlanta, constraints);
		
		//checkbox
		constraints.fill=GridBagConstraints.NONE;
		constraints.anchor=GridBagConstraints.WEST;
		constraints.insets.set(0, 0, 5, 5);
		constraints.gridx = 2;
		constraints.gridy = 3;
		panel.add(esOrigen, constraints);
		
		//listener tipoPlanta
		tipoPlanta.addActionListener (a -> {
			switch((String)tipoPlanta.getSelectedItem()){
			case "Acopio":
				esOrigen.setEnabled(true);
				break;
			case "Producción":
				esOrigen.setEnabled(false);
				break;
			}
		});
		
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
			String tipo;
			String nombre;
			Boolean origen = false, existeOrigen = false, existeSumidero = false;
			errorOrigen.setText("");
			errorTipo.setText("");
			errorNombre.setText("");
			
			if ((String)tipoPlanta.getSelectedItem() == "Acopio") {
				origen = esOrigen.isSelected();
				//verifico si ya hay cargado un origen / sumidero
				for(PlantaAcopio planta : listaPlantasAcopio) {
					if (planta.esOrigen())
						existeOrigen = true;
					else
						existeSumidero = true;
				}
				if(existeOrigen && existeSumidero) {
					errorTipo.setText("Ya existen ambas Plantas de Acopio");
					return;
				} else {
					if (origen && existeOrigen) {
						errorOrigen.setText("Ya existe una Planta de Acopio origen");
						return;
					} else if (!origen && existeSumidero) {
						errorOrigen.setText("Ya existe una Planta de Acopio sumidero");
						return;
					} else {
						tipo = (String)tipoPlanta.getSelectedItem();
					}
				}
			} else {
				tipo = (String)tipoPlanta.getSelectedItem();
			}
			if(nombrePlanta.getText().isEmpty()) {
				errorNombre.setText("Debe ingresar un nombre");
				return;
			} else {
				nombre = nombrePlanta.getText();
			}
			
			if(JOptionPane.showConfirmDialog(ventana, "¿Desea guardar la nueva planta con los datos ingresados?","Confirmación",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==0) {
				if (tipo == "Acopio") { //
					Planta planta = new PlantaAcopio(0, nombre, origen);
					controller.agregarPlanta(planta);
				} else {
					Planta planta = new PlantaProduccion(0, nombre);
					controller.agregarPlanta(planta);
				}
				if(JOptionPane.showConfirmDialog(ventana, "Planta creada exitosamente. \n¿Desea agregar otra planta?","Confirmación",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==0) {
					this.agregarPlanta();
				} else {
					GestionEntidades.mostrarMenu();
				}
			}
			
		});
		panel.add(aceptar, constraints);
		
		//errores
		constraints.anchor=GridBagConstraints.NORTHWEST;
		constraints.insets.set(5,0,3,0);
		constraints.gridx=3;
				
		constraints.gridy=1;
		errorTipo.setPreferredSize(new Dimension(230, 16));
		errorTipo.setForeground(Color.red);
		panel.add(errorTipo,constraints);
				
		constraints.gridy=2;
		errorNombre.setPreferredSize(new Dimension(230, 16));
		errorNombre.setForeground(Color.red);
		panel.add(errorNombre,constraints);
				
		constraints.gridy=3;
		errorOrigen.setPreferredSize(new Dimension(230, 16));
		errorOrigen.setForeground(Color.red);
		panel.add(errorOrigen,constraints);
		
		//ventana
		ventana.setContentPane(panel);
		ventana.pack();
		ventana.setSize(800, 600);
		ventana.setLocationRelativeTo(null);
		ventana.setTitle("Sistema de Gestión de Entidades");
		ventana.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		ventana.setVisible(true);
	}
	
	public void modificarPlanta() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		JLabel encabezado = new JLabel("Modificar Planta");
		JButton aceptar = new JButton("Aceptar"), volver = new JButton("Volver");
		JComboBox<String> seleccionarPlanta = new JComboBox<String>();
		
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
		panel.add(seleccionarPlanta, constraints);
		
		for (Planta planta : controller.listaPlantas()) {
			String item = "";
			item += Integer.toString(planta.getId()) + ": " + planta.getNombre();
			if (planta instanceof PlantaAcopio) {
				item += " (A)";
			} else {
				item += " (P)";
			}
			seleccionarPlanta.addItem(item);
		}
		
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
			String stringPlanta = (String)seleccionarPlanta.getSelectedItem();
			String arr[] = stringPlanta.split(": ");
			Integer idSeleccionado = Integer.valueOf(arr[0]);
			String segParte[] = arr[1].split(" ");
			String ultimaPalabra = segParte[segParte.length-1];
			
			if (ultimaPalabra.equals("(A)")) {
				this.modificarPlanta((PlantaAcopio)controller.buscarPlantaAcopio(idSeleccionado));
			} else {
				this.modificarPlanta((PlantaProduccion)controller.buscarPlantaProduccion(idSeleccionado));
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
	
	public void modificarPlanta(Planta planta){
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		JLabel encabezado = new JLabel("Modificar Planta");
		JButton guardarCambios = new JButton("Guardar"), volver = new JButton("Volver");
		JLabel errorNombre = new JLabel();
		JTextField nombrePlanta = new JTextField(20);
		JComboBox<String> tipoPlanta = new JComboBox<String>();
		JCheckBox esOrigen = new JCheckBox();
		
		//titulo
		constraints.gridx=0;
		constraints.gridy=0;
		constraints.gridheight=1;
		constraints.gridwidth=8;
		constraints.anchor=GridBagConstraints.NORTH;
		constraints.insets.set(5, 5, 40, 5);
		encabezado.setFont(new Font(encabezado.getFont().getName(), encabezado.getFont().getStyle(), 40));
		panel.add(encabezado,constraints);
		
		//labels
		constraints.fill=GridBagConstraints.NONE;
		constraints.anchor=GridBagConstraints.EAST;
		constraints.insets.set(5, 5, 15, 5);
		constraints.gridx=1;
		constraints.gridwidth=1;
		
		constraints.gridy=1;
		panel.add(new JLabel("Tipo de Planta: "), constraints);
		
		constraints.gridy=2;
		panel.add(new JLabel("Nombre: "), constraints);
		
		constraints.gridy=3;
		panel.add(new JLabel("Es Origen: "), constraints);
		
		//checkbox origen
		constraints.fill=GridBagConstraints.NONE;
		constraints.anchor=GridBagConstraints.WEST;
		constraints.insets.set(0, 0, 5, 5);
		constraints.gridx = 2;
		constraints.gridy = 3;
		esOrigen.setEnabled(false);
		if (planta.esOrigen())
			esOrigen.setSelected(true);
		else
			esOrigen.setSelected(false);
		panel.add(esOrigen, constraints);
		
		//campo texto nombre
		constraints.fill=GridBagConstraints.HORIZONTAL;
		constraints.anchor=GridBagConstraints.CENTER;
		constraints.insets.set(0, 5, 5, 5);
		constraints.gridy=2;
		nombrePlanta.setText(planta.getNombre());
		panel.add(nombrePlanta, constraints);

		//combobox tipo
		constraints.fill=GridBagConstraints.HORIZONTAL;
		constraints.anchor=GridBagConstraints.CENTER;
		tipoPlanta.setEnabled(false);
		tipoPlanta.addItem("Acopio");
		tipoPlanta.addItem("Producción");
		if (planta instanceof PlantaAcopio)
			tipoPlanta.setSelectedItem("Acopio");
		else
			tipoPlanta.setSelectedItem("Producción");
		constraints.insets.set(0, 5, 5, 5);
		constraints.gridy = 1;
		panel.add(tipoPlanta, constraints);	
		
		//botones
		constraints.gridy=8;
		constraints.fill=GridBagConstraints.NONE;
		
		constraints.anchor=GridBagConstraints.EAST;
		constraints.gridx=2;
		constraints.insets=new Insets(20, 5, 5, 15);
		volver.addActionListener(a -> this.modificarPlanta());
		panel.add(volver, constraints);
		
		//boton guardarCambios				
		constraints.anchor=GridBagConstraints.WEST;
		constraints.insets=new Insets(20, 35, 5, 0);
		constraints.gridx=1;
		guardarCambios.addActionListener(a -> {
			String valorNombre;
					
			errorNombre.setText("");
			if(nombrePlanta.getText().isEmpty()) {
				errorNombre.setText("Debe ingresar un nombre");
				return;
			}else {
				valorNombre = nombrePlanta.getText();
			}
					
			if(JOptionPane.showConfirmDialog(ventana, "¿Desea guardar los cambios?","Confirmación",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==0) {
				if (planta instanceof PlantaAcopio) {
					Planta plantaNueva = new PlantaAcopio(planta.getId(), valorNombre, planta.esOrigen());
					controller.editarPlanta(plantaNueva);
				} else {
					Planta plantaNueva = new PlantaProduccion(planta.getId(), valorNombre);
					controller.editarPlanta(plantaNueva);
				}
				JOptionPane.showConfirmDialog(ventana, "Planta modificada exitosamente.", "Información", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
				GestionEntidades.mostrarMenu();
			}
		});
		panel.add(guardarCambios,constraints);
		
		//errores
		constraints.anchor=GridBagConstraints.NORTHWEST;
		constraints.insets.set(5,0,3,0);
		constraints.gridx=3;
		constraints.gridy=2;
		errorNombre.setPreferredSize(new Dimension(230, 16));
		errorNombre.setForeground(Color.red);
		panel.add(errorNombre,constraints);
				
		//ventana
		ventana.setContentPane(panel);
		ventana.pack();
		ventana.setSize(800, 600);
		ventana.setLocationRelativeTo(null);
		ventana.setTitle("Sistema de Gestión de Entidades");
		ventana.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		ventana.setVisible(true);
	}
	
	public void eliminarPlanta (boolean mostrarTablaAcopio) {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		JLabel encabezado = new JLabel("Eliminar Planta"), errorSeleccion = new JLabel();
		JButton eliminar = new JButton("Eliminar"), volver = new JButton("Volver"), cambiarTipoPlanta = new JButton("Ver Plantas de Producción");
		JTable tablaPlantas;
		List<Planta> listaPlantasAcopio = new ArrayList<Planta>();
		List<Planta> listaPlantasProduccion = new ArrayList<Planta>();
		listaPlantasAcopio.addAll(controller.listaPlantasAcopio());
		listaPlantasProduccion.addAll(controller.listaPlantasProduccion());
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
		leftRenderer.setHorizontalAlignment( JLabel.LEFT );
		
		if (mostrarTablaAcopio) {
			tablaPlantas = new JTable(0,3);
			tablaPlantas.getColumnModel().getColumn(0).setPreferredWidth(17);
			tablaPlantas.getColumnModel().getColumn(1).setPreferredWidth(250);
			tablaPlantas.getColumnModel().getColumn(2).setPreferredWidth(17);
			tablaPlantas.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
			tablaPlantas.getColumnModel().getColumn(2).setHeaderValue("Es Origen");
		} else {
			tablaPlantas = new JTable(0,2);
			tablaPlantas.getColumnModel().getColumn(0).setPreferredWidth(74);
			tablaPlantas.getColumnModel().getColumn(1).setPreferredWidth(396);
		}
		
		//tabla
		constraints.insets=new Insets(5, 5, 0, 5);
		tablaPlantas.setFillsViewportHeight(true);
		tablaPlantas.setBorder(new LineBorder(new Color(0, 0, 0)));
		tablaPlantas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane scroll = new JScrollPane(tablaPlantas,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setPreferredSize(new Dimension(500, 197));
		
		//centrar celdas tabla
		tablaPlantas.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		tablaPlantas.getColumnModel().getColumn(1).setCellRenderer(leftRenderer);
		//tamaño y headers tabla
		tablaPlantas.getColumnModel().getColumn(0).setHeaderValue("Id");
		tablaPlantas.getColumnModel().getColumn(1).setHeaderValue("Nombre");
		
		constraints.gridx=1;
		constraints.gridy=1;
		constraints.gridheight=1;
		constraints.gridwidth=3;
		constraints.weightx=1;
		panel.add(scroll, constraints);
		
		//agregar datos tabla
		DefaultTableModel model = (DefaultTableModel) tablaPlantas.getModel();
		DefaultTableModel modeloDefecto = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		DefaultCellEditor editor = (DefaultCellEditor) tablaPlantas.getDefaultEditor(Object.class);
		editor.setClickCountToStart(10000);
		
		if (mostrarTablaAcopio) {
			cambiarTipoPlanta.setText("Ver Plantas de Producción");
			for (Planta planta : listaPlantasAcopio) {
				String valorOrigen = "";
				if (planta.esOrigen())
					valorOrigen += "SI";
				else
					valorOrigen += "NO";
				model.addRow(new Object[]{Integer.toString(planta.getId()), planta.getNombre(), valorOrigen});
			}
		} else {
			cambiarTipoPlanta.setText("Ver Plantas de Acopio");
			for (Planta planta : listaPlantasProduccion) {
				model.addRow(new Object[]{Integer.toString(planta.getId()), planta.getNombre()});
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
			int numFila = tablaPlantas.getSelectedRow();
			if (numFila == -1) {
				errorSeleccion.setText("Debes seleccionar una planta");
				return;
			} else {
				int id = Integer.valueOf((String)tablaPlantas.getValueAt(numFila, 0));
				if (mostrarTablaAcopio) {
					PlantaAcopio planta = (PlantaAcopio)controller.buscarPlantaAcopio(id);
					if (planta.esOrigen() && planta.getListaDeInsumos() != null && !planta.getListaDeInsumos().isEmpty()) {
						errorSeleccion.setText("No es posible eliminar. Existen insumos asociados");
												
						return;
					} else {
						if(JOptionPane.showConfirmDialog(ventana, "¿Desea eliminar la planta seleccionada?","Confirmación",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==0) {
							controller.eliminarPlanta(planta);
							this.eliminarPlanta(true);
						}
					}
				} else {
					PlantaProduccion planta = (PlantaProduccion)controller.buscarPlantaProduccion(id);
					if(JOptionPane.showConfirmDialog(ventana, "¿Desea eliminar la planta seleccionada?","Confirmación",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==0) {
						controller.eliminarPlanta(planta);
						this.eliminarPlanta(false);
					}
				}
			}
		});
		panel.add(eliminar,constraints);
		
		constraints.gridy=2;
		constraints.gridx=1;
		constraints.anchor=GridBagConstraints.CENTER;
		constraints.insets = new Insets(1,5,39,305);
		cambiarTipoPlanta.setPreferredSize(new Dimension(200, 25));
		cambiarTipoPlanta.addActionListener(a -> {
			if (cambiarTipoPlanta.getText() == "Ver Plantas de Producción") {
				this.eliminarPlanta(false);
			} else if (cambiarTipoPlanta.getText() == "Ver Plantas de Acopio"){
				this.eliminarPlanta(true);
			}
		});
		panel.add(cambiarTipoPlanta, constraints);
		
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
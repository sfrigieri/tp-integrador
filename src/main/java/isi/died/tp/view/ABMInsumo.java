package isi.died.tp.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import isi.died.tp.controller.InsumoController;
import isi.died.tp.model.Unidad;



public class ABMInsumo {

	private InsumoController controller;
	private JFrame ventana;
	
	public ABMInsumo(JFrame ventana){
		this.controller = new InsumoController();
		this.ventana = ventana;
	}
	
	public void agregarInsumo() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		JLabel errorDescripcion = new JLabel(), errorCosto = new JLabel(),
				errorPeso = new JLabel(), errorRefrigerado = new JLabel(), errorDensidad = new JLabel(),
				encabezado = new JLabel("Agregar Nuevo Insumo"), pesoCantidad = new JLabel("Peso: ");
		JTextField tDescripcion = new JTextField(20), tCosto = new JTextField(20),
					tPeso = new JTextField(20), tDensidad = new JTextField(20);
		JButton aceptar = new JButton("Aceptar"), cancelar = new JButton("Cancelar");
		JComboBox<Unidad> lUnidad = new JComboBox<Unidad>();
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
		panel.add(new JLabel("Costo: "), constraints);
		
		constraints.gridy=4;
		panel.add(pesoCantidad, constraints);

		constraints.gridy=5;
		panel.add(new JLabel("Densidad: "), constraints);
		
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
		panel.add(tDescripcion, constraints);

		constraints.gridy=3;
		panel.add(tCosto, constraints);
		
		constraints.gridy=4;
		panel.add(tPeso, constraints);
		
		constraints.gridy=5;
		panel.add(tDensidad, constraints);
		tDensidad.setEnabled(false);
		
		constraints.gridy=7;
		lUnidad.addItem(Unidad.KILO);
		lUnidad.addItem(Unidad.METRO);
		lUnidad.addItem(Unidad.M2);
		lUnidad.addItem(Unidad.LITRO);
		lUnidad.setSelectedItem(Unidad.KILO);
		panel.add(lUnidad, constraints);
		
		//listener checkboxLiquido
		ItemListener esLiquidoListener = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				int estado = itemEvent.getStateChange();
				if (estado == ItemEvent.SELECTED) { //si es liquido-- 	 descrip, unidad(LITRO) , double costo, boolean esRef, double dens, double litros
					lUnidad.setSelectedItem(Unidad.LITRO);
					lUnidad.setEnabled(false);
					pesoCantidad.setText("Cantidad: ");
					tDensidad.setEnabled(true);
				} else {							//si no es liquido-- descrip, unidadDeMedida, double costo, boolean esRefrigerado,double peso
					lUnidad.setEnabled(true);
					lUnidad.setSelectedItem(Unidad.KILO);
					pesoCantidad.setText("Peso: ");
					tDensidad.setEnabled(false);
				}
			}
		};
		esLiquido.addItemListener(esLiquidoListener);
		
		//botones
		constraints.insets.set(0,0,0,0);
		constraints.gridy=10;
		constraints.fill=GridBagConstraints.NONE;
		constraints.anchor=GridBagConstraints.EAST;
		
		constraints.gridx=0;
		panel.add(new JLabel(""), constraints);
		constraints.gridx=1;
		cancelar.addActionListener(a -> GestionEntidades.mostrarMenu());
		panel.add(cancelar, constraints);
						
		constraints.gridx=2;
		aceptar.addActionListener(e -> {
			String descripcion;
			Double costo = null, peso = null;
			Boolean refrigerado = false;
			Unidad unidad;
			Double densidad = null;
					
			errorDescripcion.setText("");
			errorCosto.setText("");
			errorPeso.setText("");
			errorDensidad.setText("");
			try {
				if(tDescripcion.getText().isEmpty()) {
					errorDescripcion.setText("Debe ingresar una descripción");
					return;
				}else {
					descripcion = tDescripcion.getText();
				}
				if(tCosto.getText().isEmpty()) {
					errorCosto.setText("Debe ingresar un costo");
					return;
				}else {
					costo = Double.parseDouble(tCosto.getText());
				}
				if(tPeso.getText().isEmpty()) {
					if (!esLiquido.isSelected()) {
						errorPeso.setText("Debe ingresar un peso");
					} else {
						errorPeso.setText("Debe ingresar una cantidad");
					}
					return;
				}else{
					peso = Double.parseDouble(tPeso.getText());
				}
				if(esLiquido.isSelected() && tDensidad.getText().isEmpty()) {
					errorDensidad.setText("Debe ingresar una densidad");
					return;
				} else if (esLiquido.isSelected()){
					densidad = Double.parseDouble(tDensidad.getText());
				}
						
				unidad = (Unidad)lUnidad.getSelectedItem();
				refrigerado = esRefrigerado.isSelected();
						
				if(JOptionPane.showConfirmDialog(ventana, "¿Desea guardar el nuevo insumo con los datos ingresados?","Confirmación",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==0) {
					if (esLiquido.isSelected()) {
						controller.agregarInsumo(0, descripcion, costo, refrigerado, densidad, peso);
					} else {
						controller.agregarInsumo(0, descripcion, unidad, costo, peso, refrigerado);
					}
				}					
						
			}catch(NumberFormatException nfex) {
				if(costo == null) {
					JOptionPane.showConfirmDialog(ventana, "El campo Costo debe ser numérico.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);	
				} else {
					if(peso == null) {
						if (esLiquido.isSelected()) {
							JOptionPane.showConfirmDialog(ventana, "El campo Cantidad debe ser numérico.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);	
						} else {
							JOptionPane.showConfirmDialog(ventana, "El campo Peso debe ser numérico.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);	
						}
					} else {
						if (densidad == null) {
							JOptionPane.showConfirmDialog(ventana, "El campo Densidad debe ser numérico.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);	
						}
					}
				}
			}
					
		});
		panel.add(aceptar, constraints);
				
		constraints.anchor=GridBagConstraints.NORTHWEST;
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
		errorPeso.setPreferredSize(new Dimension(230, 16));
		errorPeso.setForeground(Color.red);
		panel.add(errorPeso,constraints);
		
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
		// TODO Auto-generated method stub
		
	}
	
}

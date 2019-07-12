package isi.died.tp.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import isi.died.tp.app.Main;
import isi.died.tp.controller.InsumoController;
import isi.died.tp.dao.InsumoDao;
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
				errorPeso = new JLabel(), errorRefrigerado = new JLabel(), 
				encabezado = new JLabel("Agregar Nuevo Insumo");
		JTextField tDescripcion = new JTextField(20), tCosto = new JTextField(20),
					tPeso = new JTextField(20);
		JButton aceptar = new JButton("Aceptar"), cancelar = new JButton("Cancelar");
		JComboBox<Unidad> lUnidad = new JComboBox<Unidad>();
		JComboBox<Boolean> lRefrigerado = new JComboBox<Boolean>();
		
			
		constraints.insets=new Insets(5, 5, 5, 5);
		
		constraints.gridx=0;
		constraints.gridy=0;
		constraints.gridheight=1;
		constraints.gridwidth=4;
		constraints.anchor=GridBagConstraints.NORTH;
		encabezado.setFont(new Font(encabezado.getFont().getName(), encabezado.getFont().getStyle(), 40));
		panel.add(encabezado,constraints);
		constraints.fill=GridBagConstraints.HORIZONTAL;
		constraints.anchor=GridBagConstraints.CENTER;
	
		constraints.gridx=0;
		constraints.gridy=1;
		constraints.gridwidth=2;
		panel.add(new JLabel("Descripción: "), constraints);
		
		constraints.gridx=2;
		constraints.gridy=1;
		constraints.gridwidth=1;
		panel.add(tDescripcion, constraints);
		
		constraints.gridx=0;
		constraints.gridy=2;
		constraints.gridwidth=2;
		panel.add(new JLabel("Costo: "), constraints);
		
		constraints.gridx=2;
		constraints.gridy=2;
		constraints.gridwidth=1;
		panel.add(tCosto, constraints);
		
		constraints.gridx=0;
		constraints.gridy=3;
		constraints.gridwidth=2;
		panel.add(new JLabel("Peso: "), constraints);
		
		constraints.gridx=2;
		constraints.gridy=3;
		constraints.gridwidth=1;
		panel.add(tPeso, constraints);
		
		constraints.gridx=0;
		constraints.gridy=4;
		constraints.gridwidth=2;
		panel.add(new JLabel("Es Refrigerado: "), constraints);
		
		constraints.gridx=2;
		constraints.gridy=4;
		constraints.gridwidth=1;
		lRefrigerado.addItem(Boolean.FALSE);
		lRefrigerado.addItem(Boolean.TRUE);
		lRefrigerado.setSelectedItem(Boolean.FALSE);
		panel.add(lRefrigerado, constraints);
		
		constraints.gridx=0;
		constraints.gridy=5;
		constraints.gridwidth=2;
		panel.add(new JLabel("Unidad: "), constraints);
		
		constraints.gridx=2;
		constraints.gridy=5;
		constraints.gridwidth=1;
		lUnidad.addItem(Unidad.KILO);
		lUnidad.addItem(Unidad.METRO);
		lUnidad.addItem(Unidad.M2);
		lUnidad.setSelectedItem(Unidad.PIEZA);
		panel.add(lUnidad, constraints);
		
		constraints.gridx=0;
		constraints.gridy=10;
		cancelar.addActionListener(a -> Main.mostrarInterfaz());
		panel.add(cancelar, constraints);
				
		constraints.gridx=3;
		constraints.gridy=10;
		constraints.fill=GridBagConstraints.NONE;
		constraints.anchor=GridBagConstraints.WEST;
		aceptar.addActionListener(e -> {
			String descripcion;
			Double costo = null, peso = null;
			Boolean esRefrigerado = false;
			Unidad unidad;
			
			errorDescripcion.setText("");
			errorCosto.setText("");
			errorPeso.setText("");
			errorRefrigerado.setText("");
			try {
				if(tDescripcion.getText().isEmpty()) {
					errorDescripcion.setText("Debe ingresar una descripcion");
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
					errorPeso.setText("Debe ingresar un peso");
					return;
				}else{
					peso = Double.parseDouble(tPeso.getText());
				}
				
				unidad = (Unidad)lUnidad.getSelectedItem();
				esRefrigerado = (Boolean)lRefrigerado.getSelectedItem();
				
				if(JOptionPane.showConfirmDialog(ventana, "¿Está seguro que desea guardar el nuevo insumo con los datos ingresados?","Confirmacion",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==0) {
					controller.agregarInsumo(0, descripcion, unidad, costo, peso, esRefrigerado);
				}
				
								
				
			}catch(NumberFormatException nfex) {
				if(costo == null) {
					JOptionPane.showConfirmDialog(ventana, "El campo Costo debe ser numérico.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);	
				}else {
					if(peso == null) {
						JOptionPane.showConfirmDialog(ventana, "El campo Peso debe ser numérico.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);	
					}
				}
			}
			
		});
		panel.add(aceptar, constraints);
		
		constraints.gridx=3;
		constraints.gridy=1;
		errorDescripcion.setPreferredSize(new Dimension(230, 16));
		errorDescripcion.setForeground(Color.red);
		panel.add(errorDescripcion,constraints);
		
		constraints.gridy=2;
		errorCosto.setPreferredSize(new Dimension(230, 16));
		errorCosto.setForeground(Color.red);
		panel.add(errorCosto,constraints);
		
		constraints.gridy=3;
		errorPeso.setPreferredSize(new Dimension(230, 16));
		errorPeso.setForeground(Color.red);
		panel.add(errorPeso,constraints);
		
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

package isi.died.tp.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import isi.died.tp.controller.GestionEntidadesController;
import isi.died.tp.controller.InsumoController;
import isi.died.tp.controller.OpcionesOrdenInsumo;
import isi.died.tp.model.Insumo;

public class BusquedaInsumosView {

	private static InsumoController ic;
	private static JFrame ventana;
	
	
	public BusquedaInsumosView(JFrame v) {
		ic = GestionEntidadesController.insumoController;
		ventana = v;
	}

	public void busquedaEnABB() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		JTextField campoNombre = new JTextField(25), campoStockMin = new JTextField(8), 
				campoStockMax = new JTextField(8), campoCostoMin = new JTextField(8), campoCostoMax = new JTextField(8);
		JLabel encabezado = new JLabel("Búsqueda de Insumos"), filtrosTitle = new JLabel("Filtros"),
				filtroNombre = new JLabel("Nombre:"), filtroStock = new JLabel("Cantidad Disponible: Entre "),
				filtroStock2 = new JLabel(" y "), filtroCosto = new JLabel("Costo: Entre "), filtroCosto2 = new JLabel(" y "),
						ordenarPor = new JLabel("Ordenamiento Lista Por:");
		JButton volver = new JButton("Volver"),  buscar = new JButton("Buscar");

		JComboBox<OpcionesOrdenInsumo> ordenamientoSelect = new JComboBox<OpcionesOrdenInsumo>();
		ordenamientoSelect.addItem(OpcionesOrdenInsumo.NOMBRE);
		ordenamientoSelect.addItem(OpcionesOrdenInsumo.COSTO);
		ordenamientoSelect.addItem(OpcionesOrdenInsumo.CANTIDAD_TOTAL_DISPONIBLE);

		
		constraints.insets=new Insets(5, 5, 100, 5);
		constraints.gridx=0;
		constraints.gridy=0;
		constraints.gridheight=1;
		constraints.gridwidth=8;
		constraints.fill=GridBagConstraints.HORIZONTAL;
		constraints.anchor=GridBagConstraints.CENTER;
		encabezado.setFont(new Font(encabezado.getFont().getName(), encabezado.getFont().getStyle(), 40));
		panel.add(encabezado,constraints);


		constraints.gridy=3;
		constraints.gridwidth=1;
		filtrosTitle.setFont(new Font(filtrosTitle.getFont().getName(), filtrosTitle.getFont().getStyle(), 17));
		panel.add(filtrosTitle,constraints);
		
		
		constraints.gridx=0;
		constraints.gridy=5;
		constraints.gridwidth=1;
		constraints.anchor = GridBagConstraints.WEST;
		panel.add(filtroNombre,constraints);
		
		constraints.gridx=1;
		constraints.gridy=5;
		constraints.gridwidth=3;
		constraints.anchor = GridBagConstraints.CENTER;
		panel.add(campoNombre, constraints);
		
		
		constraints.gridx=0;
		constraints.gridy=7;
		constraints.gridwidth=1;
		constraints.anchor = GridBagConstraints.WEST;
		panel.add(filtroStock, constraints);
		
		constraints.gridx=1;
		constraints.gridy=7;
		constraints.gridwidth=1;
		constraints.anchor = GridBagConstraints.CENTER;
		panel.add(campoStockMin, constraints);
		

		constraints.gridx=2;
		constraints.gridy=7;
		constraints.gridwidth=1;
		constraints.anchor = GridBagConstraints.CENTER;
		panel.add(filtroStock2, constraints);
		
		constraints.gridx=3;
		constraints.gridy=7;
		constraints.gridwidth=1;
		constraints.anchor = GridBagConstraints.CENTER;
		panel.add(campoStockMax, constraints);
		
		
		constraints.gridx=0;
		constraints.gridy=11;
		constraints.gridwidth=1;
		constraints.anchor = GridBagConstraints.WEST;
		panel.add(filtroCosto, constraints);
		
		constraints.gridx=1;
		constraints.gridy=11;
		constraints.gridwidth=1;
		constraints.anchor = GridBagConstraints.CENTER;
		panel.add(campoCostoMin, constraints);
		

		constraints.gridx=2;
		constraints.gridy=11;
		constraints.gridwidth=1;
		constraints.anchor = GridBagConstraints.CENTER;
		panel.add(filtroCosto2, constraints);
		
		constraints.gridx=3;
		constraints.gridy=11;
		constraints.gridwidth=1;
		constraints.anchor = GridBagConstraints.CENTER;
		panel.add(campoCostoMax, constraints);
		

		constraints.gridx=0;
		constraints.gridy=13;
		constraints.gridwidth=1;
		constraints.anchor = GridBagConstraints.WEST;
		panel.add(ordenarPor, constraints);
		
	
		constraints.gridx=1;
		constraints.gridwidth=3;
		constraints.anchor = GridBagConstraints.CENTER;
		panel.add(ordenamientoSelect, constraints);
		
	
		constraints.gridx=0;
		constraints.gridy=15;
		constraints.gridwidth=1;
		constraints.anchor = GridBagConstraints.WEST;
		buscar.addActionListener( a -> 	{	
		
		
		List<Insumo> listaInsumosFiltro = new ArrayList<Insumo>();

		Integer stockMin = null, stockMax = null;
		Double costoMin = null, costoMax = null;
		String nombre = null;


		}
		);
		panel.add(buscar, constraints);
		
		
		constraints.gridx=3;
		constraints.gridwidth=1;
		constraints.anchor = GridBagConstraints.EAST;
		volver.addActionListener( a -> GestionEntidades.mostrarMenu());
		panel.add(volver,constraints);
		
		ventana.setContentPane(panel);
		ventana.pack();
		ventana.setSize(800, 600);
		
        ventana.setVisible(true);
	}
	
	
	
	
	/*public void setRedPlantas(Boolean firstTime) {
		JPanel panel = new JPanel(new BorderLayout()), panelInferior = new JPanel(new GridBagLayout()),
				panelSuperior = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		JComboBox<String> seleccionarInsumo = new JComboBox<String>();
		JLabel info = new JLabel(" ");
		JButton volver = new JButton("Volver"), mejorCamino = new JButton("Buscar Mejor Camino"),
				accionesInfo = new JButton("Ver Acciones Disponibles");
		List<Insumo> lista = new ArrayList<Insumo>();

		constraints.fill=GridBagConstraints.HORIZONTAL;

		lista.addAll(glc.listaInsumos());
		seleccionarInsumo.addItem("Seleccione");
		for (Insumo ins : lista) {
			if(ins.getStock() != null)
				seleccionarInsumo.addItem(Integer.toString(ins.getId()) + ": Desc: " + ins.getDescripcion()+ " | Tipo: General | Cant. Disponible: "+ins.getStock().getCantidad()+" ");
			else
				seleccionarInsumo.addItem(Integer.toString(ins.getId()) + ": Desc: " + ins.getDescripcion()+ " | Tipo: General | Cant. Disponible: 0 ");
		}
		lista.addAll(glc.listaInsumosLiquidos());
		for (Insumo ins : glc.listaInsumosLiquidos()) {
			if(ins.getStock() != null)
				seleccionarInsumo.addItem(Integer.toString(ins.getId()) + ": Desc: " + ins.getDescripcion() + " | Tipo: Líquido | Cant. Disponible: "+ins.getStock().getCantidad()+" ");
			else
				seleccionarInsumo.addItem(Integer.toString(ins.getId()) + ": Desc: " + ins.getDescripcion()+ " | Tipo: Líquido | Cant. Disponible: 0 ");
		}
		if(lista.isEmpty())
			seleccionarInsumo.setEnabled(false);

		seleccionarInsumo.setSelectedItem("Seleccione");
		constraints.gridx=0;
		constraints.gridy=2;

		panelInferior.add(seleccionarInsumo,constraints);

		
		List<Insumo> seleccionado = new ArrayList<Insumo>();
		seleccionarInsumo.addActionListener (a -> {
			switch(seleccionarInsumo.getSelectedIndex()){
			case 0:
				mejorCamino.setEnabled(false);
				seleccionado.clear();
				glc.refrescarGrafo();
				info.setText(" ");
				break;
			default:
				glc.refrescarGrafo();
				seleccionado.clear();
				if(glc.buscarFaltante(lista.get(seleccionarInsumo.getSelectedIndex()-1))) {
					info.setText("          El Sistema registra faltantes de Stock.");
					seleccionado.add(lista.get(seleccionarInsumo.getSelectedIndex()-1));
					mejorCamino.setEnabled(true);
				}else {
					info.setText(" ");
					mejorCamino.setEnabled(false);
				}
				break;
			}
		});
		constraints.gridx=1;
		constraints.gridy=2;
		mejorCamino.addActionListener(a -> {
			if(!glc.buscarMejorCamino(seleccionado.get(0)))
				JOptionPane.showConfirmDialog(ventana, "El Sistema no registra un Camino que incluya a todas las Plantas.", "Información",
						JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
			mejorCamino.setEnabled(false);
			seleccionado.clear();
		});
		constraints.insets.set(0, 20, 0,0);
		mejorCamino.setEnabled(false);
		volver.addActionListener(a -> GestionLogistica.mostrarMenu());
		constraints.anchor=GridBagConstraints.EAST;

		constraints.insets.set(0, 30, 0,0);
		panelInferior.add(mejorCamino,constraints);
		constraints.gridx=2;
		constraints.gridy=2;
		panelInferior.add(volver,constraints);
		constraints.anchor=GridBagConstraints.NORTHEAST;
		info.setForeground(Color.red);
		constraints.gridx=1;
		constraints.gridy=1;
		constraints.insets=new Insets(0, 0, 5, 0);
		panelInferior.add(info,constraints);
		panelInferior.setPreferredSize(new Dimension(900,50));
		panelSuperior.setPreferredSize(new Dimension(900,30));

		grafoController.setPlantas();
		grafoController.setRutas();
		
		
		accionesInfo.addActionListener(a -> {
			JOptionPane.showConfirmDialog(ventana, "* Reubicar Plantas:   Clic sobre Planta + Arrastrar.\r\n" + 
					"\r\n" + 
					"* Agregar Ruta:   Clic sobre Planta Origen y Destino.\r\n" + 
					"      \r\n" +
					"* Eliminar Ruta:   Doble Clic sobre la Ruta deseada.", "Información"+"\r\n"+" ",
					JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);

	});
	
		constraints.gridx=0;
		constraints.gridy=0;
		constraints.insets=new Insets(0, 0, 0, 700);
		panelSuperior.add(accionesInfo,constraints);
		panel.add(panelSuperior, BorderLayout.NORTH);
		
		panel.add(grafoView, BorderLayout.CENTER);
		panel.add(panelInferior, BorderLayout.SOUTH);
		ventana.setContentPane(panel);
		ventana.pack();
		ventana.setSize(900, 600);

		ventana.setVisible(true);

		if(firstTime && !lista.isEmpty())
			JOptionPane.showConfirmDialog(ventana, "Seleccione un Insumo para verificar si existen faltantes.", "Información", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
		else
			if(lista.isEmpty())
				JOptionPane.showConfirmDialog(ventana, "No se registran Insumos en el Sistema.", "Información",
						JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
	}*/
}

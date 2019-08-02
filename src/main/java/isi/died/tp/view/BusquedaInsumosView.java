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
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import isi.died.tp.controller.GestionEntidadesController;
import isi.died.tp.controller.InsumoController;
import isi.died.tp.model.Camion;
import isi.died.tp.model.Insumo;
import isi.died.tp.model.InsumoLiquido;
import isi.died.tp.model.StockAcopio;
import isi.died.tp.view.table.InsumoBusquedaTableModel;
import isi.died.tp.view.table.MejorSeleccionEnvioTableModel;

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
		JLabel encabezado = new JLabel("Búsqueda de Insumos"), filtrosTitle = new JLabel("Filtros de Búsqueda"),
				filtroNombre = new JLabel("Nombre:"), filtroStock = new JLabel("Cantidad Disponible:"),
						filtroStock1 = new JLabel("Min:"), filtroStock2 = new JLabel("Max:"), 
						filtroCosto = new JLabel("Costo:"),filtroCosto1 = new JLabel("Min:"), filtroCosto2 = new JLabel("Max:"),
						ordenarPor = new JLabel("Ordenar Lista Por:"), ordenarPor2 = new JLabel("Descendente:");
		JCheckBox filtrarPorNombre = new JCheckBox(), filtrarPorCosto = new JCheckBox(), filtrarPorStock = new JCheckBox(),
				ordDesc = new JCheckBox();
		JButton volver = new JButton("Volver"),  buscar = new JButton("Buscar");

		JComboBox<String> ordenamientoSelect = new JComboBox<String>();
		ordenamientoSelect.addItem("NOMBRE");
		ordenamientoSelect.addItem("COSTO");
		ordenamientoSelect.addItem("CANTIDAD TOTAL DISPONIBLE");

		
		constraints.insets=new Insets(5, 90, 90, 5);
		constraints.gridx=0;
		constraints.gridy=0;
		constraints.gridheight=1;
		constraints.gridwidth=8;
		constraints.fill=GridBagConstraints.HORIZONTAL;
		constraints.anchor=GridBagConstraints.CENTER;
		encabezado.setFont(new Font(encabezado.getFont().getName(), encabezado.getFont().getStyle(), 30));
		panel.add(encabezado,constraints);

		constraints.insets=new Insets(0,0, 15, 0);
		constraints.gridx=1;
		constraints.gridy=3;
		constraints.gridwidth=1;
		filtrosTitle.setFont(new Font(filtrosTitle.getFont().getName(), filtrosTitle.getFont().getStyle(), 13));
		panel.add(filtrosTitle,constraints);
		
		
		constraints.insets=new Insets(0,0, 25, 0);
		constraints.gridx=1;
		constraints.gridy=5;
		constraints.gridwidth=1;
		constraints.anchor = GridBagConstraints.WEST;
		panel.add(ordenarPor, constraints);
		
	
		constraints.gridx=2;
		constraints.gridy=5;
		constraints.gridwidth=3;
		constraints.anchor = GridBagConstraints.CENTER;
		panel.add(ordenamientoSelect, constraints);
		
		constraints.insets=new Insets(0,25, 25, 0);
		constraints.gridx=5;
		constraints.gridy=5;
		constraints.gridwidth=1;
		constraints.anchor = GridBagConstraints.CENTER;
		panel.add(ordenarPor2, constraints);
		
		constraints.insets=new Insets(0,5, 25, 0);
		constraints.fill=GridBagConstraints.NONE;
		constraints.anchor=GridBagConstraints.WEST;
		constraints.gridx = 6;
		constraints.gridy = 5;
		panel.add(ordDesc, constraints);
		
		
		constraints.insets=new Insets(0,0, 5, 0);
		constraints.fill=GridBagConstraints.NONE;
		constraints.anchor=GridBagConstraints.WEST;
		constraints.gridx = 0;
		constraints.gridy = 7;
		panel.add(filtrarPorNombre, constraints);
		
		constraints.gridx=1;
		constraints.gridy=7;
		constraints.gridwidth=1;
		constraints.anchor = GridBagConstraints.WEST;
		panel.add(filtroNombre,constraints);
				
		constraints.fill=GridBagConstraints.HORIZONTAL;
		constraints.gridx=2;
		constraints.gridy=7;
		constraints.gridwidth=5;
		constraints.anchor = GridBagConstraints.WEST;
		panel.add(campoNombre, constraints);
		
		
		constraints.fill=GridBagConstraints.NONE;
		constraints.anchor=GridBagConstraints.WEST;
		constraints.gridx = 0;
		constraints.gridy = 9;
		panel.add(filtrarPorStock, constraints);
		
		constraints.gridx=1;
		constraints.gridy=9;
		constraints.gridwidth=1;
		constraints.anchor = GridBagConstraints.WEST;
		panel.add(filtroStock, constraints);
		
		constraints.insets=new Insets(0,25, 5, 15);
		constraints.gridx=2;
		constraints.gridy=9;
		constraints.gridwidth=1;
		constraints.anchor = GridBagConstraints.CENTER;
		panel.add(filtroStock1, constraints);
		
		constraints.insets=new Insets(0,0, 5, 0);
		constraints.gridx=3;
		constraints.gridy=9;
		constraints.gridwidth=1;
		constraints.anchor = GridBagConstraints.CENTER;
		panel.add(campoStockMin, constraints);
		
		constraints.insets=new Insets(0,25, 5, 0);
		constraints.gridx=4;
		constraints.gridy=9;
		constraints.gridwidth=1;
		constraints.anchor = GridBagConstraints.CENTER;
		panel.add(filtroStock2, constraints);
		
		constraints.insets=new Insets(0,0, 5, 0);
		constraints.gridx=5;
		constraints.gridy=9;
		constraints.gridwidth=1;
		constraints.anchor = GridBagConstraints.CENTER;
		panel.add(campoStockMax, constraints);
		
		
		constraints.fill=GridBagConstraints.NONE;
		constraints.anchor=GridBagConstraints.WEST;
		constraints.gridx = 0;
		constraints.gridy = 11;
		panel.add(filtrarPorCosto, constraints);
		
		constraints.gridx=1;
		constraints.gridy=11;
		constraints.gridwidth=1;
		constraints.anchor = GridBagConstraints.WEST;
		panel.add(filtroCosto, constraints);
		
		constraints.insets=new Insets(0,25, 5, 15);
		constraints.gridx=2;
		constraints.gridy=11;
		constraints.gridwidth=1;
		constraints.anchor = GridBagConstraints.CENTER;
		panel.add(filtroCosto1, constraints);
		
		constraints.insets=new Insets(0,0, 5, 0);
		constraints.gridx=3;
		constraints.gridy=11;
		constraints.gridwidth=1;
		constraints.anchor = GridBagConstraints.CENTER;
		panel.add(campoCostoMin, constraints);
		
		constraints.insets=new Insets(0,25, 5, 0);
		constraints.gridx=4;
		constraints.gridy=11;
		constraints.gridwidth=1;
		constraints.anchor = GridBagConstraints.CENTER;
		panel.add(filtroCosto2, constraints);
		
		constraints.insets=new Insets(0,0, 5, 0);
		constraints.gridx=5;
		constraints.gridy=11;
		constraints.gridwidth=1;
		constraints.anchor = GridBagConstraints.CENTER;
		panel.add(campoCostoMax, constraints);
		
		constraints.insets=new Insets(60,55, 5, 0);
		constraints.gridx=1;
		constraints.gridy=22;
		constraints.gridwidth=1;
		constraints.anchor = GridBagConstraints.WEST;
		buscar.addActionListener( a -> 	{	
		


		Integer stockMin = null, stockMax = null;
		Double costoMin = null, costoMax = null;
		String nombre = null, ordPor, orden;
		
		try {
				if(filtrarPorNombre.isSelected())
					if(!campoNombre.getText().isEmpty()) 
						nombre = campoNombre.getText();
				else {
						nombre = null;
				}
				
				if(filtrarPorStock.isSelected()) {
					if(campoStockMin.getText().isEmpty()) 
						stockMin = 0;
					else
						stockMin = Integer.valueOf(campoStockMin.getText());
					
					if(campoStockMax.getText().isEmpty())	
						stockMax = Integer.MAX_VALUE;
					else
						stockMax = Integer.valueOf(campoStockMax.getText());
				
				 	
				}	
				else {
					stockMin = null;
					stockMax = null;
				}
				
				if(filtrarPorCosto.isSelected()) {
					if(campoCostoMin.getText().isEmpty()) 
						costoMin = 0.0;
					else
						costoMin = Double.valueOf(campoCostoMin.getText());
					
					if(campoCostoMax.getText().isEmpty())	
						costoMax = Double.MAX_VALUE;
					else
						costoMax = Double.valueOf(campoCostoMax.getText());
				
				 	
				}
				else {
					costoMin = null;
					costoMax = null;
				}
				
				List <Insumo> listaFiltro = ic.buscarConFiltros(nombre,stockMin, stockMax, costoMin, costoMax);
				List <Insumo> listaOrdenados = new ArrayList<Insumo>();
				System.out.println(listaFiltro.size());
				if(!listaFiltro.isEmpty()) {
					
				ordPor =  ordenamientoSelect.getSelectedItem().toString();
				if(ordDesc.isSelected())
					orden = "DESC";
				else
					orden = "ASC";
				
				switch(ordPor) {
				case "NOMBRE":
					listaOrdenados = ic.ordenarPor("NOMBRE", orden, listaFiltro);
					break;
				case "COSTO":
					listaOrdenados = ic.ordenarPor("COSTO", orden, listaFiltro);
					break;
				case "CANTIDAD TOTAL DISPONIBLE":
					listaOrdenados = ic.ordenarPor("CANTIDAD", orden, listaFiltro);
					break;
				}
				
				mostrarTablaBusqueda(listaOrdenados);
				}
				else
					JOptionPane.showConfirmDialog(null,"No existen Resultados para la Búsqueda solicitada.","Información",
							JOptionPane.DEFAULT_OPTION,
							JOptionPane.INFORMATION_MESSAGE);

			}catch(NumberFormatException nfex) {
				JOptionPane.showConfirmDialog(ventana, "Revise la información ingresada.", "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);	
			}
		}
		);
		panel.add(buscar, constraints);
		
		constraints.insets=new Insets(60, 20, 5, 0);
		constraints.gridx=4;
		constraints.gridwidth=1;
		constraints.anchor = GridBagConstraints.EAST;
		volver.addActionListener( a -> GestionEntidades.mostrarMenu());
		panel.add(volver,constraints);
		
		ventana.setContentPane(panel);
		ventana.pack();
		ventana.setSize(800, 600);
		
        ventana.setVisible(true);
	}
	
	
	public static void mostrarTablaBusqueda(List<Insumo> insumos) {

			
					GridBagConstraints constraints = new GridBagConstraints();
					JLabel encabezado = new JLabel("Resultados de Búsqueda"), infoExtra = new JLabel();
					JFrame popup = new JFrame("Información");
					JPanel panel = new JPanel(new GridBagLayout());
					JTable table;
					popup.setDefaultCloseOperation(WindowConstants. DISPOSE_ON_CLOSE);
					panel.setPreferredSize( new Dimension(850,650));
					table = new InsumoBusquedaTableModel();
					((InsumoBusquedaTableModel) table).agregarDatos(insumos);
					table.setFillsViewportHeight(true);
					JScrollPane scroll = new JScrollPane(table,
							JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
							JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
					scroll.setPreferredSize(new Dimension(650,400));




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
					panel.add(infoExtra,constraints);


					popup.setContentPane(panel);
					popup.pack();
					popup.setLocationRelativeTo(ventana);
					popup.setVisible(true);


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

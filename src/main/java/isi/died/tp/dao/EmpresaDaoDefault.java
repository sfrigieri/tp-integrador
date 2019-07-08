package isi.died.tp.dao;


import isi.died.tp.estructuras.*;
import isi.died.tp.model.*;
import isi.died.tp.dao.util.*;

public class EmpresaDaoDefault implements EmpresaDao {


	private static Grafo<Planta> GRAFO_MATERIAL  = new Grafo<Planta>();
	private static Integer SECUENCIA_ID;
	
	private CsvSource dataSource;
	
}

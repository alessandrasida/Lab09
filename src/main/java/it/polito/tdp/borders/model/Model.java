package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {

	Map<Integer, Country > idMap;
	BordersDAO dao;
	List<Border> confini;
	SimpleGraph<Country, DefaultEdge> grafo;
	
	public Model() {
		this.idMap = new HashMap<Integer, Country>();
		this.dao = new BordersDAO();
		this.dao.loadAllCountries(idMap);
		this.grafo = new SimpleGraph<>(DefaultEdge.class);
		
	
	}

	
	public void creaGrafo(int anno) {
		this.confini = this.dao.getCountryPairs(anno, idMap);
		
		for( Border c : confini) {
			Country c1 = c.getC1();
			Country c2 = c.getC2();
			this.grafo.addVertex(c1);
			this.grafo.addVertex(c2);
			if( ! this.grafo.containsEdge(c1, c2)) {
				this.grafo.addEdge(c1, c2);
			}
		}
		
		
	}
	
	public int numConnessi( Country c) {
		return this.grafo.edgesOf(c).size();
	}
	
	public List<Country> getCountries(){
		List<Country> copia = new LinkedList<>(this.grafo.vertexSet());
		return copia;
	}
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int nArchi() {
		return this.grafo.edgeSet().size();
	}


	public Object getNumberOfConnectedComponents() {
		
		ConnectivityInspector<Country, DefaultEdge> ci = new ConnectivityInspector<Country, DefaultEdge>(this.grafo);
		return ci.connectedSets().size();

	}
	
	public List<Country> getConnessi(Country c){
		
				
		ConnectivityInspector<Country, DefaultEdge> cc = new ConnectivityInspector<Country, DefaultEdge>(this.grafo);
		List<Country> risultato = new ArrayList<>(cc.connectedSetOf(c));

		
		return risultato;
	}
}

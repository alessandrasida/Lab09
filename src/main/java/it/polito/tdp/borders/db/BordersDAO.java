package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;

public class BordersDAO {

	public void loadAllCountries(Map<Integer, Country> idMap) {

		String sql = "SELECT  StateAbb, ccode, StateNme FROM country ORDER BY StateAbb";

		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				 Country c = new Country( rs.getString("StateAbb"), rs.getInt("ccode"), rs.getString("StateNme"));
				 idMap.put(c.getCCode(), c);
			}
			
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Border> getCountryPairs(int anno, Map<Integer, Country> idMap) {
		
		List<Border> risultato = new ArrayList<>();
		
		String sql = "SELECT state1no , state2no, year "
				+ "FROM contiguity "
				+ "WHERE conttype = 1 AND state1no < state2no "
				+ "AND YEAR <= ? ";
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			
			ResultSet rs = st.executeQuery();
			
			while( rs.next()) {
				Country c1 = idMap.get(rs.getInt("state1no"));
				Country c2 = idMap.get(rs.getInt("state2no"));
				risultato.add(new Border( c1, c2, rs.getInt("year")));
			}
			
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database di GetCountryPairs");
			throw new RuntimeException("Error Connection Database");
		}
		
		return risultato;
	}
	
	
	
}


package it.polito.tdp.borders;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
	 @FXML
	 private ComboBox<Country> cmbBox;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtAnno"
    private TextField txtAnno; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaConfini(ActionEvent event) {
    	this.txtResult.clear();
    	int anno = 0;
    	try {
    		anno = Integer.parseInt(this.txtAnno.getText());
    		if( anno < 1816 || anno > 2006) {
    			this.txtResult.setText("Inserire un anno compreso tra 1816-2006.");
    			return;
    		}
    	}catch( NumberFormatException e) {
    		this.txtResult.setText("Inserire un valore valido.");
    	}
    	model.creaGrafo(anno);
    	this.txtResult.setText("Number of connected components: " + model.getNumberOfConnectedComponents() + "\n\n");
    	List<Country> stati = model.getCountries();
    	for( Country c : stati) {
    		if( model.numConnessi(c) == 1)
    			this.txtResult.appendText(c.getStateNme() +" has " + model.numConnessi(c) + " border. \n");
    		else
    			this.txtResult.appendText(c.getStateNme() +" has " + model.numConnessi(c) + " borders. \n");
    	}
    	
    	// implementato comparatore anonico per mettere in ordine la lista
    	List<Country> copia = new ArrayList<Country>(model.getCountries());
    	copia.sort(new Comparator<Country>() {
    		public int compare( Country c1, Country c2) {
    			return c1.getStateNme().compareTo(c2.getStateNme());
    		}	});
    	for(Country c: copia) {
    		this.cmbBox.getItems().add(c);
    	}
    	
    }
    
    @FXML
    void CercaStati(ActionEvent event) {
    	
    	System.out.println( model.getConnessi(this.cmbBox.getValue()));
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        
    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}

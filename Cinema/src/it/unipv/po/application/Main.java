package it.unipv.po.application;


import it.unipv.po.controllers.AddSalaControll;
import it.unipv.po.model.cinema.Cinema;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	/* 
	 * Contiene il primo elemento stage, che a sua volta contiene il scene e tutti gli elemente
	 * grafici secondo una scala gerarchica.
	 * Le finestre grafiche scritte in XML sono presenti in application.view
	 */
	private Stage primaryStage;
	
	/* Le seguenti variabili sono dichiarate public and static poiché
	 * vengono usate in modo statico dal RootController per cambiare la view
	 * principale al cliccare dei pulsanti.
	 * All'avvio si ha la GestioneView
	 */
	public static BorderPane rootView;
	public static AnchorPane filmView;
	public static AnchorPane prenotazioniView;
	public static SplitPane saleView;
	public static AnchorPane addSala;
    public static Stage addSalaStage;
	
	
	//elementi del modello
	public static final Cinema myCinema = new Cinema("Cinema Prova", "Indirizzo prova");
	
	
	@Override
	public void start(Stage primaryStage) {
		
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Cinema Application");
		
		rootInit();
		viewInit();
		
		Scene scene = new Scene(rootView, 600, 400);
		primaryStage.setScene(scene);
		primaryStage.show();
			
	}
	
	//Inizializzo lo stage tramite l'elemnto di root
	
	private void rootInit() {
		
		try {
			  // Create a new root lauyot
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/RootView.fxml"));
			this.rootView = (BorderPane) loader.load();
          
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	//Inizializzo i miei pannelli grafici
	private void viewInit() {
		
		try {
			FXMLLoader loader = new FXMLLoader();
			
			loader.setLocation(Main.class.getResource("view/SaleView.fxml"));
			this.saleView = (SplitPane) loader.load();
			
			loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/FilmView.fxml"));
			this.filmView = (AnchorPane) loader.load();

			
			loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/PrenotazioneView.fxml"));
			this.prenotazioniView = (AnchorPane) loader.load();
			
			loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/AddSala.fxml"));
			this.addSala = (AnchorPane) loader.load();
			
			Scene addSalaScene = new Scene(addSala, 250, 350);
			addSalaStage = new Stage();
			addSalaStage.setScene(addSalaScene);
			addSalaStage.setResizable(false);
			
		}
			
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		launch(args);
	}
}

package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.CalcModel;
import view.MainController;



public class CalculatorApp extends Application
{

	@Override
	public void start(Stage stage) throws Exception
	{
		
		FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(MainController.class.getResource("main.fxml"));
	    BorderPane view = loader.load();
	    MainController cont = loader.getController();
	    CalcModel model =new CalcModel(); 
	    cont.setModel(model);
	   
	    
	    Scene s = new Scene(view);
	    stage.setScene(s);
	    stage.show();
		
	}
	
	public static void main(String []args)
	{
		launch(args);
	}

}

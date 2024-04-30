import java.io.IOException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.CalcModel;
import model.Operation;
import view.MainController;

@ExtendWith(ApplicationExtension.class)
public class TestMainCalc
{

	@Start
	private void start(Stage stage) {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(CalcModel.class.getResource("../view/main.fxml"));
		try {
			Pane view = loader.load();
			MainController cont = loader.getController();
			cont.setModel(new CalcModel());
			Scene s = new Scene(view);
			stage.setScene(s);
			stage.show();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void enterNumbers(FxRobot robot, String text, String target) {
		robot.clickOn(target);
		robot.write(text);
	}
	
	private void addition(FxRobot robot, String num1, String num2) {
		enterNumbers(robot, num1, "#firstNumber");
		enterNumbers(robot, num2, "#secondNumber");
		robot.clickOn("#additionOperation");
	}
	private void subtraction(FxRobot robot, String firstNum, String secondNum) {
		enterNumbers(robot, firstNum, "#firstNumber");
		enterNumbers(robot, secondNum, "#secondNumber");
		robot.clickOn("#subtractionOperation");
	}
	private void multiplication(FxRobot robot, String firstNum, String secondNum) {
		enterNumbers(robot, firstNum, "#firstNumber");
		enterNumbers(robot, secondNum, "#secondNumber");
		robot.clickOn("#multiplicationOperation");
	}
	private void division(FxRobot robot, String firstNum, String secondNum) {
		enterNumbers(robot, firstNum, "#firstNumber");
		enterNumbers(robot, secondNum, "#secondNumber");
		robot.clickOn("#divisionOperation");
	}
	
//	private void checkAnswer(FxRobot robot, String ans) {
//		Assertions.assertThat(robot.lookup("#displayAnswerLabel").queryAs(Label.class)).hasText(ans);
////		System.out.print(ans);
//	}
//	
	private void checkAnswer(FxRobot robot, String expectedAnswer) {
	    Label displayLabel = robot.lookup("#displayAnswerLabel").queryAs(Label.class);
	    String actualAnswer = displayLabel.getText();
	    Assertions.assertThat(actualAnswer).isEqualTo(expectedAnswer);
	}
	private void confirmEquationAddition(FxRobot robot, String num1, String num2, String ans) {
		addition(robot, num1, num2);
		checkAnswer(robot, ans);
		
	}
	
	private void confirmEquationSubtraction(FxRobot robot, String num1, String num2, String ans) {
		subtraction(robot, num1, num2);
		checkAnswer(robot, ans);
		
	}
	
	private void confirmEquationMultiplication(FxRobot robot, String num1, String num2, String ans) {
		multiplication(robot, num1, num2);
		checkAnswer(robot, ans);
		
	}
	
	private void confirmEquationDivision(FxRobot robot, String num1, String num2, String ans) {
		division(robot, num1, num2);
		checkAnswer(robot, ans);
		
	}

	@SuppressWarnings("unchecked")
	ListView <Operation> getOperation(FxRobot robot){
		return (ListView<Operation>) robot.lookup("#listView").queryAll().iterator().next();
		
	}
	
	@Test
	public void testAnswer(FxRobot robot) {
		
		try
		{
			Thread.sleep(1000);
			confirmEquationAddition(robot, "4", "2", "6");
			confirmEquationSubtraction(robot, "8", "8", "0");
			confirmEquationMultiplication(robot, "7", "2", "14");
			confirmEquationDivision(robot, "32", "2", "16");
	        Thread.sleep(1000); 

	        // Retrieve ListView
	        ListView<Operation> listView = getOperation(robot);

	        ObservableList<Operation> items = listView.getItems();

	        Assertions.assertThat(items).isNotNull();
	        
		Operation [] opt = {
		new Operation(4, " + ", 2, 6),
		new Operation(8, " - ", 8, 0),
		new Operation(7, " * ", 2, 14),
		new Operation(32, " / ", 2, 16),
};

	        for (Operation expectedOperation : opt) {
	            boolean found = false;
	            for (Operation actualOperation : items) {
//	            	System.out.print("Expected " + expectedOperation + " Actual " + actualOperation);
	                if (expectedOperation.getNum1() == actualOperation.getNum1() &&
	                        expectedOperation.getNum2() == actualOperation.getNum2() &&
	                        expectedOperation.getOp().equals(actualOperation.getOp()) &&
	                        expectedOperation.getResult() == actualOperation.getResult()) {
	                    found = true;
	                    break;
	                }
	            }
	            // Assert that the expected operation is found in the ListView
	            Assertions.assertThat(found).isTrue();
	        }
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }
	}
	

}

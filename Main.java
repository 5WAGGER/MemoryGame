//make a level 10:
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.event.EventHandler;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.layout.GridPane;


import java.util.Scanner;
import java.lang.Math;

import java.util.Random;
import java.util.*;  
import java.io.*;
import java.lang.Thread;

class Player{
  int score;
  int lives;
  boolean isStillPlaying;
  boolean won;

  public void scored() {
  score++;
  }

  public void gotWrong() {
  lives--;
  }

  public void checkIfCorrect(int numAns) {

    
  if (lives ==0){isStillPlaying=false;
                won = false;
              System.out.println("You lost"); 
              }
  if(score ==numAns){isStillPlaying= false;
                    won = true;
                    System.out.println("You won");
                    }
    
    
  }
  
}


public class Main extends Application 
{ 
  public Stage primary;
  public Scene scene;
  public GridPane gridPane;
  public Button [] buttons;
  public int playerScore;
  public int playerLives;
  public boolean playerWon = false;
  public boolean gameIsNotOver = false;
  public int level = 1;
  
  
    
  private static boolean check(int[] arr, int toCheckValue)
  {
      // check if the specified element
      // is present in the array or not
      // using Linear Search method
      boolean test = false;
      for (int element : arr) {
          if (element == toCheckValue) {
              test = true;
              break;
          }
      }

      // Print the result
    if (test){return true;}
    else {return false;}
  }//end fuction
  
  public static int[] loadRandomNumberArray(int[] currentInfo) {
	  int answerSize = currentInfo[0];
	  int sizeOfArray = currentInfo[1];
	  Random rand = new Random();
    sizeOfArray = sizeOfArray*sizeOfArray;
	     int answers[] = new int[answerSize]; 
	      for (int z = 0; z < answerSize; z++) {
	      int num = rand.nextInt(sizeOfArray); 
	      while (check(answers, num)) {
	        //checking and changing if there is a     duplicate 
	        num = rand.nextInt(sizeOfArray); 
	      }//end while statement
	        answers[z] = num;
	      }//end for loop
	  return answers;
	}//end fuction
  
  public static int[] generateLevel(int level){
	   
	  
	  int grid = 3;
	  int ans = level + 2;
	    boolean normal;
	    if (level == 1 || level == 2 ){
	      
	      normal = false;
	    }//end if
	    else {
	      normal = true;
	    }//end else

	    if (normal){
	    int num = level / 3;

	      
	      for (int x = 1; x < level; x++) {
	      if (num == x){
	        
	        grid = 3 + num;
	      }//end if
	      }//end for
	    }//end if
	    
	    int buttonSize = 360 / grid;
	  int[] ansThenGrid = new int[]{ans,grid,buttonSize};
	    
	    return ansThenGrid;
	    
	  }//end fuction
  
  public void loadDisplay(boolean notAllWhite, int[] listOfAnswers, int[] currentInfo){
  
    int ansSize = currentInfo[0];
  int gridSize = currentInfo[1];
  int buttonSize = currentInfo[2];
    
  int numOfButtons = gridSize * gridSize;
    buttons = new Button[numOfButtons];
    for(int i=0; i<numOfButtons;++i){
      buttons[i] = new Button("Button #"+(i+1));
      buttons[i].setPrefSize(buttonSize,buttonSize);
      Button current = buttons[i];
      
      if (check(listOfAnswers, i)){

        current.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent e) {        
     current.setStyle("-fx-base: light green");

        playerScore++;
        System.out.println("playerScore = "+playerScore);

        
        System.out.println("ansSize = "+ansSize);


        if (playerScore == ansSize){
          playerWon = true;
          System.out.println("you won!");
          
        }

        if (playerWon){
      
       level++; 
        playerLives = 0;
        playerScore = 0;
        playerWon = false;
        
    gridPane = new GridPane();
    scene = new Scene(gridPane, 360, 360);
    primary.setTitle("Level "+level);
  loadAll(scene,primary,level);
  
        
      }
      }
    });
        
  
        
      }//end if

      else{
      current.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent e) {        
     current.setStyle("-fx-base: Black");
        playerLives--;
        System.out.print("playerLives = "+ playerLives);

        if (playerLives==-3){
          gameIsNotOver = false;
          System.out.println("Game is over!");
          System.exit(0);
        }//end if
      }
    });
      
        
      }//end else
    }//end for

    int buttonIndex = 0;
    for (int rowIndex=0;rowIndex < gridSize; ++rowIndex) {
      for (int colIndex = 0; colIndex < gridSize;++colIndex) {
        
    	  

        if (check(listOfAnswers, buttonIndex)&&notAllWhite){
          //change color of current button to green
        	buttons[buttonIndex].setStyle("-fx-base: lightgreen");
        }

        else{
        	buttons[buttonIndex].setStyle(null);
        }
        
        gridPane.add(buttons[buttonIndex], colIndex, rowIndex);

        

        
        ++buttonIndex;
        
      }//end col for loop
    }//end row for loop

    
  }//end method loadDisplay

  public void closeALL(Scene scene, Stage arg0, int level){
    arg0.close();
  }

  
  public void loadAll(Scene scene,Stage arg0,int level){
    int[] ansThenSizeThenButtonSize = generateLevel(level);
int ansSize = ansThenSizeThenButtonSize[0];
int gridSize = ansThenSizeThenButtonSize[1];
    
  int[] currentInfo = new int[]{ansSize,gridSize};
  int[] currentAnswers = loadRandomNumberArray(currentInfo);
    
    loadDisplay(true, currentAnswers, ansThenSizeThenButtonSize);
    closeALL(scene,primary,level);
    arg0.setScene(scene);
    arg0.show();
    PauseTransition pause = new PauseTransition(
    	    Duration.seconds(2)
    	);
    	pause.setOnFinished(event -> {
    	    closeALL(scene,primary,level);
    loadDisplay(false, currentAnswers, ansThenSizeThenButtonSize);
    	    arg0.show();
        if(playerWon){
          closeALL(scene,primary,level);
        }//end if
    	});
    	pause.play();   
    
  }

  public static void main(String[] args) {
      launch(args);
  }
  
  public void start(Stage arg0) throws Exception{
	  

    primary = arg0;
    gridPane = new GridPane();
    scene = new Scene(gridPane, 360, 360);
    
  loadAll(scene,primary,level);

    primary.setTitle("Level "+level);


    //while (game){
      //if (playerWon){
       //level++; 
        //playerLives = 0;
        //playerScore = 0; loadAll(user,scene,primary,level);
        //playerWon = false;
      //}
      
    //}

    
    
     
    
  }//end startarg
}//Main

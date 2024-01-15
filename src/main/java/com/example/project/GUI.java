package com.example.project;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

import java.io.IOException;

public class GUI extends Application implements Constants{

    GridPane gridPane = new GridPane();
    Rectangle [][] squares = new Rectangle[gridSize][gridSize];
    Game game = new Game();

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Populate the GridPane with squares
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                Rectangle square = getRectangle(row, col);
                gridPane.add(square, col, row);
                squares[row][col] = square;
            }
        }

        Scene scene = new Scene(gridPane, 800, 800);
        scene.setFill(Color.LIGHTCYAN);
        primaryStage.setTitle("Game of Life");
        primaryStage.setScene(scene);
        primaryStage.show();


        scene.addEventHandler(javafx.scene.input.KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.R) {
                game.random();
                game.updateUI(squares);
            }
        });

        // Add the second key pressed event listener
        scene.addEventHandler(javafx.scene.input.KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.C) {
                game.clear();
                game.updateUI(squares);
            }
        });

        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                // Call your custom method or perform the desired action
                disableSquaresListeners();
                game.play(squares);
            }});

    }


    private Rectangle getRectangle(int row, int col) {
        Rectangle square = new Rectangle(800.0/gridSize, 800.0/gridSize);
        square.setFill(Color.DARKCYAN);
        square.setOnMouseClicked(event -> {
            game.setAlive(col, row);
            square.setFill(Color.YELLOWGREEN);
        });
        return square;
    }

    public void disableSquaresListeners()
    {
        for(Rectangle[] row: squares)
        {
            for(Rectangle square: row)
            {
                square.setOnMouseClicked(null);
            }
        }
    }


    public static void main(String[] args)
    {
        launch();
    }
}
package com.example.project;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Random;

public class Game implements Constants{

    private boolean[][] cells;
    private boolean[][] next;

    public Game()
    {
        cells = new boolean[gridSize][gridSize];
        next = new boolean[gridSize][gridSize];
    }

    void play(Rectangle [][] squares) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(timeStamp), event -> {
                    predict();

                    step();

                    Platform.runLater(() -> {
                        updateUI(squares);
                    });
                })
        );
        timeline.setCycleCount(numberOfIterations);
        timeline.play();
    }

    public void updateUI(Rectangle [][] squares) {
        for(int i = 0; i < gridSize; i++)
        {
            for(int j = 0; j < gridSize; j++)
            {
                if(cells[i][j] == true)
                    squares[i][j].setFill(Color.YELLOWGREEN);
                else squares[i][j].setFill(Color.DARKCYAN);
            }
        }
    }

    public void predict() {
        for (int y = 0; y < gridSize; y++)
        {
            for (int x = 0; x < gridSize; x++)
            {
                int neighbours = 0;

                for (int j = y - range; j <= y + range; j++)
                {
                    if (j < 0 || j > gridSize - 1) continue;

                    for (int i = x - range; i <= x + range; i++)
                    {
                        if (i < 0 || i > gridSize - 1 || (i==x && j==y)) continue;
                        if (cells[i][j] == true ) neighbours++;
                    }
                }

                if (cells[x][y] && neighbours >= surviveDownLimit && neighbours <= surviveUpLimit) next[x][y] = true;
                else if (neighbours >= bornDownLimit && neighbours <= bornUpLimit) next[x][y] = true;
                else next[x][y] = false;

            }
        }
    }

    public void step()
    {
        for(int j = 0; j < gridSize; j++)
        {
            for(int i = 0; i < gridSize; i++)
            {
                cells[i][j] = next[i][j];
            }
        }
    }

    public void setAlive(int x, int y)
    {
        cells[x][y] = true;
    }

    public void clear()
    {
        cells = new boolean[gridSize][gridSize];
        next = new boolean[gridSize][gridSize];
    }

    public void random()
    {
        Random random = new Random();
        for(int i = 0; i< gridSize; i++)
            for(int j = 0; j< gridSize; j++)
            {
                cells[i][j] = random.nextBoolean();
                next = new boolean[gridSize][gridSize];
            }
    }


}

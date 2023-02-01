package com.company;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Snake {
    private ArrayList<Integer> xPos = new ArrayList<Integer>();
    private ArrayList<Integer> yPos = new ArrayList<Integer>();
    private int dim;

    private final int startPositionX = 12;
    private final int startPositionY = 15;
    private final int scale = 10;

    private Image body;
    private Image head;

    // 1 - left
    // 2 - right
    // 3 - up
    // 4 - down
    private int direction = 2;

    public Snake(){
        this.dim = 5;
        initSnake();
        loadImages();
    }

    private void loadImages() {
        ImageIcon img = new ImageIcon("./src/img/dot.png");
        body = img.getImage();

        img = new ImageIcon("./src/img/head.png");
        head = img.getImage();
    }

    private void initSnake(){
        for (int i = 0; i < this.dim; i++){
            xPos.add(startPositionX + i);
            yPos.add(startPositionY);
        }
    }

    public ArrayList<Integer> getxPos() {
        return xPos;
    }

    public ArrayList<Integer> getyPos() {
        return yPos;
    }

    public int getLength(){
        return xPos.size();
    }

    public int getHeadX(){
        return this.xPos.get(xPos.size() - 1);
    }

    public int getHeadY(){
        return this.yPos.get(yPos.size() - 1);
    }

    public void drawSnake(Graphics g){
        for (int i = 0; i < xPos.size(); i++){
            if (i == xPos.size() - 1){
                // head
                g.drawImage(head, xPos.get(i) * scale, yPos.get(i) * scale, null);
            }
            else {
                // body
                g.drawImage(body, xPos.get(i) * scale, yPos.get(i) * scale, null);
            }
        }
    }

    public int getDirection(){
        return this.direction;
    }

    public void changeDirection(int direction){
        this.direction = direction;
    }

    public boolean collisionCheck(){
        if (    this.xPos.get(xPos.size() - 1) == 0 ||
                this.xPos.get(xPos.size() - 1) == 30 ||
                this.yPos.get(yPos.size() - 1) == 0 ||
                this.yPos.get(yPos.size() - 1) == 30){
            return false;
        }

        for (int i = 0; i < xPos.size() - 1; i++){
            if (xPos.get(i).equals(xPos.get(xPos.size() - 1)) &&
                    yPos.get(i).equals(yPos.get(yPos.size() - 1))){
                return false;
            }
        }

        return true;
    }

    public void move(boolean eatenApple){
        int newPosX = 0, newPosY = 0;
        switch (this.direction) {
            case 1 -> {
                newPosX = xPos.get(xPos.size() - 1) - 1;
                newPosY = yPos.get(yPos.size() - 1);
            }
            case 2 -> {
                newPosX = xPos.get(xPos.size() - 1) + 1;
                newPosY = yPos.get(yPos.size() - 1);
            }
            case 3 -> {
                newPosX = xPos.get(xPos.size() - 1);
                newPosY = yPos.get(yPos.size() - 1) - 1;
            }
            case 4 -> {
                newPosX = xPos.get(xPos.size() - 1);
                newPosY = yPos.get(yPos.size() - 1) + 1;
            }
        }
        this.xPos.add(newPosX);
        this.yPos.add(newPosY);

        if (!eatenApple){
            removePieceOfSnakeFromMove();
        }
    }

    private void removePieceOfSnakeFromMove(){
        for (int i = 0; i < xPos.size() - 1; i++){
            xPos.set(i, xPos.get(i + 1));
            yPos.set(i, yPos.get(i + 1));
        }
        xPos.remove(xPos.size() - 1);
        yPos.remove(yPos.size() - 1);
    }
}

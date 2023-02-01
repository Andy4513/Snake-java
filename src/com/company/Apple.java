package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Apple {

    private int x;
    private int y;

    private Image appleImg;

    private final int scale = 10;

    public Apple(){
        loadImages();
    }

    public void newAppleLocation(ArrayList<Integer> x, ArrayList<Integer> y){
        int[][] freeSpace = new int[30][30];
        final int numberOfRandomTries = 25;
        final int min = 0;
        final int max = 30;
        boolean ok = false;
        for (int i = 0; i < x.size(); i++){
            freeSpace[x.get(i)][y.get(i)] = 1;
        }
        // try a random position an amount of times
        for (int i = 0; i < numberOfRandomTries; i++){
            int randomX = ThreadLocalRandom.current().nextInt(min, max);
            int randomY = ThreadLocalRandom.current().nextInt(min, max);
            if (freeSpace[randomX][randomY] == 0){
                this.x = randomX;
                this.y = randomY;
                ok = true;
                break;
            }
        }
        // try a linear search for a free space in case a random free space was not found
        if (!ok){
            for (int i = 0; i < 30; i++){
                for (int j = 0; j < 30; j++){
                    if (freeSpace[i][j] == 0){
                        this.x = i;
                        this.y = j;
                        return;
                    }
                }
            }
        }
    }

    private void loadImages(){
        ImageIcon img = new ImageIcon("./src/img/apple.png");
        this.appleImg = img.getImage();
    }

    public void drawApple(Graphics g){
        g.drawImage(appleImg, this.x * scale, this.y * scale, null);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean eaten(int direction, int headX, int headY){
        switch (direction) {
            case 1 -> headX -= 1;
            case 2 -> headX += 1;
            case 3 -> headY -= 1;
            case 4 -> headY += 1;
        }

        return headX == this.x && headY == this.y;
    }
}

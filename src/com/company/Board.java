package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel implements ActionListener {

    private final int width = 300;
    private final int height = 300;
    private int delay = 150;

    private boolean gameOn = true;

    private Timer timer;

    Snake snake;
    Apple apple;

    public Board(){
        initBoard();
    }

    private void initBoard(){
        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);
        setFocusable(true);

        setPreferredSize(new Dimension(width, height));

        this.snake = new Snake();
        this.apple = new Apple();
        this.apple.newAppleLocation(this.snake.getxPos(), this.snake.getyPos());

        this.timer = new Timer(delay, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        draw(g);
    }

    private void draw(Graphics g) {
        if (gameOn) {
            drawGame(g);
        }
        else {
            drawEndScreen(g);
        }
        Toolkit.getDefaultToolkit().sync();
    }

    private void drawGame(Graphics g) {
        // draw snake

        this.snake.drawSnake(g);

        // draw apple

        this.apple.drawApple(g);

    }

    private void drawEndScreen(Graphics g) {
        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (width - metr.stringWidth(msg)) / 2, height / 2);

        int score = snake.getLength() - 5;
        msg = "Your score was: " + score;

        g.drawString(msg, (width - metr.stringWidth(msg)) / 2, height / 2 + 50);

    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameOn) {
            gameOn = this.snake.collisionCheck();
            boolean eaten =  this.apple.eaten(this.snake.getDirection(), this.snake.getHeadX(), this.snake.getHeadY());
            this.snake.move(eaten);
            if (eaten){
                this.apple.newAppleLocation(this.snake.getxPos(), this.snake.getyPos());
            }
        }
        repaint();
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            switch (key){
                case KeyEvent.VK_LEFT:
                    if (snake.getDirection() != 2) {
                        snake.changeDirection(1);
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (snake.getDirection() != 1){
                        snake.changeDirection(2);
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (snake.getDirection() != 4){
                        snake.changeDirection(3);
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (snake.getDirection() != 3){
                        snake.changeDirection(4);
                    }
                    break;
                case KeyEvent.VK_ESCAPE:
                    System.exit(0);
                    break;
                case KeyEvent.VK_SPACE:
                    timer.stop();
                    gameOn = true;
                    initBoard();
                    break;
                case KeyEvent.VK_ADD:
                    if (delay > 50) {
                        delay -= 25;
                        timer.setDelay(delay);
                    }
                    break;
                case KeyEvent.VK_SUBTRACT:
                    if (delay < 300) {
                        delay += 25;
                        timer.setDelay(delay);
                    }
                    break;
            }
    }
}
}


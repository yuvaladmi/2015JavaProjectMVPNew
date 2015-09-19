package view;

import java.util.Observer;

import algorithms.mazeGenerators.Maze3d;

public interface View {
    public void start();

    public void displayByte(byte[] arr);

    public void displayString(String arr);

    public void displayInt(int[][] arr);

    public void addObservers(Observer o);

}

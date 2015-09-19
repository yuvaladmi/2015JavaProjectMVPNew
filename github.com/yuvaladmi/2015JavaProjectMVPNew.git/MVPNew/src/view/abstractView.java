package view;

import java.util.Observable;
import java.util.Observer;


public abstract class abstractView extends Observable implements View {
    public abstract void displayByte(byte[] arr);
    public abstract void displayString(String arr);
    public abstract void displayInt(int[][] arr);
    public abstract void start();
    @Override
    public void addObservers(Observer o){
	addObserver(o);
    }

}

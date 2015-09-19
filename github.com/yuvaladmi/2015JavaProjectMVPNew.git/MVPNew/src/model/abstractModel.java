package model;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public abstract class abstractModel extends Observable implements Model {

    public StringBuilder sb;
    public ExecutorService threadpool;
    int numOfThread;
    public HashMap<String, Maze3d> hMaze;
    public HashMap<String, Solution<Position>> hSol;

    public abstract void dirToPath(String[] arr);

    public abstract void generateMaze(String[] arr);

    public abstract byte[] sendGame(String arr);

    public abstract void crossSection(String[] arr);

    public abstract void save(String[] arr);

    public abstract void load(String[] arr);

    public abstract void solve(String[] arr);

    public abstract String bringSolution(String arr);

    public abstract void gameSize(String[] arr);

    public abstract void fileSize(String[] arr);

    public abstract void close();
    
    public abstract void addObservers(Observer o);


}

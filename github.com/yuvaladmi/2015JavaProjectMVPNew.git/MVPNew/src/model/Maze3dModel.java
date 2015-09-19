package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Stack;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.MyMaze3dGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.AStar;
import algorithms.search.BFS;
import algorithms.search.CommonSearcher;
import algorithms.search.MazeManhattenDistance;
import algorithms.search.SearchableMaze;
import algorithms.search.Solution;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;
import presenter.Presenter;

public class Maze3dModel extends abstractModel {
    // Maze3d myMaze;
    // HashMap <Maze3d, Solution<Position>> hMazeSol;

    public Maze3dModel() {
	hMaze = new HashMap<String, Maze3d>();
	hSol = new HashMap<String, Solution<Position>>();
	sb = new StringBuilder();
	numOfThread = 10;
	threadpool = Executors.newFixedThreadPool(numOfThread);
    }

    public void addObservers(Observer o) {
	addObserver(o);
    }

    /**
     * This method create a new Maze3d in a thread. All the mazes saved in an
     * HashMap.
     */
    @Override
    public void generateMaze(String[] arr) {
	threadpool.submit(new Callable<Maze3d>() {

	    @Override
	    public Maze3d call() throws Exception {
		sb = new StringBuilder();
		int i = 1;
		if (arr[i].equals("3d") || arr[i].equals("3D")) {
		    i++;
		    ;
		    if (arr[i].equals("maze")) {
			i++;
		    }
		}
		while (i < arr.length) {
		    sb.append(arr[i]);
		    i++;
		}
		String name = sb.substring(0, sb.length() - 3);
		int x = (int) sb.charAt(sb.length() - 3) - 48;
		int y = (int) sb.charAt(sb.length() - 2) - 48;
		int z = (int) sb.charAt(sb.length() - 1) - 48;
		MyMaze3dGenerator mg = new MyMaze3dGenerator(x, y, z);
		Maze3d m = mg.generate(x, y, z);
		hMaze.put(name, m);
		String[] messege = ("maze is ready:" + name).split(":");
		// notifyObservers(messege);
		return m;
	    }

	});
    }

    /**
     * This method gets a Maze name and sends the Controller this maze.
     */
    @Override
    public byte[] sendGame(String arr) {
	byte[] byteArr;
	try {
	    Maze3d temp = hMaze.get(arr);
	    byteArr = temp.toByteArray();
	    return byteArr;
	} catch (IOException e) {
	    // notifyObservers(("Error uccord, please try again").split(" "));
	}
	return null;
    }

    /**
     * This method gets a name of a maze and sends the Controller a CrossSection
     * of this maze
     */
    @Override
    public void crossSection(String[] arr) {
	sb = new StringBuilder();
	int i = arr.length - 2;
	if (arr[i].equals("for")) {
	    for (int j = i + 1; j < arr.length; j++) {
		sb.append(arr[j]);
	    }
	}
	String name = sb.toString();
	int index = (arr[--i].charAt(0)) - 48;
	char crossBy = arr[--i].charAt(0);
	Maze3d maze = hMaze.get(name);
	int[][] myMaze = null;
	switch (crossBy) {
	case 'x':
	    myMaze = maze.getCrossSectionByX(index);
	    break;
	case 'X':
	    myMaze = maze.getCrossSectionByX(index);
	    break;
	case 'y':
	    myMaze = maze.getCrossSectionByY(index);
	    break;
	case 'Y':
	    myMaze = maze.getCrossSectionByY(index);
	    break;
	case 'z':
	    myMaze = maze.getCrossSectionByZ(index);
	    break;
	case 'Z':
	    myMaze = maze.getCrossSectionByZ(index);
	    break;
	default:
	    notifyObservers("Error, pkease try again");
	    break;
	}
	notifyObservers(myMaze);

    }

    /**
     * This methods gets a name of a file and a maze and save this maze in the
     * file.
     */
    @Override
    public void save(String[] arr) {
	String name = arr[arr.length - 2];
	String fileName = arr[arr.length - 1];
	Maze3d m = hMaze.get(name);
	try {
	    OutputStream out = new MyCompressorOutputStream(new FileOutputStream(fileName));
	    out.write(m.toByteArray());
	    out.close();
	    notifyObservers(("save:" + name).split(":"));
	} catch (FileNotFoundException e) {
	    notifyObservers("Error - the file was not found");
	} catch (IOException e) {
	    notifyObservers("Error - IOException");
	}
    }

    /**
     * This methods gets a name of a file and a maze and load to the new maze
     * the objects from the file.
     */
    @Override
    public void load(String[] arr) {
	String name = arr[arr.length - 1];
	String fileName = arr[arr.length - 2];
	try {
	    byte[] temp = new byte[4096];
	    InputStream in = new MyDecompressorInputStream(new FileInputStream(fileName));
	    int numOfBytes = in.read(temp);
	    in.close();
	    byte[] b = new byte[numOfBytes];
	    for (int i = 0; i < b.length; i++) {
		b[i] = temp[i];
	    }
	    Maze3d maze = new Maze3d(b);
	    hMaze.put(name, maze);
	    notifyObservers(("load:" + name).split(":"));
	} catch (FileNotFoundException e) {
	    notifyObservers("Error - the file was not found");
	} catch (IOException e) {
	    notifyObservers("Error - IOException");
	}
    }

    /**
     * This method gets a name of a maze and solving algorithm and solves it in
     * a Thread. All the solutions saved in an HashMap.
     */
    @Override
    public void solve(String[] arr) {
	String nameAlg = arr[arr.length - 1];
	sb = new StringBuilder();
	for (int i = 1; i < arr.length - 1; i++) {
	    sb.append(arr[i]);
	}
	String name = sb.toString();

	if ((hSol.get(name)) != null) {
	    notifyObservers(("solution:" + name).split(":"));
	}
	notifyObservers(threadpool.submit(new Callable<Solution<Position>>() {
	    
	    @Override
	    public Solution<Position> call() throws Exception {
		Maze3d m = hMaze.get(name);
		SearchableMaze sMaze = new SearchableMaze(m);
		CommonSearcher<Position> cs;
		Solution<Position> s = new Solution<Position>();
		switch (nameAlg) {
		case "Astar":
		    cs = new AStar<Position>(new MazeManhattenDistance());
		    s = cs.search(sMaze);
		    hSol.put(name, s);
		    break;
		case "A*":
		    cs = new AStar<Position>(new MazeManhattenDistance());
		    s = cs.search(sMaze);
		    hSol.put(name, s);
		    break;
		case "BFS":
		    cs = new BFS<Position>();
		    s = cs.search(sMaze);
		    hSol.put(name, s);
		    break;
		default:
		    notifyObservers("The algorithm was not found");
		    break;
		}

//		notifyObservers(("solution:" + name).split(":"));
		return s;
	    }
	}));
    }

    /**
     * This method gets a name of a maze and sends the Controller its solution
     */
    @Override
    public String bringSolution(String arr) {
	Solution<Position> s = hSol.get(arr);
	if (s != null) {
	    Stack<Position> sol = s.getSolution();
	    sb = new StringBuilder();
	    while (!sol.isEmpty()) {
		sb.append(sol.pop());
	    }
	    return sb.toString();
	}
	return "Solution do not exist for " + arr + " maze.";
    }

    /**
     * This method gets a name of a maze and sends the Controller its size.
     */
    @Override
    public void gameSize(String[] arr) {
	Maze3d temp = hMaze.get(arr[arr.length - 1]);
	try {
	    int size = (temp.toByteArray()).length;
	    notifyObservers("Maze " + arr[arr.length - 1] + " size is:" + size);
	} catch (IOException e) {
	    notifyObservers("Error, please try again");
	}
    }

    /**
     * This method gets a name of a file and sends the Controller its size.
     */
    @Override
    public void fileSize(String[] arr) {
	File f = new File(arr[arr.length - 1]);
	if (f.exists()) {
	    long size = f.length();
	    notifyObservers("File " + arr[arr.length - 1] + " size is:" + size);
	} else {
	    notifyObservers("Error, please try again");
	}
    }

    /**
     * This method closes all the open threads.
     */
    @Override
    public void close() {
	System.out.println("shutting down");
	threadpool.shutdown();
	// wait 10 seconds over and over again until all running jobs have
	// finished
	try {
	    boolean allTasksCompleted = false;
	    while (!(allTasksCompleted = threadpool.awaitTermination(10, TimeUnit.SECONDS)))
		;
//	    c.exit();
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}

    }

    @Override
    public void dirToPath(String[] arr) {
	// TODO Auto-generated method stub

    }

}

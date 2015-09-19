package presenter;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.DEFAULT;

import controller.Command;
import model.Model;
import view.View;

public class Presenter implements Observer {

    View v;
    Model m;
    HashMap<String, Command> hCommands;

    public Presenter(Model m, View v) {
	this.v = v;
	this.m = m;
	this.hCommands = new HashMap<String, Command>();
    }

    public void createHashMap() {
	hCommands.put("generate", new Command() {

	    @Override
	    public void doCommand(String[] arr) {
		m.generateMaze(arr);
	    }
	});
	hCommands.put("display", new Command() {

	    @Override
	    public void doCommand(String[] arr) {
		switch (arr[1]) {
		case "cross":
		    m.crossSection(arr);
		    break;
		case "solution":
		    m.bringSolution(arr);
		    break;
		default:
		    m.sendGame(arr);
		    break;
		}

	    }
	});
	hCommands.put("save", new Command() {

	    @Override
	    public void doCommand(String[] arr) {
		m.save(arr);
	    }
	});
	hCommands.put("load", new Command() {

	    @Override
	    public void doCommand(String[] arr) {
		m.load(arr);

	    }
	});
	hCommands.put("solve", new Command() {

	    @Override
	    public void doCommand(String[] arr) {
		m.solve(arr);
	    }
	});

	hCommands.put("maze", new Command() {

	    @Override
	    public void doCommand(String[] arr) {
		m.gameSize(arr);

	    }
	});
	hCommands.put("file", new Command() {

	    @Override
	    public void doCommand(String[] arr) {
		m.fileSize(arr);

	    }
	});
	hCommands.put("exit", new Command() {

	    @Override
	    public void doCommand(String[] arr) {
		m.close();

	    }
	});
    }

    @Override
    public void update(Observable o, Object arg) {
	if (o == v) {
	    String[] s = (String[]) arg;
	   Command command = hCommands.get(arg);
	   command.doCommand(arg);
	} else {
	    if (o == m) {

	    }
	}

	// if (o == v) {
	// if (((arg.getClass()).getName()).equals("String[]")) {
	// String[] temp = (String[]) arg;
	// switch (temp[0]) {
	// case "maze is ready":
	// byte[] b = m.sendGame(temp[1]);
	// if (b == null)
	// v.displayString("Error, please try again");
	// else
	// v.displayByte(b);
	// break;
	// case "save":
	// v.displayString("The maze "+temp[1]+" is saved");
	// break;
	// case "load":
	// v.displayString("The maze "+temp[1]+" is loaded");
	// case "solution":
	// v.displayString(m.bringSolution(temp[1]));
	// default:
	// String error = (String) arg;
	// v.displayString(error);
	// break;
	// }
	// }
	// else{
	// if (((arg.getClass()).getName()).equals("int[][]")){
	// int[][] temp = (int[][]) arg;
	// v.displayInt(temp);
	// }
	// }
	// } else {
	// if (o == v) {
	//
	// }
	// }

    }
}

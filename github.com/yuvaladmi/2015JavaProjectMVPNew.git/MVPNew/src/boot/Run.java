package boot;

import model.Maze3dModel;
import model.Model;
import presenter.Presenter;
import view.Maze3dView;
import view.View;

public class Run {

	public static void main(String[] args) {
		Model m=new Maze3dModel();
		View v= new Maze3dView();
		Presenter p=new Presenter(m,v);
		m.addObservers(p);
		v.addObservers(p);
	}

}

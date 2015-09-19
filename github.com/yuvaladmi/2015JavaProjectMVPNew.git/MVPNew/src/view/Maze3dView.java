package view;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class Maze3dView extends abstractView {
    
    public void start(){
	
    }
    
    public void displayByte(byte[] arr) {
	ByteArrayInputStream bArr = new ByteArrayInputStream(arr);
	DataInputStream data = new DataInputStream(bArr);
	try {
	    int x = data.readInt();
	    int y = data.readInt();
	    int z = data.readInt();
	    System.out.println("Start Position: " + data.readInt() + "," + data.readInt() + "," + data.readInt());
	    System.out.println("Goal Position: " + data.readInt() + "," + data.readInt() + "," + data.readInt());

	    System.out.println("Maze size: " + x + "," + y + "," + z);
	    System.out.println();
	    for (int i = 0; i < x; i++) {
		for (int j = 0; j < y; j++) {
		    for (int k = 0; k < z; k++) {
			System.out.print(data.read());
		    }
		    System.out.println();
		}
		System.out.println();
	    }
	} catch (IOException e) {
	   e.printStackTrace();
	}
    }

    @Override
    public void displayString(String arr) {
	System.out.println(arr);
	
    }

    @Override
    public void displayInt(int[][] arr) {
	for (int i = 0; i < arr.length; i++) {
	    for (int j = 0; j < arr[i].length; j++) {
		System.out.print(arr[i][j]);
	    }
	    System.out.println();
	}	
    }

}

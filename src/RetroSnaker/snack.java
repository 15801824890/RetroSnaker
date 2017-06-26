package RetroSnaker;
import javax.swing.*;

public class snack extends JFrame{
    public snack(){
    	setTitle("RetroSnaker");
    	snackWin win=new snackWin();
    	add(win);
    	setSize(435,390);
    	setLocation(200,200);
    	setVisible(true);
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        new snack();
	}

}

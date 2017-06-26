package RetroSnaker;
import java.awt.*;
import java.awt.List;

import javax.security.auth.x500.X500Principal;
import javax.swing.*;

import org.omg.CosNaming.IstringHelper;

import java.awt.event.*;
import java.util.*;

public class snackWin extends JPanel implements ActionListener,KeyListener,Runnable{
    JButton newGame=new JButton("start");
    JButton stopGame=new JButton("stop");
    int score=0,speed=0;
    boolean start=false;
    Random r=new Random();//food 
    int rx=0,ry=0;
    java.util.List<snackAct> list=new ArrayList<snackAct>();
    int temp=0,tempeat1=0,tempeat2=0;
    JDialog dialog=new JDialog();
    JLabel label=new JLabel("you lost,your score"+score);
    JButton ok=new JButton("ok...");
    Thread nThread;
    
    
    public snackWin(){
      this.setLayout(new FlowLayout(FlowLayout.LEFT));
      this.add(newGame);
      this.add(stopGame); 
      ok.addActionListener(this);
      newGame.addActionListener(this);
      stopGame.addActionListener(this);
      this.addKeyListener(this);
      dialog.setLayout(new GridLayout(2,1));
      dialog.add(label);
      dialog.add(ok);
      dialog.setSize(100,100);
      dialog.setLocation(300,300);
      dialog.setVisible(false);
    }
    public void paintComponent(Graphics g){
    	super.paintComponent(g);
    	g.drawRect(5, 40, 400, 290);
    	g.drawString("score:"+score, 150,20);
    	g.drawString("speed:"+speed, 150,35);
    	g.setColor(new Color(0,255,0));
    	if(start){
    		g.fillRect(5+rx*10, 40+ry*10, 10, 10);
    		g.setColor(new Color(225,0,0));
    		for (int i = 0; i < list.size(); i++) {
    			g.fillRect(5+list.get(i).getX()*10, 40+list.get(i).getY()*10, 10, 10);
			}
    	}
    }
	public void move(int x,int y){
		if(minYes(x, y)){
			otherMove();
			list.get(0).setX(list.get(0).getX()+x);
			list.get(0).setY(list.get(0).getY()+y);
			eat();
			repaint();
			}else{//dead method
				nThread=null;
				label.setText("you lost,your score£º"+score);
				dialog.setVisible(true);
				
				}
			}
	
	public boolean minYes(int x,int y){
		if (!maxYes(list.get(0).getX()+x,list.get(0).getY()+y)) {
			return false;
		}
		return true;
	}
	public boolean maxYes(int x,int y){
		if (x<0||x>=40||y<0||y>=29) {
			return false;
		}
		for (int i = 0; i < list.size(); i++) {
			if (i>1&&list.get(0).getX()==list.get(i).getX()&&list.get(0).getY()==list.get(i).getY()) {
				return false;
			}
		}
		return true;
	}
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (start) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
                move(0,-1);
                temp=1;
				break;
			case KeyEvent.VK_DOWN:
                move(0,1);
                temp=2;
				break;
			case KeyEvent.VK_LEFT:
                move(-1,0);
				temp=3;
                break;
			case KeyEvent.VK_RIGHT:
                move(1,0);
                temp=4;
				break;
			default:
				break;
			}
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==newGame){
			newGame.setEnabled(false);
			start=true;
			rx=r.nextInt(40);ry=r.nextInt(28);
			snackAct tempAct=new snackAct();
			tempAct.setX(20);
			tempAct.setY(15);
			list.add(tempAct);
			this.requestFocus(true);
			nThread=new Thread(this);
			nThread.start();
			repaint();
		}
		if(e.getSource()==stopGame){
			System.exit(0);
		}
		if (e.getSource()==ok) {
			list.clear();
		    score=0;
		    speed=0;
			start=false;
			newGame.setEnabled(true);
			dialog.setVisible(false);
			repaint();
		}
	}
	public void otherMove(){
		snackAct tempAct=new snackAct();
		for (int i = 0; i < list.size(); i++) {
			if (i==1) {
				list.get(i).setX(list.get(0).getX());
				list.get(i).setY(list.get(0).getY());
			}else if(i>1){
				tempAct=list.get(i-1);
				list.set(i-1, list.get(i));
				list.set(i, tempAct);
			}
		}
	}
	
	private void eat(){
		if(rx==list.get(0).getX()&&ry==list.get(0).getY()){
			rx=r.nextInt(40);ry=r.nextInt(30);
			snackAct tempAct=new snackAct();
			tempAct.setX(list.get(list.size()-1).getX());
			tempAct.setY(list.get(list.size()-1).getY());
			list.add(tempAct);
			score+=100+10*speed;
			tempeat1+=1;
			if(tempeat1-tempeat2>=10){
				tempeat2=tempeat1;
				if (speed<=9) {
					speed+=1;
				}
			}
			}
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (start) {
			switch(temp){
			case 1:
			    move(0, -1);
			    break;
			case 2:
			    move(0, 1);
			    break;
			case 3:
			    move(-1, 0);
			    break;
			case 4:
			    move(1,0);
			    break;
			default:
				move(1,0);
				break;
			
			}
			score+=10*speed;
			repaint();
			try {
				Thread.sleep(500-(50*speed));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}

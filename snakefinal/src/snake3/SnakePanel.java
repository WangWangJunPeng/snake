package snake3;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class SnakePanel extends JPanel implements KeyListener, ActionListener {

	private static final long serialVersionUID = 1L;
	ImageIcon up = new ImageIcon("up.png");
	ImageIcon down = new ImageIcon("down.png");
	ImageIcon right = new ImageIcon("right.png");
	ImageIcon left = new ImageIcon("left.png");
	ImageIcon title = new ImageIcon("title.jpg");
	ImageIcon body = new ImageIcon("body.png");
	ImageIcon food = new ImageIcon("food.png");
	
	int[] snakex = new int[750];
	int[] snakey = new int[750];
	int len = 3; //长度
	int score=0; //分数

	String fangxiang = "R"; //方向：R, L, U, D
	
	Random rand = new Random();
	int foodx = rand.nextInt(34)*25+25;
	int foody = rand.nextInt(23)*25+75;
	
	boolean isStarted = false;
	boolean isFailed = false;
	Timer timer = new Timer(100, this);
	
	
	public SnakePanel() {
		this.setFocusable(true);
		this.addKeyListener(this);
		timer.start();
		snakex[0] = 100;
		snakey[0] = 100;
		snakex[1] = 75;
		snakey[1] = 100;
		snakex[2] = 50;
		snakey[2] = 100;

	}
	
	public void paint(Graphics g) {
		title.paintIcon(this, g, 25, 11);
		g.setColor(Color.BLACK);
		g.fillRect(25, 75, 850, 600);
		
		if(fangxiang.equals("L")) {
			left.paintIcon(this, g, snakex[0], snakey[0]);
		} else if(fangxiang.equals("D")) {
			down.paintIcon(this, g, snakex[0], snakey[0]);
		} else if(fangxiang.equals("U")) {
			up.paintIcon(this, g, snakex[0], snakey[0]);
		} else {
			right.paintIcon(this, g, snakex[0], snakey[0]);
		}
		
		for(int i=1; i<len; i++) {
			body.paintIcon(this, g, snakex[i], snakey[i]);
		}
		
		food.paintIcon(this, g, foodx, foody);
		
	    g.setColor(Color.WHITE);
	    g.setFont(new Font("arial", Font.PLAIN, 12));
		g.drawString("Score: "+score, 750, 35);
		g.drawString("Length: "+len, 750, 50);
		
		//提示
		if(isStarted == false) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("arial", Font.BOLD, 40));
			g.drawString("Press Space to Start", 300, 300);
		}

		if(isFailed) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("arial", Font.BOLD, 40));
			g.drawString("Game Over...", 300, 300);
			g.setFont(new Font("arial", Font.BOLD, 20));
			g.drawString("Press Space to restart...", 350, 340);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyCode == KeyEvent.VK_SPACE) {
			if(isFailed) {
				isFailed = false;
				isStarted = false;
				len = 3;
				snakex[0] = 100;
				snakey[0] = 100;
				snakex[1] = 75;
				snakey[1] = 100;
				snakex[2] = 50;
				snakey[2] = 100;
				fangxiang = "R";
			} else {
				isStarted = !isStarted;
			}
		}else if(keyCode == KeyEvent.VK_RIGHT) {
		   if(fangxiang !="L") fangxiang = "R";	
		}else if(keyCode == KeyEvent.VK_LEFT) {
		   if(fangxiang !="R") fangxiang = "L";
		}else if(keyCode == KeyEvent.VK_UP) {
			if(fangxiang !="D")	fangxiang = "U";
		}else if(keyCode == KeyEvent.VK_DOWN) {
			if(fangxiang !="U")fangxiang = "D";
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if(isStarted && !isFailed) {
			for(int i=len; i>0; i--) {
				snakex[i] = snakex[i-1];
				snakey[i] = snakey[i-1];
			}
			
			if(fangxiang.equals("R")) {
				snakex[0] = snakex[0] + 25;
				if(snakex[0] > 850) snakex[0] = 25;
			}else if(fangxiang.equals("L")) {
				snakex[0] = snakex[0] - 25;
				if(snakex[0] < 25) snakex[0] = 850;
			}else if(fangxiang.equals("U")) {
				snakey[0] = snakey[0] - 25;
				if(snakey[0] < 75) snakey[0] = 650;
			} else if(fangxiang.equals("D")) {
				snakey[0] = snakey[0] + 25;
				if(snakey[0] > 650) snakey[0] = 75;
			}
			
			if(snakex[0] == foodx && snakey[0] == foody) {
				len++;
				score++;
				foodx = rand.nextInt(34)*25+25;
				foody = rand.nextInt(24)*25+75;
			}
			
			for(int i=1; i<len; i++) {
				if(snakex[i] == snakex[0] && snakey[i] == snakey[0]) {
					isFailed = true;
				}
			}
		}
		
		repaint();
	}

}

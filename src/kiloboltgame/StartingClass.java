package kiloboltgame;

import java.applet.Applet;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import kiloboltgame.framework.Animation;

public class StartingClass extends Applet implements Runnable, KeyListener {

	enum GameState{
		Start,Running,Dead
	}
//    GameState state = GameState.Start;
	GameState state = GameState.Running;
    //declaration
	private static Robot robot;
	public static Heliboy hb,hb2,hb3,hb4;
	 public static HealthyFood hf, hf2, hf3;
	public static int score = 0;// to store the score 
	private Font font = new Font(null, Font.BOLD, 30);


	private Image image, currentSprite, character, character2, character3,
			characterDown, characterJumped, background, heliboy, heliboy2,healthyfood;

	public static Image tilegrassTop, tilegrassBot, tilegrassLeft,
			tilegrassRight, tiledirt;

	private Graphics second;
	private URL base;
	private static Background bg1, bg2;
	private Animation anim, hanim,fanim;

	//to store array of images of tile 
	private ArrayList<Tile> tilearray = new ArrayList<Tile>();
	

	@Override
	public void init() {

		setSize(800, 480);
		setBackground(Color.BLACK);
		setFocusable(true);
		addKeyListener(this);
		Frame frame = (Frame) this.getParent().getParent();
		frame.setTitle("Avoid Junkies");
		frame.setVisible(false);
		
		Frame f=new Frame();
	    f.setSize(800,480);
	    f.setLayout(null);
	    
	
	    
	    Button b1=new Button("PLAY");
		Button b2=new Button("INSTRUCTION");
		Button b3=new Button("EXIT");
		b1.setBounds(130, 300, 150, 50);
		b2.setBounds(330,300,150,50);
		b3.setBounds(530, 300, 150,50);
		
		Font ft=new Font("Ariela",Font.BOLD,78);
		f.setBackground(Color.cyan);
		Label l=new Label("Avoid Junk");
		l.setFont(ft);
		l.setBounds(190, 100, 470, 150);
		f.add(l);
	    f.add(b1);
	    f.add(b2);
		f.add(b3);
		f.setVisible(true);
		b2.addActionListener(new ActionListener(){

			
			public void actionPerformed(ActionEvent e) {
			
				f.setVisible(false);
				Frame f2=new Frame("Instructions");
				f2.setSize(800,500);
				Font ft1=new Font("Ariela",Font.BOLD,14);
				Label l2=new Label("1. THERE WILL BE  ");
				Label l3=new Label("2. ");
				Label l4=new Label("3. ");
				Label l5=new Label("4. ");
				Label l6=new Label("5. ");
				l2.setBounds(50, 50, 400, 30);
				l3.setBounds(50, 120, 400, 30);
				l4.setBounds(50, 190, 400, 30);
				l5.setBounds(50, 260, 400, 30);
				l6.setBounds(50, 330, 400, 30);
				l2.setFont(ft1);
				l3.setFont(ft1);
				l4.setFont(ft1);
				l5.setFont(ft1);
				l6.setFont(ft1);
				Button b4=new Button("BACK");
				b4.setBounds(700, 450, 80, 50);
				f2.add(b4);
				f2.add(l2);
				f2.add(l3);
				f2.add(l4);
				f2.add(l5);
				f2.add(l6);
				f2.setBackground(Color.LIGHT_GRAY);
				f2.setLayout(null);
				f2.setVisible(true);
				
				  f2.addWindowListener(new WindowAdapter() {
					    public void windowClosing(WindowEvent windowEvent){
					    	f2.setVisible(false);
							f.setVisible(true);
					    }        
					    });
				  
				  b4.addActionListener(new ActionListener(){

						
						public void actionPerformed(ActionEvent e) {
						
							f2.setVisible(false);
							f.setVisible(true);
							
							}
						});
				}
			});
		

	 
		 b1.addActionListener(new ActionListener(){

				
				public void actionPerformed(ActionEvent e) {
				
			
				f.dispose();
				
				frame.setResizable(false);
			    frame.setVisible(true);
				frame.setFocusable(true);
				setFocusable(true);
				
			   }

			

		
		 });
		
		 b3.addActionListener(new ActionListener(){

				
				public void actionPerformed(ActionEvent e) {
				System.exit(0);
					}
				});
		 
	    f.addWindowListener(new WindowAdapter() {
		    public void windowClosing(WindowEvent windowEvent){
		      System.exit(0);
		    }        
		    });
	  
//		Panel startpanel = new Panel();
//		Button b = new Button("Play");
//		startpanel.add(b);
//		
//		frame.add(startpanel);
		
		try {
			base = getDocumentBase();
		} catch (Exception e) {
			// TODO: handle exception
		}

		// Image Setups
		//characters for animation
		character = getImage(base, "data/character.png");
		character2 = getImage(base, "data/character2.png");
		character3 = getImage(base, "data/character3.png");
		//for jump and duck
		characterDown = getImage(base, "data/down.png");
		characterJumped = getImage(base, "data/jumped.png");

		//enemy animation	
		heliboy = getImage(base, "data/pizza.png");
		heliboy2 = getImage(base, "data/pizza1.png");
		
		healthyfood = getImage(base,"data/cabbage.png");

		//background animation
		background = getImage(base, "data/background1.png");

		//tile maps
		tiledirt = getImage(base, "data/tiledirt.png");
		tilegrassTop = getImage(base, "data/tilegrasstop.png");
		tilegrassBot = getImage(base, "data/tilegrassbot.png");
		tilegrassLeft = getImage(base, "data/tilegrassleft.png");
		tilegrassRight = getImage(base, "data/tilegrassright.png");

		//robo animation
		anim = new Animation();	
		anim.addFrame(character, 1250);
		anim.addFrame(character2, 50);
		anim.addFrame(character3, 50);
		anim.addFrame(character2, 50);
		
		//heliboy animation
		hanim = new Animation();
		hanim.addFrame(heliboy, 200);
		hanim.addFrame(heliboy2, 200);
		
		fanim=new Animation();
		fanim.addFrame(healthyfood, 150);

		currentSprite = anim.getImage();
	}

	@Override
	public void start() {
		// start of Applet 
		
		bg1 = new Background(0, 0);
		bg2 = new Background(2160, 0);
		robot = new Robot();
		// Initialize Tiles
		try {
			loadMap("data/map1.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//init helliboys 
		hb = new Heliboy(340, 360);
		hb2 = new Heliboy(700, 360);
		hb3 = new Heliboy(1000, 360);
		
		hf = new HealthyFood(600,320);
		hf2 = new HealthyFood(3200,250);
		hf3 = new HealthyFood(4200,250);
		
		// creating thread
		Thread thread = new Thread(this);
		thread.start();
	}

	private void loadMap(String filename) throws IOException {
		ArrayList lines = new ArrayList();
		int width = 0;
		int height = 0;
		//reading values from file 
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		while (true) {
			String line = reader.readLine();
			// no more lines to read
			if (line == null) {
				reader.close();
				break;
			}

			if (!line.startsWith("!")) {
				lines.add(line);
				width = Math.max(width, line.length());

			}
		}
		height = lines.size();
		//j = height , i = width
		
		for (int j = 0; j < 12; j++) {
			String line = (String) lines.get(j);
			for (int i = 0; i < width; i++) {

				if (i < line.length()) {
					char ch = line.charAt(i);
					Tile t = new Tile(i, j, Character.getNumericValue(ch)); //typecast character to integer
					tilearray.add(t);
				}

			}
		}

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

    @Override
public void run() {

	if (state == GameState.Running) {
		while (true) {
			robot.update();
			if (robot.isJumped()) {
				currentSprite = characterJumped;
			} else if (robot.isJumped() == false
					&& robot.isDucked() == false) {
				currentSprite = anim.getImage();
			}

			ArrayList projectiles = robot.getProjectiles();
			
			for (int i = 0; i < projectiles.size(); i++) {
				Projectile p = (Projectile) projectiles.get(i);
				if (p.isVisible() == true) {
					p.update();
				} else {
					projectiles.remove(i);
				}
			}

			updateTiles();
			hb.update();
			hb2.update();
			hb3.update();
			hf.update();
			hf2.update();
			hf3.update();
			
			bg1.update();
			bg2.update();
			anim.update(10); //robot update
			hanim.update(50); //helliboy update 
			fanim.update(50);
			
			repaint();
			try {
				Thread.sleep(17); // calculated with 60fps
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (robot.getCenterY() > 500) {
				state = GameState.Dead;
			}
			if (score < 0) {
				state = GameState.Dead;
				score = 0;
			}
			
		}
	}

}
		
	

	@Override
	public void update(Graphics g) {
		if (image == null) {
			image = createImage(this.getWidth(), this.getHeight());
			second = image.getGraphics();
		}

		second.setColor(getBackground());
		second.fillRect(0, 0, getWidth(), getHeight());
		second.setColor(getForeground());
		paint(second);

		g.drawImage(image, 0, 0, this);

	}

	@Override
	public void paint(Graphics g) {
		if(state == GameState.Start){
			
//			g.setColor(Color.BLACK);
//			g.fillRect(0, 0, 800, 480);
//			g.setColor(Color.WHITE);
//			g.drawString("Start", 360, 240);
//			g.drawString(Integer.toString(score), 360, 280);	
			//state = GameState.Running;
			
		}
		else if (state == GameState.Running) {

		g.drawImage(background, bg1.getBgX(), bg1.getBgY(), this);
		g.drawImage(background, bg2.getBgX(), bg2.getBgY(), this);
		paintTiles(g);

		ArrayList projectiles = robot.getProjectiles();
		for (int i = 0; i < projectiles.size(); i++) {
			Projectile p = (Projectile) projectiles.get(i);
			g.setColor(Color.YELLOW);
			g.fillRect(p.getX(), p.getY(), 10, 5);
		}
		//render on screen
		g.drawImage(currentSprite, robot.getCenterX() - 61,
				robot.getCenterY() - 63, this);
		g.drawImage(hanim.getImage(), hb.getCenterX() - 48,
				hb.getCenterY() - 48, this);
		g.drawImage(hanim.getImage(), hb2.getCenterX() - 48,
				hb2.getCenterY() - 48, this);		
		g.drawImage(hanim.getImage(), hb3.getCenterX() - 48,
						hb3.getCenterY() - 48, this);
		g.drawImage(fanim.getImage(), hf.getCenterX() - 48,
	              hf.getCenterY() - 48, this);
		g.drawImage(fanim.getImage(), hf2.getCenterX() - 48,
	              hf2.getCenterY() - 48, this);            
		g.drawImage(fanim.getImage(), hf3.getCenterX() - 48,
	              hf3.getCenterY() - 48, this);
	            
		
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString("Health", 600, 30);
		g.drawString(Integer.toString(score), 740, 30);	
		} 
		else if (state == GameState.Dead) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 800, 480);
			g.setColor(Color.WHITE);
			g.drawString("Dead", 360, 240);
			g.drawString(Integer.toString(score), 360, 280);	
		}
	}

	private void updateTiles() {

		for (int i = 0; i < tilearray.size(); i++) {
			Tile t = (Tile) tilearray.get(i);
			t.update();
		}

	}

	private void paintTiles(Graphics g) {
		for (int i = 0; i < tilearray.size(); i++) {
			Tile t = (Tile) tilearray.get(i);
			g.drawImage(t.getTileImage(), t.getTileX(), t.getTileY(), this);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			System.out.println("Move up");
			break;

		case KeyEvent.VK_DOWN:
			currentSprite = characterDown;
			if (robot.isJumped() == false) {
				robot.setDucked(true);
				robot.setSpeedX(0);
			}
			break;

		case KeyEvent.VK_LEFT:
			robot.moveLeft();
			robot.setMovingLeft(true);
			break;

		case KeyEvent.VK_RIGHT:
			robot.moveRight();
			robot.setMovingRight(true);
			break;

		case KeyEvent.VK_SPACE:
			robot.jump();
			break;

		case KeyEvent.VK_CONTROL:
			if (robot.isDucked() == false && robot.isJumped() == false
					&& robot.isReadyToFire()) {
				robot.shoot();
				robot.setReadyToFire(false);
			}
			break;

		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			System.out.println("Stop moving up");
			break;

		case KeyEvent.VK_DOWN:
			currentSprite = anim.getImage();
			robot.setDucked(false);
			break;

		case KeyEvent.VK_LEFT:
			robot.stopLeft();
			break;

		case KeyEvent.VK_RIGHT:
			robot.stopRight();
			break;

		case KeyEvent.VK_SPACE:
			break;

		case KeyEvent.VK_CONTROL:
			robot.setReadyToFire(true);
			break;

		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public static Background getBg1() {
		return bg1;
	}

	public static Background getBg2() {
		return bg2;
	}

	public static Robot getRobot() {
		return robot;
	}

}

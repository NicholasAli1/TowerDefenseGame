import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class TowerGame extends JComponent implements Runnable, MouseListener {

	long lastTime;
	boolean togglePlaceTowers = false, loading = true;
	double spawn = 0;
	int score = 0, lives = 5;
	ArrayList<Tower> towerList = new ArrayList<Tower>();
	ArrayList<BadGuy> badGuyList = new ArrayList<BadGuy>();
	ArrayList<Bullet> bulletList = new ArrayList<Bullet>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		TowerGame game = new TowerGame();

		JFrame frame = new JFrame("Spacey");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setSize(825, 800);

		frame.add(game);

		frame.getContentPane().setBackground(Color.black);

	}

	public TowerGame() {

		this.addMouseListener(this);

		Thread t = new Thread(this);
		t.start();

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

	public void paintComponent(Graphics g) {

		g.setColor(Color.magenta);
		for (BadGuy bg : badGuyList) {
			g.fillOval(bg.getX(), bg.getY(), bg.getWidth(), bg.getHeight());
		}

		g.setColor(Color.pink);
		for (int i = 0; i < bulletList.size(); i++) {
			g.fillOval(bulletList.get(i).getX(), bulletList.get(i).getY(), bulletList.get(i).getWidth(),
					bulletList.get(i).getHeight());
		}

		for (Tower t : towerList) {

			g.setColor(Color.green);

			g.drawRect(t.getX(), t.getY(), t.getSize(), t.getSize());
		}

		g.setColor(Color.white);
		g.drawString("Lives: " + lives, 750, 20);
		g.drawString("Score: " + score, 20, 20);
		loading = false;

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		this.togglePlaceTowers = !this.togglePlaceTowers;
		System.out.println(this.togglePlaceTowers);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

		towerList.add(new Tower(e.getX(), e.getY()));

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		lastTime = System.currentTimeMillis();
		gameLoop: while (true) {
			long currentTime = System.currentTimeMillis();
			double deltaTime = (currentTime - lastTime) / 1000.0;
			lastTime = currentTime;

			if (loading) {

				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				continue gameLoop;

			}

			badguyCheck: for (int i = 0; i < badGuyList.size(); i++) {

				badGuyList.get(i).updated(deltaTime);

				for (int j = 0; j < towerList.size(); j++) {

					if (towerList.get(j).isInRange(badGuyList.get(i).getCenterX(), badGuyList.get(i).getCenterY(),
							badGuyList.get(i).getHeight() / 2)) {
						bulletList.add(new Bullet(towerList.get(j).getCenterX(), towerList.get(j).getCenterY(),
								badGuyList.get(i).getCenterX(), badGuyList.get(i).getCenterY(), 300));
					}

				}

				for (int j = 0; j < bulletList.size(); j++) {
					if (badGuyList.get(i).didHit(bulletList.get(j).getCenterX(), bulletList.get(j).getCenterY(),
							bulletList.get(j).getRadius())) {
						bulletList.remove(j);
						badGuyList.remove(i);
						score++;
						continue badguyCheck;
					}
				}

				if (badGuyList.get(i).isDead()) {
					lives--;
					badGuyList.remove(i);
					i--;
				}

			}

			for (int i = 0; i < bulletList.size(); i++) {
				bulletList.get(i).update(deltaTime);
			}

			for (int i = 0; i < towerList.size(); i++) {
				towerList.get(i).update(deltaTime);
			}

			spawn -= deltaTime;

			if (spawn < 0) {
				int[] x = { 0, 200, 400, 400, 600, 600, 700, 700, 50, 50, 50, -100, };
				int[] y = { 0, 200, 200, 400, 400, 600, 600, 700, 700, 700, 250, 250 };
				badGuyList.add(new BadGuy(0, -50, x, y));
				spawn = .5;
			}

			if (lives <= 0) {

				badGuyList.clear();
				lives = 5;

			}

			repaint();

			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

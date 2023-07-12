package a11;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * A top-level panel for playing a game similar to Plants Vs Zombies.
 * 
 * This panel is primarily responsible for coordinating the various aspects of
 * the game, including: - Running the game step-by-step using a timer - Creating
 * and displaying other components that make up the game - Creating new plants
 * and/or zombies, when necessary - Checking for the end of the game
 * 
 * (Not all of the above behavior is provided in the starter code)
 * 
 * @author Raj Reddy
 */
@SuppressWarnings("serial")
public class Game extends JPanel implements ActionListener {
	private static final int NUM_ROWS = 5;
	private static final int NUM_COLS = 7;
	private static final int GRID_BUFFER_PIXELS = 20;
	private static final int CELL_SIZE = 75;
	private static final int STEP_TIME = 30;

	private Timer timer;
	private JLabel openingText;

	/**
	 * This panel is responsible for displaying plants and zombies, and for managing
	 * their interactions.
	 */
	private ActorDisplay actorDisplay = new ActorDisplay(NUM_COLS * CELL_SIZE + GRID_BUFFER_PIXELS * 2,
			NUM_ROWS * CELL_SIZE + GRID_BUFFER_PIXELS * 2);

	private Game() {
		// first box
		Box firstBox = Box.createHorizontalBox();

		// second box
		Box secondBox = Box.createHorizontalBox();

		// choose your icon
		openingText = new JLabel("CHOOSE YOUR PLANT!");
		JRadioButton firstTree = new JRadioButton(
				new ImageIcon(((new ImageIcon("src/a11/Animal-Icons/plant.png")).getImage()).getScaledInstance(100, 100,
						java.awt.Image.SCALE_SMOOTH)));

		// adding text to the first box
		firstBox.add(firstTree);
		JRadioButton secondTree = new JRadioButton(
				new ImageIcon(((new ImageIcon("src/a11/Animal-Icons/cool-plant.png")).getImage()).getScaledInstance(100,
						100, java.awt.Image.SCALE_SMOOTH)));

		// adding text to the second box
		firstBox.add(secondTree);

		// adding to second box
		secondBox.add(openingText);
		add(firstBox);
		add(secondBox);

		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {

				int plantType = 0;
				if (firstTree.isSelected()) {

					plantType = 0;
				} else if (secondTree.isSelected()) {

					plantType = 1;
				} else {

					return;
				}

				newPlant(plantType, me.getX(), me.getY() - 100);
			}
		});

		add(actorDisplay);
		// This layout causes all elements to be stacked vertically
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		// The timer calls the actionPerformed method every STEP_TIME milliseconds
		timer = new Timer(STEP_TIME, this);
		timer.start();

		// This adds a plant to every row
		// TODO: replace this with your new functionality
		for (int i = 0; i < NUM_ROWS; i++) {
			addPlant(0, i);
		}
	}

	/**
	 * Executes game logic every time the timer ticks.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (actorDisplay.step()) {
			timer.stop();
			openingText.setText("GAME OVER");
		}
		Random rand = new Random();
		int randZombie = rand.nextInt(2);
		if (rand.nextInt(100) > 98) {
			addZombie(randZombie, NUM_COLS - 1, rand.nextInt(NUM_ROWS));
		}
	}

	/**
	 * Adds a plant to the official game grid & display panel.
	 */
	private void addPlant(int col, int row) {
		// The magic numbers below define various hardcoded plant properties
		actorDisplay.addActor(new Plant(gridToPixel(col), gridToPixel(row), CELL_SIZE * 4 / 5,
				"src/a11/Animal-Icons/plant.png", 150, 5, 1));

	}

	private void newPlant(int plantType, int col, int row) {

		Plant newPlant = null;

		if (plantType == 0) {
			newPlant = new Plant(gridToPixel(pixelToGrid(col)), gridToPixel(pixelToGrid(row)), CELL_SIZE * 4 / 5,
					"src/a11/Animal-Icons/plant.png", 150, 5, 1);
		} else if (plantType == 1) {
			newPlant = new CoolPlant(gridToPixel(pixelToGrid(col)), gridToPixel(pixelToGrid(row)), CELL_SIZE * 4 / 5,
					"src/a11/Animal-Icons/cool-plant.png", 150, 5);
		}

		// The magic numbers below define various hardcoded plant properties
		actorDisplay.addActor(newPlant);
	}

	/**
	 * Adds a zombie to the official game grid & display panel.
	 */
	private void addZombie(int randZombie, int col, int row) {
		if (randZombie == 0) {
			// The magic numbers below define various hardcoded zombie properties
			actorDisplay.addActor(new Zombie(gridToPixel(col), gridToPixel(row), CELL_SIZE * 4 / 5,
					"src/a11/Animal-Icons/zombie.png", 100, 50, -2, 10));
		} else {
			actorDisplay.addActor(new CoolZombie(gridToPixel(col), gridToPixel(row), CELL_SIZE * 4 / 5,
					"src/a11/Animal-Icons/cool-zombie.png", 100, 50, -2, 10));
		}
	}

	/**
	 * Converts a row or column to its exact pixel location in the grid.
	 */
	private int gridToPixel(int xOrY) {
		return xOrY * CELL_SIZE + GRID_BUFFER_PIXELS;
	}

	/**
	 * The inverse of gridToPixel
	 */
	private int pixelToGrid(int xOrY) {
		return (xOrY - GRID_BUFFER_PIXELS) / CELL_SIZE;
	}

	/**
	 * Create, start, and run the game.
	 */
	public static void main(String[] args) {
		JFrame windowName = new JFrame("PLANTS VS ZOMBIES");

		windowName.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		windowName.add(new Game());
		windowName.pack();
		windowName.setVisible(true);
	}
}
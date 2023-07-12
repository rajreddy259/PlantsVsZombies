package a11;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.*;

@SuppressWarnings("serial")
public class ActorDisplay extends JPanel {
    /** Contains all plants and zombies in this game. */
	private ArrayList<Actor> actors = new ArrayList<>();

	/**
	 * Creates a canvas upon which all actors will live.
	 * @param colPixels the number of pixels that this panel is wide
	 * @param rowPixels the number of pixels that this panel is high
	 */
	public ActorDisplay(int colPixels, int rowPixels) {
		setPreferredSize(new Dimension(colPixels, rowPixels));
	}
	
	/**
	 * Adds an actor to the master list of actors ONLY IF
	 * the provided actor is not colliding with any of the existing
	 * actors.
	 * @param actor the object to add
	 * @return false if something prevents the actor from being added, true otherwise
	 */
	public boolean addActor(Actor actor) {
	    if (actor.isCollidingAny(actors)) {
	        return false;
	    }
        actors.add(actor);
        return true;
	}

	/**
	 * This overrided method draws the details of this particular panel,
	 * including all actors that are contained within.
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Actor actor : actors) {
			actor.draw(g);
		}
	}

	/**
	 * Executes all of the actor logic that happens in one turn, including
	 * moving actors, checking for collisions, managing attacks, and more.
	 */
	public boolean step() {
		// Increment actor cooldowns.
		for (Actor actor : actors) {
			actor.update();
		}

		// Allow all actors to interact with all other actors.
		// This is where attacks, healing, etc happen.
		for (Actor actor : actors) {
			for (Actor other : actors) {
					actor.actOn(other);
			}
		}

		// Remove plants and zombies with low health
		ArrayList<Actor> nextTurnActors = new ArrayList<>();
		for (Actor actor : actors) {
			if (actor.isAlive())
				nextTurnActors.add(actor);
			else
				actor.removeAction(actors); // Execute any special effects for dead actors
		}
		actors = nextTurnActors;

		// Move the (alive) actors that are not colliding.
		for (Actor actor : actors) {
		    if (!actor.isCollidingAny(actors)) {
		        actor.move();
		    }
		}
		
		for (Actor actor : actors) {
			if(actor instanceof Zombie && actor.getXPosition() <= 0) {
				return true;
			}
		}
		// Redraw the scene.
		repaint();
		return false;
	}
}
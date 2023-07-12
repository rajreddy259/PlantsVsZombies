package a11;

/**
 * This class is the cool plant class. The cool plant is a modified version of
 * the other plant. It has a starting damage of about 2 times the normal amount,
 * which is in the other plant. It also kicks back the zombie about 100 pixels
 * when in battle.
 * 
 * @author Raj Reddy
 *
 */
public class CoolPlant extends Plant {

	private final static int DAMAGE = 2;

	public CoolPlant(int xPosition, int yPosition, int size, String imgPath, int health, int coolDown) {
		super(xPosition, yPosition, size, imgPath, health, coolDown, DAMAGE);
	}

	@Override
	public void actOn(Zombie other) {
		if (isColliding(other)) {
			if (isReadyForAction()) {
				other.changeHealth(-attackDamage);
				other.shiftPosition(100, 0);
				resetCoolDown();
			}
		}

		// other.damage(1000101)
	}
}

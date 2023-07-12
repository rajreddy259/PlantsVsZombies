package a11;

/**
 * The cool zombie is the modified version of the regular zombie. It has a much
 * more abilities. This zombie will attack the plant and whatever damage that is
 * dealt, will be added to the zombie in half.
 * 
 * 
 * @author Raj Reddy
 *
 */
public class CoolZombie extends Zombie {

	public CoolZombie(int xPosition, int yPosition, int size, String imgPath, int health, int coolDown, int speed,
			int attackDamage) {
		super(xPosition, yPosition, size, imgPath, health, coolDown, speed, attackDamage);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void actOn(Plant other) {
		if (isColliding(other)) {
			if (isReadyForAction()) {
				other.changeHealth(-attackDamage);
				this.changeHealth(attackDamage / 2);
				resetCoolDown();
			}
		}
	}

}

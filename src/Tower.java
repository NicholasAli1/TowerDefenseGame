public class Tower {

	private int radius;
	private int centerX, centerY, height;
	private boolean lock = false;
	double fireRate = 0;

	public Tower(int x, int y) {

		height = 20;

		radius = 200;
		centerX = x / height * height + height / 2;
		centerY = y / height * height + height / 2;

	}

	public int getX() {
		return centerX - height / 2;
	}

	public int getY() {
		return centerY - height / 2;
	}

	public int getCenterX() {
		return centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	public int getSize() {
		return height;
	}

	public boolean isInRange(int x, int y, int radius) {
		int totalRadius = this.radius + radius;
		int deltaX = x - this.centerX;
		int deltaY = y - this.centerY;
		double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

		if (lock || totalRadius < distance) {
			return false;
		}
		lock = true;
		return true;
	}

	public void update(double deltaTime) {
		// TODO Auto-generated method stub
		fireRate -= deltaTime;

		if (fireRate < 0 && lock) {

			lock = false;
			fireRate += 1;

		}
	}

}

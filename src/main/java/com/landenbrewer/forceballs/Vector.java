package com.landenbrewer.forceballs;

public class Vector {
	public static final double BIG_G = 6.67E-11;
	public static final double EARTH_MASS = 5.972E24;
	public static final double MOON_MASS = 7.34767309E22;
	public double magnitude;
	public double direction;

	public double xComponent;
	public double yComponent;

	public Vector(double magnitude, double direction) {
		this.magnitude = magnitude;
		if (Double.isNaN(direction)) {
			this.direction = 0;
		} else {
			this.direction = direction;
		}


		this.xComponent = magnitude * Math.cos(Math.toRadians(this.direction));

		this.yComponent = magnitude * Math.sin(Math.toRadians(this.direction));
	}

	public Vector() {
	}

	public Vector add(Vector... vectors) {
		double tempx = this.xComponent;
		double tempy = this.yComponent;

		for (Vector vector : vectors) {
			tempx += vector.xComponent;
			tempy += vector.yComponent;
		}

		this.xComponent = tempx;
		this.yComponent = tempy;

		this.magnitude = Math.sqrt(Math.pow(tempx, 2) + Math.pow(tempy, 2));

		this.magnitude = (tempx < 0) ? -this.magnitude : this.magnitude;

		double direction = Math.toDegrees(Math.atan(tempy / tempx));


		return this;
	}

	//TODO change sub function to actually work
	public Vector sub(Vector vector) {
		return add(new Vector(-vector.magnitude, vector.direction));
	}


	public double dot(Vector vector) {
		return (this.xComponent * vector.xComponent) + (this.yComponent * vector.yComponent);
	}

	public static Vector gravityVector(double mass) {
		return new Vector(mass * 9.8 * 0.005, 90);
	}

	public static double gravity(double mass, double distance) {
		return (BIG_G * mass) / Math.pow(distance, 2);
	}

	@Override
	public String toString() {
		return "Vector [" +
				"magnitude=" + magnitude +
				", direction=" + direction +
				", xComponent=" + xComponent +
				", yComponent=" + yComponent +
				']';
	}

	public static void main(String[] args) {
		Vector v1 = new Vector(10, 30);
		Vector v2 = new Vector(10, 43.2);

		v1.add(v2);

		System.out.println(v1);

		System.out.println("EARTH GRAVITY: " + gravity(EARTH_MASS, 6371071.03 + (200 * 1000)));
		System.out.println("BASEBALL GRAVITY " + gravity(EARTH_MASS * 10, 149600000));

		System.out.println(new Vector(0, 45).add(new Vector(0, 0)));
		System.out.println(new Vector(21, Double.NaN));
	}

}

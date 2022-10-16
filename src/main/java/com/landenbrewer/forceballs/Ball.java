package com.landenbrewer.forceballs;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;


public class Ball extends Pane {

	//double startX, startY, xBoundary, yBoundary, lowXBoundary, lowYBoundary, radius;

	/*
		x and y are the starting point of the middle of the ball.
		If not provided to the constructor, each will be a default value of 50
	 */
	private double x, y;


	/*
		highXBoundary and highYBoundary are the MAXIMUM boundaries set for the ball when in motion.

		highXBoundary will set the boundary for the right side.
		highYBoundary will set the boundary for the bottom.

		When not provided both will default to being 100.

		It will not pass this point.
	 */
	private final double highXBoundary, highYBoundary;


	/*
		lowXBoundary and lowYBoundary are the MINIMUM boundaries set for the ball when in motion.

		lowXBoundary will be the leftmost boundary set for the ball.
		lowYBoundary will be the topmost boundary set.

		When not provided each value will be set to a default of 0.
	 */
	private final double lowXBoundary, lowYBoundary;
	/*
		The radius of the ball.
	 */
	private final double radius;

	private final Circle ball;

	private Vector velocity;

	private final double mass;

	private double scale;

	private Line velocityVector;

	public Ball(double startX, double startY, double highXBoundary, double highYBoundary,
				double lowXBoundary, double lowYBoundary, double radius, double mass) {
		this.x = startX;
		this.y = startY;
		this.highXBoundary = highXBoundary;
		this.highYBoundary = highYBoundary;
		this.lowXBoundary = lowXBoundary;
		this.lowYBoundary = lowYBoundary;
		this.radius = radius;
		this.mass = mass;

		ball = new Circle(startX, startY, radius);
		ball.setFill(Color.TRANSPARENT);
		ball.setStroke(Color.BLACK);

		//Velocity vector. NOT IN NEWTONS. MAGNITUDE IS IN M/S^2
		this.velocity = new Vector(0, 0);

		//System.out.println(velocity);

		//velocityVector = new Line(this.x, this.y, velocity.xComponent, velocity.yComponent);

		getChildren().addAll(ball);

		this.scale = 1;


	}
	public Ball(double startX, double startY, double highXBoundary, double highYBoundary, double radius) {
		this(startX, startY, highXBoundary, highYBoundary, 0, 0, radius, 10);
	}

	public Ball(double startX, double startY, double radius) {
		this(startX, startY, 100, 100, 0, 0, radius, 10);
	}

	public Ball() {
		this(50, 50, 100, 100, 0, 0, 10, 10);
	}
	public void move(Vector push) {

		//Acceleration vector x and y components
		double newVelocityXComponent = (push.xComponent / this.mass);
		double newVelocityYComponent = (push.yComponent / this.mass);

		double magnitude = Math.sqrt(Math.pow(newVelocityXComponent, 2) + Math.pow(newVelocityYComponent, 2));
		double direction = Math.toDegrees(Math.atan(newVelocityYComponent / newVelocityXComponent));


		//ONCE AGAIN THE MAGNITUDE IS IN M/S^2 NOT NEWTONS
		Vector newVelocity = new Vector(((push.magnitude < 0) ? -magnitude : magnitude) * 0.005, direction);


		velocity.add(newVelocity);

		this.x = (this.x + (velocity.xComponent / scale));
		this.y = (this.y + (velocity.yComponent / scale));

		this.ball.setCenterX(this.x);
		this.ball.setCenterY(this.y);

		/*this.velocityVector.setStartX(this.x);
		this.velocityVector.setStartY(this.y);
		this.velocityVector.setEndX(this.x + velocity.xComponent);
		this.velocityVector.setEndY(this.y + velocity.yComponent);*/
	}

	public static void populateArray(Ball[] arr) {

		//Chunk of code to find where the first null value;
		int startIndex;
		int i = arr.length / 2;
		if(arr[i] == null) {
			while(arr[i] == null) {
				i--;
			}
		} else {
			while(arr[i] != null) {
				i++;
			}
		}
		startIndex = i;

		for(int j = startIndex; j < arr.length; j++) {
			new Ball();
		}

	}

	public void setVelocity(Vector velocity) {
		this.velocity = velocity;
	}

	public Circle getBall() {
		return ball;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public Vector getVelocity() { return velocity; }

	public double getMass() {return this.mass;}

	public void setScale(double scale) {this.scale = scale;}
}

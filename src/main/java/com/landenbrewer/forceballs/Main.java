package com.landenbrewer.forceballs;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
	@Override
	public void start(Stage stage) {
		Pane mainPane = new Pane();


		//Define some variables to set the scene.
		final double[] goToLocation = {400.0, 400.0};

		//Whether the circular orbit paths are shown.
		boolean circles = false;

		//The y value for the outermost circle
		double startY = 10;
		//The y value for the innermost circle.
		double endY = 390;

		//The amount of balls equally spaced from the start to then end
		int ballCount = 26;


		//Array holding all the balls created.
		Ball[] balls = new Ball[ballCount];

		//The spacing of each of the balls radii
		double radiusDecrement = (endY - startY) / ballCount;

		//Loop creating every ball and setting the speed required for each.
		for (int i = 0; i < balls.length; i++) {
			//The radius of the ball
			double radius = startY + (i * radiusDecrement);

			//Create a new ball, starting in the center, x-wise, and a varying y value. Has a radius of 3 and a mass of 10
			Ball ball = new Ball(400, radius, 800, 800, 0, 0, 3, 10);

			//Set the velocity to the SQRT(6.67e-11 * 5.98e24 / 1000 * distance from center) * 0.005. Has a direction of 0 (or 180 sometimes)
			ball.setVelocity(new Vector(Math.sqrt((Vector.BIG_G * Vector.EARTH_MASS) / ((1000 * (goToLocation[1] - radius)))) * 0.005, 0));
			ball.setScale(1000);
			//Create the paths for each of the circles if the circles variable is true
			if (circles) {
				Circle path = new Circle(400, 400, (goToLocation[1] - radius));
				path.setFill(Color.TRANSPARENT);
				path.setStroke(Color.DODGERBLUE);
				mainPane.getChildren().add(path);
			}

			//Add the created ball to the total collection of balls.
			balls[i] = ball;

			//Add the ball to the display.
			mainPane.getChildren().add(ball);
		}

		Circle forceCircle = new Circle(400, 400,3);
		mainPane.getChildren().add(forceCircle);

		/*Ball ballo = new Ball(400, 200, 800, 800, 0, 0, 10, 10);
		ballo.setVelocity(new Vector(Math.sqrt((Vector.BIG_G * Vector.EARTH_MASS) / ((1000 * 200))) * 0.005, 0));
		Circle path = new Circle(400, 400, 200);
		path.setFill(Color.TRANSPARENT);
		path.setStroke(Color.DODGERBLUE);
		mainPane.getChildren().addAll(forceCircle, ballo, path);*/


		/*

		Ball smallRadius = new Ball(400, 300, 800, 800, 0, 0, 10, 10);

		ball.setVelocity(new Vector(Math.sqrt((Vector.BIG_G * Vector.EARTH_MASS) / ((800 * 200))) * 0.005, 0));
		smallRadius.setVelocity(new Vector(Math.sqrt((Vector.BIG_G * Vector.EARTH_MASS) / ((800 * 100))) * 0.005, 0));

		Text text = new Text(10, 10, "");

		Line testLine = new Line();
		Circle path = new Circle(400, 400, 200);
		Circle path2 = new Circle(400, 400, 100);
		path.setFill(Color.TRANSPARENT);
		path.setStroke(Color.DODGERBLUE);
		path2.setFill(Color.TRANSPARENT);
		path2.setStroke(Color.DODGERBLUE);
		mainPane.getChildren().addAll(ball, smallRadius, text, forceCircle, testLine, path, path2);
		*/


		/*testLine.setStartX(400);
		testLine.setStartY(400);

		testLine.setEndX(ball.getX());
		testLine.setEndY(ball.getY());*/



		Timeline animation = new Timeline(new KeyFrame(Duration.millis(5), e ->  {
			for (Ball ball : balls) {
				double magnitude = Math.sqrt(Math.pow(goToLocation[0] - ball.getX(), 2) + Math.pow(goToLocation[1] - ball.getY(), 2));
				double direction = Math.atan((goToLocation[1] - ball.getY()) / (goToLocation[0] - ball.getX()));

				//Gravity in meters per second. Needs to be converted to meters per 5 milliseconds for display reasons.
				double gravity = ball.getMass() * Vector.gravity(Vector.EARTH_MASS, (magnitude * 1000)); //Starts at 200, total distance of 160000

				//System.out.println(gravity);

				ball.move(new Vector(((goToLocation[0] - ball.getX() >= 0) ? gravity : -gravity) * 0.005, Math.toDegrees(direction))); //NEEDS TO BE IN DEGREES
			}


			/*
			double magnitude = Math.sqrt(Math.pow(goToLocation[0] - ball.getX(), 2) + Math.pow(goToLocation[1] - ball.getY(), 2));
			double smallRadiusMag = Math.sqrt(Math.pow(goToLocation[0] - smallRadius.getX(), 2) + Math.pow(goToLocation[1] - smallRadius.getY(), 2));

			double direction = Math.atan((goToLocation[1] - ball.getY()) / (goToLocation[0] - ball.getX()));
			double smallRadiusDir = Math.atan((goToLocation[1] - smallRadius.getY()) / (goToLocation[0] - smallRadius.getX()));

			text.setText("Distance: " + Math.floor(magnitude * 800) / 1000 + " Kilometers\n" + "Ball Velocity: " + ball.getVelocity().magnitude / 0.005);

			//Gravity in meters per second. Needs to be converted to meters per 5 milliseconds for display reasons.
			double gravity = Vector.gravity(ball.getMass() * Vector.EARTH_MASS, (magnitude * 800)); //Starts at 200, total distance of 160000
			double smallRadiusGrav = Vector.gravity(smallRadius.getMass() * Vector.EARTH_MASS, (smallRadiusMag * 800)); //Starts at 200, total distance of 160000

			ball.move(new Vector(((goToLocation[0] - ball.getX() > 0) ? gravity : -gravity ) * 0.005, Math.toDegrees(direction))); //NEEDS TO BE IN DEGREES
			smallRadius.move(new Vector(((goToLocation[0] - smallRadius.getX() > 0) ? smallRadiusGrav : -smallRadiusGrav ) * 0.005, Math.toDegrees(smallRadiusDir))); //NEEDS TO BE IN DEGREES
			*/

			//THIS VECTOR IS GIVEN IN NEWTONS
			//9.8 meters per seconds times mass of object. mg
			//ball.move(new Vector(98 * 0.025, 90));
		}));
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.play();

		Scene scene = new Scene(mainPane, 800, 800);

		scene.setOnMouseClicked(e -> {
			goToLocation[0] = e.getX();
			goToLocation[1] = e.getY();

			forceCircle.setCenterX(goToLocation[0]);
			forceCircle.setCenterY(goToLocation[1]);

				/*testLine.setStartX(goToLocation[0]);
				testLine.setStartY(goToLocation[1]);*/
		});

		stage.setTitle("Physics!");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch();
	}
}
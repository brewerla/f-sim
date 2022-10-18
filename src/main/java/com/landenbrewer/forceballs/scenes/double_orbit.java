package com.landenbrewer.forceballs.scenes;

import com.landenbrewer.forceballs.Ball;
import com.landenbrewer.forceballs.Vector;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class double_orbit extends Application {
	@Override
	public void start(Stage stage) {
		Pane mainPane = new Pane();

		Circle forceCircle = new Circle(400, 400,3);


		boolean circle = true;
		double radius = 30;
		double satOrbitRadius = radius - 25;
		double scale = 1000;

		final double[] goToLocation = {400, 400.0};

		Ball ball = new Ball(radius, 400, 800, 800, 0, 0, 5, Vector.MOON_MASS);
		ball.setVelocity(new Vector(Math.sqrt((Vector.BIG_G * Vector.EARTH_MASS) / (scale * (goToLocation[1] - radius))) * 0.005, -90));

		//Create satellite
		Ball satellite = new Ball(satOrbitRadius, 400, 800, 800, 0, 0, 3, 300);

		double satToBall =  Math.sqrt(Math.pow(ball.getX() - satellite.getX(), 2) + Math.pow(ball.getY() - satellite.getY(), 2));

		satellite.setVelocity(new Vector(Math.sqrt((Vector.BIG_G * ball.getMass()) / (scale * satToBall)) * 0.005, -90)
				.add(new Vector(Math.sqrt((Vector.BIG_G * Vector.EARTH_MASS) / (scale * (goToLocation[1] - satOrbitRadius))) * 0.005, -90)));
		//satellite.setVelocity(new Vector(Math.sqrt((Vector.BIG_G * Vector.EARTH_MASS) / (scale * (goToLocation[1] - 175))) * 0.005, -90));


		ball.setScale(scale);
		satellite.setScale(scale);

		Circle path = new Circle(400, 400, (goToLocation[1] - radius));
		path.setFill(Color.TRANSPARENT);
		path.setStroke(Color.DODGERBLUE);

		mainPane.getChildren().addAll(ball, satellite, forceCircle, path);


		Timeline animation = new Timeline(new KeyFrame(Duration.millis(5), e ->  {
			double ballMagnitude = Math.sqrt(Math.pow(goToLocation[0] - ball.getX(), 2) + Math.pow(goToLocation[1] - ball.getY(), 2));
			double ballDirection = Math.atan((goToLocation[1] - ball.getY()) / (goToLocation[0] - ball.getX()));


			//Gravity in meters per second. Needs to be converted to meters per 5 milliseconds for display reasons.
			double ballGravity = ball.getMass() * Vector.gravity(Vector.EARTH_MASS, (ballMagnitude * scale)); //Starts at 200, total distance of 160000

			//System.out.println(gravity);
			ball.move(new Vector(((goToLocation[0] - ball.getX() > 0) ? ballGravity : -ballGravity) * 0.005, Math.toDegrees(ballDirection))); //NEEDS TO BE IN DEGREES

			//ABSOLUTELY NEEDS TO BE ATTRACTED TO THE CENTER AS WELL.
			double satToBallMag =  Math.sqrt(Math.pow(ball.getX() - satellite.getX(), 2) + Math.pow(ball.getY() - satellite.getY(), 2));

			double satToCenterMag = Math.sqrt(Math.pow(goToLocation[0] - satellite.getX(), 2) + Math.pow(goToLocation[1] - satellite.getY(), 2));

			double satToCenterDir = Math.atan((goToLocation[1] - satellite.getY()) / (goToLocation[0] - satellite.getX()));

			double satToBallDir = Math.atan((ball.getY() - satellite.getY()) / (ball.getX() - satellite.getX()));
			double satGravity = satellite.getMass() * Vector.gravity(Vector.MOON_MASS, (satToBallMag * scale));

			double satGravityOfCenter =  satellite.getMass() * Vector.gravity(Vector.EARTH_MASS, (satToCenterMag * scale));

			satellite.move(new Vector(((ball.getX() - satellite.getX() > 0) ? satGravity : -satGravity) * 0.005, Math.toDegrees(satToBallDir))
					.add(new Vector(((goToLocation[0] - satellite.getX() > 0) ? satGravityOfCenter : -satGravityOfCenter) * 0.005, Math.toDegrees(satToCenterDir))));

			//satellite.move(new Vector(((goToLocation[0] - satellite.getX() > 0) ? satGravityOfCenter : -satGravityOfCenter) * 0.005, Math.toDegrees(satToCenterDir)));
		}));

		animation.setCycleCount(Timeline.INDEFINITE);
		animation.play();

		Scene scene = new Scene(mainPane, 800, 800);
		stage.setTitle("Physics!");
		stage.setScene(scene);
		stage.show();
	}
}

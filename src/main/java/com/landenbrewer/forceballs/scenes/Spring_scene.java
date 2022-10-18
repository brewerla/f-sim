package com.landenbrewer.forceballs.scenes;

import com.landenbrewer.forceballs.Ball;
import com.landenbrewer.forceballs.Vector;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;


/*
Attempt to make a spring using a ball.

The actual spring class will possibly be a connector, connecting two things and holding the spring values.
Possibly be used to make a soft body object at some point.

*POST WORKING VERSION*
It works. The ball class is just the application of forces.
If it moves using forces it can work.

 */
public class Spring_scene extends Application {
	@Override
	public void start(Stage stage) {
		Pane mainPane = new Pane();


		double restingLength = 200;

		double springStartX = 200;
		double springStartY = 400;

		double dampingFactor = 7500;
		double k = 10; //Spring Constant.


		Ball springEnd = new Ball(200 + 100, 400, 0, 0, 800, 800, 10, 10);


		springEnd.setScale(0.001);

		Line line = new Line();

		mainPane.getChildren().addAll(springEnd, new Circle(springStartX, springStartY, 3), line);


		Timeline animation = new Timeline(new KeyFrame(Duration.millis(5), e ->  {

			double currentLength = Math.sqrt(Math.pow(springStartX - springEnd.getX(), 2) + Math.pow(springStartY - springEnd.getY(), 2));
			double compressionDistance = currentLength - restingLength;


			//System.out.println(compressionDistance);

			double directionToStart = Math.atan((springStartY - springEnd.getY()) / (springStartX - springEnd.getX()));

			Vector unitVector = new Vector(1, Math.toDegrees(directionToStart));

			Vector testVector = new Vector((springStartX - springEnd.getX() >= 0) ? currentLength : -currentLength, Math.toDegrees(directionToStart));

			line.setStartX(springEnd.getX());
			line.setStartY(springEnd.getY());
			line.setEndX(springEnd.getX() + testVector.xComponent);
			line.setEndY(springEnd.getY() + testVector.yComponent);

			//Subtracting a vector is adding the opposite
			Vector velocityDifference = new Vector(0, 0)
					.add(new Vector(-springEnd.getVelocity().magnitude, springEnd.getVelocity().direction));

			double dampingForce = unitVector.dot(velocityDifference) * dampingFactor;

			//The force in Newtons
			double force = k * compressionDistance;  //Hooke's law
			//System.out.println(force);

			double totalSpringForce = force + -dampingForce; //MUST CHANGE

			Vector Fs = new Vector(((springStartX - springEnd.getX() >= 0) ? totalSpringForce : -totalSpringForce) * 0.005, Math.toDegrees(directionToStart));


			springEnd.move(Fs);


			if(totalSpringForce < 0.005 && totalSpringForce > -0.005) {
				springEnd.move(new Vector(50, Math.toDegrees(directionToStart)));
			}

			//System.out.println(springEnd.getX() + " " + springEnd.getY());
		}));

		animation.setCycleCount(Timeline.INDEFINITE);
		animation.play();

		Scene scene = new Scene(mainPane, 800, 800);
		stage.setTitle("Physics!");
		stage.setScene(scene);
		stage.show();
	}
}

package com.group3.cruisebookingsystem;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Main{

    private CruiseBookingSystem cbs;

    public Main(CruiseBookingSystem cbs) {
        this.cbs = cbs;
    }

    public Scene mainScene()  {

        BorderPane borderPane = new BorderPane();

        // Load background image
        Image image = new Image(CruiseBookingSystem.class.getResource("mainbg.png").toString(), 1039,694,false,true);
        BackgroundImage bI = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);

        borderPane.setBackground(new Background(bI));

        // Set center content to the login view
        borderPane.setCenter(CenterLogin());

        return new Scene(borderPane, 1039, 694);
    }

    public BorderPane CenterLogin(){

        // App logo
        Image logoImage = new Image(CruiseBookingSystem.class.getResource("logo.png").toString(), 1039,694,false,true);
        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.setFitWidth(300);
        logoImageView.setFitHeight(150);

        // Load the downloaded font
        Font customFont = Font.loadFont(getClass().getResourceAsStream("Lato-Regular.ttf"), 18);

        //--------------------------------------------------------------------------------

        // Create and style sign in button
        Button signInButton = new Button("Login");

        // Create a DropShadow effect for the button
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.DARKGRAY);
        dropShadow.setRadius(5);
        dropShadow.setOffsetX(2);
        dropShadow.setOffsetY(2);
        signInButton.setEffect(dropShadow);

        signInButton.setStyle("-fx-background-color: rgba(221,221,221, 0.3); -fx-background-radius: 25px; -fx-padding: 10px 20px;-fx-text-fill: #171718; -fx-font-size: 20px;-fx-font-family: '" + customFont.getName() + "';-fx-font-weight: bold;");

        signInButton.setPrefSize(150, 50);

        signInButton.setOnMouseEntered(event -> {
            signInButton.setStyle("-fx-background-color: #60442a; -fx-border-color: transparent; -fx-background-radius: 25px;-fx-text-fill: white; -fx-padding: 10px 20px; -fx-font-size: 20px;-fx-font-family: '" + customFont.getName() + "';-fx-font-weight: bold;");
        });

        signInButton.setOnMouseExited(event -> {
            signInButton.setStyle("-fx-background-color: rgba(221, 221, 221, 0.3); -fx-border-color: transparent; -fx-background-radius: 25px;-fx-text-fill: #171718; -fx-padding: 10px 20px; -fx-font-size: 20px;-fx-font-family: '" + customFont.getName() + "';-fx-font-weight: bold;");
        });

        signInButton.setOnAction(event -> {
            cbs.switchToLoginScene();
        });

        //--------------------------------------------------------------------------------

        //Create and style sign in button
        Button signUpButton = new Button("Register");
        signUpButton.setStyle("-fx-background-color: rgba(221,221,221, 0.3); -fx-background-radius: 25px; -fx-padding: 10px 20px;-fx-text-fill: #171718; -fx-font-size: 20px;-fx-font-family: '" + customFont.getName() + "';-fx-font-weight: bold;");

        signUpButton.setEffect(dropShadow);

        signUpButton.setPrefSize(150, 50);

        //Sign Up Button Event
        signUpButton.setOnMouseEntered(event -> {
            signUpButton.setStyle("-fx-background-color: #60442a; -fx-border-color: transparent; -fx-background-radius: 25px;-fx-text-fill: white; -fx-padding: 10px 20px; -fx-font-size: 20px;-fx-font-family: '" + customFont.getName() + "';-fx-font-weight: bold;");
        });

        signUpButton.setOnMouseExited(event -> {
            signUpButton.setStyle("-fx-background-color: rgba(221, 221, 221, 0.3); -fx-border-color: transparent; -fx-background-radius: 25px;-fx-text-fill: #171718; -fx-padding: 10px 20px; -fx-font-size: 20px;-fx-font-family: '" + customFont.getName() + "'; -fx-font-weight: bold;");
        });

        signUpButton.setOnAction(event -> {
            cbs.switchToRegisterScene();
        });

        //-----------------------------------------------------------------------------

        //Create a vertical layout for logo and buttons
        VBox vBox = new VBox(logoImageView, signInButton, signUpButton);
        VBox.setMargin(logoImageView, new Insets(100,0,50,0));
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setSpacing(30);

        // Set the vertical layout in the center of the border pane

        BorderPane borderPane = new BorderPane(vBox);

        return borderPane;
    }
}

package com.group3.cruisebookingsystem;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CruiseBookingSystem extends Application {
    private Stage stage;
    private Main main;
    private Login login;
    private Register register;
    private Home home;
    private Search search;
    private Details detail;
    private Booking booking;
    private Book1 book1;
    private RoomType roomType;
    private AdminPage adminPage;
    private Deck deck;
    private Place1 place1;
    private Payment payment;
    @Override
    public void start(Stage stage) throws IOException {

        this.stage = stage;
        main = new Main(this);
        login = new Login(this);
        register = new Register(this);
        home = new Home(this, login);
        search = new Search(this, login);
        detail = new Details(this, search, login);
        booking = new Booking(this, search, login);
        book1 = new Book1(this, booking, search, login);
        roomType = new RoomType(this, booking, book1, search, login);
        place1 = new Place1(this, booking, book1, search, roomType, login);
        deck = new Deck(this, booking, book1, search, roomType, place1, login);
        payment = new Payment(this, booking, book1, search, roomType, place1, deck, login);
        adminPage = new AdminPage(this, login);
        stage.setTitle("CruiseBooker");
        switchToMainScene();
        stage.setResizable(false);
        stage.show();
    }

    public void switchToMainScene() {
        Scene scene = main.mainScene();
        stage.setScene(scene);
    }

    public void switchToLoginScene() {
        Scene scene = login.loginScene();
        stage.setScene(scene);
    }

    public void switchToRegisterScene() {
        Scene scene = register.registerScene();
        stage.setScene(scene);
    }

    public void switchToHomeScene() {
        Scene scene = home.homeScene();
        stage.setScene(scene);
    }

    public void switchToSearchScene() {
        Scene scene = search.searchScene();
        stage.setScene(scene);
    }

    public void switchToDetailScene() {
        Scene scene = detail.detailScene();
        stage.setScene(scene);
    }

    public void switchToBookingScene() {
        Scene scene = booking.bookingScene();
        stage.setScene(scene);
    }

    public void switchToBook1Scene() {
        Scene scene = book1.book1Scene();
        stage.setScene(scene);
    }

    public void switchToRoomTypeScene() {
        Scene scene = roomType.roomTypeScene();
        stage.setScene(scene);
    }

    public void switchToDeckScene() {
        Scene scene = deck.deckScene();
        stage.setScene(scene);
    }

    public void switchToPlace1Scene() {
        Scene scene = place1.place1Scene();
        stage.setScene(scene);
    }

    public void switchToPaymentScene() {
        Scene scene = payment.paymentScene();
        stage.setScene(scene);
    }

    public void switchToAdminPageScene() {
        Scene scene = adminPage.adminPageScene();
        stage.setScene(scene);
    }



    public static void main(String[] args) {
        launch();
    }
}
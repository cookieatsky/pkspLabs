package com.javatunes.gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PieChartApp extends Application {

    private PieChart pieChart;
    private List<PieChart.Data> previousData = new ArrayList<>(); // Список для хранения предыдущего состояния данных

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Диаграмма услуг СПА Салона");

        pieChart = new PieChart();
        loadChartData(); // Инициализация данных при запуске

        // Таймер для периодического обновления диаграммы
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), e -> loadChartData())); // Обновление каждые 5 секунд
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        StackPane root = new StackPane();
        root.getChildren().add(pieChart);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void loadChartData() {
        List<PieChart.Data> newData = new ArrayList<>(); // Новый список для хранения данных

        String jdbcUrl = "jdbc:derby://localhost:1527/JavaTunesDB"; // URL вашей базы данных
        String username = "guest"; // Ваше имя пользователя БД
        String password = "password"; // Ваш пароль для БД

        String query = "SELECT name, cost FROM Service"; // SQL-запрос для получения данных об услугах

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                double cost = resultSet.getDouble("cost");
                newData.add(new PieChart.Data(name, cost));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Проверяем, изменились ли данные
        if (!isDataEqual(previousData, newData)) {
            pieChart.getData().clear(); // Очищаем старые данные
            pieChart.getData().addAll(newData); // Добавляем новые данные
            previousData = newData; // Обновляем предыдущие данные
        }
    }

    // Метод для сравнения старого и нового списка данных
    private boolean isDataEqual(List<PieChart.Data> oldData, List<PieChart.Data> newData) {
        if (oldData.size() != newData.size()) {
            return false;
        }

        for (int i = 0; i < oldData.size(); i++) {
            PieChart.Data oldDatum = oldData.get(i);
            PieChart.Data newDatum = newData.get(i);

            if (!oldDatum.getName().equals(newDatum.getName()) || oldDatum.getPieValue() != newDatum.getPieValue()) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
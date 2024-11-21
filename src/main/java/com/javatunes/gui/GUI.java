package com.javatunes.gui;

import com.javatunes.domain.ServiceCategory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class GUI extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField nameField, costField, durationField;
    private JComboBox<ServiceCategory> categoryComboBox;  // Комбобокс для выбора категории

    public GUI() {
        setTitle("Данные СПА Салона");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Инициализация таблицы
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Название услуги");
        tableModel.addColumn("Категория");
        tableModel.addColumn("Цена");
        tableModel.addColumn("Время в минутах");

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Панель для управления (добавление/удаление услуг)
        JPanel controlPanel = new JPanel(new GridLayout(2, 1));

        // Панель для создания новой услуги
        JPanel createPanel = new JPanel(new GridLayout(5, 2));
        createPanel.add(new JLabel("Название услуги:"));
        nameField = new JTextField();
        createPanel.add(nameField);

        createPanel.add(new JLabel("Категория:"));
        categoryComboBox = new JComboBox<>(ServiceCategory.values());  // Инициализация выпадающего списка
        createPanel.add(categoryComboBox);

        createPanel.add(new JLabel("Цена:"));
        costField = new JTextField();
        createPanel.add(costField);

        createPanel.add(new JLabel("Время (минуты):"));
        durationField = new JTextField();
        createPanel.add(durationField);

        JButton createButton = new JButton("Создать услугу");
        createButton.addActionListener(this::createService);
        createPanel.add(createButton);

        controlPanel.add(createPanel);

        // Панель для удаления услуги
        JButton deleteButton = new JButton("Удалить услугу");
        deleteButton.addActionListener(this::deleteService);
        controlPanel.add(deleteButton);

        add(controlPanel, BorderLayout.SOUTH);

        // Загрузка данных из базы данных
        loadDataFromDatabase();

        setVisible(true);
    }

    private void loadDataFromDatabase() {
        String jdbcUrl = "jdbc:derby://localhost:1527/JavaTunesDB";  // URL вашей базы данных
        String username = "guest";  // Ваше имя пользователя БД
        String password = "password";  // Ваш пароль для БД

        String query = "SELECT id, name, category, cost, duration FROM Service";  // SQL-запрос для получения данных об услугах

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            // Очищаем таблицу перед загрузкой новых данных
            tableModel.setRowCount(0);

            // Перебор данных из ResultSet и добавление их в таблицу
            while (resultSet.next()) {
                Object[] row = {
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        ServiceCategory.valueOf(resultSet.getString("category")).getDescription(),  // Преобразуем строку обратно в enum
                        resultSet.getDouble("cost"),
                        resultSet.getInt("duration")
                };
                tableModel.addRow(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Ошибка загрузки данных из базы данных: " + e.getMessage());
        }
    }

    // Метод для создания новой услуги
    private void createService(ActionEvent event) {
        String jdbcUrl = "jdbc:derby://localhost:1527/JavaTunesDB";
        String username = "guest";
        String password = "password";

        String name = nameField.getText();
        ServiceCategory category = (ServiceCategory) categoryComboBox.getSelectedItem();  // Получаем выбранную категорию
        double cost;
        int duration;

        try {
            cost = Double.parseDouble(costField.getText());
            duration = Integer.parseInt(durationField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Введите корректные значения для цены и времени.");
            return;
        }

        String insertQuery = "INSERT INTO Service (name, category, cost, duration) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             PreparedStatement statement = connection.prepareStatement(insertQuery)) {

            statement.setString(1, name);
            statement.setString(2, category.name());  // Сохраняем имя enum в базу данных
            statement.setDouble(3, cost);
            statement.setInt(4, duration);
            statement.executeUpdate();

            JOptionPane.showMessageDialog(this, "Услуга успешно добавлена!");

            // Очищаем поля после добавления
            nameField.setText("");
            costField.setText("");
            durationField.setText("");

            // Обновляем таблицу
            loadDataFromDatabase();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Ошибка добавления услуги: " + e.getMessage());
        }
    }

    // Метод для удаления выбранной услуги
    private void deleteService(ActionEvent event) {
        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Выберите услугу для удаления.");
            return;
        }

        int serviceId = (int) tableModel.getValueAt(selectedRow, 0);
        String jdbcUrl = "jdbc:derby://localhost:1527/JavaTunesDB";
        String username = "guest";
        String password = "password";

        String deleteQuery = "DELETE FROM Service WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             PreparedStatement statement = connection.prepareStatement(deleteQuery)) {

            statement.setInt(1, serviceId);
            statement.executeUpdate();

            JOptionPane.showMessageDialog(this, "Услуга успешно удалена!");

            // Обновляем таблицу
            loadDataFromDatabase();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Ошибка удаления услуги: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUI::new);
    }
}
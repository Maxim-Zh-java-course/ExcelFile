package ABPExcel;

import javax.swing.*;
import java.awt.*;

public class FormCreator {

    public static JFrame createForm() {
        // Создание основного окна
        JFrame frame = new JFrame("Время работы с проектом / Time spent working on the project");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLayout(new BorderLayout());

        // Добавление логотипа
        ImageIcon logoIcon = new ImageIcon("Logo.png"); // Укажите путь к логотипу
        Image scaledImage = logoIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH); // Масштабирование
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel logoLabel = new JLabel(scaledIcon);
        logoLabel.setHorizontalAlignment(SwingConstants.LEFT);

        // Панель для логотипа с отступом сверху
        JPanel logoPanel = new JPanel(new BorderLayout());
        logoPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0)); // Отступ: сверху 20px
        logoPanel.add(logoLabel, BorderLayout.WEST);

        // Основная панель с отступами
        JPanel mainPanel = new JPanel(new GridLayout(6, 4));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Отступы

        // Поля ввода данных
        JLabel idLabel = new JLabel("Employee / Работник / :");
        JTextField idField = new JTextField();
        JLabel projectLabel = new JLabel("Project / Проект / :");
        JTextField projectField = new JTextField();

        // Добавление компонентов на основную панель
        mainPanel.add(idLabel);
        mainPanel.add(idField);
        mainPanel.add(projectLabel);
        mainPanel.add(projectField);
        mainPanel.add(new JLabel()); // пустое пространство

        // Добавление логотипа и основной панели в окно
        frame.add(logoLabel, BorderLayout.BEFORE_FIRST_LINE);
        frame.add(mainPanel, BorderLayout.CENTER);

        return frame;
    }

}

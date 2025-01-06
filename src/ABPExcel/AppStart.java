package ABPExcel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AppStart {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Время работы с проектом / Time spent working on the project");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 300);
            frame.setLayout(new GridLayout(6, 4));

            // Поля ввода данных
            JLabel idLabel = new JLabel("Employee / Работник / :");
            JTextField idField = new JTextField();
            JLabel projectLabel = new JLabel("Project / Проект / :");
            JTextField projectField = new JTextField();

            // Добавление компонентов на форму
            frame.add(idLabel);
            frame.add(idField);
            frame.add(projectLabel);
            frame.add(projectField);
            frame.add(new JLabel()); // пустое пространство

            // Обработчик для автоматического перехода на поле "Проект"
            idField.addActionListener(e -> projectField.requestFocus());

            // Обработчик для автоматической записи в файл после ввода проекта
            projectField.addActionListener(e -> {
                String id = idField.getText();
                String project = projectField.getText();

                if (id.isEmpty() || project.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    saveToExcel(id, project);
                    idField.setText(""); // Очищаем поле "Работник"
                    projectField.setText(""); // Очищаем поле "Проект"
                    idField.requestFocus(); // Переводим фокус обратно на поле "Работник"
                }
            });

            frame.setVisible(true);
        });
    }

    private static void saveToExcel(String id, String project) {
        try (FileInputStream fileInputStream = new FileInputStream("EmployeesDataABP.xlsx");
             Workbook workbook = new XSSFWorkbook(fileInputStream)) {

            // Проверяем существование ID
            if (!doesIdExist(id, workbook)) {
                JOptionPane.showMessageDialog(null, "Работника не существует!", "Ошибка", JOptionPane.WARNING_MESSAGE);
                return; // Прерываем выполнение метода для возвращения к вводу
            }

            // Открываем лист для записи данных
            Sheet sheet = workbook.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();
            Row dataRow = sheet.createRow(lastRowNum + 1);

            // Записываем данные
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

            dataRow.createCell(0).setCellValue(id);
            dataRow.createCell(1).setCellValue("Null");
            dataRow.createCell(2).setCellValue(project);
            dataRow.createCell(3).setCellValue(currentDateTime.format(formatter));
            dataRow.createCell(4).setCellValue("Null");

            // Сохраняем файл
            try (FileOutputStream fileOut = new FileOutputStream("EmployeesDataABP.xlsx")) {
                workbook.write(fileOut);
            }

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Ошибка при сохранении файла: " + ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static boolean doesIdExist(String id, Workbook workbook) {
        // Открываем лист для проверки ID
        Sheet sheet = workbook.getSheetAt(1); // Предполагаем, что проверка на втором листе

        for (Row row : sheet) {
            Cell cell = row.getCell(0); // ID находится в колонке 0
            if (cell != null && cell.getCellType() == CellType.STRING && cell.getStringCellValue().equals(id)) {
                return true; // ID найден
            }
        }
        return false; // ID не найден
    }



}

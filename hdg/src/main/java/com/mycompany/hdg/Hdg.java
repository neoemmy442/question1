package com.mycompany.hdg;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Hdg {

    static class Student {
        String name, id, faculty, title, contact, email, imagePath;

        public Student(String name, String id, String faculty, String title,
                       String contact, String email, String imagePath) {
            this.name = name;
            this.id = id;
            this.faculty = faculty;
            this.title = title;
            this.contact = contact;
            this.email = email;
            this.imagePath = imagePath;
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Registration App");
        frame.setSize(500, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        CardLayout layout = new CardLayout();
        JPanel mainPanel = new JPanel(layout);

        JPanel homePage = createMainPage(layout, mainPanel);
        JPanel formPage = createFormPage(layout, mainPanel);
        JPanel confirmPage = createConfirmPage(layout, mainPanel);
        JPanel searchPage = createSearchPage(layout, mainPanel, new HashMap<>());

        mainPanel.add(homePage, "HomePage");
        mainPanel.add(formPage, "FormPage");
        mainPanel.add(confirmPage, "ConfirmPage");
        mainPanel.add(searchPage, "SearchPage");

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private static JPanel createMainPage(CardLayout layout, JPanel mainPanel) {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);

        JLabel welcomeLabel = new JLabel("Welcome to the Registration App");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        welcomeLabel.setBounds(100, 100, 300, 30);
        panel.add(welcomeLabel);

        JButton startButton = new JButton("Start Registration");
        startButton.setBounds(160, 180, 160, 40);
        panel.add(startButton);

        JButton searchButton = new JButton("Search Student");
        searchButton.setBounds(160, 240, 160, 40);
        panel.add(searchButton);

        startButton.addActionListener(e -> layout.show(mainPanel, "FormPage"));
        searchButton.addActionListener(e -> layout.show(mainPanel, "SearchPage"));

        return panel;
    }

    private static JPanel createFormPage(CardLayout layout, JPanel mainPanel) {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.LIGHT_GRAY);

        JLabel name = new JLabel("Student Name:");
        name.setBounds(50, 20, 100, 25);
        JTextField namebox = new JTextField();
        namebox.setBounds(180, 20, 200, 25);

        JLabel id = new JLabel("Registration ID:");
        id.setBounds(50, 55, 100, 25);
        JTextField idbox = new JTextField();
        idbox.setBounds(180, 55, 200, 25);

        JLabel faculty = new JLabel("Faculty:");
        faculty.setBounds(50, 90, 100, 25);
        JTextField fbox = new JTextField();
        fbox.setBounds(180, 90, 200, 25);

        JLabel title = new JLabel("Project Title:");
        title.setBounds(50, 125, 100, 25);
        JTextField tbox = new JTextField();
        tbox.setBounds(180, 125, 200, 25);

        JLabel contact = new JLabel("Contact Number:");
        contact.setBounds(50, 160, 120, 25);
        JTextField cbox = new JTextField();
        cbox.setBounds(180, 160, 200, 25);

        JLabel email = new JLabel("Email Address:");
        email.setBounds(50, 195, 100, 25);
        JTextField ebox = new JTextField();
        ebox.setBounds(180, 195, 200, 25);

        JLabel imageLabel = new JLabel("No Image");
        imageLabel.setBounds(80, 220, 80, 100);
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JButton uploadBtn = new JButton("Upload Image");
        uploadBtn.setBounds(60, 330, 130, 25);

        JButton next = new JButton("Next");
        next.setBounds(290, 360, 90, 30);
        JButton back = new JButton("Back");
        back.setBounds(100, 360, 90, 30);

        panel.add(name); panel.add(namebox);
        panel.add(id); panel.add(idbox);
        panel.add(faculty); panel.add(fbox);
        panel.add(title); panel.add(tbox);
        panel.add(contact); panel.add(cbox);
        panel.add(email); panel.add(ebox);
        panel.add(uploadBtn); panel.add(imageLabel);
        panel.add(next); panel.add(back);

        final String[] imagePath = {null};
        uploadBtn.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            int result = chooser.showOpenDialog(panel);
            if (result == JFileChooser.APPROVE_OPTION) {
                imagePath[0] = chooser.getSelectedFile().getAbsolutePath();
                ImageIcon icon = new ImageIcon(new ImageIcon(imagePath[0]).getImage().getScaledInstance(80, 100, Image.SCALE_SMOOTH));
                imageLabel.setIcon(icon);
                imageLabel.setText("");
            }
        });

        next.addActionListener(e -> {
            // Field validation
            if (namebox.getText().trim().isEmpty() || idbox.getText().trim().isEmpty() ||
                fbox.getText().trim().isEmpty() || tbox.getText().trim().isEmpty() ||
                cbox.getText().trim().isEmpty() || ebox.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(panel, "All fields must be filled out.");
                return;
            }
            // Email validation
            String emailText = ebox.getText().trim();
            if (!emailText.matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$")) {
                JOptionPane.showMessageDialog(panel, "Invalid email format.");
                return;
            }
            Student student = new Student(
                namebox.getText(), idbox.getText(), fbox.getText(),
                tbox.getText(), cbox.getText(), emailText, imagePath[0]
            );
            DBHelper.insertOrUpdateStudent(student);
            panel.putClientProperty("student", student);
            layout.show(mainPanel, "ConfirmPage");
        });

        back.addActionListener(e -> layout.show(mainPanel, "HomePage"));

        return panel;
    }

    private static JPanel createConfirmPage(CardLayout layout, JPanel mainPanel) {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.PINK);

        JLabel message = new JLabel("Review your details:");
        message.setFont(new Font("Arial", Font.BOLD, 14));
        message.setBounds(160, 20, 200, 30);
        panel.add(message);

        JTextArea summary = new JTextArea();
        summary.setBounds(50, 60, 380, 150);
        summary.setEditable(false);
        summary.setFont(new Font("Monospaced", Font.PLAIN, 12));
        panel.add(summary);

        JButton submit = new JButton("Submit");
        submit.setBounds(250, 230, 100, 30);

        JButton back = new JButton("Back");
        back.setBounds(130, 230, 100, 30);

        panel.add(submit);
        panel.add(back);

        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                JPanel formPanel = (JPanel) mainPanel.getComponent(1);
                Student student = (Student) formPanel.getClientProperty("student");
                if (student != null) {
                    summary.setText(String.format(
                        "Name: %s\nID: %s\nFaculty: %s\nTitle: %s\nContact: %s\nEmail: %s",
                        student.name, student.id, student.faculty, student.title, student.contact, student.email
                    ));
                }
            }
        });

        submit.addActionListener(e ->
            JOptionPane.showMessageDialog(panel, "Registration submitted successfully!")
        );

        back.addActionListener(e -> layout.show(mainPanel, "FormPage"));

        return panel;
    }

    private static JPanel createSearchPage(CardLayout layout, JPanel mainPanel, Map<String, Student> database) {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.CYAN);

        JLabel searchLabel = new JLabel("Enter Registration ID:");
        searchLabel.setBounds(50, 30, 150, 25);
        JTextField searchField = new JTextField();
        searchField.setBounds(200, 30, 200, 25);
        JButton searchBtn = new JButton("Search");
        searchBtn.setBounds(410, 30, 90, 25);

        JTextArea resultArea = new JTextArea();
        resultArea.setBounds(50, 70, 400, 180);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JLabel imagePreview = new JLabel();
        imagePreview.setBounds(180, 260, 100, 100);
        imagePreview.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JButton deleteBtn = new JButton("Delete");
        deleteBtn.setBounds(320, 370, 100, 30);
        panel.add(deleteBtn);

        deleteBtn.addActionListener(e -> {
            String regId = searchField.getText().trim();
            DBHelper.deleteStudent(regId);
            resultArea.setText("Student deleted.");
            imagePreview.setIcon(null);
        });

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(200, 370, 100, 30);

        panel.add(searchLabel);
        panel.add(searchField);
        panel.add(searchBtn);
        panel.add(resultArea);
        panel.add(imagePreview);
        panel.add(backBtn);

        searchBtn.addActionListener((ActionEvent e) -> {
            String regId = searchField.getText().trim();
            Student student = DBHelper.getStudentById(regId);
            if (student != null) {
                resultArea.setText(String.format(
                    "Name: %s\nID: %s\nFaculty: %s\nTitle: %s\nContact: %s\nEmail: %s",
                    student.name, student.id, student.faculty, student.title, student.contact, student.email
                ));
                if (student.imagePath != null) {
                    ImageIcon icon = new ImageIcon(new ImageIcon(student.imagePath).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
                    imagePreview.setIcon(icon);
                }
            } else {
                resultArea.setText("No student found with ID: " + regId);
                imagePreview.setIcon(null);
            }
        });

        backBtn.addActionListener(e -> layout.show(mainPanel, "HomePage"));

        return panel;
    }
}

class DBHelper {
    private static final String DB_URL = "jdbc:ucanaccess:///home/neo_emmy442/NetBeansProjects/hdg/database/registration.accdb";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void insertOrUpdateStudent(Hdg.Student student) {
        try (Connection conn = getConnection()) {
            PreparedStatement check = conn.prepareStatement("SELECT COUNT(*) FROM Students WHERE id = ?");
            check.setString(1, student.id);
            ResultSet rs = check.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            if (count > 0) {
                PreparedStatement update = conn.prepareStatement(
                    "UPDATE Students SET name=?, faculty=?, title=?, contact=?, email=?, imagePath=? WHERE id=?"
                );
                update.setString(1, student.name);
                update.setString(2, student.faculty);
                update.setString(3, student.title);
                update.setString(4, student.contact);
                update.setString(5, student.email);
                update.setString(6, student.imagePath);
                update.setString(7, student.id);
                update.executeUpdate();
            } else {
                PreparedStatement insert = conn.prepareStatement(
                    "INSERT INTO Students (id, name, faculty, title, contact, email, imagePath) VALUES (?, ?, ?, ?, ?, ?, ?)"
                );
                insert.setString(1, student.id);
                insert.setString(2, student.name);
                insert.setString(3, student.faculty);
                insert.setString(4, student.title);
                insert.setString(5, student.contact);
                insert.setString(6, student.email);
                insert.setString(7, student.imagePath);
                insert.executeUpdate();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error saving Student: " + e.getMessage());
        }
    }

    public static Hdg.Student getStudentById(String id) {
        try (Connection conn = getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Students WHERE id = ?");
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Hdg.Student(
                    rs.getString("name"),
                    rs.getString("id"),
                    rs.getString("faculty"),
                    rs.getString("title"),
                    rs.getString("contact"),
                    rs.getString("email"),
                    rs.getString("imagePath")
                );
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error retrieving Student: " + e.getMessage());
        }
        return null;
    }

    public static void deleteStudent(String id) {
        try (Connection conn = getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Students WHERE id = ?");
            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error deleting Student: " + e.getMessage());
        }
    }
}

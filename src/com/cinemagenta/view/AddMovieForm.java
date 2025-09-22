package com.cinemagenta.view;

import com.cinemagenta.controller.MovieController;
import com.cinemagenta.model.Movie;

import javax.swing.*;
import java.awt.*;

public class AddMovieForm extends JFrame {

    private JTextField titleField;
    private JTextField directorField;
    private JSpinner yearSpinner;
    private JSpinner durationSpinner;
    private JComboBox<String> genreComboBox;
    private JButton saveButton;
    private JButton clearButton;

    public AddMovieForm() {
        setTitle("Agregar Película");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 350);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 2, 10, 10));

        titleField = new JTextField();
        directorField = new JTextField();
        yearSpinner = new JSpinner(new SpinnerNumberModel(2023, 1900, 2100, 1));
        durationSpinner = new JSpinner(new SpinnerNumberModel(90, 1, 500, 1));
        genreComboBox = new JComboBox<>(new String[]{
                "Comedia", "Drama", "Acción", "Suspenso", "Aventura", "Animación"
        });
        saveButton = new JButton("Guardar");
        clearButton = new JButton("Limpiar");

        add(new JLabel("Título:"));
        add(titleField);

        add(new JLabel("Director:"));
        add(directorField);

        add(new JLabel("Año de lanzamiento:"));
        add(yearSpinner);

        add(new JLabel("Duración (minutos):"));
        add(durationSpinner);

        add(new JLabel("Género:"));
        add(genreComboBox);

        add(saveButton);
        add(clearButton);

        // guardar
        saveButton.addActionListener(e -> saveMovie());

        // limpiar
        clearButton.addActionListener(e -> clearForm());
    }

    private void saveMovie() {
        String title = titleField.getText().trim();
        String director = directorField.getText().trim();
        int year = (int) yearSpinner.getValue();
        int duration = (int) durationSpinner.getValue();
        String genre = (String) genreComboBox.getSelectedItem();

        if (title.isEmpty() || director.isEmpty() || genre == null) {
            JOptionPane.showMessageDialog(this,
                    "Debe completar todos los campos.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Movie movie = new Movie(title, director, year, duration, genre);
        MovieController controller = new MovieController();

        if (controller.addMovie(movie)) {
            JOptionPane.showMessageDialog(this,
                    "Película guardada exitosamente.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Ocurrió un error al guardar la película.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        titleField.setText("");
        directorField.setText("");
        yearSpinner.setValue(2023);
        durationSpinner.setValue(90);
        genreComboBox.setSelectedIndex(0);
    }
}

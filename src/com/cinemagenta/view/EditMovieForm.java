package com.cinemagenta.view;

import com.cinemagenta.controller.MovieController;
import com.cinemagenta.model.Movie;
import com.cinemagenta.utils.InputValidator;
import com.cinemagenta.view.SearchMovieForm;

import javax.swing.*;
import java.awt.*;

public class EditMovieForm extends JFrame {

    private final JTextField titleField;
    private final JTextField directorField;
    private final JSpinner yearSpinner;
    private final JSpinner durationSpinner;
    private final JComboBox<String> genreComboBox;
    private final JButton saveButton;
    private final JButton clearButton;

    private final Movie originalMovie;
    private final MovieController controller;

    public EditMovieForm(Movie movie) {
        this.originalMovie = movie;
        this.controller = new MovieController();

        setTitle("Editar Película");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(7, 2, 10, 10));

        // Componentes
        titleField = new JTextField(movie.getTitle());
        directorField = new JTextField(movie.getDirector());
        yearSpinner = new JSpinner(new SpinnerNumberModel(movie.getReleaseYear(), 1900, 2100, 1));
        durationSpinner = new JSpinner(new SpinnerNumberModel(movie.getDurationMinutes(), 1, 500, 1));
        genreComboBox = new JComboBox<>(new String[]{
                "Comedia", "Drama", "Acción", "Suspenso", "Aventura", "Animación"
        });
        genreComboBox.setSelectedItem(movie.getGenre());

        saveButton = new JButton("Guardar cambios");
        clearButton = new JButton("Limpiar");

        // Agregar al layout
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

        // Acciones
        saveButton.addActionListener(e -> updateMovie());
        clearButton.addActionListener(e -> resetForm());
    }

    private void updateMovie() {
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

        if (!InputValidator.isValidName(title)) {
            JOptionPane.showMessageDialog(this,
                    "El título contiene caracteres no permitidos. Evite símbolos como ^ % * $ @.",
                    "Error en título",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!InputValidator.isValidName(director)) {
            JOptionPane.showMessageDialog(this,
                    "El nombre del director contiene caracteres no permitidos. Evite símbolos como ^ % * $ @.",
                    "Error en director",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Movie updated = new Movie(
                originalMovie.getId(), title, director, year, duration, genre
        );

        boolean success = controller.updateMovie(updated);
        if (success) {
            JOptionPane.showMessageDialog(this,
                    "Película actualizada correctamente.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Error al actualizar la película.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetForm() {
        titleField.setText(originalMovie.getTitle());
        directorField.setText(originalMovie.getDirector());
        yearSpinner.setValue(originalMovie.getReleaseYear());
        durationSpinner.setValue(originalMovie.getDurationMinutes());
        genreComboBox.setSelectedItem(originalMovie.getGenre());
    }
}

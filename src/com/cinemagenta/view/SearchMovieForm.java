package com.cinemagenta.view;

import com.cinemagenta.controller.MovieController;
import com.cinemagenta.model.Movie;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SearchMovieForm extends JFrame {

    private JTextField searchField;
    private JButton searchButton, editButton, deleteButton, clearButton;
    private JTable resultsTable;
    private DefaultTableModel tableModel;
    private MovieController controller;

    public SearchMovieForm() {
        setTitle("Buscar Películas");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        controller = new MovieController();

        setLayout(new BorderLayout());

        // Panel superior de búsqueda
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("Buscar por título, director o año:"));
        searchField = new JTextField(20);
        topPanel.add(searchField);

        searchButton = new JButton("Buscar");
        clearButton = new JButton("Limpiar");
        topPanel.add(searchButton);
        topPanel.add(clearButton);

        add(topPanel, BorderLayout.NORTH);

        // Tabla
        String[] columns = {"ID", "Título", "Director", "Año", "Duración", "Género"};
        tableModel = new DefaultTableModel(columns, 0);
        resultsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(resultsTable);
        add(scrollPane, BorderLayout.CENTER);

        // Panel inferior con botones
        JPanel bottomPanel = new JPanel(new FlowLayout());
        editButton = new JButton("Editar seleccionada");
        deleteButton = new JButton("Eliminar seleccionada");
        bottomPanel.add(editButton);
        bottomPanel.add(deleteButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // Acciones
        searchButton.addActionListener(e -> searchMovies());
        clearButton.addActionListener(e -> clearSearch());
        editButton.addActionListener(e -> editSelectedMovie());
        deleteButton.addActionListener(e -> deleteSelectedMovie());
    }

    private void searchMovies() {
        String term = searchField.getText().trim();
        if (term.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un término de búsqueda.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<Movie> movies = controller.findMovies(term);
        tableModel.setRowCount(0); // Limpiar tabla

        for (Movie movie : movies) {
            tableModel.addRow(new Object[]{
                    movie.getId(),
                    movie.getTitle(),
                    movie.getDirector(),
                    movie.getReleaseYear(),
                    movie.getDurationMinutes(),
                    movie.getGenre()
            });
        }

        if (movies.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se encontraron películas.", "Resultado", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void clearSearch() {
        searchField.setText("");
        tableModel.setRowCount(0);
    }

    private void editSelectedMovie() {
        int selectedRow = resultsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una película para editar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int movieId = (int) tableModel.getValueAt(selectedRow, 0);
        Movie movie = controller.findMovieById(movieId);
        if (movie != null) {
            EditMovieForm editForm = new EditMovieForm(movie);
            editForm.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "La película ya no existe.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteSelectedMovie() {
        int selectedRow = resultsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una película para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int movieId = (int) tableModel.getValueAt(selectedRow, 0);
        String title = (String) tableModel.getValueAt(selectedRow, 1);

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro que desea eliminar la película \"" + title + "\"?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = controller.deleteMovieById(movieId);
            if (success) {
                JOptionPane.showMessageDialog(this, "Película eliminada correctamente.");
                tableModel.removeRow(selectedRow);
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar la película.");
            }
        }
    }
}

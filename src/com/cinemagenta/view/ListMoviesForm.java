package com.cinemagenta.view;

import com.cinemagenta.controller.MovieController;
import com.cinemagenta.model.Movie;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class ListMoviesForm extends JFrame {
    private MovieController controller;
    private JTable movieTable;
    private JScrollPane scrollPane;
    private JLabel messageLabel;
    private JComboBox<String> genreComboBox;
    private JTextField startYearField;
    private JTextField endYearField;

    public ListMoviesForm(MovieController controller) {
        this.controller = controller;
        setTitle("Listar Películas");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel superior: filtros
        JPanel filterPanel = new JPanel(new FlowLayout());

        filterPanel.add(new JLabel("Género:"));
        genreComboBox = new JComboBox<>(new String[]{"Todos", "Acción", "Comedia", "Drama", "Terror", "Aventura", "Animación", "Ciencia Ficción"});
        filterPanel.add(genreComboBox);

        filterPanel.add(new JLabel("Año Inicio:"));
        startYearField = new JTextField(5);
        filterPanel.add(startYearField);

        filterPanel.add(new JLabel("Año Fin:"));
        endYearField = new JTextField(5);
        filterPanel.add(endYearField);

        JButton searchButton = new JButton("Buscar");
        searchButton.addActionListener(this::handleSearch);
        filterPanel.add(searchButton);

        JButton clearButton = new JButton("Limpiar");
        clearButton.addActionListener(e -> clearFilters());
        filterPanel.add(clearButton);

        add(filterPanel, BorderLayout.NORTH);

        // Tabla y mensajes
        movieTable = new JTable();
        scrollPane = new JScrollPane(movieTable);
        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setFont(new Font("SansSerif", Font.ITALIC, 14));
        messageLabel.setForeground(Color.GRAY);
        
        add(scrollPane, BorderLayout.CENTER);
        add(messageLabel, BorderLayout.SOUTH);
        messageLabel.setVisible(false); // Ocultar inicialmente

        loadAllMovies(); // Carga inicial de peliculas
    }

    private void handleSearch(ActionEvent e) {
        String genre = (String) genreComboBox.getSelectedItem();
        if ("Todos".equalsIgnoreCase(genre)) {
            genre = "";
        }
        String startYearText = startYearField.getText().trim();
        String endYearText = endYearField.getText().trim();

        Integer startYear = null;
        Integer endYear = null;

        try {
            if (!startYearText.isEmpty()) {
                startYear = Integer.parseInt(startYearText);
            }
            if (!endYearText.isEmpty()) {
                endYear = Integer.parseInt(endYearText);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese años válidos (números).", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (startYear != null && endYear != null && startYear > endYear) {
            JOptionPane.showMessageDialog(this, "El año de inicio no puede ser mayor que el año de fin.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Movie> filtered = controller.filterMovies(genre, startYear, endYear);
        renderTable(filtered);
    }

    private void clearFilters() {
        genreComboBox.setSelectedIndex(0);
        startYearField.setText("");
        endYearField.setText("");
        loadAllMovies();
    }

    private void loadAllMovies() {
        List<Movie> allMovies = controller.getAllMovies();
        renderTable(allMovies);
    }

    private void renderTable(List<Movie> movies) {
        String[] columns = {"ID", "Título", "Director", "Año", "Duración (min)", "Género"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        if (movies.isEmpty()) {
            movieTable.setModel(model); // Limpiando tabla
            scrollPane.setVisible(false);
            messageLabel.setVisible(true);

            if (controller.getAllMovies().isEmpty()) {
                messageLabel.setText("Aún no se han agregado películas. Use el menú 'Películas' para comenzar a registrar.");
            } else {
                messageLabel.setText("No se han encontrado resultados para esta búsqueda.");
            }
        } else {
            for (Movie m : movies) {
                Object[] row = {
                    m.getId(),
                    m.getTitle(),
                    m.getDirector(),
                    m.getReleaseYear(),
                    m.getDurationMinutes(),
                    m.getGenre()
                };
                model.addRow(row);
            }

            movieTable.setModel(model);
            scrollPane.setVisible(true);
            messageLabel.setVisible(false);
        }

        revalidate();
        repaint();
    }
}

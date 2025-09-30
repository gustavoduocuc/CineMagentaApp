package com.cinemagenta.view;

import javax.swing.*;
import java.awt.event.ActionEvent;

import com.cinemagenta.view.SearchMovieForm;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Sistema Cine Magenta");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null); // Centrado basico en pantalla

        // Creando una barra de menú
        JMenuBar menuBar = new JMenuBar();

        // Menú principal
        JMenu movieMenu = new JMenu("Películas");

        // Opción para Agregar Película
        JMenuItem addMovieItem = new JMenuItem("Agregar película");
        addMovieItem.addActionListener(this::openAddMovieForm);
        movieMenu.add(addMovieItem);
        
        JMenuItem searchMovieItem = new JMenuItem("Buscar / Editar / Eliminar película");
        searchMovieItem.addActionListener(e -> {
            SearchMovieForm searchForm = new SearchMovieForm();
            searchForm.setVisible(true);
        });
        movieMenu.add(searchMovieItem);

        // Agregando menu a la barra
        menuBar.add(movieMenu);

        // Estableciendo la barra en el frame
        setJMenuBar(menuBar);

        JLabel welcomeLabel = new JLabel("Bienvenido al Sistema Cine Magenta", SwingConstants.CENTER);
        add(welcomeLabel);
    }

    private void openAddMovieForm(ActionEvent e) {
        AddMovieForm form = new AddMovieForm();
        form.setVisible(true);
    }
}

package com.cinemagenta.view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.BorderLayout;
import com.cinemagenta.controller.MovieController;

public class MainFrame extends JFrame {
    private final MovieController controller;

    public MainFrame() {
        this.controller = new MovieController();
        setTitle("Sistema Cine Magenta");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        showMenuBarWithOptions();
        add(createWelcomePanel(), BorderLayout.CENTER);
    }

    private void showMenuBarWithOptions() {
        JMenuBar menuBar = new JMenuBar();

        // Menú Principal de Películas
        JMenu movieMenu = new JMenu("Menú Películas");
        
        // Opciones del Menu
        JMenuItem addMovieItem = new JMenuItem("Agregar película");
        addMovieItem.addActionListener(this::openAddMovieForm);
        movieMenu.add(addMovieItem);

        JMenuItem searchMovieItem = new JMenuItem("Buscar / Editar / Eliminar película");
        searchMovieItem.addActionListener(e -> openSearchForm());
        movieMenu.add(searchMovieItem);

        movieMenu.addSeparator();

        JMenuItem listMovieItem = new JMenuItem("Listar películas (con filtros)");
        listMovieItem.addActionListener(e -> openListMoviesForm());
        movieMenu.add(listMovieItem);

        menuBar.add(movieMenu);
        setJMenuBar(menuBar);
    }
    
    private JPanel createWelcomePanel() {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel welcomeLabel = new JLabel("Bienvenido al Sistema Cine Magenta", SwingConstants.CENTER);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel instructionsLabel = new JLabel("Utilice el menú superior para comenzar", SwingConstants.CENTER);
        instructionsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(welcomeLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(instructionsLabel);
        centerPanel.add(Box.createVerticalGlue());

        return centerPanel;
    }

    private void openAddMovieForm(ActionEvent e) {
        AddMovieForm form = new AddMovieForm();
        form.setVisible(true);
    }

    private void openSearchForm() {
        SearchMovieForm searchForm = new SearchMovieForm();
        searchForm.setVisible(true);
    }

    private void openListMoviesForm() {
        ListMoviesForm listForm = new ListMoviesForm(controller);
        listForm.setVisible(true);
    }
}

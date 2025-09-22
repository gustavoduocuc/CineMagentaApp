package com.cinemagenta.model;

public class Movie {
    private int id;
    private String title;
    private String director;
    private int releaseYear;
    private int durationMinutes;
    private String genre;

    public Movie(int id, String title, String director, int releaseYear, int durationMinutes, String genre) {
        this.id = id;
        this.title = title;
        this.director = director;
        this.releaseYear = releaseYear;
        this.durationMinutes = durationMinutes;
        this.genre = genre;
    }

    public Movie(String title, String director, int releaseYear, int durationMinutes, String genre) {
        this(0, title, director, releaseYear, durationMinutes, genre);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
package br.com.estudando.screenmatch.model;

public enum Categoria {

    ACAO("Action", "Acao"),

    ROMANCE("Romance", "Romance"),

    COMEDIA("Comedy", "Comédia"),

    DRAMA("Drama", "Drama"),

    CRIME("Crime", "Crime");

    private final String categoriaOmdb;

    private String categorioPortugues;

    Categoria(String categoriaOmdb, String categorioPortugues) {
        this.categoriaOmdb = categoriaOmdb;
        this.categorioPortugues = categorioPortugues;
    }

    public static Categoria fromString(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaOmdb.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada");
    }

    public static Categoria fromPortugues(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categorioPortugues.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada");
    }
}

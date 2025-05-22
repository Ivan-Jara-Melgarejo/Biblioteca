package bibliotecaapp;

import java.util.Arrays;

/**
 *
 * @author Ivan Jara
 */
public class Bibliotecario {
    private Libro[] libros;
    private int contadorLibros;
    private final int CAPACIDAD_INICIAL = 10;

    public Bibliotecario() {
        this.libros = new Libro[CAPACIDAD_INICIAL];
        this.contadorLibros = 0;
    }

    private void redimensionarArreglo() {
        if (contadorLibros == libros.length) {
            libros = Arrays.copyOf(libros, libros.length * 2); 
        }
    }

    public boolean agregarLibro(Libro nuevoLibro) {
        if (buscarLibroPorId(nuevoLibro.getId()) != null) {
            return false; 
        }
        redimensionarArreglo();
        libros[contadorLibros] = nuevoLibro;
        contadorLibros++;
        return true;
    }


    public Libro buscarLibroPorId(String id) {
        for (int i = 0; i < contadorLibros; i++) {
            if (libros[i].getId().equals(id)) {
                return libros[i];
            }
        }
        return null; 
    }


    public boolean actualizarLibro(String id, String nuevoTitulo, String nuevoAutor, int nuevoAnioPublicacion, String nuevoGenero) {
        Libro libroExistente = buscarLibroPorId(id);
        if (libroExistente != null) {
            libroExistente.setTitulo(nuevoTitulo);
            libroExistente.setAutor(nuevoAutor);
            libroExistente.setAnioPublicacion(nuevoAnioPublicacion);
            libroExistente.setGenero(nuevoGenero);
            return true;
        }
        return false; 
    }

    public String[] obtenerListaLibros() {
        String[] lista = new String[contadorLibros];
        for (int i = 0; i < contadorLibros; i++) {
            lista[i] = libros[i].toString();
        }
        return lista;
    }
}


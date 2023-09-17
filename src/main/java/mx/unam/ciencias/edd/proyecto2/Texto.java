package mx.unam.ciencias.edd.proyecto2;

/**
 * Clase Texto que modela una línea de texto en formato SVG.
 * @author Armando Ramírez González.
 * @version 1.0.0
 */
public class Texto extends Figura {

    /* Elemento del vector. */
    private String elemento;
    /* Posición del texto. */
    private Vector posicion;
    /* Color del texto. */
    private String relleno;

    /**
     * Método constructor.
     * @param elemento el elemento del vector.
     * @param x abscisa de la posición del texto.
     * @param y ordenada de la posición del texto.
     */
    public Texto(String elemento, double x, double y, String relleno) {
        this.elemento = elemento;
        this.posicion = new Vector(x,y);
        this.relleno = relleno;
    }

    /**
     * Método toSVG.
     * @return String un texto en formato SVG.
     */
    public String toSVG() {
        String texto = "<text x='" + posicion.abscisa
                       + "' y='" + posicion.ordenada
                       + "' font-family='sans-serif' font-size='18'"
                       + " fill='" + relleno + "'>"
                       + elemento + "</text>";
        return texto;
    }
}

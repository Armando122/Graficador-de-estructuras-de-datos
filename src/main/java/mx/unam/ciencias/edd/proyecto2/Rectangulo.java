package mx.unam.ciencias.edd.proyecto2;

/**
 * Clase Rectangulo.
 * Que modela un rectangulo en formato SVG.
 * @author Armando Ramírez González.
 * @version 1.0.0
 */
public class Rectangulo extends Figura {

    /** Esquina superior izquierda del rectangulo. */
    private Vector punto;
    /** Ancho del rectángulo. */
    private double ancho;
    /** Alto del rectángulo. */
    private double alto;

    /**
     * Constructor de un rectángulo.
     * Los rectángulos tendrán un ancho y alto constante,
     * por cuestiones prácticas no se implementará aquí, para
     * permitir el uso de la clase en nuevas clases en caso
     * de ser necesario.
     * @param x abscisa del punto.
     * @param y ordenada del punto.
     * @param ancho ancho del rectángulo.
     * @param alto alto del rectángulo.
     */
    public Rectangulo(double x, double y, double ancho, double alto){
      this.punto = new Vector(x,y);
      this.ancho = ancho;
      this.alto = alto;
    }

    /**
     * Método toSVG.
     * Que regresa el rectángulo en formato SVG.
     * @return String que contiene el rectángulo en SVG.
     * stroke indica el color de la línea exterior que delimita la
     *        figura.
     * stroke-width indica el grosor de la línea exterior que delimita
     *        la figura expresado en píxeles.
     * fill indica el color del relleno de la figura.
     * x,y indican la coordenada de la esquina superior izquierda
     *     del rectangulo.
     * width indica el ancho del rectangulo.
     * height indica la altura del rectangulo.
     */
    public String toSVG(){
      String rectangulo = "<rect x='" + punto.abscisa
                            + "' y='" + punto.ordenada
                            + "' width='" + ancho
                            + "' height='" + alto
                            + "' stroke='black' stroke-width='2'"
                            + " fill='white'/>";
      return rectangulo;
    }
}

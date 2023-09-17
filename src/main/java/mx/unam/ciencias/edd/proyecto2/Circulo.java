package mx.unam.ciencias.edd.proyecto2;

/**
 * Clase Circulo.
 * Que modela un círculo en formato SVG.
 * @author Armando Ramírez González.
 * @version 1.0.0
 */
public class Circulo extends Figura {

    /* Centro del círculo. */
    private Vector centro;
    /* Radio del círculo. */
    private int radio;
    /* Color de la línea exterior que delimita la figura. */
    private String exterior;
    /* Color del relleno de la figura. */
    private String relleno;

    /**
     * Constructor de un círculo.
     * @param x abscisa del centro del círculo.
     * @param y ordenada del centro del círculo.
     * @param radio el radio del círculo.
     */
    public Circulo(int x, int y, int radio, String ext, String fill){
      this.centro = new Vector(x,y);
      this.radio = radio;
      this.exterior = ext;
      this.relleno = fill;
    }

    /**
     * Método toSVG.
     * @return String Que regresa los valores del círculo en formato SVG
     * cx y cy indican las coordenadas del centro del círculo.
     * r indica el radio del círculo en píxeles.
     * stroke indica el color de la línea exterior que delimita la
     *        figura.
     * stroke-width indica el grosor de la línea exterior que delimita
     *        la figura expresado en píxeles.
     * fill indica el color del relleno de la figura.
     */
    public String toSVG(){
      String circulo = "<circle cx='" + centro.abscisa
                        + "' cy='" + centro.ordenada
                        + "' r='" + radio
                        + "' stroke='" + exterior
                        + "' stroke-width='3'"
                        + " fill='" + relleno + "' />";
      return circulo;
    }
}

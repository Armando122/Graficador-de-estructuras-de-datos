package mx.unam.ciencias.edd.proyecto2;

/**
 * Clase Linea que modela una línea en formato SVG.
 * @author Armando Ramírez González.
 * @version 1.0.0
 */
public class Linea extends Figura {

    /** Vector inicial de la línea. */
    private Vector v1;
    /** Vector final de la línea. */
    private Vector v2;

    /**
     * Constructor de una línea que recibe cuatro números
     * @param x1 abscisa del primer vector.
     * @param y1 ordenada del primer vector.
     * @param x2 abscisa del segundo vector.
     * @param y2 ordenada del segundo vector.
     */
    public Linea(double x1, double y1, double x2, double y2){
      this.v1 = new Vector(x1,y1);
      this.v2 = new Vector(x2,y2);
    }

    /**
     * Método para regresar la linea en formato SVG.
     * @return String cadena con el formato SVG de una línea.
     */
    public String toSVG(){
      /*
      * stroke indica el color de la línea exterior que delimita la
      *        figura.
      * stroke-width indica el grosor de la línea exterior que delimita
      *        la figura expresado en píxeles.
      * x1,y1 indican el vector incial de la línea.
      * x2,y2 indican el vector final de la línea.
      */
      String linea = ("<line x1='" + v1.abscisa
                      + "' y1='" + v1.ordenada
                      + "' x2='" + v2.abscisa
                      + "' y2='" + v2.ordenada
                      + "' stroke-width='3' stroke='Black'/>");
      return linea;
    }
}

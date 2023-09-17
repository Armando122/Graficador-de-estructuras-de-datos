package mx.unam.ciencias.edd.proyecto2;

/**
 * Clase Triangulo.
 * Que modela un triángulo en formato SVG.
 * @author Armando Ramírez González.
 * @version 1.0.0
 */
public class Triangulo extends Figura {

    /** Un punto dl triángulo. */
    private Vector punto1;
    /** Segundo punto del triángulo. */
    private Vector punto2;
    /** Tercer punto del triángulo. */
    private Vector punto3;

    /**
     * Constructor de un Triangulo.
     * @param x1 abscisa del primer punto.
     * @param y1 ordenada del primer punto.
     * @param x2 abscisa del segundo punto.
     * @param y2 ordenada del segundo punto.
     * @param x3 abscisa del tercer punto.
     * @param y3 ordenada del tercer punto.
     */
    public Triangulo(double x1, double y1, double x2, double y2, double x3, double y3){
      this.punto1 = new Vector(x1,y1);
      this.punto2 = new Vector(x2,y2);
      this.punto3 = new Vector(x3,y3);
    }

    /**
     * Método toSVG.
     * @return String que regresa el triángulo en formato SVG.
     * stroke indica el color de la línea exterior que delimita la
     *        figura.
     * stroke-width indica el grosor de la línea exterior que delimita
     *        la figura expresado en píxeles.
     * fill indica el color del relleno de la figura.
     * polygon points es de la forma x1,y1 x2,y2 x3,y3
     * x1,y1 indican el primer punto del triángulo.
     * x2,y2 indican el segundo punto del triángulo.
     * x3,y3 indican el tercer punto del triángulo.
     */
    public String toSVG(){
      String triangulo = "<polygon points='"
                          + punto1.abscisa + ", " + punto1.ordenada + " "
                          + punto2.abscisa + ", " + punto2.ordenada + " "
                          + punto3.abscisa + ", " + punto3.ordenada
                          + "' fill='black' stroke-width='3' stroke='black' />";
      return triangulo;
    }
}

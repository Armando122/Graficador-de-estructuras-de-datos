package mx.unam.ciencias.edd.proyecto2;

/**
 * Clase Flecha que modelas flechas en formato SVG.
 * @author Armando Ramírez González.
 * @version 1.0.0.
 */
public class Flecha {

    /* String correspondiente a la flecha en SVG. */
    private String flecha;

    /**
     * Método flechaDerecha.
     * @param x,y coordenadas del inicio de la flecha.
     * @param x1,y1 coordenadas del final de la flecha.
     * @return String una flecha en formato SVG en dirección
     *        a la derecha.
     */
    public String flechaDerecha(double x, double y, double x1, double y1) {
        Linea f = new Linea(x,y,x1,y1);
        Triangulo t = new Triangulo(x1,y1,x1-3,y1+3,x1-3,y1-3);
        flecha += f.toSVG() + "\n";
        flecha += t.toSVG() + "\n";
        return flecha;
    }

    /**
     * Método flechaIzquierda.
     * @param x,y coordenadas del inicio de la flecha.
     * @param x1,y1 coordenadas del final de la flecha.
     * @return String una flecha en formato SVG en dirección
     *        a la izquierda.
     */
    public String flechaIzquierda(double x, double y, double x1, double y1) {
      Linea f = new Linea(x,y,x1,y1);
      Triangulo t = new Triangulo(x,y,x-3,y+3,x-3,y-3);
      flecha += f.toSVG() + "\n";
      flecha += t.toSVG() + "\n";
      return flecha;
    }

    /**
     * Método flechaBidireccional.
     * @param x,y coordenadas del inicio de la flecha.
     * @param x1,y1 coordenadas del final de la flecha.
     * @return String una flecha en formato SVG en dirección
     * en ambas direcciones.
     */
    public String flechaBidireccional(double x, double y, double x1, double y1) {
      Linea f = new Linea(x,y,x1,y1);
      Triangulo ti = new Triangulo(x,y,x+3,y+3,x+3,y-3);
      Triangulo tf = new Triangulo(x1,y1,x1-3,y1+3,x1-3,y1-3);
      flecha += f.toSVG() + "\n";
      flecha += ti.toSVG() + "\n";
      flecha += tf.toSVG() + "\n";
      return flecha;
    }
}

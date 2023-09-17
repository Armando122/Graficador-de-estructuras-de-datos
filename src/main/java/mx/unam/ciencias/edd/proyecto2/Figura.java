package mx.unam.ciencias.edd.proyecto2;

/**
 * Clase abstracta para dibujar figuras en formato
 * svg.
 * @author Armando Ramírez González.
 */
public abstract class Figura {

    /**
     * Clase interna protegida para vectores.
     */
    protected class Vector {
        /** Coordenada x del vector. */
        public double abscisa;
        /** Coordenada y del vector. */
        public double ordenada;

        /**
         * Construye un vector.
         * @param x coordenada abscisa del vector.
         * @param y coordenada ordenada del vector.
         */
        public Vector(double x, double y) {
            this.abscisa = x;
            this.ordenada = y;
        }

        /**
         * Método puntoMedio.
         * Que calcula las coordenadas del punto medio
         * de dos vectores.
         * @param vertor1 Vector inicial
         * @param vector2 Vector final.
         * @return Vector con las coordenadas correspondientes
         *        al punto medio entre vector1 y vector2.
         */
        public Vector puntoMedio(Vector vector1, Vector vector2){
          double x = (vector1.abscisa + vector2.abscisa)/2.0;
          double y = (vector1.ordenada + vector2.ordenada)/2.0;
          return new Vector(x,y);
        }
    }

    /**
     * Crea el formato svg correspondiente a la figura
     * que se elija.
     * @return String con la cadena correspondiente al formato svg.
     */
    public abstract String toSVG();
}

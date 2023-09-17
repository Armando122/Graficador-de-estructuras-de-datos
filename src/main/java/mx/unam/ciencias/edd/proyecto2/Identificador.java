package mx.unam.ciencias.edd.proyecto2;

/**
 * Clase Identificador .
 * @author Armando Ramírez González.
 * @version 1.0.0.
 */
public class Identificador {

  /* Graficador a devolver. */
  Graficador graph = new Graficador();

  /**
   * Método define.
   * Que define el valor de la estructura g.
   * @param tipo el nombre de la clase.
   * @param g un graficador.
   * @return Un graficador con la estructura definida.
   */
  public Graficador define(String tipo) {
    switch (tipo) {
      case "Lista":
        graph.estructura(Estructura.LISTA);
      break;

      case "Pila":
        graph.estructura(Estructura.PILA);
      break;

      case "Cola":
        graph.estructura(Estructura.COLA);
      break;

      case "ArbolBinarioCompleto":
        graph.estructura(Estructura.ARBOLCOMPLETO);;
      break;

      case "ArbolBinarioOrdenado":
        graph.estructura(Estructura.ARBOLORDENADO);
      break;

      case "ArbolRojinegro":
        graph.estructura(Estructura.ARBOLROJINEGRO);
      break;

      case "ArbolAVL":
        graph.estructura(Estructura.ARBOLAVL);
      break;

      case "Grafica":
        graph.estructura(Estructura.GRAFICA);
      break;

      case "MonticuloMinimo":
        graph.estructura(Estructura.MONTICULOMINIMO);
      break;

      default:
        graph.estructura(Estructura.NINGUNO);
      break;
    }
    return graph;
  }
}

import java.util.ArrayList;

public class ArbolPlagas {
    NodoRiesgo raiz = new NodoRiesgo("Plagas");

    void construir() {
        NodoRiesgo foliares = raiz.agregarHijo("Foliares");
        foliares.agregarHijo("Roya");
        foliares.agregarHijo("Ojo de gallo");
        foliares.agregarHijo("Sigatoka");

        NodoRiesgo fruto = raiz.agregarHijo("Fruto");
        fruto.agregarHijo("Broca");
    }

    ArrayList<String> plagasCompatibles(String cultivo, String suelo, Grafo grafo, ArrayList<String> yaDetectadas) {
        ArrayList<String> resultado = new ArrayList<>();
        recolectar(raiz, cultivo, suelo, grafo, yaDetectadas, resultado);
        return resultado;
    }

    private void recolectar(NodoRiesgo nodo, String cultivo, String suelo, Grafo grafo,
                            ArrayList<String> yaDetectadas, ArrayList<String> resultado) {
        for (NodoRiesgo hijo : nodo.hijos) {
            if (hijo.hijos.isEmpty()) {
                if (!yaDetectadas.contains(hijo.nombre)
                        && grafo.estanConectados(hijo.nombre, cultivo)
                        && grafo.estanConectados(hijo.nombre, suelo)) {
                    resultado.add(hijo.nombre);
                }
            } else {
                recolectar(hijo, cultivo, suelo, grafo, yaDetectadas, resultado);
            }
        }
    }
}

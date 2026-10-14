public class VisualizadorArbol {
    public static void imprimir(NodoRiesgo raiz, String titulo) {
        System.out.println("\n--- " + titulo.toUpperCase() + "--- ");
        System.out.println("Altura: " + raiz.altura()
                + " | Nodos: " + raiz.contarNodos()
                + " | Hojas: " + raiz.contarHojas());
        imprimirRecursivo(raiz, "", true);
    }

    private static void imprimirRecursivo(NodoRiesgo nodo, String prefijo, boolean esUltimo) {
        System.out.println(prefijo + (esUltimo ? "└── " : "├── ") + nodo.nombre);
        String nuevoPrefijo = prefijo + (esUltimo ? "    " : "│   ");
        for (int i = 0; i < nodo.hijos.size(); i++) {
            imprimirRecursivo(nodo.hijos.get(i), nuevoPrefijo, i == nodo.hijos.size() - 1);
        }
    }
}

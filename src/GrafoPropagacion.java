import java.util.ArrayList;

public class GrafoPropagacion {
    ArrayList<Parcela> parcelas = new ArrayList<>();
    ArrayList<ArrayList<Integer>> adyacentes = new ArrayList<>();

    void agregarParcela(Parcela p) {
        parcelas.add(p);
        adyacentes.add(new ArrayList<>());
        for (int i = 0; i < parcelas.size() - 1; i++) {
            Parcela otra = parcelas.get(i);
            if (otra.cultivo.equals(p.cultivo) || otra.suelo.equals(p.suelo)) {
                adyacentes.get(i).add(parcelas.size() - 1);
                adyacentes.get(parcelas.size() - 1).add(i);
            }
        }
    }

    ArrayList<String> parcelasVecinasConRiesgo(Parcela origen, ArbolDecision arbol, Grafo grafo) {
        ArrayList<String> resultado = new ArrayList<>();
        int idx = parcelas.indexOf(origen);
        if (idx < 0) return resultado;
        boolean[] visitados = new boolean[parcelas.size()];
        visitados[idx] = true;
        for (int vecino : adyacentes.get(idx)) {
            explorar(vecino, arbol, grafo, visitados, resultado);
        }
        return resultado;
    }

    private void explorar(int idx, ArbolDecision arbol, Grafo grafo, boolean[] visitados, ArrayList<String> resultado) {
        if (visitados[idx]) return;
        visitados[idx] = true;
        Parcela p = parcelas.get(idx);
        int puntaje = arbol.calcularRiesgo(p, grafo);
        if (puntaje >= 30) {
            resultado.add(p.nombre + " (riesgo " + puntaje + ")");
        }
        for (int vecino : adyacentes.get(idx)) {
            explorar(vecino, arbol, grafo, visitados, resultado);
        }
    }

    void imprimir() {
        System.out.println("\n--- GRAFO DE PROPAGACIÓN ENTRE PARCELAS ---");
        for (int i = 0; i < parcelas.size(); i++) {
            System.out.print(parcelas.get(i).nombre + " -> ");
            ArrayList<String> nombres = new ArrayList<>();
            for (int j : adyacentes.get(i)) {
                nombres.add(parcelas.get(j).nombre);
            }
            System.out.println(nombres);
        }
    }
}

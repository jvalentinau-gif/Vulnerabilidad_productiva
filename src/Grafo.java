import java.util.ArrayList;

public class Grafo {
    ArrayList<String> vertices = new ArrayList<>();
    ArrayList<ArrayList<Integer>> adyacentes = new ArrayList<>();

    void agregarVertice(String nombre) {
        vertices.add(nombre);
        adyacentes.add(new ArrayList<>());
    }

    void conectar(String a, String b) {
        int i = vertices.indexOf(a);
        int j = vertices.indexOf(b);
        if (i >= 0 && j >= 0) {
            adyacentes.get(i).add(j);
            adyacentes.get(j).add(i);
        }
    }

    boolean estanConectados(String a, String b) {
        int i = vertices.indexOf(a);
        int j = vertices.indexOf(b);
        return i >= 0 && j >= 0 && adyacentes.get(i).contains(j);
    }

    void dfs(int v, boolean[] visitados) {
        visitados[v] = true;
        for (int vecino : adyacentes.get(v)) {
            if (!visitados[vecino]) {
                dfs(vecino, visitados);
            }
        }
    }

    boolean mismoComponente(String a, String b) {
        int i = vertices.indexOf(a);
        int j = vertices.indexOf(b);
        if (i < 0 || j < 0) return false;
        boolean[] visitados = new boolean[vertices.size()];
        dfs(i, visitados);
        return visitados[j];
    }

    void imprimir() {
        System.out.println("\n--- GRAFO DE RELACIONES PLAGA-CULTIVO-SUELO ---");
        for (int i = 0; i < vertices.size(); i++) {
            System.out.print(vertices.get(i) + " -> ");
            ArrayList<String> nombres = new ArrayList<>();
            for (int j : adyacentes.get(i)) {
                nombres.add(vertices.get(j));
            }
            System.out.println(nombres);
        }
    }
}

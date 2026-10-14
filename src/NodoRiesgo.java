import java.util.ArrayList;

public class NodoRiesgo {
    String nombre;
    ArrayList<NodoRiesgo> hijos = new ArrayList<>();

    NodoRiesgo(String nombre) {
        this.nombre = nombre;
    }

    NodoRiesgo agregarHijo(String nombre) {
        NodoRiesgo hijo = new NodoRiesgo(nombre);
        hijos.add(hijo);
        return hijo;
    }

    int altura() {
        if (hijos.isEmpty()) return 1;
        int max = 0;
        for (NodoRiesgo hijo : hijos) {
            max = Math.max(max, hijo.altura());
        }
        return max + 1;
    }

    int contarNodos() {
        int total = 1;
        for (NodoRiesgo hijo : hijos) {
            total += hijo.contarNodos();
        }
        return total;
    }

    int contarHojas() {
        if (hijos.isEmpty()) return 1;
        int total = 0;
        for (NodoRiesgo hijo : hijos) {
            total += hijo.contarHojas();
        }
        return total;
    }
}

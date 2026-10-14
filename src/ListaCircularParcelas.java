public class ListaCircularParcelas {
    NodoParcela cabeza;
    NodoParcela ultimo;

    void agregar(Parcela parcela) {
        NodoParcela nuevo = new NodoParcela(parcela);
        if (cabeza == null) {
            cabeza = nuevo;
            nuevo.siguiente = cabeza;
        } else {
            ultimo.siguiente = nuevo;
            nuevo.siguiente = cabeza;
        }
        ultimo = nuevo;
    }

    int contar() {
        if (cabeza == null) return 0;
        return contarDesde(cabeza);
    }

    private int contarDesde(NodoParcela actual) {
        if (actual.siguiente == cabeza) return 1;
        return 1 + contarDesde(actual.siguiente);
    }
}

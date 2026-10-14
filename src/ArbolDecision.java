public class ArbolDecision {
    NodoRiesgo raiz = new NodoRiesgo("RiesgoTotal");

    void construir() {
        NodoRiesgo plagas = raiz.agregarHijo("Plagas");
        NodoRiesgo suelo = raiz.agregarHijo("Suelo");
        NodoRiesgo agua = raiz.agregarHijo("Agua");
        NodoRiesgo agro = raiz.agregarHijo("Agroquímicos");

        plagas.agregarHijo("Plaga compatible con cultivo y suelo (+35)");
        plagas.agregarHijo("Plaga compatible solo con cultivo (+20)");
        suelo.agregarHijo("pH fuera de rango (+20)");
        agua.agregarHijo("Calidad de agua baja (+20)");
        agro.agregarHijo("Más de 4 aplicaciones (+20)");
    }

    int calcularRiesgo(Parcela parcela, Grafo grafo) {
        return evaluar(raiz, parcela, grafo);
    }

    private int evaluar(NodoRiesgo nodo, Parcela parcela, Grafo grafo) {
        int total = 0;
        for (NodoRiesgo hijo : nodo.hijos) {
            total += evaluarCondicion(hijo, parcela, grafo);
        }
        return total;
    }

    private int evaluarCondicion(NodoRiesgo condicion, Parcela parcela, Grafo grafo) {
        switch (condicion.nombre) {
            case "Plagas":
                int p = 0;
                for (String plaga : parcela.plagasDetectadas) {
                    if (grafo.estanConectados(plaga, parcela.cultivo)) {
                        p += grafo.estanConectados(plaga, parcela.suelo) ? 35 : 20;
                    }
                }
                return p;
            case "Suelo":
                return (parcela.ph < 4.5 || parcela.ph > 5.5) ? 20 : 0;
            case "Agua":
                return parcela.calidadAgua < 50 ? 20 : 0;
            case "Agroquímicos":
                return parcela.aplicacionesAgroquimicos > 4 ? 20 : 0;
            default:
                return 0;
        }
    }
}

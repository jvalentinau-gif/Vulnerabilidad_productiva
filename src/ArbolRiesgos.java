import java.util.ArrayList;

public class ArbolRiesgos {
    NodoRiesgo raiz = new NodoRiesgo("Riesgo");

    void construir() {
        NodoRiesgo alto = raiz.agregarHijo("ALTO");
        alto.agregarHijo("General").agregarHijo("Priorizar esta parcela en la próxima visita técnica");
        alto.agregarHijo("Plagas").agregarHijo("Inspeccionar la parcela de inmediato y controlar la plaga detectada");
        alto.agregarHijo("Suelo").agregarHijo("Realizar análisis de suelo y corregir el pH según el resultado");
        alto.agregarHijo("Agua").agregarHijo("Suspender agroquímicos cerca de la quebrada y proteger la fuente de agua");
        alto.agregarHijo("Agroquímicos").agregarHijo("Reducir las aplicaciones y usar solo productos recomendados");

        NodoRiesgo medio = raiz.agregarHijo("MEDIO");
        medio.agregarHijo("General").agregarHijo("Programar seguimiento en las próximas dos semanas");
        medio.agregarHijo("Plagas").agregarHijo("Monitorear la plaga y registrar su avance cada semana");
        medio.agregarHijo("Suelo").agregarHijo("Ajustar la fertilización y revisar el pH");
        medio.agregarHijo("Agua").agregarHijo("Revisar la calidad del agua y evitar la escorrentía");
        medio.agregarHijo("Agroquímicos").agregarHijo("Revisar las dosis y la frecuencia de aplicación");

        NodoRiesgo bajo = raiz.agregarHijo("BAJO");
        bajo.agregarHijo("General").agregarHijo("Mantener el registro periódico de la parcela");
        bajo.agregarHijo("Plagas").agregarHijo("Continuar con el monitoreo preventivo");
        bajo.agregarHijo("Suelo").agregarHijo("Mantener las prácticas actuales de manejo del suelo");
        bajo.agregarHijo("Agua").agregarHijo("Mantener el cuidado de la fuente de agua");
        bajo.agregarHijo("Agroquímicos").agregarHijo("Mantener el uso controlado de agroquímicos");
    }

    void mostrarRecomendaciones(String nivel, ArrayList<String> areas) {
        NodoRiesgo categoria = buscarNivel(raiz, nivel);
        if (categoria == null) return;
        for (NodoRiesgo area : categoria.hijos) {
            if (areas.contains(area.nombre)) {
                recorrer(area);
            }
        }
    }

    private NodoRiesgo buscarNivel(NodoRiesgo nodo, String nivel) {
        if (nodo.nombre.equals(nivel)) return nodo;
        for (NodoRiesgo hijo : nodo.hijos) {
            NodoRiesgo encontrado = buscarNivel(hijo, nivel);
            if (encontrado != null) return encontrado;
        }
        return null;
    }

    void recorrer(NodoRiesgo nodo) {
        for (NodoRiesgo hijo : nodo.hijos) {
            System.out.println("  - " + nodo.nombre + ": " + hijo.nombre);
            recorrer(hijo);
        }
    }
}

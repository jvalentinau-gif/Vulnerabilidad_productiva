import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static String[] nombresCultivos = {"Café Caturra", "Café Castillo", "Plátano"};
    static String[] nombresSuelos = {"Arcilloso", "Franco", "Arenoso"};
    static String[] nombresPlagas = {"Broca", "Roya", "Ojo de gallo", "Sigatoka"};

    static Grafo grafoPlagas = new Grafo();
    static GrafoPropagacion grafoPropagacion = new GrafoPropagacion();
    static ListaCircularParcelas listaParcelas = new ListaCircularParcelas();
    static ArbolRiesgos arbolRiesgos = new ArbolRiesgos();
    static ArbolDecision arbolDecision = new ArbolDecision();
    static ArbolPlagas arbolPlagas = new ArbolPlagas();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        cargarGrafo();
        cargarArboles();
        int opcion;
        do {
            System.out.println("\n---- SISTEMA DE ANÁLISIS DE RIESGOS CAFETEROS ----");
            System.out.println("1. Registrar parcela");
            System.out.println("2. Cargar casos de prueba");
            System.out.println("3. Mostrar reporte de riesgos");
            System.out.println("4. Visualizar árbol de recomendaciones");
            System.out.println("5. Visualizar árbol de decisión");
            System.out.println("6. Visualizar árbol taxonómico de plagas");
            System.out.println("7. Visualizar grafo de plagas");
            System.out.println("8. Visualizar grafo de propagación entre parcelas");
            System.out.println("9. Ver estadísticas de las estructuras");
            System.out.println("0. Salir");
            opcion = leerEntero("Seleccione una opción: ");
            switch (opcion) {
                case 1: registrarParcela(); break;
                case 2: cargarCasosDePrueba(); break;
                case 3: mostrarReporte(); break;
                case 4: VisualizadorArbol.imprimir(arbolRiesgos.raiz, "Árbol de recomendaciones"); break;
                case 5: VisualizadorArbol.imprimir(arbolDecision.raiz, "Árbol de decisión"); break;
                case 6: VisualizadorArbol.imprimir(arbolPlagas.raiz, "Árbol taxonómico de plagas"); break;
                case 7: grafoPlagas.imprimir(); break;
                case 8: grafoPropagacion.imprimir(); break;
                case 9: mostrarEstadisticas(); break;
                case 0: System.out.println("Saliendo..."); break;
                default: System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 0);
    }

    static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }

    static boolean leerSiNo(String pregunta) {
    while (true) {
        System.out.print(pregunta);
        String resp = scanner.nextLine().trim().toLowerCase();
        if (resp.equals("s")) return true;
        if (resp.equals("n")) return false;
        System.out.println("Respuesta no válida. Use 's' o 'n'.");
    }
}

    static double leerDecimal(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Double.parseDouble(scanner.nextLine().trim().replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }

    static void cargarGrafo() {
        for (String cultivo : nombresCultivos) grafoPlagas.agregarVertice(cultivo);
        for (String suelo : nombresSuelos) grafoPlagas.agregarVertice(suelo);
        for (String plaga : nombresPlagas) grafoPlagas.agregarVertice(plaga);

        grafoPlagas.conectar("Broca", "Café Caturra");
        grafoPlagas.conectar("Broca", "Café Castillo");
        grafoPlagas.conectar("Roya", "Café Caturra");
        grafoPlagas.conectar("Ojo de gallo", "Café Caturra");
        grafoPlagas.conectar("Ojo de gallo", "Café Castillo");
        grafoPlagas.conectar("Sigatoka", "Plátano");
        grafoPlagas.conectar("Broca", "Franco");
        grafoPlagas.conectar("Broca", "Arenoso");
        grafoPlagas.conectar("Roya", "Franco");
        grafoPlagas.conectar("Ojo de gallo", "Arcilloso");
        grafoPlagas.conectar("Sigatoka", "Arcilloso");
        grafoPlagas.conectar("Sigatoka", "Franco");
    }

    static void cargarArboles() {
        arbolRiesgos.construir();
        arbolDecision.construir();
        arbolPlagas.construir();
    }

    static String elegir(String titulo, String[] opciones) {
        System.out.println(titulo + ":");
        for (int i = 0; i < opciones.length; i++) {
            System.out.println("  " + (i + 1) + ". " + opciones[i]);
        }
        while (true) {
            int op = leerEntero("Seleccione una opción: ");
            if (op >= 1 && op <= opciones.length) {
                return opciones[op - 1];
            }
            System.out.println("Opción no válida. Intente de nuevo.");
        }
    }

    static void registrarParcela() {
        System.out.print("Nombre de la parcela: ");
        String nombre = scanner.nextLine();
        String cultivo = elegir("Cultivo", nombresCultivos);
        String suelo = elegir("Tipo de suelo", nombresSuelos);
        double ph = leerDecimal("pH del suelo: ");
        int calidadAgua = leerEntero("Calidad del agua (0-100): ");
        int aplicaciones = leerEntero("Aplicaciones de agroquímicos al mes: ");
        ArrayList<String> plagasDetectadas = new ArrayList<>();
        for (String plaga : nombresPlagas) {
            if (leerSiNo("¿Se detectó " + plaga + "? (s/n): ")) {
                plagasDetectadas.add(plaga);
            }
        }
        Parcela nueva = new Parcela(nombre, cultivo, suelo, ph, calidadAgua, aplicaciones, plagasDetectadas);
        listaParcelas.agregar(nueva);
        grafoPropagacion.agregarParcela(nueva);
        System.out.println("Parcela registrada correctamente.");
    }

    static void cargarCasosDePrueba() {
        ArrayList<String> plagasEsperanza = new ArrayList<>();
        plagasEsperanza.add("Broca");
        plagasEsperanza.add("Roya");
        Parcela p1 = new Parcela("La Esperanza", "Café Caturra", "Franco", 4.2, 40, 6, plagasEsperanza);
        listaParcelas.agregar(p1);
        grafoPropagacion.agregarParcela(p1);

        ArrayList<String> plagasMirador = new ArrayList<>();
        plagasMirador.add("Ojo de gallo");
        Parcela p2 = new Parcela("El Mirador", "Café Castillo", "Arcilloso", 5.0, 70, 2, plagasMirador);
        listaParcelas.agregar(p2);
        grafoPropagacion.agregarParcela(p2);

        Parcela p3 = new Parcela("El Platanal", "Plátano", "Franco", 5.2, 85, 1, new ArrayList<>());
        listaParcelas.agregar(p3);
        grafoPropagacion.agregarParcela(p3);

        System.out.println("Casos de prueba cargados: La Esperanza, El Mirador, El Platanal.");
    }

    static void mostrarEstadisticas() {
        System.out.println("\n--- ESTADÍSTICAS DE LAS ESTRUCTURAS ---");
        System.out.println("Parcelas registradas: " + listaParcelas.contar());
        System.out.println("Vértices en grafo de plagas: " + grafoPlagas.vertices.size());
        System.out.println("Vértices en grafo de propagación: " + grafoPropagacion.parcelas.size());
        System.out.println("Nodos en árbol de recomendaciones: " + arbolRiesgos.raiz.contarNodos());
        System.out.println("Altura del árbol de recomendaciones: " + arbolRiesgos.raiz.altura());
        System.out.println("Nodos en árbol de decisión: " + arbolDecision.raiz.contarNodos());
        System.out.println("Nodos en árbol de plagas: " + arbolPlagas.raiz.contarNodos());
    }

    static void mostrarReporte() {
        if (listaParcelas.cabeza == null) {
            System.out.println("No hay parcelas registradas");
            return;
        }
        NodoParcela actual = listaParcelas.cabeza;
        do {
            analizar(actual.dato);
            actual = actual.siguiente;
        } while (actual != listaParcelas.cabeza);
    }

    static void analizar(Parcela parcela) {
        int puntaje = arbolDecision.calcularRiesgo(parcela, grafoPlagas);
        ArrayList<String> areas = new ArrayList<>();
        areas.add("General");
        for (String plaga : parcela.plagasDetectadas) {
            if (grafoPlagas.estanConectados(plaga, parcela.cultivo)) {
                areas.add("Plagas");
                break;
            }
        }
        if (parcela.ph < 4.5 || parcela.ph > 5.5) areas.add("Suelo");
        if (parcela.calidadAgua < 50) areas.add("Agua");
        if (parcela.aplicacionesAgroquimicos > 4) areas.add("Agroquímicos");

        String nivel = puntaje >= 60 ? "ALTO" : puntaje >= 30 ? "MEDIO" : "BAJO";

        System.out.println("\n--- Parcela: " + parcela.nombre + " (" + parcela.cultivo + ", suelo " + parcela.suelo + ") ---");
        System.out.println("Plagas detectadas: " + parcela.plagasDetectadas);
        System.out.println("Puntaje de riesgo: " + puntaje + " - Nivel " + nivel);

        ArrayList<String> vigilar = arbolPlagas.plagasCompatibles(parcela.cultivo, parcela.suelo, grafoPlagas, parcela.plagasDetectadas);
        if (!vigilar.isEmpty()) {
            System.out.println("Plagas a vigilar (afectan " + parcela.cultivo + " en suelo " + parcela.suelo + "): " + vigilar);
        }

        arbolRiesgos.mostrarRecomendaciones(nivel, areas);

        ArrayList<String> vecinas = grafoPropagacion.parcelasVecinasConRiesgo(parcela, arbolDecision, grafoPlagas);
        if (!vecinas.isEmpty()) {
            System.out.println("Posible propagación hacia: " + vecinas);
        }
    }
}

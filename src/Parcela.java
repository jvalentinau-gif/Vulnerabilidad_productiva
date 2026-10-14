import java.util.ArrayList;

public class Parcela {
    String nombre;
    String cultivo;
    String suelo;
    double ph;
    int calidadAgua;
    int aplicacionesAgroquimicos;
    ArrayList<String> plagasDetectadas;

    Parcela(String nombre, String cultivo, String suelo, double ph, int calidadAgua,
            int aplicacionesAgroquimicos, ArrayList<String> plagasDetectadas) {
        this.nombre = nombre;
        this.cultivo = cultivo;
        this.suelo = suelo;
        this.ph = ph;
        this.calidadAgua = calidadAgua;
        this.aplicacionesAgroquimicos = aplicacionesAgroquimicos;
        this.plagasDetectadas = plagasDetectadas;
    }
}

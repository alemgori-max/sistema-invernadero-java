public class Sensor {

    private String id;
    private String ubicacion;
    private boolean activo;
    private double ultimaMedicion;

    public Sensor(String id, String ubicacion) {
        this.id = id;
        this.ubicacion = ubicacion;
        this.activo = true;
        this.ultimaMedicion = 0;
    }

    public String getId() {
        return id;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public boolean isActivo() {
        return activo;
    }

    public double getUltimaMedicion() {
        return ultimaMedicion;
    }

    public void activar() {
        activo = true;
    }

    public void desactivar() {
        activo = false;
    }

    // Solo registra la medición si el sensor está activo.
    public boolean registrarMedicion(double valor) {
        if (!activo) {
            return false;
        }
        ultimaMedicion = valor;
        return true;
    }

    public String getUnidad() {
        return "";
    }

    // Comportamiento general: será sobrescrito en cada subclase.
    public String evaluarMedicion() {
        return "Medición sin interpretar";
    }

    public String mostrarInformacion() {
        return id + " | " + ubicacion + " | " + (activo ? "activo" : "inactivo")
                + " | " + ultimaMedicion + " " + getUnidad();
    }
}

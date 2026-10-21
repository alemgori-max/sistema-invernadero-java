public class SistemaRiego {

    private boolean activo;

    public SistemaRiego() {
        this.activo = false;
    }

    public void activar() {
        activo = true;
    }

    public void desactivar() {
        activo = false;
    }

    public boolean estaActivo() {
        return activo;
    }

    // El riego USA la información del sensor; no hereda de él.
    public void regularSegun(SensorHumedadSuelo suelo) {
        if (suelo.requiereRiego()) {
            activar();
        } else {
            desactivar();
        }
    }

    public String mostrarEstado() {
        return "Sistema de riego: " + (activo ? "ACTIVO" : "INACTIVO");
    }
}

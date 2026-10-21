public class SensorHumedadSuelo extends Sensor {

    public SensorHumedadSuelo(String id, String ubicacion) {
        super(id, ubicacion);
    }

    @Override
    public String getUnidad() {
        return "%";
    }

    @Override
    public String evaluarMedicion() {
        double h = getUltimaMedicion();
        if (h < 30) {
            return "Humedad del suelo baja";
        } else if (h <= 70) {
            return "Humedad del suelo adecuada";
        }
        return "Humedad del suelo alta";
    }

    // Comportamiento exclusivo de este sensor.
    public boolean requiereRiego() {
        return getUltimaMedicion() < 30;
    }
}

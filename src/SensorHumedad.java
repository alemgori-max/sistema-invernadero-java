public class SensorHumedad extends Sensor {

    public SensorHumedad(String id, String ubicacion) {
        super(id, ubicacion);
    }

    @Override
    public String getUnidad() {
        return "%";
    }

    @Override
    public String evaluarMedicion() {
        double h = getUltimaMedicion();
        if (h < 40) {
            return "Humedad ambiental baja";
        } else if (h <= 70) {
            return "Humedad ambiental adecuada";
        }
        return "Humedad ambiental alta";
    }
}

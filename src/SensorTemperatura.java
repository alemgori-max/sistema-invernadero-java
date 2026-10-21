public class SensorTemperatura extends Sensor {

    public SensorTemperatura(String id, String ubicacion) {
        super(id, ubicacion);
    }

    @Override
    public String getUnidad() {
        return "°C";
    }

    @Override
    public String evaluarMedicion() {
        double t = getUltimaMedicion();
        if (t < 18) {
            return "Temperatura baja";
        } else if (t <= 30) {
            return "Temperatura adecuada";
        }
        return "Temperatura alta";
    }
}

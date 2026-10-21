public class Main {

    public static void main(String[] args) {
        System.out.println("Sistema de invernadero inteligente");

        Sensor s1 = new SensorTemperatura("T-01", "zona norte");
        Sensor s2 = new SensorHumedad("H-01", "zona central");
        Sensor s3 = new SensorHumedadSuelo("HS-01", "zona sur");
        Sensor[] sensores = {s1, s2, s3};

        s1.registrarMedicion(32);
        s2.registrarMedicion(55);
        s3.registrarMedicion(22);

        for (Sensor sensor : sensores) {
            System.out.println(sensor.mostrarInformacion()
                    + "  ->  " + sensor.evaluarMedicion());
        }
    }
}

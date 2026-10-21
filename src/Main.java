public class Main {

    public static void main(String[] args) {
        System.out.println("Sistema de invernadero inteligente");
        System.out.println("==================================");

        // Referencias Sensor, objetos de distintas subclases
        Sensor s1 = new SensorTemperatura("T-01", "zona norte");
        Sensor s2 = new SensorHumedad("H-01", "zona central");
        Sensor s3 = new SensorHumedadSuelo("HS-01", "zona sur");
        Sensor[] sensores = {s1, s2, s3};

        simular(sensores, 32, 55, 22, "Escenario 1");
        simular(sensores, 24, 62, 51, "Escenario 2");
    }

    private static void simular(Sensor[] sensores,
                                double temp, double hum, double suelo, String titulo) {
        System.out.println("\n--- " + titulo + " ---");
        sensores[0].registrarMedicion(temp);
        sensores[1].registrarMedicion(hum);
        sensores[2].registrarMedicion(suelo);

        // Polimorfismo + enlace dinámico: sin if/else por tipo
        for (Sensor sensor : sensores) {
            System.out.println(sensor.mostrarInformacion()
                    + "  ->  " + sensor.evaluarMedicion());
        }

        // instanceof + casting para comportamiento específico
        for (Sensor sensor : sensores) {
            if (sensor instanceof SensorTemperatura) {
                SensorTemperatura t = (SensorTemperatura) sensor;
                System.out.println("Temperatura en °F: " + t.convertirAFahrenheit());
            } else if (sensor instanceof SensorHumedadSuelo) {
                SensorHumedadSuelo s = (SensorHumedadSuelo) sensor;
                System.out.println("¿Requiere riego? " + s.requiereRiego());
            }
        }
    }
}

public class PruebasInvernadero {

    private static int ok = 0;
    private static int fallos = 0;

    public static void main(String[] args) {
        // 1. Estado inicial
        Sensor t = new SensorTemperatura("T-01", "norte");
        Sensor h = new SensorHumedad("H-01", "centro");
        Sensor hs = new SensorHumedadSuelo("HS-01", "sur");
        verificar("Crear sensores: id", "T-01", t.getId());
        verificar("Crear sensores: ubicación", "centro", h.getUbicacion());
        verificar("Crear sensores: activo", "true", String.valueOf(hs.isActivo()));

        // 2. Temperatura
        evaluar(t, 15, "Temperatura baja");
        evaluar(t, 25, "Temperatura adecuada");
        evaluar(t, 34, "Temperatura alta");
        // 3. Humedad ambiental
        evaluar(h, 32, "Humedad ambiental baja");
        evaluar(h, 55, "Humedad ambiental adecuada");
        evaluar(h, 82, "Humedad ambiental alta");
        // 4. Humedad del suelo
        evaluar(hs, 18, "Humedad del suelo baja");
        evaluar(hs, 48, "Humedad del suelo adecuada");
        evaluar(hs, 83, "Humedad del suelo alta");

        // 5. El mismo valor (32) se interpreta distinto según el tipo
        t.registrarMedicion(32);
        h.registrarMedicion(32);
        hs.registrarMedicion(32);
        Sensor[] sensores = {t, h, hs};
        String[] esperado = {"Temperatura alta", "Humedad ambiental baja",
                             "Humedad del suelo adecuada"};
        for (int i = 0; i < sensores.length; i++) {
            verificar("Polimorfismo " + sensores[i].getId(),
                    esperado[i], sensores[i].evaluarMedicion());
        }

        // 6. instanceof + casting
        verificar("instanceof SensorTemperatura", "true", String.valueOf(t instanceof SensorTemperatura));
        verificar("instanceof falso", "false", String.valueOf(h instanceof SensorTemperatura));
        if (t instanceof SensorTemperatura) {
            SensorTemperatura st = (SensorTemperatura) t;
            verificar("Casting: 32 °C en °F", "89.6", String.valueOf(st.convertirAFahrenheit()));
        }

        // 7. Riego
        SistemaRiego riego = new SistemaRiego();
        SensorHumedadSuelo suelo = new SensorHumedadSuelo("HS-02", "sur");
        suelo.registrarMedicion(22);
        riego.regularSegun(suelo);
        verificar("Suelo bajo: riego activo", "true", String.valueOf(riego.estaActivo()));
        suelo.registrarMedicion(55);
        riego.regularSegun(suelo);
        verificar("Suelo adecuado: riego inactivo", "false", String.valueOf(riego.estaActivo()));
        suelo.registrarMedicion(85);
        riego.activar();
        riego.regularSegun(suelo);
        verificar("Suelo alto: riego inactivo", "false", String.valueOf(riego.estaActivo()));

        // 8. Sensor inactivo no registra
        suelo.desactivar();
        verificar("Sensor inactivo no registra", "false", String.valueOf(suelo.registrarMedicion(10)));

        System.out.println("\nResultado: " + ok + " correctas, " + fallos + " fallidas");
    }

    private static void evaluar(Sensor s, double valor, String esperado) {
        s.registrarMedicion(valor);
        verificar(s.getId() + " = " + valor, esperado, s.evaluarMedicion());
    }

    private static void verificar(String caso, String esperado, String obtenido) {
        boolean pasa = esperado.equals(obtenido);
        if (pasa) ok++; else fallos++;
        System.out.println((pasa ? "[OK]    " : "[FALLO] ") + caso
                + (pasa ? "" : " | esperado: " + esperado + " | obtenido: " + obtenido));
    }
}

# SistemaInvernadero

Aplicación de consola en Java que simula un invernadero inteligente: tres sensores (temperatura, humedad ambiental y humedad del suelo) y un sistema de riego que se activa según la humedad del suelo. Práctica 5: herencia y polimorfismo.

## Clases implementadas

| Clase | Responsabilidad |
|---|---|
| `Sensor` | Superclase. Guarda id, ubicación, estado y última medición; registra mediciones y muestra su información |
| `SensorTemperatura` | Interpreta °C (baja < 18, adecuada 18–30, alta > 30); convierte a °F |
| `SensorHumedad` | Interpreta humedad ambiental (baja < 40, adecuada 40–70, alta > 70) |
| `SensorHumedadSuelo` | Interpreta humedad del suelo (baja < 30, adecuada 30–70, alta > 70); indica si requiere riego |
| `SistemaRiego` | Activa/desactiva el riego, informa su estado y decide según el suelo |
| `Main` | Demuestra el sistema con dos escenarios |
| `PruebasInvernadero` | Ejecuta los casos de prueba |

## Cómo ejecutar

1. Abrir la carpeta del proyecto en IntelliJ IDEA (JDK 17 o superior).
2. Ejecutar `Main` para la demostración y `PruebasInvernadero` para las pruebas.

Por terminal: `javac -d out src/*.java && java -cp out Main`

## Conceptos de POO aplicados

| Concepto | Dónde se observa |
|---|---|
| Encapsulación | Atributos `private` en `Sensor` y `SistemaRiego`; `Main` solo cambia el estado con `registrarMedicion()`, `activar()`, `desactivar()` |
| Herencia | `SensorTemperatura`, `SensorHumedad` y `SensorHumedadSuelo` extienden `Sensor` y reutilizan id, ubicación, estado y medición |
| `super` | En cada constructor de subclase: `super(id, ubicacion)` |
| Sobrescritura | `evaluarMedicion()` y `getUnidad()` con `@Override` en las tres subclases |
| Polimorfismo | En `Main`, `Sensor[] sensores` se recorre con `for (Sensor sensor : sensores)` |
| Enlace dinámico | `sensor.evaluarMedicion()` ejecuta la versión del objeto real (con 32: temperatura alta, humedad baja o suelo adecuado) |
| `instanceof` | En `Main`, para detectar `SensorTemperatura` y `SensorHumedadSuelo` dentro de la colección |
| Casting | Tras `instanceof`, `(SensorTemperatura) sensor` y `(SensorHumedadSuelo) sensor` para usar `convertirAFahrenheit()` y `requiereRiego()` |

## Pruebas realizadas

`PruebasInvernadero` ejecuta 22 verificaciones (22 correctas): creación de sensores, rangos bajo/adecuado/alto de los tres sensores, procesamiento polimórfico, `instanceof`, casting, riego con suelo bajo/adecuado/alto y sensor inactivo. Detalle en el documento de análisis.

## Documentación

[Análisis y diseño](docs/01-analisis-diseno.md) · [UML inicial](docs/uml-inicial.png) · [UML final](docs/uml-final.png)

## Conclusión

Separar lo común (superclase `Sensor`) de lo particular (criterios de cada tipo) evitó duplicar código y permitió procesar todos los sensores con el mismo tipo. El riego quedó fuera de la jerarquía porque actúa en lugar de medir, y colabora con el sensor de suelo solo mediante `regularSegun()`.

## Reflexión final

- **Superclase y subclases:** `Sensor`; `SensorTemperatura`, `SensorHumedad` y `SensorHumedadSuelo`.
- **Por qué herencia:** cada una *es un* sensor y comparte sus datos y acciones.
- **En la superclase:** id, ubicación, estado, última medición, `registrarMedicion()`, `activar()`, `desactivar()` y `mostrarInformacion()`, porque son iguales para todos.
- **Para qué `super`:** para que el constructor de `Sensor` inicialice sus atributos `private`.
- **Método sobrescrito:** `evaluarMedicion()` (y `getUnidad()`).
- **Polimorfismo:** en el ciclo sobre `Sensor[]` en `Main`.
- **Enlace dinámico:** en la llamada `sensor.evaluarMedicion()` de ese ciclo, que decide la versión en ejecución.
- **`instanceof` y casting:** para usar `convertirAFahrenheit()` y `requiereRiego()`, que solo existen en una subclase.
- **Cambio de UML:** se agregó `getUnidad()` y `SistemaRiego.regularSegun()`, y la dependencia del riego pasó de `Sensor` a `SensorHumedadSuelo`.
- **Agregar `SensorLuminosidad`:** crear la clase con `extends Sensor`, constructor con `super`, y sobrescribir `getUnidad()` y `evaluarMedicion()`; luego agregarla al arreglo en `Main`. `Sensor`, las otras subclases y `SistemaRiego` no cambian.

# Análisis y diseño

## 1. Descripción del problema

Voy a representar un invernadero inteligente simplificado que funciona con el ciclo **observar → evaluar → actuar**.

- **Información que maneja:** de cada sensor, su identificación, ubicación, estado (activo/inactivo) y última medición simulada (°C o %); del riego, su estado (ACTIVO/INACTIVO).
- **Elementos que intervienen:** tres sensores (temperatura del aire, humedad ambiental, humedad del suelo) y un sistema de riego.
- **Operaciones generales:** registrar una medición, interpretarla según el tipo de sensor (baja/adecuada/alta), consultar la información del sensor, activar/desactivar el riego y decidir el riego a partir de la humedad del suelo.

El mismo número significa cosas distintas según la variable (32 es temperatura alta, humedad ambiental baja o humedad del suelo adecuada), por eso la interpretación depende del tipo de sensor.

## 2. Identificación de objetos

| Objeto | Qué representa | Por qué es objeto | Responsabilidad |
|---|---|---|---|
| Sensor (general) | Cualquier sensor del invernadero | Tiene identidad, estado y comportamiento comunes | Guardar id, ubicación, estado y última medición; registrar medición |
| Sensor de temperatura | Sensor del aire en °C | Interpreta con criterios propios | Evaluar temperatura baja/adecuada/alta |
| Sensor de humedad ambiental | Sensor de humedad del aire en % | Interpreta con criterios propios | Evaluar humedad ambiental |
| Sensor de humedad del suelo | Sensor del suelo en % | Interpreta con criterios propios y sirve para decidir el riego | Evaluar humedad del suelo e indicar si requiere riego |
| Sistema de riego | Dispositivo que suministra agua | Tiene estado propio y acciones propias | Activar, desactivar y reportar su estado |

El invernadero como tal no lo modelo como clase: `Main` coordina los objetos en esta práctica.

## 3. Estado y comportamiento

| Objeto propuesto | Responsabilidad | Información que debe conservar | Comportamientos que debe realizar |
|---|---|---|---|
| Sensor | Representar lo común a todos los sensores | Identificación, ubicación, estado, última medición | Permitir conocer sus datos, activarse/desactivarse, registrar una medición, interpretar su medición, mostrar su información |
| Sensor de temperatura | Interpretar temperatura | (lo heredado) | Interpretar con rangos de °C; expresar su medición en °F |
| Sensor de humedad ambiental | Interpretar humedad del aire | (lo heredado) | Interpretar con rangos de % de aire |
| Sensor de humedad del suelo | Interpretar humedad del suelo | (lo heredado) | Interpretar con rangos de suelo; indicar si requiere riego |
| Sistema de riego | Suministrar agua | Si está activo o inactivo | Activarse, detenerse, informar su estado, decidir con base en la humedad del suelo |

## 4. Características comunes y especialización

- **Información en común:** identificación, ubicación, estado activo/inactivo y última medición.
- **Comportamientos en común:** registrar una medición, consultarla, activarse/desactivarse y mostrar su información.
- **Lo que cambia según el tipo:** la unidad (°C o %), los criterios de interpretación y los comportamientos particulares.
- **Concepto general:** *Sensor*.
- **Especializaciones:** sensor de temperatura, de humedad ambiental y de humedad del suelo.

## 5. Relaciones entre objetos

| Relación | Tipo | ¿Herencia? |
|---|---|---|
| Sensor de temperatura **ES UN** Sensor | Generalización/especialización | Sí |
| Sensor de humedad ambiental **ES UN** Sensor | Generalización/especialización | Sí |
| Sensor de humedad del suelo **ES UN** Sensor | Generalización/especialización | Sí |
| Sistema de riego **UTILIZA** información del sensor de humedad del suelo | Colaboración | No |

- **Colaboran:** el sensor de humedad del suelo y el sistema de riego. El riego necesita saber si el suelo requiere riego; el sensor no necesita conocer al riego.
- **No se duplica:** id, ubicación, estado y medición viven solo en la superclase; la decisión de regar vive en el riego, no en el sensor.

**¿Por qué `SistemaRiego` no debería ser subclase de `Sensor`?** Porque un sensor *observa* (produce y interpreta una medición) y el riego *actúa* (suministra agua). El riego no tiene ubicación de medición ni última medición ni interpretación; heredar le daría atributos y métodos que no le corresponden. La relación correcta es "utiliza", no "es un".

## 6. Diseño de clases

| Clase | Atributos propuestos | Tipo de dato | Métodos propuestos | Responsabilidad |
|---|---|---|---|---|
| Sensor | `- id`, `- ubicacion`, `- activo`, `- ultimaMedicion` | String, String, boolean, double | `Sensor(id, ubicacion)`, getters, `activar()`, `desactivar()`, `registrarMedicion(double)`, `evaluarMedicion()`, `mostrarInformacion()` | Datos y comportamiento comunes |
| SensorTemperatura | (ninguno propio) | — | constructor, `@Override evaluarMedicion()`, `convertirAFahrenheit()` | Interpretar °C |
| SensorHumedad | (ninguno propio) | — | constructor, `@Override evaluarMedicion()` | Interpretar humedad ambiental |
| SensorHumedadSuelo | (ninguno propio) | — | constructor, `@Override evaluarMedicion()`, `requiereRiego()` | Interpretar suelo e indicar necesidad de riego |
| SistemaRiego | `- activo` | boolean | `SistemaRiego()`, `activar()`, `desactivar()`, `estaActivo()`, `mostrarEstado()` | Estado y acciones del riego |

- **Private:** todos los atributos; solo se cambian con métodos (`registrarMedicion`, `activar`, `desactivar`).
- **Por constructor:** `id` y `ubicacion` en los sensores. El sensor inicia activo con medición 0; el riego inicia inactivo.
- **Consultables:** mediante getters (`getId`, `getUltimaMedicion`, `isActivo`, `estaActivo`).
- **En la clase general:** lo común. **Solo en subclases:** criterios de interpretación y comportamientos particulares.

## 7. Jerarquía de clases

- **Jerarquía:** `Sensor` ← `SensorTemperatura`, `SensorHumedad`, `SensorHumedadSuelo`.
- **Solo colabora:** `SistemaRiego` usa información del sensor de humedad del suelo.

## Diagrama UML inicial

![Diagrama UML inicial](uml-inicial.png)

## 8. Justificación del diseño

- **Por qué estas clases:** una por cada elemento del problema con responsabilidad propia, más una superclase para lo compartido.
- **Responsabilidad principal:** `Sensor` guarda y registra datos; cada subclase interpreta su variable; `SistemaRiego` controla el agua.
- **Atributos private:** para que no se modifiquen desde `Main` (por ejemplo `sensor.ultimaMedicion = 25`) y el objeto controle su estado.
- **Constructores:** `id` y `ubicacion`; las subclases los pasan con `super(...)`.
- **Superclase:** `Sensor`. **Subclases:** `SensorTemperatura`, `SensorHumedad`, `SensorHumedadSuelo`.
- **Por qué "es un":** cada una es un tipo de sensor y tiene todo lo que un sensor tiene.
- **En la superclase:** id, ubicación, estado, medición y su manejo. **En las subclases:** interpretación y comportamientos propios.
- **Por qué `SistemaRiego` no está en la jerarquía:** actúa, no mide (ver sección 5).
- **Evitar duplicación:** ninguna subclase redeclara atributos; la decisión de riego no se repite en el sensor.

## 9. Constructores y super

- **¿Los constructores de la superclase se heredan?** No. Cada subclase declara el suyo.
- **¿Qué constructor se ejecuta primero?** El de la superclase `Sensor`, y después el de la subclase.
- **¿Para qué usé `super(id, ubicacion)`?** Para inicializar la parte `Sensor` del objeto (id, ubicación, estado, medición) sin repetir esa asignación en cada subclase; además los atributos son `private` y las subclases no pueden asignarlos directamente.

## 10. Sobrescritura

- **¿Qué método sobrescribí?** `evaluarMedicion()` (y también `getUnidad()`, para mostrar la unidad correcta).
- **¿Qué indica `@Override`?** Que el método reemplaza uno de la superclase; el compilador marca error si la firma no coincide.
- **¿Por qué el mismo nombre?** Porque la acción es la misma (interpretar la medición) aunque el criterio cambie por tipo.
- **Ventaja:** desde cualquier `Sensor` basta llamar `evaluarMedicion()`, sin conocer su tipo ni tener métodos distintos (`evaluarTemp`, `evaluarSuelo`...). Agregar un sensor nuevo no obliga a cambiar el código que ya los procesa.

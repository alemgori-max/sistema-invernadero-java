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

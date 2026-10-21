# Análisis y diseño

## 1. Descripción del problema

Voy a representar un invernadero inteligente simplificado que funciona con el ciclo **observar → evaluar → actuar**.

- **Información que maneja:** de cada sensor, su identificación, ubicación, estado (activo/inactivo) y última medición simulada (°C o %); del riego, su estado (ACTIVO/INACTIVO).
- **Elementos que intervienen:** tres sensores (temperatura del aire, humedad ambiental, humedad del suelo) y un sistema de riego.
- **Operaciones generales:** registrar una medición, interpretarla según el tipo de sensor (baja/adecuada/alta), consultar la información del sensor, activar/desactivar el riego y decidir el riego a partir de la humedad del suelo.

El mismo número significa cosas distintas según la variable (32 es temperatura alta, humedad ambiental baja o humedad del suelo adecuada), por eso la interpretación depende del tipo de sensor.

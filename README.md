# Personal Software Process (PSP) — Estudiante 03

Portafolio del curso de **Personal Software Process**, la metodología del SEI (Software Engineering Institute) para medir y mejorar el desempeño individual del ingeniero de software. Universidad Autónoma de Zacatecas, agosto–diciembre 2022.

Lo que distingue a este repositorio de un repositorio de tareas cualquiera es que **cada programa viene acompañado de su expediente completo de proceso**: plan estimado, registro de tiempos y defectos, reporte de pruebas, listas de verificación de revisión y postmortem. El código es apenas una de las siete entregas por programa.

---

## La metodología PSP

PSP obliga a tratar el propio trabajo como un proceso medible. Por cada programa se registra:

1. **Estimación previa** — tamaño (LOC) y tiempo esperados, usando el método **PROBE** apoyado en datos históricos propios.
2. **Registro de tiempos** — minutos reales invertidos por fase: planeación, diseño, revisión de diseño, codificación, revisión de código, compilación, pruebas y postmortem.
3. **Registro de defectos** — cada defecto con su tipo, la fase donde se **inyectó** y la fase donde se **eliminó**.
4. **Listas de verificación** — revisiones de diseño y de código guiadas por checklist, para atrapar defectos antes de compilar.
5. **Postmortem** — comparación entre lo estimado y lo real, y ajuste de los datos históricos para el siguiente ciclo.

El objetivo del ciclo es concreto: reducir la densidad de defectos y estrechar el error de estimación conforme avanzan los programas.

---

## Programas desarrollados

| # | Programa | Qué calcula | Por qué importa en PSP |
|---|---|---|---|
| **2** | Contador de líneas de código | Cuenta LOC de archivos fuente, distinguiendo código de comentarios y líneas en blanco | Es la **herramienta de medición** del propio proceso: sin conteo confiable de LOC no hay estimación PROBE |
| **3** | Coeficiente de correlación | Correlación lineal entre dos conjuntos de datos leídos de archivo | Base estadística de PROBE: mide qué tan buena es una variable como predictor de tamaño |
| **4** | Integración numérica (Simpson) | Integral definida entre dos límites por la **regla de Simpson** | Necesario para evaluar la distribución t de Student |
| **5** | Inversión de la integral | Encuentra el valor de *x* tal que la integral de 0 a *x* alcanza un valor dado | Búsqueda numérica sobre el programa 4; da los valores críticos de la distribución |
| **6** | Intervalos de predicción | Intervalos de predicción para las estimaciones de tamaño y tiempo | Cierra el ciclo: cuantifica la **incertidumbre** de las propias estimaciones |

Los programas se construyen unos sobre otros — el 5 usa el 4, el 6 usa el 3 y el 5 — reflejando la idea de PSP de acumular una base de herramientas reutilizables.

---

## Estructura del repositorio

Cada carpeta corresponde a un programa y sigue la nomenclatura del curso `03_<Entregable>_Programa<N>_E<Ejercicio>_A22`:

```
03_ProgramaN_EX_A22/
├── 03_Code_ProgramaN.java          # Código fuente
├── 03_PIP_ProgramaN.pdf            # Project Plan Summary — estimaciones y datos reales
├── 03_Res_ProgramaN.pdf            # Resultados y postmortem
├── 03_TestReport_ProgramaN.pdf     # Reporte de pruebas
├── 03_Checklist1/2_ProgramaN.pdf   # Listas de verificación de revisión (diseño y código)
├── 03_FunctionalTemplate.pdf       # Especificación funcional      ┐
├── 03_LogicalTemplate.pdf          # Especificación lógica         ├ (Programa 6)
├── 03_OperationalTemplate.pdf      # Escenarios operacionales      ┘
└── 03_Tool_ProgramaN.zip           # Herramientas de registro (tiempos y defectos)
```

```
.
├── 03_Programa2_E2_A22/   # Contador de LOC
├── 03_Programa3_E1_A22/   # Coeficiente de correlación
├── 03_Programa4_E1_A22/   # Integración por regla de Simpson
├── 03_Programa5_E1_A22/   # Inversión de la integral (+ revision_plan.zip)
└── 03_Programa6_E1_A22/   # Intervalos de predicción
```

---

## Cómo ejecutar los programas

Todos son aplicaciones Java de consola sin dependencias externas:

```bash
cd 03_Programa3_E1_A22
javac 03_Code_Programa3_E3_A22.java
java Programa3
```

Los programas que leen datos desde archivo solicitan la ruta por entrada estándar.

---

## Stack

`Java` · `PSP (SEI)` · `Métodos numéricos` · `Estadística aplicada`

---

## Autor

**Adalberto Cerrillo Vázquez** — Ingeniería de Software, Universidad Autónoma de Zacatecas.

# MyWinaryAPP - Actividad 2.2: Threads y Sensores

Este repositorio contiene la implementación de la **Actividad 2.2: Threads y Sensores** para el proyecto MyWinaryAPP[cite: 1]. El objetivo principal de esta etapa es mejorar el rendimiento y la interactividad de la aplicación mediante la programación de subprocesos y la integración de hardware del dispositivo[cite: 1].

## 🛠️ ¿Qué se implementó en esta etapa?

Durante el desarrollo de esta actividad, se realizaron las siguientes integraciones en `MainActivity4`:

1. **Gestión de la Interfaz de Usuario (UI):**
   - Se diseñó una interfaz limpia utilizando `ConstraintLayout` con un `ImageView` y un `Button`.
   - Se aplicaron buenas prácticas de desarrollo Android, como la extracción de textos al archivo `strings.xml` y la optimización de recursos gráficos en la carpeta `drawable`.

2. **Implementación de Threads (Subprocesos):**
   - Se delegaron tareas simuladas de descarga de imágenes a hilos secundarios (`Background Thread`) para evitar el bloqueo del hilo principal (`UI Thread`)[cite: 1, 2].
   - Se utilizó el método `post()` para actualizar la interfaz gráfica de forma segura una vez que el hilo secundario finaliza su trabajo[cite: 1].

3. **Integración de Sensores:**
   - Se implementó `SensorManager` para acceder al hardware del dispositivo[cite: 1].
   - Se capturaron los datos tridimensionales (Ejes X, Y, Z) del sensor de orientación `TYPE_ROTATION_VECTOR`[cite: 1].
   - Se gestionó correctamente el ciclo de vida del sensor (`registerListener` en `onResume` y `unregisterListener` en `onPause`) para optimizar el uso de la batería.

4. **Operaciones Asincrónicas con AsyncTask:**
   - Se estructuró una clase interna heredando de `AsyncTask` para gestionar tareas cortas, pasando por sus fases principales: `onPreExecute`, `doInBackground` y `onPostExecute`[cite: 1, 2].

---

## 📝 Preguntas de Cierre

A continuación, se presentan las reflexiones finales sobre los aprendizajes de esta actividad[cite: 1]:

**1. ¿Qué nuevos conocimientos y habilidades has adquirido en el manejo de subprocesos y captura de datos de sensores?**[cite: 1]
He aprendido a separar la lógica pesada del hilo principal utilizando tanto `Thread` como `AsyncTask`, comprendiendo que esta delegación es vital para evitar errores de bloqueo (ANR) y mantener la fluidez de la interfaz gráfica[cite: 2]. Además, adquirí la habilidad de inicializar y capturar datos en tiempo real de hardware integrado, específicamente leyendo las coordenadas tridimensionales (ejes X, Y, Z) del sensor `TYPE_ROTATION_VECTOR`, e implementando su correcto registro y apagado en los ciclos de vida `onResume` y `onPause` para optimizar el rendimiento y la batería[cite: 1].

**2. ¿Cómo podrías utilizar los Threads y los sensores en otras aplicaciones móviles?**[cite: 1]
Los subprocesos son fundamentales para cualquier aplicación que requiera consumir servicios web, descargar archivos grandes, o interactuar con bases de datos en segundo plano sin congelar la pantalla que ve el usuario[cite: 2]. Por otro lado, los sensores de rotación y movimiento tienen un gran potencial en el desarrollo de videojuegos interactivos, aplicaciones de realidad aumentada, o herramientas deportivas que analicen el movimiento y la postura del teléfono.

**3. ¿Qué desafíos encontraste en el proceso y cómo los superaste?**[cite: 1]
Uno de los principales desafíos técnicos fue la correcta vinculación de la interfaz gráfica en XML con el código Java, ya que inicializar elementos visuales antes de que existan en el `ConstraintLayout` provoca el cierre inesperado de la app. Otro desafío fue adaptarme a las reglas estrictas de recursos de Android, como la prohibición de usar espacios y mayúsculas en los nombres de archivos en la carpeta `drawable`. Superé estos obstáculos analizando los mensajes de advertencia del entorno, corrigiendo la sintaxis de los nombres de archivo y utilizando el *Logcat* para depurar y verificar que el sensor capturaba correctamente los datos.

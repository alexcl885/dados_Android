# Juego de Dados Android 🎲 - Alejandro Copado López

Este proyecto es un juego de dados simple desarrollado para Android en el que el usuario toca una imagen de un vaso para lanzar tres dados. Según las combinaciones obtenidas en los dados, el jugador gana puntos o pierde intentos. El juego termina cuando el jugador gana 3 puntos o pierde tras 3 intentos fallidos.

## 📝 Descripción del Juego

### Reglas del Juego:
1. **Puntuación de los Dados:**
   - Si dos de los tres dados muestran el mismo número, el jugador gana **1 punto**.
   - Si los tres dados muestran el mismo número, el jugador gana **2 puntos**.
   - Si ninguno de los dados coincide, el jugador pierde un intento.

2. **Condiciones de Fin del Juego:**
   - El jugador **gana** si alcanza **3 puntos**.
   - El jugador **pierde** si acumula **3 intentos fallidos**.

3. **Interacción:**
   - El juego se inicia al tocar el vaso de los dados (`ivVaso`).
   - Se puede reiniciar el juego con el botón de reinicio (`btReiniciar`).

## 📂 Estructura del Proyecto

### Archivos Importantes:
- **MainActivity.kt**: Archivo principal donde se maneja la lógica del juego.
- **activity_main.xml**: Interfaz gráfica que incluye el vaso (`ivVaso`), los dados (`ivDadoUno`, `ivDadoDos`, `ivDadoTres`), y los contadores de puntos e intentos.

## 📱 Vista de la Interfaz

La interfaz del juego contiene:
- Un vaso (`ivVaso`) que el usuario toca para lanzar los dados.
- Tres imágenes de dados (`ivDadoUno`, `ivDadoDos`, `ivDadoTres`) que muestran el resultado de la tirada.
- TextViews para mostrar la cantidad de **puntos** obtenidos (`tvNumeroPuntos`) y los **intentos** (`tvNumeroTiradas`).
- Un botón de reinicio (`btReiniciar`) para reiniciar el juego.

## ⚙️ Lógica del Juego (Explicación del Código)

### Funciones Principales:

- **`programarTirada()`**: Inicia la animación del vaso y programar el lanzamiento de los dados después de un breve retraso.

- **`realizarTirada()`**: Realiza la tirada y evalúa los resultados, llamando a `cambiarDados()` para cambiar la imagen de los dados según el número obtenido.

- **`cambiarDados()`**: Genera tres números aleatorios para simular los resultados de los dados. Luego, actualiza los contadores de puntos e intentos según las combinaciones obtenidas:
  - **Tres dados iguales**: +2 puntos
  - **Dos dados iguales**: +1 punto
  - **Ninguno igual**: +1 intento fallido

- **`verificarFinDelJuego()`**: Verifica si el jugador ha alcanzado 3 puntos (gana) o 3 intentos fallidos (pierde). Muestra un mensaje `Toast` para informar al jugador y deshabilita el botón de tirada.

- **`reiniciarJuego()`**: Restablece los puntos e intentos a cero, habilitando de nuevo el botón de tirada para que el jugador pueda empezar una nueva partida.



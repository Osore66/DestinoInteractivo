[![Ask DeepWiki](https://deepwiki.com/badge.svg)](https://deepwiki.com/Osore66/DestinoInteractivo)
# Destino Interactivo

[![Android Studio](https://img.shields.io/badge/Developed%20with-Android%20Studio-3DDC84.svg)](https://developer.android.com/studio)
[![Kotlin](https://img.shields.io/badge/Language-Kotlin-7F52FF.svg)](https://kotlinlang.org/)
[![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack%20Compose-4285F4.svg)](https://developer.android.com/jetpack/compose)

"Destino Interactivo" es una innovadora aplicación móvil Android que redefine la experiencia de las aventuras de texto interactivas, inspirada en los clásicos libros de "Elige tu propia aventura". Sumérgete en narrativas dinámicas donde cada decisión moldea el curso de la historia, con un enfoque especial en un emocionante sistema de combate RPG por turnos.

## 🚀 Descripción del Proyecto

Este proyecto fin de ciclo presenta una aplicación Android desarrollada íntegramente en Kotlin y utilizando Jetpack Compose para la interfaz de usuario. El objetivo principal es ofrecer una experiencia inmersiva y rejugable, enriquecida con componentes gráficos animados y efectos de sonido. Los usuarios toman decisiones que afectan directamente la trama, enfrentándose a desafíos que culminan en batallas estratégicas por turnos. La aplicación guarda el progreso del jugador y permite personalizar la experiencia mediante un menú de opciones.

## ✨ Características Principales

* **Sistema de Narrativa Interactiva:** Historias ramificadas con múltiples finales basados en las decisiones del usuario.
* **Combate RPG por Turnos:** Sistema de combate estratégico que involucra salud, ataque, defensa, uso de pociones y ataques críticos.
* **Persistencia de Datos:** Guardado y carga automática de la partida mediante la base de datos local Room.
* **Gestión de Opciones:** Personaliza la experiencia con ajustes de idioma, volumen de música y efectos, y vibración.
* **Animaciones y Efectos Visuales:** Transiciones fluidas, imágenes animadas (Lottie) y efectos visuales para una mayor inmersión.
* **Audio Inmersivo:** Efectos de sonido y música ambiente que enriquecen la narrativa y el combate.
* **Interfaz Intuitiva:** Diseño limpio y accesible, optimizado para dispositivos Android.
* **Reinicio de Partida:** Opción para comenzar una nueva aventura en cualquier momento.

## 🛠️ Tecnologías Utilizadas

* **Lenguaje de Programación:** Kotlin
* **Framework UI:** Jetpack Compose
* **Arquitectura:** MVVM (Model-View-ViewModel)
* **Gestión de Estado:** Android Architecture Components (ViewModel)
* **Persistencia de Datos:** Room (SQLite)
* **Navegación:** Jetpack Navigation
* **Inyección de Dependencias:** Koin
* **Concurrencia:** Kotlin Coroutines
* **Animaciones:** Jetpack Compose animations, Lottie
* **Gestión de Audio:** Android MediaPlayer

## 📦 Instalación

Para ejecutar este proyecto localmente, sigue estos pasos:

1.  **Clona el repositorio:**
    ```bash
    git clone [https://github.com/Osore66/DestinoInteractivo.git](https://github.com/Osore66/DestinoInteractivo.git)
    cd DestinoInteractivo
    ```
2.  **Abre el proyecto en Android Studio:**
    Abre Android Studio y selecciona `Open an existing Android Studio project`. Navega hasta la carpeta `DestinoInteractivo` que acabas de clonar y ábrela.
3.  **Sincroniza el proyecto:**
    Android Studio debería sincronizar automáticamente el proyecto y descargar las dependencias necesarias. Si no lo hace, haz clic en `Sync Project with Gradle Files` (el icono del elefante).
4.  **Ejecuta la aplicación:**
    Conecta un dispositivo Android (API 24 o superior) o inicia un emulador. Selecciona tu dispositivo/emulador en la barra de herramientas de Android Studio y haz clic en el botón `Run` (el triángulo verde).

## 🎮 Uso de la Aplicación

1.  **Inicio:** Al abrir la aplicación, se te presentará la pantalla principal con opciones para "Nueva Partida" o "Continuar Partida" (si ya hay una guardada).
2.  **Navegación por la Historia:** Lee el texto de la narrativa y selecciona una de las opciones disponibles para avanzar en la historia. Tus decisiones afectarán el desarrollo y los posibles desenlaces.
3.  **Combate:** Cuando entres en combate, verás la vida de tu personaje y la del enemigo. Tendrás botones para "Atacar", "Defender" y "Usar Poción". El combate es por turnos, y la estrategia es clave.
4.  **Menú de Opciones:** Desde la pantalla principal o durante la partida (dependiendo de la implementación), puedes acceder al menú de opciones para ajustar el idioma, los volúmenes y la vibración.
5.  **Reinicio de Partida:** Si deseas empezar desde cero, puedes usar la opción de "Reiniciar Partida" disponible en el menú de opciones.

## 📂 Estructura del Proyecto

El proyecto sigue una arquitectura MVVM y está organizado de manera modular, facilitando el mantenimiento y la escalabilidad. Las carpetas principales incluyen:

* `data`: Repositorios y fuentes de datos (Room).
* `domain`: Lógica de negocio (modelos, casos de uso).
* `ui`: Componentes de interfaz de usuario (Compose UIs, ViewModels).
* `navigation`: Gestión de la navegación entre pantallas.
* `utils`: Clases de utilidad, como el reproductor de sonido.

## 💖 Créditos

* **Autor:** David de Andrés Fontcuberta
* **Tutor del Proyecto:** David Antolín Gutiérrez

## 📄 Licencia

Este proyecto ha sido desarrollado en el marco del Ciclo Formativo de Grado Superior en Desarrollo de Aplicaciones Multiplataforma (DAM) del IES El Grao, Valencia (2024-2025). La licencia específica no está definida en la memoria del proyecto. Se recomienda contactar al autor para más detalles sobre el uso y distribución.
## 📄 Licencia

Este proyecto ha sido desarrollado en el marco del Ciclo Formativo de Grado Superior en Desarrollo de Aplicaciones Multiplataforma (DAM) del IES El Grao, Valencia (2024-2025). La licencia específica no está definida en la memoria del proyecto. Se recomienda contactar al autor para más detalles sobre el uso y distribución.

# Aplicacion-Mis-Notas

# Manual de Uso: Mis Notas App

Bienvenido a **Mis Notas**, una aplicación sencilla y moderna para gestionar tus notas y tareas personales. Desarrollada con Jetpack Compose y siguiendo una arquitectura modular, esta app te permite organizar tus ideas de forma eficiente y local en tu dispositivo.

## 1. Requisitos del Sistema

* **Dispositivo:** Teléfono o tablet Android.
* **Versión de Android:** Android 5.0 (API 21) o superior.

## 2. Instalación de la Aplicación

Para instalar y probar la aplicación en tu dispositivo Android:

1.  **Habilita Opciones de Desarrollador:**
    * En tu dispositivo Android, ve a **Ajustes** (Settings).
    * Busca **"Acerca del teléfono"** (About phone) o "Información del tablet".
    * Toca repetidamente (7 veces) en **"Número de compilación"** (Build number) hasta que aparezca el mensaje "¡Ya eres desarrollador!".
2.  **Habilita Depuración USB e Instalación por USB:**
    * Vuelve a la pantalla principal de Ajustes.
    * Busca **"Opciones de desarrollador"** (Developer options) (puede estar bajo "Sistema" o "Más ajustes").
    * Dentro de "Opciones de desarrollador", activa **"Depuración USB"** (USB debugging) y **"Instalar aplicaciones por USB"** (Install via USB) si están disponibles. Confirma cualquier advertencia de seguridad.
3.  **Conecta tu Dispositivo:**
    * Conecta tu dispositivo Android a tu computadora mediante un cable USB.
    * Acepta la solicitud de "Permitir depuración USB" en tu dispositivo.
4.  **Ejecuta desde Android Studio:**
    * Abre el proyecto `Examen_DISPMoviles` en Android Studio.
    * Selecciona tu dispositivo en el menú desplegable de dispositivos en la barra superior.
    * Haz clic en el botón **"Run App"** (el triángulo verde `▶️`) en la barra de herramientas de Android Studio. La aplicación se instalará y ejecutará automáticamente en tu dispositivo.

## 3. Primeros Pasos con MyNotes App

### 3.1. Iniciar Sesión

Al abrir la aplicación por primera vez, serás recibido por la pantalla de inicio de sesión. Para acceder:

* **Usuario:** `user`
* **Contraseña:** `pass`
* Ingresa estas credenciales en los campos correspondientes y haz clic en el botón **"Entrar"**.

### 3.2. Pantalla Principal (Mis Notas)

Una vez iniciada la sesión, accederás a la pantalla principal. Esta muestra una lista de todas tus notas. Si es tu primera vez usando la app o si has realizado una limpieza de datos, la lista estará inicialmente vacía.

## 4. Funcionalidades de la Aplicación

### 4.1. Añadir una Nueva Nota

1.  En la pantalla "Mis Notas", haz clic en el **botón flotante (`+`)** ubicado en la esquina inferior derecha de la pantalla.
2.  Serás dirigido a la pantalla **"Nueva Nota"**.
3.  **Título:** Ingresa un título descriptivo para tu nota (este campo es obligatorio).
4.  **Contenido:** Escribe el contenido o los detalles de tu nota (debe tener al menos 5 caracteres).
5.  **Completada (Opcional):** Puedes activar el switch si esta nota o tarea ya está completada.
6.  Haz clic en el botón **"Guardar Nota"** en la parte inferior.
7.  La aplicación te regresará a la pantalla "Mis Notas", y tu nueva nota aparecerá en la lista.

### 4.2. Ver Detalles de una Nota

1.  En la pantalla "Mis Notas", simplemente haz clic sobre cualquier **nota existente** en la lista.
2.  Serás dirigido a la pantalla **"Detalle de Nota"**, donde podrás visualizar el título completo, contenido, la fecha y hora de creación, y su estado (Completada/Pendiente).

### 4.3. Editar una Nota Existente

1.  Desde la pantalla "Detalle de Nota", haz clic en el **botón flotante con el icono de lápiz (`✏️`)** ubicado en la esquina inferior derecha.
2.  Esto te llevará a la pantalla de edición, donde los campos estarán precargados con la información de la nota seleccionada.
3.  Realiza los cambios deseados en el título, contenido o el estado de "Completada".
4.  Haz clic en el botón **"Guardar Nota"** para aplicar los cambios. La aplicación regresará al detalle de la nota actualizada.

### 4.4. Eliminar una Nota

1.  En la pantalla "Mis Notas" (la lista principal), localiza la nota que deseas eliminar.
2.  Haz clic en el **icono de la papelera (`🗑️`)** que se encuentra a la derecha del título de la nota.
3.  La nota será eliminada instantáneamente de la lista y de la base de datos local.

### 4.5. Acceder a Configuración

1.  En la pantalla "Mis Notas", busca y haz clic en el **icono de engranaje (`⚙️`)** ubicado en la esquina superior derecha de la barra de título.
2.  Serás dirigido a la pantalla **"Configuración"**, donde podrás ver la versión de la aplicación y un switch de ejemplo para una opción de privacidad.

## 5. Consideraciones Importantes

* **Almacenamiento Local:** Todos tus datos (notas) se almacenan exclusivamente en tu dispositivo. No se realiza ninguna sincronización en la nube ni se envían datos a servidores externos.
* **Pérdida de Datos:** Si desinstalas la aplicación, todos los datos almacenados en la base de datos local se perderán de forma permanente.
* **Desarrollo:** Esta aplicación ha sido desarrollada como parte de un proyecto de examen, enfocándose en la implementación de una arquitectura limpia y las tecnologías modernas de Android.

---

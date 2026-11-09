 Descripción del Proyecto

Aplicación de escritorio desarrollada en **JavaFX** y **Maven** para la gestión de pacientes de la *Clínica Veterinaria PataVerde*. 

Migra el modelo de consola previo a una arquitectura **MVC** con interfaz gráfica interactiva y almacenamiento local en **JSON** (usando la librería **Gson**).

 Características principales:
- **Modelo y Reglas de Negocio:** Manejo de Perros y Gatos con cálculo dinámico de costos y validaciones de datos (Sección 4).
- **Interfaz Gráfica:** Formulario dinámico con deshabilitación de campos según el tipo de animal y tabla con las 5 columnas requeridas.
- **Persistencia en JSON:** Carga y guardado automático en `perros.json` y `gatos.json` al registrar o eliminar pacientes.
- **Arranque limpio:** Inicia con la tabla vacía si no existen los archivos JSON previamente.

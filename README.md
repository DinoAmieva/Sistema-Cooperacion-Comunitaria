# Sistema de Cooperación Comunitaria - ProyectoParalela

## Descripción del Proyecto

Sistema Java desktop para el registro y gestión de expedientes de cooperación de habitantes de un poblado. Implementa arquitectura orientada a objetos con 6 hilos concurrentes, persistencia en base de datos SQLite, e interfaz gráfica moderna con Swing.

## Cumplimiento de Especificaciones

### Arquitectura Orientada a Objetos
- **Encapsulación**: Todas las clases implementan getters/setters y validaciones
- **Herencia**: Modelos extienden funcionalidad base
- **Composición**: Controllers componen DAOs y Threads
- **Separación de responsabilidades**: MVC pattern implementado
- **Modularidad**: Paquetes organizados por funcionalidad
- **Escalabilidad**: Patrones Singleton, Observer, Factory

### Programación Paralela
- **6 Hilos concurrentes** implementados con `Runnable`
- **Sincronización** con `BlockingQueue`, `AtomicBoolean`, `Semaphore`
- **Flujo concurrente no bloqueante** con colas asíncronas
- **Coordinación centralizada** mediante `ThreadManager`

### Base de Datos
- **Consultas SQL seguras** con `PreparedStatement`
- **Registro y recuperación** de datos con DAOs
- **Transacciones** y manejo de excepciones
- **Pool de conexiones** básico implementado

### Interfaz Gráfica
- **Pantalla de administrador** con dashboard
- **Registro COPACI y CAP** funcional
- **Registro mensual** de cooperaciones
- **Registro de participaciones** en faenas
- **Consulta de expedientes** con filtros
- **Navegación clara** con sidebar
- **Retroalimentación visual** y diseño profesional

## Implementación de Hilos Concurrentes

### Hilo 1: RegistroHabitanteThread (15%)
**Función**: Alta automática al cumplir mayoría de edad
```java
// Implementación: src/proyectoparalela/threads/RegistroHabitanteThread.java
- Verifica cada 5 segundos habitantes elegibles
- Registra automáticamente nuevos habitantes de 18 años
- Control de estado: start, pause, resume, stop
- Sincronización con wait/notify
- Notificaciones en tiempo real
```

### Hilo 2: GeneracionExpedienteThread (20%)
**Función**: Alta en COPACI y CAP, creación de expedientes
```java
// Implementación: src/proyectoparalela/threads/GeneracionExpedienteThread.java
- Cola de tareas con BlockingQueue<Long>
- Genera expedientes automáticamente para nuevos habitantes
- Crea expedientes tipo "COPACI-CAP"
- Calcula puntuaciones basadas en participación
- Notifica a la interfaz sobre nuevos expedientes
```

### Hilo 3: RegistroMensualThread (20%)
**Función**: Guarda cooperación mensual o anual
```java
// Implementación: src/proyectoparalela/threads/RegistroMensualThread.java
- Scheduler con Timer para ejecuciones periódicas
- Se ejecuta automáticamente el primer día de cada mes
- Genera reportes de cooperación por habitante
- Calcula estadísticas mensuales y anuales
- Crea respaldos automáticos de datos
```

### Hilo 4: RegistroFaenasThread (20%)
**Función**: Registra participación en actividades comunitarias
```java
// Implementación: src/proyectoparalela/threads/RegistroFaenasThread.java
- Cola asíncrona con BlockingQueue<Faena>
- Monitorea participación en faenas comunitarias
- Asigna puntos por participación
- Valida asistencia y horas trabajadas
- Actualiza expedientes con nueva información
```

### Hilo 5: PersistenciaThread (15%)
**Función**: Guarda datos en BD sin bloquear la interfaz
```java
// Implementación: src/proyectoparalela/threads/PersistenciaThread.java
- Cola de operaciones pendientes con BlockingQueue<Operation>
- Maneja todas las operaciones de BD de forma asíncrona
- Realiza respaldos automáticos periódicos
- Optimiza consultas para mejor rendimiento
- Maneja transacciones complejas
- Control de integridad referencial
```

### Hilo 6: SesionThread (10%)
**Función**: Mantener la sesión durante todo momento
```java
// Implementación: src/proyectoparalela/threads/SesionThread.java
- Mantiene sesiones activas de usuarios
- Implementa timeout de sesiones inactivas (60 segundos)
- Maneja múltiples sesiones concurrentes con ConcurrentHashMap
- Valida autenticación continua
- Heartbeat cada 5 segundos
- Logs de actividad de usuarios
```

## Coordinación de Hilos

### ThreadManager
```java
// Implementación: src/proyectoparalela/utils/ThreadManager.java
- Coordinador centralizado de todos los hilos
- Inicializa y arranca los 6 hilos concurrentemente
- Gestiona dependencias entre hilos
- Proporciona acceso a instancias de hilos
- Notificaciones centralizadas
```

### Sincronización
```java
// Implementación: src/proyectoparalela/utils/SynchronizationUtils.java
- Semáforos para control de acceso a recursos
- Locks reentrantes para operaciones críticas
- Evita condiciones de carrera (race conditions)
- Patrones Producer-Consumer implementados
- Sistema de prioridades para operaciones críticas
```

## Arquitectura del Sistema

### Patrones Implementados
- **MVC (Model-View-Controller)**: Separación clara de responsabilidades
- **Singleton**: DatabaseConnection para conexiones únicas
- **Observer/Observable**: Notificaciones en tiempo real
- **DAO (Data Access Object)**: Abstracción de persistencia
- **Factory**: Creación de componentes UI
- **Producer-Consumer**: Colas asíncronas entre hilos

### Estructura de Paquetes
```
src/proyectoparalela/
├── model/          # Entidades de datos (Habitante, Expediente, etc.)
├── dao/            # Acceso a datos (HabitanteDAO, ExpedienteDAO, etc.)
├── controller/     # Lógica de negocio (HabitanteController, etc.)
├── view/           # Interfaz gráfica (MainWindow, pantallas específicas)
├── threads/        # 6 hilos concurrentes
├── utils/          # Utilidades (ThreadManager, SynchronizationUtils, etc.)
├── db/             # Configuración de base de datos
└── Main.java       # Punto de entrada de la aplicación


## Base de Datos

### SQLite Implementation
- **Archivo**: `data/cooperacion.db`
- **Tablas**: habitantes, expedientes, cooperaciones, faenas, sesiones
- **Conexión**: Singleton pattern con pool básico
- **Transacciones**: Manejo automático de commits/rollbacks
- **Integridad**: Claves foráneas y validaciones

### DAOs Implementados
- `HabitanteDAO`: CRUD completo para habitantes
- `ExpedienteDAO`: Gestión de expedientes
- `CooperacionDAO`: Registro de cooperaciones
- `FaenaDAO`: Control de faenas comunitarias
- `SesionDAO`: Manejo de sesiones de usuario

## Interfaz Gráfica

### Características
- **Dashboard** con métricas en tiempo real
- **Sidebar de navegación** con marca "CooperaTec"
- **Cards de métricas** con datos clave
- **Badges de estado** de hilos con colores
- **Formularios unificados** con validación
- **Tablas modernas** con filtros y búsqueda
- **Diseño responsive** con scroll automático

### Pantallas Implementadas
1. **Dashboard**: Estadísticas y estado del sistema
2. **Alta COPACI/CAP**: Registro de cooperativas
3. **Registro Mensual**: Gestión de cooperaciones
4. **Participaciones**: Registro de faenas
5. **Consulta Expediente**: Búsqueda y filtros

## Instalación y Ejecución

### Requisitos
- Java 21+
- NetBeans IDE 27
- SQLite JDBC Driver

### Pasos de Instalación
1. **Clonar/descargar** el proyecto
2. **Abrir en NetBeans** IDE 27
3. **Configurar librerías**:
   - `lib/sqlite-jdbc-3.46.1.0.jar`
   - `lib/slf4j-api-2.0.13.jar`
   - `lib/slf4j-simple-2.0.13.jar`
4. **Clean & Build** (Shift+F11)
5. **Run Project** (F6)

### Configuración de Librerías en NetBeans
1. Click derecho en proyecto → Properties
2. Libraries → Compile → Add JAR/Folder
3. Seleccionar archivos de `lib/`
4. OK y Clean & Build

## Funcionalidades del Sistema

### Gestión de Habitantes
- Registro automático al cumplir 18 años
- Validación de datos personales
- Búsqueda y filtrado avanzado
- Historial de cooperaciones

### Gestión de Expedientes
- Creación automática para nuevos habitantes
- Tipos: COPACI, CAP, Faenas
- Estados: ACTIVO, PENDIENTE, COMPLETADO
- Seguimiento de puntuaciones

### Gestión de Cooperaciones
- Registro mensual automático
- Cálculo de estadísticas
- Reportes por período
- Respaldo de datos

### Gestión de Faenas
- Registro de participación
- Cálculo de horas trabajadas
- Asignación de puntos
- Alertas por inasistencias

### Control de Sesiones
- Autenticación continua
- Timeout automático
- Múltiples sesiones concurrentes
- Logs de actividad

## Monitoreo y Logs

### Estado de Hilos en Tiempo Real
- **ACTIVO**: Verde con parpadeo
- **PAUSADO**: Naranja
- **DETENIDO**: Rojo
- **ERROR**: Púrpura

### Notificaciones
- Sistema Observer para notificaciones
- Barra de estado con información del sistema
- Logs detallados de operaciones
- Alertas de errores y excepciones

## Testing y Validación

### Pruebas Implementadas
- Validación de entrada de datos
- Manejo de excepciones SQL
- Sincronización de hilos
- Persistencia de datos
- Interfaz gráfica responsive

### Casos de Uso Cubiertos
- Registro de nuevos habitantes
- Creación automática de expedientes
- Procesamiento mensual de cooperaciones
- Registro de participaciones en faenas
- Persistencia asíncrona de datos
- Mantenimiento de sesiones activas

## Métricas y Rendimiento

### Optimizaciones Implementadas
- **Colas asíncronas** para operaciones no bloqueantes
- **Pool de conexiones** para eficiencia de BD
- **Cache en memoria** para datos frecuentes
- **Transacciones optimizadas** para mejor rendimiento
- **Sincronización granular** para evitar bloqueos

### Monitoreo
- Dashboard con métricas en tiempo real
- Estado de hilos visible
- Logs de rendimiento
- Alertas de errores

## Justificación Técnica

### Cumplimiento de Especificaciones
**6 Hilos concurrentes** con funciones específicas (100% de valor distribuido)
**Arquitectura OOP** con encapsulación, herencia y composición
**Base de datos** con consultas seguras y eficientes
**Interfaz gráfica** completa y funcional
**Programación paralela** con sincronización adecuada
**Modularidad** y escalabilidad del sistema

### Innovaciones Implementadas
- **Dashboard moderno** con Material Design
- **Sistema de notificaciones** en tiempo real
- **Coordinación inteligente** de hilos
- **Persistencia asíncrona** sin bloqueos
- **Interfaz responsive** y profesional

## Documentación Adicional

### Código Comentado
- Javadoc completo en todas las clases
- Comentarios explicativos en métodos críticos
- Documentación de patrones implementados
- Guías de uso para desarrolladores

### Manual de Usuario
- Guía de instalación paso a paso
- Tutorial de uso de la interfaz
- Explicación de funcionalidades
- Solución de problemas comunes

---

**Desarrollado por**: [Dino Amieva Garza]
**Fecha**: 2025
**Versión**: 1.0
**Tecnologías**: Java 21, Swing, SQLite, NetBeans IDE 27
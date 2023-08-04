# Dishcovery - Technical Documentation

## Project Description

Dishcovery is the name I've given to the app. It is developed in Kotlin using Android Studio. The software architecture adopted for this project is the Model-View-Intent (MVI), implemented along with the principles of Clean Architecture.

## Clean Architecture and Project Structure

The implementation of Clean Architecture was carried out following the guidelines provided in the official Android documentation in its software architecture guide. The basic Clean Architecture approach was extended to specifically adapt to the knowledge being required in the project based on my experience gained from previous projects. The application is an example of implementing Clean Architecture and the MVI pattern in Android application development.

The layers used in the project are as follows:

1. **Domain**: This layer represents the entity layer and contains the data models that define business rules. Here, following the Repository pattern, interfaces for repositories that manage the retrieval of this data are defined.

2. **Data**: The Data layer depends on the Domain and contains concrete implementations of the repositories defined in the domain layer, as well as the definition of the corresponding DataSources. This layer is responsible for accessing and managing the data without interacting with external libraries.

3. **Framework**: The framework layer depends on the data layer and provides the implementation of the DataSources. Additionally, this layer includes dependency injection and integration of external libraries (Retrofit and Room) that facilitate obtaining information from external sources and storing this information.

4. **Interactors**: Interactors (or use cases) form the layer of use cases and represent the business logic. These interactors depend on the domain layer and orchestrate the interaction between the UI layer and the data, with the ViewModels acting as intermediaries.

5. **UI**: The User Interface (UI) layer is the presentation layer of the application. Here you will find the definitions of the views (all developed in Jetpack Compose), their respective ViewModels, Intents, and States, following common practices of the MVI architecture.

## Notes on Repository and DataSource Patterns

The Repository pattern is common in this type of layered implementation and is used with the aim of separating data access logic from business logic. This separation allows a high degree of flexibility and maintainability in the project, as the upper layers do not directly depend on the implementation details of the data sources.

DataSources were used to encapsulate the interaction logic with external frameworks. This ensures that the lower layers remain independent of the specific details of the framework used to obtain external information. It makes the project 'agnostic', meaning it is not dependent on frameworks.

## Important Libraries to Highlight

### In the Project

- **Android Architecture Components**: ViewModel, Lifecycle, and more...

- **Jetpack Compose**: A suite of declarative UI libraries that replaces the definition of UI through XML.

- **Navigation**: Navigation between fragments.

- **Kotlin Coroutines**: Used to handle all background operations.

- **Kotlin Flows**: Actually part of the Kotlin-stdlib, extensively used in the project.

- **Google Maps and Maps Compose**: Maps and Geolocation according to the requirements of the app (This app was developed for a technical test to apply to a role).

- **Hilt**: Dependency Injection.

- **Retrofit**: Communication with the REST API.

- **Room**: Data Storage.

### Unit Testing

- **JUnit 4**: Unit testing framework for Java and Kotlin.

- **Mockito**: Creates mock dependencies.

- **Turbine**: Simplifies testing of Kotlin Flows.

### Instrumented Testing (UI Testing)

- **Compose UI Test**: Tools for testing user interfaces developed with Jetpack Compose.

- **Espresso**: General UI testing.

## User Interface (UI) Module Structure

The "Dishcovery" application focuses on displaying food recipes to users. Jetpack Compose is used entirely for building the interface in each fragment. Each Fragment is located in a package that also contains the ViewModel, Intents, and States, following the implementation of MVI or what I have managed to understand from it in my recent projects.

### Screens

- **SplashFragment**: It's a splash screen, simply an initial screen. It includes a button that allows users to navigate to the next fragment.

- **MainFragment**: This fragment is the central piece. It contains a `VerticalPager` that allows users to navigate between three distinct screens:

  1. **Home**: This screen presents a list of featured or categorized recipes.

  2. **Search**: Here, users can search for recipes.

  3. **Favorites**: Here, users can view and manage their saved favorite recipes (yes, recipes can be added to favorites, although it wasn't requested, I decided to add it).

- **DetailFragment**: Displays a detailed view of a specific recipe.

- **MapFragment**: On this screen, users can view a randomly generated city location on a map. The location comes from the recipe model and is generated randomly by the backend.

IMPORTANT NOTE:
Unit Testing: As I applied TDD to the modules that weren't UI-related, I mostly completed them. However, I couldn't complete the UI testing due to lack of time. However I will.

It's not crucial, but the backend is developed in Node.js and is hosted on AWS.

# Dishcovery (Spanish description)

## Descripción del Proyecto

Dishcovery es el nombre que le he asignado a la aplicación. Está desarrollada en Kotlin utilizando Android Studio. La arquitectura de software adoptada para este proyecto es el Modelo-View-Intent (MVI), implementado junto con los principios de Clean Architecture.

## Clean Architecture y Estructura del Proyecto

La implementación de Clean Architecture se realizó siguiendo las directrices proporcionadas en la documentación oficial de Android en su guía de arquitectura de software. Se extendió el enfoque básico de Clean Architecture para adaptarse específicamente a requerimientos del proyecto basándome en mi experiencia adquirida en proyectos previos. La aplicación es un ejemplo de implementación de Clean Architecture y el patrón MVI en el desarrollo de aplicaciones Android.

Las capas utilizadas en el proyecto son las siguientes:

1. **Domain**: Esta capa representa la capa de entidad y contiene los modelos de datos que definen las reglas de negocio. Aquí, siguiendo el patrón Repository, se definen las interfaces de los repositorios que gestionan la obtención de estos datos.

2. **Data**: La capa de Data depende de Domain y contiene las implementaciones concretas de los repositorios definidos en la capa de dominio, así como la definición de las fuentes de datos o DataSources correspondientes. Esta capa se encarga de acceder y gestionar los datos sin interactuar con librerías externas.

3. **Framework**: La capa de framework depende de la capa de datos y proporciona la implementación de las fuentes de datos (DataSources). Además, esta capa incluye la inyección de dependencias e integración de las bibliotecas externas (Retrofit y Room) que facilitan la obtención de información desde fuentes externas y el almacenamiento de esta información.

4. **Interactors**: Los interactors (o use cases) forman la capa de casos de uso y representan la lógica de negocio. Estos interactors dependen de la capa de dominio y orquestan la interacción entre la capa de UI y los datos, siendo los ViewModels los intermediarios.

5. **UI**: La capa de interfaz de usuario (UI) es la capa de presentación de la aplicación. Aquí se encuentran las definiciones de las vistas (todas desarrolladas en Jetpack Compose), sus respectivos viewmodels, Intents y States, siguiendo las prácticas comunes de la arquitectura MVI.

## Notas respecto a los Patrones Repository y DataSource

El patrón Repository es común en este tipo de implementaciones por capas y se emplea con el objetivo de separar la lógica de acceso a datos de la lógica de negocio. Esta separación permite un alto grado de flexibilidad y mantenibilidad en el proyecto, ya que las capas superiores no dependen directamente de los detalles de implementación de las fuentes de datos.

Las fuentes de datos (datasources) se utilizaron para encapsular la lógica de interacción con frameworks externos. Esto garantiza que las capas inferiores permanezcan independientes de los detalles específicos del framework utilizado para obtener información externa. Convierte al proyecto en 'agnóstico', es decir, no depende de los frameworks.

## Librerías importantes a destacar

### En el proyecto

- **Componentes de Arquitectura de Android**: ViewModel, Lifecycle y demás...

- **Jetpack Compose**: Suite de librerías de UI declarativa que reemplaza la definición de UI mediante XML.

- **Navigation**: Navegación entre fragments.

- **Kotlin Coroutines**: Empleadas para manejar todas las operaciones en segundo plano.

- **Kotlin Flows**: En realidad, son parte de la biblioteca estándar de Kotlin-stdlib, se usaron extensivamente en el proyecto.

- **Google Maps y Maps Compose**: Mapas y Geolocalización de acuerdo con los requerimientos de la prueba técnica.

- **Hilt**: Inyección de Dependencias.

- **Retrofit**: Comunicación con la API REST.

- **Room**: Almacenamiento de Datos.

### Unit Testing

- **JUnit 4**: Framework de pruebas unitarias para Java y Kotlin.

- **Mockito**: Crea mocks de dependencias.

- **Turbine**: Simplifica las pruebas de Kotlin Flows.

### Instrumented Testing (UI Testing)

- **Compose UI Test**: Herramientas para el testing de interfaces de usuario desarrolladas con Jetpack Compose.

- **Espresso**: UI testing en general.

## Estructura del Módulo de Interfaz de Usuario (UI)

La aplicación "Dishcovery" se centra en mostrar recetas de comida a los usuarios. En cada fragment se utiliza en su totalidad Jetpack Compose para construir la interfaz. Cada Fragment se encuentra en un paquete que también contiene el ViewModel, los Intents y los States, de acuerdo con la implementación de MVI, o lo que he logrado entender de ella en mis proyectos recientes.

### Pantallas

- **SplashFragment**: Es un splash, simplemente una pantalla de inicio. Incluye un botón que permite a los usuarios avanzar hacia el siguiente fragmento.

- **MainFragment**: Este fragmento es la pieza central. Contiene un `VerticalPager` que permite a los usuarios navegar entre tres pantallas distintas:

  1. **Home**: Esta pantalla presenta una lista de recetas destacadas o según categoría.

  2. **Search**: Aquí los usuarios pueden buscar recetas.

  3. **Favorites**: Aquí, los usuarios pueden ver y gestionar sus recetas favoritas guardadas 

- **DetailFragment**: Muestra una vista de detalle de una receta específica.

- **MapFragment**: En esta pantalla, los usuarios pueden ver una ubicación de una ciudad aleatoria en un mapa. La ubicación viene en el modelo de la receta y es generada aleatoriamente por el backend.

No es importante, pero el backend está desarrollado en Node.js y se encuentra alojado en AWS.

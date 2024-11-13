# ScreenMatch Application

## Description
The **ScreenMatch** project is a Java-based application designed to manage multimedia content, focusing primarily on series and episodes. It includes functionalities for querying, filtering, and organizing series and episodes using external APIs and Java Streams, enabling efficient data handling.

## Overview
The **ScreenMatch** application offers features for organizing and querying multimedia content such as series and episodes. By leveraging Java Streams and integrating with external APIs, the application is optimized for efficient data processing.

## Key Features

### 1. Stream Example
- **com.aluracursos.screenmatch.menu.EjemploStreams**: Demonstrates the use of Java Streams to manipulate lists, including filtering, sorting, and transforming data within name lists.

### 2. Main Menu
- **com.aluracursos.screenmatch.menu.MenuPrincipal**: The primary user interface class, containing the main menu for interacting with the application. It enables users to search for series, query episodes, and apply various content filters by season and rating.

### 3. Category Modeling
- **com.aluracursos.screenmatch.model.Categoria**: Defines the categories or genres of series, helping classify multimedia content into different genres.

### 4. Episode Data
- **com.aluracursos.screenmatch.model.DatosEpisodio**: Class that stores data for each episode, providing methods and attributes to manage specific details of the episodes in a series.

### 5. Series Data
- **com.aluracursos.screenmatch.model.DatosSerie**: Contains basic information about series, such as title, rating, and other key details.

### 6. Season Data
- **com.aluracursos.screenmatch.model.DatosTemporadas**: Structures season data, allowing episodes to be organized by season number.

### 7. Episode
- **com.aluracursos.screenmatch.model.Episodio**: Represents an episode in the application’s data structure, providing details such as episode number, title, and rating.

### 8. Series
- **com.aluracursos.screenmatch.model.Serie**: Defines a series as a whole, linking episodes and seasons and enabling access to specific series data.

### 9. Series Repository
- **com.aluracursos.screenmatch.repository.SerieRepository**: A data access interface enabling CRUD operations on stored series and offering methods to query series by title and rating.

### 10. Main Application
- **com.aluracursos.screenmatch.ScreenmatchApplication**: The Spring Boot application entry point. This class initializes the environment and runs the main menu for users to interact with the series and episode management features.

### 11. DeepL Translation Query
- **com.aluracursos.screenmatch.service.ConsultaDeepL**: Integrates the DeepL API for text translation to Spanish. Sends HTTP requests to DeepL to obtain translations, facilitating content internationalization.

### 12. External API Consumption
- **com.aluracursos.screenmatch.service.ConsumoApi**: Class for making HTTP requests to fetch JSON data from external APIs, enabling the application to integrate content from third-party services.

### 13. Data Conversion
- **com.aluracursos.screenmatch.service.ConvierteDatos**: Provides methods for converting JSON data into Java objects, facilitating the processing and integration of external data within the application’s structure.

### 14. Data Conversion Interface
- **com.aluracursos.screenmatch.service.IConvierteDatos**: Interface defining methods for data conversion, ensuring flexibility and consistency across different conversion classes.

### 15. Logger Base
- **com.aluracursos.logger.loggerbase.LoggerBase**: Base class for handling logging throughout the application. Includes methods to log and display error and warning messages.

### 16. Logger Implementation
- **com.aluracursos.logger.loggerbase.LoggerBaseImpl**: Specific implementation of `LoggerBase` that manages the details of event logging, ensuring errors and events are consistently documented.

### 17. Anime Application (Exercise 1)
- **com.aluracursos.ejercicio1.main_1.Main_1**: Entry point for the anime content management application, launching the specific main menu for this functionality.

### 18. Anime Main Menu
- **com.aluracursos.ejercicio1.menu.MenuPrincipal**: Main menu for managing anime-related content, offering options to explore and organize specific anime series.

### 19. Anime Model
- **com.aluracursos.ejercicio1.model.Anime**: Class defining anime properties and characteristics, storing and accessing specific information about anime, such as title and category.

### 20. Anime Data
- **com.aluracursos.ejercicio1.model.DatosAnime**: Stores specific details about an anime series, facilitating the organization and retrieval of relevant metadata for each anime.

### 21. Anime Episode Data
- **com.aluracursos.ejercicio1.model.DatosEpisodio**: Class to store data on anime episodes, such as titles, episode numbers, and ratings, with support for JSON deserialization.

### 22. Anime Title Data
- **com.aluracursos.ejercicio1.model.DatosTitulo**: Stores the title of an anime, adapting to JSON received using the `@JsonAlias` annotation.

### 23. Anime Episode
- **com.aluracursos.ejercicio1.model.Episodio**: Represents an episode in the specific anime application, including episode title and number.

### 24. Anime API Consumption
- **com.aluracursos.ejercicio1.service.ConsumoApi**: Class for making HTTP requests to fetch JSON data for the anime application.

### 25. Anime Data Conversion
- **com.aluracursos.ejercicio1.service.ConvierteDatos**: Provides methods to convert JSON data into Java objects, applicable to the anime application structure.

### 26. Anime Data Conversion Interface
- **com.aluracursos.ejercicio1.service.IConvierteDatos**: Interface defining data conversion methods within the anime application context.

### 27. Application Exercise 2
- **com.aluracursos.ejercicio2.main_2.Main_2**: Entry point for the second application, designed to manage and organize additional content.

### 28. Main Menu for Exercise 2
- **com.aluracursos.ejercicio2.menu.MenuPrincipal**: Main menu class in the second application, offering interaction and content management options.

### 29. Area
- **com.aluracursos.ejercicio2.model.Area**: Represents a specific thematic area or category within the application, facilitating content organization.

### 30. Artist
- **com.aluracursos.ejercicio2.model.Artista**: Models an artist, allowing the management of specific data related to them within the application.

### 31. BeginArea
- **com.aluracursos.ejercicio2.model.BeginArea**: Represents the origin location of an artist or song. Includes a protected constructor and methods to access the city of origin.

### 32. Song
- **com.aluracursos.ejercicio2.model.Cancion**: Model of a song, with properties such as name, description, duration, rating, and a link to the corresponding artist. Includes data validation and methods to associate an artist.

### 33. Artist Data
- **com.aluracursos.ejercicio2.model.DatosArtista**: Contains relevant artist data, facilitating the storage and access to detailed artist information within the application context.

### 34. Song Data
- **com.aluracursos.ejercicio2.model.DatosCancion**: Stores detailed information about a song, used to map data to the `Cancion` model.

### 35. Musical Genre
- **com.aluracursos.ejercicio2.model.GeneroMusical**: Defines the musical genre of a song or artist, structuring musical preferences into easily accessible categories.

### 36. Life Span
- **com.aluracursos.ejercicio2.model.LifeSpan**: Represents the active period of a musical artist or group, providing information on the beginning and end of a career.

### 37. Song Repository
- **com.aluracursos.ejercicio2.repository.CancionRepository**: Provides access to stored song data, enabling CRUD operations in the database.

### 38. Music Repository
- **com.aluracursos.ejercicio2.repository.MusicRepository**: Extends the functionality of `CancionRepository` to include advanced searches and domain-specific queries for music.

### 39. Music API Consumption
- **com.aluracursos.ejercicio2.service.ConsumoApi**: Class for making HTTP requests to fetch data from external APIs specific to the music application.

### 40. Music Data Conversion
- **com.aluracursos.ejercicio2.service.ConvierteDatos**: Provides methods to transform JSON data into Java objects, ensuring compatibility between external data and the application’s internal models.

### 41. Data Conversion Interface
- **com.aluracursos.ejercicio2.service.IConvierteDatos**: Interface defining the contract for converting JSON data to instances of a specific class. Uses a generic `<T>` type to allow flexible data conversion.

## System Requirements
To run this application, ensure your system meets the following requirements:
- **Java SDK 8 or higher**: The application is developed in Java.
- **Spring Boot**: Used to facilitate application construction and execution.
- **Internet connection**: Required to make API calls for external data.

## How to Run
1. **Clone the Repository**: Use the following command to clone the project:
   ```bash
   git clone https://github.com/DiiegoA/persistencia-de-datos-y-consultas-con-Spring-Data-JPA.git

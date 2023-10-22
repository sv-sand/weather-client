# Weather client

Here is the library for loading weather data from API of http://openweathermap.org

## Build

You can build this sources as artifact. As a result, a file `weather-client.jar` will be created.
## How to use

### Manually include in to the project

Open project structure and add new java library file `weather-client-1.0.2.jar`

### Copy library to m2 local repository

Run bottom command in bash or command line:

```bash
mvn install:install-file \
  -Dfile=weather-client-1.0.2.jar \
  -DgroupId=ru.sanddev \
  -DartifactId=weather-client \
  -Dversion=1.0.2 \
  -Dpackaging=jar
```

Now you can use library in your any maven project:

```xml
<dependency>
    <groupId>ru.sanddev</groupId>
    <artifactId>weather-client</artifactId>
    <version>1.0.1</version>
</dependency>
```


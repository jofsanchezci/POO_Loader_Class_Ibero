# Carga Dinámica de Clases en Java usando Class Loaders

## Descripción
Este proyecto demuestra cómo cargar clases dinámicamente en Java utilizando **Class Loaders**. La carga dinámica de clases permite que una aplicación cargue y ejecute clases sin necesidad de recompilar el código principal, lo que es útil para sistemas de plugins, frameworks modulares y optimización de memoria.

---

## **Estructura del Proyecto**
```
├── Plugin.java              # Interfaz que define el contrato de los plugins
├── HolaMundoPlugin.java     # Implementación de la interfaz Plugin
├── DynamicClassLoader.java  # ClassLoader personalizado para carga dinámica
├── Main.java                # Programa principal que carga la clase en tiempo de ejecución
```

---

## **Paso 1: Definir una Interfaz Común** (`Plugin.java`)
La interfaz define el contrato para las clases que serán cargadas dinámicamente.

```java
public interface Plugin {
    void ejecutar();
}
```

---

## **Paso 2: Crear la Clase a Cargar Dinámicamente** (`HolaMundoPlugin.java`)

```java
public class HolaMundoPlugin implements Plugin {
    @Override
    public void ejecutar() {
        System.out.println("¡Hola desde la clase cargada dinámicamente!");
    }
}
```

Compilar antes de ejecutar `Main.java`:
```sh
javac HolaMundoPlugin.java
```

---

## **Paso 3: Implementar un `ClassLoader` Personalizado** (`DynamicClassLoader.java`)

```java
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class DynamicClassLoader extends ClassLoader {
    private final String directorioClases;
    
    public DynamicClassLoader(String directorioClases) {
        this.directorioClases = directorioClases;
    }

    @Override
    public Class<?> findClass(String nombreClase) throws ClassNotFoundException {
        try {
            File archivoClase = new File(directorioClases + nombreClase + ".class");
            byte[] bytesClase = Files.readAllBytes(archivoClase.toPath());
            return defineClass(null, bytesClase, 0, bytesClase.length);
        } catch (IOException e) {
            throw new ClassNotFoundException("No se pudo cargar la clase: " + nombreClase, e);
        }
    }
}
```

---

## **Paso 4: Cargar y Ejecutar la Clase Dinámicamente** (`Main.java`)

```java
import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) {
        try {
            // Especifica el directorio donde está el archivo .class
            String directorio = "./"; // Asegúrate de que contiene HolaMundoPlugin.class
            
            // Crear una instancia del ClassLoader personalizado
            DynamicClassLoader classLoader = new DynamicClassLoader(directorio);
            
            // Cargar la clase dinámicamente
            Class<?> claseDinamica = classLoader.findClass("HolaMundoPlugin");
            
            // Crear una instancia de la clase cargada dinámicamente
            Object instancia = claseDinamica.getDeclaredConstructor().newInstance();
            
            // Llamar al método 'ejecutar' de la clase (usando reflexión)
            Method metodo = claseDinamica.getMethod("ejecutar");
            metodo.invoke(instancia);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

---

## **Paso 5: Compilar y Ejecutar**

### **1. Compilar los archivos**
```sh
javac Plugin.java HolaMundoPlugin.java DynamicClassLoader.java Main.java
```

### **2. Ejecutar el programa**
```sh
java Main
```

### **Salida esperada en consola:**
```
¡Hola desde la clase cargada dinámicamente!
```

---

## **Explicación del Código**

1. **DynamicClassLoader** extiende `ClassLoader` y carga archivos `.class` desde un directorio.
2. **Main.java** usa `DynamicClassLoader` para cargar `HolaMundoPlugin.class` en tiempo de ejecución.
3. Usa **Reflection API** para llamar al método `ejecutar()` de la clase cargada dinámicamente.

---

## **Aplicaciones de la Carga Dinámica**

- **Sistemas de Plugins/Modulares**: Extensiones para IDEs, navegadores y aplicaciones empresariales.
- **Optimización de Recursos**: Carga diferida (lazy loading) para mejorar la eficiencia.
- **Ejecución de Código Externo**: Frameworks que permiten añadir funcionalidad sin recompilar.

---

## **Conclusión**
- La carga dinámica de clases es una técnica poderosa en **Java** para construir sistemas flexibles y modulares.
- Se usa en **frameworks como Spring, sistemas de plugins y en desarrollo de aplicaciones escalables**.
- **Requiere estrategias adecuadas** para evitar problemas de seguridad y rendimiento.

🚀 ¡Ahora puedes experimentar con la carga dinámica en tus propios proyectos! 🚀

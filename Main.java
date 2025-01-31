import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) {
        try {
            // Especifica el directorio donde está el archivo .class
            String directorio = "./";  // Asegúrate de que contiene HolaMundoPlugin.class

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

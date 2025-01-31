import java.io.File;
import java.io.FileInputStream;
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

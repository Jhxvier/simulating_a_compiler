
//AUTHOR: HENRRY JAVIER LEIBA CALERO

/* 
 Code for parsing Python code, identifying tokens and lexical components to verify their correct usage and syntactic structures, implementing syntax-oriented translation, as well as understanding the use of intermediate code generation.
*/


package proyecto2;

import java.io.BufferedWriter;   // CONTIENE CLASE PARA ESCRIBIR EN UN ARCHIVO CON BUFFER
import java.io.FileWriter;       // CONTIENE CLASE PARA ESCRIBIR EN UN ARCHIVO
import java.io.IOException;      // CONTIENE CLASE PARA MANEJAR EXCEPCIONES DE ENTRADA/SALIDA
import java.io.BufferedReader;   // CONTIENE CLASE PARA LEER UN ARCHIVO CON BUFFER
import java.io.FileReader;       // CONTIENE CLASE PARA LEER UN ARCHIVO

public class Archivos {
    
    // MÉTODO QUE CREA UN ARCHIVO Y ESCRIBE EL RESULTADO DE LAS VALIDACIONES
    public  boolean crearArchivoErrores(String nombreArchivoErrores, String resultadoValidaciones) {
        
        //ABRE UN "BufferedWriter" PARA ESCRIBIR EN EL ARCHIVO QUE SE PASÓ COMO PARÁMETRO, METODO TRY-WITH-RESOURCES
        try (BufferedWriter escribidor = new BufferedWriter(new FileWriter(nombreArchivoErrores))) {
            escribidor.write(resultadoValidaciones);// ESCRIBE EL CONTENIDO DEL CODIGO DE PYTHON EN EL ARCHIVO
            return true;//DEVUELVE BOOLEANO EXITOSO
            
        } catch (IOException e) { // CAPTURA EXCEPCIONES DE ENTRADA/SALIDA SI HAY UN ERROR AL ABRIR O ESCRIBIR EN EL ARCHIVO
            e.printStackTrace();  // MUESTRA INFORMACIÓN DEL ERROR OCURRIDO EN EL PROGRAMA EN CONSOLA
            return false;//DEVUELVE BOOLEANO FALLIDO
        }
    }

    // MÉTODO QUE LEE Y ESCRIBE EL CONTENIDO DE UN ARCHIVO Y LO DEVUELVE COMO UNA CADENA DE TEXTO
    public  String leerArchivo(String archivoPython) {
        // SE UTILIZA UN STRINGBUILDER PARA ALMACENAR TODO EL CONTENIDO DEL ARCHIVO LÍNEA POR LÍNEA
        StringBuilder contenido = new StringBuilder();
        
        //ABRE UN "BufferedReader" PARA LEER EL ARCHIVO QUE SE PASÓ COMO PARÁMETRO, METODO TRY-WITH-RESOURCES
        try (BufferedReader leedorArchivo = new BufferedReader(new FileReader(archivoPython))) {
            
            String lineas; // VARIABLE QUE ALMACENA CADA LÍNEA DEL ARCHIVO 
            
            // MIENTRAS HAYA LÍNEAS EN EL ARCHIVO, SE VAN LEYENDO UNA POR UNA
            while ((lineas = leedorArchivo.readLine()) != null) {
                // SE AGREGA CADA LÍNEA LEÍDA AL STRINGBUILDER SEPARADOS POR UN SALTO DE LÍNEA
                contenido.append(lineas).append("\n");
            }
            // UNA VEZ LEÍDO TODO EL ARCHIVO, DEVUELVE EL CONTENIDO COMO UNA CADENA DE TEXTO
            return contenido.toString();
        } catch (IOException e) { // CAPTURA EXCEPCIONES DE ENTRADA/SALIDA SI HAY UN ERROR AL ABRIR O ESCRIBIR EN EL ARCHIVO
            e.printStackTrace();  // MUESTRA INFORMACIÓN DEL ERROR OCURRIDO EN EL PROGRAMA EN CONSOLA
            return null;//RETORNA VALOR NULL
        }
    }

}

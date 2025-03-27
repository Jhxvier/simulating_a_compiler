
//AUTHOR: HENRRY JAVIER LEIBA CALERO

/* 
 Code for parsing Python code, identifying tokens and lexical components to verify their correct usage and syntactic structures, implementing syntax-oriented translation, as well as understanding the use of intermediate code generation.
*/
package proyecto2;

public class Proyecto2 {

    // MÉTODO PRINCIPAL DEL PROGRAMA QUE RECIBE UN ARGUMENTO COMO PARÁMETRO
    public static void main(String[] args) {

        // VERIFICA SI EL USUARIO HA PASADO ARGUMENTOS AL EJECUTAR EL PROGRAMA
        if (args.length > 0) {

            //INSTANCIA DE LA CLASE ARCHIVOS QUE MANEJAR LA LECTURA/ESCRITURA DEL ARCHIVO
            Archivos archivos = new Archivos();
            String nombreArchivo = args[0]; // ALMACENA LA RUTA DEL ARCHIVO QUE SE PASÓ COMO ARGUMENTO

            // EXTRAE LA EXTENSIÓN DEL ARCHIVO, ALMACENA EL TEXTO ENCONTRADO DESPUÉS DEL ÚLTIMO PUNTO "(.)"
            String extension = nombreArchivo.substring(nombreArchivo.lastIndexOf(".") + 1);

            //VALIDA QUE LA EXTENSIÓN DEL ARCHIVO SEA "PY"
            if (!extension.equals("py")) {

                System.out.println("El archivo debe tener la extensión .py");

            } else {
                // SI LA EXTENSIÓN ES CORRECTA, PASA EL ARCHIVO PARA SER LEIDO Y ESCRITO, LO OBTENIDO LO ALMACENA
                String contenidoArchivo = archivos.leerArchivo(nombreArchivo);

                // SI EL ARCHIVO EXISTE Y CONTIENE CONTENIDO, LLAMA AL MÉTODO PARA COMPILAR Y ANALIZAR EL CÓDIGO
                if (contenidoArchivo != null) {
                    compilarCodigo(contenidoArchivo, nombreArchivo);
                } else {
                    System.out.println("El archivo no existe");
                }
            }
        } else { // SI NO SE PASÓ NINGÚN ARCHIVO COMO ARGUMENTO, MUESTRA UN MENSAJE INDICANDO QUE FALTA EL ARCHIVO
            System.out.println("Debes ingresar el nombre del archivo");

        }

    }

// MÉTODO QUE COMPILA EL CÓDIGO Y EJECUTA VALIDACIONES
    public static void compilarCodigo(String contenidoArchivo, String nombreArchivo) {
        Archivos archivos = new Archivos(); //INSTANCIA DE LA CLASE ARCHIVOS QUE MANEJAR LA LECTURA/ESCRITURA DEL ARCHIVO
        Logica logica = new Logica(); // INSTANCIA DE LA CLASE LOGICA, QUE CONTIENE LA LÓGICA DE VALIDACIÓN DEL CÓDIGO

        //LLAMA AL MÉTODO "ENUMERARCODIGO" EN LA CLASE "LOGICA" PARA AGREGAR NÚMEROS DE LÍNEA AL ARCHIVO DE ERRORES
        String resultadoValidaciones = logica.enumerarCodigo(contenidoArchivo);

        // LLAMA AL MÉTODO PARA CONTAR LOS OPERADORES PRESENTES EN EL CÓDIGO
        //ESTADO ACTUAL DE CONTEO + SALTO DE LINEA PARA SEPARAR LOS DIFERENTES CONTEOS POR OPERADOR
        resultadoValidaciones = resultadoValidaciones + "\n" + logica.contarOperadores(contenidoArchivo);

        // LLAMA AL MÉTODO PARA CONTAR LOS COMENTARIOS EN EL CÓDIGO
        resultadoValidaciones = resultadoValidaciones + "\n" + logica.contarComentarios(contenidoArchivo);

        // LLAMA AL MÉTODO PARA VALIDAR QUE LOS "IMPORT" SEAN CORRECTOS
        resultadoValidaciones = resultadoValidaciones + "\n" + logica.validarImports(contenidoArchivo);

        // LLAMA AL MÉTODO PARA VERIFICAR LA DECLARACIÓN DE VARIABLES
        resultadoValidaciones = resultadoValidaciones + "\n" + logica.validarDeclaracionVariables(contenidoArchivo);

        // LLAMA AL MÉTODO PARA VERIFICAR SI HAY "INPUTS" CORRECTOS EN EL CÓDIGO
        resultadoValidaciones = resultadoValidaciones + "\n" + logica.validarInputs(contenidoArchivo);
        //LLAMA AL METODO PARA VERIFICAR LOS WHILE
        resultadoValidaciones = resultadoValidaciones + "\n" + logica.validarWhile(contenidoArchivo);
        //LLAMA AL METODO PARA VERIFICAR LAS FUNCIONES
        resultadoValidaciones = resultadoValidaciones + "\n" + logica.validarFunciones(contenidoArchivo);
        //LLAMA AL METODO PARA VERIFICAR LOS LLAMADOS A LAS FUNCIONES
        resultadoValidaciones = resultadoValidaciones + "\n" + logica.validarLlamadoFunciones(contenidoArchivo);
        //LLAMA AL METODO PARA VERIFICAR LOS PRINTS
        resultadoValidaciones = resultadoValidaciones + "\n" + logica.validarPrints(contenidoArchivo);
        //LLAMA AL METODO PARA VERIFICAR LOS TRY EXCEPT
        resultadoValidaciones = resultadoValidaciones + "\n" + logica.validarTryExcept(contenidoArchivo);
        // AL FINAL ESCRIBE EL RESULTADO DE LAS VALIDACIONES EN UN ARCHIVO DE SALIDA
        archivos.crearArchivoErrores(nombreArchivo.replace(".py", "") + "-errores.log", resultadoValidaciones);

    }
}

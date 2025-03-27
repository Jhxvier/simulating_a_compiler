
//AUTHOR: HENRRY JAVIER LEIBA CALERO

/* 
 Code for parsing Python code, identifying tokens and lexical components to verify their correct usage and syntactic structures, implementing syntax-oriented translation, as well as understanding the use of intermediate code generation.
*/
package proyecto2;

import java.util.Arrays; // CONTIENE CLASE PARA MANIPULACIONES DE ARREGLOS
import java.util.HashSet; //MANIPULACION DE COLECCIONES
import java.util.Set;//MANIPULACION DE COLECCIONES
import java.util.regex.Matcher; // CONTIENE CLASE PARA BUSCAR COINCIDENCIAS CON EXPRESIONES REGULARES
import java.util.regex.Pattern; // CONTIENE CLASE PARA DEFINIR EXPRESIONES REGULARES

public class Logica {

    // MÉTODO PARA CONTAR OPERADORES EN EL CÓDIGO
    public String contarOperadores(String codigo) {
        StringBuilder resultado = new StringBuilder();
        // ARREGLO DE ENTEROS PARA CONTAR CADA OPERADOR EN EL ARCHIVO .PY
        int[] conteoOperadores = new int[Constantes.operadores.length];

        // CONTAR LA CANTIDAD DE CADA OPERADOR
        for (int i = 0; i < Constantes.operadores.length; i++) {
            String operador = Constantes.operadores[i];// OBTIENE CADA OPERADOR DEL ARREGLO
            int indice = codigo.indexOf(operador);// BUSCA EL ÍNDICE DEL OPERADOR EN EL CÓDIGO

            while (indice != -1) {// MIENTRAS SE ENCUENTRE EL OPERADOR EN LA CADENA
                conteoOperadores[i]++; // INCREMENTA EL CONTADOR AL OPERADOR ACTUAL
                indice = codigo.indexOf(operador, indice + 1);// ACTUALIZA EL INDICE PARA BUSCAR EL SIGUIENTE OPERADOR A PARTIR DE LA POSICIÓN SIGUIENTE 
            }
        }

        // CONCATENAR LOS RESULTADOS EN UNA CADENA
        for (int i = 0; i < Constantes.operadores.length; i++) {
            resultado.append("• " + conteoOperadores[i]) // AÑADE EL CONTADOR
                    .append(" token ") // AÑADE LA PALABRA 'token'
                    .append(Constantes.operadores[i]) // AÑADE EL NOMBRE DEL OPERADOR
                    .append("\n"); // AÑADE UN SALTO DE LÍNEA
        }

        return resultado.toString(); // DEVUELVE LOS RESULTADOS COMO UNA CADENA
    }

    // FUNCIÓN PARA VALIDAR LA UBICACIÓN DE LOS IMPORTS
    public String validarImports(String codigo) {
        String[] lineas = codigo.split("\n"); // SEPARA EL CÓDIGO EN LÍNEAS
        boolean importMalUbicado = false; // BANDERA PARA DETECTAR IMPORTS MAL UBICADOS
        boolean codigoIniciado = false; // BANDERA PARA DETECTAR SI EL CÓDIGO HA INICIADO
        boolean existeImport = false;
        StringBuilder resultado = new StringBuilder(); //PARA ALMACENAR RESULTADOS

        //RECORRER CADA LÍNEA DEL CÓDIGO
        for (int i = 0; i < lineas.length; i++) {
            String linea = lineas[i].trim();// ELIMINA ESPACIOS EN BLANCO DE LOS EXTREMOS

            // SALTA LÍNEAS QUE SON COMENTARIOS
            if (linea.startsWith("#") || linea.startsWith("\"\"\"") || linea.startsWith("'''")) {
                continue;
            }

            // INDICA QUE EL CÓDIGO HA INICIADO SI NO ES UN IMPORT
            if (!linea.isEmpty() && !linea.startsWith("import") && !linea.startsWith("from")) {
                codigoIniciado = true;
            }

            if (linea.startsWith("import")) {
                existeImport = true;
            }
            // VERIFICA SI HAY IMPORTS DESPUÉS DE INICIAR EL CÓDIGO
            if (codigoIniciado && (linea.startsWith("import") || linea.startsWith("from"))) {
                importMalUbicado = true; // MARCA IMPORT MAL UBICADO
                resultado.append("Error 200. Linea ") // AGREGA AL StringBuilder LA CADENA "Error 200. Linea "
                        .append(i + 1) // AGREGA EL NÚMERO DE LÍNEA DEL CÓDIGO DONDE SE ENCONTRÓ EL ERROR
                        .append(". Import mal ubicado") // AGREGA EL TEXTO QUE DESCRIBE EL ERROR
                        .append("\n"); // AGREGA UN SALTO DE LÍNEA
            }
        }

        if (!existeImport) {
            resultado.append("Error 200.  ") // AGREGA AL StringBuilder LA CADENA "Error 200.".append(i + 1) // AGREGA EL NÚMERO DE LÍNEA DEL CÓDIGO DONDE SE ENCONTRÓ EL ERROR
                    .append("Debe existir al menor un import.") // AGREGA EL TEXTO QUE DESCRIBE EL ERROR
                    .append("\n"); // AGREGA UN SALTO DE LÍNEA
        }

        return resultado.toString(); // DEVUELVE LOS RESULTADOS
    }

    // MÉTODO PARA CONTAR LOS COMENTARIOS EN EL CÓDIGO
    public String contarComentarios(String codigo) {

        // EXPRESIÓN REGULAR PARA ENCONTRAR COMENTARIOS DE UNA LÍNEA
        Pattern comentarioLinea = Pattern.compile("(?m)\\s*#.*"); //REGLA PARA BUSCAR COMENTARIOS QUE EMPIEZAN CON #
        Matcher matcherLinea = comentarioLinea.matcher(codigo); //APLICA LA REGLA AL CÓDIGO PARA ENCONTRAR COINCIDENCIAS

        // VARIABLE CONTADORA DE COMENTARIOS DE UNA LINEA
        int contadorComentariosLinea = 0;

        // CUENTA LOS COMENTARIOS DE UNA LÍNEA
        while (matcherLinea.find()) {
            contadorComentariosLinea++; // INCREMENTA EL CONTADOR
        }

        // EXPRESIÓN REGULAR PARA ENCONTRAR COMENTARIOS MULTILINEA
        Pattern comentarioMultilinea = Pattern.compile("\"\"\"(.*?)\"\"\"", Pattern.DOTALL); //BUSCA COMENTARIOS TRIPLES, DOTALL PERMITE QUE EL TEXTO INCLUYA SALTOS DE LINEA

        //APLICA LA REGLA AL CÓDIGO PARA ENCONTRAR COINCIDENCIAS
        Matcher matcherMultilinea = comentarioMultilinea.matcher(codigo);

        // VARIABLE CONTADORA DE COMENTARIOS MULTILINEA
        int contadorComentariosMultilinea = 0;

        // CUENTA LOS COMENTARIOS MULTILINEA
        while (matcherMultilinea.find()) {
            contadorComentariosMultilinea++; // INCREMENTA EL CONTADOR
        }

        // CALCULA EL TOTAL DE COMENTARIOS
        int totalComentarios = contadorComentariosLinea + contadorComentariosMultilinea;
        return totalComentarios + " lineas de comentarios"; // DEVUELVE EL TOTAL DE COMENTARIOS
    }

    // MÉTODO PARA ENUMERAR CÓDIGO
    public String enumerarCodigo(String codigo) {
        StringBuilder codigoEnumerado = new StringBuilder();
        String[] lineas = codigo.split("\n"); // SEPARA EL CÓDIGO EN LÍNEAS

        // ENUMERA CADA LÍNEA
        for (int i = 0; i < lineas.length; i++) {

            String numeroLinea = String.format("%03d", i + 1); // FORMATEA EL NÚMERO DE LÍNEA CON TRES DÍGITOS
            codigoEnumerado.append(numeroLinea).append("  ").append(lineas[i]).append("\n"); // AÑADE EL NÚMERO Y LA LÍNEA AL StringBuilder
        }

        return codigoEnumerado.toString(); // DEVUELVE EL CÓDIGO ENUMERADO COMO UNA CADENA
    }

    public String validarDeclaracionVariables(String codigoPython) {

        StringBuilder errores = new StringBuilder(); // StringBuilder PARA ALMACENAR ERRORES

        String[] lineas = codigoPython.split("\n"); // SEPARA EL CÓDIGO EN LÍNEAS

        int contadorLineasComentarios = 0; // CONTADOR PARA LÍNEAS DE COMENTARIOS

        // RECORRE CADA LÍNEA DEL CÓDIGO
        for (int i = 0; i < lineas.length; i++) {
            String linea = lineas[i].trim(); // ELIMINA ESPACIOS EN BLANCO DE LOS EXTREMOS

            // CONTABILIZA LÍNEAS QUE SON COMENTARIOS
            if (linea.startsWith("#")) {
                contadorLineasComentarios++;
                continue; // CONTINÚA A LA SIGUIENTE ITERACIÓN
            }

            // VERIFICA SI LA LÍNEA NO ESTÁ VACÍA Y ES UNA DECLARACIÓN DE ASIGNACIÓN
            if (!linea.isEmpty() && esDeclaracionAsignacion(linea)) {

                String[] partes = linea.split("="); // SEPARA LA LÍNEA EN PARTES POR EL SIGNO '='

                if (partes.length >= 2) {
                    String variable = partes[0].trim(); // OBTIENE EL NOMBRE DE LA VARIABLE

                    // VERIFICA SI LA VARIABLE COMIENZA CON UN NÚMERO
                    if (Character.isDigit(variable.charAt(0))) {
                        errores.append("Error 200. Línea ") // AÑADE UN MENSAJE DE ERROR
                                .append(i + 1 - contadorLineasComentarios)
                                .append(". La variable '").append(variable)
                                .append("' no puede empezar con un número.\n");
                    }

                    // VERIFICA SI LA VARIABLE CONTIENE CARACTERES NO PERMITIDOS
                    String nombreVariable = obtenerNombreVariable(variable);
                    if (!esNombreVariableValido(nombreVariable)) {
                        errores.append("Error 200. Línea ")
                                .append(i + 1 - contadorLineasComentarios)
                                .append(". La variable '").append(variable)
                                .append("' contiene caracteres no permitidos.\n");
                    }

                    // VERIFICA SI LA VARIABLE ES UNA PALABRA RESERVADA
                    if (esPalabraReservada(nombreVariable)) {
                        errores.append("Error 200. Línea ")
                                .append(i + 1 - contadorLineasComentarios)
                                .append(". ").append(nombreVariable)
                                .append(" Es una palabra reservada y no se puede usar como nombre de variable.\n");
                    }
                }
            }
        }

        return errores.toString(); // DEVUELVE TODOS LOS ERRORES ENCONTRADOS EN UNA CADENA
    }

    // MÉTODO PARA VERIFICAR SI ES UNA DECLARACIÓN DE ASIGNACIÓN
    private boolean esPalabraReservada(String variable) {
        return Arrays.asList(Constantes.palabrasReservadas).contains(variable); // DEVUELVE VERDADERO SI HAY UN SIGNO '='
    }

    // MÉTODO PARA OBTENER EL NOMBRE DE LA VARIABLE
    private boolean esDeclaracionAsignacion(String linea) {
        // DEVUELVE VERDADERO SI LA LÍNEA CONTIENE '=' Y NO INICIA CON PALABRAS CLAVE
        return linea.contains("=") && (!(linea.startsWith("if") || linea.startsWith("for") || linea.startsWith("while")
                || linea.contains("print") || linea.contains("input") || linea.contains("random")
                || linea.startsWith("def") || linea.contains("all"))); // AGREGA TAMBIEN EL 'all'
    }

    private boolean esNombreVariableValido(String variable) {
        // VERIFICA QUE CADA CARÁCTER DE LA VARIABLE SEA UNA LETRA, DÍGITO O '_'
        for (char c : variable.toCharArray()) {
            if (!Character.isLetterOrDigit(c) && c != '_') {
                return false;
            }
        }
        return true; // DEVUELVE VERDADERO SI TODOS LOS CARACTERES SON VÁLIDOS
    }

    private String obtenerNombreVariable(String variable) {
        // OBTIENE EL NOMBRE DE LA VARIABLE ANTES DEL ÍNDICE DE '[' (SI EXISTE)
        int indice = variable.indexOf('[');
        return (indice != -1) ? variable.substring(0, indice).trim() : variable.trim();
    }

    // MÉTODO PARA VALIDAR LA SINTAXIS DEL COMANDO 'input'
    public String validarInputs(String codigoPython) {
        StringBuilder errores = new StringBuilder(); //StringBuilder PARA ALMACENAR ERRORES DE SINTAXIS

        // SEPARA EL CÓDIGO EN LÍNEAS
        String[] lineas = codigoPython.split("\n");

        // ITERA SOBRE CADA LÍNEA DEL CÓDIGO
        for (int i = 0; i < lineas.length; i++) {
            String linea = lineas[i].trim(); // ELIMINA ESPACIOS EN BLANCO AL INICIO Y AL FINAL

            // ELIMINA COMENTARIOS DE LA LÍNEA PARA VALIDAR SOLO EL CÓDIGO
            String lineaSinComentarios = eliminarComentariosDeLinea(linea);

            // VERIFICA SI LA LÍNEA CONTIENE 'input(' Y NO CONTIENE 'map'
            if (lineaSinComentarios.contains("input(") && !linea.contains("map")) {

                // VALIDA LA SINTAXIS DE 'input
                // SI NO ES VÁLIDA, AGREGA UN MENSAJE DE ERROR AL StringBuilder
                if (!validarDeclaracionSimpleInput(lineaSinComentarios)) {
                    errores.append("Error 200. Linea ").append(i + 1)
                            .append(".La sintaxis de 'input' no es válida").append("\n");
                }
            }
        }
        // DEVUELVE LA LISTA DE ERRORES EN FORMA DE CADENA
        return errores.toString();
    }

    // METODO QUE ELIMINA LOS COMENTARIOS DE UNA LÍNEA
    // SE ELIMINAN LOS COMENTARIOS QUE INICIAN CON '#' Y LOS MULTILINEA QUE USAN '"""'
    private String eliminarComentariosDeLinea(String linea) {
        return linea.replaceAll("#.*", "").replaceAll("\"\"\"[\\s\\S]*?\"\"\"", "");
    }

    // VERIFICA SI LA LÍNEA TIENE UNA SINTAXIS VÁLIDA PARA EL USO DEL COMANDO 'input'
    // UTILIZA UNA EXPRESIÓN REGULAR O PATRON DE BUSQUEDA PARA COMPROBAR LA ESTRUCTURA
    //DEBEN SEGUIR EL FORMATO DE DECLARACIONES 'input' DE PYTHON
    private boolean validarDeclaracionSimpleInput(String linea) {

        String patronBusqueda = "^[a-zA-Z_][a-zA-Z0-9_]*\\s*=\\s*input\\s*\\(\\s*\"[^\"]*\"\\s*\\)\\s*$";
        return linea.matches(patronBusqueda); // DEVUELVE VERDADERO SI COINCIDE CON LA EL PATRON DE BUSQUEDA
    }
    
    
    // Función para validar el bloque de 'while' en términos de indentación
    public String validarWhile(String codigo) {
        String[] lineas = codigo.split("\n");//SE DIVIDE EL CODIGO EN LINEAS
        StringBuilder errores = new StringBuilder(); //StringBuilder PARA ALMACENAR ERRORES DE SINTAXIS

        for (int i = 0; i < lineas.length; i++) {
            String linea = lineas[i].trim();

            // SE DETECTA EL BLOQUE WHILE
            if (linea.startsWith("while") && linea.endsWith(":")) {
                int nivelIndentacionWhile = contarEspaciosInicio(lineas[i]);//SE VERIFICA LA IDENTACION
                boolean bloqueValido = false;
                //SE ITERAN LAS LINEAS
                for (int j = i + 1; j < lineas.length; j++) {
                    int nivelIndentacionLinea = contarEspaciosInicio(lineas[j]);

                    // SI ENCONTRAMOS UNA LINEA VACIA CONTINUAMOS
                    if (lineas[j].trim().isEmpty()) {
                        continue;
                    }

                    // VERIFICAMOS QUE EL BLOQUE TENGA LA IDENTACION CORRECTA
                    if (nivelIndentacionLinea <= nivelIndentacionWhile) {
                        if (!bloqueValido) {
                            errores.append("Error 200. Línea ").append(i + 1)
                                    .append(": El bloque 'while' no contiene código indentado correctamente.\n");
                        }
                        break;
                    } else {
                        bloqueValido = true;
                    }
                }
            } else if (linea.startsWith("while") && !linea.endsWith(":")) {//VERIFICAMOS LA ESTRUCTURA DEL WHILE
                errores.append("Error 200. Línea ").append(i + 1)
                        .append(": La declaracion de bloque while debe de finalizar con : .\n");
            }
        }

        return errores.toString();//RETORNAMOS LOS MENSAJES DE ERROR EN CASO DE QUE LOS HAYA.
    }

// FUNCION PARA CONTAR LOS ESPACIOS EN EL INICIO DE UNA LINEA
    private int contarEspaciosInicio(String linea) {
        int espacios = 0;
        //ITERAMOS LA LINEA HASTA QUE NO HAYAN ESPACIOS
        while (espacios < linea.length() && linea.charAt(espacios) == ' ') {
            espacios++;//AUMENTAMOS EL CONTADOR
        }
        return espacios;
    }

    // METODO PARA VALIDAR LLA DEFINICION DE FUNCIONES
    public String validarFunciones(String codigo) {
        String[] lineas = codigo.split("\n");//SE SEPARA EL CODIGO EN LINEAS
        StringBuilder errores = new StringBuilder(); //StringBuilder PARA ALMACENAR ERRORES DE SINTAXIS

        for (int i = 0; i < lineas.length; i++) {
            String linea = lineas[i].trim();

            // SE VERIFICA SI LA LINEA INICIA CON DEF 
            if (linea.startsWith("def ")) {
                // SE VALIDA QUE HAYAN PARENTESIS TE APERTURA Y CIERRE
                if (!linea.contains("(") || !linea.contains(")")) {
                    errores.append("Error 200. Línea ").append(i + 1)
                            .append(": La función no contiene paréntesis de apertura y cierre correctamente.\n");
                    continue;
                }

                // SE VALIDA QUE LOS PUNTOS ESTEN AL FINAL DE LA DECLARACION DE LA FUNCION
                if (!linea.endsWith(":")) {
                    errores.append("Error 200. Línea ").append(i + 1)
                            .append(": La función no termina con dos puntos.\n");
                    continue;
                }

                // SE VERIFICA EL BLOQUE IDENTADO DE CONYENIDO DE LA FUNCION
                boolean bloqueValido = false;
                int nivelIndentacionDef = contarEspaciosInicio(lineas[i]);

                for (int j = i + 1; j < lineas.length; j++) {
                    String siguienteLinea = lineas[j].trim();
                    int nivelIndentacionLinea = contarEspaciosInicio(lineas[j]);

                    // SE OMITEN LAS LINEAS VACIAS
                    if (siguienteLinea.isEmpty()) {
                        continue;
                    }

                    // BLOQUE NO IDENTADO
                    if (nivelIndentacionLinea <= nivelIndentacionDef) {
                        if (!bloqueValido) {
                            errores.append("Error 200. Línea ").append(i + 1)
                                    .append(": El bloque de la función no contiene código indentado correctamente.\n");
                        }
                        break;
                    } else {
                        bloqueValido = true;//BLOQUE CORRECTAMENTE IDENTADO
                    }
                }
            }
        }

        return errores.toString();
    }

    //METODO PARA VALIDAR EL LLAMADO DE LAS FUNCIONES
    public String validarLlamadoFunciones(String codigoPython) {
        StringBuilder errores = new StringBuilder(); //StringBuilder PARA ALMACENAR ERRORES DE SINTAXIS
        String[] lineas = codigoPython.split("\n");
        Set<String> funcionesDefinidas = obtenerFuncionesDefinidas(codigoPython);

        for (int i = 0; i < lineas.length; i++) {
            String linea = lineas[i].trim();

            // SE OMITEN COMENTARIOS Y LINEAS VACIAS
            if (linea.isEmpty() || linea.startsWith("#") || linea.startsWith("print(")) {
                continue;
            }

            // SE VERIFICA SI LA LINEA CONTIENE UN LLAMADO A UNA FUNCION
            if (esLlamadoFuncion(linea)) {
                String nombreFuncion = obtenerNombreFuncionLlamada(linea);

                // SE VALIDA SI LA FUNCION ESTA DEFINIDA
                if (!funcionesDefinidas.contains(nombreFuncion)) {
                    errores.append("Error 200. Línea ")
                            .append(i + 1)
                            .append(". La función '")
                            .append(nombreFuncion)
                            .append("' no está definida.\n");
                }
            }
        }

        return errores.toString();
    }

// METODO PARA OBTENER LAS FUNCIONES DEFINIDAS EN EL CODIGO
    private Set<String> obtenerFuncionesDefinidas(String codigoPython) {
        Set<String> funcionesDefinidas = new HashSet<>();
        String[] lineas = codigoPython.split("\n");

        for (String linea : lineas) {
            String lineaTrim = linea.trim();
            if (esDefinicionFuncion(lineaTrim)) {
                String nombreFuncion = obtenerNombreFuncionDefinida(lineaTrim);
                funcionesDefinidas.add(nombreFuncion);
            }
        }

        return funcionesDefinidas;
    }

// METODO PARA VALIDAR SI UNA LINEA CORRESPONDE AL LLAMADO DE UNA FUNCION
    private boolean esLlamadoFuncion(String linea) {
        return linea.matches("[a-zA-Z_][a-zA-Z0-9_]*\\s*\\(.*\\)");
    }

// METODO PARA OBTENER EL NOMBRE DEL LLAMADO DE UNA FUNCION
    private String obtenerNombreFuncionLlamada(String linea) {
        int indiceParentesis = linea.indexOf('(');
        return linea.substring(0, indiceParentesis).trim();
    }

// METODO PARA VALIDAR SI LA LINEA CORRESPONDE A LA DEFINICION DE UNA FUNCION
    private boolean esDefinicionFuncion(String linea) {
        return linea.matches("^def\\s+[a-zA-Z_][a-zA-Z0-9_]*\\s*\\(.*\\)\\s*:\\s*$");
    }

// METODO PARA OBTENER EL NOMBRE DE UNA FUNCION EN UNA DEFINICION
    private String obtenerNombreFuncionDefinida(String linea) {
        int indiceInicioNombre = linea.indexOf("def") + 3;
        int indiceFinNombre = linea.indexOf('(');
        return linea.substring(indiceInicioNombre, indiceFinNombre).trim();
    }

    //METODO PARA VALIDAR LOS PRINTS
    public String validarPrints(String codigoPython) {
        StringBuilder errores = new StringBuilder(); //StringBuilder PARA ALMACENAR ERRORES DE SINTAXIS
        String[] lineas = codigoPython.split("\n");
        Set<String> variablesDeclaradas = obtenerVariablesDeclaradas(codigoPython);//COLECCION PARA GUARDAR LAS VARIABLES DECLARADAS
    //SE INTERAN LAS LINEAS DEL CODIGO
        for (int i = 0; i < lineas.length; i++) {
            String linea = lineas[i].trim();

            // SE IGNORAN LOS COMENTARIOS Y LINEAS VACIAS
            if (linea.isEmpty() || linea.startsWith("#")) {
                continue;
            }

            // SE IGNORAN LAS EXCEPCIONES DE LOS PRINTS
            if (validarPrintExcepcion(linea)) {
                continue;
            }

            //SE OBTIENE LA LINEA SIN COMENTARIOS
            String lineaSinComentario = linea.contains("#") ? linea.substring(0, linea.indexOf("#")).trim() : linea;

            // SE VALIDA SI LA LINEA CONTIENE UN PRINT
            if (linea.startsWith("print(") && linea.endsWith(")")) {
                if (!esPrintValido(linea, variablesDeclaradas) && !linea.contains("if") && !linea.contains("else")) {
                    errores.append("Error 200. Línea ")
                            .append(i + 1)
                            .append(". Sintaxis de print() no válida: '")
                            .append(linea)
                            .append("'\n");
                }
            } else if (lineaSinComentario.startsWith("print(") && !lineaSinComentario.endsWith(")")) {
                //SE VALIDA LA ESTRUCTURA DEL PRINT
                errores.append("Error 200. Línea ")
                        .append(i + 1)
                        .append(". Falta el parentesis de cierre: '")
                        .append(linea)
                        .append("'\n");
            }
        }

        return errores.toString();
    }

// METODO PARA VALIDAR LA SINTAXIS CORRECTA DEL PRINT
    private boolean esPrintValido(String linea, Set<String> variablesDeclaradas) {
        String contenido = linea.substring(6, linea.length() - 1).trim(); // Extraer contenido dentro de los paréntesis

        // PRINT()
        if (contenido.isEmpty()) {
            return true;
        }

        // PRINT("")
        if (contenido.startsWith("\"") && contenido.endsWith("\"")) {
            return true;
        }

        // PRINT(VARIABLE)
        if (variablesDeclaradas.contains(contenido)) {
            return true;
        }

        return false;
    }

    // METODO PARA IDENTIFICAR SI UNA LINEA ES PRINT DE UN EXCEPCION
    private boolean validarPrintExcepcion(String linea) {
        return linea.matches("^print\\(f?\".*\".*end=.*\\)$");
    }

    // METODO PARA OBTENER LAS DECLARACIONES DEL CODIGO
    private Set<String> obtenerVariablesDeclaradas(String codigoPython) {
        Set<String> variablesDeclaradas = new HashSet<>();
        String[] lineas = codigoPython.split("\n");

        for (String linea : lineas) {
            linea = linea.trim();

            // SE IDENTIFICAN LAS VARIABLES
            if (linea.matches("^[a-zA-Z_][a-zA-Z0-9_]*\\s*=.*$")) {
                String nombreVariable = linea.split("=")[0].trim();
                variablesDeclaradas.add(nombreVariable);
            }
        }

        return variablesDeclaradas;
    }

    public String validarTryExcept(String codigoPython) {
        StringBuilder errores = new StringBuilder(); //StringBuilder PARA ALMACENAR ERRORES DE SINTAXIS
        String[] lineas = codigoPython.split("\n");
        boolean dentroDeTry = false;
        boolean dentroDeExcept = false;
        boolean tieneLineaEnTry = false;
        boolean contienePrintEnExcept = false; // INDICADOR PARA LOS PRINTS EN LOS EXCEPT
        int nivelIdentacionTry = -1;
        int nivelIdentacionExcept = -1;
        String excepcionDetectada = null;
        int lineaTry = -1;
        int lineaExcept = -1;

        for (int i = 0; i < lineas.length; i++) {
            String linea = lineas[i].trim();
            int nivelIdentacionActual = contarEspaciosInicio(lineas[i]);

            // SE IGNORAN COMENTARIOS Y LINEAS VACIAS
            if (linea.isEmpty() || linea.startsWith("#")) {
                continue;
            }

            if (linea.startsWith("try")) {

                String lineaSinComentario = linea.contains("#") ? linea.substring(0, linea.indexOf("#")).trim() : linea;

                if (!lineaSinComentario.endsWith(":")) {
                    errores.append("Error 200. Línea ").append(i + 1).append(". La instruccion 'try' debe finalizar con ':'.\n");
                }

                dentroDeTry = true;
                dentroDeExcept = false;
                tieneLineaEnTry = false;
                nivelIdentacionTry = nivelIdentacionActual + 4; // IDENTACION DEL BLOQUE TRY
                lineaTry = i + 1;
            } else if (linea.startsWith("except")) {
                String lineaSinComentario = linea.contains("#") ? linea.substring(0, linea.indexOf("#")).trim() : linea;
                if (!lineaSinComentario.endsWith(":")) {
                    errores.append("Error 200. Línea ").append(i + 1).append(". La instruccion 'except' debe finalizar con ':'.\n");
                }
                if (!dentroDeTry || dentroDeExcept) {
                    errores.append("Error 200. Línea ").append(i + 1).append(". 'except' sin bloque try correspondiente.\n");
                }
                dentroDeTry = false;
                dentroDeExcept = true;
                contienePrintEnExcept = false;
                nivelIdentacionExcept = nivelIdentacionActual + 4; // IDENTACION DEL BLOQUE EXCEPT
                lineaExcept = i + 1;

                // SE VALIDA LA EXCEPCION
                excepcionDetectada = obtenerExcepcion(linea);
                if (excepcionDetectada == null || !vaidarExcepcionValida(excepcionDetectada)) {
                    errores.append("Error 200. Línea ").append(i + 1).append(". Excepción no válida: '").append(excepcionDetectada).append("'.\n");
                }
            } else if (dentroDeTry && nivelIdentacionActual == nivelIdentacionTry) {
                tieneLineaEnTry = true;
            } else if (dentroDeExcept && nivelIdentacionActual == nivelIdentacionExcept) {
                if (linea.startsWith("print(")) {
                    contienePrintEnExcept = true; // CONFIRMACION SI HAY UN PRINT EN EL BLOQUE EXCEPT
                }
            } else {
                if (nivelIdentacionActual <= nivelIdentacionTry) {
                    dentroDeTry = false;
                }
                if (nivelIdentacionActual <= nivelIdentacionExcept) {
                    if (!contienePrintEnExcept) {
                        errores.append("Error 200. Línea ").append(lineaExcept).append(". Bloque except debe manejar el error con al menos un print().\n");
                    } else {
                        dentroDeExcept = true;

                    }
                }
            }
        }

        // SE VALIDA SI FALTARON LINEAS
        if (lineaTry > 0 && !tieneLineaEnTry) {
            errores.append("Error 200. Línea ").append(lineaTry).append(". Bloque try debe contener al menos una línea.\n");
        }
        if (lineaExcept > 0 && !contienePrintEnExcept) {
            errores.append("Error 200. Línea ").append(lineaExcept).append(". Bloque except debe manejar el error con al menos un print().\n");
        }

        return errores.toString();
    }

// METODO PARA OBTENER LA EXCEPCION
    private String obtenerExcepcion(String linea) {
        if (linea.startsWith("except ")) {
            int inicio = linea.indexOf(" ") + 1;
            int fin = linea.indexOf(":");
            if (inicio > 0 && fin > inicio) {
                return linea.substring(inicio, fin).trim();
            }
        }
        return null;
    }

// METODO PARA VERIFICAR SI LA EXCEPCION ES VALIDA
    private boolean vaidarExcepcionValida(String excepcion) {
        return excepcion.equals("ArithmeticError") || excepcion.equals("ZeroDivisionError") || excepcion.equals("ValueError");
    }

}


//AUTHOR: HENRRY JAVIER LEIBA CALERO

/* 
 Code for parsing Python code, identifying tokens and lexical components to verify their correct usage and syntactic structures, implementing syntax-oriented translation, as well as understanding the use of intermediate code generation.
*/

package proyecto2;

public class Constantes {

    // ARREGLO QUE ALMACENA OPERADORES DE COMPARACIÓN
    public static String[] operadores = new String[] { "==", "!=", ">", "<", ">=", "<=" };
    
    // ARREGLO QUE ALMACENA PALABRAS RESERVADAS DEL LENGUAJE PYTHON
    public static String[] palabrasReservadas = new String[] {
            "import", "break", "elif", "or",
            "def", "continue", "False", "pass",
            "for", "else", "finally", "raise",
            "if", "and", "from", "True",
            "print", "as", "global", "with",
            "return", "assert", "is", "yield",
            "in", "async", "lambda", "not",
            "while", "await", "None", "del",
            "try", "class", "nonlocal", "except",
            "range", "input"
    };

}

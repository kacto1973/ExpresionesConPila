import java.util.Scanner;
import java.util.Stack;

public class Principal {
    public static void main(String[] args) {

        //Pedimos los datos al usuario
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese datos sin espacios");
        String expresionInfija = scanner.next();
        System.out.println("Su expresion infija ingresada: " + expresionInfija);
        String resultadoPosfijo = transformadorPosfijo(expresionInfija);
        System.out.println("Resultado posfijo: " + resultadoPosfijo);

    }


    public static String transformadorPosfijo(String expresionInfija) {

        //con este objeto vamos a construir nuestra version final posfija
        StringBuilder expresionPosfija = new StringBuilder();

        //definimos nuestra pila para usarla para almacenar los caracteres
        Stack<Character> pila = new Stack<>();


        //vamos a trabajar mientras que aun nos quede texto con el cual trabajar
        for (int i = 0; i < expresionInfija.length(); i++) {

            //creamos la variable token que contendra el token actualmente analizado
            char token = expresionInfija.charAt(i);

            //si el caracter es un operando, lo pasamos directamente a la expresion posfija
            if (Character.isLetterOrDigit(token)) {
                expresionPosfija.append(token);
            }

            //si el caracter es un parentesis ( , lo enviamos a la pila
            if (token == '(') {
                pila.push(token);
            }

            //si es un parentesis ) , hacemos:
            if (token == ')') {

                //mientras tengamos con que trabajar y el tope de la pila no sea ( entonces
                //a la expresion posfija le agregaremos el ultimo dato de la pila
                while (!pila.isEmpty() && pila.peek() != '(') {
                    expresionPosfija.append(pila.pop());
                }

                //si el tope de la pila es ( , entonces lo sacamos
                if (!pila.isEmpty() && pila.peek() == '(') {
                    pila.pop();
                }
            } //fin if


            //si no resulto ser el char analizado actualmente un operando o un parentesis, eso quiere decir
            //que estamos analizando un operando, por lo tanto checamos su precedencia/importancia
            else {
                while (!pila.isEmpty() && precedencia(token) <= precedencia(pila.peek())) {
                    expresionPosfija.append(pila.pop());//sacamos el valor de la pila y lo intercambiamos con el actual
                }
                pila.push(token);//lo ponemos al final, ya sea intercambiado o no intercambiado
            }
        }

        while (!pila.isEmpty()) {
            //al final de analizar todo vaciaremos la pila y agregaremos valores
            //que sobraron a la expresion posfija
            expresionPosfija.append(pila.pop());
        }

        //regresamos la expresion posfija para su impresion
        return expresionPosfija.toString();
    }//fin metodo transformador

    //asignamos un valor a las operaciones aritmeticas basicas para su manejo en la pila
    private static int precedencia(char operador) {
        switch (operador) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return -1;
        }
    }//fin valor precedencia/importancia


}//fin clase

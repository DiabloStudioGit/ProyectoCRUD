package Inputs

class InputsJuego {
    companion object {
        /**
         * Solicita al usuario que introduzca un número entre 1 y 10, y lo devuelve una vez validado.
         *
         * @return El número introducido por el usuario.
         */
        fun introducirNumero(): Int {
            var numero: Int?

            do {
                print("Introduce número a ver si aciertas: ")
                numero = readln().toIntOrNull()
                if (numero == null || numero < 1 || numero > 10) {
                    println("!ERROR!")
                }
            } while (numero == null || numero < 1 || numero > 10)

            return numero
        }
    }
}
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
                var valido = true
                print("Introduce un número del 1 al 10 a ver si aciertas: ")
                numero = readln().toIntOrNull()
                if (numero == null || numero !in 1..10) {
                    println("!ERROR!")
                    valido = false
                }
            } while (!valido)

            return numero!!
        }
    }
}
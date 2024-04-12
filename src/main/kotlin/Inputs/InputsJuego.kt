package Inputs

import UI.MenuColores

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
                print("Introduce un " + MenuColores.azul("número") + " del " + MenuColores.rojo("1") + " al " + MenuColores.rojo("10") + " a ver si aciertas: ")
                numero = readln().toIntOrNull()
                if (numero == null) {
                    println(MenuColores.error() + " El valor no puede ser vacío o " + MenuColores.magenta("nulo"))
                    valido = false
                } else if (numero !in 1..10) {
                    println(MenuColores.error() + " El valor debe estar en el rango de " + MenuColores.rojo("1") + " a " + MenuColores.rojo("10"))
                    valido = false
                }
            } while (!valido)

            return numero!!
        }
    }
}
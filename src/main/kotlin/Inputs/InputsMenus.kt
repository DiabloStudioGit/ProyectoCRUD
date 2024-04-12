package Inputs

import UI.MenuColores

class InputsMenus {
    companion object {
        /**
         * Solicita al usuario que introduzca un número que represente una opción del menú,
         * asegurándose de que el número esté dentro del rango [1 - numOpciones].
         *
         * @param numeroOpciones El número total de opciones disponibles en el menú.
         * @return El número de la opción seleccionada por el usuario.
         */
        fun seleccionarOpcionMenu(numeroOpciones: Int): Int {
            var opt: Int?

            do {
                var valido = true
                print("Introduce una opción " + MenuColores.random("[1-$numeroOpciones]") + ": ")
                opt = readln().toIntOrNull()

                if (opt == null) {
                    println(MenuColores.error() + " El numero no puede ser " + MenuColores.magenta("nulo") + ".")
                    valido = false
                } else if (opt !in 1..numeroOpciones) {
                    println(MenuColores.error() + " La opción " + MenuColores.magenta(opt.toString()) + " está fuera del rango válido.")
                    println(MenuColores.azul("Sugerencia:") + " Prueba a introducir un número entre el rango " + MenuColores.cian("[1-$numeroOpciones]"))
                    valido = false
                }
            } while (!valido)

            return opt!!
        }
    }
}
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
                print("Introduce una opción " + MenuColores.set("[1-$numeroOpciones]", MenuColores.magenta) + ": ")
                opt = readln().toIntOrNull()
                if (opt == null || opt !in 1.. numeroOpciones) {
                    println(MenuColores.set(MenuColores.error() + "!La opción $opt no existe "))
                    println(MenuColores.set("Suegerencia: Prueba a introducir un numero entre el rango ", MenuColores.azul) + MenuColores.set("[1-$numeroOpciones]", MenuColores.cian))
                    valido = false
                }
            } while (!valido)

            return opt!!
        }

        /**
         * Indica la acción de salida del menú.
         *
         * @return `true` para indicar que se debe salir del menú.
         */
        fun salirMenu(): Boolean {
            return true
        }
    }
}
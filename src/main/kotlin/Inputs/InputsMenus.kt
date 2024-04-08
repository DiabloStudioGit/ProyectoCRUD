package Inputs

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
                print("Introduce una opción [1-$numeroOpciones]: ")
                opt = readln().toIntOrNull()
                if (opt == null || opt !in 1.. numeroOpciones) {
                    println("!ERROR!")
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
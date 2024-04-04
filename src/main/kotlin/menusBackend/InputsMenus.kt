package menusBackend

class InputsMenus {
    /**
     *
     * Solicita al usuario que introduzca un número que represente una opción del menú,
     * asegurándose de que el número esté dentro del rango [1 - numOpciones].
     *
     * @param numeroOpciones El número total de opciones disponibles en el menú.
     * @return El número de la opción seleccionada por el usuario.
     */
    fun seleccionarOpcionMenu(numeroOpciones: Int): Int {
        var opt: Int?

        do {
            print("Introduce una opción [1-$numeroOpciones]: ")
            opt = readln().toIntOrNull()
            if (opt == null || opt < 1 || opt > numeroOpciones) {
                println("!ERROR!")
            }
        } while (opt == null || opt < 1 || opt > numeroOpciones)

        return opt
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












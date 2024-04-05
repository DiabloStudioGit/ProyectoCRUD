package menusBackend

import juego.Historial

class InputsJuego {
    companion object {
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
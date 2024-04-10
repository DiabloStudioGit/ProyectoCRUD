package UI

class MenuColores {
    companion object {
        //Colores ANSI

        val negro = "\u001b[30m"
        val rojo = "\u001b[31m"
        val verde = "\u001b[32m"
        val amarillo = "\u001b[33m"
        val azul = "\u001b[34m"
        val magenta = "\u001b[35m"
        val cian = "\u001b[36m"
        val blanco = "\u001b[37m"

        val fondoNegro = "\u001b[40m"
        val fondoRojo = "\u001b[41m"
        val fondoVerde = "\u001b[42m"
        val fondoAmarillo = "\u001b[43m"
        val fondoAzul = "\u001b[44m"
        val fondoMagenta = "\u001b[45m"
        val fondoCian = "\u001b[46m"
        val fondoBlanco = "\u001b[47m"

        val reset = "\u001b[0m"

        fun set(texto: String, color: String = this.reset): String {
            return color + texto + this.reset
        }

        fun ok(): String {
            return set("[OK]", this.verde)
        }

        fun error(): String {
            return set("[ERROR]", this.rojo)
        }

        fun random(texto: String): String {
            val colores = arrayOf(rojo, verde, amarillo, azul, magenta, cian)
            return set(texto, colores.random())
        }
    }
}
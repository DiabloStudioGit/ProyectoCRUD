package UI

class MenuColores {
    //Colores ANSI

    companion object {
        //Funcion Set
        private fun set(texto: String, color: String): String {
            val reset = "\u001b[0m"
            return color + texto + reset
        }

        //Status
        fun ok(): String {
            return verde("[OK]")
        }

        fun error(): String {
            return rojo("[ERROR]")
        }

        fun info(): String {
            return amarillo("[INFO]")
        }

        //Metodos colores
        fun random(texto: String): String {
            val colores = arrayOf(::rojo, ::verde, ::amarillo, ::azul, ::magenta, ::cian)
            return (colores.random()).invoke(texto)
        }

        fun rojo(texto: String) : String {
            val rojo = "\u001b[31m"
            return set(texto, rojo)
        }

        fun verde(texto: String): String {
            val verde = "\u001b[32m"
            return set(texto, verde)
        }

        fun amarillo(texto: String): String {
            val amarillo = "\u001b[33m"
            return set(texto, amarillo)
        }

        fun azul(texto: String): String {
            val azul = "\u001b[34m"
            return set(texto, azul)
        }

        fun magenta(texto: String): String {
            val magenta = "\u001b[35m"
            return set(texto, magenta)
        }

        fun cian(texto: String): String {
            val cian = "\u001b[36m"
            return set(texto, cian)
        }

        fun fondoRojo(texto: String): String {
            val fondoRojo = "\u001b[41m"
            return set(texto, fondoRojo)
        }

        fun fondoVerde(texto: String): String {
            val fondoVerde = "\u001b[42m"
            return set(texto, fondoVerde)
        }

        fun fondoAmarillo(texto: String): String {
            val fondoAmarillo = "\u001b[43m"
            return set(texto, fondoAmarillo)
        }

        fun fondoAzul(texto: String): String {
            val fondoAzul = "\u001b[44m"
            return set(texto, fondoAzul)
        }

        fun fondoMagenta(texto: String): String {
            val fondoMagenta = "\u001b[45m"
            return set(texto, fondoMagenta)
        }

        fun fondoCian(texto: String): String {
            val fondoCian = "\u001b[46m"
            return set(texto, fondoCian)
        }

        fun fondoBlanco(texto: String): String {
            val fondoBlanco = "\u001b[47m"
            return set(texto, fondoBlanco)
        }
    }

}
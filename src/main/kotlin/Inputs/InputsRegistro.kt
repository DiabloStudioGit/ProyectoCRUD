package Inputs

import Usuario.Roles

class InputsRegistro {
    companion object {
        /**
         * Solicita al usuario que introduzca su nombre y devuelve este nombre como una cadena de texto.
         *
         * Esta función garantiza que el nombre ingresado no sea nulo y solicita al usuario que lo vuelva a ingresar en caso de ser nulo.
         *
         * @return El nombre ingresado por el usuario como una cadena de texto.
         */
        fun introducirNombre(): String {
            var nombre: String?

            do {
                var valido = true
                print("Introduce tu nombre: ")
                nombre = readlnOrNull()
                if (nombre.isNullOrBlank()) {
                    println("!ERROR!")
                    valido = false
                }
            } while (!valido)

            return nombre!!
        }

        /**
         * Solicita al usuario que introduzca un correo electrónico y devuelve el correo electrónico como una cadena de texto.
         *
         * @return El correo electrónico ingresado por el usuario como una cadena de texto.
         */
        fun introducirEmail(): String {
            var email: String?
            val patronEmail = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})\$".toRegex()

            do {
                var valido = true
                print("Introduce un email: ")
                email = readlnOrNull()
                if (email == null || !patronEmail.matches(email)) {
                    println("!ERROR!")
                    valido = false
                }
            } while (!valido)

            return email!!
        }

        /**
         * Solicita al usuario que introduzca sus apellidos y devuelve los apellidos como una cadena de texto.
         *
         * @return Los apellidos ingresados por el usuario como una cadena de texto.
         */
        fun introducirApellidos(): String {
            var apellido: String?

            do {
                var valido = true
                print("Introduce tus apellidos: ")
                apellido = readlnOrNull()
                if (apellido.isNullOrBlank()) {
                    println("!ERROR!")
                    valido = false
                }
            } while (!valido)

            return apellido!!
        }

        /**
         * Solicita al usuario que introduzca su edad y devuelve la edad como un número entero.
         *
         * @return La edad ingresada por el usuario como un número entero.
         */
        fun introducirEdad(): Int {
            var edad: Int?

            do {
                var valido = true
                print("Introduce tu edad: ")
                edad = readln().toIntOrNull()
                if (edad == null || edad !in 12..150) {
                    println("!ERROR!")
                    valido = false
                }
            } while (!valido)

            return edad!!
        }

        /**
         * Solicita al usuario que introduzca una contraseña de mínimo 5 cararacteres y devuelve la contraseña como una cadena de texto.
         *
         * @return La contraseña ingresada por el usuario como una cadena de texto.
         */
        fun introducirContrasenia(): String {
            var contrasenia: String?

            do {
                var valido = true
                print("Contraseña [5 dígitos mínimos]: ")
                contrasenia = readlnOrNull()
                if (contrasenia.isNullOrBlank() || contrasenia.length < 5) {
                    println("!ERROR!")
                    valido = false
                }
            } while (!valido)

            return contrasenia!!
        }

        fun introducirRol(): Roles {
            var rol = Roles.ESTANDAR
            println("Introduce un Rol para asignar")
            println("@=============================@")
            println("|     [1]  Administrador      |")
            println("|       [2]  Estandar         |")
            println("@=============================@")

            when (InputsMenus.seleccionarOpcionMenu(2)) {
                1 -> rol = Roles.ADMINISTRADOR
            }
            return rol
        }
    }
}
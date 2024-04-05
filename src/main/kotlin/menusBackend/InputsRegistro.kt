package menusBackend

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
                print("Introduce tu nombre: ")
                nombre = readlnOrNull()
                if (nombre == null) {
                    println("!ERROR!")
                }
            } while (nombre == null)

            return nombre
        }

        /**
         * Solicita al usuario que introduzca un correo electrónico y devuelve el correo electrónico como una cadena de texto.
         *
         * @return El correo electrónico ingresado por el usuario como una cadena de texto.
         */
        fun introducirEmail(): String {
            var email: String?
            val patronEmail = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$\n".toRegex()

            do {
                print("Introduce un email: ")
                email = readlnOrNull()
                if (email == null || email.matches(patronEmail)) {
                    println("!ERROR!")
                }
            } while (email == null)

            return email
        }

        /**
         * Solicita al usuario que introduzca sus apellidos y devuelve los apellidos como una cadena de texto.
         *
         * @return Los apellidos ingresados por el usuario como una cadena de texto.
         */
        fun introducirApellidos(): String {
            var apellido: String?

            do {
                print("Introduce tus apellidos: ")
                apellido = readlnOrNull()
                if (apellido == null) {
                    println("!ERROR!")
                }
            } while (apellido == null)

            return apellido
        }

        /**
         * Solicita al usuario que introduzca su edad y devuelve la edad como un número entero.
         *
         * @return La edad ingresada por el usuario como un número entero.
         */
        fun introducirEdad(): Int {
            var edad: Int?

            do {
                print("Introduce tu edad: ")
                edad = readln().toIntOrNull()
                if (edad == null || edad < 0 || edad > 150) {
                    println("!ERROR!")
                }
            } while (edad == null || edad < 0 || edad > 150)

            return edad
        }

        /**
         * Solicita al usuario que introduzca una contraseña de mínimo 5 cararacteres y devuelve la contraseña como una cadena de texto.
         *
         * @return La contraseña ingresada por el usuario como una cadena de texto.
         */
        fun introducirContrasenia(): String {
            var contrasenia: String?

            do {
                print("Contraseña [5 dígitos mínimos]: ")
                contrasenia = readlnOrNull()
                if (contrasenia == null || contrasenia.length < 5) {
                    println("!ERROR!")
                }
            } while (contrasenia == null || contrasenia.length < 5)

            return contrasenia
        }
    }
}
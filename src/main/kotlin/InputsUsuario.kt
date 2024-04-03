import usuario.Usuario

class InputsUsuario {
    companion object {
        // SELECCION MENU
        fun seleccionarOpcionMenu(): Int {
            var opt: Int?

            print("Introduce una opción [1-3]: ")
            do {
                opt = readln().toInt()
            } while (opt == null || opt < 1 || opt > 3)

            return opt
        }

        fun salirSistema(): Boolean {
            return true
        }

        //REGISTRO
        fun introducirNombre(): String {
            var nombre: String?

            do {
                nombre = readlnOrNull()
            } while (nombre == null)

            return nombre
        }

        fun introducirEmail(): String {
            var email: String?

            do {
                email = readlnOrNull()
            } while (email == null)

            return email
        }

        fun introducirApellidos(): String {
            var apellido: String?

            do {
                apellido = readlnOrNull()
            } while (apellido == null)

            return apellido
        }

        fun introducirEdad(): Int {
            var edad: Int?

            do {
                edad = readln().toIntOrNull()
            } while (edad == null || edad < 0 || edad > 150)

            return edad
        }

        fun introducirClave(): String {
            var contrasenia: String?

            do {
                contrasenia = readlnOrNull()
            } while (contrasenia == null)

            return contrasenia
        }

        // LOGIN
        fun esCorrectoEmail(usuario: Usuario, email: String) {
            do {
                print("Por favor, introduce el usuario correctamente: ")
            } while (usuario.email != email)
        }

        fun esCorrectoEmail(usuario: Usuario): Boolean {
            var email: String?
            var correcto = false

            do {
                print("Introducir @email: ")
                email = readlnOrNull()
                if (email != usuario.email) {
                    print("Por favor, introduce el email correctamente: ")
                } else {
                    correcto = true
                }
            } while (!correcto)

            return correcto
        }

        fun esCorrectaContrasenia(usuario: Usuario): Boolean {
            var contrasenia: String?
            var correcto = false

            do {
                print("Introducir contraseña: ")
                contrasenia = readlnOrNull()
                if (contrasenia != usuario.contrasenia) {
                    print("Por favor, introduce la contraseña correctamente: ")
                } else {
                    correcto = true
                }
            } while (!correcto)

            return correcto
        }

        // ADMINISTRADOR
        fun seleccionMenuAdministrador(): Int {
            var opt: Int?

            print("Introduce una opción [1-7]: ")
            do {
                opt = readln().toInt()
            } while (opt == null || opt < 1 || opt > 7)

            return opt
        }
    }
}












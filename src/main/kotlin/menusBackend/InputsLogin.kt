package menusBackend

import usuario.Usuario

class InputsLogin {
    /**
     * Solicita al usuario que ingrese un correo electrónico y lo devuelve una vez que se ha ingresado.
     *
     * @return El correo electrónico ingresado por el usuario.
     */
    fun ingresoEmail(): String {
        var emailInput: String?

        do {
            print("Ingrese el email: ")
            emailInput = readlnOrNull()
        } while (emailInput == null)

        return emailInput
    }

    /**
     * Permite al usuario ingresar una contraseña y verifica si coincide con la contraseña del usuario proporcionado.
     *
     * @param usuario El objeto Usuario que contiene la contraseña.
     */
    fun ingresoContrasenia(usuario: Usuario) {
        var contrasenia: String?

        do {
            print("Introducir contraseña: ")
            contrasenia = readlnOrNull()
            if (contrasenia != usuario.contrasenia) {
                print("Por favor, introduce la contraseña correctamente: ")
            }
        } while (contrasenia != usuario.contrasenia)
    }
}

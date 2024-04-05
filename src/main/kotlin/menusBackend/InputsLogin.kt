package menusBackend

import usuario.Usuario

class InputsLogin {
    /**
     * Permite al usuario ingresar un correo electrónico y verifica si coincide con el correo electrónico del usuario proporcionado.
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

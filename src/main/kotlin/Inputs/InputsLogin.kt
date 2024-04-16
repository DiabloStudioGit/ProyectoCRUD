package Inputs

import UI.MenuColores
import Data.Usuario.Usuario

class InputsLogin {
    /**
     * Solicita al usuario que ingrese un correo electrónico y lo devuelve una vez que se ha ingresado.
     *
     * @return El correo electrónico ingresado por el usuario.
     */
    fun ingresoEmail(): String {
        var emailInput: String?

        do {
            print("Ingrese el " + MenuColores.random("email") + ": ")
            emailInput = readlnOrNull()
        } while (emailInput == null)

        return emailInput
    }

    /**
     * Permite al usuario ingresar una contraseña y verifica si coincide con la contraseña del usuario proporcionado.
     *
     * @param usuario El objeto Usuario que contiene la contraseña.
     * @return Un valor booleano si la contraseña introducida coincide con la del usuario.
     */
    fun ingresoContrasenia(usuario: Usuario): Boolean {
        var contrasenia: String?
        var esCorrecta = false

        do {
            print("Introducir " + MenuColores.random("contraseña") + ": ")
            contrasenia = readlnOrNull()
            if (contrasenia == usuario.contrasenia) {
                esCorrecta = true
            } else {
                println(MenuColores.error() + " Contraseña incorrecta.")
            }
        } while (contrasenia != usuario.contrasenia)

        return esCorrecta
    }
}

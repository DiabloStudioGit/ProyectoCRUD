package Usuario

import java.io.Serializable

data class Usuario(var nombre : String, var apellido : String, var edad : Int, var email : String, var contrasenia : String, var rol : Roles) : Serializable {

    override fun toString(): String {
        return "Usuario:   \n  Nombre: $nombre \n  Apellido: $apellido \n  Edad: $edad \n  Email: $email \n  Contraseña: $contrasenia \n  Tipo: $rol \n"
    }
}

package Data.Usuario

import UI.MenuColores
import java.io.Serializable

data class Usuario(var nombre : String, var apellido : String, var edad : Int, var email : String, var contrasenia : String, var rol : Roles) : Serializable {

    fun copy() : Usuario {
        return Usuario(this.nombre, this.apellido, this.edad, this.email, this.contrasenia, this.rol)
    }

    override fun toString(): String {
        return "Usuario:   \n  ${MenuColores.verde("Nombre:")} $nombre \n  ${MenuColores.amarillo("Apellido:")} $apellido \n  ${MenuColores.azul("Edad:")} $edad \n  ${MenuColores.magenta("Email:")} $email \n  ${MenuColores.rojo("Tipo:")} $rol \n"
    }
}

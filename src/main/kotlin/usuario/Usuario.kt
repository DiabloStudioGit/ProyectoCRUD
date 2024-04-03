package usuario

import java.io.Serializable

data class Usuario(val nombre : String, val apellido : String, val edad : Int, val email : String, val contrasenia : String, val rol : Roles) : Serializable

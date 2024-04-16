package Gestion

import Data.Usuario.Roles
import Data.Usuario.Usuario

interface IGestorUsuarios {

    fun añadirUsuario(usuario : Usuario)
    fun borrarUsuario(usuario : Usuario)
    fun obtenerUsuario(email : String) : Usuario?
    fun modificarPermisos(usuario : Usuario, rol : Roles)
    fun modificarUsuario(usuarioOriginal : Usuario, datosNuevos : Usuario)
    fun mostrarUsuarios()
    fun obtenerUsuarios() : ArrayList<Usuario>
}
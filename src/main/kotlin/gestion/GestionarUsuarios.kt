package gestion

import usuario.Roles
import usuario.Usuario
import java.io.EOFException
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class GestionarUsuarios {
    private val FICHERO_USUARIOS = "usuarios.dat"
    private var usuarios = arrayListOf<Usuario>()

    constructor() {
        this.usuarios = obtenerUsuarios()
    }

    fun crearUsuario(usuario : Usuario) {
        val fileStream = FileOutputStream(FICHERO_USUARIOS, true)
        val objectStream = ObjectOutputStream(fileStream)
        objectStream.writeObject(usuario)
        objectStream.close()
        println("Usuario \"${usuario.nombre}\" creado correctamente.")
    }

    fun borrarUsuario(usuarioBorrar : Usuario) {
        val usuariosActuales = arrayListOf<Usuario>()
        var borrado = false

        val usuarios = obtenerUsuarios()
        for (usuario in usuarios) {
            if (usuario == usuarioBorrar) {
                borrado = true
            }else {
                usuariosActuales.add(usuario)
            }
        }

        if (borrado) {
            println("Usuario borrado correctamente.")
            guardarUsuarios(usuariosActuales)
        }else {
            println("No se ha encontrado al usuario.")
        }
    }

    fun obtenerUsuario(email : String) : Usuario? {
        var usuarioBuscado : Usuario? = null

        val usuarios = obtenerUsuarios()
        for (usuario in usuarios) {
            if (usuario.email.equals(email, true)) {
                usuarioBuscado = usuario
            }else {
                println("No se ha encontrado el usuario.")
            }
        }

        return usuarioBuscado
    }

    fun obtenerUsuarios() : ArrayList<Usuario> {
        val usuarios = arrayListOf<Usuario>()
        val fichero = File(FICHERO_USUARIOS)
        if (fichero.exists()) {
            var objectInputStream : ObjectInputStream? = null
            try {
                val fileInputStream = FileInputStream(FICHERO_USUARIOS)
                objectInputStream = ObjectInputStream(fileInputStream)
                while (true) {
                    val usuario = objectInputStream.readObject() as Usuario
                    usuarios.add(usuario)
                }
            }catch (_: EOFException) {
            } finally {
                objectInputStream?.close()
            }
        }
        return usuarios
    }

    fun modificarPermisos(usuario : Usuario, rol : Roles) {

    }

    private fun guardarUsuarios(usuarios : List<Usuario>) {
        val fileStream = FileOutputStream(FICHERO_USUARIOS)
        val objectStream = ObjectOutputStream(fileStream)
        for (usuario in usuarios) {
            objectStream.writeObject(usuario)
        }
        objectStream.close()
    }
}
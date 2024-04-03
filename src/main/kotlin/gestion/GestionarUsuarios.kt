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
        this.usuarios.add(usuario)
        this.guardarUsuarios()
        println("Usuario \"${usuario.nombre}\" creado correctamente.")
    }

    fun borrarUsuario(usuario : Usuario) {
        if (this.usuarios.contains(usuario)) {
            this.usuarios.remove(usuario)
            this.guardarUsuarios()
            println("Usuario borrado correctamente.")
        }else {
            println("No se ha podido encontrar el usuario.")
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

    private fun guardarUsuarios() {
        val fileStream = FileOutputStream(FICHERO_USUARIOS)
        val objectStream = ObjectOutputStream(fileStream)
        for (usuario in this.usuarios) {
            objectStream.writeObject(usuario)
        }
        objectStream.close()
    }
}
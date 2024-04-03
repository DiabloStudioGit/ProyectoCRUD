package gestion

import usuario.Usuario
import java.io.EOFException
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class GestionarUsuarios {
    private val FICHERO_USUARIOS = "usuarios.dat"

    fun crearUsuario(usuario : Usuario) {
        val fileStream = FileOutputStream(FICHERO_USUARIOS, true)
        val objectStream = ObjectOutputStream(fileStream)
        objectStream.writeObject(usuario)
        objectStream.close()
        println("Usuario \"${usuario.nombre}\" creado correctamente.")
    }

    fun borrarUsuario(usuario : Usuario) {

    }

    fun obtenerUsuarios() : List<Usuario> {
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
            }catch (ex : EOFException) {

            } finally {
                objectInputStream?.close()
            }
        }
        return usuarios
    }
}
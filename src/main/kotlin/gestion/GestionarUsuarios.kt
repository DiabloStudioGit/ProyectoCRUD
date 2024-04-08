package gestion

import juego.Historial
import usuario.Roles
import usuario.Usuario
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.StreamCorruptedException

class GestionarUsuarios {
    private val FICHERO_USUARIOS = "usuarios.dat"
    private var usuarios : ArrayList<Usuario>

    /**
     * Constructor para inicializar los usuarios.
     */
    constructor() {
        this.usuarios = this.obtenerUsuarios()
    }

    /**
     * Añade al usuario al listado de usuarios tanto instanciado como al fichero.
     *
     * @param usuario Usuario a añadir
     */
    fun añadirUsuario(usuario : Usuario) {
        var gestionarHistorial = GestionarHistoriales()
        var historial = Historial(usuario.email, 0, 0, 0)
        this.usuarios.add(usuario)
        this.guardarUsuarios()
        gestionarHistorial.añadirHistorial(historial)
        println("Usuario \"${usuario.nombre}\" creado correctamente.")
    }

    /**
     * Borra el usuario de la lista de usuarios y actualiza el fichero.
     *
     * @param usuario Usuario a eliminar.
     */
    fun borrarUsuario(usuario : Usuario) {
        if (this.usuarios.contains(usuario)) {
            this.usuarios.remove(usuario)
            this.guardarUsuarios()
            println("Usuario borrado correctamente.")
        }else {
            println("No se ha podido encontrar el usuario.")
        }
    }

    /**
     * Devuelve el usuario con el email en cuestion.
     *
     * @param email Email a buscar entre los usuarios.
     * @return El usuario en caso de encontrarlo o null en caso opuesto.
     */
    fun obtenerUsuario(email : String) : Usuario? {
        var usuarioBuscado : Usuario? = null

        for (usuario in this.usuarios) {
            if (usuario.email.equals(email, true)) {
                usuarioBuscado = usuario
            }
        }

        if (usuarioBuscado == null) {
            println("No se ha encontrado el usuario.")
        }

        return usuarioBuscado
    }

    /**
     * Añade al usuario al listado de usuarios tanto instanciado como al fichero.
     *
     * @param usuario Usuario a añadir
     */
    fun modificarPermisos(usuario : Usuario, rol : Roles) {
        var exito = false
        for (i in this.usuarios.indices) {
            if (this.usuarios[i] == usuario) {
                this.usuarios[i].rol = rol
                exito = true
            }
        }

        if (exito) {
            this.guardarUsuarios()
            println("Se ha modificado el permiso del usuario ${usuario.nombre} correctamente.")
        }else {
            println("No se ha encontrado el usuario ${usuario.nombre}.")
        }
    }

    /**
     * Modifica un usuario completo a raiz de una plantilla (Otro usuario falso).
     *
     * @param usuarioOriginal Usuario a modificar.
     * @param datosNuevos Plantilla de usuario con los datos nuevos.
     */
    fun modificarUsuario(usuarioOriginal : Usuario, datosNuevos : Usuario) {
        var exito = false
        for (i in this.usuarios.indices) {
            if (this.usuarios[i] == usuarioOriginal) {
                this.usuarios[i] = datosNuevos
                exito = true
            }
        }

        if (exito) {
            this.guardarUsuarios()
            println("Se ha modificado el usuario ${usuarioOriginal.nombre} correctamente.")
        }else {
            println("No se ha encontrado el usuario ${usuarioOriginal.nombre}.")
        }
    }

    /**
     * Imprime en pantalla la informacion de todos los usuarios.
     */
    fun mostrarUsuarios() {
        if (this.usuarios.isNotEmpty()) {
            var index = 0
            for (usuario in this.usuarios) {
                println("${++index} $usuario")
            }
        }else {
            println("No hay usuarios para mostrar.")
        }
    }

    /**
     * Obtiene todos los usuarios que se encuentren en el fichero.
     *
     * @return Devuelve la lista de usuarios.
     */
    private fun obtenerUsuarios() : ArrayList<Usuario> {
        var usuariosExistentes = arrayListOf<Usuario>()
        val fichero = File(FICHERO_USUARIOS)
        if (fichero.exists()) {
            var objectInputStream : ObjectInputStream? = null
            try {
                val fileInputStream = FileInputStream(FICHERO_USUARIOS)
                objectInputStream = ObjectInputStream(fileInputStream)
                usuariosExistentes = objectInputStream.readObject() as ArrayList<Usuario>
            }catch (exception : StreamCorruptedException) {
                println("Los datos no se han podido obtener.")
            } finally {
                objectInputStream?.close()
            }
        }
        return usuariosExistentes
    }

    /**
     * Guarda/Actualiza los usuarios que se encuentren actualmente en la memoria del programa.
     */
    private fun guardarUsuarios() {
        val fileStream = FileOutputStream(FICHERO_USUARIOS)
        val objectStream = ObjectOutputStream(fileStream)
        objectStream.writeObject(usuarios)
        objectStream.close()
    }
}
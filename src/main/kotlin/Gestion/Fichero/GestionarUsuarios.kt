package Gestion.Fichero

import Gestion.Gestores
import Gestion.IGestorUsuarios
import Gestion.Log
import UI.MenuColores
import Data.Usuario.Roles
import Data.Usuario.Usuario
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.StreamCorruptedException

class GestionarUsuarios : IGestorUsuarios {
    private val FICHERO_USUARIOS = "usuarios.dat"
    private var usuarios : ArrayList<Usuario>
    private var gestorLogs = GestionarLogs()

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
    override fun añadirUsuario(usuario : Usuario) {
        this.usuarios.add(usuario)
        this.guardarUsuarios()
        println(MenuColores.ok() + " Usuario \"${usuario.nombre}\" creado correctamente.")
        val logRegistro = Log(usuario.email, Gestores.fechaActual(), "Usuario creado")
        gestorLogs.añadirLog(logRegistro)
    }

    /**
     * Borra el usuario de la lista de usuarios y actualiza el fichero.
     *
     * @param usuario Usuario a eliminar.
     */
    override fun borrarUsuario(usuario : Usuario) {
        if (this.usuarios.contains(usuario)) {
            this.usuarios.remove(usuario)
            this.guardarUsuarios()
            println(MenuColores.ok() + " Usuario " + MenuColores.rojo("borrado") + " correctamente.")

            val logEliminar = Log(usuario.email, Gestores.fechaActual(), "Usuario eliminado")
            gestorLogs.añadirLog(logEliminar)

            val gestionarHistorial = GestionarHistoriales()
            val historial = gestionarHistorial.obtenerHistorial(usuario, false)
            if (historial != null) {
                gestionarHistorial.borrarHistorial(historial)
            }
        }else {
            println(MenuColores.error() + " No se ha podido encontrar el usuario.")
        }
    }

    /**
     * Devuelve el usuario con el email en cuestion.
     *
     * @param email Email a buscar entre los usuarios.
     * @return El usuario en caso de encontrarlo o null en caso opuesto.
     */
    override fun obtenerUsuario(email : String) : Usuario? {
        var usuarioBuscado : Usuario? = null

        for (usuario in this.usuarios) {
            if (usuario.email.equals(email, true)) {
                usuarioBuscado = usuario
            }
        }

        if (usuarioBuscado == null) {
            println(MenuColores.error() + " No se ha encontrado el usuario .")
        }

        return usuarioBuscado
    }

    /**
     * Añade al usuario al listado de usuarios tanto instanciado como al fichero.
     *
     * @param usuario Usuario a añadir
     */
    override fun modificarPermisos(usuario : Usuario, rol : Roles) {
        var exito = false
        for (i in this.usuarios.indices) {
            if (this.usuarios[i] == usuario) {
                this.usuarios[i].rol = rol
                exito = true
            }
        }

        if (exito) {
            this.guardarUsuarios()
            println(MenuColores.ok() + " Se ha modificado el permiso del usuario ${usuario.nombre} correctamente.")
            val logPermiso = Log(usuario.email, Gestores.fechaActual(), "Permisos del usuario modificados.")
            gestorLogs.añadirLog(logPermiso)
        }else {
            println(MenuColores.error() + " No se ha encontrado el usuario ${usuario.nombre}.")
        }
    }

    /**
     * Modifica un usuario completo a raiz de una plantilla (Otro usuario falso).
     *
     * @param usuarioOriginal Usuario a modificar.
     * @param datosNuevos Plantilla de usuario con los datos nuevos.
     */
    override fun modificarUsuario(usuarioOriginal : Usuario, datosNuevos : Usuario) {
        var exito = false
        for (i in this.usuarios.indices) {
            if (this.usuarios[i] == usuarioOriginal) {
                this.usuarios[i] = datosNuevos
                exito = true
            }
        }

        if (exito) {
            this.guardarUsuarios()
            if (usuarioOriginal.email != datosNuevos.email) {
                gestorLogs.modificarLog(usuarioOriginal.email, datosNuevos.email)
            }
            println(MenuColores.ok() + " Se ha modificado el usuario " + MenuColores.magenta(datosNuevos.nombre) + " correctamente.")

            val logModificacion = Log(datosNuevos.email, Gestores.fechaActual(), " Datos del usuario modificados.")
            gestorLogs.añadirLog(logModificacion)
        }else {
            println(MenuColores.error() + " No se ha encontrado el usuario ${usuarioOriginal.nombre}.")
        }
    }

    /**
     * Imprime en pantalla la informacion de todos los usuarios.
     */
    override fun mostrarUsuarios() {
        if (this.usuarios.isNotEmpty()) {
            var index = 0
            for (usuario in this.usuarios) {
                println("${++index} $usuario")
            }
        }else {
            println(MenuColores.info() + " No hay usuarios para mostrar.")
        }
    }

    /**
     * Obtiene todos los usuarios que se encuentren en el fichero.
     *
     * @return Devuelve la lista de usuarios.
     */
    override fun obtenerUsuarios() : ArrayList<Usuario> {
        var usuariosExistentes = arrayListOf<Usuario>()


        val fichero = File(FICHERO_USUARIOS)
        if (fichero.exists()) {
            var objectInputStream : ObjectInputStream? = null
            try {
                val fileInputStream = FileInputStream(FICHERO_USUARIOS)
                objectInputStream = ObjectInputStream(fileInputStream)
                usuariosExistentes = objectInputStream.readObject() as ArrayList<Usuario>
            }catch (exception : StreamCorruptedException) {
                println(MenuColores.error() + " Los datos no se han podido obtener.")
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
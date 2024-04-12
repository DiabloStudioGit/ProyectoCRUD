import Gestion.BaseDeDatos.GestionarBaseDatos
import UI.MenuLogin
import Gestion.Fichero.GestionarUsuarios
import Gestion.Gestor
import Gestion.IGestorUsuarios
import Inputs.InputsMenus
import Inputs.InputsRegistro
import UI.MenuAdmin
import UI.MenuColores
import UI.MenuJuego
import Usuario.Roles
import Usuario.Usuario
import java.sql.SQLException

fun main() {
    var gestionarUsuarios : IGestorUsuarios
    //Imprimir eleccion entre bbdd o fichero

    MenuLogin.bienvenida()
    MenuLogin.menuGestor()
    val gestor = InputsMenus.seleccionarOpcionMenu(2)
    when (gestor) {
        1 -> {
            println("Se va a proceder con el " + MenuColores.azul("sistema de ficheros."))
            gestionarUsuarios = GestionarUsuarios()
        }
        else -> {
            try{
                gestionarUsuarios = GestionarBaseDatos()
                println("Se va a proceder con el sistema de " + MenuColores.azul("Base de Datos."))
                Gestor.eleccion = true
            }catch (ex : SQLException) {
                println(MenuColores.error() + " No se ha podido conectar con la base de datos.")
                println(MenuColores.info() + " Se va a proceder con el " + MenuColores.azul("sistema de ficheros."))
                gestionarUsuarios = GestionarUsuarios()
            }
        }
    }

    //Añade un administrador para Debug
    if (gestionarUsuarios.obtenerUsuario("admin@test.es") == null) {
        gestionarUsuarios.añadirUsuario(Usuario("Admin", "Root", 0, "admin@test.es", "12345", Roles.ADMINISTRADOR))
    }

    val menuLogin = MenuLogin(gestionarUsuarios)

    do {
        menuLogin.imprimirOpciones()
        val opcion = InputsMenus.seleccionarOpcionMenu(3)
        when (opcion) {
            1 -> {
                val usuario = menuLogin.iniciarSesion()
                if (usuario != null){
                    val menuJuego = MenuJuego(usuario)

                    if (usuario.rol == Roles.ADMINISTRADOR) {
                        //Si el usuario es Administrador, pide a donde ir
                        when (menuLogin.pedirAdmin(usuario.email)) {
                            1 -> {
                                menuJuego.juego()
                            }
                            2 -> {
                                val menuAdmin = MenuAdmin(gestionarUsuarios)
                                menuAdmin.menuAdmin()
                            }
                        }
                    } else if (usuario.rol == Roles.ADMIN_NoJuego) {
                        //Si el usuario es Administrador NO ESTANDAR, va al menuAdmin
                        val menuAdmin = MenuAdmin(gestionarUsuarios)
                        menuAdmin.menuAdmin()

                    } else {
                        menuJuego.juego()
                    }
                }
            }
            2 -> {
                gestionarUsuarios.añadirUsuario(Usuario(InputsRegistro.introducirNombre(), InputsRegistro.introducirApellidos(), InputsRegistro.introducirEdad(), InputsRegistro.introducirEmail(), InputsRegistro.introducirContrasenia(), Roles.ESTANDAR))
            }
        }

    } while (opcion != 3)
}

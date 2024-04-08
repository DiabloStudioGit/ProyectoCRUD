import UI.MenuLogin
import Gestion.GestionarHistoriales
import Gestion.GestionarUsuarios
import Juego.LogicaJuego
import Inputs.InputsJuego
import Inputs.InputsMenus
import Inputs.InputsRegistro
import UI.MenuAdmin
import Usuario.Roles
import Usuario.Usuario

fun main() {
    val gestionarUsuarios = GestionarUsuarios()

    //Añade un administrador para Debug
    if (gestionarUsuarios.obtenerUsuario("admin@test.es") == null) {
        gestionarUsuarios.añadirUsuario(Usuario("Admin", "Root", 0, "admin@test.es", "12345", Roles.ADMINISTRADOR))
    }

    var menuLogin = MenuLogin()
    do {
        menuLogin.imprimirOpciones()
        val opcion = InputsMenus.seleccionarOpcionMenu(3)
        when (opcion) {
            1 -> {
                var usuario = menuLogin.iniciarSesion()
                if (usuario != null){
                    if (usuario.rol == Roles.ADMINISTRADOR) {
                        //Si el usuario es Administrador, pide a donde ir
                        when (menuLogin.pedirAdmin()) {
                            1 -> {
                                while (juego(usuario)){}
                            }
                            2 -> {
                                //menuAdmin()
                            }
                        }
                    } else {
                        //Si el usuario es Estandar, va al juego
                        while (juego(usuario)){}
                    }
                }
            }
            2 -> {
                gestionarUsuarios.añadirUsuario(Usuario(InputsRegistro.introducirNombre(), InputsRegistro.introducirApellidos(), InputsRegistro.introducirEdad(), InputsRegistro.introducirEmail(), InputsRegistro.introducirContrasenia(), Roles.ESTANDAR))
            }
        }

    } while (opcion != 3)
}

fun juego(usuario: Usuario): Boolean{
    var eleccion = true
    val juego = LogicaJuego()
    var historial = GestionarHistoriales()
    var historialUsuario = historial.obtenerHistorial(usuario.email)

    if (historialUsuario == null) {
        println("[ERROR] Historial no encontrado")
    } else {
        when (MenuLogin.menuJuego(usuario)) {
            1 -> historial.modificarHistorial(historialUsuario, juego.hasAcertado(historialUsuario, InputsJuego.introducirNumero()), false)
            2 -> {
                println(historialUsuario)
                println("Porcentaje de Victorias: ${juego.calculoPorcentajeVictorias(historialUsuario)}%")
            }
            3 -> eleccion = false
        }
    }
    return eleccion
}
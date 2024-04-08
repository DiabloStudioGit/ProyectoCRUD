import UI.MenuLogin
import gestion.GestionarHistoriales
import gestion.GestionarUsuarios
import gestion.MenuAdmin
import juego.LogicaJuego
import menusBackend.InputsJuego
import menusBackend.InputsRegistro
import usuario.Roles
import usuario.Usuario

fun main() {
    val gestionarUsuarios = GestionarUsuarios()

    var menuLogin = MenuLogin()
    do {
        menuLogin.imprimirOpciones()
        val opcion = menuLogin.pedirAccion()
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
                                menuAdmin()
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
            1 -> {
                juego.hasAcaertado(historialUsuario, InputsJuego.introducirNumero())
            }
            2 -> {
                //Vuelve a obtener el historial para resultados actualizados
                historialUsuario = historial.obtenerHistorial(usuario.email)
                println(historialUsuario)
            }
            3 -> {
                eleccion = false
            }
        }
    }
    return eleccion
}

fun menuAdmin(){
    val menu = MenuAdmin()
    println("Administrando")


}
package UI

import Gestion.BaseDeDatos.GestionarBaseDatos
import Gestion.Fichero.GestionarHistoriales
import Gestion.Fichero.GestionarLogs
import Gestion.Gestor
import Gestion.IGestorHistoriales
import Gestion.IGestorLogs
import Gestion.Log
import Inputs.InputsJuego
import Inputs.InputsMenus
import Juego.Historial
import Juego.LogicaJuego
import Usuario.Usuario

class MenuJuego {
    var gestorLogs : IGestorLogs
    var gestor : IGestorHistoriales
    val usuario : Usuario
    constructor(usuario : Usuario) {
        if (!Gestor.eleccion) {
            gestor = GestionarHistoriales()
            gestorLogs = GestionarLogs()

        } else {
            gestor = GestionarBaseDatos()
            gestorLogs = GestionarBaseDatos()
        }
        this.usuario = usuario
    }
    fun juego() {
        var eleccion = true
        val juego = LogicaJuego()
        var historial = gestor.obtenerHistorial(usuario, true)

        if (historial == null) {
            println(MenuColores.info() + " El usuario posiblemente no haya jugado todavia.")
            println(MenuColores.info() + " Creando historial para el usuario...")
            gestor.añadirHistorial(Historial(usuario.email, 0, 0, 0))
            historial = gestor.obtenerHistorial(usuario, true)
            if (historial != null) {
                while (eleccion) {
                    when (menuJuego()) {
                        1 -> gestor.modificarHistorial(historial, juego.hasAcertado(historial, InputsJuego.introducirNumero()), false)
                        2 -> {
                            println(historial)
                            println("Porcentaje de Victorias: " + MenuColores.magenta("${juego.calculoPorcentajeVictorias(historial)}%"))
                        }
                        3 -> eleccion = false
                    }
                }
            }
        } else {
            while (eleccion) {
                when (menuJuego()) {
                    1 -> gestor.modificarHistorial(historial, juego.hasAcertado(historial, InputsJuego.introducirNumero()), false)
                    2 -> {
                        println(historial)
                        println("Porcentaje de Victorias: " + MenuColores.magenta("${juego.calculoPorcentajeVictorias(historial)}%"))
                    }
                    3 -> {
                        val logCerrarSesion = Log(usuario.email, Gestor.fechaActual(), "Sesion de usuario cerrada.")
                        gestorLogs.añadirLog(logCerrarSesion)
                        eleccion = false
                    }
                }
            }
        }
    }

    fun menuJuego(): Int {
        println("Hola! ${MenuColores.random("[${usuario.nombre}]")}")
        println(MenuColores.verde("@======") + "¿Que Desea Hacer?" + MenuColores.verde("======@"))
        println(MenuColores.verde("|                             |"))
        println(MenuColores.verde("|        ") + MenuColores.azul("[1]")+"  Jugar" + MenuColores.verde("           |"))
        println(MenuColores.verde("|      ") + MenuColores.azul("[2]") + "  Puntuacion" + MenuColores.verde("        |"))
        println(MenuColores.verde("|    ") + MenuColores.azul("[3]") + "  Cerrar Sesion" + MenuColores.verde("       |"))
        println(MenuColores.verde("|                             |"))
        println(MenuColores.verde("@=============================@"))

        return InputsMenus.seleccionarOpcionMenu(3)
    }
}
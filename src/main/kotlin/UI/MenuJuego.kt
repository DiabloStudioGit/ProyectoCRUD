package UI

import Gestion.BaseDeDatos.GestionarBaseDatos
import Gestion.Fichero.GestionarHistoriales
import Gestion.Gestor
import Gestion.IGestorHistoriales
import Inputs.InputsJuego
import Inputs.InputsMenus
import Juego.Historial
import Juego.LogicaJuego
import Usuario.Usuario

class MenuJuego {
    var gestor : IGestorHistoriales
    val usuario : Usuario
    val historial : Historial?
    constructor(usuario : Usuario) {
        if (!Gestor.eleccion) {
            gestor = GestionarHistoriales()
        } else {
            gestor = GestionarBaseDatos()
        }
        this.usuario = usuario
        this.historial = gestor.obtenerHistorial(usuario.email)
    }
    fun juego() {
        var eleccion = true
        val juego = LogicaJuego()

        while (eleccion) {
            if (historial == null) {
                println(MenuColores.error() + " Historial no encontrado")
                println(MenuColores.info() + " El usuario posiblemente no haya jugado todavia")
                val historialNuevo = Historial(usuario.email, 0, 0, 0)
                gestor.añadirHistorial(historialNuevo)
                println(MenuColores.ok() + " Historial creado correctamente")
                eleccion = false
            } else {
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
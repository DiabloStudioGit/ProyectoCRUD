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
                eleccion = false
            } else {
                when (menuJuego()) {
                    1 -> gestor.modificarHistorial(historial, juego.hasAcertado(historial, InputsJuego.introducirNumero()), false)
                    2 -> {
                        println(historial)
                        println("Porcentaje de Victorias: ${juego.calculoPorcentajeVictorias(historial)}%")
                    }
                    3 -> eleccion = false
                }
            }
        }
    }

    fun menuJuego(): Int {
        println("Hola! ${MenuColores.random("[${usuario.nombre}]")}")
        println(MenuColores.set("@======", MenuColores.verde) + "¿Que Desea Hacer?" + MenuColores.set("======@", MenuColores.verde))
        println(MenuColores.set("|                             |", MenuColores.verde))
        println(MenuColores.set("|        ", MenuColores.verde) + MenuColores.set("[1]", MenuColores.azul)+"  Jugar" + MenuColores.set("           |", MenuColores.verde))
        println(MenuColores.set("|      ", MenuColores.verde) + MenuColores.set("[2]", MenuColores.azul) + "  Puntuacion" + MenuColores.set("        |", MenuColores.verde))
        println(MenuColores.set("|    ", MenuColores.verde) + MenuColores.set("[3]", MenuColores.azul) + "  Cerrar Sesion" + MenuColores.set("       |", MenuColores.verde))
        println(MenuColores.set("|                             |", MenuColores.verde))
        println(MenuColores.set("@=============================@", MenuColores.verde))

        return InputsMenus.seleccionarOpcionMenu(3)
    }
}
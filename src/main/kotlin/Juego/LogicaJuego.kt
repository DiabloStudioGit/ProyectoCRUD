package Juego

import Data.Juego.Historial
import UI.MenuColores
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale


class LogicaJuego {
    /**
     * Genera un número aleatorio entre 1 y 10, ambos inclusive.
     *
     * @return El número aleatorio generado.
     */
    private fun crearNumeroAleatorio(): Int {
        return (1..10).random()
    }

    /**
     * Verifica si un jugador ha adivinado un número generado aleatoriamente.
     *
     * @param historial Objeto de tipo Historial que almacena el registro de las partidas.
     * @param numeroJugador El número elegido por el jugador.
     */
    fun hasAcertado(historial: Historial, numeroJugador: Int): Historial {
        if (numeroJugador == crearNumeroAleatorio()) {
            println("!Has " + MenuColores.verde("acertado") + "!")
            historial.partidasGanadas++
            historial.partidasJugadas++
        } else {
            println("No has " + MenuColores.rojo("acertado") + " :(")
            historial.partidasJugadas++
        }
        return historial
    }

    /**
     * Calcula el porcentaje de partidas ganadas en función del historial proporcionado.
     *
     * @param historial Objeto de tipo Historial que contiene el registro de las partidas.
     * @return El porcentaje de partidas ganadas como un valor decimal.
     */
    fun calculoPorcentajeVictorias(historial: Historial): Double {
        var porcentaje : Double
        if(historial.partidasJugadas != 0) {
            porcentaje = (DecimalFormat("#.##", DecimalFormatSymbols(Locale.US)).format((historial.partidasGanadas.toDouble() / historial.partidasJugadas.toDouble()) * 100)).toDouble()
        } else {
            porcentaje = 0.0
        }
        return porcentaje
    }
}
package Juego


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
            println("!Has acertado!")
            historial.partidasGanadas++
            historial.partidasJugadas++
        } else {
            println("No has hacertado :(")
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
        return historial.partidasGanadas / historial.partidasJugadas.toDouble()
    }
}
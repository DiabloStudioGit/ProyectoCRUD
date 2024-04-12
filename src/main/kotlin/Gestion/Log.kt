package Gestion

import UI.MenuColores
import java.io.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class Log(var email : String, var fecha : String, var accion : String) : Serializable {

    override fun toString(): String {
        return "Log: \n ${MenuColores.azul("Usuario:")} $email \n ${MenuColores.magenta("Fecha:")} $fecha \n ${MenuColores.cian("Accion:")} $accion \n"
    }
}
package Gestion

interface IGestorLogs {

    fun añadirLog(log : Log)
    fun obtenerLog(email: String) : ArrayList<Log>?

    fun obtenerLogs() : ArrayList<Log>

    fun mostarLogs()
}
package Data

import java.nio.file.Files
import java.nio.file.Paths
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class Encriptacion {

    private val cipher : Cipher
    private val secretKeySpec : SecretKeySpec

    constructor(secretKey : String) {
        this.cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        this.secretKeySpec = SecretKeySpec(secretKey.toByteArray(), "AES")
    }

    fun encriptar(archivo : String, data : String) {
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec)
        val encryptedBytes = cipher.doFinal(data.toByteArray())
        Files.write(Paths.get(archivo), Base64.getEncoder().encode(encryptedBytes))
    }

    fun desencriptar(archivo : String) : List<String> {
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec)
        val encryptedBytes = Base64.getDecoder().decode(Files.readAllBytes(Paths.get(archivo)))
        val decryptedString = String(cipher.doFinal(encryptedBytes))
        return decryptedString.split("\n")
    }
}
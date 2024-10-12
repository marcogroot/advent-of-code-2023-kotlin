import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines

object Utils {
    fun readInput(name: String) = Path("src/files/$name.txt").readLines()

    fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
        .toString(16)
        .padStart(32, '0')

    fun Any?.println() = println(this)

    val directions = listOf(
        Pair(0, 1),
        Pair(1, 0),
        Pair(-1, 0),
        Pair(0, -1),
    )
}

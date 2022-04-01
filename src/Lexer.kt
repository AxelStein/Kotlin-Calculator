class Lexer(private val text: String) {
    private var pos = 0
    private val buffer = StringBuilder()

    fun hasNextToken(): Boolean {
        return peekNextToken() !is TokenEOL
    }

    fun peekNextToken(): Token {
        val p = pos
        val token = nextToken()
        pos = p
        return token
    }

    fun nextToken(): Token {
        val symbols = listOf('+', '-', '*', '/')
        while (pos < text.length) {
            val char = text[pos++]
            if (char.isWhitespace()) {
                continue
            }
            if (char.isDigit()) {
                buffer.append(char)
                if (charIsDigit()) {
                    continue
                } else if (charIsPoint()) {
                    buffer.append(getChar())
                    continue
                } else {
                    val s = buffer.toString()
                    buffer.setLength(0)
                    return if (s.contains('.')) {
                        TokenFloat(s.toFloat())
                    } else {
                        TokenInt(s.toInt())
                    }
                }
            }
            if (char in symbols) {
                return TokenSymbol(char)
            }
        }
        return TokenEOL()
    }

    private fun charIsDigit(): Boolean {
        return hasChar() && getChar().isDigit()
    }

    private fun charIsPoint(): Boolean {
        return hasChar() && getChar() == '.'
    }

    private fun hasChar() = pos < text.length

    private fun getChar() = text[pos]
}

interface Token

data class TokenInt(val value: Int) : Token

data class TokenFloat(val value: Float): Token

data class TokenSymbol(val value: Char) : Token

class TokenEOL : Token
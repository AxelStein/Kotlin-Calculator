class Lexer(private val text: String) {
    private var pos = 0
    private val buffer = StringBuilder()

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
                } else {
                    return TokenInt(buffer.toString().toInt()).also {
                        buffer.setLength(0)
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
        return pos < text.length && text[pos].isDigit()
    }
}

interface Token

data class TokenInt(val value: Int) : Token

data class TokenSymbol(val value: Char) : Token

class TokenEOL : Token
class Lexer(private val text: String) {
    private var pos = 0
    private val buffer = StringBuilder()

    private fun hasNextChar(): Boolean {
        return pos + 1 < text.length
    }

    private fun getNextChar(): Char {
        return text[pos + 1]
    }

    private fun nextCharIsDigit(): Boolean {
        return hasNextChar() && getNextChar().isDigit()
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
            val char = text[pos]
            if (char.isWhitespace()) {
                pos++
                continue
            }
            if (char.isDigit()) {
                buffer.append(char)
                if (nextCharIsDigit()) {
                    pos++
                    continue
                } else {
                    return IntToken(buffer.toString().toInt()).also {
                        buffer.setLength(0)
                        pos++
                    }
                }
            }
            if (char in symbols) {
                return SymbolToken(char)
            }
            pos++
        }
        return Token(TokenType.EOL)
    }
}

open class Token(val type: TokenType) {
    override fun toString(): String {
        return type.toString()
    }
}

class IntToken(val value: Int) : Token(TokenType.INT) {
    override fun toString(): String {
        return value.toString()
    }
}


class SymbolToken(val value: Char) : Token(TokenType.SYMBOL) {
    override fun toString(): String {
        return value.toString()
    }
}

enum class TokenType {
    INT,
    SYMBOL,
    EOL,
}
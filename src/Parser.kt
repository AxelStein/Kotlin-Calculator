import com.sun.org.apache.xpath.internal.operations.Bool

class Parser(private val lexer: Lexer) {
    private val priorityMap = mapOf('+' to 1,
            '-' to 1,
            '*' to 2,
            '/' to 2
    )

    fun parse(): Int {
        return parseExpression(0)
    }

    private fun parseExpression(priority: Int): Int {
        var result = parseItem(lexer.nextToken())
        while (getSymbolPriority(lexer.peekNextToken()) > priority) {
            result = parseTerm(result, lexer.nextToken())
        }
        return result
    }

    private fun parseTerm(left: Int, token: Token): Int {
        val priority = getSymbolPriority(token)
        val right = parseExpression(priority)
        token as TokenSymbol
        return when (token.value) {
            '+' -> left + right
            '-' -> left - right
            '*' -> left * right
            '/' -> left / right
            else -> throw Exception()
        }
    }

    private fun parseItem(token: Token): Int {
        if (token is TokenSymbol) {
            if (token.value == '-') {
                val nextToken = lexer.peekNextToken()
                if (nextToken is TokenInt) {
                    lexer.nextToken()
                    return nextToken.value * -1
                }
            }
        }
        if (token is TokenInt) {
            return token.value
        }
        return 0
    }

    private fun getSymbolPriority(token: Token): Int {
        if (token is TokenSymbol) {
            return priorityMap[token.value]!!
        }
        return 0
    }
}
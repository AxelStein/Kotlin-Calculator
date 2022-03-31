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
        token as TokenSymbol
        val right = parseExpression(priority)
        return when (token.value) {
            '+' -> left + right
            '-' -> left - right
            '*' -> left * right
            '/' -> left / right
            else -> throw Exception()
        }
    }

    private fun parseItem(token: Token): Int {
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
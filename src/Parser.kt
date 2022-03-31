class Parser(private val lexer: Lexer) {
    fun parse(): Int {
        return parseExpression()
    }

    private val symbolPriorityMap = mapOf(
  '+' to 1,
        '-' to 1,
        '*' to 2,
        '/' to 2
    )

    private fun getSymbolPriority(token: Token): Int {
        if (token is TokenSymbol) {
            return symbolPriorityMap[token.value]!!
        }
        return 0
    }

    private fun parseExpression(): Int {
        var result = parseTerm()
        var token = lexer.peekNextToken()
        while (getSymbolPriority(token) == 1) {
            token as TokenSymbol
            lexer.nextToken()

            val n = parseTerm()
            if (token.value == '+') {
                result += n
            } else {
                result -= n
            }
            token = lexer.peekNextToken()
        }
        return result
    }

    private fun parseTerm(): Int {
        var result = parseItem()
        var token = lexer.peekNextToken()
        while (getSymbolPriority(token) == 2) {
            token as TokenSymbol
            lexer.nextToken()

            val n = parseItem()
            if (token.value == '*') {
                result *= n
            } else {
                result /= n
            }
            token = lexer.peekNextToken()
        }
        return result
    }

    private fun parseItem(): Int {
        val token = lexer.peekNextToken()
        if (token is TokenInt) {
            lexer.nextToken()
            return token.value
        }
        return parseExpression()
    }
}
class Parser(private val lexer: Lexer) {
    private val priorityMap = mapOf('+' to 1,
            '-' to 1,
            '*' to 2,
            '/' to 2
    )

    fun parse(): Float {
        return parseExpression(0)
    }

    private fun parseExpression(priority: Int): Float {
        var result = parseItem(lexer.nextToken())
        while (getOperatorPriority(lexer.peekNextToken()) > priority) {
            result = parseTerm(result, lexer.nextToken())
        }
        return result
    }

    private fun parseTerm(left: Float, token: Token): Float {
        val priority = getOperatorPriority(token)
        val right = parseExpression(priority)
        token as TokenOperator
        return when (token.value) {
            '+' -> left + right
            '-' -> left - right
            '*' -> left * right
            '/' -> left / right
            else -> throw Exception()
        }
    }

    private fun parseItem(token: Token): Float {
        if (token is TokenOperator) {
            if (token.value == '-') {
                val nextToken = lexer.peekNextToken()
                if (nextToken.isInt() || nextToken.isFloat()) {
                    lexer.nextToken()
                    if (nextToken is TokenInt) {
                        return nextToken.value.toFloat() * -1f
                    }
                    if (nextToken is TokenFloat) {
                        return nextToken.value * -1f
                    }
                }
            }
        }
        if (token is TokenInt) {
            return token.value.toFloat()
        }
        if (token is TokenFloat) {
            return token.value
        }
        return 0f
    }

    private fun Token.isInt() = this is TokenInt

    private fun Token.isFloat() = this is TokenFloat

    private fun getOperatorPriority(token: Token): Int {
        if (token is TokenOperator) {
            return priorityMap[token.value] ?: 0
        }
        return 0
    }
}
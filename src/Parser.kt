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

    private fun parseItem(token: Token, isNegative: Boolean = false): Float {
        if (token is TokenOperator) {
            if (token.value == '-') {
                return parseItem(lexer.nextToken(), true)
            }
        }
        var result = 0f
        if (token is TokenInt) {
            result = token.value.toFloat()
        }
        if (token is TokenFloat) {
            result = token.value
        }
        if (isNegative) {
            result *= -1f
        }
        return result
    }

    private fun getOperatorPriority(token: Token): Int {
        if (token is TokenOperator) {
            return priorityMap[token.value] ?: 0
        }
        return 0
    }
}
fun main(args: Array<String>) {
    val lexer = Lexer("45 + 4 * 784 / 431")
    println(lexer.peekNextToken())
    println(lexer.nextToken())
    /*while(true) {
        val token = lexer.nextToken()
        if (token.type == TokenType.EOL) {
            break
        }
        println(token)
    }*/
}
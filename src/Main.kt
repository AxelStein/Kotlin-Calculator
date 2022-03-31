fun main(args: Array<String>) {
    val lexer = Lexer("2 + 2 * 2 + 11 * 3 - 2 / 2")
    val result = Parser(lexer).parse()
    println(result)
}
fun main(args: Array<String>) {
    val result = Parser(Lexer("2 + 2 * 2 * 3 - 2")).parse()
    println(result)
}
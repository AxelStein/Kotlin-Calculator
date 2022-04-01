fun main(args: Array<String>) {
    while (true) {
        print(">>> ")
        val lexer = Lexer(readLine()!!)
        val result = Parser(lexer).parse()
        println(result)
    }
}
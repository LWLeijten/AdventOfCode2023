package day08

class Node(val label: String, var left: Node?, var right: Node?) {
    fun next(command: Char) : Node?{
        return if (command == 'L') {
            this.left
        } else {
            this.right
        }
    }
}
package day08

import utils.lcm

val NODE_REGEX = "(\\w+) = \\((\\w+), (\\w+)\\)".toRegex()
const val START = "AAA"
const val STOP = "ZZZ"

class Network(fileContent: List<String>) {

    private val nodeLookup = mutableMapOf<String, Node>()

    init {
        for (fc in fileContent) {
            val matchResult = NODE_REGEX.find(fc)?.groupValues!!
            val left: Node = initChildNode(matchResult[2])
            val right: Node = initChildNode(matchResult[3])
            initMainNode(matchResult[1], left, right)
        }
    }

    private fun initChildNode(label: String): Node {
        var left: Node? = nodeLookup.getOrDefault(label, null)
        if (left == null) {
            left = Node(label, null, null)
            nodeLookup[label] = left
        }
        return left
    }

    private fun initMainNode(
        label: String,
        left: Node,
        right: Node
    ) {
        var node: Node? = nodeLookup.getOrDefault(label, null)
        if (node == null) {
            node = Node(label, null, null)
        }
        node.left = left
        node.right = right
        nodeLookup[node.label] = node
    }

    fun traverse(commands: String): Int {
        var steps = 0
        var currentNode = nodeLookup[START]
        while (currentNode!!.label != STOP) {
            val command = commands[steps % commands.length]
            currentNode = currentNode.next(command)
            steps++
        }
        return steps
    }

    fun traverseLikeGhosts(commands: String): Long {
        val starts = nodeLookup.values.filter { it.label.endsWith('A') }
        val shortestPaths = starts.map {
            var steps = 0
            var cur = it
            while (!cur.label.endsWith('Z')) {
                val command = commands[steps % commands.length]
                cur = cur.next(command)!!
                steps++
            }
            steps.toLong()
        }
        return lcm(shortestPaths)
    }
}
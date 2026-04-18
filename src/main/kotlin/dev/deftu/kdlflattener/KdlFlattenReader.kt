package dev.deftu.kdlflattener

import com.google.gson.GsonBuilder
import dev.kdl.KdlNode
import dev.kdl.parse.KdlParser
import java.io.FilterReader
import java.io.Reader
import java.io.StringReader

class KdlFlattenReader(input: Reader) : FilterReader(processKdl(input)) {
    companion object {
        private fun processKdl(reader: Reader): StringReader {
            val kdlText = reader.readText()
            val parser = KdlParser.v2()
            val document = parser.parse(kdlText)
            val flatMap = mutableMapOf<String, String>()

            fun traverse(nodes: List<KdlNode>, currentPrefix: String) {
                for (node in nodes) {
                    val nodeName = node.name
                    val key = if (currentPrefix.isEmpty()) {
                        nodeName
                    } else {
                        "$currentPrefix.$nodeName"
                    }

                    if (node.arguments.isNotEmpty()) {
                        val value = node.arguments[0].value().toString()
                        flatMap[key] = value
                    }

                    if (node.children.isNotEmpty()) {
                        traverse(node.children, key)
                    }
                }
            }

            traverse(document.nodes, "")

            val gson = GsonBuilder().setPrettyPrinting().create()
            return StringReader(gson.toJson(flatMap))
        }
    }
}
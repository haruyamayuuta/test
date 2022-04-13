package com.example.models

import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement
import java.util.*
import kotlin.collections.ArrayList

fun main(args: Array<String>) {
    val users = findAllArticles()
    println(users[0])
}

fun findAllArticles(): List<Users> {
    var conn: Connection? = null
    var statement: Statement? = null
    var resultSet: ResultSet? = null

    val users=ArrayList<Users>()

    try {
        conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/test","root","")
        statement = conn?.createStatement()
        //sqlで何がしたいのか？データ取得
        resultSet = statement?.executeQuery("select * from users")
        while(resultSet?.next() ?: false) {
            users.add(Users(
                resultSet!!.getLong(1),
                resultSet!!.getString(2)!!
            ))
        }
    } finally {
        resultSet?.close()
        statement?.close()
        conn?.close()
    }

    return users
}

data class Users(
    val id: Long,
    val name: String
)
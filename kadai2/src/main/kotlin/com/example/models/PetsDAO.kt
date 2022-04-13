package com.example.models

import com.typesafe.config.ConfigException.Null
import io.ktor.server.routing.*
import io.ktor.http.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import io.ktor.server.application.*
import io.ktor.server.response.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.Properties
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.dao.id.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SchemaUtils.create


//テーブルの枠
object PetsDAO:IntIdTable("Pets"){
    //val number = integer("id").uniqueIndex()
    val userid=integer("user_id").uniqueIndex()
    val name = varchar("name",50)
}
//テーブルにクラス
class Pet(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Pet>(PetsDAO)
    //var number by UsersDAO.number
    var userid by PetsDAO.userid
    var name by PetsDAO.name
}
fun Application.petsdao() {
    Database.connect("jdbc:mysql://127.0.0.1/test", "com.mysql.cj.jdbc.Driver", "root", "")
    routing {
        get("/users/{id}/pets") {
            var did: Int = 0
            var dname: String? = null
            var dd: String? = null
            var co: Int = 0
            var allname: String=""
            transaction {
                val sid = call.parameters["id"]
                val i: Int = Integer.parseInt(sid)
                println(i)
                val userdata = Pet.findById(i)
                if (userdata != null) {
                    did = userdata.userid
                    dname = userdata.name
                }
            }
            var ts: Int = 0
            var sse: Int = 0
            var sna: String? = null
            //var hs:String?=null
            while (true) {
                ts += 1
                transaction {
                    val se = Pet.findById(ts)
                    sse = se!!.userid
                    sna = se!!.name
                }
                //println(sna)
                if (sse == did) {
                    if (ts != 1) {
                        allname += ","
                    }
                    allname += "${sna}"
                    co += 1 //数
                    //na.add(se!!.name) //名前加える
                    println("$sna")
                }
                if (ts == 10) {
                    break
                }
            }
            call.respondText("$did" + "の買っているペットは" + "$co" + "頭で、それぞれの名前は" + "$allname")
        }
    }
}



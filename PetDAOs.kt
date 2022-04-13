package com.example.models

import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.dao.id.*
import org.jetbrains.exposed.sql.*


//テーブルの枠
object PetsDAOs:IntIdTable("Pets"){
    //val countber = integer("id").uniqueIndex()
    val userid=integer("user_id").uniqueIndex()
    val name = varchar("name",50)
}
//テーブルにクラス
class PetDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Pet>(PetsDAO)
    //var countber by UsersDAO.countber
    var userid by PetsDAO.userid
    var name by PetsDAO.name
}
fun Application.petsdao() {
    var count:Int=0
    Database.connect("jdbc:mysql://127.0.0.1/test", "com.mysql.cj.jdbc.Driver", "root", "")
    //データベース内データ数のカウント
    transaction {
        val movies=Pet.all()
        count= movies.count().toInt()
        println( movies.count())
    }
    routing {
        get("/users/{id}/pets") {
            var did: Int = 0
            var dname: String? = null
            var dd: String? = null
            var pet_count: Int = 0 //ペットの数
            var allname: String=""
            transaction {
                //入力されたidを使用する
                val sid = call.parameters["id"]
                //int型に変更
                val i: Int = Integer.parseInt(sid)
                val userdata = Pet.findById(i)
                //didにuserid、dnameにnameを代入
                if (userdata != null) {
                    did = userdata.userid
                    dname = userdata.name
                }
            }
            var ts: Int = 0
            var ssid: Int = 0
            var sna: String? = null
            //
            while (true) {
                ts += 1
                println(ts)
                transaction {
                    val se = Pet.findById(ts)
                    ssid = se!!.userid
                    sna = se!!.name
                }
                if (ssid == did) {
                    if (ts != 1) {
                        allname += ","
                    }
                    allname += "${sna}"
                    pet_count += 1 //ペット数加える
                }
                if (ts == count) {
                    break
                }
            }
            call.respondText("$did" + "の買っているペットは" + "$pet_count" + "頭で、それぞれの名前は" + "$allname")
        }
    }
}



package com.feifei.share.manager

import java.sql.Date

import com.feifei.share.constants.Constant
import com.feifei.share.util.DBUtil

import scala.io.Source

/**
 * Created by yanpf on 2016/12/12.
 */
object ShareCustomManager {

  val sql_insert = "insert into t_share(code, date, current_price, increment, incr_persent, turnover, volume) values (?,?,?,?,?,?,?)"

  def insertData(): Unit ={
    val codeList = CodeManager.getCodeList()
    val sb = new StringBuffer()
    var i = 0
    val date = new Date(System.currentTimeMillis())
    for(code <- codeList){
      sb.append("s_").append(code).append(",")
      i = i + 1
      if(i % 1 == 0){
        getAndInsert(sb, date)
        sb.setLength(0)
      }
    }
    if(sb.length() > 0){
      getAndInsert(sb, date)
    }
  }

  private def getAndInsert(sb:StringBuffer, date:Date): Unit ={
    val url = Constant.URL.TECENT_CONSTANT + sb.toString
    val datas = Source.fromURL(url)
    val connection = DBUtil.getConnection
    val statement = connection.prepareStatement(sql_insert)
    datas.mkString.split(";").foreach(item => {
      try {
        val split = item.split("=")
        val code = split(0).substring(4)
        val useful = split(1).split("~")
        val currentPrice = useful(3)
        val increment = useful(4)
        val incrPercent = useful(5)
        val turnover = useful(6)
        val volume = useful(7)
        println(code + "," + date + "," + currentPrice + "," + increment + "," + incrPercent + "," + turnover + "," + volume)

        statement.setString(1, code)
        statement.setDate(2, date)
        statement.setString(3, currentPrice)
        statement.setString(4, increment)
        statement.setString(5, incrPercent)
        statement.setString(6, turnover)
        statement.setString(7, volume)

        statement.execute()

      }catch {
        case e:Exception =>
          println("error:" + e.getMessage)
      }

    })
  }
}

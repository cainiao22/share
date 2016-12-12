package com.feifei.share.manager

import com.feifei.share.constants.Constant
import com.feifei.share.util.DBUtil

import scala.io.Source

/**
 * Created by yanpf on 2016/12/12.
 */
object ShareCapitalManager {

  val sql_insert = "insert into t_share_capital(code, date, inflow_capital, outflow_capital, net_inflow_capital, inflow_individual, outflow_individual, net_inflow_individual, total_flow) values (?, ?, ?, ?, ?,?,?,?,?)"

  //val sql_insert_sz = "insert into t_share_capital_sz(code, date, inflow, outflow, net_inflow) values (?, ?, ?, ?, ?)"


  def insertData(): Unit = {
    val codeList = CodeManager.getCodeList()
    val sb = new StringBuffer()
    var i = 0
    for(code <- codeList){
      sb.append("ff_").append(code).append(",")
      i = i + 1
      if(i % 1 == 0){
        getAndInsert(sb)
        sb.setLength(0)
      }
    }
    if(sb.length() > 0){
      getAndInsert(sb)
    }
  }

  private def getAndInsert(sb: StringBuffer): Unit = {
    val url = Constant.URL.TECENT_CAPITAL + sb.toString
    val datas = Source.fromURL(url)
    val connection = DBUtil.getConnection
    val statement = connection.prepareStatement(sql_insert)
    datas.mkString.split(";").foreach(item => {
      try {
        val split = item.split("=")
        val useful = split(1).split("~")

        val code = useful(0).substring(useful(0).indexOf("\"") + 1);
        val inflowCapital = useful(1)
        val outflowCapittal = useful(2)
        val netflowCapital = useful(3)

        val infowIndevial = useful(5)
        val outflowIndevial = useful(6)
        val netflowIndevial = useful(7)

        val totalflow = useful(9)

        val date = useful(13)
        println(code + "," + date + "," + inflowCapital + "," + outflowCapittal + "," + netflowCapital + "," + infowIndevial + "," + outflowIndevial + "," + netflowIndevial + "," + totalflow)


        statement.setString(1, code)
        statement.setString(2, date)
        statement.setString(3, inflowCapital)
        statement.setString(4, outflowCapittal)
        statement.setString(5, netflowCapital)
        statement.setString(6, infowIndevial)
        statement.setString(7, outflowIndevial)
        statement.setString(8, netflowIndevial)
        statement.setString(9, totalflow)

        statement.execute()
      } catch {
        case e: Exception =>
          println("error: " + e.getMessage)
      }
    })

    DBUtil.closeConnection(connection, statement, null)
  }
}

package com.feifei.share.manager

import com.feifei.share.constants.Constant
import com.feifei.share.util.DBUtil

import scala.collection.immutable.List
import scala.io.Source

/**
 * Created by Administrator on 2016/5/7.
 */
object CodeManager {

  val sql_select1 = "select code from t_code where flag=0 and code not IN (select DISTINCT code from t_share)";

  val sql_select = "select code from t_code";

  val sql_update = "update t_code set flag=? where code=?"

  val sql_insert = "insert into t_code(code, total_capital, flow_capital, flag) values (?, ?, ?, ?)"

  def getCodeList():List[String] = {
    val connection = DBUtil.getConnection;
    val statement = connection.prepareStatement(sql_select);
    val result = statement.executeQuery();
    var list = List[String]();
    while(result.next()){
      list = result.getString(1)::list;
    }
    DBUtil.closeConnection(connection, statement, result);
    list;
  }

  def updateState(code:Int, flag:Int): Unit = {
    val connection = DBUtil.getConnection;
    val statement = connection.prepareStatement(sql_update);
    statement.setInt(1, flag);
    statement.setInt(2, code);
    statement.execute();
    DBUtil.closeConnection(connection, statement, null);

  }

  def updateCapitalInit():Unit = {
    val sb:StringBuffer = new StringBuffer()
    for(i:Int <- 1 to 2829){
      var code:String = ""
      if(i < 10)
        code = "00000" + i
      else if(i < 100)
        code = "0000" + i
      else if(i < 1000)
        code = "000" + i
      else if(i < 10000)
        code = "00" + i
      sb.append("sz").append(code).append(",")
      getDataAndInsert(sb, 1)
    }

    for(i:Int <- 300001 to 300573){
      sb.append("sz").append(i).append(",")
      getDataAndInsert(sb, 2)
    }

    for(i:Int <- 600000 to 603999){
      sb.append("sh").append(i).append(",")
      getDataAndInsert(sb, 0)
    }

  }

  def updateCapital():Unit = {
    val sb:StringBuffer = new StringBuffer()
    val codeList = getCodeList()
    for(i:Int <- 1 to 2829){
      var code:String = ""
      if(i < 10)
        code = "00000" + i
      else if(i < 100)
        code = "0000" + i
      else if(i < 1000)
        code = "000" + i
      else if(i < 10000)
        code = "00" + i
      if(!codeList.contains("sz" + code)){
        sb.append("sz").append(code).append(",")
        getDataAndInsert(sb, 1)
        sb.setLength(0)
      }
    }
    for(i:Int <- 300001 to 300573){
      if(!codeList.contains("sz" + i)){
        sb.append("sz").append(i).append(",")
        getDataAndInsert(sb, 2)
      }
    }
    sb.setLength(0)
    for(i:Int <- 600000 to 603999){
      if(!codeList.contains("sh" + i)){
        sb.append("sh").append(i).append(",")
        getDataAndInsert(sb, 0)
      }
    }

  }

  private def getDataAndInsert(sb: StringBuffer, flag:Int): Unit = {
    if(sb.length() == 0) return ;
    val url = Constant.URL.TECENT_CONSTANT + sb.toString
    val connection = DBUtil.getConnection
    val statement = connection.prepareStatement(sql_insert)
    val datas = Source.fromURL(url);
    datas.mkString.split("\r\n").foreach(item => {
      try {
        val split = item.split("=")
        val capital = split(1).split("~")(45);
        val flow = split(1).split("~")(44)
        val code = split(0).substring(2)
        println(code + "," + capital + "," + flow)
        statement.setString(1, code)
        statement.setDouble(2, capital.toDouble) //total_capital
        statement.setDouble(3, flow.toDouble) //flow_capital
        statement.setInt(4, flag)
        statement.execute()
      } catch {
        case e: Exception =>
          println("empty:" + sb.toString + "," + e.getMessage)
      }
    })
    sb.setLength(0)
    DBUtil.closeConnection(connection, statement, null)
  }
}

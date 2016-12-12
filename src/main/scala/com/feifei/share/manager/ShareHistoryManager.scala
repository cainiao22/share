package com.feifei.share.manager

import com.feifei.share.bean.ShareHistory
import com.feifei.share.util.DBUtil

/**
 * Created by Administrator on 2016/4/17.
 */
object ShareHistoryManager {

  private val SQL_LIST_BY_CODE = "select * from t_share where code=?"
  private val SQL_INSERT = "insert into t_share(code, open_price, high_price, low_price, close_price, volume, adj_close, date) values (?, ?, ?, ?, ?, ?, ?, ?)"

  def getShareHistoryListByCode(code : String): List[ShareHistory] = {
    val connection = DBUtil.getConnection;
    val statement = connection.prepareStatement(SQL_LIST_BY_CODE)
    statement.setString(1, code)
    val resultSet = statement.executeQuery()
    val list = ShareHistory.getListFromResultSet(resultSet)
    DBUtil.closeConnection(connection, statement, resultSet)
    list
  }

  def insertShareHistory(shareHistory: ShareHistory): Unit = {
    val connection = DBUtil.getConnection;
    val statement = connection.prepareStatement(SQL_INSERT)
    statement.setString(1, shareHistory.code)
    statement.setDouble(2, shareHistory.openPrice)
    statement.setDouble(3, shareHistory.highPrice)
    statement.setDouble(4, shareHistory.lowPrice)
    statement.setDouble(5, shareHistory.closePrice)
    statement.setDouble(6, shareHistory.volume)
    statement.setDouble(7, shareHistory.adjClose)
    statement.setString(8, shareHistory.date)
    statement.execute()
    DBUtil.closeConnection(connection, statement, null)
  }

  def insert(code:String, lines:Array[String]):Unit = {
    var length = lines.size;
    if(length > 120){
      length = 120
    }
    for(i <- 1 to length) {
      val share = ShareHistory.create(code, lines(i))
      insertShareHistory(share)
    }
  }
}

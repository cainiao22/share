package com.feifei.share.bean

import java.sql.ResultSet


/**
 * Created by Administrator on 2016/4/17.
 */
class ShareHistory {
  var id:Long = 0
  var code:String = ""
  var openPrice:Double = 0;
  var highPrice:Double = 0;
  var lowPrice:Double = 0;
  var closePrice:Double = 0;
  var volume:Long = 0
  var adjClose:Double = 0;
  var avg5:Double = 0
  var date:String = null

  override
  def toString():String = {
    code  + "," + avg5 + "," + openPrice + "," + highPrice + "," + lowPrice + "," + closePrice + "," + date + ""
  }


}

object ShareHistory {
  def create(code:String, line:String):ShareHistory = {
    val data = line.split(",")
    data match {
      case Array(date, open, high, low, close, volume, adjClose) => {
        val shareHistory = new ShareHistory;
        shareHistory.code = code;
        shareHistory.date = date
        shareHistory.openPrice = open.toDouble
        shareHistory.highPrice = high.toDouble
        shareHistory.lowPrice = low.toDouble
        shareHistory.closePrice = close.toDouble
        shareHistory.volume = volume.toLong
        shareHistory.adjClose = volume.toDouble

        return shareHistory
      }

      case _ => null
    }


  }

  def create(code:Int, line:String):ShareHistory = {
    create(code + "", line)
  }

  def getListFromResultSet(resultSet: ResultSet):List[ShareHistory] = {
    resultSet match {
      case null => return null
      case _ => {
        var list = List[ShareHistory]()
        while(resultSet.next()){
          val shareHistory : ShareHistory = new ShareHistory;
          shareHistory.id = resultSet.getLong("id")
          shareHistory.code = resultSet.getString("code")
          shareHistory.openPrice = resultSet.getDouble("open_price")
          shareHistory.highPrice = resultSet.getDouble("high_price")
          shareHistory.lowPrice = resultSet.getDouble("low_price")
          shareHistory.closePrice = resultSet.getDouble("close_price")
          shareHistory.date = resultSet.getString("date")
          shareHistory.volume = resultSet.getLong("volume")
          shareHistory.adjClose = resultSet.getDouble("adj_close")
          list = list ::: List(shareHistory)
        }
        return list
      }
    }
  }
}

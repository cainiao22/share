package com.feifei.share

import java.io.FileNotFoundException
import java.net.ConnectException

import com.feifei.share.constants.Constant
import com.feifei.share.manager.{ShareHistoryManager, CodeManager}

import scala.io.Source

/**
 * Created by Administrator on 2016/5/7.
 */
object DailyInsert {

  def main(args: Array[String]) {
    val list = CodeManager.getCodeList();
    list.foreach(i => {
      try {
        val url = Constant.URL.SHARE_HISTORY + i + ".ss"
        val lines = Source.fromURL(url, "UTF-8").mkString.split("\n")
        ShareHistoryManager.insert(i + "", lines)
        //CodeManager.updateState(i, 1); //1更新成功
      } catch {
        case e: ConnectException => {
          println("error:" + i + "\t" + e.getMessage + "|" + e.getClass)
        }

        case io:FileNotFoundException => {
          //CodeManager.updateState(i, 2);//2不存在
          println("error:" + i + "\t" + io.getMessage + "|" + io.getClass)
        }

        case other => {
          println("error:" + i + "\t" + other.getMessage + "|" + other.getClass)
        }
      }
    })
  }
}

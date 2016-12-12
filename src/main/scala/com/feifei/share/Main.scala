package com.feifei.share

import java.io.PrintWriter
import java.text.SimpleDateFormat
import java.util.Date

import com.feifei.share.bean.ShareHistory
import com.feifei.share.manager.ShareHistoryManager
import com.feifei.share.strategy.MinPercentProcessor
import com.feifei.share.thread.ShareHistoryThread

import scala.io.Source
import com.feifei.share.constants.Constant

/**
 * Created by Administrator on 2016/4/17.
 */
object Main {
  def main1(args: Array[String]) {
    for(i <- 0 to 30){
      val index = i*1000 + 600000
      val array = Array.range(index, index + 1000)
      val runnable = new ShareHistoryThread[Int](index, array.length - 1 ,array) {
        def processJob(code : Int) = {
            val lines = Source.fromURL(Constant.URL.SHARE_HISTORY + code + ".ss", "UTF-8").mkString.split("\n")
            for(i <- 1 to 300) {
              val shareHistory = ShareHistory.create(code.toString, lines(i))
              ShareHistoryManager.insertShareHistory(shareHistory)
            }
        }
      }
      new Thread(runnable).start();
    }
    val array = Array.range(1,1000).map(num => {
      val pattern = "000000"
      val numStr = num + ""
      pattern.substring(0, 6 - numStr.length) + numStr
    })
    val runnable = new ShareHistoryThread[String](0, array.length - 1, array) {
      def processJob(code : String) = {
        val lines = Source.fromURL(Constant.URL.SHARE_HISTORY + code + ".sz", "UTF-8").mkString.split("\n")
        println(lines)
        for(i <- 1 to 300) {
          val shareHistory = ShareHistory.create(code.toString, lines(i))
          ShareHistoryManager.insertShareHistory(shareHistory)
        }
      }
    }
    new Thread(runnable).start()

    //sz002001-sz002792
    val array2 = Array.range(2001,2793).map(num => {
      val pattern = "000000"
      val numStr = num + ""
      pattern.substring(0, 6 - numStr.length) + numStr
    })
    val runnable2 = new ShareHistoryThread[String](0, array2.length - 1, array2) {
      def processJob(code : String) = {
        val lines = Source.fromURL(Constant.URL.SHARE_HISTORY + code + ".sz", "UTF-8").mkString.split("\n")
        for(i <- 1 to 300) {
          val shareHistory = ShareHistory.create(code.toString, lines(i))
          ShareHistoryManager.insertShareHistory(shareHistory)
        }
      }
    }
    new Thread(runnable2).start()

  }

  def main(args: Array[String]) {
    val date = new SimpleDateFormat("yyyyMMddhhmm-").format(new Date())

    val writer = new PrintWriter("F:\\datas\\MinPercentProcessor\\" + date + "temp.txt")
    val processor = new MinPercentProcessor(writer);
    for (i <- 600614 to 603999) {
      println("code:" + i)
      val url = Constant.URL.SHARE_HISTORY + i + ".ss"
      try {
        val lines = Source.fromURL(url, "UTF-8").mkString.split("\n")
        processor.process(i, lines);
      } catch {
        case e => {
          println("error:" + i + "\t" + e.getMessage + "|" + e.getClass + "\t" + url)
        }

      }
    }
    writer.close();
  }
}



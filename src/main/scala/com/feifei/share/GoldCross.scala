package com.feifei.share

import java.io.PrintWriter

import com.feifei.share.bean.ShareHistory
import com.feifei.share.constants.Constant

import scala.io.Source

/**
 * Created by Administrator on 2016/4/30.
 */
object GoldCross {
  def main(args: Array[String]) {
    val globalOut = new PrintWriter("F:\\datas\\high\\ups");
    for(i <- 600000 to 603999) {
      println("code:" + i)
      val url = Constant.URL.SHARE_HISTORY + i + ".ss"
      try {
        val lines = Source.fromURL(url, "UTF-8").mkString.split("\n")
        var list60 = List[ShareHistory]();
        var list30 = List[ShareHistory]();
        var list10 = List[ShareHistory]();
        var list5 = List[ShareHistory]();

        for(j <- Range(60, 1, -1)){
          list60 = ShareHistory.create(i, lines(j))::list60
        }

        list30 = list60.take(30)

        list10 = list60.take(10)

        list5 = list60.take(5)

        var avg60 = List[Double]();
        var avg30 = List[Double]();
        var avg10 = List[Double]();
        var avg5 = List[Double]();

        var sum60 = 0.0
        list60.foreach(item => sum60 = sum60 + item.closePrice)
        avg60 = sum60/60 :: avg60
        var sum30 = 0.0
        list30.foreach(item => sum30 = sum30 + item.closePrice)
        avg30 = sum30/30::avg30
        var sum10 = 0.0
        list10.foreach(item => sum10 = sum10 + item.closePrice)
        avg10 = sum10/10::avg10
        var sum5 = 0.0
        list5.foreach(item => sum5 = sum5 + item.closePrice)
        avg5 = sum5/5::avg5
        for(k <- 1 to 2){
          sum60 = sum60 - list60(k).closePrice + ShareHistory.create(k, lines(k + 59)).closePrice
          avg60 = sum60/60::avg60
          sum30 = sum30 - list60(k).closePrice + list60(29+k).closePrice
          avg30 = sum30/30::avg30
          sum10 = sum10 - list60(k).closePrice + list60(9+k).closePrice
          avg10 = sum10/10::avg10
          sum5 = sum5 - list60(k).closePrice + list60(4+k).closePrice
          avg5 = sum5/5::avg5
        }

        avg30.zip(avg60).map(b => b._1 - b._2) match {
          case a::b::c::tail => {
            if(a< 0 && b <0 && c > 0){
              var flag = 1
              if(ShareHistory.create(i,lines(1)).closePrice > list60(0).closePrice){
                flag = 0
              }
              globalOut.println(i + ", " + "avg60:" + avg60 + c/avg60(2) + "," + flag + "\n")
              globalOut.flush();
            }
          }

        }

        avg10.zip(avg30).map(b => b._1 - b._2) match {
          case a::b::c::tail => {
            if(a< 0 && b <0 && c > 0){
              var flag = 1
              if(ShareHistory.create(i,lines(1)).closePrice > list60(0).closePrice){
                flag = 0
              }
              globalOut.println(i + ", " + "avg30:" + avg30 + c/avg30(2) + "," + flag + "\n")
              globalOut.flush();
            }
          }

        }

        avg5.zip(avg10).map(b => b._1 - b._2) match {
          case a::b::c::tail => {
            var flag = 1
            if(ShareHistory.create(i,lines(1)).closePrice > list60(0).closePrice){
              flag = 0
            }
            if(a< 0 && b <0 && c > 0){
              globalOut.println(i + ", " + "avg60:" + avg10 + c/avg10(2) + "," + flag + "\n")
              globalOut.flush();
            }
          }

        }

      }catch {
        case e => println("error:" + i + "\t" + e.getMessage + "\t" + e.getLocalizedMessage)
      }
    }

    globalOut.close()
  }
}

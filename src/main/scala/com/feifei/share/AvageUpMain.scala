package com.feifei.share

import java.io.PrintWriter

import com.feifei.share.bean.ShareHistory
import com.feifei.share.constants.Constant

import scala.io.Source

/**
 * Created by Administrator on 2016/4/28.
 */
object AvageUpMain {
  def main(args: Array[String]) {
    val globalOut = new PrintWriter("F:\\datas\\high\\ups");
    for(i <- 600000 to 603999) {
      println("code:" + i)
      val url = Constant.URL.SHARE_HISTORY + i + ".ss"
      try {
        val lines = Source.fromURL(url, "UTF-8").mkString.split("\n")
        var list = List[ShareHistory]();
        var all:Double = 0
        for(j <- 5 to 35) {
          list = ShareHistory.create(i+"", lines(j))::list;
          all = all + list(0).closePrice
        }
        all = all / list.length;
        val out = new PrintWriter("F:\\datas\\" + i)
        for(j <- Range(list.length - 1, 4, -1)){
          var sum5:Double = 0
          for(k <- Range(j, j-5, -1)){
            //println("date:" + list(k).date +  ",close:" + list(k).closePrice + ",sum5:" + sum5)
            sum5 = sum5 + list(k).closePrice;
          }
          list(j).avg5 = sum5 / 5;

          out.append(list(j).toString())
          out.flush()
        }
        list = list.reverse
        val value = list.zip(list.takeRight(list.size - 1):::List(list(0))).map(b => if(b._1.avg5 > b._2.avg5) 1 else 0).take(3).reduce(_+_)
        println("value:" + value)
        if(value == 3){
          globalOut.append(i + "\t")
          globalOut.append("up\t")
          if((all - list(0).closePrice) > 0.05 * all){
            globalOut.append("high up\n")
          }
          globalOut.flush()
        }
        out.flush()
        out.close()

      }catch {
        case e => println("error:" + i + "\t" + e.getStackTrace)
      }
    }

    globalOut.close()
  }
}

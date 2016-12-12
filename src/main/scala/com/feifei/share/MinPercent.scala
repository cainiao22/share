package com.feifei.share

import java.io.PrintWriter
import java.net.{ConnectException}

import com.feifei.share.bean.ShareHistory
import com.feifei.share.constants.Constant
import com.feifei.share.manager.ShareHistoryManager


import scala.io.Source

/**
 * Created by Administrator on 2016/5/3.
 */
object MinPercent {
  def main(args: Array[String]) {
    val writer1 = new PrintWriter("F:/datas/temp/writer1");
    val writer2 = new PrintWriter("F:/datas/temp/writer2");

    getWriter1(writer1, writer2);
    getWriter2(writer2, writer1);
    for (i <- 600796 to 603999) {
      println("code:" + i)
      val url = Constant.URL.SHARE_HISTORY + i + ".ss"
      try {
        val lines = Source.fromURL(url, "UTF-8").mkString.split("\n")
        ShareHistoryManager.insert(i + "", lines)
      }catch {
        case e:ConnectException => {
          println("error:" + i + "\t" + e.getMessage)
          writer1.append(i + "\n");
          writer1.flush();
        }
        case other => {
          println("error:" + i + "\t" + other.getMessage)
        }
      }
    }

    getWriter1(writer1, writer2);

  }

  def getWriter1(writer1:PrintWriter, writer2 : PrintWriter): Unit = {
    writer2.write("")
    writer2.flush();
    val source1 = Source.fromFile("F:/datas/temp/writer1").mkString
    if (source1.isEmpty) {
      return;
    }
    val datas = source1.split("\n");
    datas.foreach(i => {
      try {
        val url = Constant.URL.SHARE_HISTORY + i + ".ss"
        val lines = Source.fromURL(url, "UTF-8").mkString.split("\n")
        ShareHistoryManager.insert(i, lines)
      } catch {
        case e: ConnectException => {
          println("error:" + i + "\t" + e.getMessage)
          writer2.append(i + "\n");
          writer2.flush()
        }

        case other => {
          println("error:" + i + "\t" + other.getMessage)
        }
      }
    })

    getWriter2(writer2, writer1);
  }

    def getWriter2(writer2:PrintWriter, writer1:PrintWriter):Unit = {
      writer1.write("")
      writer1.flush();
      val source2 = Source.fromFile("F:/datas/temp/writer2").mkString
      if(source2.isEmpty){
        return ;
      }
      val datas = source2.split("\n");
      datas.foreach(i => {
        try {
          val url = Constant.URL.SHARE_HISTORY + i + ".ss"
          val lines = Source.fromURL(url, "UTF-8").mkString.split("\n")
          ShareHistoryManager.insert(i, lines)
        } catch {
          case e: ConnectException => {
            println("error:" + i + "\t" + e.getMessage)
            writer1.append(i + "\n");
            writer1.flush()
          }

          case other => {
            println("error:" + i + "\t" + other.getMessage)
          }
        }
      })

      getWriter1(writer1, writer2);
    }

}

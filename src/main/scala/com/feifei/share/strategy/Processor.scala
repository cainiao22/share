package com.feifei.share.strategy

import java.io.PrintWriter

import com.feifei.share.bean.ShareHistory

/**
 * Created by Administrator on 2016/5/3.
 */
abstract class Processor(w: PrintWriter) {
  var writer:PrintWriter = w;


  def process(code: Int, lines:Array[String]): Unit = {
    var list = List[ShareHistory]();
    for(i <- Range(100, 0, -1)) {
      val share = ShareHistory.create(code, lines(i));
      list = share::list;
    }

    realProcess(list);

    writer.flush();
  }

  protected  def realProcess(shareList:List[ShareHistory]): Unit ={

  }
}

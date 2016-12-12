package com.feifei.share.strategy

import java.io.PrintWriter
import java.text.SimpleDateFormat
import java.util.Date

import com.feifei.share.bean.ShareHistory

/**
 * Created by Administrator on 2016/5/3.
 */
class MinPercentProcessor(w:PrintWriter) extends Processor(w) {

  private val path = "F:\\datas\\MinPercentProcessor\\high\\"

  val date = new SimpleDateFormat("yyyyMMddHHmm-").format(new Date())

  private var minIndex = 0;
  private var maxIndex = 0;
  private var min = 999999d;
  private var max = 0d;
  //private val writer2 = new PrintWriter("F:\\datas\\MinPercentProcessor\\high.txt");
  override def realProcess(shareList:List[ShareHistory]): Unit ={
      val curr = shareList(0).closePrice;
      findMinAndMax(shareList);
      var maxPercent = 0.0;
      var percent = 0.0;
      if(curr > min) {
        percent = (curr - min)*100/min;
        writer.append("+\t");
      }else {
        percent = (min - curr)*100/curr;
        writer.append("-\t");
      }
      maxPercent = (max / curr) * 100 / curr;
      writer.append(shareList(0).code + "\t" + percent + "\t" + shareList(0) + "\t" + shareList(minIndex) + "\t" + shareList(maxIndex) + "\t" + maxPercent + "\n")

      val range = (percent.toInt/5 + 1) * 5;
      if(range > 30){
        return;
      }
      val writerHigh = new PrintWriter(path + date + range + ".txt");
      if(curr > min) {
        writerHigh.append("+\t");
      }else {
        writerHigh.append("-\t");
      }

      writerHigh.append(shareList(0).code + "\t" + percent + "\t" + shareList(0) + "\t" + shareList(minIndex) + "\t" + shareList(maxIndex) + "\t" + maxPercent + "\n")
      writerHigh.close();
  }

  def findMinAndMax(shareList:List[ShareHistory]):Double = {
    for(i <- 0 to shareList.length - 1) {
      if(min > shareList(i).closePrice) {
        min = shareList(i).closePrice;
        minIndex = i;
      }
      if(max < shareList(i).closePrice){
        max = shareList(i).closePrice;
        maxIndex = i;
      }
    }

    return min;
  }
}

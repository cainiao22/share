package com.feifei.share

import com.feifei.share.manager.{ShareCustomManager, ShareCapitalManager, CodeManager}

/**
 * Created by yanpf on 2016/12/11.
 */
object TotalCapitalMain {

  def main(args: Array[String]) {
    //CodeManager.updateCapital()

    ShareCapitalManager.insertData()
    //ShareCapitalManager.insertDataLost("20161215")
    ShareCustomManager.insertData()
  }

}

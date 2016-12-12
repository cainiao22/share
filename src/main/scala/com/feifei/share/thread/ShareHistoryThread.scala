package com.feifei.share.thread

/**
 * Created by Administrator on 2016/4/17.
 */
abstract class ShareHistoryThread[R](index:Int, range:Int, jobs:Array[R]) extends Runnable {

  def run() = {
    for(i <- index to range) {
      processJob(jobs(i))
    }
  }

  def processJob(obj : R): Unit
}

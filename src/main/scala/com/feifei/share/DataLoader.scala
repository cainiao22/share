package com.feifei.share

import com.feifei.share.util.DBUtil

/**
 * Created by Administrator on 2016/5/5.
 */
object DataLoader {
  def main(args: Array[String]) {
    val connection = DBUtil.getConnection
    val statement = connection.prepareStatement("insert into t_code(code, flag) values(?, 0)");
    for(i <- 600000 to 603999){
      statement.setInt(1, i);
      statement.execute();
    }

    DBUtil.closeConnection(connection, statement, null)
  }
}

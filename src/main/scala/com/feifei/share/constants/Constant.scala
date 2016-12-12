package com.feifei.share.constants

/**
 * Created by Administrator on 2016/4/17.
 */
object Constant {
  object URL {
    val SHARE_HISTORY = "http://table.finance.yahoo.com/table.csv?s="

    /**
     * 要获取最新行情
     *
     * 0: 未知
       1: 名字
       2: 代码
       3: 当前价格
       4: +
       5: 今开
       6: 成交量（手）
       7: 外盘
       8: 内盘
       9: 买一
      10: 买一量（手）
      11-18: 买二 买五
      19: 卖一
      20: 卖一量
      21-28: 卖二 卖五
      29: 最近逐笔成交
      30: 时间
      31: 涨跌
      32: 涨跌%
      33: 最高
      34: 最低
      35: 价格/成交量（手）/成交额
      36: 成交量（手）
      37: 成交额（万）
      38: 换手率
      39: 市盈率
      40:
      41: 最高
      42: 最低
      43: 振幅
      44: 流通市值
      45: 总市值
      46: 市净率
      47: 涨停价
      48: 跌停价
     */
    val TECENT_CONSTANT = "http://qt.gtimg.cn/q="

    /**
     * 获取实时资金流向
     *
     * 0: 代码
       1: 主力流入
       2: 主力流出
       3: 主力净流入
       4: 主力净流入/资金流入流出总和
       5: 散户流入
       6: 散户流出
       7: 散户净流入
       8: 散户净流入/资金流入流出总和
       9: 资金流入流出总和1+2+5+6
      10: 未知
      11: 未知
      12: 名字
      13: 日期
     */
    val TECENT_CAPITAL = "http://qt.gtimg.cn/q="

    /**
     * 获取简要信息
     *
     *0: 未知
      1: 名字
      2: 代码
      3: 当前价格
      4: 涨跌
      5: 涨跌%
      6: 成交量（手）
      7: 成交额（万）
      8:
      9: 总市值
     */
    val TECENT_CUSTOM = "http://qt.gtimg.cn/q="
  }
}

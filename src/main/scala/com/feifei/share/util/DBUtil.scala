package com.feifei.share.util

import java.sql.{PreparedStatement, ResultSet, DriverManager, Connection};

/**
 * Created by Administrator on 2016/4/17.
 */
object DBUtil {

    private val user : String = "root"
    private val password : String = "root123"
    private val url : String = "jdbc:mysql://localhost:3306/share"
    val driver = "com.mysql.jdbc.Driver"

    def getConnection:Connection = {
        Class.forName(driver)
        DriverManager.getConnection(url, user, password)
    }

    def closeConnection(connection:Connection, statement: PreparedStatement, resultSet: ResultSet) {
        try{
            if(resultSet != null){
                resultSet.close()
            }
        }catch {
            case e:Exception => e.printStackTrace()
        }finally {
            try{
                if(statement != null){
                    statement.close()
                }
            }catch {
                case e:Exception => e.printStackTrace()
            }finally {
                if(connection != null) {
                    try {
                        connection.close()
                    }catch {
                        case e:Exception => e.printStackTrace()
                    }
                }
            }

        }

    }
}

import java.sql.{Connection, PreparedStatement}

/**
  *
  */

object SqlBox {

  case class SqlBoxRawValue(value:String)

  def createInsert(tb:String, map: Map[String, Any]):SqlBox = {
    val sqlBox = new SqlBox()
    val columns = new StringBuilder()
    val values = new StringBuilder()
    map.foreach((kv) => {
      columns ++= kv._1 ++= ","
      kv._2 match {
        case SqlBoxRawValue(val1:String) => {
          values ++= val1 ++= ","
        }
        case _ => {
          values ++= "?,"
          sqlBox.putParam(true, kv._2)
        }
      }
    })
    if (columns.length > 0) {
      columns.deleteCharAt(columns.length - 1)
    }
    if (values.length > 0) {
      values.deleteCharAt(values.length - 1)
    }
    val sql = s"insert into $tb ($columns) values ($values)".toString
    sqlBox.sql = sql
    return sqlBox
  }
}

class SqlBox {

  val _paramList = collection.mutable.ListBuffer[Any]()
  var _sql:String = null

  def putParam(condition:Boolean, params:Any*):Boolean = {
    if (condition) {
      params.foreach((param:Any) => {_paramList += param})
    }
    return condition
  }

  def putWhenContainsKey(params:Map[String, Any], keys:String*):Boolean = {
    var checkResult = true
    keys.foreach((key) => {
      if (!params.contains(key)) {
        checkResult = false
      }
    })
    if (checkResult) {
      keys.foreach((key) => {
        _paramList += params(key)
      })
      return true
    }else {
      return false
    }
  }

  def paramList:List[Any] = this._paramList.toList

  def sql_=(sql:String) = {
    _sql = sql
  }

  def sql = {
    _sql
  }

  def setStatementParams(stmt:PreparedStatement) = {
    (1 to _paramList.size).foreach((idx) => {stmt.setObject(idx, _paramList(idx-1))})
  }
}

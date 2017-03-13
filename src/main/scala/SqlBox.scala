import java.sql.{Connection, PreparedStatement}

/**
  *
  */
class SqlBox {

  val _paramList = collection.mutable.ListBuffer[Any]()
  var _sql:String = null

  def addParam(condition:Boolean, params:Any*):Boolean = {
    if (condition) {
      params.foreach((param:Any) => {_paramList += param})
    }
    return condition
  }

  def addIfContainsKey(params:Map[String, Any], keys:String*):Boolean = {
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

import SqlBox.SqlBoxRawValue
import org.scalatest.FunSuite

/**
  *
  */
class TestSqlBox extends FunSuite {

  test("test1") {
    val sqlBox = SqlBox.createInsert("table1", Map[String, Any]("a" -> 1, "b" -> SqlBoxRawValue("fdfda"), "c" -> "d"))
    println(sqlBox.sql)
    println(sqlBox.paramList)
  }
}

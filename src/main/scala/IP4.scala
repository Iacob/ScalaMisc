import org.apache.commons.lang3.math.NumberUtils

/**
  *
  */
object IP4 {

  def isValid(ipStr:String):Boolean = {
    if (ipStr == null) return false
    val items = ipStr.trim().split('.')
    if (items.length != 4) return false
    for (item:String <- items) {
      val itemValue = NumberUtils.toInt(item, -1)
      if (itemValue < 0) return false
      if (itemValue > 255) return false
    }
//    items.foreach((item) => {
//      val itemValue = NumberUtils.toInt(item, -1)
//      if (itemValue < 0) return false
//      if (itemValue > 255) return false
//    })
    return true
  }

  def toInt(ipStr:String):BigInt = {
    if (!isValid(ipStr)) return BigInt(-1)
    val items = ipStr.trim().split('.')
    val byte1:Int = NumberUtils.toInt(items(0))
    val byte2:Int = NumberUtils.toInt(items(1))
    val byte3:Int = NumberUtils.toInt(items(2))
    val byte4:Int = NumberUtils.toInt(items(3))

    var value1:BigInt = BigInt(0)
    value1 |= (BigInt(byte1) << 24)
    value1 |= (BigInt(byte2) << 16)
    value1 |= (BigInt(byte3) << 8)
    value1 |= BigInt(byte4)

    return value1
  }

  def toString(ip:BigDecimal):String = {
    val ipInt = ip.toBigInt()
    val byte1:BigInt = (ipInt & 0xff000000) >> 24
    val byte2:BigInt = (ipInt & 0xff0000) >> 16
    val byte3:BigInt = (ipInt & 0xff00) >> 8
    val byte4:BigInt = (ipInt & 0xff)

    val result = new StringBuilder()
    result ++= byte1.toString() ++= "." ++= byte2.toString() ++= "." ++= byte3.toString() ++= "." ++= byte4.toString()

    return result.toString()
  }
}

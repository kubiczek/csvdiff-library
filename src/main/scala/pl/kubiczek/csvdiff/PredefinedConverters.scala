package pl.kubiczek.csvdiff

trait PredefinedConverters {
  val stringConverter = Converter[String](s=>s)
  val intConverter = Converter[Int](_.toInt)
  val longConverter = Converter[Long](_.toLong)
  val floatConverter = Converter[Float](_.toFloat)
  val doubleConverter = Converter[Double](_.toDouble)
}

object PredefinedConverters extends PredefinedConverters

package pl.kubiczek.csvdiff

trait PredefinedConverters {
  val stringConverter = Converter[String](s=>s)
  val intConverter = Converter[Int](_.toInt)
  val longConverter = Converter[Long](_.toLong)
  val floatConverter = Converter[Float](_.toFloat)
  val doubleConverter = Converter[Double](_.toDouble)
}

object PredefinedConverters extends PredefinedConverters {
  def apply[T](clazz: Class[T]) = {
    if (clazz.getName() == classOf[String].getName()) stringConverter
    else if(clazz.getName() == classOf[Int].getName()) intConverter
    else if(clazz.getName() == classOf[Long].getName()) longConverter
    else if(clazz.getName() == classOf[Float].getName()) floatConverter
    else if(clazz.getName() == classOf[Double].getName()) doubleConverter
    else throw new IllegalArgumentException(
        "There is no predefined converter for this type: %s".format(clazz.getName()))
  }
}

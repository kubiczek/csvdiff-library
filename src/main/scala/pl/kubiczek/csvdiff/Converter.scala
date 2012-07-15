package pl.kubiczek.csvdiff

object Converter {
  def apply[T](f: String => T) = new Converter[T] {
    def convert(value: String) = f(value)
  }
}

trait Converter[T] { self =>
  def convert(value: String): T
}


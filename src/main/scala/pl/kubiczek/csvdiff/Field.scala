package pl.kubiczek.csvdiff

object Field {
  
  def apply[T](value: String, columnType: ColumnType) =
    columnType match {
      case String => new Field(value)
      case Integer => new Field(value.toInt)
      case Long => new Field(value.toLong)
      case Double => new Field(value.toDouble)
    }
}

class Field[T](value: T) {
  
  def getValue = this.value
  
  def ==(that: Field[T]) = this.getValue == that.getValue
}
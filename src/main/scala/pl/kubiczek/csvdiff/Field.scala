package pl.kubiczek.csvdiff

/**
 * Factory for [[pl.kubiczek.csvdiff.Field]] instances.
 * 
 * @author kubiczek
 */
object Field {
  /**
   * Creates field with a given value and type.
   * 
   * @param value string representation of the value.
   * @param type the type of the value. String representation of the value
   * must be converted to this type.
   * @return a new [[pl.kubiczek.csvdiff.Field]] instance.
   */
  def apply(value: String, columnType: ColumnType) =
    columnType match {
      case String => new Field(value)
      case Integer => new Field(value.toInt)
      case Long => new Field(value.toLong)
      case Double => new Field(value.toDouble)
    }
}

/**
 * This component represents a single value from input CSV file.
 */
class Field[T](value: T) {
  /**
   * Gets the value of this field.
   * @return the value of this field
   */
  def getValue = this.value
  /**
   * Compares this field with another field.
   * 
   * @param that the other field
   * @return true iff the fields are equal.
   */
  def ==(that: Field[T]) = this.getValue == that.getValue
}
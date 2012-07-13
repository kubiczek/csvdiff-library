package pl.kubiczek.csvdiff

import pl.kubiczek.csvdiff.PredefinedConverters._

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
   * @param converter TODO
   * @return a new [[pl.kubiczek.csvdiff.Field]] instance.
   */
  def apply[T](value: String, converter: Converter[T]) = new Field(converter.convert(value))
  
  //def apply[T](value: String) = new Field(value)
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
  def ==(that: Field[_]) = this.getValue == that.getValue
  
  override def toString = value.toString
}
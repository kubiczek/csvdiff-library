package pl.kubiczek.csvdiff

trait RowComponent { this: DiffResultComponent with ConfigurationComponent =>

  /**
   * This component represents a single line in input CSV file.
   *
   * @param rowNr row number in table
   * @param fields array with [[pl.kubiczek.csvdiff.Field]] values in this row
   * @param metadata array with [[pl.kubiczek.csvdiff.ColumnMetadata]] in this row
   *
   * @author kubiczek
   */
  class Row(rowNr: Int, fields: Array[_ <: Field[_]], metadata: Array[ColumnMetadata]) {

    def getField(i: Int) = this.fields(i)

    def getFields = this.fields

    def getRowNumber = this.rowNr

    def getMetadata(i: Int) = this.metadata(i)

    def length = this.fields.length

    def key = config.keyColumns.map(getField(_))

    def compare(that: Row) = {
      this.getFields.zip(that.getFields)
        .zipWithIndex // array of ((actualValue, expectedValue), columnNr)
        .filter(x => !config.unimportantColumns.contains(x._2)) //unimportant columns are filtered out
        .filter(x => x._1._1 != x._1._2) // array filtered by actualValue != expectedValue
        .map(x => NoMatchValue(this, that, x._2))
        .toList
    }

    override def toString = fields.map(_.toString()).reduce(_ + "," + _)
  }
}
package pl.kubiczek.csvdiff

/**
 * This component represents a single line in input CSV file.
 * 
 * rowNr row number in table
 * fields array with tokenized values for given row number
 * config the configuration of the cvsdiff framework
 * 
 * @author kubiczek
 */
class Row(rowNr: Int, fields: Array[_ <: Field[_]], metadata: Array[ColumnMetadata]) extends Configuration {

  def getField(i: Int) = this.fields(i)
  
  def getFields = this.fields
  
  def getRowNumber = this.rowNr
  
  def getMetadata(i: Int) = this.metadata(i)
  
  def length = this.fields.length
  
  def key = keyColumns.map(getField(_))
  
  def compare(that: Row) = {
    this.getFields.zip(that.getFields)
    	.zipWithIndex // array of ((actualValue, expectedValue), columnNr)
    	.filter(x => !unimportantColumns.contains(x._2)) //unimportant columns are filtered out
      	.filter(x => x._1._1 != x._1._2) // array filtered by actualValue != expectedValue
      	.map(x => NoMatchValue(this, that, x._2))
      	.toList
  }

}
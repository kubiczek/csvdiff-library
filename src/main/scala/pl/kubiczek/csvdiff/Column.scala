package pl.kubiczek.csvdiff

class Column(columnNr: Int, fields: Array[String], metadata: ColumnMetadata, config: Configuration) {

  def getField(i: Int) = this.fields(i)
  
  def getFields = this.fields
  
  def getColumnNumber = this.columnNr
  
  def getMetadata = this.metadata
  
  def length = this.fields.length

}
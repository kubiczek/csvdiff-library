package pl.kubiczek.csvdiff

class Column(columnNr: Int, fields: Array[String], config: Configuration) {

  def getField(i: Int) = this.fields(i)
  
  def getFields = this.fields
  
  def getColumnNumber = this.columnNr
  
  def length = this.fields.length

}
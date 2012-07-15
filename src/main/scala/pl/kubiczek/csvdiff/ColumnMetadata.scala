package pl.kubiczek.csvdiff

class ColumnMetadata(name: Option[String] = None, columnType: ColumnType) {
  
  def getName = this.name
  
  def getColumnType = this.columnType
}
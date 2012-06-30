package pl.kubiczek.csvdiff

abstract sealed class DiffResult {
  def toXml(): scala.xml.Elem
}
case class NoMatchValue(actual: Row, expected:Row, columnNr: Int) extends DiffResult {
  //override def toString = "" // TODO pretty-printing
  def toXml = <noMatchValue actualLine={actual.getRowNumber.toString} 
                  expectedLine={expected.getRowNumber.toString}
                  actualValue={actual.getField(columnNr)}
                  expectedValue={expected.getField(columnNr)}
                  columnNr={columnNr.toString} />
}
case class ExpectedRowNotExist(expected: Row) extends DiffResult {
  def toXml = <expectedRowNotExist expectedLine={expected.getRowNumber.toString} />
}
case class UnexpectedRow(actual: Row) extends DiffResult {
  def toXml = <unexpectedRow expectedLine={actual.getRowNumber.toString} />
}

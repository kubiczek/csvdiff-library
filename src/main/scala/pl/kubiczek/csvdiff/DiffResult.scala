package pl.kubiczek.csvdiff
import java.io.File

abstract sealed class DiffResult {
  def toXml(): scala.xml.Elem
}

case class NoMatchValue(actual: Row, expected:Row, columnNr: Int) extends DiffResult {
  
  override def toString = new StringBuffer()
            .append("Actual line %d > %s <\n")
            .append("Expected line %d > %s <\n")
            .append(" Difference in field %d\n")
            .append("  Actual   > %s <\n")
            .append("  Expected > %s <\n\n")
            .append("-----------------------------------------------\n").toString()
            .format(actual.getRowNumber, actual.getFields.toString(),
                expected.getRowNumber, expected.getFields.toString(),
                columnNr,
                actual.getField(columnNr),
                expected.getField(columnNr))
                
  def toXml = <noMatchValue actualLine={actual.getRowNumber.toString} 
                  expectedLine={expected.getRowNumber.toString}
                  actualValue={actual.getField(columnNr)}
                  expectedValue={expected.getField(columnNr)}
                  columnNr={columnNr.toString} />
}

case class ExpectedRowNotExist(expected: Row) extends DiffResult {
  
  override def toString = new StringBuffer()
            .append("Expected line %d does not exist > %s <\n\n")
            .append("-----------------------------------------------\n").toString()
            .format(expected.getRowNumber, expected.getFields.toString())
                
  def toXml = <expectedRowNotExist expectedLine={expected.getRowNumber.toString} />
}

case class UnexpectedRow(actual: Row) extends DiffResult {
  
  override def toString = new StringBuffer()
            .append("Actual line %d is not expected > %s <\n\n")
            .append("-----------------------------------------------\n").toString()
            .format(actual.getRowNumber, actual.getFields.toString())
            
  def toXml = <unexpectedRow expectedLine={actual.getRowNumber.toString} />
}

case class UniqueKeyViolation(file: File, key: List[String], rows: List[Row]) extends DiffResult {
  
  override def toString = new StringBuffer()
           .append("Unique key violation in file %s\n")
           .append("  lines with the same key > %s <\n")
           .append("    %s\n\n")
           .append("-----------------------------------------------\n").toString()
            .format(file.toString(),
                key.toString(),
                rows.toString())
  
  def toXml = <uniqueKeyViolation file={file.toString()}
                  key={key.toString()}
                  rows={rows.toString()} />
}

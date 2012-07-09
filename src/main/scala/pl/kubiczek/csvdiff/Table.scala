package pl.kubiczek.csvdiff
import java.io.File

/**
 * Factory for [[pl.kubiczek.csvdiff.Table]] instances.
 * 
 * @author kubiczek
 */
object Table {
  /**
   * Creates a new [[pl.kubiczek.csvdiff.Table]] instance by parsing input CSV file.
   * 
   * @param input file in CSV format.
   * @param config the configuration of the csvdiff framework.
   * @return a new [[pl.kubiczek.csvdiff.Table]] instance.
   */
  def apply(file: File, config: Configuration) = {
    val lines = scala.io.Source.fromFile(file).getLines.toArray
    // creates an array of column's meta-data
    val metadata = lines(0)
        .split(config.delimiter)
        .map(name => new ColumnMetadata(if(config.isColumnName) Some(name) else None))
    // creates an array of rows
    val rows = lines
          .map(_.split(config.delimiter))
          .zipWithIndex
          .map(x => new Row(x._2, x._1, metadata, config))
    // creates an array of columns
    val columns = (1 to  rows(0).length toArray)
          .map(columnNr => rows.map(_.getField(columnNr)))
          .zipWithIndex
          .map(x => new Column(x._2, x._1, metadata(x._2), config))
    // creates and returns a new instance of Table
    new Table(rows, columns, config)
  }
}

/**
 * This component is an data structure for representing input CSV file. Each
 * line of the file is represented by [[pl.kubiczek.csvdiff.Row]] instance.
 * 
 * @author kubiczek
 */
class Table(rows: Array[Row], columns: Array[Column], config: Configuration) {
  /**
   * Gets the single row from this table.
   * 
   * @param i number of the row.
   */
  def getRow(i: Int) = this.rows(i)
  /**
   * Gets all rows of this table.
   * 
   * @return an array of the rows.
   */
  def getRows = this.rows
  /**
   * Gets the single column from this table.
   * 
   * @param i number of the column.
   */
  def getColumn(i: Int) = this.columns(i)
  /**
   * Gets all columns of this table.
   * 
   * @return an array of the columns.
   */
  def getColumns = this.columns
  /**
   * The length of the table.
   * 
   * @return the number of rows in this table.
   */
  def length = this.rows.length
  /**
   * Compares this table with another table. Row-by-row (by default) 
   * or key-by-key (if key columns are defined) algorithm of comparison 
   * should be applied. This depends on `keyColumns` variable of [[pl.kubiczek.csvdiff.Configuration]].
   * 
   * @param that the other table.
   * @return a list of [[pl.kubiczek.csvdiff.DiffResult]] representing differences
   * in comparison. Empty list is returned if no differences found.
   */
  def compare(that: Table) = {
    if(config.keyColumns.isEmpty)
      this.compareRowByRow(that)
    else
      this.compareKeyByKey(that)
  }
  
  private def compareRowByRow(that: Table) = 
    createDiffResults(this.getRows.toList, that.getRows.toList)
  
  private def createDiffResults(actual: List[Row], expected: List[Row]): List[DiffResult] = 
    (actual, expected) match {
      case (x::xs, y::ys) => x.compare(y) ++ createDiffResults(xs, ys)
      case (Nil, ls@_::_) => ls.map(ExpectedRowNotExist(_))
      case (ls@_::_, Nil) => ls.map(UnexpectedRow(_))
      case (Nil, Nil) => Nil 
    }
    
  private def compareKeyByKey(that: Table): List[DiffResult] = {
    val thisMap = indexing(this.getRows)
    val thatMap = indexing(that.getRows)
    val violations = checkUniqueKeyViolation(thisMap, config.actualFile). // TODO or expected file
                       ++ (checkUniqueKeyViolation(thatMap, config.expectedFile))
                       
    if(violations.isEmpty){ // compare rows if keys are unique
      thisMap.keys.flatMap(key => thatMap.get(key) match {
          case Some(rows) => thisMap(key)(0).compare(rows(0)).toList
          case None => List(UnexpectedRow(thisMap(key)(0))) // TODO or ExpectedRowNotExist
        }
      ).toList            
    } else { // otherwise returns list of unique key violations
      violations.toList  
    }

  }
  
  private def indexing(rows: Array[Row]) = {
    rows.groupBy(row => config.keyColumns.map(row.getField(_)))
  }
  
  private def checkUniqueKeyViolation(rowsByKey: Map[List[String], Array[Row]], file: File) =
    for((key, value) <- rowsByKey if value.length > 1) yield UniqueKeyViolation(file, key, value.toList)
}
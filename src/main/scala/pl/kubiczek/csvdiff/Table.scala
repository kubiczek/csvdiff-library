package pl.kubiczek.csvdiff

/**
 * This component is an data structure for representing input CSV file. Each
 * line of the file is represented by [[pl.kubiczek.csvdiff.Row]] instance.
 * 
 * @author kubiczek
 */
class Table(rows: Array[Row], config: Configuration) {
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
    // TODO handling of unique key violation, i.e. thisMap(key).length > 1
  
    thisMap.keys.flatMap(key => thatMap.get(key) match {
        case Some(rows) => thisMap(key)(0).compare(rows(0)).toList
        case None => List(UnexpectedRow(thisMap(key)(0))) // TODO or ExpectedRowNotExist
      }
    ).toList
  }
  
  private def indexing(rows: Array[Row]) = {
    rows.groupBy(row => config.keyColumns.map(row.getField(_)))
  }

}
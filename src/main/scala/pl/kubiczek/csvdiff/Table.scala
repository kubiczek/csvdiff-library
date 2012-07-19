package pl.kubiczek.csvdiff
import java.io.File
import com.codahale.logula.Logging

trait TableComponent { this: RowComponent with DiffResultComponent with ConfigurationComponent =>

  /**
   * Factory for [[pl.kubiczek.csvdiff.Table]] instances.
   *
   * @author kubiczek
   */
  object Table extends Logging {
    /**
     * Creates a new [[pl.kubiczek.csvdiff.Table]] instance by parsing input CSV file.
     *
     * @param file input file in CSV format.
     * @return a new [[pl.kubiczek.csvdiff.Table]] instance.
     */
    def apply(file: File) = {
      log.info("Create Table instance for file %s", file)
      val lines = scala.io.Source.fromFile(file).getLines.toArray
      // create an array of column's meta-data
      val metadata = lines(0)
        .split(config.delimiter)
        .map(name => new ColumnMetadata(if (config.isColumnName) Some(name) else None, pl.kubiczek.csvdiff.String)) // TODO column type configuration
      log.info("Create an array of column's meta-data: %s", metadata)
      // create an array of rows
      val rows = lines
        .map(_.split(config.delimiter))
        .map(_.map(Field(_, classOf[String]))) // TODO support for other types of field, converters needed
        .zipWithIndex
        .map(x => new Row(x._2, x._1, metadata))
      log.info("Create an array of rows: %s", rows.map(_.toString).reduce(_ + "," + _))
      // create an array of columns
      val columns = (1 to rows(0).length - 1 toArray)
        .map(columnNr => rows.map(_.getField(columnNr)))
        .zipWithIndex
        .map(x => new Column(x._2, x._1, metadata(x._2)))
      log.info("Create an array of columns: %s", columns)
      // create and returns a new instance of Table
      new Table(rows, columns)
    }
  }

  /**
   * This component is an data structure for representing input CSV file. Each
   * line of the file is represented by [[pl.kubiczek.csvdiff.Row]] instance.
   *
   * @author kubiczek
   */
  class Table(rows: Array[Row], columns: Array[Column]) extends Configuration {
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
      if (keyColumns.isEmpty)
        this.compareRowByRow(that)
      else
        this.compareKeyByKey(that)
    }

    private def compareRowByRow(that: Table) =
      createDiffResults(this.getRows.toList, that.getRows.toList)

    private def createDiffResults(actual: List[Row], expected: List[Row]): List[DiffResult] =
      (actual, expected) match {
        case (x :: xs, y :: ys) => x.compare(y) ++ createDiffResults(xs, ys)
        case (Nil, ls @ _ :: _) => ls.map(ExpectedRowNotExist(_))
        case (ls @ _ :: _, Nil) => ls.map(UnexpectedRow(_))
        case (Nil, Nil) => Nil
      }

    private def compareKeyByKey(that: Table): List[DiffResult] = {
      val thisMap = indexing(this.getRows)
      val thatMap = indexing(that.getRows)
      val violations = checkUniqueKeyViolation(thisMap, actualFile). // TODO or expected file
        ++(checkUniqueKeyViolation(thatMap, expectedFile))

      if (violations.isEmpty) { // compare rows if keys are unique
        thisMap.keys.flatMap(key => thatMap.get(key) match {
          case Some(rows) => thisMap(key)(0).compare(rows(0)).toList
          case None => List(UnexpectedRow(thisMap(key)(0))) // TODO or ExpectedRowNotExist
        }).toList
      } else { // otherwise returns list of unique key violations
        violations.toList
      }

    }

    private def indexing(rows: Array[Row]) = {
      rows.groupBy(row => keyColumns.map(row.getField(_)))
    }

    private def checkUniqueKeyViolation(rowsByKey: Map[List[Field[_]], Array[Row]], file: File) =
      for ((key, value) <- rowsByKey if value.length > 1) yield UniqueKeyViolation(file, key, value.toList)
  }

}
package pl.kubiczek.csvdiff

import java.io._

/**
 * This component provides parsers for input files.
 * 
 * @author kubiczek
 */
class CsvParser(config: Configuration) {
  
  /**
   * Parses the input files specified by [[pl.kubiczek.csvdiff.Configuration]].
   * 
   * @return a pair of [[pl.kubiczek.csvdiff.Table]] instances representing 
   * actual and expected CSV files (see [[pl.kubiczek.csvdiff.Configuration]]).
   */
  def parse() = {
	(createTable(config.actualFile), createTable(config.expectedFile))
  }
  
  /**
   * Creates a new [[pl.kubiczek.csvdiff.Table]] instance by parsing input CSV file.
   * 
   * @param input file in CSV format.
   * @return a new [[pl.kubiczek.csvdiff.Table]] instance.
   */
  private def createTable(file: File) = {
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
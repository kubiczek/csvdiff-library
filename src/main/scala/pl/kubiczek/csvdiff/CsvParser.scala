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
    new Table(scala.io.Source.fromFile(file)
				.getLines
				.map(_.split(config.delimiter))
				.toArray
				.zipWithIndex
				.map(x => new Row(x._2, x._1, config)),
			  config)
  }

}
package pl.kubiczek.csvdiff

/**
 * This component provides parsers for input files.
 * 
 * @author kubiczek
 */
class CsvParser extends Configuration {
  /**
   * Parses the input files specified by [[pl.kubiczek.csvdiff.Configuration]].
   * 
   * @return a pair of [[pl.kubiczek.csvdiff.Table]] instances representing 
   * actual and expected CSV files (see [[pl.kubiczek.csvdiff.Configuration]]).
   */
  def parse() = {
	(Table(actualFile), Table(expectedFile))
  }
}
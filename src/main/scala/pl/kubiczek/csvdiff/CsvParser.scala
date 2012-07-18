package pl.kubiczek.csvdiff

/**
 * This component provides parsers for input files.
 * 
 * @author kubiczek
 */
trait CsvParserComponent { this: ConfigurationComponent with TableComponent =>

class CsvParser {
  /**
   * Parses the input files specified by [[pl.kubiczek.csvdiff.Configuration]].
   * 
   * @return a pair of [[pl.kubiczek.csvdiff.Table]] instances representing 
   * actual and expected CSV files (see [[pl.kubiczek.csvdiff.Configuration]]).
   */
  def parse() = {
//    object Table extends TableComponent with ConfigurationComponent {
//      val actual = Table(config.actualFile)
//      val expected = Table(config.expectedFile)
//    }
    (Table(config.actualFile), Table(config.expectedFile))
  }
}

}
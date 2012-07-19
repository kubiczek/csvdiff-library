package pl.kubiczek.csvdiff

import com.codahale.logula.Logging

trait CsvParserComponent { this: ConfigurationComponent with TableComponent =>
  /**
   * This component provides parsers for input files.
   *
   * @author kubiczek
   */
  class CsvParser extends Logging {
    /**
     * Parses the input files specified by [[pl.kubiczek.csvdiff.Configuration]].
     *
     * @return a pair of [[pl.kubiczek.csvdiff.Table]] instances representing
     * actual and expected CSV files (see [[pl.kubiczek.csvdiff.Configuration]]).
     */
    def parse() = {
      log.info("Parsing input files: %s and %s", config.actualFile, config.expectedFile)
      (Table(config.actualFile), Table(config.expectedFile))
    }
  }
}
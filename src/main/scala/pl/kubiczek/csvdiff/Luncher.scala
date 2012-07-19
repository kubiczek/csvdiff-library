package pl.kubiczek.csvdiff

import org.apache.log4j.Level

import com.codahale.logula.Logging

trait LuncherComponent { this: CsvParserComponent with TableComponent with ConfigurationComponent =>

  val luncher: Luncher

  /**
   * This class is used to lunch the csvdiff framework. Client code should
   * call run() method.
   *
   * @author kubiczek
   */
  class Luncher(parser: CsvParser) extends Logging {
    /**
     * Runs csvdiff framework.
     *
     * @return a list of [[pl.kubiczek.csvdiff.DiffResult]] instances representing
     * differences in compared files.
     */
    def run() = {
      log.info("CsvDiff is running...")
      val (actualTable, expectedTable) = parser.parse()
      actualTable.compare(expectedTable)
    }
  }
}

object CsvDiff extends LuncherComponent
  with TableComponent
  with RowComponent
  with CsvParserComponent
  with DiffResultComponent
  with ConfigurationComponent {

  val config = new Configuration
  val luncher = new Luncher(new CsvParser)

  Logging.configure { log =>
    log.registerWithJMX = true

    log.level = Level.TRACE

    log.loggers("com.codahale.logula.examples.SilencedRunner") = Level.OFF

    log.console.enabled = true
    log.console.threshold = Level.ALL

    log.file.enabled = true
    log.file.filename = "./logs/example-logging-run.log"
    log.file.threshold = Level.ALL

    log.syslog.enabled = true
    log.syslog.host = "localhost"
    log.syslog.facility = "LOCAL7"
  }
}
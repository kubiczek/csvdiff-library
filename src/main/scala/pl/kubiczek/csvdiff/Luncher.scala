package pl.kubiczek.csvdiff

///**
// * Factory for [[pl.kubiczek.csvdiff.Luncher]] instances.
// * 
// * @author kubiczek
// */
//object Luncher {
//  /**
//   * Creates luncher instance.
//   * 
//   * @return a new [[pl.kubiczek.csvdiff.Luncher]] instance.
//   */
//  def apply() = new Luncher(new CsvParser)
//}

/**
 * This class is used to lunch the csvdiff framework. Client code should 
 * call run() method.
 * 
 * @author kubiczek
 */

trait LuncherComponent { this: CsvParserComponent with TableComponent with ConfigurationComponent =>

  val luncher: Luncher
  
class Luncher(parser: CsvParser) {
  /**
   * Runs csvdiff framework.
   * 
   * @return a list of [[pl.kubiczek.csvdiff.DiffResult]] instances representing
   * differences in compared files.
   */
  def run() = {
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
}
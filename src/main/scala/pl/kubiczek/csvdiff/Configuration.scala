package pl.kubiczek.csvdiff
import java.io.File

/**
 * A configuration used by the csvdiff framework.
 * 
 * Configuration instance specify all user-configurable parameters which
 * are used at runtime by the csvdiff framework.
 * 
 * @author kubiczek
 */
trait Configuration {
  /**
   * Character used to separate values in CSV files. By default the values are
   * comma-separated.
   */
  var delimiter = ","
  /**
   * Input file in CSV format used for comparison, the file contains
   * actual values.
   */
  var actualFile = new File("actual.csv")
  /**
   * Input file in CSV format used for comparison, the file contains 
   * expected values.
   */
  var expectedFile = new File("expected.csv")
  /**
   * Set of column numbers which are skipped during comparison. By default the set
   * is empty, i.e. each column is involved in comparison.
   */
  var unimportantColumns: Set[Int] = Set()
  /**
   * List of column numbers which are a primary key. Only rows with equal primary keys
   * should be compared. By default the list is empty, i.e. no primary key is defined
   * and row-by-row algorithm of comparison shall be applied.
   */
  var keyColumns: List[Int] = List()
  /**
   * Flag indicating if the first line of input CSV file contains names of the columns.
   * By default the flag is set to false.
   */
  var isColumnName = false
  // TODO column type
  var columnTypes: Option[Array[Class[_]]] = None
}

/**
 * Client can use this component to access configuration through imports (see Scala's
 * Selfless Trait Pattern). For mixin composition use Configuration trait.
 */
object Configuration extends Configuration
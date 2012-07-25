package pl.kubiczek.csvdiff
import org.scalatest.mock.EasyMockSugar

trait TestingEnvironment extends LuncherComponent
  with TableComponent
  with DiffResultComponent
  with RowComponent
  with CsvParserComponent
  with ConfigurationComponent
  with EasyMockSugar {

  val config = mock[Configuration]
  val luncher = mock[Luncher]
}
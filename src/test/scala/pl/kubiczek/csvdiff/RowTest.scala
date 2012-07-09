package pl.kubiczek.csvdiff

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite

@RunWith(classOf[JUnitRunner])
class RowTest extends FunSuite {
  
  test("test method getField") {
    var config = new Configuration
    val fields = Array("f1", "f2", "f3")
    val metadata = Array(new ColumnMetadata, new ColumnMetadata, new ColumnMetadata)
    var row = new Row(10, fields, metadata, config)
    
    assert(row.getField(0) === "f1")
    assert(row.getField(1) === "f2")
    assert(row.getField(2) === "f3")
  }

}
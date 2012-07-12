package pl.kubiczek.csvdiff

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.scalatest.mock.EasyMockSugar

@RunWith(classOf[JUnitRunner])
class RowTest extends FunSuite with EasyMockSugar {
  
  test("getField is called on row") {
    val row = new Row(10, Array("f1", "f2", "f3"), 
         Array(mock[ColumnMetadata], mock[ColumnMetadata], mock[ColumnMetadata]))
    
    assert(row.getField(0) === "f1")
    assert(row.getField(1) === "f2")
    assert(row.getField(2) === "f3")
  }

}
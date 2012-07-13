package pl.kubiczek.csvdiff

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.scalatest.mock.EasyMockSugar

@RunWith(classOf[JUnitRunner])
class RowTest extends FunSuite with EasyMockSugar {
  
  test("getField is called on row") {
    val row = new Row(10, Array(Field("f1"), Field("f2"), Field("f3")), 
         Array(mock[ColumnMetadata], mock[ColumnMetadata], mock[ColumnMetadata]))
    
    assert(row.getField(0) == Field("f1"))
    assert(row.getField(1) == Field("f2"))
    assert(row.getField(2).getValue === "f3")
  }

}
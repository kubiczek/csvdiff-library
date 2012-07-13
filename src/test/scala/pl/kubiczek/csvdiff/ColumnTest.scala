package pl.kubiczek.csvdiff

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.scalatest.mock.EasyMockSugar

@RunWith(classOf[JUnitRunner])
class ColumnTest extends FunSuite with EasyMockSugar {
  
  test("getField is called on column") {
    val column = new Column(5, Array(Field("a"), Field("5", _.toInt), Field("6.1", _.toDouble)), mock[ColumnMetadata])
    
    assert(column.getField(0) == Field("a"))
    assert(column.getField(1) == Field("5", _.toInt))
    assert(column.getField(2) == Field("6.1", _.toDouble))
    assert(column.length === 3)
  }
  
  test("getField is called on column with index out of bound") {
    val column = new Column(5, Array(Field("a"), Field("b"), Field("c")), mock[ColumnMetadata])
    
    intercept[ArrayIndexOutOfBoundsException] {
    	column.getField(3)      
    }
    assert(column.length === 3)
  }
  
  test("getFields is called on column") {
    val column = new Column(6, Array(Field("a"), Field("b")), mock[ColumnMetadata])
    val fields = column.getFields 
    assert(fields(0).getValue === "a")
    assert(fields(1).getValue === "b")
    assert(column.length === 2)
  }
  
  test("getColumnNumber is called on column") {
    val column = new Column(7, Array(Field("a")), mock[ColumnMetadata])
    
    assert(column.getColumnNumber === 7)
    assert(column.length === 1)
  }
  
  test("getMetadata is called on column") {
    val metadata = mock[ColumnMetadata]
    val column = new Column(7, Array(Field("a")), metadata)

    assert(column.getMetadata === metadata)
    assert(column.length === 1)
  }

}
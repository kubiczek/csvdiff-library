package pl.kubiczek.csvdiff

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.scalatest.mock.EasyMockSugar

@RunWith(classOf[JUnitRunner])
class ColumnTest extends FunSuite with EasyMockSugar {
  
  test("getField is called on column") {
    val column = new Column(5, Array("a", "b", "c"), mock[ColumnMetadata], mock[Configuration])
    
    assert(column.getField(0) === "a")
    assert(column.getField(1) === "b")
    assert(column.getField(2) === "c")
    assert(column.length === 3)
  }
  
  test("getField is called on column with index out of bound") {
    val column = new Column(5, Array("a", "b", "c"), mock[ColumnMetadata], mock[Configuration])
    
    intercept[ArrayIndexOutOfBoundsException] {
    	column.getField(3)      
    }
    assert(column.length === 3)
  }
  
  test("getFields is called on column") {
    val column = new Column(6, Array("a", "b"), mock[ColumnMetadata], mock[Configuration])
    
    assert(column.getFields === Array("a", "b"))
    assert(column.length === 2)
  }
  
  test("getColumnNumber is called on column") {
    val column = new Column(7, Array("a"), mock[ColumnMetadata], mock[Configuration])
    
    assert(column.getColumnNumber === 7)
    assert(column.length === 1)
  }
  
  test("getMetadata is called on column") {
    val metadata = mock[ColumnMetadata]
    val column = new Column(7, Array("a"), metadata, mock[Configuration])

    assert(column.getMetadata === metadata)
    assert(column.length === 1)
  }

}
package pl.kubiczek.csvdiff

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.scalatest.mock.EasyMockSugar
import org.scalatest.BeforeAndAfter

@RunWith(classOf[JUnitRunner])
class RowTest extends FunSuite with EasyMockSugar {
  
  val metadataArray = Array(mock[ColumnMetadata], mock[ColumnMetadata], mock[ColumnMetadata])
  var row = new Row(10, Array(new Field("f1"), new Field("f2"), new Field("f3")), metadataArray)
  
  
  test("getField is called on row") {
    assert(row.getField(0) == new Field("f1"))
    assert(row.getField(1) == new Field("f2"))
    assert(row.getField(2).getValue === "f3")
    
  }
  
  test("getFields is called on row") {
    assert(row.getFields === Array(new Field("f1"), new Field("f2"), new Field("f3")))
    assert(row.length === 3)
  }
  
  test("getField is called on row with index out of bounds") {
    intercept[ArrayIndexOutOfBoundsException] {
        row.getField(4)
    }
    assert(row.length === 3)
  }
  
  test("getRowNumber is called on row") {
    assert(row.getRowNumber === 10)
    assert(row.length === 3)
  }
  
  test("getMetadata is called on row") {
    assert(row.getMetadata(1) === metadataArray(1))
    assert(row.length === 3)
  }
  
  test("getMetadata is called on row with index out of bounds") {
    intercept[ArrayIndexOutOfBoundsException] {
        row.getMetadata(3)
    }
    assert(row.length === 3)
  }
  
  /*
  test("compare") {
    val metadataArray = Array(mock[ColumnMetadata], mock[ColumnMetadata], mock[ColumnMetadata])
    val row = new Row(10, Array("f1", "f2", "f3"), metadataArray, mock[Configuration])
    val row2 = new Row(9, Array("f1", "f2", "f3"), metadataArray, mock[Configuration])
    
    assert(row.compare(row2) === List)
  }*/
}
package pl.kubiczek.csvdiff

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite

@RunWith(classOf[JUnitRunner])
class RowTest extends FunSuite with TestingEnvironment {

  val metadataArray = Array(mock[ColumnMetadata], mock[ColumnMetadata], mock[ColumnMetadata])
  val fields = Array(new Field("f1"), new Field("f2"), new Field("f3"))
  var row = new Row(10, fields, metadataArray)

  test("getField is called on row") {
    assert(row.getField(0) == new Field("f1"))
    assert(row.getField(1) == new Field("f2"))
    assert(row.getField(2).getValue === "f3")
  }

  test("getFields is called on row") {
    assert(row.getFields === fields)
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

//  test("compare in case rows are identical") {
//    val row2 = row
//    val result = row compare row2
//
//    assert(result.length === 0)
//    assert(result === List())
//  }
//
//  test("compare in case rows are not identical (1 difference)") {
//    val fields = Array(new Field("f1"), new Field("f2"), new Field("f13"))
//    val row2 = new Row(10, fields, metadataArray)
//    val result = row compare row2
//    assert(result.length === 1)
//    result(0) match { // result.head
//      case NoMatchValue(actual, expected, columnNr) =>
//        assert(columnNr === 2)
//        assert(actual === row)
//        assert(expected === row2)
//    }
//  }
//
//  test("compare in case rows are not identical (2 differences)") {
//    val fields = Array(new Field("f1"), new Field("f12"), new Field("f13"))
//    val row2 = new Row(10, fields, metadataArray)
//    val result = row compare row2
//    assert(result.length === 2)
//    result.head match { // result(0)
//      case NoMatchValue(actual, expected, columnNr) =>
//        assert(columnNr === 1)
//        assert(actual === row)
//        assert(expected === row2)
//    }
//    result(1) match {
//      case NoMatchValue(actual, expected, columnNr) =>
//        assert(columnNr === 2)
//        assert(actual === row)
//        assert(expected === row2)
//    }
//  }
}
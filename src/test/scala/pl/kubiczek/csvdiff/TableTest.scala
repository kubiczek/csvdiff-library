package pl.kubiczek.csvdiff

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.scalatest.mock.EasyMockSugar

@RunWith(classOf[JUnitRunner])
class TableTest extends FunSuite with EasyMockSugar {
  
  val metadataArray = Array(mock[ColumnMetadata], mock[ColumnMetadata], mock[ColumnMetadata])
  
  val row0 = new Row(0, Array(new Field("r0c0"), new Field("r0c1"), new Field("r0c2")), metadataArray)
  val row1 = new Row(1, Array(new Field("r1c0"), new Field("r1c1"), new Field("r1c2")), metadataArray)
  val row2 = new Row(2, Array(new Field("r2c0"), new Field("r2c1"), new Field("r2c2")), metadataArray)
  
  val column0 = new Column(0, Array(new Field("r0c0"), new Field("r1c0"), new Field("r2c0")), mock[ColumnMetadata])
  val column1 = new Column(1, Array(new Field("r0c1"), new Field("r1c1"), new Field("r2c1")), mock[ColumnMetadata])
  val column2 = new Column(2, Array(new Field("r0c2"), new Field("r1c2"), new Field("r2c2")), mock[ColumnMetadata])
 
  val table = new Table(Array(row0, row1, row2), 
          Array(column0, column1, column2))
  
  
  test("getRow is called on table") {
    assert(table.getRow(0) === row0)
    assert(table.getRow(1) === row1)
    assert(table.getRow(2) === row2)
    assert(table.length === 3)
  }
  
  test("getRows is called on table") {
    assert(table.getRows === Array(row0, row1, row2))
  }
 
  test("getColumn is called on table") {
    assert(table.getColumn(0) === column0)
    assert(table.getColumn(1) === column1)
    assert(table.getColumn(2) === column2)
    assert(table.length === 3)
  }
  
  test("getColumns is called on table") {
    assert(table.getColumns === Array(column0, column1, column2))
  }
  
  
  test("compare tables with equal fields") {
    val result = table compare table
    
    assert(table.length === 3)
    assert(result.length === 0)
    assert(result === List())
  }
  
  test("compare tables - in 1 row 1 different column") {
    var row0Diff = new Row(0, Array(new Field("r0c0"), new Field("r0c1"), new Field("r0c2diff")), metadataArray)
    var column2Diff = new Column(2, Array(new Field("r0c2diff"), new Field("r1c2"), new Field("r2c2")), mock[ColumnMetadata])

    var table2 = new Table(Array(row0Diff, row1, row2), 
          Array(column0, column1, column2Diff))
    
    assert(table.length === 3)
    assert(table2.length === 3)
    
    val result = table compare table2
    
    assert(result.length === 1)
    result(0) match { // result.head
      case NoMatchValue (actual, expected, columnNr) => 
        assert(columnNr === 2)
        assert(actual === row0)
        assert(expected === row0Diff)
    }
  }
   
  test("compare tables - in 1 row 2 different columns") {
    var row0Diff = new Row(0, Array(new Field("r0c0diff"), new Field("r0c1"), new Field("r0c2diff")), metadataArray)
    var column0Diff = new Column(2, Array(new Field("r0c0diff"), new Field("r1c2"), new Field("r2c2")), mock[ColumnMetadata])
    var column2Diff = new Column(2, Array(new Field("r0c2diff"), new Field("r1c2"), new Field("r2c2")), mock[ColumnMetadata])

    var table2 = new Table(Array(row0Diff, row1, row2), 
          Array(column0Diff, column1, column2Diff))
    
    assert(table.length === 3)
    assert(table2.length === 3)
    
    val result = table compare table2
    
    assert(result.length === 2)
    result(0) match { // result.head
      case NoMatchValue (actual, expected, columnNr) => 
        assert(columnNr === 0)
        assert(actual === row0)
        assert(expected === row0Diff)
    } 
    result(1) match {
      case NoMatchValue (actual, expected, columnNr) => 
        assert(columnNr === 2)
        assert(actual === row0)
        assert(expected === row0Diff)
    }
  }
  
  test("compare tables - in 2 rows 1 different column") {
    var row0Diff = new Row(0, Array(new Field("r0c0"), new Field("r0c1"), new Field("r0c2diff")), metadataArray)
    var row2Diff = new Row(0, Array(new Field("r2c0"), new Field("r2c1"), new Field("r2c2diff")), metadataArray)
    var column2Diff = new Column(2, Array(new Field("r0c2diff"), new Field("r1c2"), new Field("r2c2diff")), mock[ColumnMetadata])

    var table2 = new Table(Array(row0Diff, row1, row2Diff), 
          Array(column0, column1, column2Diff))
    
    assert(table.length === 3)
    assert(table2.length === 3)
    
    val result = table compare table2
    
    assert(result.length === 2)
    result(0) match { // result.head
      case NoMatchValue (actual, expected, columnNr) => 
        assert(columnNr === 2)
        assert(actual === row0)
        assert(expected === row0Diff)
    }
    result(1) match {
      case NoMatchValue (actual, expected, columnNr) => 
        assert(columnNr === 2)
        assert(actual === row2)
        assert(expected === row2Diff)
    }
  }
  
  test("compare tables - in second table 1 row less - UnexpecteRow") {
    var table2 = new Table(Array(row0, row1), 
          Array(column0, column1, column2))
    
    assert(table.length === 3)
    assert(table2.length === 2)
    
    val result = table compare table2
    
    assert(result.length === 1)
    result(0) match { // result.head
      case UnexpectedRow (expected) => 
        assert(expected === row2)
    }
  }
  
  test("compare tables - in second table 1 row more - ExpectedRowNotExist") {
    val row3 = new Row(2, Array(new Field("r3c0"), new Field("r3c1"), new Field("r3c2")), metadataArray)
    
    val c0 = new Column(0, Array(new Field("r0c0"), new Field("r1c0"), new Field("r2c0"), new Field("r3c0")), mock[ColumnMetadata])
    val c1 = new Column(1, Array(new Field("r0c1"), new Field("r1c1"), new Field("r2c1"), new Field("r3c1")), mock[ColumnMetadata])
    val c2 = new Column(2, Array(new Field("r0c2"), new Field("r1c2"), new Field("r2c2"), new Field("r3c2")), mock[ColumnMetadata])
    
    var table2 = new Table(Array(row0, row1, row2, row3), 
          Array(c0, c1, c2))
    
    assert(table.length === 3)
    assert(table2.length === 4)
    
    val result = table compare table2
    
    assert(result.length === 1)
    result(0) match { // result.head
      case ExpectedRowNotExist (expected) => 
        assert(expected === row3)
    }
  }
  
  //todo UniqueKeyViolation
}
package pl.kubiczek.csvdiff

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite

@RunWith(classOf[JUnitRunner])
class TableTest extends FunSuite with TestingEnvironment {

  val metadataArray = Array(mock[ColumnMetadata], mock[ColumnMetadata], mock[ColumnMetadata])

  var row0 = new Row(0, Array(new Field("r0c0"), new Field("r0c1"), new Field("r0c2")), metadataArray)
  var row1 = new Row(1, Array(new Field("r1c0"), new Field("r1c1"), new Field("r1c2")), metadataArray)
  var row2 = new Row(2, Array(new Field("r2c0"), new Field("r2c1"), new Field("r2c2")), metadataArray)

  var column0 = new Column(0, Array(new Field("r0c0"), new Field("r1c0"), new Field("r2c0")), mock[ColumnMetadata])
  var column1 = new Column(1, Array(new Field("r0c1"), new Field("r1c1"), new Field("r2c1")), mock[ColumnMetadata])
  var column2 = new Column(2, Array(new Field("r0c2"), new Field("r1c2"), new Field("r2c2")), mock[ColumnMetadata])

  var table = new Table(Array(row0, row1, row2),
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

  //todo test compare method
}
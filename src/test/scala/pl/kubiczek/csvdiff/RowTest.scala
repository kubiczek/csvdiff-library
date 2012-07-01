package pl.kubiczek.csvdiff

import org.scalatest.FunSuite

class RowTest extends FunSuite {
  
  test("test method getField") {
    var config = new Configuration
    val fields = new Array[String] (3)
    fields(0) = "f1"
    fields(1) = "f2"
    fields(2) = "f3"
    var row = new Row(10, fields, config)
    
    assert(row.getField(0) === "f1")
    assert(row.getField(0) === "f2")
    assert(row.getField(0) === "f3")
  }

}
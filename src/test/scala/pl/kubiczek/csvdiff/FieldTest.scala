package pl.kubiczek.csvdiff

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.scalatest.mock.EasyMockSugar

@RunWith(classOf[JUnitRunner])
class FieldTest extends FunSuite with EasyMockSugar {
  
  test("factory method is called to create field with value of string type") {
    val x = Field("test", String);    
    assert(x.getValue === "test")
  }
  
  test("factory method is called to create field with value of integer type") {
    val x = Field("1234", Integer);    
    assert(x.getValue === 1234)
  }
  
  test("factory method is called to create field with value of long type") {
    val x = Field("123412341234", Long);    
    assert(x.getValue === 123412341234L)
  }
  
  test("factory method is called to create field with value of double type") {
    val x = Field("3.14", Double);    
    assert(x.getValue === 3.14)
  }
  
  test("factory method is called with illegal format of integer number") {   
    intercept[NumberFormatException] {
    	val x = Field("+1234", Integer);      
    }
    intercept[NumberFormatException] {
    	val x = Field("1234.0", Integer);      
    }
  }
  
  test("== is called on fields") {
    val x = new Field(5)
    val y = new Field(5)
    val z = new Field(20)
    val t = new Field("a")
    
    assert((x == y) === true)
    assert((x == z) === false)
    assert((x == t) === false)
    assert((t == t) === true)
  }
}
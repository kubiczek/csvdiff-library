package pl.kubiczek.csvdiff

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.scalatest.mock.EasyMockSugar

@RunWith(classOf[JUnitRunner])
class PredefinedConvertersTest extends FunSuite with EasyMockSugar {

  test("string converter") {
    assert(PredefinedConverters(classOf[String]).convert("test") === "test")
  }
  
  test("int converter") {
    assert(PredefinedConverters(classOf[Int]).convert("123") === 123)
  }
  
  test("long converter") {
    assert(PredefinedConverters(classOf[Long]).convert("123412341234") === 123412341234L)
  }
  
  test("float converter") {
    assert(PredefinedConverters(classOf[Float]).convert("3.14") === 3.14f)
  }
  
  test("double converter") {
    assert(PredefinedConverters(classOf[Double]).convert("1.23456") === 1.23456)
  }
  
  test("different types equality") {
    assert(PredefinedConverters(classOf[Long]).convert("3") === 
    			PredefinedConverters(classOf[Float]).convert("3.00"))
    assert(3L === 3.00f)
    
    assert(PredefinedConverters(classOf[Int]).convert("5340000") === 
    			PredefinedConverters(classOf[Long]).convert("5340000"))
    assert(534000 === 534000L)
  }
  
  test("unsupported converter") {
    intercept[IllegalArgumentException] {
      PredefinedConverters(classOf[Object])
    }
  }
  
}
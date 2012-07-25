package pl.kubiczek.csvdiff

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import java.io.File
import java.io.PrintWriter

@RunWith(classOf[JUnitRunner])
class LuncherIntegrationTest extends FunSuite {

  def autoFile[A](content: String = "")(filename: String)(body: File => A) = {
    val f = File.createTempFile(filename, ".csv")
    try {
      if (content != "") {
        val out = new PrintWriter(f)
        out println content
        out close
      }
      body(f)
    } finally {
      f delete
    }
  }

  test("compare two identical files by using default config") {
    val actualContent =
      """
      FIRST_NAME,SECOND_NAME,PROFESSION
      """
    val expectedContent =
      """
      FIRST_NAME,SECOND_NAME,PROFESSION
      """

    autoFile(actualContent)("actual-test") { f1 =>
      autoFile(expectedContent)("expected-test") { f2 =>
        CsvDiff.config.actualFile = f1
        CsvDiff.config.expectedFile = f2
        val result = CsvDiff.luncher.run()
        assert(result === List())
      }
    }
  }

  test("compare two different files by using default config") {
    val actualContent =
      """
      FIRST_NAME,SECOND,PROFESSION
      """
    val expectedContent =
      """
      FIRST_NAME,SECOND_NAME,PROFESSION
      """

    autoFile(actualContent)("actual-test") { f1 =>
      autoFile(expectedContent)("expected-test") { f2 =>
        CsvDiff.config.actualFile = f1
        CsvDiff.config.expectedFile = f2
        val result = CsvDiff.luncher.run()
        assert(result.length === 1)
      }
    }
  }

}
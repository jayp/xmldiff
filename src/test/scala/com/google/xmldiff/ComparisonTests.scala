package com.google.xmldiff

import org.scalatest.FunSuite
import scala.xml._

class ComparisonTests extends FunSuite {

  val comp = new Comparison

  test("Single tag") {
    val rawXml = <a/>
    val fromStr = XML.loadString("""<a/>""")
    assert(comp(rawXml, fromStr) === NoDiff)
  }

  /* TODO */
  test("Single tag - xml generated via optional text") {
    val value: Option[xml.Text] = None
    val rawXml = <a key={ value }/>
    val fromStr = XML.loadString("""<a/>""")
    assert(comp(rawXml, fromStr) === NoDiff)
  }

  test("Single tag - standalone tag expansion") {
    val rawXml = <a/>
    val fromStr = XML.loadString("""<a></a>""")
    assert(comp(rawXml, fromStr) === NoDiff)
  }

  test("Single tag - different tags compared (failure case)") {
    val rawXml = <a/>
    val fromStr = XML.loadString("""<b/>""")
    comp(rawXml, fromStr) match {
      case NoDiff => fail()
      case Diff(pos, msg) => // success
    }
  }

  test("Single tag with single attribute") {
    val rawXml = <a key="value"/>
    val fromStr = XML.loadString("""<a key="value"/>""")
    assert(comp(rawXml, fromStr) === NoDiff)
  }

  test("Single tag with single attribute - xml generated via optional text - I") {
    val value: Option[xml.Text] = Some(xml.Text("value"))
    val rawXml = <a key={ value }/>
    val fromStr = XML.loadString("""<a key="value"/>""")
    assert(comp(rawXml, fromStr) === NoDiff)
  }

  /* TODO */
  test("Single tag with single attribute - xml generated via optional text - II") {
    val value: Option[xml.Text] = Some(xml.Text("value"))
    val value2: Option[xml.Text] = None
    val rawXml = <a key={ value } key2={ value2 }/>
    val fromStr = XML.loadString("""<a key="value"/>""")
    assert(comp(rawXml, fromStr) === NoDiff)
  }

  test("Single tag with multiple attributes") {
    val rawXml = <a key1="value1" key2="value2"/>
    val fromStr = XML.loadString("""<a key1="value1" key2="value2"/>""")
    assert(comp(rawXml, fromStr) === NoDiff)
  }

  test("Single tag with multiple attributes - with spaces") {
    val rawXml = <a key1="value1" key2="value2"/>
    val fromStr = XML.loadString("""<a key1 = "value1" key2 = "value2"/>""")
    assert(comp(rawXml, fromStr) === NoDiff)
  }

  test("Single tag with multiple attributes - attributes out of order") {
    val rawXml = <a key1="value1" key2="value2"/>
    val fromStr = XML.loadString("""<a key2="value2" key1="value1"/>""")
    assert(comp(rawXml, fromStr) === NoDiff)
  }
}

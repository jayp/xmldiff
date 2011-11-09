package com.google.xmldiff

import org.scalatest.FunSuite
import scala.xml._

class ComparisonTests extends FunSuite {

  val comp = new Comparison

  test("Simple root") {
    val rawXml = <a/>
    val fromStr = XML.loadString("""<a/>""")
    assert(comp(rawXml, fromStr) === NoDiff)
  }

  test("Simple root - xml generated via optional text") {
    val value: Option[xml.Text] = None
    val rawXml = <a key={ value }/>
    val fromStr = XML.loadString("""<a/>""")
    assert(comp(rawXml, fromStr) === NoDiff)
  }

  test("Simple root - standalone tag expansion") {
    val rawXml = <a/>
    val fromStr = XML.loadString("""<a></a>""")
    assert(comp(rawXml, fromStr) === NoDiff)
  }

  test("Simple root - different tags compared (failure case)") {
    val rawXml = <a/>
    val fromStr = XML.loadString("""<b/>""")
    comp(rawXml, fromStr) match {
      case NoDiff => fail()
      case Diff(pos, msg) => // success
    }
  }

  test("Simple root with single attribute") {
    val rawXml = <a key="value"/>
    val fromStr = XML.loadString("""<a key="value"/>""")
    assert(comp(rawXml, fromStr) === NoDiff)
  }

  test("Simple root with single attribute - xml generated via optional text - I") {
    val value: Option[xml.Text] = Some(xml.Text("value"))
    val rawXml = <a key={ value }/>
    val fromStr = XML.loadString("""<a key="value"/>""")
    assert(comp(rawXml, fromStr) === NoDiff)
  }

  test("Simple root with single attribute - xml generated via optional text - II") {
    val value: Option[xml.Text] = Some(xml.Text("value"))
    val value2: Option[xml.Text] = None
    val rawXml = <a key={ value } key2={ value2 }/>
    val fromStr = XML.loadString("""<a key="value"/>""")
    assert(comp(rawXml, fromStr) === NoDiff)
  }

  test("Simple root with multiple attributes") {
    val rawXml = <a key1="value1" key2="value2"/>
    val fromStr = XML.loadString("""<a key1="value1" key2="value2"/>""")
    assert(comp(rawXml, fromStr) === NoDiff)
  }

  test("Simple root with multiple attributes - with spaces") {
    val rawXml = <a key1="value1" key2="value2"/>
    val fromStr = XML.loadString("""<a key1 = "value1" key2 = "value2"/>""")
    assert(comp(rawXml, fromStr) === NoDiff)
  }

  test("Simple root with multiple attributes - attributes out of order") {
    val rawXml = <a key1="value1" key2="value2"/>
    val fromStr = XML.loadString("""<a key2="value2" key1="value1"/>""")
    assert(comp(rawXml, fromStr) === NoDiff)
  }

  test("Simple root with child elelment") {
    val rawXml = <Dial timeout="30" callerId="+1999">+1888</Dial>
    val fromStr = XML.loadString("""<Dial timeout="30" callerId="+1999">+1888</Dial>""")
    assert(comp(rawXml, fromStr) == NoDiff)
  }

  test("Simple root with multiple children elelment - children out of order") {
    val rawXml = <Dial timeout="30" callerId="+1999"><A/><B/><C/></Dial>
    val fromStr = XML.loadString("""<Dial timeout="30" callerId="+1999"><B/><C/><A/></Dial>""")
    assert(comp(rawXml, fromStr) == NoDiff)
  }
}

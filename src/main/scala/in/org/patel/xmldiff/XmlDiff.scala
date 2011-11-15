/* Copyright (c) 2011 Jay A. Patel <jay@patel.org.in>
 * Copyright (c) 2008 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package in.org.patel.xmldiff

import scala.xml.Elem

abstract class XmlDiff {
  def isSimilar = true
}

/** Documents are similar */
case object NoDiff extends XmlDiff

/** The difference between two nodes. */
case class Diff(path: List[Elem], err: String) extends XmlDiff {
  override def isSimilar = false
  
  override def toString = {
    val sb = new StringBuilder
    sb.append("Diff at ")
    for (p <- path.reverse) p.nameToString(sb.append('/'))
    sb.append(": ").append(err)
    sb.toString
  }
}

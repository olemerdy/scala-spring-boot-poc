package org.lafeuille.scalaspringboot.domain.event

import org.springframework.data.neo4j.core.schema.{GeneratedValue, Id, Node}

import java.util.UUID
import scala.beans.BeanProperty

@Node
class Event {

  @Id
  @GeneratedValue
  @BeanProperty
  var id: UUID = _

  @BeanProperty
  var title: String = _
}

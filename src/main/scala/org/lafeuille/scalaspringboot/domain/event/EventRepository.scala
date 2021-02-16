package org.lafeuille.scalaspringboot.domain.event

import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository
import org.springframework.stereotype.Repository

import java.util.UUID

@Repository
trait EventRepository extends ReactiveNeo4jRepository[Event, UUID]

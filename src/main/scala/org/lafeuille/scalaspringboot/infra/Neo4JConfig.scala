package org.lafeuille.scalaspringboot.infra

import org.neo4j.driver.Driver
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.data.neo4j.core.ReactiveDatabaseSelectionProvider
import org.springframework.data.neo4j.core.transaction.ReactiveNeo4jTransactionManager

@Configuration
class Neo4JConfig {

  @Bean
  def reactiveTransactionManager(driver: Driver, databaseNameProvider: ReactiveDatabaseSelectionProvider) =
    new ReactiveNeo4jTransactionManager(driver, databaseNameProvider)

}

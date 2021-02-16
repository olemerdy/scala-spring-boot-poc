package org.lafeuille.scalaspringboot.infra

import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.InjectionPoint
import org.springframework.beans.factory.config.ConfigurableBeanFactory._
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.{Bean, Configuration, Scope}

@Configuration
class InfraConfig {

  import java.time.Clock

  @Bean
  @ConditionalOnMissingBean
  def clock: Clock =
    Clock.systemUTC

  @Bean
  @Scope(SCOPE_PROTOTYPE)
  def logger(injectionPoint: InjectionPoint): Logger =
    LoggerFactory.getLogger(injectionPoint.getMember.getDeclaringClass)

}

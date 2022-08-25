package org.apache.kyuubi.example

import org.apache.kafka.clients.CommonClientConfigs
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
import org.apache.kafka.common.serialization.StringSerializer
import org.apache.kyuubi.config.KyuubiConf
import org.apache.kyuubi.events.KyuubiEvent

import java.util.Properties

class KafkaClient(kyuubiConf: KyuubiConf) extends MessageQueueClient {

  private val topic: String = getOption("kyuubi.event.handler.kafka.topic")

  private lazy val kafkaProducer: KafkaProducer[String, String] = {
    val properties = new Properties()
    val servers = getOption("kyuubi.event.handler.kafka.bootstrap.servers")
    properties.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, servers)
    properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer].getName)
    properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer].getName)
    new KafkaProducer[String, String](properties)
  }

  override def send(kyuubiEvent: KyuubiEvent): Unit = {
    val record = new ProducerRecord[String, String](topic, kyuubiEvent.eventType, kyuubiEvent.toJson)
    kafkaProducer.send(record)
  }

  private def getOption(key: String): String = {
    kyuubiConf.getOption(key)
      .getOrElse {
        throw new IllegalArgumentException(s"[$key] can't be found, " +
          "please set it in kyuubi-defaults.conf")
      }
  }
}

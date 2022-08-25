package org.apache.kyuubi.example

import org.apache.kyuubi.config.KyuubiConf
import org.apache.kyuubi.events.KyuubiEvent
import org.apache.kyuubi.events.handler.{CustomEventHandlerProvider, EventHandler}

class MessageQueueEventHandlerProvider extends CustomEventHandlerProvider {

  override def create(kyuubiConf: KyuubiConf): EventHandler[KyuubiEvent] = {
    val kafkaClient: MessageQueueClient = new KafkaClient(kyuubiConf)
    new MessageQueueEventHandler(kafkaClient)
  }
}

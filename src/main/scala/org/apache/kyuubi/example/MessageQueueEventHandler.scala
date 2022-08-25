package org.apache.kyuubi.example

import org.apache.kyuubi.events.KyuubiEvent
import org.apache.kyuubi.events.handler.EventHandler

class MessageQueueEventHandler(messageQueueClient: MessageQueueClient) extends EventHandler[KyuubiEvent] {

  override def apply(event: KyuubiEvent): Unit = {
    messageQueueClient.send(event)
  }
}

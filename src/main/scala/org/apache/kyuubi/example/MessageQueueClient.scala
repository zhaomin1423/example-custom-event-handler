package org.apache.kyuubi.example

import org.apache.kyuubi.events.KyuubiEvent

trait MessageQueueClient {

  def send(kyuubiEvent: KyuubiEvent): Unit

}

package actors

import akka.actor._

/**
 * Represents a WebSocket handler
 * This actor lives for the time that a web socket (a browser tab in the demo) is open
 * It mediates between the actor play creates representing the socket, and the chat room actor
 */
class ChatSessionActor (chatRoom: ActorRef) extends Actor {
  def receive = {
    // this message is sent by the internal web socket actor
    // we simply 'forward' it to the chatRoom actor, 
    // as it will be communicating directly with it
    case msg: String => chatRoom forward msg
  }
}

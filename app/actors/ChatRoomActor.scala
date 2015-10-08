package actors

import akka.actor._
import play.api.libs.json._

class ChatRoomActor extends Actor {
  var messages: List[String] = Nil
  var users: List[ActorRef] = Nil
  
  def receive = {
    // user is joining when they send a 'join' message,
    // brittle, just for demo purposes
    case msg: String if msg == "join" => {
      users :+= sender // we assume that the 'sender' is a WebSocket session actor
      
      // send this one user the current state of the room (list of messages)
      sendRoomState(sender)
    }
    // any other string is considered a message to the chat room
    case message: String => {
      // append the message
      messages :+= message
      
      // notify all users with the new room state (the list of messages)
      users foreach sendRoomState
    }
  }
  
  private def sendRoomState (user: ActorRef) = user ! Json.toJson(messages)
}

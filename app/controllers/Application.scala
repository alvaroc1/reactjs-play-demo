package controllers

import play.api._
import play.api.mvc._
import javax.inject._
import play.api.libs.json._
import play.api.Play.current
import play.api.libs.concurrent.Akka

@Singleton class Application extends Controller {
  
  val chatRoom = Akka.system.actorOf(akka.actor.Props(new actors.ChatRoomActor))

  def index = Action {
    Ok(views.html.index())
  }
  
  /**
   * WebSocket backend
   * 'out' is the play actor representing the web socket
   * we don't really use it directly since we rely on the 'sender' reference
   */
  def socket = WebSocket.acceptWithActor [String, JsValue] {implicit r => out => 
    akka.actor.Props(new actors.ChatSessionActor(chatRoom))
  }

}

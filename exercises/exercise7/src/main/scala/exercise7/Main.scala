package exercise7

import akka.actor._

object Main extends App {

  import Room._

  /**
    * Let's model a chat application in Akka.  For this application we
    * will need the following actors:
    * 
    * - Room
    * 
    *   Room will keep a state of the list of users that currently
    *   belong to the room.  Also it needs to handle the following
    *   messages:
    * 
    *   - AddUser(actorRef): Adds an user to the room
    *   - SendMessage(user, message): broadcasts the received message
    *     to all users
    * 
    * - Member
    * 
    *   A member has a username, that _should_ be unique.  It should
    *   handle the the following message:
    * 
    *   - NewMessage(memeber, msg).  It can just log whatever mesage
    *     it received to the console.
    * 
    * To test the application, we can instantiate a room and a couple
    * of users from Main and send a couple of messages too.
    */

  val system = ActorSystem("exercise7")

  system.terminate()

}

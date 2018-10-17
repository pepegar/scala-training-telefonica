package dbaccess

import javax.inject._
import play.api.mvc._
import play.api.db.{Database, Databases}
import play.api.db.evolutions._

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class Controller @Inject()(db: Database, cc: ControllerComponents) extends AbstractController(cc) {

  def withMyDatabase[T](block: Database => T) = {

    Databases.withInMemory(
      urlOptions = Map(
        "MODE" -> "MYSQL"
      ),
      config = Map(
        "logStatements" -> true
      )
    ) { database =>

      Evolutions.withEvolutions(database, SimpleEvolutionsReader.forDefault(
        Evolution(
          1,
          "create table users (id bigint not null, name varchar(255));",
          "drop table test;"
        )
      )) {

        block(database)

      }
    }
  }

  def index() = Action { implicit req =>

    withMyDatabase(_.withConnection { conn =>
      val stmt = conn.createStatement()
      stmt.execute(s"insert into users values (1, 'pepe')")
    })

    Ok("OK")
  }

}

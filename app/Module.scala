import com.google.inject.AbstractModule
import config.{JWTConfig, PlayJWTConfig}
import daos.{PermissionDAO, RoleDAO, TokenDAO, UserDAO}
import daos.impl.{PermissionDAOImpl, RoleDAOImpl, TokenDAOPsqlImpl, UserDAOPsqlImpl}
import monix.execution.Scheduler
import play.api.Configuration
import services.{AuthService, JWTService, UserService}
import services.impl.{AuthServiceImpl, UserServiceImpl}

class Module extends AbstractModule {

  override def configure() = {
    bind(classOf[Scheduler]).toInstance(Scheduler.global)
    bind(classOf[AuthService]).to(classOf[AuthServiceImpl])
    bind(classOf[UserService]).to(classOf[UserServiceImpl])
    bind(classOf[JWTService]).asEagerSingleton()

    bind(classOf[JWTConfig]).to(classOf[PlayJWTConfig])

    bind(classOf[TokenDAO]).to(classOf[TokenDAOPsqlImpl])
    bind(classOf[UserDAO]).to(classOf[UserDAOPsqlImpl])
    bind(classOf[PermissionDAO]).to(classOf[PermissionDAOImpl])
    bind(classOf[RoleDAO]).to(classOf[RoleDAOImpl])

  }

}

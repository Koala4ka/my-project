import com.google.inject.AbstractModule
import config.{JWTConfig, PlayJWTConfig}
import daos.{EmployeeDAO, GoalDAO, PermissionDAO, RoleDAO, TokenDAO, UserDAO}
import daos.impl.{EmployeeDAOImpl, GoalDAOImpl, PermissionDAOImpl, RoleDAOImpl, TokenDAOImpl, UserDAOImpl}
import models.Employee
import models.dtos.answers.EmployeeDTO
import monix.execution.Scheduler
import play.api.Configuration
import services.{AuthService, GoalService, JWTService, UserService}
import services.impl.{AuthServiceImpl, GoalServiceImpl, UserServiceImpl}

class Module extends AbstractModule {

  override def configure() = {
    bind(classOf[Scheduler]).toInstance(Scheduler.global)
    bind(classOf[AuthService]).to(classOf[AuthServiceImpl])
    bind(classOf[UserService]).to(classOf[UserServiceImpl])
    bind(classOf[GoalService]).to(classOf[GoalServiceImpl])
    bind(classOf[JWTService]).asEagerSingleton()

    bind(classOf[JWTConfig]).to(classOf[PlayJWTConfig])

    bind(classOf[TokenDAO]).to(classOf[TokenDAOImpl])
    bind(classOf[UserDAO]).to(classOf[UserDAOImpl])
    bind(classOf[PermissionDAO]).to(classOf[PermissionDAOImpl])
    bind(classOf[RoleDAO]).to(classOf[RoleDAOImpl])
    bind(classOf[GoalDAO]).to(classOf[GoalDAOImpl])
    bind(classOf[EmployeeDAO]).to(classOf[EmployeeDAOImpl])


  }

}

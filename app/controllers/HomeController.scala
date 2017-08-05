package controllers

import javax.inject._
import javax.print.attribute.standard.JobOriginatingUserName

import play.api._
import play.api.mvc._


/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController extends Controller{

  /**
   * User-Id: 123
   * User-Name: jasmine
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */

  def index() = Action{ implicit request =>

      Ok(views.html.message())

  }

  def sendData(userId: Int, userName: String) = Action { implicit request: Request[AnyContent] =>

     Redirect(routes.HomeController.receiveData).withSession("userId" -> s"$userId", "userName" -> s"$userName" )

  }

  def receiveData() = Action{ implicit request: Request[AnyContent] =>

    val userId = request.session.get("userId")
    val userName = request.session.get("userName")

    val (keyOfMessage, message) = if(userId.exists(_ == "123") && userName.exists(_ == "jasmine")) ("Success","Hi, Jasmine! You have successfully logged in!")
    else ("Error", "Oops!! I couldn't recognize you!")
    Redirect(routes.HomeController.displayMessage()).flashing(keyOfMessage->message)

  }

  def displayMessage() = Action { implicit request: Request[AnyContent] =>

    Ok(views.html.index())

  }

}

//package utils
//
//import java.io.UnsupportedEncodingException
//import java.time.Instant
//import java.util.Date
//import java.util.UUID.randomUUID
//import java.util.concurrent.ThreadLocalRandom
//import com.auth0.jwt.JWT.{create, require}
//import com.auth0.jwt.algorithms.Algorithm.{HMAC512, none}
//import com.auth0.jwt.exceptions.{JWTCreationException, JWTVerificationException}
//import com.auth0.jwt.impl.NullClaim
//import models.User
//import org.slf4j.LoggerFactory
//import play.api.mvc.Results.InternalServerError
//
//object TokenUtils {
//
//  private val LOG = LoggerFactory.getLogger("application")
//  private val DIGITS = "0123456789"
//
//  private def threadLocalRandom = ThreadLocalRandom.current()
//
//  def generateNumericCode = generateToken(6)
//
//  def generateToken(tokenLength: Int): String =
//    if (tokenLength == 0) "" else DIGITS(threadLocalRandom.nextInt(DIGITS.length())) +
//      generateToken(tokenLength - 1)
//
//  def generateJwtSimpleCode(seq: Map[String, String]) = {
//    try {
//      val algorithm = none()
//      val jwt = create
//      seq.foreach(e => jwt.withClaim(e._1, e._2))
//      jwt.sign(algorithm)
//    } catch {
//      case e: UnsupportedEncodingException =>
//        LOG.error("Generating token error", e)
//        //throw InternalServerError("Generating token error")
//      case e: JWTCreationException =>
//        LOG.error("Generating token error", e)
//       // throw InternalServerError("Generating token error")
//    }
//  }
//
//  def generateJwtStrongCode(user: User,
//                            expires: Instant) = {
//    try {
//      val algorithm = HMAC512("secret")
//      create
//        .withJWTId(randomUUID.toString)
//        .withIssuer("advancedopen")
//        .withClaim("userId", user.id.toString)
//        //.withClaim("userDeviceId", userDeviceId.value.toString)
//        .withExpiresAt(Date.from(expires))
//        .withIssuedAt(Date.from(Instant.now))
//        .sign(algorithm)
//    } catch {
//      case e: UnsupportedEncodingException =>
//        LOG.error("Generating token error", e)
//      //  throw ("Generating token error")
//      case e: JWTCreationException =>
//        LOG.error("Generating token error", e)
//      //  throw InternalServerError("Generating token error")
//    }
//  }
//
//  def verifyJwtCode(token: String) = {
//    try {
//      val algorithm = HMAC512("secret")
//      val verifier = require(algorithm)
//        //.withIssuer("advancedopen")
//        //.withAudience("www.advancedopen.com")
//        .build
//      verifier.verify(token)
//    } catch {
//      case e: UnsupportedEncodingException =>
//        //throw InternalServerError("Token is invalid")
//      case e: JWTVerificationException =>
//        LOG.error(s"Exception at verifying JWT code [$token]: ${e.getLocalizedMessage}")
//       // throw InternalServerError("Token is invalid")
//    }
//  }
//
////  def userFromJwtCode(token: String) = {
////    try {
////      verifyJwtCode(token).getClaim("userId") match {
////        case claim if claim.isInstanceOf[NullClaim] =>
////          None
////        case claim =>
////          Some(User(claim.asString().toLong))
////      }
////    } catch {
////      case ex: Exception =>
////        LOG.error(s"userFromJwtCode exception: ${ex.getLocalizedMessage}")
////        None
////    }
//}
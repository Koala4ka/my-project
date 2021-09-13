package services

import config.JWTConfig
import exceptions.Exceptions.TokenBrokenOrExpired
import models.{Token, User}
import pdi.jwt.{Jwt, JwtAlgorithm, JwtClaim}
import play.api.libs.json.Json

import java.time.Clock
import javax.inject.Inject
import java.time.Instant

class JWTService @Inject()(config: JWTConfig){

  def createToken(user: User): Token = {
    val json = s"""{ "id": "${user.id}", "email": "${user.email}" }"""
    val expiresInSeconds = 30L * 24 * 60 * 60 // 30 days
    val claim = JwtClaim(json).issuedNow(Clock.systemUTC())
      .expiresIn(expiresInSeconds)(Clock.systemUTC())

    val token = Jwt.encode(claim, config.secretKey.string, JwtAlgorithm.HS384)
    Token(id = 0,userId = user.id,token = token,createdAt = Instant.now())
  }

  def decodeToken(token: String): (Long, String) = {
    Jwt
      .decode(token, config.secretKey.string, Seq(JwtAlgorithm.HS384))
      .map { decodedClaim =>
        val json = Json.parse(decodedClaim.toJson)
        val id = (json \ "id").as[Long]
        val email = (json \ "email").as[String]
        (id,email)
      }
      .getOrElse {
       throw TokenBrokenOrExpired

      }
  }

}

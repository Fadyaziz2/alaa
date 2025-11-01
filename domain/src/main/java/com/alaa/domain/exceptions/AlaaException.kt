package com.alaa.domain.exceptions

open class AlaaException(message: String) : Exception(message)
open class NetworkException(message: String) : AlaaException(message)
open class ServerException(message: String) : AlaaException(message)
class NoInternetException(message: String) : NetworkException(message)
class NullResultException(message: String) : AlaaException(message)
class NotFoundException(message: String) : AlaaException(message)
class ServerErrorException(message: String) : ServerException(message)

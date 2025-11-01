package com.alaa.alaagallo.model.registration

data class Profile (
    val user: ProfileDetails,
    val token: String
)
data class ProfileDetails(
    val id: Int,
    val name: String,
    val email: String,
    val subscription: ProfileSubscription,
)
data class ProfileSubscription(
    val end: String,
)
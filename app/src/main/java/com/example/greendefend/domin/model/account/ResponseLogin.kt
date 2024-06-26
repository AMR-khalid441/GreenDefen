package com.example.greendefend.domin.model.account

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ResponseLogin(
    @SerializedName("fullName"        ) var fullName        : String?  = null,
    @SerializedName("email"           ) var email           : String?  = null,
    @SerializedName("userId"          ) var userId          : String?  = null,
    @SerializedName("token"           ) var token           : String?  = null,
    @SerializedName("region"          ) var region          : String?  = null,
    @SerializedName("imageUrl"        ) var imageUrl        : String?  = null,
    @SerializedName("message"         ) var message         : String?  = null,
    @SerializedName("isAuthenticated" ) var isAuthenticated : Boolean? = null,
    @SerializedName("bio"             ) var bio             : String?  = null,
    @SerializedName("country"         ) var country         : String?  = null,
    @SerializedName("expier"          ) var expier          : String?  = null
): Serializable
package code.madlife.foodfirstver.data.model.user

import code.madlife.foodfirstver.core.common.MySharedPreferences


object UserClient {
    var id: String? = null
    var name: String? = null
    var email: String? = null
    var image: String? = null
    var banner: String? = null
    var phone: String? = null

    fun setUserFromUser(user: User) {
        id = user._id
        name = user.fullName
        email = user.email
        image = user.image
        banner = user.imageBanner
        phone = user.phone
    }

    fun saveUser(sharedPreferences: MySharedPreferences, user: User) {
        sharedPreferences.putString("id", user._id.toString())
        sharedPreferences.putString("name", user.fullName.toString())
        sharedPreferences.putString("email", user.email.toString())
        sharedPreferences.putString("image", user.image.toString())
        sharedPreferences.putString("banner", user.imageBanner.toString())
        sharedPreferences.putString("phone", user.phone.toString())
    }

    fun getUser(sharedPreferences: MySharedPreferences): UserClient {
        id = sharedPreferences.getString("id", "")
        name = sharedPreferences.getString("name", "")
        email = sharedPreferences.getString("email", "")
        image = sharedPreferences.getString("image", "")
        banner = sharedPreferences.getString("banner", "")
        phone = sharedPreferences.getString("phone", "")
        return UserClient
    }
}
package code.madlife.foodfirstver.core.common

object Constants {

    const val TOKEN_USER = "TOKEN_USER"
    const val TYPE_REGISTER = "register"
    const val TYPE_LOGIN = "login"
    const val TAG_SETTING_TITLE = "TAG_SETTING_TITLE"
    const val TAG_SETTING_NOT_NULL_LOGIN = "TAG_SETTING_NOT_NULL_LOGIN"
    const val TAG_SETTING_SHOW_INFO = "TAG_SETTING_SHOW_INFO"
    const val TAG_SETTING_NOTIFICATION = "TAG_SETTING_NOTIFICATION"
    const val TAG_SETTING_PAYMENT = "TAG_SETTING_PAYMENT"
    const val TAG_SETTING_CHANG_PASS = "TAG_SETTING_CHANG_PASS"
    const val TAG_SETTING_CHANG_LANGUAGE = "TAG_SETTING_CHANG_LANGUAGE"
    const val TAG_SETTING_CHANG_THEME = "TAG_SETTING_CHANG_THEME"
    const val TAG_SETTING_CHANG_HELP = "TAG_SETTING_CHANG_HELP"
    const val TAG_SETTING_ABOUT_US = "TAG_SETTING_ABOUT_US"
    const val TAG_SETTING_LOGOUT = "TAG_SETTING_LOGOUT"
    object Inject {
        const val API = "API"
        const val AUTH = "AUTH"
    }

    object CodeError {
        const val EMAIL_ALREADY_EXISTS = 1
        const val ACCOUNT_LOCKED = 2
        const val OTP_valid = 3
        const val OTP_expired = 4
        const val email_not_exits = 5
        const val email_and_pass_not_exits = 6
        const val email_un_verify = 7
    }

    object Preference {
        const val PREF_PROFILE = "PROFILE"
        const val PREF_IS_HOME_QUICK_VIEW = "IS_HOME_QUICK_VIEW"
        const val PREF_CONFIG_APP = "pref_config_app"
        const val PREF_FONT_LEVEL = "pref_font_level"
        const val SAVE_READ_POST = "save_read_post"
        const val LOGIN_SUCCESS = "login"
        const val PREF_IS_SHOW_TOOL_TIP = "pref_show_tool_tip"
        const val PREF_AUTH_MODEL = "AUTH_MODEL"
        const val PREF_DARK_MODE_APP = "PREF_DARK_MODE_APP"
        const val PREF_DARK_MODE_SYSTEM = "IS_DARK_MODE_SYSTEM"
        const val PREF_DARK_MODE_IS_DISMISS_PROMPT = "PREF_DARK_MODE_IS_DISMISS_PROMPT"
    }

    object TabMain {
        const val HOME = 0
        const val LIVE = 1
        const val VIDEO = 2
        const val NOTIFICATION = 3
        const val USER = 4
    }
}
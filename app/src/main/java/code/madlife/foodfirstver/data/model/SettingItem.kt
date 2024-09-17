package code.madlife.foodfirstver.data.model

import code.madlife.foodfirstver.R

data class SettingItem(
    val tag: String? = null,
    val viewType: Int,
    val background: Int,
    val iconResourceLeft: Int,
    val iconResourceRight: Int = R.drawable.ic_button_infor_setting,
    val title: String,
    val description: String,
    var isVisible: Boolean = true
)
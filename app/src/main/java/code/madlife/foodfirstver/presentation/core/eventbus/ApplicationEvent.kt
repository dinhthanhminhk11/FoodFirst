package code.madlife.foodfirstver.presentation.core.eventbus

class ApplicationEvent(val typeEvent: Type) {

    enum class Type {
        OPEN_APP,
        HIDDEN_APP
    }
}
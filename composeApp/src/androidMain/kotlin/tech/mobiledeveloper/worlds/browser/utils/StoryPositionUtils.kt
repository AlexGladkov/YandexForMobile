package tech.mobiledeveloper.worlds.browser.utils

/**
 * Stories lib doesn't allow custom data for StoryFrame,
 * so we have to do some ugly things to pass position to custom view
 */
object StoryPositionUtils {

    fun positionAsImageUrl(position: Int): String {
        return position.toString()
    }

    fun imageUrlAsPosition(imageId: String): Int {
        return imageId.toInt()
    }
}

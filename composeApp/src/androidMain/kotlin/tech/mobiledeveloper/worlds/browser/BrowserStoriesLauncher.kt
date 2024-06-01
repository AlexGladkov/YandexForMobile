package tech.mobiledeveloper.worlds.browser

import android.app.Activity
import android.graphics.Color
import cache.StoriesCacheFactory
import cache.StoriesConfig
import com.redmadrobot.stories.models.StoriesInputParams
import com.redmadrobot.stories.models.Story
import com.redmadrobot.stories.models.StoryFrame
import com.redmadrobot.stories.models.StoryFrameAction
import com.redmadrobot.stories.models.StoryFrameContent
import com.redmadrobot.stories.models.StoryFrameContentPosition
import com.redmadrobot.stories.models.StoryFrameControlsColor
import com.redmadrobot.stories.models.StoryFrameShowGradients
import com.redmadrobot.stories.stories.StoriesController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import tech.mobiledeveloper.worlds.browser.utils.StoryPositionUtils
import java.util.UUID

object BrowserStoriesLauncher {

    private var isPrepared = false

    fun launchStories(activity: Activity) {
        prepareStories(activity)

        activity.startActivity(
            BrowserDivKitActivity.newIntent(
                activity,
                StoriesInputParams.createDefaults()
            )
        )
    }

    private fun prepareStories(activity: Activity) {
        if (isPrepared) {
            return
        }
        isPrepared = true

        StoriesCacheFactory
            .init(activity.application, CoroutineScope(Dispatchers.IO + SupervisorJob()))
            .preloadImages(false)
            .setConfig(StoriesConfig.All)

        val story = Story(
            id = UUID.randomUUID().toString(),
            name = "",
            isSeen = false,
            previewUrl = "",
            title = "",
            frames = List(5) { index -> createFrame(index) }
        )

        val stories = listOf(story)
        val controller: StoriesController = StoriesCacheFactory.getInstance()
        controller.clearAndAdd(StoriesConfig.All, stories)
    }

    private fun createFrame(index: Int): StoryFrame {
        return StoryFrame(
            imageUrl = StoryPositionUtils.positionAsImageUrl(index),
            content = StoryFrameContent(
                controlsColor = StoryFrameControlsColor.DARK,
                showGradients = StoryFrameShowGradients.BOTTOM,
                position = StoryFrameContentPosition.TOP,
                textColor = "",
                header1 = "",
                header2 = "",
                descriptions = listOf(""),
                action = StoryFrameAction(
                    text = "",
                    url = ""
                ),
                gradientColor = Color.TRANSPARENT.toString()
            )
        )
    }
}

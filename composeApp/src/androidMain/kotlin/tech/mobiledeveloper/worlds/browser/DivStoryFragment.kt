package tech.mobiledeveloper.worlds.browser

import android.content.Context
import android.os.Bundle
import com.redmadrobot.stories.models.Story
import com.redmadrobot.stories.stories.StoryFragment
import com.redmadrobot.stories.stories.views.BaseStoryFrameView
import com.yandex.div.core.Div2Context
import com.yandex.div.core.DivConfiguration
import com.yandex.div.core.font.DivTypefaceProvider
import com.yandex.div.data.DivParsingEnvironment
import com.yandex.div.font.YandexSansDivTypefaceProvider
import com.yandex.div.json.ParsingEnvironment
import com.yandex.div.json.ParsingErrorLogger
import com.yandex.div.picasso.PicassoDivImageLoader
import org.json.JSONArray
import org.json.JSONObject
import tech.mobiledeveloper.worlds.browser.utils.IOUtils

class DivStoryFragment : StoryFragment() {

    private lateinit var divContext: Div2Context
    private lateinit var parsingEnvironment: ParsingEnvironment
    private lateinit var itemsJson: JSONArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activity = requireActivity()
        val divConfiguration = DivConfiguration.Builder(PicassoDivImageLoader(activity))
            .supportHyphenation(true)
            .typefaceProvider(DivTypefaceProvider.DEFAULT)
            .actionHandler(DivUrlActionHandler(requireContext()))
            .typefaceProvider(YandexSansDivTypefaceProvider(requireContext()))
            .build()
        divContext = Div2Context(
            baseContext = activity,
            configuration = divConfiguration,
            lifecycleOwner = activity
        )

        val divJson = JSONObject(IOUtils.read(activity.assets.open(("browser_story.json"))))
        val templatesJson = divJson.optJSONObject("templates")
        itemsJson = divJson.getJSONArray("cards")
        parsingEnvironment = DivParsingEnvironment(ParsingErrorLogger.ASSERT).apply {
            if (templatesJson != null) parseTemplates(templatesJson)
        }
    }

    override fun createStoryFrameView(context: Context): BaseStoryFrameView =
        DivStoriesView(context, divContext, parsingEnvironment, itemsJson)

    companion object {
        fun newInstance(story: Story): StoryFragment =
            DivStoryFragment().addStoryToArguments(story)
    }
}
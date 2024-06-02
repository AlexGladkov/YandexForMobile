package tech.mobiledeveloper.worlds.browser

import android.annotation.SuppressLint
import android.content.Context
import com.redmadrobot.stories.models.StoryFrame
import com.redmadrobot.stories.stories.views.BaseStoryFrameView
import com.yandex.div.DivDataTag
import com.yandex.div.core.Div2Context
import com.yandex.div.core.view2.Div2View
import com.yandex.div.json.ParsingEnvironment
import com.yandex.div2.DivData
import org.json.JSONArray
import tech.mobiledeveloper.worlds.browser.utils.StoryPositionUtils

@SuppressLint("ViewConstructor")
class DivStoriesView(context: Context,
                     divContext: Div2Context,
                     private val parsingEnvironment: ParsingEnvironment,
                     private val itemsJson: JSONArray) : BaseStoryFrameView(context) {

    private val divView: Div2View

    init {
        divView = Div2View(divContext)
        addView(divView)
    }

    override fun onFrameSet(frame: StoryFrame) {
        val index = StoryPositionUtils.imageUrlAsPosition(frame.imageUrl)
        val divData = DivData.invoke(parsingEnvironment, itemsJson.getJSONObject(index))
        divView.setData(divData, DivDataTag(divData.logId))
    }
}
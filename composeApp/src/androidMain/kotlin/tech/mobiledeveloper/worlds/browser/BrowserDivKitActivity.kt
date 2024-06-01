package tech.mobiledeveloper.worlds.browser

import android.content.Context
import android.content.Intent
import com.redmadrobot.stories.models.StoriesInputParams
import com.redmadrobot.stories.models.Story
import com.redmadrobot.stories.stories.StoriesBaseActivity
import com.redmadrobot.stories.stories.StoryFragment

class BrowserDivKitActivity : StoriesBaseActivity() {

    override fun onStoryActionClicked(url: String) {
        // do nothing
    }

    override val createStoryFragment: ((Story) -> StoryFragment) = { story ->
        DivStoryFragment.newInstance(story)
    }

    companion object {
        fun newIntent(
            context: Context,
            storiesInputParams: StoriesInputParams
        ): Intent = newStoriesIntent(
            context = context,
            clazz = BrowserDivKitActivity::class.java,
            storiesInputParams = storiesInputParams
        )
    }
}
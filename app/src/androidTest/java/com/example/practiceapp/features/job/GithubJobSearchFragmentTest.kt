package com.example.practiceapp.features.job

import android.view.KeyEvent
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.pressKey
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.practiceapp.R
import com.example.practiceapp.network.response.ApiResponse
import com.example.practiceapp.network.response.JobResponse
import com.example.practiceapp.utils.CountingAppExecutorsRule
import com.example.practiceapp.utils.RecyclerViewMatcher
import com.example.practiceapp.utils.TaskExecutorWithIdlingResourceRule
import com.example.practiceapp.utils.TestUtils
import com.example.practiceapp.utils.ViewModelUtils
import com.example.practiceapp.utils.mock
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
class GithubJobSearchFragmentTest {

    @Rule
    @JvmField
    val executorRule = TaskExecutorWithIdlingResourceRule()

    @Rule
    @JvmField
    val countingAppExecutors = CountingAppExecutorsRule()

    private val navController = mock<NavController>()

    private val searchJobLiveData = MutableLiveData<ApiResponse<List<JobResponse>>>()

    private lateinit var viewModel: GithubJobSearchViewModel

    @Before
    fun init() {
        viewModel = mock(GithubJobSearchViewModel::class.java)
//        doNothing().`when`(viewModel).setJobTitle(anyString())
        `when`(viewModel.searchJobLiveData).thenReturn(searchJobLiveData)

        launchFragmentInContainer(themeResId = R.style.Theme_AppCompat_Light_DarkActionBar) {
            GithubJobSearchFragment().apply {
                viewModelFactory = ViewModelUtils.createFor(viewModel)
            }
        }.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), navController)
        }
    }

    @Test
    fun search() {
        onView(withId(R.id.etxSearch)).perform(
            typeText("android"),
            pressKey(KeyEvent.KEYCODE_ENTER)
        )
        verify(viewModel).setJobTitle("android")
    }

    @Test
    fun valueLoaded() {
        val jobs = listOf(TestUtils.createJob())

        searchJobLiveData.postValue(ApiResponse.Success(jobs))
        onView(
            listMatcher().atPositionOnView(0, R.id.tvJobTitle)
        ).check(
            matches(withText("Software Engineer"))
        )
    }

    @Test
    fun loadMore() {
        val jobs = TestUtils.createJobs(30)
        searchJobLiveData.postValue(ApiResponse.Success(jobs))
        val action = RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(29)
        onView(withId(R.id.recyclerView)).perform(action)
        onView(listMatcher().atPosition(29)).check(matches(isDisplayed()))
        verify(viewModel).loadMore()
    }

    private fun listMatcher(): RecyclerViewMatcher {
        return RecyclerViewMatcher(R.id.recyclerView)
    }
}

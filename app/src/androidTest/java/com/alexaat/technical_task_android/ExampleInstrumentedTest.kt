package com.alexaat.technical_task_android

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.alexaat.technical_task_android.adapters.UsersViewHolder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest: BaseTest() {

    @Before
    fun setup() {
        launchActivity<MainActivity>()
        Thread.sleep(5000)
    }

    @Test
    fun press_add_button_new_user_dialog_displayed(){
        onView(withId(R.id.add_user_floating_action_button)).perform(click())
        onView(withText("New User")).check(matches(isDisplayed()))
    }

    @Test
    fun long_press_delete_user_dialog_displayed(){
        onView(withId(R.id.users_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition<UsersViewHolder>(0, longClick()))
        onView(withText("Delete User")).check(matches(isDisplayed()))
    }

    @Test
    fun add_user_and_verify_tha_use_added(){
        onView(withId(R.id.add_user_floating_action_button)).perform(click())
        onView(withId(R.id.new_user_name_text_input)).perform(click(), replaceText("Oliver"))
        onView(withId(R.id.new_user_email_text_input)).perform(click(), replaceText("Oliver@aol.com"))
        onView(withText("OK")).perform(click())
        Thread.sleep(5000)
        onView(withId(R.id.users_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition<UsersViewHolder>(0, click()))
        waitForView(withText("Oliver")).check(matches(isDisplayed()))
    }

    @Test
    fun add_user_then_delete_user_and_verify(){
        onView(withId(R.id.add_user_floating_action_button)).perform(click())
        onView(withId(R.id.new_user_name_text_input)).perform(click(), replaceText("Oliver"))
        onView(withId(R.id.new_user_email_text_input)).perform(click(), replaceText("Oliver@aol.com"))
        onView(withText("OK")).perform(click())
        Thread.sleep(3000)
        onView(withId(R.id.users_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition<UsersViewHolder>(0, longClick()))
        onView(withText("OK")).perform(click())
        Thread.sleep(3000)
        onView(withId(R.id.users_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition<UsersViewHolder>(0, click()))
        onView(withText("Oliver")).check(doesNotExist())
    }

}
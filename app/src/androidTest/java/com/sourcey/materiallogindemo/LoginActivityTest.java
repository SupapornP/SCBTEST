package com.sourcey.materiallogindemo;

import android.content.Intent;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.TestCase.assertTrue;


@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> loginActivityRule = new ActivityTestRule<>(LoginActivity.class);

    @Before
    public void createAccount() {
        onView(withId(R.id.link_signup)).perform(click());
        onView(withId(R.id.input_name)).perform(ViewActions.scrollTo(), typeText("Supaporn"));
        onView(withId(R.id.input_address)).perform(ViewActions.scrollTo(), typeText("21/4"));
        onView(withId(R.id.input_email)).perform(ViewActions.scrollTo(), typeText("yam.supaporn@gmail.com"));
        onView(withId(R.id.input_mobile)).perform(ViewActions.scrollTo(), typeText("0819129880"));
        onView(withId(R.id.input_password)).perform(ViewActions.scrollTo(), typeText("Munkeaw21"));
        onView(withId(R.id.input_reEnterPassword)).perform(ViewActions.scrollTo(), typeText("Munkeaw21"), closeSoftKeyboard());
        onView(withId(R.id.btn_signup)).perform(ViewActions.scrollTo(), click());

    }

    @Test
    public void loginSuccess() throws InterruptedException {

        loginActivityRule.launchActivity(new Intent());

        // Type valid email and password then press login button.
        onView(withId(R.id.input_email)).perform(typeText("yam.supaporn@gmail.com"));
        onView(withId(R.id.input_password)).perform(typeText("Munkeaw21"), closeSoftKeyboard());
        onView(withId(R.id.btn_login)).perform(click());

        // Check the result.
        Thread.sleep(5000);
        assertTrue(loginActivityRule.getActivity().isFinishing());

    }

    @Test
    public void loginFailWithInvalidEmail() {

        loginActivityRule.launchActivity(new Intent());

        // Type text and then press the button.
        onView(withId(R.id.input_email)).perform(typeText("test test"), closeSoftKeyboard());
        onView(withId(R.id.btn_login)).perform(click());

        // Check the result.
        onView(withId(R.id.input_email)).check(matches(hasErrorText("enter a valid email address")));
    }

    @Test
    public void loginFailWithPasswordThatLengthLessThan4Characters() {

        loginActivityRule.launchActivity(new Intent());

        // Type text then press the button.
        onView(withId(R.id.input_email)).perform(typeText("yam.supaporn@gmail.com"));
        onView(withId(R.id.input_password)).perform(typeText("yam"), closeSoftKeyboard());
        onView(withId(R.id.btn_login)).perform(click());

        // Check the result.
        onView(withId(R.id.input_password)).check(matches(hasErrorText("between 4 and 10 alphanumeric characters")));
    }

    @Test
    public void loginFailWithPasswordThatLengthMoreThan10Characters() {

        loginActivityRule.launchActivity(new Intent());

        // Type text then press the button.
        onView(withId(R.id.input_email)).perform(typeText("yam.supaporn@gmail.com"));
        onView(withId(R.id.input_password)).perform(typeText("yammmmmm1234"), closeSoftKeyboard());
        onView(withId(R.id.btn_login)).perform(click());

        // Check the result.
        onView(withId(R.id.input_password)).check(matches(hasErrorText("between 4 and 10 alphanumeric characters")));
    }

    @Test
    public void loginFailWithInvalidEmailAndPassword() {

        loginActivityRule.launchActivity(new Intent());

        // Type text then press the button.
        onView(withId(R.id.input_email)).perform(typeText("supaporn@gmail.com"));
        onView(withId(R.id.input_password)).perform(typeText("yam1234"), closeSoftKeyboard());
        onView(withId(R.id.btn_login)).perform(click());

        // Check the result.
        onView(withId(R.id.input_password)).check(matches(hasErrorText("enter a valid email address or password")));
    }
}

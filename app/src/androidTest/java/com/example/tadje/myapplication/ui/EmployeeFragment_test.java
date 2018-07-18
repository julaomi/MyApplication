package com.example.tadje.myapplication.ui;

import android.content.Context;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.example.tadje.myapplication.Persistence.AppDatabase;
import com.example.tadje.myapplication.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

/**
 * Created by tadje on 26.04.2018.
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class EmployeeFragment_test extends AndroidTestCase {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void employeeFragment_test () throws InterruptedException {

        ViewInteraction appCompatImageButton = onView(allOf(withContentDescription("öffne " +
                "navigation drawer"), childAtPosition(allOf(withId(R.id.toolbar), childAtPosition(withClassName(is("android.support.design.widget.AppBarLayout")),
                0)), 1), isDisplayed()));
        appCompatImageButton.perform(click());




        ViewInteraction navigationMenuItemView = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.design_navigation_view),
                                childAtPosition(
                                        withId(R.id.nav_view),
                                        0)),
                        6),
                        isDisplayed()));
        navigationMenuItemView.perform(click());

        Thread.sleep(1000);
/*
        ViewInteraction navigationMenuItemView2 = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.design_navigation_view),
                                childAtPosition(
                                        withId(R.id.nav_view),
                                        0)),
                        6),
                        isDisplayed()));
        navigationMenuItemView2.perform(click());
*/




        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.addEmployeeButton), withText("+"),
                        childAtPosition(
                                allOf(withId(R.id.fragment_container_add),
                                        childAtPosition(
                                                withClassName(is("android.widget.FrameLayout")),
                                                0)),
                                3),
                        isDisplayed()));
        appCompatButton.perform(click());





        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.empNumbEdit),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.textInputLayout),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(click());





        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.empNumbEdit),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.textInputLayout),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("28"), closeSoftKeyboard());





        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.empLastnameEdit),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.textInputLayout2),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("T"), closeSoftKeyboard());





        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.empFirstnameEdit),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.textInputLayout3),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("Testi"), closeSoftKeyboard());





        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.empFirstnameEdit), withText("Testi"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.textInputLayout3),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText5.perform(click());




        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.workingTimeEdit),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.textInputLayout4),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText6.perform(replaceText("25"), closeSoftKeyboard());




        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.workingTimeEdit), withText("25"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.textInputLayout4),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText7.perform(pressImeActionButton());




        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.roleEdit),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.textInputLayout5),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText8.perform(replaceText("hallo"), closeSoftKeyboard());





        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.roleEdit), withText("hallo"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.textInputLayout5),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText9.perform(pressImeActionButton());





        ViewInteraction appCompatEditText10 = onView(
                allOf(withId(R.id.roleEdit), withText("hallo"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.textInputLayout5),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText10.perform(replaceText("hallo"));




        ViewInteraction appCompatEditText11 = onView(
                allOf(withId(R.id.roleEdit), withText("hallo"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.textInputLayout5),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText11.perform(closeSoftKeyboard());




        ViewInteraction appCompatEditText12 = onView(
                allOf(withId(R.id.roleEdit), withText("hallo"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.textInputLayout5),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText12.perform(pressImeActionButton());




        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.addImageButton), withText("Bild hinzufügen"),
                        childAtPosition(
                                allOf(withId(R.id.alertDialogEmployee),
                                        childAtPosition(
                                                withId(android.R.id.custom),
                                                0)),
                                6),
                        isDisplayed()));
        appCompatButton2.perform(click());





        ViewInteraction appCompatButton3 = onView(
                allOf(withId(android.R.id.button1), withText("Galerie"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton3.perform(ViewActions.pressBack());
        appCompatButton3.perform(scrollTo(), click());





        ViewInteraction appCompatButton4 = onView(
                allOf(withId(android.R.id.button1), withText("Hinzufügen"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton4.perform(scrollTo(), click());

    }

    static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }


}

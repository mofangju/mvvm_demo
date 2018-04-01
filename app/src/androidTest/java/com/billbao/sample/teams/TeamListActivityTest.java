package com.billbao.sample.teams;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.billbao.sample.R;
import com.billbao.sample.TestUtils;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.billbao.sample.TestUtils.getCurrentActivity;
import static org.hamcrest.core.IsNot.not;

/**
 * Tests for the tasks screen, the main screen which contains a list of all tasks.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class TeamListActivityTest {

    private static final int ITEM_BELOW_THE_FOLD = 30;

    /**
     * {@link ActivityTestRule} is a JUnit {@link Rule @Rule} to launch your activity under test.
     * <p>
     * Rules are interceptors which are executed for each test method and are important building
     * blocks of Junit tests.
     */
    @Rule
    public ActivityTestRule<TeamListActivity> mActivityRule = new ActivityTestRule<>(TeamListActivity.class);

    @Test
    public void checkFirstTeamSortedByFullName() {
        sortByFullName();

        onView(withText("Atlanta Hawks")).check(matches(isDisplayed()));
    }

    @Test
    public void checkFirstTeamSortedByWins() {
        sortByWins();

        onView(withText("Memphis Grizzlies")).check(matches(isDisplayed()));
    }

    @Test
    public void checkFirstTeamSortedByLosses() {
        sortByLosses();

        onView(withText("Houston Rockets")).check(matches(isDisplayed()));
    }

    @Test
    public void checkFirstTeamSortedByLossesAfterO() {
        sortByLosses();

        onView(withText("Houston Rockets")).check(matches(isDisplayed()));
        // Rotate the screen
        TestUtils.rotateOrientation(getCurrentActivity());

        onView(withText("Houston Rockets")).check(matches(isDisplayed()));
    }

    @Test
    public void scrollToItemBelowFold_checkItsTex() {
        sortByFullName();

        // First scroll to the position that needs to be matched and click on it.
        onView(ViewMatchers.withId(R.id.teams_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(29, scrollTo()));

        // Match the text in an item below the fold and check that it's displayed.
        onView(withText("Washington")).check(matches(isDisplayed()));
    }


    @Test
    public void scrollToItemBelowFold_checkItsText() {
        sortByFullName();

        // First scroll to the position that needs to be matched and click on it.
        onView(ViewMatchers.withId(R.id.teams_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Match the text in an item below the fold and check that it's displayed.
        onView(withText("Bazemore, Kent")).check(matches(isDisplayed()));
    }


    private static void sortByFullName() {
        onView(withId(R.id.menu_sort)).perform(click());
        onView(withText(R.string.sort_by_name)).perform(click());
    }

    private static void sortByWins() {
        onView(withId(R.id.menu_sort)).perform(click());
        onView(withText(R.string.sort_by_wins)).perform(click());
    }

    private static void sortByLosses() {
        onView(withId(R.id.menu_sort)).perform(click());
        onView(withText(R.string.sort_by_losses)).perform(click());
    }


}

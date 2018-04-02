package com.billbao.sample.teams;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.billbao.core.util.EspressoIdlingResource;
import com.billbao.sample.R;
import com.billbao.sample.TestUtils;

import org.junit.After;
import org.junit.Before;
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

/**
 * Tests for the tasks screen, the main screen which contains a list of all tasks.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class TeamListActivityTest {
    private static final int LAST_ITEM_NUM = 29;
    private static final String FIRST_TEAM_BY_NAME = "Atlanta Hawks";
    private static final String FIRST_TEAM_BY_WINS = "Memphis Grizzlies";
    private static final String FIRST_TEAM_BY_LOSSES = "Houston Rockets";
    private static final String LAST_TEAM_BY_NAME = "Washington Wizards";

    /**
     * {@link ActivityTestRule} is a JUnit {@link Rule @Rule} to launch your activity under test.
     * <p>
     * Rules are interceptors which are executed for each test method and are important building
     * blocks of Junit tests.
     */
    @Rule
    public ActivityTestRule<TeamListActivity> mActivityRule = new ActivityTestRule<>(TeamListActivity.class);

    /**
     * Prepare your test fixture for this test. In this case we register an IdlingResources with
     * Espresso. IdlingResource resource is a great way to tell Espresso when your app is in an
     * idle state. This helps Espresso to synchronize your test actions, which makes tests
     * significantly more reliable.
     */
    @Before
    public void registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource());
    }

    /**
     * Unregister your Idling Resource so it can be garbage collected and does not leak any memory.
     */
    @After
    public void unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource());
    }

    @Test
    public void checkFirstTeamSortedByFullName() {
        sortByFullName();
        onView(withText(FIRST_TEAM_BY_NAME)).check(matches(isDisplayed()));
    }

    @Test
    public void checkFirstTeamSortedByWins() {
        sortByWins();
        onView(withText(FIRST_TEAM_BY_WINS)).check(matches(isDisplayed()));
    }

    @Test
    public void checkFirstTeamSortedByLosses() {
        sortByLosses();
        onView(withText(FIRST_TEAM_BY_LOSSES)).check(matches(isDisplayed()));
    }

    @Test
    public void checkScrollToLastTeamSortedByName() {
        sortByFullName();
        // First scroll to the position that needs to be matched and click on it.
        onView(ViewMatchers.withId(R.id.teams_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(LAST_ITEM_NUM, scrollTo()));
        // Match the text in an item below the fold and check that it's displayed.
        onView(withText(LAST_TEAM_BY_NAME)).check(matches(isDisplayed()));
    }

    /**
     * The following tests are related to device rotation
     */
    @Test
    public void checkFirstTeamSortedByFullNameAfterRotation() {
        checkFirstTeamSortedByFullName();

        // Rotate the screen
        TestUtils.rotateOrientation(getCurrentActivity());

        checkFirstTeamSortedByFullName();
    }

    @Test
    public void checkFirstTeamSortedByWinsAfterRotation() {
        checkFirstTeamSortedByWins();

        // Rotate the screen
        TestUtils.rotateOrientation(getCurrentActivity());

        checkFirstTeamSortedByWins();
    }

    @Test
    public void checkFirstTeamSortedByLossesAfterRotation() {
        checkFirstTeamSortedByLosses();

        // Rotate the screen
        TestUtils.rotateOrientation(getCurrentActivity());

        checkFirstTeamSortedByLosses();
    }

    @Test
    public void checkScrollToLastTeamSortedByNameAfterRotation() {
        checkScrollToLastTeamSortedByName();

        // Rotate the screen
        TestUtils.rotateOrientation(getCurrentActivity());

        checkScrollToLastTeamSortedByName();
    }

    /**
     * The test utils are listed as follows.
     */
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

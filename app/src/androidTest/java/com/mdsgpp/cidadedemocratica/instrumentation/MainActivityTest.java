package com.mdsgpp.cidadedemocratica.instrumentation;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.hasToString;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;


import com.mdsgpp.cidadedemocratica.MainActivity;
import com.mdsgpp.cidadedemocratica.R;
import com.mdsgpp.cidadedemocratica.model.Entity;
import com.mdsgpp.cidadedemocratica.model.Tag;
import com.mdsgpp.cidadedemocratica.model.User;
import com.mdsgpp.cidadedemocratica.persistence.DataUpdateListener;
import com.mdsgpp.cidadedemocratica.persistence.EntityContainer;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class);

    @Test
    public void shouldGetNewInstanceOfMainActivity() {
        assertThat(mActivityRule.getActivity().findViewById(R.id.button_tags),isDisplayed());
    }

    @Test
    public void shouldShowProposalList() {

        onView(withId(R.id.buttom_proposalID)).perform(click());
        onView(withText("Tudo")).check(matches(isDisplayed()));
        onView(withText("Por aqui")).check(matches(isDisplayed()));
        onView(withText("Localidade")).check(matches(isDisplayed()));

    }

    @Test
    public void shouldShowTagsList() throws InterruptedException {
        //List
        final CountDownLatch latch = new CountDownLatch(1);


        EntityContainer.getInstance(Tag.class).setDataUpdateListener(new DataUpdateListener() {
            @Override
            public void dataUpdated(Class<? extends Entity> entityType) {
                latch.countDown();
            }
        });

        latch.await(500, TimeUnit.SECONDS);

        onView(withId(R.id.button_tags)).perform(click());
        onView(withText("cidadania")).check(matches(isDisplayed()));

    }

    @Test
    public void shouldShowUsersList() throws InterruptedException {
        //List
        final CountDownLatch latch = new CountDownLatch(1);

        EntityContainer.getInstance(User.class).setDataUpdateListener(new DataUpdateListener() {
            @Override
            public void dataUpdated(Class<? extends Entity> entityType) {
                latch.countDown();
            }
        });

        latch.await(500, TimeUnit.SECONDS);

        onView(withId(R.id.buttom_userID)).perform(click());
        onView(withText("Henrique Parra Parra Filho")).check(matches(isDisplayed()));

    }

}

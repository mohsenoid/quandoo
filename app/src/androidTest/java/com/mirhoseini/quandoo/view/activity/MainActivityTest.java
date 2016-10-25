package com.mirhoseini.quandoo.view.activity;

import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.mirhoseini.quandoo.QuandooApplication;
import com.mirhoseini.quandoo.R;
import com.mirhoseini.quandoo.di.component.ApplicationTestComponent;
import com.mirhoseini.quandoo.domain.client.QuandooApi;
import com.mirhoseini.quandoo.domain.model.CustomerResponse;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import rx.Observable;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.mirhoseini.quandoo.test.support.Matcher.childAtPosition;
import static org.hamcrest.Matchers.allOf;
import static org.mockito.Mockito.when;

/**
 * Created by Mohsen on 24/10/2016.
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private static final int TEST_CHARACTER_ID = 1;
    private static final String TEST_CUSTOMER_FIRST_NAME = "Mohsen";
    private static final String TEST_CUSTOMER_LAST_NAME = "Mirhoseini Argi";

    @Rule
    public ActivityTestRule<MainActivity> mainActivity = new ActivityTestRule<>(
            MainActivity.class,
            true,
            // do not launch the activity immediately
            false);

    @Inject
    QuandooApi api;

    CustomerResponse[] expectedResult;

    @Before
    public void setUp() {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        QuandooApplication app = (QuandooApplication) instrumentation.getTargetContext().getApplicationContext();
        ApplicationTestComponent component = (ApplicationTestComponent) QuandooApplication.getComponent();
        component.inject(this);

        // Set up the stub we want to return in the mock
        expectedResult = new CustomerResponse[1];
        expectedResult[0] = new CustomerResponse(TEST_CHARACTER_ID, TEST_CUSTOMER_FIRST_NAME, TEST_CUSTOMER_LAST_NAME);

        // Set up the mock
        when(api.getCustomers())
                .thenReturn(Observable.just(expectedResult));
    }

    @Test
    public void shouldBeAbleToShowTestCustomer() {
        // Launch the activity
        mainActivity.launchActivity(new Intent());

        // Check that the view is what we expect it to be
        ViewInteraction firstName = onView(
                allOf(withId(R.id.first), withText(TEST_CUSTOMER_FIRST_NAME),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.instanceOf(android.widget.FrameLayout.class),
                                        0),
                                1),
                        isDisplayed()));
        firstName.check(matches(withText(TEST_CUSTOMER_FIRST_NAME)));

        ViewInteraction lastName = onView(
                allOf(withId(R.id.last), withText(TEST_CUSTOMER_LAST_NAME),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.instanceOf(android.widget.FrameLayout.class),
                                        0),
                                2),
                        isDisplayed()));
        lastName.check(matches(withText(TEST_CUSTOMER_LAST_NAME)));
    }

}


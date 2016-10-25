package com.mirhoseini.quandoo.view.fragment;

import com.mirhoseini.quandoo.BuildConfig;
import com.mirhoseini.quandoo.R;
import com.mirhoseini.quandoo.test.support.ShadowSnackbar;
import com.mirhoseini.quandoo.view.activity.MainActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static com.mirhoseini.quandoo.test.support.Assert.assertProgressDialogIsShown;
import static com.mirhoseini.quandoo.test.support.Assert.assertSnackbarIsShown;
import static com.mirhoseini.quandoo.test.support.Assert.assertViewIsNotVisible;
import static com.mirhoseini.quandoo.test.support.Assert.assertViewIsVisible;
import static com.mirhoseini.quandoo.test.support.ViewLocator.getView;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by Mohsen on 24/10/2016.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, shadows = {ShadowSnackbar.class})
public class CustomersFragmentRobolectricTest {

    private MainActivity activity;
    private CustomersFragment fragment;

    @Before
    public void setUp() throws Exception {
        // setup activity
        activity = Robolectric.setupActivity(MainActivity.class);
        assertNotNull(activity);

        // setup fragment
        fragment = (CustomersFragment) activity.getSupportFragmentManager().findFragmentByTag(MainActivity.TAG_CUSTOMERS_FRAGMENT);
        assertNotNull(fragment);

    }

    @Test
    public void testShowProgress() throws Exception {
        fragment.showProgress();

        assertProgressDialogIsShown(R.string.please_wait);
    }

    @Test
    public void testHideProgress() throws Exception {
        fragment.showProgress();
        fragment.hideProgress();

        assertProgressDialogIsShown(R.string.please_wait);
    }

    @Test
    public void testShowError() throws Exception {
        fragment.showError(new Throwable("unknown error"));

        assertSnackbarIsShown(R.string.retry_message);
    }

    @Test
    public void testShowRetryMessage() throws Exception {
        fragment.showRetryMessage(new Throwable("unknown error"));

        assertSnackbarIsShown(R.string.retry_message);
    }

    @Test
    public void testShowOfflineMessage() throws Exception {
        fragment.showOfflineMessage();

        assertSnackbarIsShown(R.string.offline_message);
        assertViewIsNotVisible(getView(activity, R.id.no_network));
        assertViewIsVisible(getView(activity, R.id.list));
    }

    @Test
    public void testShowNoInternetMessage() throws Exception {
        fragment.showNoInternetMessage();

        assertSnackbarIsShown(R.string.no_internet_message);
        assertViewIsVisible(getView(activity, R.id.no_network));
        assertViewIsNotVisible(getView(activity, R.id.list));
    }

    @Test
    public void testOnDestroy() throws Exception {
        fragment.onDetach();

        assertNull(fragment.listener);
        assertNull(fragment.presenter);
    }

}
package com.mirhoseini.quandoo.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.mirhoseini.quandoo.R;
import com.mirhoseini.quandoo.database.model.CustomerModel;
import com.mirhoseini.quandoo.di.component.ApplicationComponent;
import com.mirhoseini.quandoo.view.fragment.TablesFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by Mohsen on 24/10/2016.
 */

public class TablesActivity extends BaseActivity implements TablesFragment.OnListFragmentInteractionListener {

    private static final String ARG_CUSTOMER = "customer";
    private static final String TAG_TABLES_FRAGMENT = "tables";

    // injecting dependencies via Dagger
    @Inject
    Context context;
    @Inject
    Resources resources;

    // injecting views via ButterKnife
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private TablesFragment tablesFragment;

    public static Intent newIntent(Context context, CustomerModel customer) {
        Intent intent = new Intent(context, TablesActivity.class);
        intent.putExtra(ARG_CUSTOMER, customer);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tables);

        // inject views using ButterKnife
        ButterKnife.bind(this);

        CustomerModel customer = (CustomerModel) getIntent().getExtras().getSerializable(ARG_CUSTOMER);

        if (null == customer)
            finish();

        setupToolbar(customer.getFullName());

        if (null == savedInstanceState) {
            tablesFragment = TablesFragment.newInstance(customer);
            attachFragments();
        } else {
            tablesFragment = (TablesFragment) getSupportFragmentManager().findFragmentByTag(TAG_TABLES_FRAGMENT);
        }

        Timber.d("Tables Activity Created");
    }

    private void attachFragments() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.tables_fragment, tablesFragment, TAG_TABLES_FRAGMENT);
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void setupToolbar(String customerName) {
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.logo);
        getSupportActionBar().setTitle(customerName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void showMessage(String message) {
        Timber.d("Showing Message: %s", message);

        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showOfflineMessage() {
        Timber.d("Showing Offline Message");

        Snackbar.make(toolbar, R.string.offline_message, Snackbar.LENGTH_LONG)
                .setAction(R.string.go_online, v -> startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS)))
                .setActionTextColor(Color.GREEN)
                .show();
    }

    @Override
    public void showNoInternetMessage() {
        Timber.d("Showing No Internet Message");

        Snackbar.make(toolbar, R.string.no_internet_message, Snackbar.LENGTH_LONG)
                .setAction(R.string.go_online, v -> startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS)))
                .setActionTextColor(Color.RED)
                .show();
    }

    @Override
    protected void injectDependencies(ApplicationComponent component) {
        component.inject(this);
    }

    @Override
    public void finishBooking() {
        finish();
    }

}

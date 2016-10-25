package com.mirhoseini.quandoo.view.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.mirhoseini.quandoo.R;
import com.mirhoseini.quandoo.database.model.CustomerModel;
import com.mirhoseini.quandoo.di.component.ApplicationComponent;
import com.mirhoseini.quandoo.view.fragment.CustomersFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by Mohsen on 24/10/2016.
 */

public class MainActivity extends BaseActivity implements CustomersFragment.OnListFragmentInteractionListener {

    public static final String TAG_CUSTOMERS_FRAGMENT = "customers_fragment";

    // injecting dependencies via Dagger
    @Inject
    Context context;
    @Inject
    Resources resources;

    // injecting views via ButterKnife
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private CustomersFragment customersFragment;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // inject views using ButterKnife
        ButterKnife.bind(this);

        setupToolbar();

        if (null == savedInstanceState) {
            customersFragment = CustomersFragment.newInstance();
            attachFragments();
        } else {
            customersFragment = (CustomersFragment) getSupportFragmentManager().findFragmentByTag(TAG_CUSTOMERS_FRAGMENT);
        }

        Timber.d("Main Activity Created");
    }

    @Override
    protected void injectDependencies(ApplicationComponent component) {
        component.inject(this);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.logo);
    }

    private void attachFragments() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.customers_fragment, customersFragment, TAG_CUSTOMERS_FRAGMENT);
        fragmentTransaction.commitAllowingStateLoss();
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
    public void showTables(CustomerModel customer) {
        startActivity(TablesActivity.newIntent(this, customer));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) MainActivity.this.getSystemService(Context.SEARCH_SERVICE);

        searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(MainActivity.this.getComponentName()));

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    if (newText.isEmpty())
                        customersFragment.clearFilter();
                    else
                        customersFragment.doSearch(newText);
                    return true;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {
                    if (query.isEmpty())
                        customersFragment.clearFilter();
                    else
                        customersFragment.doSearch(query);

                    return true;
                }
            });
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();

        clearSearch();
    }

    private void clearSearch() {
        if (null != searchView) {
            searchView.setQuery("", false);
            searchView.clearFocus();
            searchView.setIconified(true);
        }
    }

}

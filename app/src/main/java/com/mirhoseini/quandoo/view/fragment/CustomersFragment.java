package com.mirhoseini.quandoo.view.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.mirhoseini.quandoo.Presentation.CustomerPresenter;
import com.mirhoseini.quandoo.R;
import com.mirhoseini.quandoo.database.model.CustomerModel;
import com.mirhoseini.quandoo.di.component.ApplicationComponent;
import com.mirhoseini.quandoo.di.module.AppCustomerModule;
import com.mirhoseini.quandoo.util.GridSpacingItemDecoration;
import com.mirhoseini.quandoo.view.BaseView;
import com.mirhoseini.quandoo.view.CustomerView;
import com.mirhoseini.quandoo.view.adapter.CustomersRecyclerViewAdapter;
import com.mirhoseini.utils.Utils;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by Mohsen on 24/10/2016.
 */

public class CustomersFragment extends BaseFragment implements CustomerView {

    @Inject
    Context context;
    @Inject
    Resources resources;
    @Inject
    CustomerPresenter presenter;
    @Inject
    LinearLayoutManager layoutManager;
    @Inject
    GridSpacingItemDecoration gridSpacingItemDecoration;
    @Inject
    CustomersRecyclerViewAdapter adapter;
    @Inject
    OnListFragmentInteractionListener listener;

    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.no_network)
    View noNetwork;
    private FirebaseAnalytics firebaseAnalytics;
    private ProgressDialog progressDialog;
    private CompositeSubscription subscriptions = new CompositeSubscription();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CustomersFragment() {
    }

    public static CustomersFragment newInstance() {
        return new CustomersFragment();
    }

    @OnClick(R.id.no_network)
    void onNoNetworkClick() {
        presenter.loadCustomersData(Utils.isConnected(context));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer, container, false);

        ButterKnife.bind(this, view);

        initRecyclerView();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // Obtain the FirebaseAnalytics instance.
        firebaseAnalytics = FirebaseAnalytics.getInstance(context);

        subscriptions.add(
                adapter.asObservable()
                        .filter(customer -> null != listener)
                        .subscribe(listener::showTables));
    }

    @Override
    public void onResume() {
        super.onResume();

        presenter.loadCustomersData(Utils.isConnected(context));
    }

    @Override
    protected void injectDependencies(ApplicationComponent component, Context context) {
        component
                .plus(new AppCustomerModule(context, this))
                .inject(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;

        presenter.destroy();
        presenter = null;

        subscriptions.unsubscribe();
    }

    @Override
    public void showMessage(String message) {
        if (null != listener) {
            listener.showMessage(message);
        }
    }

    @Override
    public void showOfflineMessage() {
        if (null != listener) {
            listener.showOfflineMessage();
        }
    }

    @Override
    public void showNoInternetMessage() {
        noNetwork.setVisibility(View.VISIBLE);
        list.setVisibility(View.GONE);

        if (null != listener) {
            listener.showNoInternetMessage();
        }
    }

    private void initRecyclerView() {
        list.setLayoutManager(layoutManager);
        list.addItemDecoration(gridSpacingItemDecoration);
    }

    @Override
    public void showError(Throwable throwable) {
        Timber.e(throwable, "Error!");

        showRetryMessage(throwable);
    }

    @Override
    public void showRetryMessage(Throwable throwable) {
        Timber.e(throwable, "Retry error!");

        Snackbar.make(list, resources.getString(R.string.retry_message), Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry, v -> presenter.loadCustomersData(Utils.isConnected(context)))
                .show();
    }

    @Override
    public void showProgress() {
        if (null != progressDialog)
            progressDialog.dismiss();

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setTitle(R.string.please_wait);
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        if (null != progressDialog)
            progressDialog.dismiss();
    }

    @Override
    public void setCustomersData(ArrayList<CustomerModel> customers) {
        Timber.i("Loaded customers data.");

        noNetwork.setVisibility(View.GONE);
        list.setVisibility(View.VISIBLE);

        adapter.setCustomers(customers);
        list.setAdapter(adapter);
    }

    public void doSearch(String query) {
        logFirebaseAnalyticsEvent(query);

        presenter.doSearch(query);
    }

    private void logFirebaseAnalyticsEvent(String query) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SEARCH_TERM, query);
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SEARCH, bundle);
    }

    public void clearFilter() {
        presenter.clearFilter();
    }

    public interface OnListFragmentInteractionListener extends BaseView {

        void showTables(CustomerModel customer);

    }

}

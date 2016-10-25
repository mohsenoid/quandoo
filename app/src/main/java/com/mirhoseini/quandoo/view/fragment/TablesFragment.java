package com.mirhoseini.quandoo.view.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mirhoseini.quandoo.Presentation.TablePresenter;
import com.mirhoseini.quandoo.R;
import com.mirhoseini.quandoo.database.model.BookingModel;
import com.mirhoseini.quandoo.database.model.CustomerModel;
import com.mirhoseini.quandoo.database.model.TableModel;
import com.mirhoseini.quandoo.di.component.ApplicationComponent;
import com.mirhoseini.quandoo.di.module.AppTableModule;
import com.mirhoseini.quandoo.util.GridSpacingItemDecoration;
import com.mirhoseini.quandoo.view.BaseView;
import com.mirhoseini.quandoo.view.TableView;
import com.mirhoseini.quandoo.view.adapter.TablesRecyclerViewAdapter;
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

public class TablesFragment extends BaseFragment implements TableView {

    private static final String ARG_CUSTOMER = "customer";

    @Inject
    public TablePresenter presenter;
    @Inject
    Context context;
    @Inject
    Resources resources;
    @Inject
    GridLayoutManager layoutManager;
    @Inject
    GridSpacingItemDecoration gridSpacingItemDecoration;
    @Inject
    TablesRecyclerViewAdapter adapter;
    @Inject
    OnListFragmentInteractionListener listener;

    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.no_network)
    View noNetwork;
    private ProgressDialog progressDialog;
    private CompositeSubscription subscriptions = new CompositeSubscription();
    private CustomerModel customer;
    private AlertDialog bookingDialog;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TablesFragment() {
    }

    public static TablesFragment newInstance(CustomerModel customer) {
        TablesFragment fragment = new TablesFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_CUSTOMER, customer);
        fragment.setArguments(bundle);
        return fragment;
    }

    @OnClick(R.id.no_network)
    void onNoNetworkClick() {
        presenter.loadTablesData(Utils.isConnected(context));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        customer = (CustomerModel) getArguments().getSerializable(ARG_CUSTOMER);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        subscriptions.add(
                adapter.asObservable()
                        .subscribe(table -> bookTable(table, customer)));
    }

    private void bookTable(TableModel table, CustomerModel customer) {
        if (table.isReserved() || adapter.checkTableIsBooked(table))
            showMessage(resources.getString(R.string.table_unavailable));
        else
            presenter.bookTable(table, customer);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tables, container, false);

        ButterKnife.bind(this, view);

        initRecyclerView();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        presenter.loadTablesData(Utils.isConnected(context));
    }

    @Override
    protected void injectDependencies(ApplicationComponent component, Context context) {
        component
                .plus(new AppTableModule(context, this, 3))
                .inject(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();

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
    public void showRetryMessage(Throwable throwable) {
        Timber.e(throwable, "Retry error!");

        Snackbar.make(list, resources.getString(R.string.retry_message), Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry, v -> presenter.loadTablesData(Utils.isConnected(context)))
                .show();
    }

    @Override
    public void showError(Throwable throwable) {
        Timber.e(throwable, "Error!");

        Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_LONG).show();
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
    public void setTablesData(ArrayList<TableModel> tables, ArrayList<BookingModel> bookings) {
        if (tables.size() > 0) {
            list.setVisibility(View.VISIBLE);
            noNetwork.setVisibility(View.GONE);

            adapter.setTables(tables, bookings);
            list.setAdapter(adapter);
        } else {
            list.setVisibility(View.GONE);
            noNetwork.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showBookingDialog(TableModel table, CustomerModel customer) {
        if (null != bookingDialog)
            bookingDialog.dismiss();

        bookingDialog = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.booking_title)
                .setMessage(String.format(resources.getString(R.string.booking_message), table.getNumber(), customer.getFullName()))
                .setPositiveButton(R.string.book_table, (dialog, which) -> presenter.doBooking(table, customer))
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    @Override
    public void finishBooking() {
        showMessage(resources.getString(R.string.table_booked));

        if (null != listener)
            listener.finishBooking();
    }

    public interface OnListFragmentInteractionListener extends BaseView {

        void finishBooking();

    }

}

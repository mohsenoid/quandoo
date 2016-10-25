package com.mirhoseini.quandoo.view.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jakewharton.rxbinding.view.RxView;
import com.mirhoseini.quandoo.BR;
import com.mirhoseini.quandoo.R;
import com.mirhoseini.quandoo.database.model.CustomerModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by Mohsen on 24/10/2016.
 */

public class CustomersRecyclerViewAdapter extends RecyclerView.Adapter<CustomerViewHolder> {

    private final PublishSubject<CustomerModel> notify = PublishSubject.create();
    private ArrayList<CustomerModel> customers = new ArrayList<>();

    @Inject
    public CustomersRecyclerViewAdapter() {
    }

    public void setCustomers(List<CustomerModel> customers) {
        if (null != customers)
            this.customers = new ArrayList<>(customers);
    }

    @Override
    public CustomerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_customer, parent, false);
        return new CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CustomerViewHolder holder, int position) {

        final CustomerModel customer = customers.get(position);

        holder.customer = customer;
        holder.getBinding().setVariable(BR.customer, customer);
        holder.getBinding().executePendingBindings();

        RxView.clicks(holder.view)
                .map(aVoid -> holder.customer)
                .subscribe(notify::onNext);
    }

    public Observable<CustomerModel> asObservable() {
        return notify.asObservable();
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }

}

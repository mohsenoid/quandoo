package com.mirhoseini.quandoo.view.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mirhoseini.quandoo.database.model.CustomerModel;

/**
 * Created by Mohsen on 24/10/2016.
 */

public class CustomerViewHolder extends RecyclerView.ViewHolder {

    public final View view;
    private final ViewDataBinding binding;
    CustomerModel customer;

    public CustomerViewHolder(View view) {
        super(view);
        this.view = view;

        binding = DataBindingUtil.bind(view);

    }

    public ViewDataBinding getBinding() {
        return binding;
    }

}

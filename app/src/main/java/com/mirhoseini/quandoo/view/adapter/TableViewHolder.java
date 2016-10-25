package com.mirhoseini.quandoo.view.adapter;

import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mirhoseini.quandoo.R;
import com.mirhoseini.quandoo.database.model.TableModel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mohsen on 24/10/2016.
 */

@BindingMethods({
        @BindingMethod(type = android.widget.ImageView.class,
                attribute = "app:srcCompat",
                method = "setImageResource")})
public class TableViewHolder extends RecyclerView.ViewHolder {

    public final View view;
    private final ViewDataBinding binding;
    @BindView(R.id.icon)
    AppCompatImageView icon;
    @BindView(R.id.state)
    TextView state;
    TableModel table;
    boolean isBooked;

    public TableViewHolder(View view) {
        super(view);
        this.view = view;

        ButterKnife.bind(this, view);

        binding = DataBindingUtil.bind(view);
    }

    public ViewDataBinding getBinding() {
        return binding;
    }

    public void setBooked(boolean isBooked) {
        this.isBooked = isBooked;
        if (isBooked) {
            icon.setImageResource(R.drawable.ic_table_occupied);
            state.setText(R.string.booked);
        }
    }

}

package com.mirhoseini.quandoo.view.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jakewharton.rxbinding.view.RxView;
import com.mirhoseini.quandoo.BR;
import com.mirhoseini.quandoo.R;
import com.mirhoseini.quandoo.database.model.BookingModel;
import com.mirhoseini.quandoo.database.model.TableModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by Mohsen on 24/10/2016.
 */

public class TablesRecyclerViewAdapter extends RecyclerView.Adapter<TableViewHolder> {

    private final PublishSubject<TableModel> notify = PublishSubject.create();
    private ArrayList<TableModel> tables = new ArrayList<>();
    private ArrayList<BookingModel> bookings = new ArrayList<>();

    @Inject
    public TablesRecyclerViewAdapter() {
    }

    public void setTables(List<TableModel> tables, ArrayList<BookingModel> bookings) {
        if (null != tables)
            this.tables = new ArrayList<>(tables);

        this.bookings = bookings;
    }

    @Override
    public TableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_table, parent, false);
        return new TableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TableViewHolder holder, int position) {

        final TableModel table = tables.get(position);

        holder.table = table;
        holder.getBinding().setVariable(BR.table, table);
        holder.getBinding().executePendingBindings();
        holder.setBooked(checkTableIsBooked(table));

        RxView.clicks(holder.view)
                .map(aVoid -> holder.table)
                .subscribe(notify::onNext);
    }

    public boolean checkTableIsBooked(TableModel table) {
        if (null != bookings)
            for (BookingModel booking : bookings) {
                if (booking.getTable().getId() == table.getId())
                    return true;
            }

        return false;
    }

    public Observable<TableModel> asObservable() {
        return notify.asObservable();
    }

    @Override
    public int getItemCount() {
        return tables.size();
    }

}

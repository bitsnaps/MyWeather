package com.ssheetz.myweather;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ssheetz.myweather.fragments.ItemDetailFragment;
import com.ssheetz.myweather.model.Cities;
import com.ssheetz.myweather.model.City;

import java.util.Locale;


/**
 * Connects the {@link Cities} collection to a RecyclerView to display the list of the user's cities.
 * Also provides delete city functionality.
 */
public class CityListAdapter
        extends RecyclerView.Adapter<CityListAdapter.ViewHolder> {

    private final ItemListActivity parentActivity;
    private final Cities cities;
    private final boolean twoPane;
    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            City item = (City) view.getTag();
            if (twoPane) {
                Bundle arguments = new Bundle();
                arguments.putString(ItemDetailFragment.ARG_ITEM_ID, item.getId());
                ItemDetailFragment fragment = new ItemDetailFragment();
                fragment.setArguments(arguments);
                parentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.item_detail_container, fragment)
                        .commit();
            } else {
                Context context = view.getContext();
                Intent intent = new Intent(context, ItemDetailActivity.class);
                intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, item.getId());

                context.startActivity(intent);
            }
        }
    };

    CityListAdapter(ItemListActivity parent,
                    Cities cities,
                    boolean twoPane) {
        this.cities = cities;
        parentActivity = parent;
        this.twoPane = twoPane;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final City city = cities.getCities().get(position);
        holder.mContentView.setText(city.getLabel());
        holder.itemView.setTag(city);
        holder.itemView.setOnClickListener(onClickListener);
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promptForDelete(city);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cities.getCities().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView mIdView;
        final TextView mContentView;
        final ImageButton deleteBtn;

        ViewHolder(View view) {
            super(view);
            mIdView = view.findViewById(R.id.id_text);
            mContentView = view.findViewById(R.id.content);
            deleteBtn = view.findViewById(R.id.delete);
        }
    }

    private void promptForDelete(final City city) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        delete(city);
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };
        String promptStr = String.format(Locale.getDefault(), "%s %s?", parentActivity.getString(R.string.button_delete), city.getLabel());
        new AlertDialog.Builder(parentActivity)
                .setMessage(promptStr)
                .setPositiveButton(parentActivity.getString(R.string.button_yes), dialogClickListener)
                .setNegativeButton(parentActivity.getString(R.string.button_no), dialogClickListener)
                .show();
    }

    private void delete(City city) {
        if (parentActivity != null) {
            parentActivity.onCityDeleted(city.getId());
        }
    }
}

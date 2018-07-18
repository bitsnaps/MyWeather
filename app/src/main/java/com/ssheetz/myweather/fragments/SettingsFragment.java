package com.ssheetz.myweather.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.ssheetz.myweather.OnCityChangeListener;
import com.ssheetz.myweather.R;


/**
 * Shows the user a dialog box with settings.
 */
public class SettingsFragment extends DialogFragment  {

    public SettingsFragment() {
        // Empty constructor required for DialogFragment
    }

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        Window window = dialog.getWindow();
        if (window != null) {
            window.requestFeature(Window.FEATURE_NO_TITLE);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        return dialog;
    }

    @Override
    public void onResume() {
        super.onResume();
        setDialogPosition();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button doneButton = view.findViewById(R.id.done);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        Button deleteAllBtn = view.findViewById(R.id.delete_all);
        deleteAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promptForDeleteAll();
            }
        });
    }

    private void setDialogPosition() {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenWidth = size.x;
        int screenHeight = size.y;
        Window window = getDialog().getWindow();
        if (window == null) {
            return;
        }
        window.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
        WindowManager.LayoutParams params = window.getAttributes();
        params.height = screenHeight - FragmentUtils.dpToPx(getActivity(), 80);
        params.width = screenWidth - FragmentUtils.dpToPx(getActivity(),80);
        window.setAttributes(params);
    }

    private void promptForDeleteAll() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        Activity activity = getActivity();
                        if (activity instanceof OnCityChangeListener) {
                            ((OnCityChangeListener)activity).onAllCitiesDeleted();
                        }
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };
        Activity activity = getActivity();
        new AlertDialog.Builder(activity)
                .setMessage(activity.getString(R.string.prompt_delete_all))
                .setPositiveButton(activity.getString(R.string.button_yes), dialogClickListener)
                .setNegativeButton(activity.getString(R.string.button_no), dialogClickListener)
                .show();
    }
}

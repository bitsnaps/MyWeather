package com.ssheetz.myweather.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
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
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.ssheetz.myweather.R;


/**
 * Shows the user a dialog box with a web view in it, which will load the specified URL.
 */
public class WebViewerFragment extends DialogFragment  {

    public WebViewerFragment() {
        // Empty constructor required for DialogFragment
    }

    public static WebViewerFragment newInstance(String url, String title) {
        WebViewerFragment frag = new WebViewerFragment();
        Bundle args = new Bundle();
        args.putString("url", url);
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
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
        return inflater.inflate(R.layout.fragment_webviewer, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String url = getArguments().getString("url", "");
        String title = getArguments().getString("title", "");
        Button doneButton = view.findViewById(R.id.done);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        TextView titleView = view.findViewById(R.id.title);
        titleView.setText(title);
        WebView webView = view.findViewById(R.id.webview);
        webView.loadUrl(url);
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
}

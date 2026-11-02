package com.gathin.tvhome;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * Frosted Glass Search Dialog for selecting any Indonesian city by typing its name.
 * Initially shows only the search box with hint "Ketik nama kota"; dynamically reveals
 * matching available cities below as the user types.
 */
public final class CitySearchDialog {

    private CitySearchDialog() {
    }

    public static void show(final Activity activity, final Runnable onCityChanged) {
        if (activity == null || activity.isFinishing()) return;

        final float density = activity.getResources().getDisplayMetrics().density;
        final Typeface outfit = activity.getResources().getFont(R.font.inter);
        final Handler uiHandler = new Handler(Looper.getMainLooper());
        final Runnable[] pendingOnlineSearch = new Runnable[1];

        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.Theme_TvHome_GlassDialog);

        // Root Frosted Glass Container
        LinearLayout root = new LinearLayout(activity);
        root.setOrientation(LinearLayout.VERTICAL);
        int padH = dp(20, density);
        int padV = dp(18, density);
        root.setPadding(padH, padV, padH, padV);

        GradientDrawable dialogBg = new GradientDrawable(
                GradientDrawable.Orientation.TL_BR,
                new int[]{0xF2141E30, 0xF20B111E}
        );
        dialogBg.setCornerRadius(dp(20, density));
        dialogBg.setStroke(dp(1.5f, density), 0x4D64D2FF);
        root.setBackground(dialogBg);

        // Header Row: Title + Current City Subtitle
        TextView titleView = new TextView(activity);
        titleView.setText("📍 Ubah Kota Cuaca");
        titleView.setTextSize(17f);
        titleView.setTextColor(0xFFFFFFFF);
        titleView.setTypeface(outfit, Typeface.BOLD);
        titleView.setFontVariationSettings("'wght' 700");
        root.addView(titleView);

        String currentCity = AppLocationManager.getLocationName(activity);
        String currentProvince = AppLocationManager.getActiveProvince(activity);
        TextView subView = new TextView(activity);
        subView.setText("Kota saat ini: " + currentCity + ", " + currentProvince);
        subView.setTextSize(12f);
        subView.setTextColor(0xFF94A3B8);
        subView.setTypeface(outfit);
        subView.setFontVariationSettings("'wght' 500");
        subView.setPadding(0, dp(2, density), 0, dp(12, density));
        root.addView(subView);

        // Search Input Box Container
        LinearLayout searchBox = new LinearLayout(activity);
        searchBox.setOrientation(LinearLayout.HORIZONTAL);
        searchBox.setGravity(Gravity.CENTER_VERTICAL);
        searchBox.setPadding(dp(14, density), dp(10, density), dp(14, density), dp(10, density));

        final GradientDrawable searchBgNormal = new GradientDrawable();
        searchBgNormal.setColor(0x1FFFFFFF);
        searchBgNormal.setCornerRadius(dp(14, density));
        searchBgNormal.setStroke(dp(1.5f, density), 0x40FFFFFF);

        final GradientDrawable searchBgFocused = new GradientDrawable();
        searchBgFocused.setColor(0x2BFFFFFF);
        searchBgFocused.setCornerRadius(dp(14, density));
        searchBgFocused.setStroke(dp(1.8f, density), 0xFF64D2FF);

        searchBox.setBackground(searchBgFocused);

        TextView searchIcon = new TextView(activity);
        searchIcon.setText("🔍");
        searchIcon.setTextSize(14f);
        searchIcon.setPadding(0, 0, dp(10, density), 0);
        searchBox.addView(searchIcon);

        final EditText searchInput = new EditText(activity);
        searchInput.setHint("Ketik nama kota");
        searchInput.setHintTextColor(0x80FFFFFF);
        searchInput.setTextColor(0xFFFFFFFF);
        searchInput.setTextSize(15f);
        searchInput.setTypeface(outfit);
        searchInput.setFontVariationSettings("'wght' 500");
        searchInput.setSingleLine(true);
        searchInput.setBackground(null);
        searchInput.setPadding(0, 0, 0, 0);
        searchInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        searchInput.setImeOptions(EditorInfo.IME_ACTION_SEARCH | EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        searchBox.addView(searchInput, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

        final TextView clearBtn = new TextView(activity);
        clearBtn.setText("✕");
        clearBtn.setTextSize(13f);
        clearBtn.setTextColor(0xB3FFFFFF);
        clearBtn.setPadding(dp(8, density), dp(2, density), dp(4, density), dp(2, density));
        clearBtn.setVisibility(View.GONE);
        clearBtn.setClickable(true);
        clearBtn.setFocusable(true);
        clearBtn.setOnClickListener(v -> {
            searchInput.setText("");
            searchInput.requestFocus();
        });
        searchBox.addView(clearBtn);

        root.addView(searchBox, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        searchInput.setOnFocusChangeListener((v, hasFocus) ->
                searchBox.setBackground(hasFocus ? searchBgFocused : searchBgNormal));

        // Scrollable Results Container (Initially GONE until user types!)
        final ScrollView resultsScroll = new ScrollView(activity);
        resultsScroll.setVerticalScrollBarEnabled(false);
        resultsScroll.setOverScrollMode(View.OVER_SCROLL_NEVER);
        resultsScroll.setClipChildren(true);
        resultsScroll.setClipToPadding(true);
        resultsScroll.setVisibility(View.GONE);

        final LinearLayout resultsContainer = new LinearLayout(activity);
        resultsContainer.setOrientation(LinearLayout.VERTICAL);
        resultsContainer.setPadding(0, dp(10, density), 0, dp(4, density));
        resultsScroll.addView(resultsContainer, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        LinearLayout.LayoutParams scrollLp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        root.addView(resultsScroll, scrollLp);

        builder.setView(root);
        final AlertDialog dialog = builder.create();

        // Helper to select a city station
        final List<AppLocationManager.Station> currentMatches = new ArrayList<>();
        final Runnable renderResults = () -> {
            resultsContainer.removeAllViews();
            String q = searchInput.getText() != null ? searchInput.getText().toString().trim() : "";
            if (q.isEmpty()) {
                resultsScroll.setVisibility(View.GONE);
                return;
            }

            resultsScroll.setVisibility(View.VISIBLE);
            if (currentMatches.isEmpty()) {
                TextView emptyView = new TextView(activity);
                emptyView.setText("Kota \"" + q + "\" tidak ditemukan");
                emptyView.setTextSize(13.5f);
                emptyView.setTextColor(0x99FFFFFF);
                emptyView.setTypeface(outfit);
                emptyView.setPadding(dp(8, density), dp(12, density), dp(8, density), dp(8, density));
                resultsContainer.addView(emptyView);
                return;
            }

            for (int i = 0; i < currentMatches.size(); i++) {
                final AppLocationManager.Station station = currentMatches.get(i);
                LinearLayout row = new LinearLayout(activity);
                row.setOrientation(LinearLayout.VERTICAL);
                row.setPadding(dp(14, density), dp(9, density), dp(14, density), dp(9, density));
                row.setFocusable(true);
                row.setClickable(true);
                row.setBackground(createCityRowBackground(density));

                TextView cityTitle = new TextView(activity);
                cityTitle.setText(station.name);
                cityTitle.setTextSize(14.5f);
                cityTitle.setTextColor(0xFFFFFFFF);
                cityTitle.setTypeface(outfit, Typeface.BOLD);
                cityTitle.setFontVariationSettings("'wght' 600");
                cityTitle.setSingleLine(true);
                row.addView(cityTitle);

                TextView citySub = new TextView(activity);
                String sub = (station.state != null && !station.state.isEmpty())
                        ? (station.place + "  •  " + station.state)
                        : station.place;
                if (station.place != null && station.state != null
                        && station.place.toLowerCase(Locale.ROOT).contains(station.state.toLowerCase(Locale.ROOT))) {
                    sub = station.place;
                }
                citySub.setText(sub);
                citySub.setTextSize(11.5f);
                citySub.setTextColor(0xB3CBD5E1);
                citySub.setTypeface(outfit);
                citySub.setFontVariationSettings("'wght' 400");
                citySub.setSingleLine(true);
                row.addView(citySub);

                row.setOnClickListener(v -> {
                    SoundEffects.playSelect();
                    AppLocationManager.setStation(activity, station);
                    HomeWeather.forget(activity);
                    Toast.makeText(activity, "Lokasi diubah ke: " + station.name, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    if (onCityChanged != null) {
                        onCityChanged.run();
                    }
                });

                LinearLayout.LayoutParams rowLp = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                rowLp.bottomMargin = dp(6, density);
                resultsContainer.addView(row, rowLp);
            }

            // Cap scroll height so dialog stays well-proportioned on screen
            int maxScrollHeight = dp(230, density);
            resultsContainer.measure(
                    View.MeasureSpec.makeMeasureSpec( dp(380, density), View.MeasureSpec.AT_MOST),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            );
            int measuredH = resultsContainer.getMeasuredHeight();
            ViewGroup.LayoutParams lp = resultsScroll.getLayoutParams();
            lp.height = measuredH > maxScrollHeight ? maxScrollHeight : ViewGroup.LayoutParams.WRAP_CONTENT;
            resultsScroll.setLayoutParams(lp);
        };

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (pendingOnlineSearch[0] != null) {
                    uiHandler.removeCallbacks(pendingOnlineSearch[0]);
                }
                final String q = s != null ? s.toString().trim() : "";
                clearBtn.setVisibility(q.isEmpty() ? View.GONE : View.VISIBLE);

                if (q.isEmpty()) {
                    currentMatches.clear();
                    renderResults.run();
                    return;
                }

                // 1. Instant offline search across all 500+ Indonesian Kabupaten & Kota
                List<AppLocationManager.Station> localResults = AppLocationManager.searchIndonesianCities(q);
                currentMatches.clear();
                currentMatches.addAll(localResults);
                resultsScroll.scrollTo(0, 0);
                renderResults.run();

                // 2. Debounced online geocoding fallback for smaller kecamatan/desa in Indonesia
                if (q.length() >= 2) {
                    pendingOnlineSearch[0] = () -> AppLocationManager.searchOnlineIndonesianCities(q, extra -> {
                        if (!dialog.isShowing()) return;
                        String latestQ = searchInput.getText() != null ? searchInput.getText().toString().trim() : "";
                        if (!latestQ.equalsIgnoreCase(q)) return;

                        Set<String> existingNames = new HashSet<>();
                        for (AppLocationManager.Station st : currentMatches) {
                            existingNames.add(st.name.toLowerCase(Locale.ROOT) + "|" + st.state.toLowerCase(Locale.ROOT));
                        }
                        boolean added = false;
                        for (AppLocationManager.Station ext : extra) {
                            String key = ext.name.toLowerCase(Locale.ROOT) + "|" + ext.state.toLowerCase(Locale.ROOT);
                            if (!existingNames.contains(key)) {
                                existingNames.add(key);
                                currentMatches.add(ext);
                                added = true;
                            }
                        }
                        if (added) {
                            renderResults.run();
                        }
                    });
                    uiHandler.postDelayed(pendingOnlineSearch[0], 280);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // Allow pressing Down / Enter from searchInput to focus first matching city
        searchInput.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE
                    || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(searchInput.getWindowToken(), 0);
                }
                if (resultsContainer.getChildCount() > 0) {
                    View firstChild = resultsContainer.getChildAt(0);
                    if (firstChild.isFocusable()) {
                        firstChild.requestFocus();
                        return true;
                    }
                }
            }
            return false;
        });

        searchInput.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                if (resultsContainer.getChildCount() > 0) {
                    View firstChild = resultsContainer.getChildAt(0);
                    if (firstChild.isFocusable()) {
                        firstChild.requestFocus();
                        return true;
                    }
                }
            }
            return false;
        });

        dialog.show();

        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            DisplayMetrics dm = activity.getResources().getDisplayMetrics();
            int targetWidth = Math.min(dp(420, density), (int) (dm.widthPixels * 0.88f));
            window.setLayout(targetWidth, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
                    | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }

        searchInput.requestFocus();
        searchInput.postDelayed(() -> {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.showSoftInput(searchInput, InputMethodManager.SHOW_IMPLICIT);
            }
        }, 120);
    }

    private static StateListDrawable createCityRowBackground(float density) {
        GradientDrawable focused = new GradientDrawable();
        focused.setColor(0x3864D2FF);
        focused.setCornerRadius(dp(12, density));
        focused.setStroke(dp(1.5f, density), 0xFF64D2FF);

        GradientDrawable pressed = new GradientDrawable();
        pressed.setColor(0x2E64D2FF);
        pressed.setCornerRadius(dp(12, density));
        pressed.setStroke(dp(1f, density), 0xAA64D2FF);

        GradientDrawable normal = new GradientDrawable();
        normal.setColor(0x16FFFFFF);
        normal.setCornerRadius(dp(12, density));
        normal.setStroke(dp(1f, density), 0x22FFFFFF);

        StateListDrawable states = new StateListDrawable();
        states.addState(new int[]{android.R.attr.state_focused}, focused);
        states.addState(new int[]{android.R.attr.state_pressed}, pressed);
        states.addState(new int[]{}, normal);
        return states;
    }

    private static int dp(float val, float density) {
        return Math.round(val * density);
    }
}

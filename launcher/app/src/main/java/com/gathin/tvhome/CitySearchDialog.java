package com.gathin.tvhome;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
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
 * The weather-location panel: a glass card like the wallpaper selector, with a search field on
 * top and, below it, the city in use plus the six nearest to it. Typing replaces that list with
 * matching cities from the bundled Indonesian list, and then from the online geocoder.
 *
 * <p>Opened from Home it takes the place and size of the launcher menu panel it came from
 * ({@link MainActivity#placeLikeMenuPanel}); opened elsewhere it is centred.
 */
public final class CitySearchDialog {

    private CitySearchDialog() {
    }

    /** Centred panel, for a screen other than Home. */
    public static void show(final Activity activity, final Runnable onCityChanged) {
        show(activity, 0, 0, false, onCityChanged);
    }

    /**
     * Panel in the launcher menu's place and size ({@code width}, {@code height} as the menu
     * recorded them), under the Candy Box pill.
     */
    public static void show(final Activity activity, final int menuWidth, final int menuHeight,
                            final Runnable onCityChanged) {
        show(activity, menuWidth, menuHeight, true, onCityChanged);
    }

    private static void show(final Activity activity, final int menuWidth, final int menuHeight,
                             final boolean likeMenu, final Runnable onCityChanged) {
        if (activity == null || activity.isFinishing()) return;

        final float density = activity.getResources().getDisplayMetrics().density;
        final Typeface inter = activity.getResources().getFont(R.font.inter);
        final Handler uiHandler = new Handler(Looper.getMainLooper());
        final Runnable[] pendingOnlineSearch = new Runnable[1];

        AlertDialog.Builder builder =
                new AlertDialog.Builder(activity, R.style.Theme_TvHome_GlassDialog_SemiTransparent);

        // The same card as the wallpaper selector: the blurred wallpaper behind it (Backdrop,
        // lined up with the screen), a light veil, and GlassRim's edge at the two corners. The
        // dialog window itself is clear, so this is the only surface drawn.
        LinearLayout root = new LinearLayout(activity);
        root.setOrientation(LinearLayout.VERTICAL);
        int pad = Math.round(6 * density);
        root.setPadding(pad, pad, pad, pad);
        root.setBackground(new GlassDrawable(root, 20 * density, density, null, 0.45f));
        Backdrop.watch(root);

        // Same corners and inner padding as the menu's rows.
        LinearLayout searchBox = new LinearLayout(activity);
        searchBox.setOrientation(LinearLayout.HORIZONTAL);
        searchBox.setGravity(Gravity.CENTER_VERTICAL);
        searchBox.setPadding(Math.round(12 * density), 0, Math.round(12 * density), 0);

        final GradientDrawable searchBgNormal = new GradientDrawable();
        searchBgNormal.setColor(0x1FFFFFFF);
        searchBgNormal.setCornerRadius(13 * density);
        searchBgNormal.setStroke(Math.round(1.5f * density), 0x40FFFFFF);

        final GradientDrawable searchBgFocused = new GradientDrawable();
        searchBgFocused.setColor(0x2BFFFFFF);
        searchBgFocused.setCornerRadius(13 * density);
        searchBgFocused.setStroke(Math.round(1.8f * density), 0xFF64D2FF);

        searchBox.setBackground(searchBgNormal);

        final EditText searchInput = new EditText(activity);
        searchInput.setHint("Cari nama kota");
        searchInput.setHintTextColor(0x80FFFFFF);
        searchInput.setTextColor(0xFFFFFFFF);
        searchInput.setTextSize(16f);
        searchInput.setTypeface(inter, Typeface.BOLD);
        searchInput.setFontVariationSettings("'wght' 700");
        searchInput.setSingleLine(true);
        searchInput.setBackground(null);
        searchInput.setPadding(0, 0, 0, 0);
        searchInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        searchInput.setImeOptions(EditorInfo.IME_ACTION_SEARCH | EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        searchBox.addView(searchInput, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f));

        final TextView clearBtn = new TextView(activity);
        clearBtn.setText("✕");
        clearBtn.setTextSize(13f);
        clearBtn.setTextColor(0xB3FFFFFF);
        clearBtn.setPadding(Math.round(10 * density), 0, 0, 0);
        clearBtn.setVisibility(View.GONE);
        clearBtn.setClickable(true);
        clearBtn.setFocusable(true);
        clearBtn.setOnClickListener(v -> {
            searchInput.setText("");
            searchInput.requestFocus();
        });
        searchBox.addView(clearBtn);

        root.addView(searchBox, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, Math.round(42 * density)));

        // The list below the field: the city in use first, then the six nearest to it.
        final AppLocationManager.Station currentStation = AppLocationManager.getCurrentStation(activity);

        final ScrollView resultsScroll = new ScrollView(activity);
        resultsScroll.setVerticalScrollBarEnabled(false);
        resultsScroll.setOverScrollMode(View.OVER_SCROLL_NEVER);
        resultsScroll.setClipChildren(true);
        resultsScroll.setClipToPadding(true);
        resultsScroll.setFocusable(false);

        final LinearLayout resultsContainer = new LinearLayout(activity);
        resultsContainer.setOrientation(LinearLayout.VERTICAL);
        resultsScroll.addView(resultsContainer, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        // The list fills what is left of the panel's height, so the card keeps the menu's size
        // whichever list is in it, with a gap under the field so the two do not touch.
        LinearLayout.LayoutParams scrollLp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f);
        scrollLp.topMargin = Math.round(10 * density);
        root.addView(resultsScroll, scrollLp);

        builder.setView(root);
        final AlertDialog dialog = builder.create();

        // The OK that opened the panel is still on its way out as the first row takes focus; on
        // this TV its release can land on that row. Swallow clicks for a moment.
        final long openedAt = android.os.SystemClock.uptimeMillis();

        searchInput.setOnFocusChangeListener((v, hasFocus) ->
                searchBox.setBackground(hasFocus ? searchBgFocused : searchBgNormal));

        final List<AppLocationManager.Station> currentMatches = new ArrayList<>();
        // It: the city in use, then the six nearest.
        final List<AppLocationManager.Station> nearby = new ArrayList<>();
        if (currentStation != null) nearby.add(currentStation);
        nearby.addAll(AppLocationManager.getNearbyCities(AppLocationManager.getLatitude(activity),
                AppLocationManager.getLongitude(activity), 6));

        final Runnable renderResults = () -> {
            resultsContainer.removeAllViews();
            String q = searchInput.getText() != null ? searchInput.getText().toString().trim() : "";

            if (q.isEmpty()) {
                resultsScroll.setVisibility(nearby.isEmpty() ? View.GONE : View.VISIBLE);
                for (AppLocationManager.Station station : nearby) {
                    boolean isCurrent = currentStation != null
                            && (station.id.equalsIgnoreCase(currentStation.id)
                            || station.name.equalsIgnoreCase(currentStation.name));
                    resultsContainer.addView(createCityRow(activity, inter, density, dialog,
                            station, isCurrent, openedAt, onCityChanged));
                }
                resultsScroll.scrollTo(0, 0);
                return;
            }

            resultsScroll.setVisibility(View.VISIBLE);
            if (currentMatches.isEmpty()) {
                TextView emptyView = new TextView(activity);
                emptyView.setText("Kota \"" + q + "\" tidak ditemukan");
                emptyView.setTextSize(14f);
                emptyView.setTextColor(0x99FFFFFF);
                emptyView.setTypeface(inter);
                emptyView.setPadding(Math.round(12 * density), Math.round(10 * density),
                        Math.round(12 * density), 0);
                resultsContainer.addView(emptyView);
                return;
            }
            for (AppLocationManager.Station station : currentMatches) {
                resultsContainer.addView(createCityRow(activity, inter, density, dialog,
                        station, false, openedAt, onCityChanged));
            }
            resultsScroll.scrollTo(0, 0);
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

                // 1. Instant offline search across all bundled Indonesian kabupaten and kota.
                List<AppLocationManager.Station> localResults = AppLocationManager.searchIndonesianCities(q);
                currentMatches.clear();
                currentMatches.addAll(localResults);
                renderResults.run();

                // 2. Debounced online geocoding fallback for smaller kecamatan and desa.
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

        // Enter closes the keyboard and moves on to the list; Down does the same straight away.
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

        renderResults.run();

        dialog.show();

        Window window = dialog.getWindow();
        if (window != null) {
            if (likeMenu) {
                // Exactly where the menu panel it was opened from was.
                MainActivity.placeLikeMenuPanel(activity, window, root, menuWidth, menuHeight);
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                        | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                // The dialog's content scrolls, and a scrolling container measures its content
                // without a height to fill: with five rows the card would come out shorter than
                // the menu it opens from. Give it the panel's height outright.
                final int cardHeight = window.getAttributes().height;
                root.post(() -> {
                    ViewGroup.LayoutParams lp = root.getLayoutParams();
                    if (lp != null && cardHeight > 0) {
                        lp.height = cardHeight;
                        root.setLayoutParams(lp);
                    }
                });
            } else {
                DisplayMetrics dm = activity.getResources().getDisplayMetrics();
                int targetWidth = Math.min(Math.round(420 * density), (int) (dm.widthPixels * 0.88f));
                int targetHeight = Math.min(Math.round(460 * density), (int) (dm.heightPixels * 0.8f));
                window.setLayout(targetWidth, targetHeight);
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
                        | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            }
        }

        searchInput.requestFocus();
        if (!likeMenu) {
            searchInput.postDelayed(() -> {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.showSoftInput(searchInput, InputMethodManager.SHOW_IMPLICIT);
                }
            }, 120);
        }
    }

    /** One city, drawn as the launcher menu's and wallpaper panel's rows are: 42dp, 16sp bold. */
    private static LinearLayout createCityRow(final Activity activity, Typeface inter, float density,
                                              final AlertDialog dialog,
                                              final AppLocationManager.Station station,
                                              final boolean isCurrent,
                                              final long openedAt, final Runnable onCityChanged) {
        LinearLayout row = new LinearLayout(activity);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.CENTER_VERTICAL);
        row.setPadding(Math.round(12 * density), 0, Math.round(12 * density), 0);
        row.setFocusable(true);
        row.setClickable(true);
        row.setBackground(null);
        row.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                GradientDrawable focusedBg = new GradientDrawable();
                focusedBg.setColor(0xA0000000);
                focusedBg.setCornerRadius(13 * density);
                v.setBackground(focusedBg);
            } else {
                v.setBackground(null);
            }
        });

        TextView rowTitle = new TextView(activity);
        rowTitle.setText(station.name);
        rowTitle.setTextSize(16f);
        rowTitle.setTextColor(isCurrent ? 0xFF64D2FF : 0xFFFFFFFF);
        rowTitle.setSingleLine(true);
        rowTitle.setEllipsize(android.text.TextUtils.TruncateAt.END);
        rowTitle.setTypeface(inter, Typeface.BOLD);
        rowTitle.setFontVariationSettings("'wght' 700");
        rowTitle.setShadowLayer(3 * density, 0, 1 * density, 0xCC000000);
        row.addView(rowTitle, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

        row.setOnClickListener(v -> {
            if (android.os.SystemClock.uptimeMillis() - openedAt < 400) return;
            SoundEffects.playSelect();
            AppLocationManager.setStation(activity, station);
            HomeWeather.forget(activity);
            Toast.makeText(activity, "Lokasi diubah ke: " + station.name, Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            if (onCityChanged != null) {
                onCityChanged.run();
            }
        });

        row.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, Math.round(42 * density)));
        return row;
    }
}

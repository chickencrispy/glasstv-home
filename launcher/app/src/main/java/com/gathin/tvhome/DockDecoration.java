package com.gathin.tvhome;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * ItemDecoration for the dock row. The glass tray has been removed, so the row shows only the
 * tiles on the wallpaper. Keeps the top and bottom offsets that space the dock from the grid.
 */
public class DockDecoration extends RecyclerView.ItemDecoration {

    private final int dockSize;
    private final float density;

    /** The dock row's area, kept for the focus shape's companion clipping. */
    final RectF area = new RectF();

    DockDecoration(int dockSize, float density) {
        this.dockSize = dockSize;
        this.density = density;
    }

    /** Kept for the caller: with no tray there is no shape to paint over. */
    void setGlider(Glider glider) {
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent,
                               @NonNull RecyclerView.State state) {
        int pos = parent.getChildAdapterPosition(view);
        if (pos >= 0 && pos < dockSize) {
            outRect.top = (int) (6 * density);
            outRect.bottom = (int) (12 * density);
        }
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        // No background, shadow or rim: the dock sits directly on the wallpaper.
    }
}

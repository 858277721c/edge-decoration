package com.sd.lib.edgedec.view;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.sd.lib.edgedec.IEdgeDecoration;
import com.sd.lib.edgedec.draw.EdgeDrawTransparent;

/**
 * 边缘透明
 */
public class FEdgeTransparentLayout extends FrameLayout implements IEdgeDecoration<EdgeDrawTransparent>
{
    public FEdgeTransparentLayout(Context context)
    {
        super(context);
        init();
    }

    public FEdgeTransparentLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public FEdgeTransparentLayout(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private int mDecorateEdge = Edge.ALL;
    private EdgeDraw mEdgeDraw;

    private void init()
    {

    }

    @Override
    public void setDecorateEdge(int edge)
    {
        mDecorateEdge = edge;
    }

    @Override
    public void setEdgeDraw(EdgeDrawTransparent edgeDraw)
    {
        mEdgeDraw = edgeDraw;
    }

    private EdgeDraw getEdgeDraw()
    {
        if (mEdgeDraw == null)
            mEdgeDraw = new EdgeDrawTransparent(getContext());
        return mEdgeDraw;
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime)
    {
        int saveLayerCount = 0;
        if (Build.VERSION.SDK_INT >= 21)
            saveLayerCount = canvas.saveLayer(0, 0, getWidth(), getHeight(), null);
        else
            saveLayerCount = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);

        final boolean drawChild = super.drawChild(canvas, child, drawingTime);

        final EdgeDraw edgeDraw = getEdgeDraw();
        edgeDraw.draw(mDecorateEdge, canvas, this, child);

        canvas.restoreToCount(saveLayerCount);
        return drawChild;
    }
}

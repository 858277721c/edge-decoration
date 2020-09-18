package com.sd.lib.edgedec.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.sd.lib.edgedec.IEdgeDecoration;

/**
 * 边缘透明
 */
public class FEdgeTransparentLayout extends FrameLayout implements IEdgeDecoration
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

    private Paint mPaint;
    // 渐变颜色
    private int[] mDecorateColors;
    // 渐变大小
    private int mDecorateSize;
    // 渐变边缘
    private int mDecorateEdge = Edge.ALL;

    private boolean mIsDirty;
    private LinearGradient mLinearGradient;

    private void init()
    {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));

        mDecorateColors = new int[]{0xffffffff, 0x00000000};
        mDecorateSize = dp2px(20, getContext());
        mIsDirty = true;
    }

    @Override
    public void setDecorateEdge(int edge)
    {
        if (mDecorateEdge != edge)
        {
            mDecorateEdge = edge;
            mIsDirty = true;
        }
    }

    /**
     * 设置渐变颜色
     *
     * @param colors
     */
    public void setDecorateColors(int[] colors)
    {
        if (mDecorateColors != colors)
        {
            mDecorateColors = colors;
            mIsDirty = true;
        }
    }

    /**
     * 设置渐变大小
     *
     * @param decorateSize
     */
    public void setDecorateSize(int decorateSize)
    {
        if (mDecorateSize != decorateSize)
        {
            mDecorateSize = decorateSize;
            mIsDirty = true;
        }
    }

    private void createGradientIfNeed()
    {
        if (mIsDirty)
        {
            mIsDirty = false;
            mLinearGradient = new LinearGradient(0, 0, 0, mDecorateSize, mDecorateColors, new float[]{0, 1.0f}, Shader.TileMode.CLAMP);
            mPaint.setShader(mLinearGradient);
        }
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime)
    {
        createGradientIfNeed();

        int saveLayerCount = 0;
        if (Build.VERSION.SDK_INT >= 21)
            saveLayerCount = canvas.saveLayer(0, 0, getWidth(), getHeight(), null);
        else
            saveLayerCount = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);

        final boolean drawChild = super.drawChild(canvas, child, drawingTime);

        final int edge = mDecorateEdge;
        final int edgeSize = mDecorateSize;
        final int width = getWidth();
        final int height = getHeight();

        if (edge == IEdgeDecoration.Edge.ALL || (edge & IEdgeDecoration.Edge.TOP) != 0)
        {
            canvas.drawRect(0, 0, width, edgeSize, mPaint);
        }

        if (edge == IEdgeDecoration.Edge.ALL || (edge & IEdgeDecoration.Edge.BOTTOM) != 0)
        {
            final int saveCount = canvas.save();
            canvas.rotate(180);
            canvas.translate(-width, -height);
            canvas.drawRect(0, 0, width, edgeSize, mPaint);
            canvas.restoreToCount(saveCount);
        }

        if (edge == IEdgeDecoration.Edge.ALL || (edge & IEdgeDecoration.Edge.LEFT) != 0)
        {
            final int saveCount = canvas.save();
            canvas.rotate(-90);
            canvas.translate(-height, 0);
            canvas.drawRect(0, 0, height, edgeSize, mPaint);
            canvas.restoreToCount(saveCount);
        }

        if (edge == IEdgeDecoration.Edge.ALL || (edge & IEdgeDecoration.Edge.RIGHT) != 0)
        {
            final int saveCount = canvas.save();
            canvas.rotate(90);
            canvas.translate(0, -width);
            canvas.drawRect(0, 0, height, edgeSize, mPaint);
            canvas.restoreToCount(saveCount);
        }

        canvas.restoreToCount(saveLayerCount);
        return drawChild;
    }

    private static int dp2px(float dp, Context context)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}

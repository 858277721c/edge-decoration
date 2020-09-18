package com.sd.lib.edgedec.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.view.View;

import com.sd.lib.edgedec.IEdgeDecoration;

public class EdgeDrawTransparent implements IEdgeDecoration.EdgeDraw
{
    private final Paint mPaint;
    // 渐变颜色
    private int[] mDrawColors = {0xffffffff, 0x00000000};
    // 渐变位置
    private float[] mGradientPosition = new float[]{0, 1.0f};
    // 渐变大小
    private int mDrawSize;

    private LinearGradient mLinearGradient;

    public EdgeDrawTransparent(Context context)
    {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));

        mDrawSize = dp2px(18, context);
        mLinearGradient = new LinearGradient(0, 0, 0, mDrawSize, mDrawColors, mGradientPosition, Shader.TileMode.CLAMP);
    }

    @Override
    public boolean draw(int edge, Canvas canvas, View parent, View child)
    {
        mPaint.setShader(mLinearGradient);

        final int width = parent.getWidth();
        final int height = parent.getHeight();

        if (edge == IEdgeDecoration.Edge.ALL || (edge & IEdgeDecoration.Edge.TOP) != 0)
        {
            canvas.drawRect(0, 0, width, mDrawSize, mPaint);
        }

        if (edge == IEdgeDecoration.Edge.ALL || (edge & IEdgeDecoration.Edge.BOTTOM) != 0)
        {
            final int saveCount = canvas.save();
            canvas.rotate(180);
            canvas.translate(-width, -height);
            canvas.drawRect(0, 0, width, mDrawSize, mPaint);
            canvas.restoreToCount(saveCount);
        }

        if (edge == IEdgeDecoration.Edge.ALL || (edge & IEdgeDecoration.Edge.LEFT) != 0)
        {
            final int saveCount = canvas.save();
            canvas.rotate(-90);
            canvas.translate(-height, 0);
            canvas.drawRect(0, 0, height, mDrawSize, mPaint);
            canvas.restoreToCount(saveCount);
        }

        if (edge == IEdgeDecoration.Edge.ALL || (edge & IEdgeDecoration.Edge.RIGHT) != 0)
        {
            final int saveCount = canvas.save();
            canvas.rotate(90);
            canvas.translate(0, -width);
            canvas.drawRect(0, 0, height, mDrawSize, mPaint);
            canvas.restoreToCount(saveCount);
        }

        return true;
    }

    private static int dp2px(float dp, Context context)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}

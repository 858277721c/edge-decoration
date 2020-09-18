package com.sd.lib.edgedec;

import android.graphics.Canvas;
import android.view.View;

public interface IEdgeDecoration<T extends IEdgeDecoration.EdgeDraw>
{
    /**
     * 设置要装饰的边缘
     *
     * @param edge {@link Edge}
     */
    void setDecorateEdge(int edge);

    /**
     * 设置边缘绘制对象
     *
     * @param edgeDraw
     */
    void setEdgeDraw(T edgeDraw);

    class Edge
    {
        public static final int ALL = 0x0000;
        public static final int LEFT = 0x0001;
        public static final int TOP = 0x0002;
        public static final int RIGHT = 0x0004;
        public static final int BOTTOM = 0x0008;
    }

    interface EdgeDraw
    {
        boolean draw(int edge, Canvas canvas, View parent, View child);
    }
}

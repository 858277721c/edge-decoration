package com.sd.lib.edgedec;

public interface IEdgeDecoration
{
    /**
     * 设置要装饰的边缘
     *
     * @param edge {@link Edge}
     */
    void setDecorateEdge(int edge);

    class Edge
    {
        public static final int ALL = 0x0000;
        public static final int LEFT = 0x0001;
        public static final int TOP = 0x0002;
        public static final int RIGHT = 0x0004;
        public static final int BOTTOM = 0x0008;
    }
}

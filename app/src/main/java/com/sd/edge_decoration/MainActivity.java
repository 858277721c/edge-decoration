package com.sd.edge_decoration;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.sd.edge_decoration.databinding.ActivityMainBinding;
import com.sd.lib.edgedec.IEdgeDecoration;

public class MainActivity extends AppCompatActivity
{
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mBinding.viewEdgeTransparent.setDecorateSize(dp2px(30));
        mBinding.viewEdgeTransparent.setDecorateEdge(IEdgeDecoration.Edge.LEFT
                | IEdgeDecoration.Edge.TOP
                | IEdgeDecoration.Edge.RIGHT
                | IEdgeDecoration.Edge.BOTTOM);
    }

    private int dp2px(float dp)
    {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
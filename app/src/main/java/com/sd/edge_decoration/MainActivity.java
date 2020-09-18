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

        mBinding.viewEdgeTransparent.setDecorateEdge(IEdgeDecoration.Edge.TOP);
    }
}
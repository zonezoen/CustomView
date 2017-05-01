package com.zone.customview.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by zone on 2017/4/24.
 */

public class EasyBehavior extends CoordinatorLayout.Behavior<TextView> {
    public EasyBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, TextView child, View dependency) {
        return dependency instanceof Button;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, TextView child, View dependency) {
        child.setX(dependency.getX() + 200);
        child.setY(dependency.getY() + 200);
        child.setPivotX(dependency.getX() + 200);
        child.setPivotY(dependency.getY() + 200);
        child.setRotation(1f);
        child.setText(dependency.getX() + "  " + dependency.getY());
        return true;
    }
}

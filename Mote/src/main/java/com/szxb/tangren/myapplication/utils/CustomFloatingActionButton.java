package com.szxb.tangren.myapplication.utils;

import android.content.Context;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;

/**
 * Created by TangRen on 2016/7/20.
 */
public class CustomFloatingActionButton extends FloatingActionButton.Behavior {

    private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();
    private boolean mIsAnimatingOut = false;

    public CustomFloatingActionButton(Context context, AttributeSet attrs) {
        super();
    }

    @Override
    public boolean onStartNestedScroll(final CoordinatorLayout coordinatorLayout, final FloatingActionButton child,
                                       final View directTargetChild, final View target, final int nestedScrollAxes) {
        // Ensure we react to vertical scrolling
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL
                || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }

    @Override
    public void onNestedScroll(final CoordinatorLayout coordinatorLayout, final FloatingActionButton child,
                               final View target, final int dxConsumed, final int dyConsumed,
                               final int dxUnconsumed, final int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        //dyConsumed > 0 && !this.mIsAnimatingOut && child.getVisibility() == View.VISIBLE
        if (dyConsumed > 0 && child.getVisibility() == View.VISIBLE) {
            // User scrolled down and the FAB is currently visible -> hide the FAB
            child.hide();
//            animateOut(child);
        } else if (dyConsumed < 0 && child.getVisibility() != View.VISIBLE) {
            // User scrolled up and the FAB is currently not visible -> show the FAB
            child.show();
//            animateIn(child);
        }
    }

    //自定义隐藏与显示

    //隐藏
    private void animateOut(FloatingActionButton child) {
        if (Build.VERSION.SDK_INT >= 14) {
            ViewCompat.animate(child).translationY(child.getHeight() + getMarginButton(child))
                    .setInterpolator(INTERPOLATOR).withLayer()
                    .setListener(new ViewPropertyAnimatorListener() {
                        @Override
                        public void onAnimationStart(View view) {
                            CustomFloatingActionButton.this.mIsAnimatingOut = true;
                        }

                        @Override
                        public void onAnimationEnd(View view) {
                            CustomFloatingActionButton.this.mIsAnimatingOut = false;
                            view.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationCancel(View view) {
                            CustomFloatingActionButton.this.mIsAnimatingOut = false;

                        }
                    }).start();
        }
    }

    private void animateIn(FloatingActionButton child) {
        child.setVisibility(View.VISIBLE);
        if (Build.VERSION.SDK_INT >= 14) {
            ViewCompat.animate(child).translationY(0)
                    .setInterpolator(INTERPOLATOR).withLayer().setListener(null)
                    .start();
        }
    }

    private int getMarginButton(View view) {

        int marginBottom = 0;
        final ViewGroup.LayoutParams group = view.getLayoutParams();
        if (group instanceof ViewGroup.MarginLayoutParams) {
            marginBottom = ((ViewGroup.MarginLayoutParams) group).bottomMargin;
        }
        return marginBottom;
    }
}

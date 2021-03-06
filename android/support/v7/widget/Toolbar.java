// 
// Decompiled by Procyon v0.5.30
// 

package android.support.v7.widget;

import android.text.TextUtils$TruncateAt;
import android.view.ContextThemeWrapper;
import android.support.v7.content.res.AppCompatResources;
import android.os.Build$VERSION;
import android.view.MenuItem;
import android.support.v4.view.MenuItemCompat;
import android.os.Parcelable;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.Menu;
import android.support.v7.app.ActionBar$LayoutParams;
import android.view.View$OnClickListener;
import android.support.v7.view.menu.MenuItemImpl;
import android.view.View$MeasureSpec;
import android.support.v7.view.SupportMenuInflater;
import android.view.MenuInflater;
import android.support.v4.view.MarginLayoutParamsCompat;
import android.view.ViewGroup$MarginLayoutParams;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.view.menu.MenuBuilder;
import android.view.ViewGroup$LayoutParams;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import java.util.List;
import android.text.TextUtils;
import android.support.v7.appcompat.R$styleable;
import android.support.v7.appcompat.R$attr;
import android.util.AttributeSet;
import android.widget.TextView;
import android.content.Context;
import android.support.v7.view.menu.MenuBuilder$Callback;
import android.widget.ImageView;
import java.util.ArrayList;
import android.view.View;
import android.graphics.drawable.Drawable;
import android.widget.ImageButton;
import android.support.v7.view.menu.MenuPresenter$Callback;
import android.view.ViewGroup;

public class Toolbar extends ViewGroup
{
    private MenuPresenter$Callback mActionMenuPresenterCallback;
    int mButtonGravity;
    ImageButton mCollapseButtonView;
    private CharSequence mCollapseDescription;
    private Drawable mCollapseIcon;
    private boolean mCollapsible;
    private int mContentInsetEndWithActions;
    private int mContentInsetStartWithNavigation;
    private RtlSpacingHelper mContentInsets;
    private boolean mEatingHover;
    private boolean mEatingTouch;
    View mExpandedActionView;
    private Toolbar$ExpandedActionViewMenuPresenter mExpandedMenuPresenter;
    private int mGravity;
    private final ArrayList<View> mHiddenViews;
    private ImageView mLogoView;
    private int mMaxButtonHeight;
    private MenuBuilder$Callback mMenuBuilderCallback;
    private ActionMenuView mMenuView;
    private final ActionMenuView$OnMenuItemClickListener mMenuViewItemClickListener;
    private ImageButton mNavButtonView;
    Toolbar$OnMenuItemClickListener mOnMenuItemClickListener;
    private ActionMenuPresenter mOuterActionMenuPresenter;
    private Context mPopupContext;
    private int mPopupTheme;
    private final Runnable mShowOverflowMenuRunnable;
    private CharSequence mSubtitleText;
    private int mSubtitleTextAppearance;
    private int mSubtitleTextColor;
    private TextView mSubtitleTextView;
    private final int[] mTempMargins;
    private final ArrayList<View> mTempViews;
    private int mTitleMarginBottom;
    private int mTitleMarginEnd;
    private int mTitleMarginStart;
    private int mTitleMarginTop;
    private CharSequence mTitleText;
    private int mTitleTextAppearance;
    private int mTitleTextColor;
    private TextView mTitleTextView;
    private ToolbarWidgetWrapper mWrapper;
    
    public Toolbar(final Context context) {
        this(context, null);
    }
    
    public Toolbar(final Context context, final AttributeSet set) {
        this(context, set, R$attr.toolbarStyle);
    }
    
    public Toolbar(final Context context, final AttributeSet set, int n) {
        super(context, set, n);
        this.mGravity = 8388627;
        this.mTempViews = new ArrayList<View>();
        this.mHiddenViews = new ArrayList<View>();
        this.mTempMargins = new int[2];
        this.mMenuViewItemClickListener = new Toolbar$1(this);
        this.mShowOverflowMenuRunnable = new Toolbar$2(this);
        final TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(this.getContext(), set, R$styleable.Toolbar, n, 0);
        this.mTitleTextAppearance = obtainStyledAttributes.getResourceId(R$styleable.Toolbar_titleTextAppearance, 0);
        this.mSubtitleTextAppearance = obtainStyledAttributes.getResourceId(R$styleable.Toolbar_subtitleTextAppearance, 0);
        this.mGravity = obtainStyledAttributes.getInteger(R$styleable.Toolbar_android_gravity, this.mGravity);
        this.mButtonGravity = obtainStyledAttributes.getInteger(R$styleable.Toolbar_buttonGravity, 48);
        final int n2 = n = obtainStyledAttributes.getDimensionPixelOffset(R$styleable.Toolbar_titleMargin, 0);
        if (obtainStyledAttributes.hasValue(R$styleable.Toolbar_titleMargins)) {
            n = obtainStyledAttributes.getDimensionPixelOffset(R$styleable.Toolbar_titleMargins, n2);
        }
        this.mTitleMarginBottom = n;
        this.mTitleMarginTop = n;
        this.mTitleMarginEnd = n;
        this.mTitleMarginStart = n;
        n = obtainStyledAttributes.getDimensionPixelOffset(R$styleable.Toolbar_titleMarginStart, -1);
        if (n >= 0) {
            this.mTitleMarginStart = n;
        }
        n = obtainStyledAttributes.getDimensionPixelOffset(R$styleable.Toolbar_titleMarginEnd, -1);
        if (n >= 0) {
            this.mTitleMarginEnd = n;
        }
        n = obtainStyledAttributes.getDimensionPixelOffset(R$styleable.Toolbar_titleMarginTop, -1);
        if (n >= 0) {
            this.mTitleMarginTop = n;
        }
        n = obtainStyledAttributes.getDimensionPixelOffset(R$styleable.Toolbar_titleMarginBottom, -1);
        if (n >= 0) {
            this.mTitleMarginBottom = n;
        }
        this.mMaxButtonHeight = obtainStyledAttributes.getDimensionPixelSize(R$styleable.Toolbar_maxButtonHeight, -1);
        n = obtainStyledAttributes.getDimensionPixelOffset(R$styleable.Toolbar_contentInsetStart, Integer.MIN_VALUE);
        final int dimensionPixelOffset = obtainStyledAttributes.getDimensionPixelOffset(R$styleable.Toolbar_contentInsetEnd, Integer.MIN_VALUE);
        final int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(R$styleable.Toolbar_contentInsetLeft, 0);
        final int dimensionPixelSize2 = obtainStyledAttributes.getDimensionPixelSize(R$styleable.Toolbar_contentInsetRight, 0);
        this.ensureContentInsets();
        this.mContentInsets.setAbsolute(dimensionPixelSize, dimensionPixelSize2);
        if (n != Integer.MIN_VALUE || dimensionPixelOffset != Integer.MIN_VALUE) {
            this.mContentInsets.setRelative(n, dimensionPixelOffset);
        }
        this.mContentInsetStartWithNavigation = obtainStyledAttributes.getDimensionPixelOffset(R$styleable.Toolbar_contentInsetStartWithNavigation, Integer.MIN_VALUE);
        this.mContentInsetEndWithActions = obtainStyledAttributes.getDimensionPixelOffset(R$styleable.Toolbar_contentInsetEndWithActions, Integer.MIN_VALUE);
        this.mCollapseIcon = obtainStyledAttributes.getDrawable(R$styleable.Toolbar_collapseIcon);
        this.mCollapseDescription = obtainStyledAttributes.getText(R$styleable.Toolbar_collapseContentDescription);
        final CharSequence text = obtainStyledAttributes.getText(R$styleable.Toolbar_title);
        if (!TextUtils.isEmpty(text)) {
            this.setTitle(text);
        }
        final CharSequence text2 = obtainStyledAttributes.getText(R$styleable.Toolbar_subtitle);
        if (!TextUtils.isEmpty(text2)) {
            this.setSubtitle(text2);
        }
        this.mPopupContext = this.getContext();
        this.setPopupTheme(obtainStyledAttributes.getResourceId(R$styleable.Toolbar_popupTheme, 0));
        final Drawable drawable = obtainStyledAttributes.getDrawable(R$styleable.Toolbar_navigationIcon);
        if (drawable != null) {
            this.setNavigationIcon(drawable);
        }
        final CharSequence text3 = obtainStyledAttributes.getText(R$styleable.Toolbar_navigationContentDescription);
        if (!TextUtils.isEmpty(text3)) {
            this.setNavigationContentDescription(text3);
        }
        final Drawable drawable2 = obtainStyledAttributes.getDrawable(R$styleable.Toolbar_logo);
        if (drawable2 != null) {
            this.setLogo(drawable2);
        }
        final CharSequence text4 = obtainStyledAttributes.getText(R$styleable.Toolbar_logoDescription);
        if (!TextUtils.isEmpty(text4)) {
            this.setLogoDescription(text4);
        }
        if (obtainStyledAttributes.hasValue(R$styleable.Toolbar_titleTextColor)) {
            this.setTitleTextColor(obtainStyledAttributes.getColor(R$styleable.Toolbar_titleTextColor, -1));
        }
        if (obtainStyledAttributes.hasValue(R$styleable.Toolbar_subtitleTextColor)) {
            this.setSubtitleTextColor(obtainStyledAttributes.getColor(R$styleable.Toolbar_subtitleTextColor, -1));
        }
        obtainStyledAttributes.recycle();
    }
    
    private void addCustomViewsWithGravity(final List<View> list, int i) {
        int n = 1;
        final int n2 = 0;
        if (ViewCompat.getLayoutDirection((View)this) != 1) {
            n = 0;
        }
        final int childCount = this.getChildCount();
        final int absoluteGravity = GravityCompat.getAbsoluteGravity(i, ViewCompat.getLayoutDirection((View)this));
        list.clear();
        i = n2;
        if (n != 0) {
            View child;
            Toolbar$LayoutParams toolbar$LayoutParams;
            for (i = childCount - 1; i >= 0; --i) {
                child = this.getChildAt(i);
                toolbar$LayoutParams = (Toolbar$LayoutParams)child.getLayoutParams();
                if (toolbar$LayoutParams.mViewType == 0 && this.shouldLayout(child) && this.getChildHorizontalGravity(toolbar$LayoutParams.gravity) == absoluteGravity) {
                    list.add(child);
                }
            }
        }
        else {
            while (i < childCount) {
                final View child2 = this.getChildAt(i);
                final Toolbar$LayoutParams toolbar$LayoutParams2 = (Toolbar$LayoutParams)child2.getLayoutParams();
                if (toolbar$LayoutParams2.mViewType == 0 && this.shouldLayout(child2) && this.getChildHorizontalGravity(toolbar$LayoutParams2.gravity) == absoluteGravity) {
                    list.add(child2);
                }
                ++i;
            }
        }
    }
    
    private void addSystemView(final View view, final boolean b) {
        final ViewGroup$LayoutParams layoutParams = view.getLayoutParams();
        Toolbar$LayoutParams layoutParams2;
        if (layoutParams == null) {
            layoutParams2 = this.generateDefaultLayoutParams();
        }
        else if (!this.checkLayoutParams(layoutParams)) {
            layoutParams2 = this.generateLayoutParams(layoutParams);
        }
        else {
            layoutParams2 = (Toolbar$LayoutParams)layoutParams;
        }
        layoutParams2.mViewType = 1;
        if (b && this.mExpandedActionView != null) {
            view.setLayoutParams((ViewGroup$LayoutParams)layoutParams2);
            this.mHiddenViews.add(view);
            return;
        }
        this.addView(view, (ViewGroup$LayoutParams)layoutParams2);
    }
    
    private void ensureContentInsets() {
        if (this.mContentInsets == null) {
            this.mContentInsets = new RtlSpacingHelper();
        }
    }
    
    private void ensureLogoView() {
        if (this.mLogoView == null) {
            this.mLogoView = new AppCompatImageView(this.getContext());
        }
    }
    
    private void ensureMenu() {
        this.ensureMenuView();
        if (this.mMenuView.peekMenu() == null) {
            final MenuBuilder menuBuilder = (MenuBuilder)this.mMenuView.getMenu();
            if (this.mExpandedMenuPresenter == null) {
                this.mExpandedMenuPresenter = new Toolbar$ExpandedActionViewMenuPresenter(this);
            }
            this.mMenuView.setExpandedActionViewsExclusive(true);
            menuBuilder.addMenuPresenter(this.mExpandedMenuPresenter, this.mPopupContext);
        }
    }
    
    private void ensureMenuView() {
        if (this.mMenuView == null) {
            (this.mMenuView = new ActionMenuView(this.getContext())).setPopupTheme(this.mPopupTheme);
            this.mMenuView.setOnMenuItemClickListener(this.mMenuViewItemClickListener);
            this.mMenuView.setMenuCallbacks(this.mActionMenuPresenterCallback, this.mMenuBuilderCallback);
            final Toolbar$LayoutParams generateDefaultLayoutParams = this.generateDefaultLayoutParams();
            generateDefaultLayoutParams.gravity = (0x800005 | (this.mButtonGravity & 0x70));
            this.mMenuView.setLayoutParams((ViewGroup$LayoutParams)generateDefaultLayoutParams);
            this.addSystemView((View)this.mMenuView, false);
        }
    }
    
    private void ensureNavButtonView() {
        if (this.mNavButtonView == null) {
            this.mNavButtonView = new AppCompatImageButton(this.getContext(), null, R$attr.toolbarNavigationButtonStyle);
            final Toolbar$LayoutParams generateDefaultLayoutParams = this.generateDefaultLayoutParams();
            generateDefaultLayoutParams.gravity = (0x800003 | (this.mButtonGravity & 0x70));
            this.mNavButtonView.setLayoutParams((ViewGroup$LayoutParams)generateDefaultLayoutParams);
        }
    }
    
    private int getChildHorizontalGravity(int n) {
        final int layoutDirection = ViewCompat.getLayoutDirection((View)this);
        switch (n = (GravityCompat.getAbsoluteGravity(n, layoutDirection) & 0x7)) {
            default: {
                if (layoutDirection == 1) {
                    n = 5;
                    return n;
                }
                return 3;
            }
            case 1:
            case 3:
            case 5: {
                return n;
            }
        }
    }
    
    private int getChildTop(final View view, int n) {
        final Toolbar$LayoutParams toolbar$LayoutParams = (Toolbar$LayoutParams)view.getLayoutParams();
        final int measuredHeight = view.getMeasuredHeight();
        if (n > 0) {
            n = (measuredHeight - n) / 2;
        }
        else {
            n = 0;
        }
        switch (this.getChildVerticalGravity(toolbar$LayoutParams.gravity)) {
            default: {
                final int paddingTop = this.getPaddingTop();
                final int paddingBottom = this.getPaddingBottom();
                final int height = this.getHeight();
                n = (height - paddingTop - paddingBottom - measuredHeight) / 2;
                if (n < toolbar$LayoutParams.topMargin) {
                    n = toolbar$LayoutParams.topMargin;
                }
                else {
                    final int n2 = height - paddingBottom - measuredHeight - n - paddingTop;
                    if (n2 < toolbar$LayoutParams.bottomMargin) {
                        n = Math.max(0, n - (toolbar$LayoutParams.bottomMargin - n2));
                    }
                }
                return n + paddingTop;
            }
            case 48: {
                return this.getPaddingTop() - n;
            }
            case 80: {
                return this.getHeight() - this.getPaddingBottom() - measuredHeight - toolbar$LayoutParams.bottomMargin - n;
            }
        }
    }
    
    private int getChildVerticalGravity(int n) {
        switch (n &= 0x70) {
            default: {
                n = (this.mGravity & 0x70);
                return n;
            }
            case 16:
            case 48:
            case 80: {
                return n;
            }
        }
    }
    
    private int getHorizontalMargins(final View view) {
        final ViewGroup$MarginLayoutParams viewGroup$MarginLayoutParams = (ViewGroup$MarginLayoutParams)view.getLayoutParams();
        return MarginLayoutParamsCompat.getMarginEnd(viewGroup$MarginLayoutParams) + MarginLayoutParamsCompat.getMarginStart(viewGroup$MarginLayoutParams);
    }
    
    private MenuInflater getMenuInflater() {
        return new SupportMenuInflater(this.getContext());
    }
    
    private int getVerticalMargins(final View view) {
        final ViewGroup$MarginLayoutParams viewGroup$MarginLayoutParams = (ViewGroup$MarginLayoutParams)view.getLayoutParams();
        return viewGroup$MarginLayoutParams.bottomMargin + viewGroup$MarginLayoutParams.topMargin;
    }
    
    private int getViewListMeasuredWidth(final List<View> list, final int[] array) {
        int max = array[0];
        int max2 = array[1];
        int size;
        int i;
        int n;
        int max3;
        int max4;
        int measuredWidth;
        for (size = list.size(), i = 0, n = 0; i < size; ++i, n += measuredWidth + max3 + max4) {
            final View view = list.get(i);
            final Toolbar$LayoutParams toolbar$LayoutParams = (Toolbar$LayoutParams)view.getLayoutParams();
            final int n2 = toolbar$LayoutParams.leftMargin - max;
            final int n3 = toolbar$LayoutParams.rightMargin - max2;
            max3 = Math.max(0, n2);
            max4 = Math.max(0, n3);
            max = Math.max(0, -n2);
            max2 = Math.max(0, -n3);
            measuredWidth = view.getMeasuredWidth();
        }
        return n;
    }
    
    private boolean isChildOrHidden(final View view) {
        return view.getParent() == this || this.mHiddenViews.contains(view);
    }
    
    private int layoutChildLeft(final View view, int n, final int[] array, int childTop) {
        final Toolbar$LayoutParams toolbar$LayoutParams = (Toolbar$LayoutParams)view.getLayoutParams();
        final int n2 = toolbar$LayoutParams.leftMargin - array[0];
        n += Math.max(0, n2);
        array[0] = Math.max(0, -n2);
        childTop = this.getChildTop(view, childTop);
        final int measuredWidth = view.getMeasuredWidth();
        view.layout(n, childTop, n + measuredWidth, view.getMeasuredHeight() + childTop);
        return toolbar$LayoutParams.rightMargin + measuredWidth + n;
    }
    
    private int layoutChildRight(final View view, int n, final int[] array, int childTop) {
        final Toolbar$LayoutParams toolbar$LayoutParams = (Toolbar$LayoutParams)view.getLayoutParams();
        final int n2 = toolbar$LayoutParams.rightMargin - array[1];
        n -= Math.max(0, n2);
        array[1] = Math.max(0, -n2);
        childTop = this.getChildTop(view, childTop);
        final int measuredWidth = view.getMeasuredWidth();
        view.layout(n - measuredWidth, childTop, n, view.getMeasuredHeight() + childTop);
        return n - (toolbar$LayoutParams.leftMargin + measuredWidth);
    }
    
    private int measureChildCollapseMargins(final View view, final int n, final int n2, final int n3, final int n4, final int[] array) {
        final ViewGroup$MarginLayoutParams viewGroup$MarginLayoutParams = (ViewGroup$MarginLayoutParams)view.getLayoutParams();
        final int n5 = viewGroup$MarginLayoutParams.leftMargin - array[0];
        final int n6 = viewGroup$MarginLayoutParams.rightMargin - array[1];
        final int n7 = Math.max(0, n5) + Math.max(0, n6);
        array[0] = Math.max(0, -n5);
        array[1] = Math.max(0, -n6);
        view.measure(getChildMeasureSpec(n, this.getPaddingLeft() + this.getPaddingRight() + n7 + n2, viewGroup$MarginLayoutParams.width), getChildMeasureSpec(n3, this.getPaddingTop() + this.getPaddingBottom() + viewGroup$MarginLayoutParams.topMargin + viewGroup$MarginLayoutParams.bottomMargin + n4, viewGroup$MarginLayoutParams.height));
        return view.getMeasuredWidth() + n7;
    }
    
    private void measureChildConstrained(final View view, int n, int childMeasureSpec, int mode, final int n2, final int n3) {
        final ViewGroup$MarginLayoutParams viewGroup$MarginLayoutParams = (ViewGroup$MarginLayoutParams)view.getLayoutParams();
        final int childMeasureSpec2 = getChildMeasureSpec(n, this.getPaddingLeft() + this.getPaddingRight() + viewGroup$MarginLayoutParams.leftMargin + viewGroup$MarginLayoutParams.rightMargin + childMeasureSpec, viewGroup$MarginLayoutParams.width);
        childMeasureSpec = getChildMeasureSpec(mode, this.getPaddingTop() + this.getPaddingBottom() + viewGroup$MarginLayoutParams.topMargin + viewGroup$MarginLayoutParams.bottomMargin + n2, viewGroup$MarginLayoutParams.height);
        mode = View$MeasureSpec.getMode(childMeasureSpec);
        n = childMeasureSpec;
        if (mode != 1073741824) {
            n = childMeasureSpec;
            if (n3 >= 0) {
                n = n3;
                if (mode != 0) {
                    n = Math.min(View$MeasureSpec.getSize(childMeasureSpec), n3);
                }
                n = View$MeasureSpec.makeMeasureSpec(n, 1073741824);
            }
        }
        view.measure(childMeasureSpec2, n);
    }
    
    private void postShowOverflowMenu() {
        this.removeCallbacks(this.mShowOverflowMenuRunnable);
        this.post(this.mShowOverflowMenuRunnable);
    }
    
    private boolean shouldCollapse() {
        if (this.mCollapsible) {
            for (int childCount = this.getChildCount(), i = 0; i < childCount; ++i) {
                final View child = this.getChildAt(i);
                if (this.shouldLayout(child) && child.getMeasuredWidth() > 0 && child.getMeasuredHeight() > 0) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    
    private boolean shouldLayout(final View view) {
        return view != null && view.getParent() == this && view.getVisibility() != 8;
    }
    
    void addChildrenForExpandedActionView() {
        for (int i = this.mHiddenViews.size() - 1; i >= 0; --i) {
            this.addView((View)this.mHiddenViews.get(i));
        }
        this.mHiddenViews.clear();
    }
    
    public boolean canShowOverflowMenu() {
        return this.getVisibility() == 0 && this.mMenuView != null && this.mMenuView.isOverflowReserved();
    }
    
    protected boolean checkLayoutParams(final ViewGroup$LayoutParams viewGroup$LayoutParams) {
        return super.checkLayoutParams(viewGroup$LayoutParams) && viewGroup$LayoutParams instanceof Toolbar$LayoutParams;
    }
    
    public void collapseActionView() {
        MenuItemImpl mCurrentExpandedItem;
        if (this.mExpandedMenuPresenter == null) {
            mCurrentExpandedItem = null;
        }
        else {
            mCurrentExpandedItem = this.mExpandedMenuPresenter.mCurrentExpandedItem;
        }
        if (mCurrentExpandedItem != null) {
            mCurrentExpandedItem.collapseActionView();
        }
    }
    
    public void dismissPopupMenus() {
        if (this.mMenuView != null) {
            this.mMenuView.dismissPopupMenus();
        }
    }
    
    void ensureCollapseButtonView() {
        if (this.mCollapseButtonView == null) {
            (this.mCollapseButtonView = new AppCompatImageButton(this.getContext(), null, R$attr.toolbarNavigationButtonStyle)).setImageDrawable(this.mCollapseIcon);
            this.mCollapseButtonView.setContentDescription(this.mCollapseDescription);
            final Toolbar$LayoutParams generateDefaultLayoutParams = this.generateDefaultLayoutParams();
            generateDefaultLayoutParams.gravity = (0x800003 | (this.mButtonGravity & 0x70));
            generateDefaultLayoutParams.mViewType = 2;
            this.mCollapseButtonView.setLayoutParams((ViewGroup$LayoutParams)generateDefaultLayoutParams);
            this.mCollapseButtonView.setOnClickListener((View$OnClickListener)new Toolbar$3(this));
        }
    }
    
    protected Toolbar$LayoutParams generateDefaultLayoutParams() {
        return new Toolbar$LayoutParams(-2, -2);
    }
    
    public Toolbar$LayoutParams generateLayoutParams(final AttributeSet set) {
        return new Toolbar$LayoutParams(this.getContext(), set);
    }
    
    protected Toolbar$LayoutParams generateLayoutParams(final ViewGroup$LayoutParams viewGroup$LayoutParams) {
        if (viewGroup$LayoutParams instanceof Toolbar$LayoutParams) {
            return new Toolbar$LayoutParams((Toolbar$LayoutParams)viewGroup$LayoutParams);
        }
        if (viewGroup$LayoutParams instanceof ActionBar$LayoutParams) {
            return new Toolbar$LayoutParams((ActionBar$LayoutParams)viewGroup$LayoutParams);
        }
        if (viewGroup$LayoutParams instanceof ViewGroup$MarginLayoutParams) {
            return new Toolbar$LayoutParams((ViewGroup$MarginLayoutParams)viewGroup$LayoutParams);
        }
        return new Toolbar$LayoutParams(viewGroup$LayoutParams);
    }
    
    public int getContentInsetEnd() {
        if (this.mContentInsets != null) {
            return this.mContentInsets.getEnd();
        }
        return 0;
    }
    
    public int getContentInsetEndWithActions() {
        if (this.mContentInsetEndWithActions != Integer.MIN_VALUE) {
            return this.mContentInsetEndWithActions;
        }
        return this.getContentInsetEnd();
    }
    
    public int getContentInsetLeft() {
        if (this.mContentInsets != null) {
            return this.mContentInsets.getLeft();
        }
        return 0;
    }
    
    public int getContentInsetRight() {
        if (this.mContentInsets != null) {
            return this.mContentInsets.getRight();
        }
        return 0;
    }
    
    public int getContentInsetStart() {
        if (this.mContentInsets != null) {
            return this.mContentInsets.getStart();
        }
        return 0;
    }
    
    public int getContentInsetStartWithNavigation() {
        if (this.mContentInsetStartWithNavigation != Integer.MIN_VALUE) {
            return this.mContentInsetStartWithNavigation;
        }
        return this.getContentInsetStart();
    }
    
    public int getCurrentContentInsetEnd() {
        int n;
        if (this.mMenuView != null) {
            final MenuBuilder peekMenu = this.mMenuView.peekMenu();
            if (peekMenu != null && peekMenu.hasVisibleItems()) {
                n = 1;
            }
            else {
                n = 0;
            }
        }
        else {
            n = 0;
        }
        if (n != 0) {
            return Math.max(this.getContentInsetEnd(), Math.max(this.mContentInsetEndWithActions, 0));
        }
        return this.getContentInsetEnd();
    }
    
    public int getCurrentContentInsetLeft() {
        if (ViewCompat.getLayoutDirection((View)this) == 1) {
            return this.getCurrentContentInsetEnd();
        }
        return this.getCurrentContentInsetStart();
    }
    
    public int getCurrentContentInsetRight() {
        if (ViewCompat.getLayoutDirection((View)this) == 1) {
            return this.getCurrentContentInsetStart();
        }
        return this.getCurrentContentInsetEnd();
    }
    
    public int getCurrentContentInsetStart() {
        if (this.getNavigationIcon() != null) {
            return Math.max(this.getContentInsetStart(), Math.max(this.mContentInsetStartWithNavigation, 0));
        }
        return this.getContentInsetStart();
    }
    
    public Drawable getLogo() {
        if (this.mLogoView != null) {
            return this.mLogoView.getDrawable();
        }
        return null;
    }
    
    public CharSequence getLogoDescription() {
        if (this.mLogoView != null) {
            return this.mLogoView.getContentDescription();
        }
        return null;
    }
    
    public Menu getMenu() {
        this.ensureMenu();
        return this.mMenuView.getMenu();
    }
    
    public CharSequence getNavigationContentDescription() {
        if (this.mNavButtonView != null) {
            return this.mNavButtonView.getContentDescription();
        }
        return null;
    }
    
    public Drawable getNavigationIcon() {
        if (this.mNavButtonView != null) {
            return this.mNavButtonView.getDrawable();
        }
        return null;
    }
    
    public Drawable getOverflowIcon() {
        this.ensureMenu();
        return this.mMenuView.getOverflowIcon();
    }
    
    public int getPopupTheme() {
        return this.mPopupTheme;
    }
    
    public CharSequence getSubtitle() {
        return this.mSubtitleText;
    }
    
    public CharSequence getTitle() {
        return this.mTitleText;
    }
    
    public int getTitleMarginBottom() {
        return this.mTitleMarginBottom;
    }
    
    public int getTitleMarginEnd() {
        return this.mTitleMarginEnd;
    }
    
    public int getTitleMarginStart() {
        return this.mTitleMarginStart;
    }
    
    public int getTitleMarginTop() {
        return this.mTitleMarginTop;
    }
    
    public DecorToolbar getWrapper() {
        if (this.mWrapper == null) {
            this.mWrapper = new ToolbarWidgetWrapper(this, true);
        }
        return this.mWrapper;
    }
    
    public boolean hasExpandedActionView() {
        return this.mExpandedMenuPresenter != null && this.mExpandedMenuPresenter.mCurrentExpandedItem != null;
    }
    
    public boolean hideOverflowMenu() {
        return this.mMenuView != null && this.mMenuView.hideOverflowMenu();
    }
    
    public boolean isOverflowMenuShowPending() {
        return this.mMenuView != null && this.mMenuView.isOverflowMenuShowPending();
    }
    
    public boolean isOverflowMenuShowing() {
        return this.mMenuView != null && this.mMenuView.isOverflowMenuShowing();
    }
    
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.removeCallbacks(this.mShowOverflowMenuRunnable);
    }
    
    public boolean onHoverEvent(final MotionEvent motionEvent) {
        final int actionMasked = MotionEventCompat.getActionMasked(motionEvent);
        if (actionMasked == 9) {
            this.mEatingHover = false;
        }
        if (!this.mEatingHover) {
            final boolean onHoverEvent = super.onHoverEvent(motionEvent);
            if (actionMasked == 9 && !onHoverEvent) {
                this.mEatingHover = true;
            }
        }
        if (actionMasked == 10 || actionMasked == 3) {
            this.mEatingHover = false;
        }
        return true;
    }
    
    protected void onLayout(final boolean b, int i, int j, int n, int n2) {
        boolean b2;
        if (ViewCompat.getLayoutDirection((View)this) == 1) {
            b2 = true;
        }
        else {
            b2 = false;
        }
        final int width = this.getWidth();
        final int height = this.getHeight();
        final int paddingLeft = this.getPaddingLeft();
        final int paddingRight = this.getPaddingRight();
        final int paddingTop = this.getPaddingTop();
        final int paddingBottom = this.getPaddingBottom();
        i = width - paddingRight;
        final int[] mTempMargins = this.mTempMargins;
        mTempMargins[mTempMargins[1] = 0] = 0;
        n = ViewCompat.getMinimumHeight((View)this);
        int min;
        if (n >= 0) {
            min = Math.min(n, n2 - j);
        }
        else {
            min = 0;
        }
        if (this.shouldLayout((View)this.mNavButtonView)) {
            if (b2) {
                i = this.layoutChildRight((View)this.mNavButtonView, i, mTempMargins, min);
                j = paddingLeft;
            }
            else {
                j = this.layoutChildLeft((View)this.mNavButtonView, paddingLeft, mTempMargins, min);
            }
        }
        else {
            j = paddingLeft;
        }
        n = i;
        n2 = j;
        if (this.shouldLayout((View)this.mCollapseButtonView)) {
            if (b2) {
                n = this.layoutChildRight((View)this.mCollapseButtonView, i, mTempMargins, min);
                n2 = j;
            }
            else {
                n2 = this.layoutChildLeft((View)this.mCollapseButtonView, j, mTempMargins, min);
                n = i;
            }
        }
        i = n;
        j = n2;
        if (this.shouldLayout((View)this.mMenuView)) {
            if (b2) {
                j = this.layoutChildLeft((View)this.mMenuView, n2, mTempMargins, min);
                i = n;
            }
            else {
                i = this.layoutChildRight((View)this.mMenuView, n, mTempMargins, min);
                j = n2;
            }
        }
        n = this.getCurrentContentInsetLeft();
        n2 = this.getCurrentContentInsetRight();
        mTempMargins[0] = Math.max(0, n - j);
        mTempMargins[1] = Math.max(0, n2 - (width - paddingRight - i));
        n = Math.max(j, n);
        n2 = (j = Math.min(i, width - paddingRight - n2));
        i = n;
        if (this.shouldLayout(this.mExpandedActionView)) {
            if (b2) {
                j = this.layoutChildRight(this.mExpandedActionView, n2, mTempMargins, min);
                i = n;
            }
            else {
                i = this.layoutChildLeft(this.mExpandedActionView, n, mTempMargins, min);
                j = n2;
            }
        }
        if (this.shouldLayout((View)this.mLogoView)) {
            if (b2) {
                j = this.layoutChildRight((View)this.mLogoView, j, mTempMargins, min);
                n = i;
            }
            else {
                n = this.layoutChildLeft((View)this.mLogoView, i, mTempMargins, min);
            }
        }
        else {
            n = i;
        }
        final boolean shouldLayout = this.shouldLayout((View)this.mTitleTextView);
        final boolean shouldLayout2 = this.shouldLayout((View)this.mSubtitleTextView);
        i = 0;
        if (shouldLayout) {
            final Toolbar$LayoutParams toolbar$LayoutParams = (Toolbar$LayoutParams)this.mTitleTextView.getLayoutParams();
            i = toolbar$LayoutParams.topMargin;
            n2 = this.mTitleTextView.getMeasuredHeight();
            i = 0 + (toolbar$LayoutParams.bottomMargin + (i + n2));
        }
        int n3;
        if (shouldLayout2) {
            final Toolbar$LayoutParams toolbar$LayoutParams2 = (Toolbar$LayoutParams)this.mSubtitleTextView.getLayoutParams();
            n2 = toolbar$LayoutParams2.topMargin;
            n3 = toolbar$LayoutParams2.bottomMargin + (n2 + this.mSubtitleTextView.getMeasuredHeight()) + i;
        }
        else {
            n3 = i;
        }
        Label_0832: {
            if (!shouldLayout) {
                n2 = j;
                i = n;
                if (!shouldLayout2) {
                    break Label_0832;
                }
            }
            TextView textView;
            if (shouldLayout) {
                textView = this.mTitleTextView;
            }
            else {
                textView = this.mSubtitleTextView;
            }
            TextView textView2;
            if (shouldLayout2) {
                textView2 = this.mSubtitleTextView;
            }
            else {
                textView2 = this.mTitleTextView;
            }
            final Toolbar$LayoutParams toolbar$LayoutParams3 = (Toolbar$LayoutParams)((View)textView).getLayoutParams();
            final Toolbar$LayoutParams toolbar$LayoutParams4 = (Toolbar$LayoutParams)((View)textView2).getLayoutParams();
            boolean b3;
            if ((shouldLayout && this.mTitleTextView.getMeasuredWidth() > 0) || (shouldLayout2 && this.mSubtitleTextView.getMeasuredWidth() > 0)) {
                b3 = true;
            }
            else {
                b3 = false;
            }
            switch (this.mGravity & 0x70) {
                default: {
                    i = (height - paddingTop - paddingBottom - n3) / 2;
                    if (i < toolbar$LayoutParams3.topMargin + this.mTitleMarginTop) {
                        i = toolbar$LayoutParams3.topMargin + this.mTitleMarginTop;
                    }
                    else {
                        n2 = height - paddingBottom - n3 - i - paddingTop;
                        if (n2 < toolbar$LayoutParams3.bottomMargin + this.mTitleMarginBottom) {
                            i = Math.max(0, i - (toolbar$LayoutParams4.bottomMargin + this.mTitleMarginBottom - n2));
                        }
                    }
                    i += paddingTop;
                    break;
                }
                case 48: {
                    i = this.getPaddingTop();
                    i = toolbar$LayoutParams3.topMargin + i + this.mTitleMarginTop;
                    break;
                }
                case 80: {
                    i = height - paddingBottom - toolbar$LayoutParams4.bottomMargin - this.mTitleMarginBottom - n3;
                    break;
                }
            }
            if (b2) {
                if (b3) {
                    n2 = this.mTitleMarginStart;
                }
                else {
                    n2 = 0;
                }
                n2 -= mTempMargins[1];
                j -= Math.max(0, n2);
                mTempMargins[1] = Math.max(0, -n2);
                if (shouldLayout) {
                    final Toolbar$LayoutParams toolbar$LayoutParams5 = (Toolbar$LayoutParams)this.mTitleTextView.getLayoutParams();
                    n2 = j - this.mTitleTextView.getMeasuredWidth();
                    final int n4 = this.mTitleTextView.getMeasuredHeight() + i;
                    this.mTitleTextView.layout(n2, i, j, n4);
                    final int mTitleMarginEnd = this.mTitleMarginEnd;
                    i = n4 + toolbar$LayoutParams5.bottomMargin;
                    n2 -= mTitleMarginEnd;
                }
                else {
                    n2 = j;
                }
                if (shouldLayout2) {
                    final Toolbar$LayoutParams toolbar$LayoutParams6 = (Toolbar$LayoutParams)this.mSubtitleTextView.getLayoutParams();
                    i += toolbar$LayoutParams6.topMargin;
                    this.mSubtitleTextView.layout(j - this.mSubtitleTextView.getMeasuredWidth(), i, j, this.mSubtitleTextView.getMeasuredHeight() + i);
                    i = this.mTitleMarginEnd;
                    final int bottomMargin = toolbar$LayoutParams6.bottomMargin;
                    i = j - i;
                }
                else {
                    i = j;
                }
                if (b3) {
                    i = Math.min(n2, i);
                }
                else {
                    i = j;
                }
                n2 = i;
                i = n;
            }
            else {
                if (b3) {
                    n2 = this.mTitleMarginStart;
                }
                else {
                    n2 = 0;
                }
                n2 -= mTempMargins[0];
                n += Math.max(0, n2);
                mTempMargins[0] = Math.max(0, -n2);
                int n6;
                if (shouldLayout) {
                    final Toolbar$LayoutParams toolbar$LayoutParams7 = (Toolbar$LayoutParams)this.mTitleTextView.getLayoutParams();
                    final int n5 = this.mTitleTextView.getMeasuredWidth() + n;
                    n2 = this.mTitleTextView.getMeasuredHeight() + i;
                    this.mTitleTextView.layout(n, i, n5, n2);
                    final int mTitleMarginEnd2 = this.mTitleMarginEnd;
                    i = toolbar$LayoutParams7.bottomMargin;
                    n6 = n5 + mTitleMarginEnd2;
                    i += n2;
                }
                else {
                    n6 = n;
                }
                int n7;
                if (shouldLayout2) {
                    final Toolbar$LayoutParams toolbar$LayoutParams8 = (Toolbar$LayoutParams)this.mSubtitleTextView.getLayoutParams();
                    n2 = i + toolbar$LayoutParams8.topMargin;
                    i = this.mSubtitleTextView.getMeasuredWidth() + n;
                    this.mSubtitleTextView.layout(n, n2, i, this.mSubtitleTextView.getMeasuredHeight() + n2);
                    n2 = this.mTitleMarginEnd;
                    final int bottomMargin2 = toolbar$LayoutParams8.bottomMargin;
                    n7 = n2 + i;
                }
                else {
                    n7 = n;
                }
                n2 = j;
                i = n;
                if (b3) {
                    i = Math.max(n6, n7);
                    n2 = j;
                }
            }
        }
        this.addCustomViewsWithGravity(this.mTempViews, 3);
        for (n = this.mTempViews.size(), j = 0; j < n; ++j) {
            i = this.layoutChildLeft(this.mTempViews.get(j), i, mTempMargins, min);
        }
        this.addCustomViewsWithGravity(this.mTempViews, 5);
        for (n = this.mTempViews.size(), j = 0; j < n; ++j) {
            n2 = this.layoutChildRight(this.mTempViews.get(j), n2, mTempMargins, min);
        }
        this.addCustomViewsWithGravity(this.mTempViews, 1);
        n = this.getViewListMeasuredWidth(this.mTempViews, mTempMargins);
        j = (width - paddingLeft - paddingRight) / 2 + paddingLeft - n / 2;
        n += j;
        if (j >= i) {
            i = j;
            if (n > n2) {
                i = j - (n - n2);
            }
        }
        n2 = this.mTempViews.size();
        n = 0;
        j = i;
        for (i = n; i < n2; ++i) {
            j = this.layoutChildLeft(this.mTempViews.get(i), j, mTempMargins, min);
        }
        this.mTempViews.clear();
    }
    
    protected void onMeasure(int resolveSizeAndState, final int n) {
        int max = 0;
        int combineMeasuredStates = 0;
        final int[] mTempMargins = this.mTempMargins;
        int n2;
        int n3;
        if (ViewUtils.isLayoutRtl((View)this)) {
            n2 = 0;
            n3 = 1;
        }
        else {
            n2 = 1;
            n3 = 0;
        }
        int n4 = 0;
        if (this.shouldLayout((View)this.mNavButtonView)) {
            this.measureChildConstrained((View)this.mNavButtonView, resolveSizeAndState, 0, n, 0, this.mMaxButtonHeight);
            n4 = this.mNavButtonView.getMeasuredWidth() + this.getHorizontalMargins((View)this.mNavButtonView);
            max = Math.max(0, this.mNavButtonView.getMeasuredHeight() + this.getVerticalMargins((View)this.mNavButtonView));
            combineMeasuredStates = ViewUtils.combineMeasuredStates(0, ViewCompat.getMeasuredState((View)this.mNavButtonView));
        }
        int n5 = n4;
        int combineMeasuredStates2 = combineMeasuredStates;
        int max2 = max;
        if (this.shouldLayout((View)this.mCollapseButtonView)) {
            this.measureChildConstrained((View)this.mCollapseButtonView, resolveSizeAndState, 0, n, 0, this.mMaxButtonHeight);
            n5 = this.mCollapseButtonView.getMeasuredWidth() + this.getHorizontalMargins((View)this.mCollapseButtonView);
            max2 = Math.max(max, this.mCollapseButtonView.getMeasuredHeight() + this.getVerticalMargins((View)this.mCollapseButtonView));
            combineMeasuredStates2 = ViewUtils.combineMeasuredStates(combineMeasuredStates, ViewCompat.getMeasuredState((View)this.mCollapseButtonView));
        }
        final int currentContentInsetStart = this.getCurrentContentInsetStart();
        final int n6 = 0 + Math.max(currentContentInsetStart, n5);
        mTempMargins[n3] = Math.max(0, currentContentInsetStart - n5);
        int n7 = 0;
        int combineMeasuredStates3 = combineMeasuredStates2;
        int max3 = max2;
        if (this.shouldLayout((View)this.mMenuView)) {
            this.measureChildConstrained((View)this.mMenuView, resolveSizeAndState, n6, n, 0, this.mMaxButtonHeight);
            n7 = this.mMenuView.getMeasuredWidth() + this.getHorizontalMargins((View)this.mMenuView);
            max3 = Math.max(max2, this.mMenuView.getMeasuredHeight() + this.getVerticalMargins((View)this.mMenuView));
            combineMeasuredStates3 = ViewUtils.combineMeasuredStates(combineMeasuredStates2, ViewCompat.getMeasuredState((View)this.mMenuView));
        }
        final int currentContentInsetEnd = this.getCurrentContentInsetEnd();
        final int n8 = n6 + Math.max(currentContentInsetEnd, n7);
        mTempMargins[n2] = Math.max(0, currentContentInsetEnd - n7);
        int n9 = n8;
        int combineMeasuredStates4 = combineMeasuredStates3;
        int max4 = max3;
        if (this.shouldLayout(this.mExpandedActionView)) {
            n9 = n8 + this.measureChildCollapseMargins(this.mExpandedActionView, resolveSizeAndState, n8, n, 0, mTempMargins);
            max4 = Math.max(max3, this.mExpandedActionView.getMeasuredHeight() + this.getVerticalMargins(this.mExpandedActionView));
            combineMeasuredStates4 = ViewUtils.combineMeasuredStates(combineMeasuredStates3, ViewCompat.getMeasuredState(this.mExpandedActionView));
        }
        int n10 = n9;
        int combineMeasuredStates5 = combineMeasuredStates4;
        int max5 = max4;
        if (this.shouldLayout((View)this.mLogoView)) {
            n10 = n9 + this.measureChildCollapseMargins((View)this.mLogoView, resolveSizeAndState, n9, n, 0, mTempMargins);
            max5 = Math.max(max4, this.mLogoView.getMeasuredHeight() + this.getVerticalMargins((View)this.mLogoView));
            combineMeasuredStates5 = ViewUtils.combineMeasuredStates(combineMeasuredStates4, ViewCompat.getMeasuredState((View)this.mLogoView));
        }
        final int childCount = this.getChildCount();
        final int n11 = 0;
        int max6 = max5;
        int combineMeasuredStates6 = combineMeasuredStates5;
        int i = n11;
        int n12 = n10;
        while (i < childCount) {
            final View child = this.getChildAt(i);
            if (((Toolbar$LayoutParams)child.getLayoutParams()).mViewType == 0) {
                if (this.shouldLayout(child)) {
                    n12 += this.measureChildCollapseMargins(child, resolveSizeAndState, n12, n, 0, mTempMargins);
                    max6 = Math.max(max6, child.getMeasuredHeight() + this.getVerticalMargins(child));
                    combineMeasuredStates6 = ViewUtils.combineMeasuredStates(combineMeasuredStates6, ViewCompat.getMeasuredState(child));
                }
            }
            ++i;
        }
        int n13 = 0;
        int n14 = 0;
        final int n15 = this.mTitleMarginTop + this.mTitleMarginBottom;
        final int n16 = this.mTitleMarginStart + this.mTitleMarginEnd;
        int combineMeasuredStates7 = combineMeasuredStates6;
        if (this.shouldLayout((View)this.mTitleTextView)) {
            this.measureChildCollapseMargins((View)this.mTitleTextView, resolveSizeAndState, n12 + n16, n, n15, mTempMargins);
            n13 = this.getHorizontalMargins((View)this.mTitleTextView) + this.mTitleTextView.getMeasuredWidth();
            n14 = this.mTitleTextView.getMeasuredHeight() + this.getVerticalMargins((View)this.mTitleTextView);
            combineMeasuredStates7 = ViewUtils.combineMeasuredStates(combineMeasuredStates6, ViewCompat.getMeasuredState((View)this.mTitleTextView));
        }
        int n17 = n14;
        int max7 = n13;
        int combineMeasuredStates8 = combineMeasuredStates7;
        if (this.shouldLayout((View)this.mSubtitleTextView)) {
            max7 = Math.max(n13, this.measureChildCollapseMargins((View)this.mSubtitleTextView, resolveSizeAndState, n12 + n16, n, n15 + n14, mTempMargins));
            n17 = n14 + (this.mSubtitleTextView.getMeasuredHeight() + this.getVerticalMargins((View)this.mSubtitleTextView));
            combineMeasuredStates8 = ViewUtils.combineMeasuredStates(combineMeasuredStates7, ViewCompat.getMeasuredState((View)this.mSubtitleTextView));
        }
        final int max8 = Math.max(max6, n17);
        final int paddingLeft = this.getPaddingLeft();
        final int paddingRight = this.getPaddingRight();
        final int paddingTop = this.getPaddingTop();
        final int paddingBottom = this.getPaddingBottom();
        final int resolveSizeAndState2 = ViewCompat.resolveSizeAndState(Math.max(max7 + n12 + (paddingLeft + paddingRight), this.getSuggestedMinimumWidth()), resolveSizeAndState, 0xFF000000 & combineMeasuredStates8);
        resolveSizeAndState = ViewCompat.resolveSizeAndState(Math.max(max8 + (paddingTop + paddingBottom), this.getSuggestedMinimumHeight()), n, combineMeasuredStates8 << 16);
        if (this.shouldCollapse()) {
            resolveSizeAndState = 0;
        }
        this.setMeasuredDimension(resolveSizeAndState2, resolveSizeAndState);
    }
    
    protected void onRestoreInstanceState(final Parcelable parcelable) {
        if (!(parcelable instanceof Toolbar$SavedState)) {
            super.onRestoreInstanceState(parcelable);
        }
        else {
            final Toolbar$SavedState toolbar$SavedState = (Toolbar$SavedState)parcelable;
            super.onRestoreInstanceState(toolbar$SavedState.getSuperState());
            Object peekMenu;
            if (this.mMenuView != null) {
                peekMenu = this.mMenuView.peekMenu();
            }
            else {
                peekMenu = null;
            }
            if (toolbar$SavedState.expandedMenuItemId != 0 && this.mExpandedMenuPresenter != null && peekMenu != null) {
                final MenuItem item = ((Menu)peekMenu).findItem(toolbar$SavedState.expandedMenuItemId);
                if (item != null) {
                    MenuItemCompat.expandActionView(item);
                }
            }
            if (toolbar$SavedState.isOverflowOpen) {
                this.postShowOverflowMenu();
            }
        }
    }
    
    public void onRtlPropertiesChanged(final int n) {
        boolean direction = true;
        if (Build$VERSION.SDK_INT >= 17) {
            super.onRtlPropertiesChanged(n);
        }
        this.ensureContentInsets();
        final RtlSpacingHelper mContentInsets = this.mContentInsets;
        if (n != 1) {
            direction = false;
        }
        mContentInsets.setDirection(direction);
    }
    
    protected Parcelable onSaveInstanceState() {
        final Toolbar$SavedState toolbar$SavedState = new Toolbar$SavedState(super.onSaveInstanceState());
        if (this.mExpandedMenuPresenter != null && this.mExpandedMenuPresenter.mCurrentExpandedItem != null) {
            toolbar$SavedState.expandedMenuItemId = this.mExpandedMenuPresenter.mCurrentExpandedItem.getItemId();
        }
        toolbar$SavedState.isOverflowOpen = this.isOverflowMenuShowing();
        return (Parcelable)toolbar$SavedState;
    }
    
    public boolean onTouchEvent(final MotionEvent motionEvent) {
        final int actionMasked = MotionEventCompat.getActionMasked(motionEvent);
        if (actionMasked == 0) {
            this.mEatingTouch = false;
        }
        if (!this.mEatingTouch) {
            final boolean onTouchEvent = super.onTouchEvent(motionEvent);
            if (actionMasked == 0 && !onTouchEvent) {
                this.mEatingTouch = true;
            }
        }
        if (actionMasked == 1 || actionMasked == 3) {
            this.mEatingTouch = false;
        }
        return true;
    }
    
    void removeChildrenForExpandedActionView() {
        for (int i = this.getChildCount() - 1; i >= 0; --i) {
            final View child = this.getChildAt(i);
            if (((Toolbar$LayoutParams)child.getLayoutParams()).mViewType != 2 && child != this.mMenuView) {
                this.removeViewAt(i);
                this.mHiddenViews.add(child);
            }
        }
    }
    
    public void setCollapsible(final boolean mCollapsible) {
        this.mCollapsible = mCollapsible;
        this.requestLayout();
    }
    
    public void setContentInsetEndWithActions(final int n) {
        int mContentInsetEndWithActions = n;
        if (n < 0) {
            mContentInsetEndWithActions = Integer.MIN_VALUE;
        }
        if (mContentInsetEndWithActions != this.mContentInsetEndWithActions) {
            this.mContentInsetEndWithActions = mContentInsetEndWithActions;
            if (this.getNavigationIcon() != null) {
                this.requestLayout();
            }
        }
    }
    
    public void setContentInsetStartWithNavigation(final int n) {
        int mContentInsetStartWithNavigation = n;
        if (n < 0) {
            mContentInsetStartWithNavigation = Integer.MIN_VALUE;
        }
        if (mContentInsetStartWithNavigation != this.mContentInsetStartWithNavigation) {
            this.mContentInsetStartWithNavigation = mContentInsetStartWithNavigation;
            if (this.getNavigationIcon() != null) {
                this.requestLayout();
            }
        }
    }
    
    public void setContentInsetsRelative(final int n, final int n2) {
        this.ensureContentInsets();
        this.mContentInsets.setRelative(n, n2);
    }
    
    public void setLogo(final int n) {
        this.setLogo(AppCompatResources.getDrawable(this.getContext(), n));
    }
    
    public void setLogo(final Drawable imageDrawable) {
        if (imageDrawable != null) {
            this.ensureLogoView();
            if (!this.isChildOrHidden((View)this.mLogoView)) {
                this.addSystemView((View)this.mLogoView, true);
            }
        }
        else if (this.mLogoView != null && this.isChildOrHidden((View)this.mLogoView)) {
            this.removeView((View)this.mLogoView);
            this.mHiddenViews.remove(this.mLogoView);
        }
        if (this.mLogoView != null) {
            this.mLogoView.setImageDrawable(imageDrawable);
        }
    }
    
    public void setLogoDescription(final int n) {
        this.setLogoDescription(this.getContext().getText(n));
    }
    
    public void setLogoDescription(final CharSequence contentDescription) {
        if (!TextUtils.isEmpty(contentDescription)) {
            this.ensureLogoView();
        }
        if (this.mLogoView != null) {
            this.mLogoView.setContentDescription(contentDescription);
        }
    }
    
    public void setMenu(final MenuBuilder menuBuilder, final ActionMenuPresenter actionMenuPresenter) {
        if (menuBuilder != null || this.mMenuView != null) {
            this.ensureMenuView();
            final MenuBuilder peekMenu = this.mMenuView.peekMenu();
            if (peekMenu != menuBuilder) {
                if (peekMenu != null) {
                    peekMenu.removeMenuPresenter(this.mOuterActionMenuPresenter);
                    peekMenu.removeMenuPresenter(this.mExpandedMenuPresenter);
                }
                if (this.mExpandedMenuPresenter == null) {
                    this.mExpandedMenuPresenter = new Toolbar$ExpandedActionViewMenuPresenter(this);
                }
                actionMenuPresenter.setExpandedActionViewsExclusive(true);
                if (menuBuilder != null) {
                    menuBuilder.addMenuPresenter(actionMenuPresenter, this.mPopupContext);
                    menuBuilder.addMenuPresenter(this.mExpandedMenuPresenter, this.mPopupContext);
                }
                else {
                    actionMenuPresenter.initForMenu(this.mPopupContext, null);
                    this.mExpandedMenuPresenter.initForMenu(this.mPopupContext, null);
                    actionMenuPresenter.updateMenuView(true);
                    this.mExpandedMenuPresenter.updateMenuView(true);
                }
                this.mMenuView.setPopupTheme(this.mPopupTheme);
                this.mMenuView.setPresenter(actionMenuPresenter);
                this.mOuterActionMenuPresenter = actionMenuPresenter;
            }
        }
    }
    
    public void setMenuCallbacks(final MenuPresenter$Callback mActionMenuPresenterCallback, final MenuBuilder$Callback mMenuBuilderCallback) {
        this.mActionMenuPresenterCallback = mActionMenuPresenterCallback;
        this.mMenuBuilderCallback = mMenuBuilderCallback;
        if (this.mMenuView != null) {
            this.mMenuView.setMenuCallbacks(mActionMenuPresenterCallback, mMenuBuilderCallback);
        }
    }
    
    public void setNavigationContentDescription(final int n) {
        CharSequence text;
        if (n != 0) {
            text = this.getContext().getText(n);
        }
        else {
            text = null;
        }
        this.setNavigationContentDescription(text);
    }
    
    public void setNavigationContentDescription(final CharSequence contentDescription) {
        if (!TextUtils.isEmpty(contentDescription)) {
            this.ensureNavButtonView();
        }
        if (this.mNavButtonView != null) {
            this.mNavButtonView.setContentDescription(contentDescription);
        }
    }
    
    public void setNavigationIcon(final int n) {
        this.setNavigationIcon(AppCompatResources.getDrawable(this.getContext(), n));
    }
    
    public void setNavigationIcon(final Drawable imageDrawable) {
        if (imageDrawable != null) {
            this.ensureNavButtonView();
            if (!this.isChildOrHidden((View)this.mNavButtonView)) {
                this.addSystemView((View)this.mNavButtonView, true);
            }
        }
        else if (this.mNavButtonView != null && this.isChildOrHidden((View)this.mNavButtonView)) {
            this.removeView((View)this.mNavButtonView);
            this.mHiddenViews.remove(this.mNavButtonView);
        }
        if (this.mNavButtonView != null) {
            this.mNavButtonView.setImageDrawable(imageDrawable);
        }
    }
    
    public void setNavigationOnClickListener(final View$OnClickListener onClickListener) {
        this.ensureNavButtonView();
        this.mNavButtonView.setOnClickListener(onClickListener);
    }
    
    public void setOnMenuItemClickListener(final Toolbar$OnMenuItemClickListener mOnMenuItemClickListener) {
        this.mOnMenuItemClickListener = mOnMenuItemClickListener;
    }
    
    public void setOverflowIcon(final Drawable overflowIcon) {
        this.ensureMenu();
        this.mMenuView.setOverflowIcon(overflowIcon);
    }
    
    public void setPopupTheme(final int mPopupTheme) {
        if (this.mPopupTheme != mPopupTheme) {
            if ((this.mPopupTheme = mPopupTheme) != 0) {
                this.mPopupContext = (Context)new ContextThemeWrapper(this.getContext(), mPopupTheme);
                return;
            }
            this.mPopupContext = this.getContext();
        }
    }
    
    public void setSubtitle(final int n) {
        this.setSubtitle(this.getContext().getText(n));
    }
    
    public void setSubtitle(final CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence)) {
            if (this.mSubtitleTextView == null) {
                final Context context = this.getContext();
                (this.mSubtitleTextView = new AppCompatTextView(context)).setSingleLine();
                this.mSubtitleTextView.setEllipsize(TextUtils$TruncateAt.END);
                if (this.mSubtitleTextAppearance != 0) {
                    this.mSubtitleTextView.setTextAppearance(context, this.mSubtitleTextAppearance);
                }
                if (this.mSubtitleTextColor != 0) {
                    this.mSubtitleTextView.setTextColor(this.mSubtitleTextColor);
                }
            }
            if (!this.isChildOrHidden((View)this.mSubtitleTextView)) {
                this.addSystemView((View)this.mSubtitleTextView, true);
            }
        }
        else if (this.mSubtitleTextView != null && this.isChildOrHidden((View)this.mSubtitleTextView)) {
            this.removeView((View)this.mSubtitleTextView);
            this.mHiddenViews.remove(this.mSubtitleTextView);
        }
        if (this.mSubtitleTextView != null) {
            this.mSubtitleTextView.setText(charSequence);
        }
        this.mSubtitleText = charSequence;
    }
    
    public void setSubtitleTextAppearance(final Context context, final int mSubtitleTextAppearance) {
        this.mSubtitleTextAppearance = mSubtitleTextAppearance;
        if (this.mSubtitleTextView != null) {
            this.mSubtitleTextView.setTextAppearance(context, mSubtitleTextAppearance);
        }
    }
    
    public void setSubtitleTextColor(final int n) {
        this.mSubtitleTextColor = n;
        if (this.mSubtitleTextView != null) {
            this.mSubtitleTextView.setTextColor(n);
        }
    }
    
    public void setTitle(final int n) {
        this.setTitle(this.getContext().getText(n));
    }
    
    public void setTitle(final CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence)) {
            if (this.mTitleTextView == null) {
                final Context context = this.getContext();
                (this.mTitleTextView = new AppCompatTextView(context)).setSingleLine();
                this.mTitleTextView.setEllipsize(TextUtils$TruncateAt.END);
                if (this.mTitleTextAppearance != 0) {
                    this.mTitleTextView.setTextAppearance(context, this.mTitleTextAppearance);
                }
                if (this.mTitleTextColor != 0) {
                    this.mTitleTextView.setTextColor(this.mTitleTextColor);
                }
            }
            if (!this.isChildOrHidden((View)this.mTitleTextView)) {
                this.addSystemView((View)this.mTitleTextView, true);
            }
        }
        else if (this.mTitleTextView != null && this.isChildOrHidden((View)this.mTitleTextView)) {
            this.removeView((View)this.mTitleTextView);
            this.mHiddenViews.remove(this.mTitleTextView);
        }
        if (this.mTitleTextView != null) {
            this.mTitleTextView.setText(charSequence);
        }
        this.mTitleText = charSequence;
    }
    
    public void setTitleMarginBottom(final int mTitleMarginBottom) {
        this.mTitleMarginBottom = mTitleMarginBottom;
        this.requestLayout();
    }
    
    public void setTitleMarginEnd(final int mTitleMarginEnd) {
        this.mTitleMarginEnd = mTitleMarginEnd;
        this.requestLayout();
    }
    
    public void setTitleMarginStart(final int mTitleMarginStart) {
        this.mTitleMarginStart = mTitleMarginStart;
        this.requestLayout();
    }
    
    public void setTitleMarginTop(final int mTitleMarginTop) {
        this.mTitleMarginTop = mTitleMarginTop;
        this.requestLayout();
    }
    
    public void setTitleTextAppearance(final Context context, final int mTitleTextAppearance) {
        this.mTitleTextAppearance = mTitleTextAppearance;
        if (this.mTitleTextView != null) {
            this.mTitleTextView.setTextAppearance(context, mTitleTextAppearance);
        }
    }
    
    public void setTitleTextColor(final int n) {
        this.mTitleTextColor = n;
        if (this.mTitleTextView != null) {
            this.mTitleTextView.setTextColor(n);
        }
    }
    
    public boolean showOverflowMenu() {
        return this.mMenuView != null && this.mMenuView.showOverflowMenu();
    }
}

package hearsilent.pixelslide;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;

import com.github.zagum.expandicon.ExpandIconView;

public class MainActivity extends AppCompatActivity {

	private View mBottomSheet;
	private BottomSheetBehavior mBottomSheetBehavior;
	private ExpandIconView mExpandIconView;

	private float mSlideOffset = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		findViews();
		setUpViews();
	}

	private void findViews() {
		mBottomSheet = findViewById(R.id.bottom_sheet);
		mExpandIconView = (ExpandIconView) mBottomSheet.findViewById(R.id.expandIconView);
	}

	private void setUpViews() {
		mExpandIconView.setState(ExpandIconView.LESS, true);
		mBottomSheetBehavior = BottomSheetBehavior.from(mBottomSheet);
		mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {

			@Override
			public void onStateChanged(@NonNull View bottomSheet, int newState) {
				if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
					mExpandIconView.setState(ExpandIconView.LESS, true);
				} else if (newState == BottomSheetBehavior.STATE_EXPANDED) {
					mExpandIconView.setState(ExpandIconView.MORE, true);
				}
			}

			@Override
			public void onSlide(@NonNull View bottomSheet, final float slideOffset) {
				mSlideOffset = slideOffset;
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						float dis = (mSlideOffset - slideOffset) * 10;
						if (dis > 1) {
							dis = 1;
						} else if (dis < -1) {
							dis = -1;
						}
						if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_DRAGGING) {
							mExpandIconView.setFraction(.5f + dis * .5f, false);
						}
					}
				}, 150);
			}
		});
		mBottomSheetBehavior.setPeekHeight((int) convertDpToPixel(100, this));
		mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
	}

	public DisplayMetrics getDisplayMetrics(Context context) {
		Resources resources = context.getResources();
		return resources.getDisplayMetrics();
	}

	public float convertDpToPixel(float dp, Context context) {
		return dp * (getDisplayMetrics(context).densityDpi / 160f);
	}

}

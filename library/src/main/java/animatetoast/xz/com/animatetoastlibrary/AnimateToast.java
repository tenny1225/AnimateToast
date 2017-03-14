package animatetoast.xz.com.animatetoastlibrary;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;


public class AnimateToast {

    private static boolean isRunning;
    private Context context;
    private String text;

    private PopupWindow popupWindow;
    private int duration;

    private AnimateToastView animateToastView;

    public void dismiss() {
        if (animateToastView != null) {
            animateToastView.stop();
        }
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }

    public AnimateToastView getAnimateToastView() {
        return animateToastView;
    }

    public static AnimateToast makeToast(Context context, String text) {
        return new AnimateToast(context, text, 1500);
    }

    public static AnimateToast makeToast(Context context, String text, int bgColor) {
        AnimateToast toast = new AnimateToast(context, text, 1500);
        toast.getAnimateToastView().setBgColor(bgColor);
        toast.getAnimateToastView().setTextColor(Color.WHITE);
        return toast;
    }


    public static AnimateToast makeToast(Context context, String text, int bgColor, int textColor) {
        AnimateToast toast = new AnimateToast(context, text, 1500);
        toast.getAnimateToastView().setBgColor(bgColor);
        toast.getAnimateToastView().setTextColor(textColor);
        return toast;
    }

    public static AnimateToast makeToast(Context context, String text, int duration, int bgColor, int textColor) {
        AnimateToast toast = new AnimateToast(context, text, duration);
        toast.getAnimateToastView().setBgColor(bgColor);
        toast.getAnimateToastView().setTextColor(textColor);
        return toast;
    }


    public static AnimateToast makeToast(Context context, String text, int duration, int bgColor, int textColor, int textSize) {
        AnimateToast toast = new AnimateToast(context, text, duration);
        toast.getAnimateToastView().setBgColor(bgColor);
        toast.getAnimateToastView().setTextColor(textColor);
        toast.getAnimateToastView().setTextSize(textSize);
        return toast;
    }

    public AnimateToast setTextSize(float textSize) {
        animateToastView.setTextSize(textSize);
        return this;
    }

    public AnimateToast setHeightAndTextSize(int height, float textSize) {
        animateToastView.setAnimateToastViewHeight(height);
        animateToastView.setTextSize(textSize);
        return this;
    }

    public AnimateToast(Context context, String text, int duration) {
        this.context = context;
        this.text = text;
        this.duration = duration;
        popupWindow = new PopupWindow(this.context);
        popupWindow.setWidth(-1);
        popupWindow.setWidth(-2);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        animateToastView = new AnimateToastView(context);
        popupWindow.setContentView(animateToastView);
    }

    public void showBottom(View v) {
        if (v == null) {
            return;
        }
        if (isRunning) {
            // return;
        }
        isRunning = true;

        popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
        animateToastView.setShowTop(false);
        animateToastView.post(new Runnable() {
            @Override
            public void run() {
                animateToastView.animate(duration);
            }
        });
    }

    public void showTop(View v) {
        if (v == null) {
            return;
        }
        if (isRunning) {
            // return;
        }
        isRunning = true;
        popupWindow.showAtLocation(v, Gravity.TOP, 0, 0);
        animateToastView.setShowTop(true);
        animateToastView.post(new Runnable() {
            @Override
            public void run() {
                animateToastView.animate(duration);
            }
        });
    }

    public void show(View v, int screenY, boolean slideInTop) {
        if (v == null) {
            return;
        }
        if (isRunning) {
            //return;
        }
        isRunning = true;
        if (!slideInTop) {
            int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            animateToastView.measure(w, h);
            int height = animateToastView.getMeasuredHeight();
            popupWindow.showAtLocation(v, Gravity.TOP, 0, screenY - height);
        } else {
            popupWindow.showAtLocation(v, Gravity.TOP, 0, screenY);
        }

        animateToastView.setShowTop(slideInTop);
        animateToastView.post(new Runnable() {
            @Override
            public void run() {
                animateToastView.animate(duration);
            }
        });
    }

    private static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    private static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    class AnimateToastView extends View {

        private boolean showTop;

        private int textColor = Color.WHITE;
        private int bgColor = Color.BLUE;
        private float textSize = sp2px(getContext(), 17);
        private float animateToastViewHeight;


        Paint paint;
        TextPaint textPaint;
        StaticLayout layout;
        WindowManager windowManager;

        public void setShowTop(boolean showTop) {
            this.showTop = showTop;
        }

        public void setTextColor(int textColor) {
            this.textColor = textColor;

        }

        public void setBgColor(int bgColor) {
            this.bgColor = bgColor;
        }

        public void setTextSize(float textSize) {
            this.textSize = textSize;
        }

        public void setAnimateToastViewHeight(float animateToastViewHeight) {
            this.animateToastViewHeight = animateToastViewHeight;
        }

        public AnimateToastView(Context context) {
            super(context);
            windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setTextAlign(Paint.Align.CENTER);
            textPaint = new TextPaint();
            textPaint.setColor(Color.parseColor("#ffffff"));
            textPaint.setTextAlign(Paint.Align.CENTER);
            textPaint.setTextSize(paint.getTextSize());
            textPaint.setAntiAlias(true);

        }

        float degree;
        float maxDegree;
        float minDegree;
        ValueAnimator animatorStart;
        ValueAnimator animatorEnd;

        private void animate(final int duration) {
            animatorStart = ValueAnimator.ofFloat(minDegree, maxDegree).setDuration(400);
            animatorStart.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    degree = (float) animation.getAnimatedValue();
                    invalidate();
                    if (degree == maxDegree) {
                        animatorEnd = ValueAnimator.ofFloat(maxDegree, minDegree).setDuration(400);
                        animatorEnd.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                degree = (float) animation.getAnimatedValue();
                                invalidate();
                                if (degree == 0 && popupWindow != null) {
                                    popupWindow.dismiss();
                                    isRunning = false;
                                }
                            }
                        });
                        animatorEnd.setStartDelay(duration);
                        animatorEnd.start();
                    }
                }
            });
            animatorStart.setStartDelay(400);
            animatorStart.start();

        }

        public void stop() {
            if (animatorStart != null) {
                animatorStart.cancel();
            }
            if (animatorEnd != null) {
                animatorEnd.cancel();
            }
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);

            paint.setTextSize(textSize);
            textPaint.setTextSize(textSize);


            float w = windowManager.getDefaultDisplay().getWidth();

            layout = new StaticLayout(text, textPaint, (int) (w - dip2px(getContext(), 16)), Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);

            float h;
            if (animateToastViewHeight == 0) {
                h = layout.getHeight() + dip2px(getContext(), 16);
            } else {
                h = animateToastViewHeight;
            }
            setMeasuredDimension((int) w, (int) h);
            maxDegree = (float) Math.atan(h / w);
            minDegree = (float) Math.atan(0);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            float h = getHeight() * 1f;
            float w = getWidth() * 1f;
            paint.setAlpha(255);
            paint.setColor(bgColor);
            Path path = new Path();

            if (showTop) {
                path.moveTo(0, 0);
                path.lineTo(w, 0);
                float hh = (float) (2 * w * Math.tan(degree));
                if (hh <= h) {
                    path.lineTo(w, hh);
                } else {
                    path.lineTo(w, h);
                    float xx = (float) ((h / Math.tan(degree)) - w);
                    path.lineTo(xx, h);
                }
                hh = (float) (w * Math.tan(degree));

                path.lineTo(0, hh);
                path.close();
            } else {
                path.moveTo(0, h);
                path.lineTo(w, h);
                float hh = (float) (2 * w * Math.tan(degree));
                if (hh <= h) {
                    path.lineTo(w, h - hh);
                } else {
                    path.lineTo(w, 0);
                    float xx = (float) ((h / Math.tan(degree)) - w);
                    path.lineTo(xx, 0);
                }
                hh = (float) (w * Math.tan(degree));

                path.lineTo(0, h - hh);
                path.close();

            }

            canvas.drawPath(path, paint);


            //paint.setAlpha((int) (255 * degree / maxDegree));
            textPaint.setColor(textColor);
            textPaint.setTextSize(textSize);
            RectF rectF = new RectF(0, 0, w, h);

            canvas.save();
            float cy;
            if (showTop) {
                cy = ((h - layout.getHeight()) / 2 + h) * degree / maxDegree - h;

            } else {
                cy = ((h - layout.getHeight()) / 2 - h) * degree / maxDegree + h;
            }
            canvas.translate(rectF.centerX(), cy);
            layout.draw(canvas);
        }
    }
}

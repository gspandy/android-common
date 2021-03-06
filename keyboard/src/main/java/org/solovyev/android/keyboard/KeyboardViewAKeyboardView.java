package org.solovyev.android.keyboard;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;
import android.view.inputmethod.InputMethodSubtype;
import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 11/3/12
 * Time: 11:41 PM
 */
public class KeyboardViewAKeyboardView extends KeyboardView implements AndroidKeyboardView<AndroidAKeyboard> {

    public KeyboardViewAKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public KeyboardViewAKeyboardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setOnKeyboardActionListener(@NotNull KeyboardView.OnKeyboardActionListener keyboardActionListener) {
        super.setOnKeyboardActionListener(keyboardActionListener);
    }

    @Override
    public void setKeyboard(@NotNull AndroidAKeyboard keyboard) {
        super.setKeyboard(keyboard.getKeyboard());
    }

    @Override
    public void close() {
        super.closing();
    }

    @Override
    public void dismiss() {
        super.handleBack();
    }

    @Override
    public void reload() {
        setKeyboard(getKeyboard());
    }

    static final int KEYCODE_OPTIONS = -100;

    @Override
    protected boolean onLongPress(Keyboard.Key key) {
        if (key.codes[0] == Keyboard.KEYCODE_CANCEL) {
            getOnKeyboardActionListener().onKey(KEYCODE_OPTIONS, null);
            return true;
        } else {
            return super.onLongPress(key);
        }
    }

    void setSubtypeOnSpaceKey(final InputMethodSubtype subtype) {
        final LatinKeyboard keyboard = (LatinKeyboard)getKeyboard();
        keyboard.setSpaceIcon(getResources().getDrawable(subtype.getIconResId()));
        invalidateAllKeys();
    }
}

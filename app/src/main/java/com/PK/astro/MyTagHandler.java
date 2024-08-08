package com.PK.astro;

import android.graphics.Color;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import org.xml.sax.XMLReader;

public class MyTagHandler implements Html.TagHandler {

    @Override
    public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
        if (tag.equalsIgnoreCase("b")) {
            processBoldTag(opening, output);
        }
    }

    private void processBoldTag(boolean opening, Editable output) {
        int len = output.length();
        if (opening) {
            output.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), len, len, Spanned.SPAN_MARK_MARK);
        } else {
            Object obj = getLast(output, StyleSpan.class);
            int where = output.getSpanStart(obj);

            output.removeSpan(obj);

            if (where != len) {
                output.setSpan(new ForegroundColorSpan(Color.RED), where, len, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }

    private Object getLast(Editable text, Class<?> kind) {
        Object[] spans = text.getSpans(0, text.length(), kind);
        if (spans.length == 0) {
            return null;
        } else {
            for (int i = spans.length; i > 0; i--) {
                if (text.getSpanFlags(spans[i - 1]) == Spanned.SPAN_MARK_MARK) {
                    return spans[i - 1];
                }
            }
            return null;
        }
    }
}

/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.borqs.omshome25.ui;

import com.borqs.omshome25.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * A corpus in the corpus selection list.
 */
public class CorpusView extends RelativeLayout {

    private ImageView mIcon;
    private TextView mLabel;

    public CorpusView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CorpusView(Context context) {
        super(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mIcon = (ImageView) findViewById(R.id.source_iv);
        mLabel = (TextView) findViewById(R.id.source_tv);
    }

    public void setLabel(CharSequence label) {
        mLabel.setText(label);
    }

    public void setIcon(Drawable icon) {
        mIcon.setImageDrawable(icon);
    }
}

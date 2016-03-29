package com.example.samsonaiyegbusi.events2you;

import android.view.View;

/**
 * Created by samsonaiyegbusi on 01/03/16.
 */
public interface Initialiser extends View.OnClickListener {

    @Override
    void onClick(View v);

    void variableInitialiser();

    void widgetInitialiser();
}

package com.espaciounido.demonfc;

import android.support.v4.app.Fragment;

/**
 * Created by MyMac on 17/03/16.
 */
public interface INfcView {
    void setTitle(String text);
    void setDataset(String[] dataset);
    Fragment getFragment();
}

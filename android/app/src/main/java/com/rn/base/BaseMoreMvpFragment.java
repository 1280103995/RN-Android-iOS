package com.rn.base;

import android.os.Bundle;
import java.util.LinkedHashSet;


/**
 * 一个Fragment有可能有多个Presenter
 */

public abstract class BaseMoreMvpFragment extends BaseFragment {

    protected LinkedHashSet<IMvpPresenter> presenter = new LinkedHashSet<>();

    protected abstract IMvpPresenter[] createPresenter();

    protected abstract void initMvpData();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addPresenters(savedInstanceState);
        initMvpData();
    }

    private void addPresenters(Bundle savedInstanceState) {
        IMvpPresenter[] presenters = createPresenter();
        if (presenters != null) {
            for (IMvpPresenter p : presenters) {
                if (p == null) {
                    throw new NullPointerException("Presenter is null! Do you return null in createPresenter()?");
                }
                p.onMvpAttachView(this, savedInstanceState);
                presenter.add(p);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (presenter != null) {
            for (IMvpPresenter p : presenter) {
                p.onMvpStart();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter != null) {
            for (IMvpPresenter p : presenter) {
                p.onMvpResume();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (presenter != null) {
            for (IMvpPresenter p : presenter) {
                p.onMvpPause();
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (presenter != null) {
            for (IMvpPresenter p : presenter) {
                p.onMvpStop();
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (presenter != null) {
            for (IMvpPresenter p : presenter) {
                p.onMvpSaveInstanceState(outState);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            for (IMvpPresenter p : presenter) {
                p.onMvpDestroy();
            }
            presenter.clear();
        }
    }

}

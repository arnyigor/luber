package com.arny.pik.presenter.korpus

import com.arny.pik.presenter.base.BaseMvpPresenter
import com.arny.pik.presenter.base.BaseMvpView


object KorpusViewContract {
    interface View : BaseMvpView {

    }

    interface Presenter : BaseMvpPresenter<View> {

    }
}

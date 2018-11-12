package com.arny.pik.presenter.korpus

import com.arny.pik.data.source.AppRepositoryImpl
import com.arny.pik.presenter.base.BaseMvpPresenterImpl


class KorpusViewPresenter : BaseMvpPresenterImpl<KorpusViewContract.View>(), KorpusViewContract.Presenter {
    val repository = AppRepositoryImpl.instance
}
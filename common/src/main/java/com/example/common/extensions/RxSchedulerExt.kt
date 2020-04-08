package com.example.common.extensions

import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

// Observable
fun <T> Observable<T>.applyIoScheduler() = applyScheduler(Schedulers.io())

fun <T> Observable<T>.applyComputationScheduler() = applyScheduler(Schedulers.computation())

private fun <T> Observable<T>.applyScheduler(scheduler: Scheduler): Observable<T> {
    return subscribeOn(scheduler).observeOn(AndroidSchedulers.mainThread())
}

// Flowable
fun <T> Flowable<T>.applyIoScheduler() = applyScheduler(Schedulers.io())

fun <T> Flowable<T>.applyComputation() = applyScheduler(Schedulers.computation())

private fun <T> Flowable<T>.applyScheduler(scheduler: Scheduler): Flowable<T> {
    return subscribeOn(scheduler).observeOn(AndroidSchedulers.mainThread())
}

// Single
fun <T> Single<T>.applyIoScheduler() = applyScheduler(Schedulers.io())

fun <T> Single<T>.applyComputation() = applyScheduler(Schedulers.computation())

private fun <T> Single<T>.applyScheduler(scheduler: Scheduler): Single<T> {
    return subscribeOn(scheduler).observeOn(AndroidSchedulers.mainThread())
}

// Maybe
fun <T> Maybe<T>.applyIoScheduler() = applyScheduler(Schedulers.io())

fun <T> Maybe<T>.applyComputation() = applyScheduler(Schedulers.computation())

private fun <T> Maybe<T>.applyScheduler(scheduler: Scheduler): Maybe<T> {
    return subscribeOn(scheduler).observeOn(AndroidSchedulers.mainThread())
}

// Completable
fun Completable.applyIoScheduler() = applyScheduler(Schedulers.io())

fun Completable.applyComputation() = applyScheduler(Schedulers.computation())

private fun Completable.applyScheduler(scheduler: Scheduler): Completable {
    return subscribeOn(scheduler).observeOn(AndroidSchedulers.mainThread())
}
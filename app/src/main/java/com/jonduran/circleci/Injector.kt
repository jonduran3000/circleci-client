package com.jonduran.circleci

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.jonduran.circleci.common.ui.utils.Injectable
import dagger.android.AndroidInjection
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection

object Injector {
    fun inject(app: CircleCiApp) {
        val component = DaggerAppComponent.factory().create(app).apply { inject(app) }
        val callbacks = InjectingActivityLifecycleCallbacks()
        app.registerActivityLifecycleCallbacks(callbacks)
    }

    private class InjectingActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
            injectActivity(activity)
        }

        override fun onActivityPaused(activity: Activity?) {
        }

        override fun onActivityResumed(activity: Activity?) {
        }

        override fun onActivityStarted(activity: Activity?) {
        }

        override fun onActivityDestroyed(activity: Activity?) {
        }

        override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
        }

        override fun onActivityStopped(activity: Activity?) {
        }

        private fun injectActivity(activity: Activity?) {
            if (activity is HasAndroidInjector) {
                AndroidInjection.inject(activity)
            }
            if (activity is FragmentActivity) {
                activity.supportFragmentManager.registerFragmentLifecycleCallbacks(
                    InjectingFragmentLifecycleCallbacks(),
                    true
                )
            }
        }
    }

    private class InjectingFragmentLifecycleCallbacks : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
            if (f is Injectable) {
                AndroidSupportInjection.inject(f)
            }
        }
    }
}
package com.example.nbaapp.core.utils

import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

// Activities and Adapters are safe from memory leaks

// Use for Fragment && BottomSheetDialogFragment
fun <T : ViewBinding> Fragment.viewBinding(bind: (View) -> T): ReadOnlyProperty<Fragment, T> =
    object : ReadOnlyProperty<Fragment, T>, LifecycleEventObserver {
        private var binding: T? = null

        override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
            val viewLifecycle = thisRef.viewLifecycleOwner.lifecycle

            binding?.let { return it }

            if (!viewLifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
                throw IllegalStateException("Cannot access viewBinding. Fragment's view is destroyed.")
            }

            return bind(thisRef.requireView()).also {
                binding = it
                viewLifecycle.addObserver(this)
            }
        }

        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            if (event == Lifecycle.Event.ON_DESTROY) {
                binding = null
                source.lifecycle.removeObserver(this)
            }
        }
    }

// Use for DialogFragment
fun <T : ViewBinding> DialogFragment.viewBinding(
    bindingInflater: (LayoutInflater) -> T
): ReadOnlyProperty<DialogFragment, T> =
    object : ReadOnlyProperty<DialogFragment, T> {

        private var binding: T? = null

        override fun getValue(thisRef: DialogFragment, property: KProperty<*>): T {
            binding?.let { return it }

            val newBinding = bindingInflater(thisRef.layoutInflater)
            binding = newBinding

            // Observe the fragment's lifecycle to clear binding when destroyed
            thisRef.lifecycle.addObserver(object : DefaultLifecycleObserver {
                override fun onDestroy(owner: LifecycleOwner) {
                    binding = null
                    thisRef.lifecycle.removeObserver(this)
                }
            })

            return newBinding
        }
    }

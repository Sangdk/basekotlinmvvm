package com.testnotification.basekotlinmvvm.utils.exts

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

fun FragmentManager.remove(fragment : Fragment) = beginTransaction().remove(fragment).commitNow()

fun FragmentManager.findFragment(fragment : Fragment) = findFragmentByTag(fragment.javaClass.simpleName)
package com.vcolofati.organizze.utils

interface NavigationPolicy {
    /**
     * Return true if going forwards is allowed.
     */
    fun canGoForward(position: Int): Boolean

    /**
     * Return true if going backwards is allowed.
     */
    fun canGoBackward(position: Int): Boolean
}
import { h } from "vue";
import { NIcon } from 'naive-ui';
import type { Component } from 'vue'

export const renderNIcon = (icon: Component) => {
    return () => {
        return h(NIcon, null, {
            default: () => h(icon)
        })
    }
}
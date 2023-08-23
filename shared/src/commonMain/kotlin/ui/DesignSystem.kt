package ui

import androidx.compose.ui.unit.dp

object DesignSystem {
    /**
     * Padding values using the Golden Ratio:
     * - Medium: 16.dp
     * - Large: Medium * ϕ
     * - ExtraLarge: Large * ϕ
     * - Small: Medium / ϕ
     * - ExtraSmall: Small / ϕ
     * */
    object Padding {
        val ExtraSmall = 6.11.dp
        val Small = 9.89.dp
        val Medium = 16.dp
        val Large = 25.89.dp
        val ExtraLarge = 41.89.dp
    }

    object Size {
        val ExtraSmall = 6.11.dp
        val Small = 9.89.dp
        val Medium = 16.dp
        val Large = 25.89.dp
        val ExtraLarge = 41.89.dp
    }
}
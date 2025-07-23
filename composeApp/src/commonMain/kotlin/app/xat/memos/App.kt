package app.xat.memos

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        ProductsScreen(
            viewModel = remember { ProductsViewModel(MockProductsApi()) }
        )
    }
}
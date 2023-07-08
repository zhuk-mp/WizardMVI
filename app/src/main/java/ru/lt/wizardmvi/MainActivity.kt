package ru.lt.wizardmvi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import ru.lt.wizardmvi.compose.MyApp


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}



















//@Preview
//@Composable
//fun MainScreenPreview() {
//    MyApp()
//}



//@AndroidEntryPoint
//class ResultFragment : Fragment() {
//
//    private val viewModel: ResultViewModel by viewModels()
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        return ComposeView(requireContext()).apply {
//            setContent {
//                ResultScreen(viewModel)
//            }
//        }
//    }
//}
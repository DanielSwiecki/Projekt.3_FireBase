package np.com.bimalkafle.firebaseauthdemoapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proj3.pages.HomePage
import com.example.proj3.pages.Screen2
import com.example.proj3.pages.Screen3
import com.google.firebase.database.FirebaseDatabase
import np.com.bimalkafle.firebaseauthdemoapp.pages.LoginPage
import np.com.bimalkafle.firebaseauthdemoapp.pages.SignupPage

@Composable
fun MyAppNavigation(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel,
    database: FirebaseDatabase
) {
    val navController = rememberNavController()


    NavHost(navController = navController, startDestination = "login", builder = {
        composable("login"){
            LoginPage(modifier,navController,authViewModel)
        }
        composable("signup"){
            SignupPage(modifier,navController,authViewModel)
        }
        composable("home"){
            HomePage(modifier,navController,authViewModel)
        }
        composable("screen2") {
            Screen2(navController = navController , database = database)
        }


        composable("screen3") {
            Screen3(navController = navController , database = database)
        }
    })
}
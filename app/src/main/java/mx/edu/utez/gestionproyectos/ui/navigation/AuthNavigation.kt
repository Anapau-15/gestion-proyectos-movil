package mx.edu.utez.gestionproyectos.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import mx.edu.utez.gestionproyectos.ui.auth.*
import mx.edu.utez.gestionproyectos.viewmodel.LoginViewModel

object AuthRoutes {
    const val LOGIN = "login"
    const val FORGOT = "forgot"
    const val VERIFY = "verify"
    const val RESET = "reset"
}

@Composable
fun AuthNavigation(
    navController: NavHostController,
    onLoginSuccess: () -> Unit
) {
    // 🔥 CREAR UNA SOLA INSTANCIA DEL VIEWMODEL
    val loginViewModel: LoginViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = AuthRoutes.LOGIN
    ) {

        composable(AuthRoutes.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    onLoginSuccess()
                },
                onForgotPasswordClick = {
                    navController.navigate(AuthRoutes.FORGOT)
                }
            )
        }

        composable(AuthRoutes.FORGOT) {
            ForgotPasswordScreen(
                viewModel = loginViewModel,  // ← PASAR AQUÍ
                onBack = { navController.popBackStack() },
                onNext = {
                    navController.navigate(AuthRoutes.VERIFY)
                }
            )
        }

        composable(AuthRoutes.VERIFY) {
            VerifyCodeScreen(
                viewModel = loginViewModel,  // ← PASAR AQUÍ
                onBack = { navController.popBackStack() },
                onNext = {
                    navController.navigate(AuthRoutes.RESET)
                }
            )
        }

        composable(AuthRoutes.RESET) {
            ResetPasswordScreen(
                viewModel = loginViewModel,  // ← PASAR AQUÍ
                onBack = { navController.popBackStack() },
                onFinish = {
                    navController.navigate(AuthRoutes.LOGIN) {
                        popUpTo(AuthRoutes.LOGIN) { inclusive = true }
                    }
                }
            )
        }
    }
}
package mx.edu.utez.gestionproyectos.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import mx.edu.utez.gestionproyectos.ui.auth.*

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
    NavHost(
        navController = navController,
        startDestination = AuthRoutes.LOGIN
    ) {

        composable(AuthRoutes.LOGIN) {
            LoginScreen(
                onLoginClick = {
                    onLoginSuccess()
                },
                onForgotPasswordClick = {
                    navController.navigate(AuthRoutes.FORGOT)
                }
            )
        }

        composable(AuthRoutes.FORGOT) {
            ForgotPasswordScreen(
                onBack = { navController.popBackStack() },
                onNext = {
                    navController.navigate(AuthRoutes.VERIFY)
                }
            )
        }

        composable(AuthRoutes.VERIFY) {
            VerifyCodeScreen(
                onBack = { navController.popBackStack() },
                onNext = {
                    navController.navigate(AuthRoutes.RESET)
                }
            )
        }

        composable(AuthRoutes.RESET) {
            ResetPasswordScreen(
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
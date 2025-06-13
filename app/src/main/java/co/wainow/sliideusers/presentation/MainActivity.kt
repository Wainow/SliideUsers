package co.wainow.sliideusers.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.wainow.sliideusers.R
import co.wainow.sliideusers.domain.entity.UsersScreenState.Error
import co.wainow.sliideusers.domain.entity.UsersScreenState.Loading
import co.wainow.sliideusers.domain.entity.UsersScreenState.Success
import co.wainow.sliideusers.presentation.components.AddUserDialog
import co.wainow.sliideusers.presentation.components.UserAddButton
import co.wainow.sliideusers.presentation.components.UsersErrorScreen
import co.wainow.sliideusers.presentation.components.UsersLoadingScreen
import co.wainow.sliideusers.presentation.components.UsersScreen
import co.wainow.sliideusers.presentation.theme.SliideUsersTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<UserViewModel>()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SliideUsersTheme {
                val screenState = viewModel.screenState.collectAsState()
                val showAddDialog = remember { mutableStateOf(false) }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    floatingActionButton = {
                        UserAddButton { showAddDialog.value = true }
                    }
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                    ) {
                        UserHeader()
                        PullToRefreshBox(
                            modifier = Modifier
                                .fillMaxHeight(),
                            isRefreshing = screenState.value is Loading,
                            onRefresh = viewModel::refresh,
                        ) {
                            when (screenState.value) {
                                is Loading -> UsersLoadingScreen()
                                is Success -> UsersScreen((screenState.value as Success).users, viewModel::removeUser)
                                is Error -> UsersErrorScreen((screenState.value as Error).message)
                            }
                        }
                    }

                    if (showAddDialog.value) {
                        AddUserDialog(
                            onDismiss = { showAddDialog.value = false },
                            onConfirm = { name, email, gender ->
                                viewModel.addUser(name, email, gender)
                                showAddDialog.value = false
                            }
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun UserHeader() {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.onSecondary)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary)
            )
            Text(
                modifier = Modifier.padding(top = 18.dp),
                text = stringResource(R.string.app_name),
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                fontSize = 20.sp
            )
        }
    }
}
package co.wainow.sliideusers.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import co.wainow.sliideusers.EMPTY
import co.wainow.sliideusers.R
import co.wainow.sliideusers.presentation.entity.Gender

@Composable
fun AddUserDialog(
    onDismiss: () -> Unit,
    onConfirm: (name: String, email: String, gender: Gender) -> Unit
) {
    var name by remember { mutableStateOf(String.EMPTY) }
    var email by remember { mutableStateOf(String.EMPTY) }
    var gender by remember { mutableStateOf(Gender.Male) }
    var isEmailValid by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(R.string.add_user),
                color = MaterialTheme.colorScheme.primary,
            )
        },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(stringResource(R.string.name)) },
                    textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.primary)
                )
                Spacer(modifier = Modifier.height(8.dp))
                EmailInputField(
                    email = email,
                    onEmailChange = { email = it },
                    onValidationChanged = { isEmailValid = it }
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    UserGenderRadioButton(
                        currentGender = gender,
                        gender = Gender.Male,
                        onClick = { gender = Gender.Male }
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    UserGenderRadioButton(
                        currentGender = gender,
                        gender = Gender.Female,
                        onClick = { gender = Gender.Female }
                    )
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm(name, email, gender) },
                enabled = name.isNotBlank() && email.isNotBlank() && isEmailValid,
                content = { Text(stringResource(R.string.add)) },
                colors = ButtonDefaults.textButtonColors(
                    disabledContentColor = Color.Gray,
                    disabledContainerColor = Color.Transparent,
                    contentColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                content = { Text(stringResource(R.string.cancel)) }
            )
        }
    )
}

@Composable
fun EmailInputField(
    email: String,
    onEmailChange: (String) -> Unit,
    onValidationChanged: (Boolean) -> Unit
) {
    val isValid = remember(email) {
        android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    LaunchedEffect(isValid) {
        onValidationChanged(isValid)
    }

    Column {
        OutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
            label = { Text(stringResource(R.string.email)) },
            isError = !isValid && email.isNotEmpty(),
            textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.onBackground),
        )

        if (!isValid && email.isNotEmpty()) {
            Text(
                text = stringResource(R.string.please_enter_a_valid_email),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}

@Composable
fun UserGenderRadioButton(currentGender: Gender, gender: Gender, onClick: () -> Unit) {
    RadioButton(
        selected = currentGender == gender,
        onClick = onClick
    )
    Text(
        gender.toString(), modifier = Modifier
            .padding(start = 4.dp, top = 14.dp)
            .clickable(onClick = onClick)
    )
}

@Composable
fun DeleteUserDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(R.string.remove_user_title),
                color = MaterialTheme.colorScheme.primary,
            )
        },
        text = { Text(stringResource(R.string.remove_user_question)) },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(stringResource(R.string.ok))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}
package co.wainow.sliideusers.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.wainow.sliideusers.R
import co.wainow.sliideusers.presentation.entity.Gender
import co.wainow.sliideusers.presentation.entity.UserUI

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersScreen(
    users: List<UserUI>,
    onRemoveUser: (Long) -> Unit
) {
    LazyColumn(Modifier.fillMaxSize()) {
        items(users) { user ->
            UserPage(user, onRemoveUser)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserPage(user: UserUI, onRemoveUser: (Long) -> Unit) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (showDeleteDialog) {
        DeleteUserDialog(
            onConfirm = {
                showDeleteDialog = false
                onRemoveUser(user.id)
            },
            onDismiss = { showDeleteDialog = false }
        )
    }

    Row(
        modifier = Modifier
            .padding(top = 4.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colorScheme.onSecondary)
            .combinedClickable(
                onClick = {},
                onLongClick = { showDeleteDialog = true },
                indication = ripple(
                    bounded = true,
                ),
                interactionSource = remember { MutableInteractionSource() }
            )
    ) {
        UserImage(user)
        Column(modifier = Modifier.padding(6.dp)) {
            UserTitle(user.name)
            UserEmail(user.email)
        }
        Spacer(modifier = Modifier.weight(1f))
        UserGender(user.gender)
    }
}

@Composable
fun UserImage(user: UserUI) {
    Box(
        modifier = Modifier
            .size(60.dp)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .matchParentSize()
                .clip(CircleShape),
            painter = ColorPainter(color = user.userColor),
            contentDescription = null
        )

        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "User Icon",
            tint = MaterialTheme.colorScheme.background,
            modifier = Modifier.size(32.dp)
        )

        UserActiveIcon(
            user,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = 4.dp, y = 4.dp)
        )
    }
}

@Composable
fun UserTitle(text: String) {
    Text(
        text = text,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    )
}

@Composable
fun UserActiveIcon(user: UserUI, modifier: Modifier = Modifier) {
    if (user.isActive) {
        Image(
            painter = painterResource(R.drawable.ic_online_24),
            contentDescription = null,
            modifier = modifier
                .size(24.dp)
                .padding(horizontal = 4.dp)
        )
    }
}

@Composable
fun UserEmail(date: String) {
    Text(
        text = date,
        fontSize = 10.sp
    )
}

@Composable
fun UserGender(gender: Gender) {
    Row(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = gender.toString().lowercase(),
            fontWeight = FontWeight.Bold,
            fontSize = 10.sp,
            maxLines = 1,
        )
    }
}

@Composable
fun UserAddButton(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        shape = CircleShape,
        content = { Icon(Icons.Default.Add, contentDescription = stringResource(R.string.add_user)) }
    )
}
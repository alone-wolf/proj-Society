package com.wh.society.ui.componment

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun RoundedTextFiled(value: String, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        colors = TextFieldDefaults.textFieldColors(
            textColor = LocalContentColor.current.copy(LocalContentAlpha.current),
//            disabledTextColor = textColor.copy(ContentAlpha.disabled),
//            backgroundColor = MaterialTheme.colors.onSurface.copy(alpha = BackgroundOpacity),
            cursorColor = MaterialTheme.colors.primary,
            errorCursorColor = MaterialTheme.colors.error,
            focusedIndicatorColor = Color.Transparent,
//            MaterialTheme.colors.primary.copy(alpha = ContentAlpha.high),
            unfocusedIndicatorColor = Color.Transparent,
//            MaterialTheme.colors.onSurface.copy(alpha = TextFieldDefaults.UnfocusedIndicatorLineOpacity),
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
//            leadingIconColor =
//            MaterialTheme.colors.onSurface.copy(alpha = TextFieldDefaults.IconOpacity),
//            disabledLeadingIconColor = leadingIconColor.copy(alpha = ContentAlpha.disabled),
//            errorLeadingIconColor = leadingIconColor,
//            trailingIconColor =
//            MaterialTheme.colors.onSurface.copy(alpha = TextFieldDefaults.IconOpacity),
//            disabledTrailingIconColor = trailingIconColor.copy(alpha = ContentAlpha.disabled),
//            errorTrailingIconColor = MaterialTheme.colors.error,
//            focusedLabelColor =
//            MaterialTheme.colors.primary.copy(alpha = ContentAlpha.high),
//            unfocusedLabelColor = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium),
//            disabledLabelColor = unfocusedLabelColor.copy(ContentAlpha.disabled),
//            errorLabelColor = MaterialTheme.colors.error,
//            placeholderColor = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium),
//            disabledPlaceholderColor = placeholderColor.copy(ContentAlpha.disabled)
        ),
        shape = RoundedCornerShape(16.dp)
    )
}

@Composable
fun RoundedTextFiled(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeHolder: String = "",
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        colors = TextFieldDefaults.textFieldColors(
            textColor = LocalContentColor.current.copy(LocalContentAlpha.current),
//            disabledTextColor = textColor.copy(ContentAlpha.disabled),
//            backgroundColor = MaterialTheme.colors.onSurface.copy(alpha = BackgroundOpacity),
            cursorColor = MaterialTheme.colors.primary,
            errorCursorColor = MaterialTheme.colors.error,
            focusedIndicatorColor = Color.Transparent,
//            MaterialTheme.colors.primary.copy(alpha = ContentAlpha.high),
            unfocusedIndicatorColor = Color.Transparent,
//            MaterialTheme.colors.onSurface.copy(alpha = TextFieldDefaults.UnfocusedIndicatorLineOpacity),
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
//            leadingIconColor =
//            MaterialTheme.colors.onSurface.copy(alpha = TextFieldDefaults.IconOpacity),
//            disabledLeadingIconColor = leadingIconColor.copy(alpha = ContentAlpha.disabled),
//            errorLeadingIconColor = leadingIconColor,
//            trailingIconColor =
//            MaterialTheme.colors.onSurface.copy(alpha = TextFieldDefaults.IconOpacity),
//            disabledTrailingIconColor = trailingIconColor.copy(alpha = ContentAlpha.disabled),
//            errorTrailingIconColor = MaterialTheme.colors.error,
//            focusedLabelColor =
//            MaterialTheme.colors.primary.copy(alpha = ContentAlpha.high),
//            unfocusedLabelColor = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium),
//            disabledLabelColor = unfocusedLabelColor.copy(ContentAlpha.disabled),
//            errorLabelColor = MaterialTheme.colors.error,
//            placeholderColor = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium),
//            disabledPlaceholderColor = placeholderColor.copy(ContentAlpha.disabled)
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = modifier,
        placeholder = { Text(text = placeHolder) },
        visualTransformation = visualTransformation
    )
}

@Preview(showBackground = true)
@Composable
fun Mutable() {
    RoundedTextFiled(value = "aaa", onValueChange = {})
}
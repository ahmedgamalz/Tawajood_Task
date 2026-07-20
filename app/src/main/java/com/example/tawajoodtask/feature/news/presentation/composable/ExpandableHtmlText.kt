package com.example.tawajoodtask.feature.news.presentation.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat

@Composable
fun ExpandableHtmlText(
    htmlText: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodyLarge,
    limit: Int = 300
) {
    var isExpanded by remember { mutableStateOf(false) }
    
    // Clean up HTML tags and also remove the api suffix like "[+3584 chars]"
    val rawText = remember(htmlText) {
        val parsed = HtmlCompat.fromHtml(htmlText, HtmlCompat.FROM_HTML_MODE_LEGACY).toString().trim()
        parsed.replace(Regex("""\s*\[\+\d+\s+chars\]"""), "")
    }

    if (rawText.isNotEmpty()) {
        val isLongText = rawText.length > limit
        val displayText = if (isLongText && !isExpanded) {
            rawText.take(limit) + "... "
        } else {
            rawText
        }

        val annotatedContent = buildAnnotatedString {
            append(displayText)
            if (isLongText) {
                if (!isExpanded) {
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            textDecoration = TextDecoration.Underline
                        )
                    ) {
                        append("See More")
                    }
                } else {
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            textDecoration = TextDecoration.Underline
                        )
                    ) {
                        append(" See Less")
                    }
                }
            }
        }

        Text(
            text = annotatedContent,
            style = style.copy(lineHeight = 26.sp),
            modifier = modifier
                .fillMaxWidth()
                .clickable(enabled = isLongText) {
                    isExpanded = !isExpanded
                }
        )
    }
}

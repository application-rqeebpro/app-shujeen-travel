package com.example.ui.components

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.FlightTakeoff
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.ShajeenBodyText
import com.example.ui.theme.ShajeenCardBg
import com.example.ui.theme.ShajeenCardBorderSoft
import com.example.ui.theme.ShajeenDarkBlue
import com.example.ui.theme.ShajeenGold
import com.example.ui.theme.ShajeenHeadingText
import com.example.ui.theme.ShajeenPrimaryButton
import com.example.ui.theme.ShajeenSecondaryText
import com.example.ui.theme.ShajeenSkyBlue
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * Data model representing the travel search/input details.
 */
data class TravelInputDetails(
    val origin: String = "",
    val destination: String,
    val departureDate: String,
    val returnDate: String = "",
    val passengersCount: Int
)

/**
 * A simple, clean, and intuitive Jetpack Compose form component for users
 * to input basic travel details: destination, dates (departure and return), and passenger count.
 */
@Composable
fun TravelInputForm(
    modifier: Modifier = Modifier,
    initialDestination: String = "",
    initialDepartureDate: String = "",
    initialPassengersCount: Int = 1,
    buttonText: String = "بحث عن الرحلات المتاحة",
    onSubmit: (TravelInputDetails) -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val dateFormatter = remember { SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()) }

    var destination by remember { mutableStateOf(initialDestination) }
    var departureDate by remember { mutableStateOf(
        if (initialDepartureDate.isNotEmpty()) initialDepartureDate else dateFormatter.format(calendar.time)
    ) }
    var returnDate by remember { mutableStateOf("") }
    var hasReturnDate by remember { mutableStateOf(false) }
    var passengersCount by remember { mutableIntStateOf(initialPassengersCount.coerceAtLeast(1)) }
    var destinationError by remember { mutableStateOf(false) }

    // Departure DatePicker Dialog
    val departureDatePicker = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val selectedCal = Calendar.getInstance()
                selectedCal.set(year, month, dayOfMonth)
                departureDate = dateFormatter.format(selectedCal.time)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    // Return DatePicker Dialog
    val returnDatePicker = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val selectedCal = Calendar.getInstance()
                selectedCal.set(year, month, dayOfMonth)
                returnDate = dateFormatter.format(selectedCal.time)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("travel_input_form_card"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = ShajeenCardBg
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, ShajeenCardBorderSoft)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            // Form Title Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    modifier = Modifier.size(38.dp),
                    shape = RoundedCornerShape(10.dp),
                    color = ShajeenSkyBlue.copy(alpha = 0.15f)
                ) {
                    Icon(
                        imageVector = Icons.Default.FlightTakeoff,
                        contentDescription = null,
                        tint = ShajeenDarkBlue,
                        modifier = Modifier
                            .padding(8.dp)
                            .size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column {
                    Text(
                        text = "تفاصيل الرحلة والمسافرين",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = ShajeenDarkBlue,
                            fontSize = 16.sp
                        )
                    )
                    Text(
                        text = "أدخل وجهة السفر والتواريخ وعدد الركاب",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color.Gray,
                            fontSize = 12.sp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 1. Destination Field
            OutlinedTextField(
                value = destination,
                onValueChange = {
                    destination = it
                    if (it.isNotBlank()) destinationError = false
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("travel_destination_input"),
                label = { Text("وجهة السفر (المدينة أو الدولة)") },
                placeholder = { Text("مثال: مكة المكرمة، القاهرة، الرياض، جدة...") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "الوجهة",
                        tint = if (destinationError) MaterialTheme.colorScheme.error else ShajeenSkyBlue
                    )
                },
                isError = destinationError,
                supportingText = {
                    if (destinationError) {
                        Text("يرجى إدخال وجهة السفر المطلوبة", color = MaterialTheme.colorScheme.error)
                    }
                },
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ShajeenSkyBlue,
                    unfocusedBorderColor = Color(0xFFD1D5DB)
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            // 2. Travel Dates Selection Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Departure Date Field
                OutlinedCard(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { departureDatePicker.show() }
                        .testTag("travel_departure_date_selector"),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.outlinedCardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)
                    ),
                    border = BorderStroke(1.dp, Color(0xFFD1D5DB))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 10.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.CalendarMonth,
                                contentDescription = null,
                                tint = ShajeenDarkBlue,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "تاريخ السفر *",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = Color.Gray,
                                    fontSize = 11.sp
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = departureDate.ifEmpty { "اختر التاريخ" },
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = if (departureDate.isNotEmpty()) ShajeenDarkBlue else Color.Gray,
                                fontSize = 13.sp
                            )
                        )
                    }
                }

                // Return Date Field (Optional)
                OutlinedCard(
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            if (!hasReturnDate) {
                                hasReturnDate = true
                            }
                            returnDatePicker.show()
                        }
                        .testTag("travel_return_date_selector"),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.outlinedCardColors(
                        containerColor = if (hasReturnDate && returnDate.isNotEmpty())
                            ShajeenSkyBlue.copy(alpha = 0.08f)
                        else
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)
                    ),
                    border = BorderStroke(
                        1.dp,
                        if (hasReturnDate && returnDate.isNotEmpty()) ShajeenSkyBlue else Color(0xFFD1D5DB)
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 10.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.CalendarMonth,
                                    contentDescription = null,
                                    tint = if (hasReturnDate) ShajeenSkyBlue else Color.Gray,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "تاريخ العودة",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = Color.Gray,
                                        fontSize = 11.sp
                                    )
                                )
                            }

                            if (returnDate.isNotEmpty()) {
                                Text(
                                    text = "إلغاء",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = MaterialTheme.colorScheme.error,
                                        fontSize = 10.sp
                                    ),
                                    modifier = Modifier.clickable {
                                        returnDate = ""
                                        hasReturnDate = false
                                    }
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (returnDate.isNotEmpty()) returnDate else "اتجاه واحد",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = if (returnDate.isNotEmpty()) FontWeight.SemiBold else FontWeight.Normal,
                                color = if (returnDate.isNotEmpty()) ShajeenDarkBlue else Color.Gray,
                                fontSize = 13.sp
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // 3. Number of Passengers Stepper
            OutlinedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("travel_passengers_stepper_card"),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.outlinedCardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)
                ),
                border = BorderStroke(1.dp, Color(0xFFD1D5DB))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            modifier = Modifier.size(34.dp),
                            shape = RoundedCornerShape(8.dp),
                            color = ShajeenGold.copy(alpha = 0.2f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.People,
                                contentDescription = null,
                                tint = ShajeenDarkBlue,
                                modifier = Modifier
                                    .padding(7.dp)
                                    .size(18.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "عدد المسافرين",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = ShajeenDarkBlue,
                                    fontSize = 14.sp
                                )
                            )
                            Text(
                                text = "البالغين والأطفال",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = Color.Gray,
                                    fontSize = 11.sp
                                )
                            )
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            modifier = Modifier.size(36.dp),
                            shape = RoundedCornerShape(8.dp),
                            color = if (passengersCount > 1) MaterialTheme.colorScheme.surface else Color(0xFFE5E7EB),
                            border = BorderStroke(1.dp, Color(0xFFD1D5DB)),
                            shadowElevation = if (passengersCount > 1) 1.dp else 0.dp
                        ) {
                            IconButton(
                                onClick = { if (passengersCount > 1) passengersCount-- },
                                enabled = passengersCount > 1,
                                modifier = Modifier.testTag("travel_passengers_decrement_btn")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Remove,
                                    contentDescription = "إنقاص عدد المسافرين",
                                    tint = if (passengersCount > 1) ShajeenDarkBlue else Color.LightGray,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }

                        Text(
                            text = "$passengersCount",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = ShajeenDarkBlue,
                                fontSize = 17.sp
                            ),
                            modifier = Modifier
                                .padding(horizontal = 6.dp)
                                .testTag("travel_passengers_count_text")
                        )

                        Surface(
                            modifier = Modifier.size(36.dp),
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.surface,
                            border = BorderStroke(1.dp, ShajeenSkyBlue.copy(alpha = 0.5f)),
                            shadowElevation = 1.dp
                        ) {
                            IconButton(
                                onClick = { if (passengersCount < 20) passengersCount++ },
                                modifier = Modifier.testTag("travel_passengers_increment_btn")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "زيادة عدد المسافرين",
                                    tint = ShajeenSkyBlue,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Submit Action Button
            Button(
                onClick = {
                    if (destination.isBlank()) {
                        destinationError = true
                        Toast.makeText(context, "يرجى كتابة وجهة السفر أولاً", Toast.LENGTH_SHORT).show()
                    } else {
                        destinationError = false
                        onSubmit(
                            TravelInputDetails(
                                destination = destination.trim(),
                                departureDate = departureDate,
                                returnDate = returnDate,
                                passengersCount = passengersCount
                            )
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("travel_submit_search_btn"),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ShajeenPrimaryButton, // #4DB7E8
                    contentColor = Color.White
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = buttonText,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 14.sp
                    )
                )
            }
        }
    }
}

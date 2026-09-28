package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.entity.AgencySettingsEntity
import com.example.data.entity.ClientEntity
import com.example.ui.components.AgencyContactFooter
import com.example.ui.components.OfficialShajeenLogoCard
import com.example.ui.theme.ShajeenDarkBlue
import com.example.ui.theme.ShajeenGold
import com.example.ui.theme.ShajeenSkyBlue
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

import com.example.ui.components.AgencyOwnerPortalCard

@Composable
fun ProfileScreen(
    currentClient: ClientEntity?,
    agencySettings: AgencySettingsEntity?,
    currentLanguage: com.example.ui.locale.AppLanguage = com.example.ui.locale.LocalAppLanguage.current,
    onLanguageSelected: (com.example.ui.locale.AppLanguage) -> Unit = {},
    onOwnerLoginClick: () -> Unit = {},
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    val strings = com.example.ui.locale.LocalAppStrings.current
    val scrollState = rememberScrollState()

    val formattedDate = currentClient?.let {
        val locale = if (currentLanguage == com.example.ui.locale.AppLanguage.ARABIC) Locale("ar") else Locale.ENGLISH
        SimpleDateFormat("yyyy/MM/dd", locale).format(Date(it.registeredAt))
    } ?: strings.today

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            // Profile Top Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("profile_header_card"),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = ShajeenDarkBlue),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(ShajeenDarkBlue, ShajeenSkyBlue)
                            )
                        )
                        .padding(20.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                        Box(
                            modifier = Modifier
                                .size(70.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = ShajeenGold,
                                modifier = Modifier.size(38.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = currentClient?.fullName ?: strings.unregisteredGuest,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 18.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = currentClient?.phone ?: "",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color.White.copy(alpha = 0.85f),
                                fontWeight = FontWeight.SemiBold
                            )
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = ShajeenGold.copy(alpha = 0.2f)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.VerifiedUser,
                                    contentDescription = null,
                                    tint = ShajeenGold,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = strings.verifiedClient,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = Color.White,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Dedicated Application Language & Layout Direction Switcher Card
            com.example.ui.components.LanguageSelectionCard(
                currentLanguage = currentLanguage,
                onLanguageSelected = onLanguageSelected
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Official Information Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = strings.officialDocsTitle,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = ShajeenDarkBlue
                        )
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    ProfileItemRow(
                        icon = Icons.Default.CreditCard,
                        label = strings.idType,
                        value = currentClient?.idType ?: strings.idTypeNational
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))

                    ProfileItemRow(
                        icon = Icons.Default.Badge,
                        label = strings.idNumber,
                        value = currentClient?.idNumber ?: "---"
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))

                    ProfileItemRow(
                        icon = Icons.Default.Public,
                        label = strings.countryGovernorate,
                        value = "${currentClient?.country ?: "اليمن"} - ${currentClient?.city ?: "صنعاء"}"
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))

                    ProfileItemRow(
                        icon = Icons.Default.LocationOn,
                        label = strings.districtArea,
                        value = "${currentClient?.district ?: "السبعين"} / ${currentClient?.area ?: "المنطقة الرئيسية"}"
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))

                    ProfileItemRow(
                        icon = Icons.Default.Phone,
                        label = strings.registrationDate,
                        value = formattedDate
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Agency Owner Portal Quick Card ("بوابة مالك الوكالة")
            AgencyOwnerPortalCard(
                onOwnerLoginClick = onOwnerLoginClick
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Logout / Switch Account Button
            OutlinedButton(
                onClick = onLogout,
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("logout_profile_btn")
            ) {
                Icon(Icons.Default.Logout, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(strings.logout, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Official Agency Logo
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                OfficialShajeenLogoCard(width = 165, elevation = 2)
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Agency Contact Footer
            AgencyContactFooter(settings = agencySettings)

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
fun ProfileItemRow(
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            shape = RoundedCornerShape(10.dp),
            color = ShajeenSkyBlue.copy(alpha = 0.1f),
            modifier = Modifier.size(38.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = ShajeenSkyBlue,
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall.copy(color = Color.Gray, fontSize = 11.sp)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    }
}

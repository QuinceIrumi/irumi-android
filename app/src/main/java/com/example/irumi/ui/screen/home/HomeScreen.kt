package com.example.irumi.ui.screen.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.example.irumi.ui.theme.BrandGreen

data class Friend(val id: Int, val name: String)

@Composable
fun HomeScreen(brand: Color = BrandGreen) {
    // 샘플 데이터
    val friends = listOf(
        Friend(0, "나"), Friend(1, "민수"), Friend(2, "나연")
    )
    var selectedFriend by remember { mutableStateOf(friends.first()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()) // 전체 스크롤 허용
            .padding(16.dp)
    ) {
        // 1. 친구 목록 (고정 가로 스크롤)
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(friends) { friend ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clickable { selectedFriend = friend }
                ) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(if (selectedFriend == friend) brand else Color.LightGray)
                    )
                    Text(friend.name, fontSize = 14.sp)
                }
            }
            item {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(Color.Gray),
                    contentAlignment = Alignment.Center
                ) {
                    Text("+", fontSize = 24.sp, color = Color.White)
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // 2. 내 화면인지 친구 화면인지 구분
        if (selectedFriend.id == 0) {
            // === 나의 화면 ===
            MyScoreSection(score = 81)
            Spacer(Modifier.height(12.dp))
            TodoSection()
            Spacer(Modifier.height(16.dp))
            StreakSection()
        } else {
            // === 친구 비교 화면 ===
            FriendCompareSection(myScore = 81, friendScore = 92, friendName = selectedFriend.name)
            Spacer(Modifier.height(16.dp))
            StreakSection(friendName = selectedFriend.name)
        }
    }
}

@Composable
fun MyScoreSection(score: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "내 점수",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = BrandGreen
        )
        Text(
            text = "${score}점",
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

@Composable
fun FriendCompareSection(
    myScore: Int,
    friendScore: Int,
    friendName: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "$friendName 와의 비교",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = BrandGreen
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "나", fontWeight = FontWeight.Bold)
                Text(text = "${myScore}점", color = Color.Red, fontSize = 22.sp)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = friendName, fontWeight = FontWeight.Bold)
                Text(text = "${friendScore}점", color = Color.Green, fontSize = 22.sp)
            }
        }
    }
}

@Composable
fun TodoSection() {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { /* 탭 전환 */ }, colors = ButtonDefaults.buttonColors(containerColor = BrandGreen)) {
                Text("데일리")
            }
            Button(onClick = { /* 탭 전환 */ }, colors = ButtonDefaults.buttonColors(containerColor = BrandGreen)) {
                Text("주간/월간")
            }
        }
        Spacer(Modifier.height(8.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp) // 세로 스크롤 영역
        ) {
            items((1..8).toList()) { i ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(checked = i == 3, onCheckedChange = {})
                    Text("미션 $i", fontSize = 16.sp)
                }
            }
        }
    }
}

@Composable
fun StreakSection(friendName: String? = null) {
    val streakDays = List(60) { it % 7 != 0 } // 예시 데이터
    Column {
        Text(
            if (friendName == null) "나의 스트릭" else "$friendName 의 스트릭",
            fontWeight = FontWeight.Bold, fontSize = 18.sp
        )
        Spacer(Modifier.height(8.dp))
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        ) {
            items(streakDays.size) { idx ->
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .padding(2.dp)
                        .background(if (streakDays[idx]) BrandGreen else Color.LightGray)
                )
            }
        }
    }
}

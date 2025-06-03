package com.example.mobile_az

sealed class OnBoardingPage(
    val title: String,
    val description: String,
    val imageRes: Int
) {
    object Page1 : OnBoardingPage("Easy Time Management", "With management based on priority and daily tasks, it will give you convenience in managing and determining the tasks that must be done first ", R.drawable.page1)
    object Page2 : OnBoardingPage("Increase Work Effectiveness", "Time management and the determination of more important tasks will give your job statistics better and always improve", R.drawable.page2)
    object Page3 : OnBoardingPage("Reminder Notification", "The advantage of this application is that it also provides reminders for you so you don't forget to keep doing your assignments well and according to the time you have set", R.drawable.page3)
}

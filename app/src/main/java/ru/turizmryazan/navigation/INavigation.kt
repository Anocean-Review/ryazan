package ru.turizmryazan.navigation

interface INavigation {

    fun openTestFragment()
    fun openWhereToStay()
    fun openWhereToStayFilter()
    fun openWhereToStayDetail(id: String?)
    fun openWelcome()
    fun back()
}
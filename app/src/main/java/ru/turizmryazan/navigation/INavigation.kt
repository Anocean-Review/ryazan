package ru.turizmryazan.navigation

interface INavigation {

    fun back()
    fun openErrorServerFragment()
    fun openTestFragment()
    fun openWhereToStay()
    fun openWhereToStayFilter()
    fun openWhereToStayDetail(id: String?)
    fun openWelcome()
    fun openWhereToEat()
    fun openWhereToEatFilter()
    fun openWhereToEatDetail(id: String?)
    fun openWhereToGo()
    fun openWhereToGoFilter()
    fun openWhereToGoDetail(id: String?)
    fun openLogin()
    fun openForgetPassword()
    fun openRegistration()
}
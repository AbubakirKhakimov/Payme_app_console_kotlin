interface Plan {
    //Service mode
    fun fileRead()
    fun fileWrite()

    //Authorization mode
    fun registration()
    fun login()

    //Main menu mode
    fun addCard()
    fun moneyTransfer()
    fun historyTransfers()
    fun cardsDisplay()
}
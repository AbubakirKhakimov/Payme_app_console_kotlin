class UserData {
    //Authorization data
    var nameSurname:String? = null
    var number:String?=null
    var pasword:String?=null
    //Card data
    var countCardsList:Int=0
    var cardsList = ArrayList<CardData>()
    //History data
    var countHistoryList:Int=0
    var historyList = ArrayList<HistoryTransfers>()
}
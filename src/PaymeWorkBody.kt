/**
 * X.A Technologies, 2021
 * Creator -> Xakimov Abubakir (c)
 * Create date -> 15.10.2021
 * Update date v1.0 -> 16.10.2021
 * Update date v2.0 -> 17.10.2021
 */
import java.io.File
import java.io.PrintWriter
import java.util.*
import kotlin.collections.ArrayList

class PaymeWorkBody:Plan {

    val usersDataList = ArrayList<UserData>()
    val inputConsole = Scanner(System.`in`)
    var loginTrue = false
    var currentUserIndex=-1

    override fun fileRead() {
        val file = File("usersDataFile.txt")
        val inputfile = Scanner(file)

        while (inputfile.hasNextLine()){
            val userData = UserData()
            //Authorization data
            userData.nameSurname=inputfile.nextLine()
            userData.number=inputfile.nextLine()
            userData.pasword=inputfile.nextLine()
            //Card data
            userData.countCardsList=inputfile.nextLine().toInt()
            repeat(userData.countCardsList){
                val cardData = CardData()
                cardData.cardName=inputfile.nextLine()
                cardData.cardNumber=inputfile.nextLine().toLong()
                cardData.cardDate=inputfile.nextLine()
                cardData.cardRemainder=inputfile.nextLine().toDouble()
                userData.cardsList.add(cardData)
            }
            //History data
            userData.countHistoryList=inputfile.nextLine().toInt()
            repeat(userData.countHistoryList){
                val historyTransfer = HistoryTransfers()
                historyTransfer.senderName=inputfile.nextLine()
                historyTransfer.recipientName=inputfile.nextLine()
                historyTransfer.senderCardNum=inputfile.nextLine()
                historyTransfer.recipientCardNum=inputfile.nextLine()
                historyTransfer.amount=inputfile.nextLine().toInt()
                historyTransfer.dateTransfer=inputfile.nextLine()
                userData.historyList.add(historyTransfer)
            }

            usersDataList.add(userData)
        }

        inputfile.close()
    }

    override fun fileWrite() {
        val file = File("usersDataFile.txt")
        val write = PrintWriter(file)

        for (userData in usersDataList){
            //Authorization data
            write.println(userData.nameSurname)
            write.println(userData.number)
            write.println(userData.pasword)
            //Card data
            write.println(userData.countCardsList)
            repeat(userData.countCardsList){
                write.println(userData.cardsList[it].cardName)
                write.println(userData.cardsList[it].cardNumber)
                write.println(userData.cardsList[it].cardDate)
                write.println(userData.cardsList[it].cardRemainder)
            }
            //History data
            write.println(userData.countHistoryList)
            repeat(userData.countHistoryList){
                write.println(userData.historyList[it].senderName)
                write.println(userData.historyList[it].recipientName)
                write.println(userData.historyList[it].senderCardNum)
                write.println(userData.historyList[it].recipientCardNum)
                write.println(userData.historyList[it].amount)
                write.println(userData.historyList[it].dateTransfer)
            }
        }

        write.close()
    }

    override fun registration() {
        println("-------------->Ro'yxatdan o'tish<--------------")
        val userData = UserData()
        print("Ism va familiyangizni kiriting (Abubakir Xakimov): ")
        userData.nameSurname = readLine()

        var work=true
        while (work) {
            print("Telefon raqamingizni kiriting: ")
            val numberEnter = inputConsole.next()

            for (i in usersDataList.indices){
                if (usersDataList[i].number==numberEnter){
                    println("Bunday raqamli foydalanuvchi ro'yxatdan o'tgan!!!")
                    break
                }else if (i==usersDataList.size-1){
                    userData.number=numberEnter
                    work=false
                }
            }
        }

        print("Parol kiriting: ")
        userData.pasword=inputConsole.next()

        usersDataList.add(userData)
        currentUserIndex=usersDataList.size-1
        fileWrite()
        println("Muvaffaqiyatli ro'yxatdan o'tdingiz!!!")
    }

    override fun login() {
        println("-------------->Kirish<--------------")
        loginTrue=false
        var index=-1
        var work1=true
        var work2=true
        while (work1) {
            print("Telefon raqamingizni kiriting (0-chiqish): ")
            val numberEnter = inputConsole.next()

            if (numberEnter=="0"){
                work1=false
                work2=false
            }else {
                for (i in usersDataList.indices) {
                    if (usersDataList[i].number == numberEnter) {
                        index = i
                        work1 = false
                        break
                    } else if (i == usersDataList.size - 1) {
                        println("Bunday raqam oldin ro'yxatdan o'tmagan!!!\n")
                    }
                }
            }
        }

        while (work2) {
            print("Parol kiriting (0-chiqish): ")
            val paswordEnter = inputConsole.next()

           if (paswordEnter=="0"){
                break
           }else if (usersDataList[index].pasword == paswordEnter) {
                loginTrue=true
                currentUserIndex=index
                break
           }else{
                println("Parol noto'g'ri kiritildi!!!\n")
           }
        }
    }

    override fun addCard() {
        println("-------------->Karta qo'shish<--------------")
        if (usersDataList[currentUserIndex].countCardsList==5){
            println("Kechirasiz bitta foydalanuvchi faqat 5 ta karta qo'shishi mumkin!!!")
        }else {
            val cardData = CardData()
            print("Karta nomini kiriting: ")
            cardData.cardName = readLine()
            print("Karta raqamini kiriting: ")
            cardData.cardNumber = inputConsole.nextLong()
            print("Karta amal qilish muddatini kiriting (mm/yy): ")
            cardData.cardDate = inputConsole.next()
            cardData.cardRemainder = Random().nextInt(500000).toDouble()

            usersDataList[currentUserIndex].cardsList.add(cardData)
            usersDataList[currentUserIndex].countCardsList++
            fileWrite()
            println("Karta muvaffaqiyatli qo'shildi!!!")
        }
    }

    override fun moneyTransfer() {
        println("-------------->O'tkazma<--------------")
        print("""
            1 -> Mening kartalarim bo'yicha o'tkazma
            2 -> Boshqa kartaga o'tkazma
            -> 
        """.trimIndent())

        when(inputConsole.nextInt()){
            1->{
                println("-------------->Kartalarim bo'yicha o'tkazma<--------------")
                while (true){
                println("--------->Kartalar<---------")
                usersDataList[currentUserIndex].cardsList.forEachIndexed { index, cardData ->
                    println("${index + 1}. ${cardData.cardName} -> ${cardData.cardNumber.toString().substring(0, 4)} " +
                            "**** **** ${cardData.cardNumber.toString().substring(12)} -> ${cardData.cardRemainder}"
                    )
                }
                print("\nQaysi kartadan pul yechilsin: ")
                val indexSender = inputConsole.nextInt()-1
                print("Qaysi kartaga o'tkazilsin: ")
                val indexRecipient = inputConsole.nextInt()-1
                print("Qancha mablag' o'tkazilsin: ")
                val amount = inputConsole.nextInt()

                if (usersDataList[currentUserIndex].cardsList[indexSender].cardRemainder<amount){
                    println("Kartada mablag' yetarli emas!!!\n")
                }else{
                print("\n${usersDataList[currentUserIndex].cardsList[indexSender].cardName} " +
                        secureCardNum(usersDataList[currentUserIndex].cardsList[indexSender].cardNumber!!) +
                        " ${usersDataList[currentUserIndex].cardsList[indexSender].cardRemainder}")
                print(" -> ")
                println("${usersDataList[currentUserIndex].cardsList[indexRecipient].cardName} " +
                        secureCardNum(usersDataList[currentUserIndex].cardsList[indexRecipient].cardNumber!!) +
                        " ${usersDataList[currentUserIndex].cardsList[indexRecipient].cardRemainder}")
                println("Summa: $amount")

                print("\nTasdiqlash uchun '+' belgisini bosing (+/-): ")
                if (inputConsole.next()=="+") {
                    usersDataList[currentUserIndex].cardsList[indexSender].cardRemainder -= amount
                    usersDataList[currentUserIndex].cardsList[indexRecipient].cardRemainder += amount

                    if (usersDataList[currentUserIndex].countHistoryList == 5) {
                        usersDataList[currentUserIndex].historyList.removeAt(0)
                    } else {
                        usersDataList[currentUserIndex].countHistoryList++
                    }
                    val historyTransfer = HistoryTransfers()
                    historyTransfer.senderName = usersDataList[currentUserIndex].nameSurname
                    historyTransfer.recipientName = usersDataList[currentUserIndex].nameSurname
                    historyTransfer.senderCardNum =
                        secureCardNum(usersDataList[currentUserIndex].cardsList[indexSender].cardNumber!!)
                    historyTransfer.recipientCardNum =
                        secureCardNum(usersDataList[currentUserIndex].cardsList[indexRecipient].cardNumber!!)
                    historyTransfer.amount = amount
                    historyTransfer.dateTransfer = Date().toString()
                    usersDataList[currentUserIndex].historyList.add(historyTransfer)

                    fileWrite()
                    println("Muvaffaqiyatli bajarildi!!!")
                    break
                }
                }
                }
            }
            2->{
                println("-------------->Boshqa kartaga o'tkazma<--------------")
                var recipientCardIndex=-1
                var recipientUserIndex=-1
                var senderCardIndex:Int
                var work = true
                while (work) {
                    print("Mablag'ni qabul qiluvchi karta raqamini kiriting (0-chiqish): ")
                    val recipientCard = inputConsole.nextLong()

                    if (recipientCard==0L){
                        return
                    }

                    for (i in usersDataList.indices) {
                        if (!work){
                            break
                        }
                        for (j in usersDataList[i].cardsList.indices) {
                            if (recipientCard == usersDataList[i].cardsList[j].cardNumber) {
                                recipientUserIndex=i
                                recipientCardIndex=j
                                work = false
                                break
                            }
                            else if (i==usersDataList.size-1 && j==usersDataList[i].cardsList.size-1){
                                println("Bunday karta mavjud emas!!!")
                            }
                        }
                    }
                }

                while (true){
                println("--------->Kartalar<---------")
                usersDataList[currentUserIndex].cardsList.forEachIndexed { index, cardData ->
                    println("${index + 1}. ${cardData.cardName} -> ${cardData.cardNumber.toString().substring(0, 4)} " +
                            "**** **** ${cardData.cardNumber.toString().substring(12)} -> ${cardData.cardRemainder}"
                    )
                }
                print("\nQaysi kartadan pul yechilsin (0-chiqish): ")
                senderCardIndex = inputConsole.nextInt()-1

                    if (senderCardIndex==-1){
                        return
                    }

                print("Qancha mablag' o'tkazilsin: ")
                val amount = inputConsole.nextInt()

                if (usersDataList[currentUserIndex].cardsList[senderCardIndex].cardRemainder<amount){
                    println("Kartada mablag' yetarli emas!!!\n")
                }else {
                    print("\n${usersDataList[currentUserIndex].cardsList[senderCardIndex].cardName} " +
                            secureCardNum(usersDataList[currentUserIndex].cardsList[senderCardIndex].cardNumber!!) +
                            " ${usersDataList[currentUserIndex].cardsList[senderCardIndex].cardRemainder}")
                    print(" -> ")
                    println("${usersDataList[recipientUserIndex].nameSurname} " +
                            secureCardNum(usersDataList[recipientUserIndex].cardsList[recipientCardIndex].cardNumber!!))
                    println("Summa: $amount")

                    print("\nTasdiqlash uchun '+' belgisini bosing (+/-): ")
                    if (inputConsole.next()=="+") {
                        usersDataList[currentUserIndex].cardsList[senderCardIndex].cardRemainder -= amount
                        usersDataList[recipientUserIndex].cardsList[recipientCardIndex].cardRemainder += amount

                        if (usersDataList[currentUserIndex].countHistoryList == 5) {
                            usersDataList[currentUserIndex].historyList.removeAt(0)
                        } else {
                            usersDataList[currentUserIndex].countHistoryList++
                        }

                        if (usersDataList[recipientUserIndex].countHistoryList == 5) {
                            usersDataList[recipientUserIndex].historyList.removeAt(0)
                        } else {
                            usersDataList[recipientUserIndex].countHistoryList++
                        }

                        val historyTransfer = HistoryTransfers()
                        historyTransfer.senderName = usersDataList[currentUserIndex].nameSurname
                        historyTransfer.recipientName = usersDataList[recipientUserIndex].nameSurname
                        historyTransfer.senderCardNum =
                            secureCardNum(usersDataList[currentUserIndex].cardsList[senderCardIndex].cardNumber!!)
                        historyTransfer.recipientCardNum =
                            secureCardNum(usersDataList[recipientUserIndex].cardsList[recipientCardIndex].cardNumber!!)
                        historyTransfer.amount = amount
                        historyTransfer.dateTransfer = Date().toString()
                        usersDataList[currentUserIndex].historyList.add(historyTransfer)
                        usersDataList[recipientUserIndex].historyList.add(historyTransfer)

                        fileWrite()
                        println("Muvaffaqiyatli bajarildi!!!")
                        break
                    }
                }
                }
            }
            else->{
                println("Noto'g'ri buyruq kiritildi!!!")
            }
        }
    }

    override fun historyTransfers() {
        println("-------------->O'tkazmalar tarixi<--------------")
        if (usersDataList[currentUserIndex].countHistoryList==0){
            println("Sizda xozircha o'tkazmalar mavjud emas!!!")
        }else{
            usersDataList[currentUserIndex].historyList.forEachIndexed { i, history ->
                println("----------->${i+1}<-----------")
                println("Kimdan: ${history.senderName}")
                println("Kimga: ${history.recipientName}")
                println("Jo'natuvchi karta raqami: ${history.senderCardNum}")
                println("Qabul qiluvchi karta raqami: ${history.recipientCardNum}")
                println("Summa: ${history.amount}")
                println("Sana: ${history.dateTransfer}")
            }
        }
    }

    override fun cardsDisplay() {
        if (usersDataList[currentUserIndex].countCardsList==0){
            println("Xozircha kartalar yo'q!")
        }else {
            usersDataList[currentUserIndex].cardsList.forEachIndexed { index, cardData ->
                println("${index + 1}. ${cardData.cardName} -> ${secureCardNum(cardData.cardNumber!!)} -> ${cardData.cardRemainder}")
            }
        }
    }

    fun secureCardNum(cardNum:Long):String{
        return cardNum.toString().substring(0, 4)+" **** **** "+cardNum.toString().substring(12)
    }

}
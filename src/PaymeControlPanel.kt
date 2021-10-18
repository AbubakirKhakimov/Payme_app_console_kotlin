import java.util.*

val paymeWorkBody = PaymeWorkBody()

fun main() {
    paymeWorkBody.fileRead()

    while (true){
        println("\n--------->Avtorizatsiya<---------")
        print("""
            1 -> Ro'yxatdan o'tish
            2 -> Kirish
            -> 
        """.trimIndent())

        when(Scanner(System.`in`).nextInt()){
            1->{
                paymeWorkBody.registration()
                paymeMainMenu()
            }
            2->{
                paymeWorkBody.login()
                if (paymeWorkBody.loginTrue){
                    paymeMainMenu()
                }
            }
        }
    }
}

fun paymeMainMenu() {
    while (true) {
        println("\n-------------->PAYME<--------------")
        paymeWorkBody.cardsDisplay()

        println()
        print("""
            1 -> Karta qo'shish
            2 -> O'tkazma
            3 -> O'tkazmalar tarixi
            4 -> Chiqish
            -> 
        """.trimIndent())

        when (Scanner(System.`in`).nextInt()) {
            1 -> paymeWorkBody.addCard()
            2 -> paymeWorkBody.moneyTransfer()
            3 -> paymeWorkBody.historyTransfers()
            4 -> break
        }
    }
}
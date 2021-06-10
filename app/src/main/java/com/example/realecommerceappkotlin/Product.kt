package com.example.realecommerceappkotlin

class Product(Pid : Int,PName: String,PPrice : Int,PPicture : String) {
    var id : Int = Pid
    var name : String = PName
    var price: Int = PPrice
    var picture : String = "http://192.168.1.3/OnlineStoreApp/osimages/$PPicture"
}
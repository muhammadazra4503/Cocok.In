package com.dicoding.cocokin.data.pref

data class PaymentHistoryRequest(
    val idkeranjang: String,
    val idbarang: String,
    var sessionid: String,
    val nama: String,
    val harga: String,
    val gambar: String,
    val catatan: String,
    val totalharga: String,
    val alamat: String
)
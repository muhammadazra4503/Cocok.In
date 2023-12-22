package com.dicoding.cocokin.data.remote.response

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class CartResponse(
	@field:SerializedName("CartResponse")
	val cartResponse: List<CartResponseItem>
)

data class CartResponseItem(
	@SerializedName("idkeranjang")
	val idkeranjang: Int,

	@SerializedName("idbarang")
	val idbarang: Int,

	@SerializedName("nama")
	val nama: String,

	@SerializedName("harga")
	val harga: Int,

	@SerializedName("catatan")
	var catatan: String? = "",

	@SerializedName("sessionid")
	val sessionid: String,

	@SerializedName("gambar")
	val gambar: String,

	var isChecked: Boolean = false
) : Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readInt(),
		parcel.readInt(),
		parcel.readString()!!,
		parcel.readInt(),
		parcel.readString(),
		parcel.readString()!!,
		parcel.readString()!!,
		parcel.readByte() != 0.toByte()
	)

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeInt(idkeranjang)
		parcel.writeInt(idbarang)
		parcel.writeString(nama)
		parcel.writeInt(harga)
		parcel.writeString(catatan)
		parcel.writeString(sessionid)
		parcel.writeString(gambar)
		parcel.writeByte(if (isChecked) 1 else 0)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<CartResponseItem> {
		override fun createFromParcel(parcel: Parcel): CartResponseItem {
			return CartResponseItem(parcel)
		}

		override fun newArray(size: Int): Array<CartResponseItem?> {
			return arrayOfNulls(size)
		}
	}
}
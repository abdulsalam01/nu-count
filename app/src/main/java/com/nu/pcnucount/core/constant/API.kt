package com.nu.pcnucount.core.constant

object API {
    const val isOffline = true

    val BASE_URL = if (isOffline) "http://192.168.100.81:8800/lakpesdam/" else "network/"
    const val PREFIX = "api/"

    // POST
    const val LOGIN = PREFIX + "login"
    const val INPUT_DATA = PREFIX + "input/data/"

    const val _GET = PREFIX + "get/"
    // GET
    const val GET_DESA = _GET + "desa"
    const val GET_DUSUN = _GET + "dusun"
    const val GET_KECAMATAN = _GET + "kecamatan"
    const val GET_RT = _GET + "rt"
    const val GET_RW = _GET + "rw"
    const val GET_PEKERJAAN = _GET + "pekerjaan"
    const val GET_SUB_1 = _GET + "sub1/"
    const val GET_SUB_2 = _GET + "sub2/"

    // one-signal-api
    const val appID = "822ed180-fd27-4806-b47d-d60e96ab9e0e"
}
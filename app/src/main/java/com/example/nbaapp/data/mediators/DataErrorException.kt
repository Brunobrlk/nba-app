package com.example.nbaapp.data.mediators

import com.example.nbaapp.core.utils.DataError

class DataErrorException(val dataError: DataError.Remote): Exception()
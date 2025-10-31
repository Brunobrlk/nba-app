package com.example.nbaapp.data.mediators

import com.example.nbaapp.core.helpers.DataError

class DataErrorException(val dataError: DataError.Remote): Exception()
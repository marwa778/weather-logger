package com.example.weatherlogger.repository.repositoryinterface

import com.example.weatherlogger.network.responses.Coordinates

interface LocationRepositoryInterface {

    suspend fun getCurrentLocation(): Coordinates
}

package me.ismartin.pokedexcompose.business

sealed class ApiResource<T>(
    val data: T? = null,
    val message: String? = null,
) {
    class Success<T>(data: T?) : ApiResource<T>(data)
    class Failure<T>(data: T? = null, message: String?) : ApiResource<T>(data, message)
}

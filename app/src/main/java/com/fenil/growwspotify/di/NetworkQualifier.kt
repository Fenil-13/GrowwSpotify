package com.fenil.growwspotify.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class PlainHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthInterceptorHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiRetrofitClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AccountApiRetrofitClient
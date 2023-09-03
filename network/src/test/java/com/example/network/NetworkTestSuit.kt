package com.example.network

import com.example.network.data.GithubApiServiceTest
import com.example.network.provider.OkHttpProviderTest
import com.example.network.provider.RetrofitProviderTest
import com.example.network.utils.ErrorHandlerTest
import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses

@RunWith(Suite::class)
@SuiteClasses(GithubApiServiceTest::class, OkHttpProviderTest::class, RetrofitProviderTest::class, ErrorHandlerTest::class)
class NetworkTestSuit

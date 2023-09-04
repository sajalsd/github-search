package com.example

import com.example.details.DetailsTestSuite
import com.example.search.SearchTestSuite
import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses

@RunWith(Suite::class)
@SuiteClasses(
    SearchTestSuite::class,
    DetailsTestSuite::class
)
class UnitTestSuite

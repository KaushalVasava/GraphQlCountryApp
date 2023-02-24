package com.lahsuak.apps.graphqlcountry.data

import com.apollographql.apollo3.ApolloClient
import com.lahsuak.CountriesQuery
import com.lahsuak.CountryQuery
import com.lahsuak.apps.graphqlcountry.domain.CountryClient
import com.lahsuak.apps.graphqlcountry.domain.DetailedCountry
import com.lahsuak.apps.graphqlcountry.domain.SimpleCountry

class ApolloCountryClient(
    private val apolloClient: ApolloClient
): CountryClient {

    override suspend fun getCountries(): List<SimpleCountry> {
        return apolloClient
            .query(CountriesQuery())
            .execute()
            .data
            ?.countries
            ?.map { it.toSimpleCountry() }
            ?: emptyList()
    }

    override suspend fun getCountry(code: String): DetailedCountry? {
        return apolloClient
            .query(CountryQuery(code))
            .execute()
            .data
            ?.country
            ?.toDetailedCountry()
    }
}
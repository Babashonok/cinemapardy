package com.dev.babak.cinemapardy.es.client.query

import org.elasticsearch.action.support.QuerySourceBuilder
import org.elasticsearch.index.query.QueryBuilder

/**
 * @author Dzmitry Mikhievich
 */
final class SearchQueries {

    static SearchQuery create(String value) {
        return { -> value }
    }

    static SearchQuery create(QueryBuilder queryBuilder) {
        return { -> new QuerySourceBuilder(queryBuilder: queryBuilder).toString() }
    }
}

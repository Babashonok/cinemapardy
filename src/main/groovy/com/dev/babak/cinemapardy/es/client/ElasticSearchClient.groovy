package com.dev.babak.cinemapardy.es.client

import com.dev.babak.cinemapardy.es.client.query.SearchQuery

interface ElasticSearchClient {

    Map<String, ?> index(IndexType indexType, String orgId, Object entity) throws IndexingException

    Map<String, ?> bulkIndex(IndexType indexType, String orgId, Iterable<?> entities) throws IndexingException

    def <T> List<T> search(IndexType indexType, String orgId, SearchQuery query, Class<T> type)

    Map<String, ?> search(IndexType indexType, String orgId, SearchQuery query)

    Map<String, ?> deleteIndex(IndexType indexType, String orgId)

    Map<String, ?> refreshIndex(IndexType indexType, String orgId)

    Map<String, ?> createIndex(IndexType indexType, String orgId)

    Map<String, ?> createIndex(IndexType indexType, String orgId, String settings)

    Map<String, ?> addAlias(IndexType indexType, String orgId)

    /**
     * Update index settings
     *
     * @param indexType
     * @param orgId
     * @param settings
     * @return response body
     * @deprecated method will be removed in the further releases. Please, use {@link #updateIndexSettings} method instead
     */
    @Deprecated
    Map<String, ?> updateSettings(IndexType indexType, String orgId, String settings)

    Map<String, ?> updateIndexSettings(IndexType indexType, String orgId, String settings)

    Map<String, ?> updateAllIndexesSettings(String settings)

    Map<String, ?> putMapping(IndexType indexType, String orgId, source)
}
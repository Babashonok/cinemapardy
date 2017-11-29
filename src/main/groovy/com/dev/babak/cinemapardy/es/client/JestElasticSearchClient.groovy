package com.dev.babak.cinemapardy.es.client

import com.dev.babak.cinemapardy.es.client.query.SearchQuery
import groovy.util.logging.Slf4j
import io.searchbox.action.Action
import io.searchbox.action.BulkableAction
import io.searchbox.client.JestClient
import io.searchbox.client.JestResult
import io.searchbox.core.Bulk
import io.searchbox.core.Index
import io.searchbox.core.Search
import io.searchbox.indices.CreateIndex
import io.searchbox.indices.DeleteIndex
import io.searchbox.indices.Refresh
import io.searchbox.indices.aliases.AddAliasMapping
import io.searchbox.indices.aliases.ModifyAliases
import io.searchbox.indices.mapping.PutMapping
import io.searchbox.indices.settings.UpdateSettings
import org.elasticsearch.index.mapper.object.ObjectMapper

import static java.lang.String.format
import static java.lang.System.lineSeparator

/**
 * Elastic search client implementation, which is backed by {@link io.searchbox.client.JestClient} instance
 *
 * @author Dzmitry Mikhievich
 */
@Slf4j
class JestElasticSearchClient implements ElasticSearchClient {

    private final JestClient jestClient
    private final ObjectMapper jsonObjectMapper

    JestElasticSearchClient(JestClient jestClient, ObjectMapper jsonObjectMapper) {
        this.jestClient = jestClient
        this.jsonObjectMapper = jsonObjectMapper
    }

    @Override
    Map<String, ?> index(IndexType indexType, String orgId, Object entity) {

        Index addToIndex = new Index.Builder(entity)
                .index(indexType.getSourceIndexName(orgId))
                .type(indexType.getTypeName())
                .build()

        execute(addToIndex)
    }

    @Override
    Map<String, ?> bulkIndex(IndexType indexType, String orgId, Iterable<?> entities) {

        Collection<BulkableAction> addEntityToIndexActions = entities.collect { new Index.Builder(it).build() }
        Bulk bulkIndex = new Bulk.Builder()
                .defaultIndex(indexType.getSourceIndexName(orgId))
                .defaultType(indexType.getTypeName())
                .addAction(addEntityToIndexActions)
                .build()

        def executionResult = execute(bulkIndex)
        if (executionResult.errors) {
            def message = formatErrorMessageForBulkIndexing(executionResult)
            throw new IndexingException(message)
        }
        return executionResult
    }

    @Override
    <T> List<T> search(IndexType indexType, String orgId, SearchQuery query, Class<T> type) {
        Search search = new Search.Builder(query.getValue())
                .addIndex(indexType.getSourceIndexName(orgId))
                .build()

        JestResult searchResult = jestClient.execute(search)

        searchResult.getHits(type)*.source
    }

    @Override
    Map<String, ?> search(IndexType indexType, String orgId, SearchQuery query) {

        Search search = new Search.Builder(query.getValue())
                .addIndex(indexType.getSourceIndexName(orgId))
                .build()

        execute(search)
    }

    @Override
    Map<String, ?> deleteIndex(IndexType indexType, String orgId) {
        def indexName = indexType.getSourceIndexName(orgId)

        log.debug("Delete index [{}]", indexName)
        DeleteIndex deleteIndex = new DeleteIndex.Builder(indexName)
                .build()

        execute(deleteIndex)
    }

    @Override
    Map<String, ?> refreshIndex(IndexType indexType, String orgId) {
        def indexName = indexType.getSourceIndexName(orgId)

        log.debug("Refresh index [{}]", indexName)
        Refresh refresh = new Refresh.Builder()
                .addIndex(indexName)
                .build()

        execute(refresh)
    }

    @Override
    Map<String, ?> createIndex(IndexType indexType, String orgId, String settings = null) {
        def indexName = indexType.getSourceIndexName(orgId)

        log.debug("Create index [{}]", indexName)
        CreateIndex createIndex = new CreateIndex.Builder(indexType.getSourceIndexName(orgId)).settings(settings)
                .build()

        execute(createIndex)
    }

    @Override
    Map<String, ?> addAlias(IndexType indexType, String orgId) {
        def indexName = indexType.getSourceIndexName(orgId)
        def aliasName = indexType.getSourceIndexAlias(orgId)

        log.debug("Add alias [{}] to index [{}]", aliasName, indexName)
        AddAliasMapping addAliasMapping = new AddAliasMapping.Builder(indexName, aliasName).build()
        ModifyAliases modifyAliases = new ModifyAliases.Builder(addAliasMapping).build()

        execute(modifyAliases)
    }

    @Override
    Map<String, ?> updateSettings(IndexType indexType, String orgId, String settings) {
        updateIndexSettings(indexType, orgId, settings)
    }

    @Override
    Map<String, ?> updateIndexSettings(IndexType indexType, String orgId, String settings) {
        def indexName = indexType.getSourceIndexName(orgId)

        log.debug("Update settings for index [{}]: {}", indexName, settings)
        UpdateSettings updateSettings = new UpdateSettings.Builder(settings).addIndex(indexName).build()

        execute(updateSettings)
    }

    @Override
    Map<String, ?> updateAllIndexesSettings(String settings) {
        log.debug("Update settings for all indexes: {}", settings)
        UpdateSettings updateSettings = new UpdateSettings.Builder(settings).build()

        execute(updateSettings)
    }

    @Override
    Map<String, ?> putMapping(IndexType indexType, String orgId, Object source) {
        def indexName = indexType.getSourceIndexName(orgId)

        log.debug("Put mapping for type [{}] in index [{}]", indexType.typeName, indexName)
        PutMapping putMapping = new PutMapping.Builder(indexName, indexType.typeName, source)
                .build()

        execute(putMapping)
    }

    private Map<String, ?> execute(Action action) {
        JestResult result = jestClient.execute(action)
        jsonObjectMapper.unmarshal(result.jsonString, Map)
    }

    private static String formatErrorMessageForBulkIndexing(Map<String, ?> executionResult) {
        String errorItems = executionResult.items.collect {
            "${it.index} -> ${it.type}: ${it.error}"
        }.join(lineSeparator())
        def messageTemplate = 'Following errors occurred during the bulk indexing: %s'
        return format(messageTemplate, errorItems)
    }
}

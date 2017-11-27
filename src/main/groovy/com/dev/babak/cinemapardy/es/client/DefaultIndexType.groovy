package com.dev.babak.cinemapardy.es.client

import static java.lang.String.format

/**
 * Elasticsearch index type abstraction. Implementation is based on the assumption that single index will have one
 * and only one type
 *
 * @author Dzmitry Mikhievich
 */
trait DefaultIndexType implements IndexType {

    @Override
    String getSourceIndexName(String orgId) {
        format(indexNameTemplate, orgId)
    }

    String getSourceIndexAlias(String orgId) {
        String indexName = getSourceIndexName(orgId)
        format(indexAliasTemplate, indexName)
    }

    String getIndexAliasTemplate() {
        '%s-alias'
    }

    abstract String getIndexNameTemplate()

    abstract String getTypeName()
}

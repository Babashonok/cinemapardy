package com.dev.babak.cinemapardy.es.client

/**
 * @author Dzmitry Mikhievich
 */
interface IndexType {

    String getSourceIndexName(String orgId)

    String getSourceIndexAlias(String orgId)

    String getTypeName()
}
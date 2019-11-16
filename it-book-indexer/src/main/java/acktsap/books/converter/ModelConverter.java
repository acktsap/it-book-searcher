/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.books.converter;


public interface ModelConverter<OriginT, ElasticSearchT> {

  OriginT convertToOrigin(ElasticSearchT elasticSearchModel);

  ElasticSearchT convertToEsModel(OriginT originModel);

}

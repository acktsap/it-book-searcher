# It book searcher

Using [National Library of Korea api](https://www.nl.go.kr/nl/service/open/api_util_book.jsp). Make a restful server which provides recent it books.

## Requirements

- Uses elasticsearch 7.4.0 for storage & searching
- Uses Spring boot Web, batch, quartz
- Uses jdk 8

## Build from source

- Prerequisite
  - jdk8
  - gradle
  - docker
- Test : `./gradlew test`
- Build : `./gradlew clean build`
- Make distribution : `./gradlew clean build installDist`
  - Distribution on `$PROJECT_HOME/assembly/build/distribution/`
  - Installed on `$PROJECT_HOME/assembly/install/it-book-searcher`

## Product

- Install : `tar -xvf it-book-searcher-x.x.tar` or `unzip it-book-searcher-x.x.zip` 
- Run
  - ElasticSearch
    - Start:`./bin/start-elasticsearch`
    - Stop:`./bin/stop-elasticsearch`
    - Remove:`./bin/remove-elasticsearch`
  - Indexer :`./bin/indexer`
  - Searcher :`./bin/search-server`

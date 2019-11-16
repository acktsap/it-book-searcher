/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.books.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RawBook {

  // 표제
  @JsonProperty("TITLE")
  protected String title;

  // 권차
  @JsonProperty("VOL")
  protected String volumeNumber;

  // 총서명
  @JsonProperty("SERIES_TITLE")
  protected String seriesTitle;

  // 총서편차
  @JsonProperty("SERIES_NO")
  protected String seriesNumber;

  // 저자
  @JsonProperty("AUTHOR")
  protected String author;

  // ISBN
  @JsonProperty("EA_ISBN")
  protected String isbn;

  // ISBN 부가기호
  @JsonProperty("EA_ADD_CODE")
  protected String isbnAddCode;

  // 세트 ISBN
  @JsonProperty("SET_ISBN")
  protected String setIsbn;

  // 세트 ISBN 부가기호
  @JsonProperty("SET_ADD_CODE")
  protected String setIsbnAddCode;

  // 세트표현 (세트, 전2권.)
  @JsonProperty("SET_EXPRESSION")
  protected String setExpression;

  // 발행처
  @JsonProperty("PUBLISHER")
  protected String publisher;

  // 판사항
  @JsonProperty("EDITION_STMT")
  protected String edititionStatement;

  // 예정가격
  @JsonProperty("PRE_PRICE")
  protected String prePrice;

  // 한국십진분류
  @JsonProperty("KDC")
  protected String kdc;

  // 듀이십진분류
  @JsonProperty("DDC")
  protected String ddc;

  // 페이지
  @JsonProperty("PAGE")
  protected String page;

  // 책크기
  @JsonProperty("BOOK_SIZE")
  protected String booksize;

  // 발행제본형태
  @JsonProperty("FORM")
  protected String form;

  // 출판예정일
  @JsonProperty("PUBLISH_PREDATE")
  protected String publishPredate;

  // 주제
  @JsonProperty("SUBJECT")
  protected String subject;

  // 전자책여부 (Y: 전자책, N : 인쇄책)
  @JsonProperty("EBOOK_YN")
  protected String ebook;

  // 신청여부 (Y: CIP신청, N: CIP신청안함)
  @JsonProperty("CIP_YN")
  protected String cipRequested;

  // CIP 제어번호
  @JsonProperty("CONTROL_NO")
  protected String controlNumber;

  // 표지이미지 URL
  @JsonProperty("TITLE_URL")
  protected String titleUrl;

  // 목차
  @JsonProperty("BOOK_TB_CNT_URL")
  protected String contentsUrl;

  // 책소개
  @JsonProperty("BOOK_INTRODUCTION_URL")
  protected String introductionUrl;

  // 책요약
  @JsonProperty("BOOK_SUMMARY_URL")
  protected String summary;

  // 출판사 홈페이지 URL
  @JsonProperty("PUBLISHER_URL")
  protected String publisherUrl;

  // 등록날짜
  @JsonProperty("INPUT_DATE")
  protected String inputDate;

  // 수정날짜
  @JsonProperty("UPDATE_DATE")
  protected String updateDate;

}

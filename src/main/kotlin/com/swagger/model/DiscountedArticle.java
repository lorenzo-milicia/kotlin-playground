package com.swagger.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * DiscountedArticle
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-05-05T20:35:13.788493400+02:00[Europe/Berlin]")


public class DiscountedArticle extends Article  {
  @JsonProperty("description")
  private String description = null;

  @JsonProperty("fullPrice")
  private BigDecimal fullPrice = null;

  @JsonProperty("discount")
  private Double discount = null;

  public DiscountedArticle description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
   **/
  @Schema(description = "")
  
    public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public DiscountedArticle fullPrice(BigDecimal fullPrice) {
    this.fullPrice = fullPrice;
    return this;
  }

  /**
   * Get fullPrice
   * @return fullPrice
   **/
  @Schema(description = "")
  
    @Valid
    public BigDecimal getFullPrice() {
    return fullPrice;
  }

  public void setFullPrice(BigDecimal fullPrice) {
    this.fullPrice = fullPrice;
  }

  public DiscountedArticle discount(Double discount) {
    this.discount = discount;
    return this;
  }

  /**
   * Get discount
   * @return discount
   **/
  @Schema(description = "")
  
    public Double getDiscount() {
    return discount;
  }

  public void setDiscount(Double discount) {
    this.discount = discount;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DiscountedArticle discountedArticle = (DiscountedArticle) o;
    return Objects.equals(this.description, discountedArticle.description) &&
        Objects.equals(this.fullPrice, discountedArticle.fullPrice) &&
        Objects.equals(this.discount, discountedArticle.discount) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(description, fullPrice, discount, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DiscountedArticle {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    fullPrice: ").append(toIndentedString(fullPrice)).append("\n");
    sb.append("    discount: ").append(toIndentedString(discount)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

package com.fun.driven.development.fun.unified.payments.api.web.rest.vm;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class SaleVM {
  @JsonProperty("token")
  private String token;

  @JsonProperty("amountInCents")
  private Long amountInCents;

  @JsonProperty("currencyIsoCode")
  private String currencyIsoCode;

  public SaleVM token(String token) {
    this.token = token;
    return this;
  }

  /**
   * Get token
   * @return token
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull

@Size(max=500)
  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public SaleVM amountInCents(Long amountInCents) {
    this.amountInCents = amountInCents;
    return this;
  }

  /**
   * Get amountInCents
   * minimum: 1
   * maximum: 100000000
   * @return amountInCents
  */
  @ApiModelProperty(example = "100", required = true, value = "")
  @NotNull

@Min(1L) @Max(100000000L)
  public Long getAmountInCents() {
    return amountInCents;
  }

  public void setAmountInCents(Long amountInCents) {
    this.amountInCents = amountInCents;
  }

  public SaleVM currencyIsoCode(String currencyIsoCode) {
    this.currencyIsoCode = currencyIsoCode;
    return this;
  }

  /**
   * Get currencyIsoCode
   * @return currencyIsoCode
  */
  @ApiModelProperty(example = "EUR", required = true, value = "")
  @NotNull

@Size(min=3,max=3)
  public String getCurrencyIsoCode() {
    return currencyIsoCode;
  }

  public void setCurrencyIsoCode(String currencyIsoCode) {
    this.currencyIsoCode = currencyIsoCode;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SaleVM sale = (SaleVM) o;
    return Objects.equals(this.token, sale.token) &&
        Objects.equals(this.amountInCents, sale.amountInCents) &&
        Objects.equals(this.currencyIsoCode, sale.currencyIsoCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(token, amountInCents, currencyIsoCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Sale {\n");

    sb.append("    token: ").append(toIndentedString(token)).append("\n");
    sb.append("    amountInCents: ").append(toIndentedString(amountInCents)).append("\n");
    sb.append("    currencyIsoCode: ").append(toIndentedString(currencyIsoCode)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}


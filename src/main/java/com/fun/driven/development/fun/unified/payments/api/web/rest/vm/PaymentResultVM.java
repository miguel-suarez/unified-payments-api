package com.fun.driven.development.fun.unified.payments.api.web.rest.vm;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

public class PaymentResultVM {
  /**
   * Gets or Sets resultCode
   */
  public enum ResultCodeEnum {
    CANCELLED("Cancelled"),

    ERROR("Error"),

    PENDING("Pending"),

    REFUSED("Refused"),

    SUCCESS("Success");

    private String value;

    ResultCodeEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static ResultCodeEnum fromValue(String value) {
      for (ResultCodeEnum b : ResultCodeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  @JsonProperty("resultCode")
  private ResultCodeEnum resultCode;

  @JsonProperty("resultDescription")
  private String resultDescription;

  public PaymentResultVM resultCode(ResultCodeEnum resultCode) {
    this.resultCode = resultCode;
    return this;
  }

  /**
   * Get resultCode
   * @return resultCode
  */
  @ApiModelProperty(example = "Success", value = "")


  public ResultCodeEnum getResultCode() {
    return resultCode;
  }

  public void setResultCode(ResultCodeEnum resultCode) {
    this.resultCode = resultCode;
  }

  public PaymentResultVM resultDescription(String resultDescription) {
    this.resultDescription = resultDescription;
    return this;
  }

  /**
   * Get resultDescription
   * @return resultDescription
  */
  @ApiModelProperty(example = "Payment successful", value = "")


  public String getResultDescription() {
    return resultDescription;
  }

  public void setResultDescription(String resultDescription) {
    this.resultDescription = resultDescription;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PaymentResultVM paymentResult = (PaymentResultVM) o;
    return Objects.equals(this.resultCode, paymentResult.resultCode) &&
        Objects.equals(this.resultDescription, paymentResult.resultDescription);
  }

  @Override
  public int hashCode() {
    return Objects.hash(resultCode, resultDescription);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PaymentResult {\n");

    sb.append("    resultCode: ").append(toIndentedString(resultCode)).append("\n");
    sb.append("    resultDescription: ").append(toIndentedString(resultDescription)).append("\n");
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


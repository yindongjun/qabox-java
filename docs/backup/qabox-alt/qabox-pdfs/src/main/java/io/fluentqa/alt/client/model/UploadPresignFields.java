/*
 * API v1
 * DocSpring is a service that helps you fill out and sign PDF templates.
 *
 * The version of the OpenAPI document: v1
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package io.fluentqa.alt.client.model;

import java.util.Objects;

import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.fluentqa.alt.client.JSON;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import io.fluentqa.alt.client.JSON;

/**
 * UploadPresignFields
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2022-03-07T00:51:44.596816+08:00[Asia/Hong_Kong]")
public class UploadPresignFields {
  public static final String SERIALIZED_NAME_KEY = "key";
  @SerializedName(SERIALIZED_NAME_KEY)
  private String key;

  public static final String SERIALIZED_NAME_POLICY = "policy";
  @SerializedName(SERIALIZED_NAME_POLICY)
  private String policy;

  public static final String SERIALIZED_NAME_X_AMZ_ALGORITHM = "x-amz-algorithm";
  @SerializedName(SERIALIZED_NAME_X_AMZ_ALGORITHM)
  private String xAmzAlgorithm;

  public static final String SERIALIZED_NAME_X_AMZ_CREDENTIAL = "x-amz-credential";
  @SerializedName(SERIALIZED_NAME_X_AMZ_CREDENTIAL)
  private String xAmzCredential;

  public static final String SERIALIZED_NAME_X_AMZ_DATE = "x-amz-date";
  @SerializedName(SERIALIZED_NAME_X_AMZ_DATE)
  private String xAmzDate;

  public static final String SERIALIZED_NAME_X_AMZ_SIGNATURE = "x-amz-signature";
  @SerializedName(SERIALIZED_NAME_X_AMZ_SIGNATURE)
  private String xAmzSignature;

  public UploadPresignFields() { 
  }

  public UploadPresignFields key(String key) {
    
    this.key = key;
    return this;
  }

   /**
   * Get key
   * @return key
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")

  public String getKey() {
    return key;
  }


  public void setKey(String key) {
    this.key = key;
  }


  public UploadPresignFields policy(String policy) {
    
    this.policy = policy;
    return this;
  }

   /**
   * Get policy
   * @return policy
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")

  public String getPolicy() {
    return policy;
  }


  public void setPolicy(String policy) {
    this.policy = policy;
  }


  public UploadPresignFields xAmzAlgorithm(String xAmzAlgorithm) {
    
    this.xAmzAlgorithm = xAmzAlgorithm;
    return this;
  }

   /**
   * Get xAmzAlgorithm
   * @return xAmzAlgorithm
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")

  public String getxAmzAlgorithm() {
    return xAmzAlgorithm;
  }


  public void setxAmzAlgorithm(String xAmzAlgorithm) {
    this.xAmzAlgorithm = xAmzAlgorithm;
  }


  public UploadPresignFields xAmzCredential(String xAmzCredential) {
    
    this.xAmzCredential = xAmzCredential;
    return this;
  }

   /**
   * Get xAmzCredential
   * @return xAmzCredential
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")

  public String getxAmzCredential() {
    return xAmzCredential;
  }


  public void setxAmzCredential(String xAmzCredential) {
    this.xAmzCredential = xAmzCredential;
  }


  public UploadPresignFields xAmzDate(String xAmzDate) {
    
    this.xAmzDate = xAmzDate;
    return this;
  }

   /**
   * Get xAmzDate
   * @return xAmzDate
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")

  public String getxAmzDate() {
    return xAmzDate;
  }


  public void setxAmzDate(String xAmzDate) {
    this.xAmzDate = xAmzDate;
  }


  public UploadPresignFields xAmzSignature(String xAmzSignature) {
    
    this.xAmzSignature = xAmzSignature;
    return this;
  }

   /**
   * Get xAmzSignature
   * @return xAmzSignature
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")

  public String getxAmzSignature() {
    return xAmzSignature;
  }


  public void setxAmzSignature(String xAmzSignature) {
    this.xAmzSignature = xAmzSignature;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UploadPresignFields uploadPresignFields = (UploadPresignFields) o;
    return Objects.equals(this.key, uploadPresignFields.key) &&
        Objects.equals(this.policy, uploadPresignFields.policy) &&
        Objects.equals(this.xAmzAlgorithm, uploadPresignFields.xAmzAlgorithm) &&
        Objects.equals(this.xAmzCredential, uploadPresignFields.xAmzCredential) &&
        Objects.equals(this.xAmzDate, uploadPresignFields.xAmzDate) &&
        Objects.equals(this.xAmzSignature, uploadPresignFields.xAmzSignature);
  }

  @Override
  public int hashCode() {
    return Objects.hash(key, policy, xAmzAlgorithm, xAmzCredential, xAmzDate, xAmzSignature);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UploadPresignFields {\n");
    sb.append("    key: ").append(toIndentedString(key)).append("\n");
    sb.append("    policy: ").append(toIndentedString(policy)).append("\n");
    sb.append("    xAmzAlgorithm: ").append(toIndentedString(xAmzAlgorithm)).append("\n");
    sb.append("    xAmzCredential: ").append(toIndentedString(xAmzCredential)).append("\n");
    sb.append("    xAmzDate: ").append(toIndentedString(xAmzDate)).append("\n");
    sb.append("    xAmzSignature: ").append(toIndentedString(xAmzSignature)).append("\n");
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


  public static HashSet<String> openapiFields;
  public static HashSet<String> openapiRequiredFields;

  static {
    // a set of all properties/fields (JSON key names)
    openapiFields = new HashSet<String>();
    openapiFields.add("key");
    openapiFields.add("policy");
    openapiFields.add("x-amz-algorithm");
    openapiFields.add("x-amz-credential");
    openapiFields.add("x-amz-date");
    openapiFields.add("x-amz-signature");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("key");
    openapiRequiredFields.add("policy");
    openapiRequiredFields.add("x-amz-algorithm");
    openapiRequiredFields.add("x-amz-credential");
    openapiRequiredFields.add("x-amz-date");
    openapiRequiredFields.add("x-amz-signature");
  }

 /**
  * Validates the JSON Object and throws an exception if issues found
  *
  * @param jsonObj JSON Object
  * @throws IOException if the JSON Object is invalid with respect to UploadPresignFields
  */
  public static void validateJsonObject(JsonObject jsonObj) throws IOException {
      if (jsonObj == null) {
        if (UploadPresignFields.openapiRequiredFields.isEmpty()) {
          return;
        } else { // has required fields
          throw new IllegalArgumentException(String.format("The required field(s) %s in UploadPresignFields is not found in the empty JSON string", UploadPresignFields.openapiRequiredFields.toString()));
        }
      }
      Set<Entry<String, JsonElement>> entries = jsonObj.entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!UploadPresignFields.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `UploadPresignFields` properties. JSON: %s", entry.getKey(), jsonObj.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : UploadPresignFields.openapiRequiredFields) {
        if (jsonObj.get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonObj.toString()));
        }
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!UploadPresignFields.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'UploadPresignFields' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<UploadPresignFields> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(UploadPresignFields.class));

       return (TypeAdapter<T>) new TypeAdapter<UploadPresignFields>() {
           @Override
           public void write(JsonWriter out, UploadPresignFields value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public UploadPresignFields read(JsonReader in) throws IOException {
             JsonObject jsonObj = elementAdapter.read(in).getAsJsonObject();
             validateJsonObject(jsonObj);
             return thisAdapter.fromJsonTree(jsonObj);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of UploadPresignFields given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of UploadPresignFields
  * @throws IOException if the JSON string is invalid with respect to UploadPresignFields
  */
  public static UploadPresignFields fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, UploadPresignFields.class);
  }

 /**
  * Convert an instance of UploadPresignFields to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

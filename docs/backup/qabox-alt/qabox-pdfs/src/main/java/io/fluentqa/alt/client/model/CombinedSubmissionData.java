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
import java.util.ArrayList;
import java.util.List;

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
 * CombinedSubmissionData
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2022-03-07T00:51:44.596816+08:00[Asia/Hong_Kong]")
public class CombinedSubmissionData {
  public static final String SERIALIZED_NAME_EXPIRES_IN = "expires_in";
  @SerializedName(SERIALIZED_NAME_EXPIRES_IN)
  private Integer expiresIn;

  public static final String SERIALIZED_NAME_METADATA = "metadata";
  @SerializedName(SERIALIZED_NAME_METADATA)
  private Object metadata;

  public static final String SERIALIZED_NAME_PASSWORD = "password";
  @SerializedName(SERIALIZED_NAME_PASSWORD)
  private String password;

  public static final String SERIALIZED_NAME_SUBMISSION_IDS = "submission_ids";
  @SerializedName(SERIALIZED_NAME_SUBMISSION_IDS)
  private List<String> submissionIds = new ArrayList<>();

  public static final String SERIALIZED_NAME_TEST = "test";
  @SerializedName(SERIALIZED_NAME_TEST)
  private Boolean test;

  public CombinedSubmissionData() { 
  }

  public CombinedSubmissionData expiresIn(Integer expiresIn) {
    
    this.expiresIn = expiresIn;
    return this;
  }

   /**
   * Get expiresIn
   * @return expiresIn
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getExpiresIn() {
    return expiresIn;
  }


  public void setExpiresIn(Integer expiresIn) {
    this.expiresIn = expiresIn;
  }


  public CombinedSubmissionData metadata(Object metadata) {
    
    this.metadata = metadata;
    return this;
  }

   /**
   * Get metadata
   * @return metadata
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Object getMetadata() {
    return metadata;
  }


  public void setMetadata(Object metadata) {
    this.metadata = metadata;
  }


  public CombinedSubmissionData password(String password) {
    
    this.password = password;
    return this;
  }

   /**
   * Get password
   * @return password
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getPassword() {
    return password;
  }


  public void setPassword(String password) {
    this.password = password;
  }


  public CombinedSubmissionData submissionIds(List<String> submissionIds) {
    
    this.submissionIds = submissionIds;
    return this;
  }

  public CombinedSubmissionData addSubmissionIdsItem(String submissionIdsItem) {
    this.submissionIds.add(submissionIdsItem);
    return this;
  }

   /**
   * Get submissionIds
   * @return submissionIds
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")

  public List<String> getSubmissionIds() {
    return submissionIds;
  }


  public void setSubmissionIds(List<String> submissionIds) {
    this.submissionIds = submissionIds;
  }


  public CombinedSubmissionData test(Boolean test) {
    
    this.test = test;
    return this;
  }

   /**
   * Get test
   * @return test
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getTest() {
    return test;
  }


  public void setTest(Boolean test) {
    this.test = test;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CombinedSubmissionData combinedSubmissionData = (CombinedSubmissionData) o;
    return Objects.equals(this.expiresIn, combinedSubmissionData.expiresIn) &&
        Objects.equals(this.metadata, combinedSubmissionData.metadata) &&
        Objects.equals(this.password, combinedSubmissionData.password) &&
        Objects.equals(this.submissionIds, combinedSubmissionData.submissionIds) &&
        Objects.equals(this.test, combinedSubmissionData.test);
  }

  @Override
  public int hashCode() {
    return Objects.hash(expiresIn, metadata, password, submissionIds, test);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CombinedSubmissionData {\n");
    sb.append("    expiresIn: ").append(toIndentedString(expiresIn)).append("\n");
    sb.append("    metadata: ").append(toIndentedString(metadata)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
    sb.append("    submissionIds: ").append(toIndentedString(submissionIds)).append("\n");
    sb.append("    test: ").append(toIndentedString(test)).append("\n");
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
    openapiFields.add("expires_in");
    openapiFields.add("metadata");
    openapiFields.add("password");
    openapiFields.add("submission_ids");
    openapiFields.add("test");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("submission_ids");
  }

 /**
  * Validates the JSON Object and throws an exception if issues found
  *
  * @param jsonObj JSON Object
  * @throws IOException if the JSON Object is invalid with respect to CombinedSubmissionData
  */
  public static void validateJsonObject(JsonObject jsonObj) throws IOException {
      if (jsonObj == null) {
        if (CombinedSubmissionData.openapiRequiredFields.isEmpty()) {
          return;
        } else { // has required fields
          throw new IllegalArgumentException(String.format("The required field(s) %s in CombinedSubmissionData is not found in the empty JSON string", CombinedSubmissionData.openapiRequiredFields.toString()));
        }
      }
      Set<Entry<String, JsonElement>> entries = jsonObj.entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!CombinedSubmissionData.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `CombinedSubmissionData` properties. JSON: %s", entry.getKey(), jsonObj.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : CombinedSubmissionData.openapiRequiredFields) {
        if (jsonObj.get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonObj.toString()));
        }
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!CombinedSubmissionData.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'CombinedSubmissionData' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<CombinedSubmissionData> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(CombinedSubmissionData.class));

       return (TypeAdapter<T>) new TypeAdapter<CombinedSubmissionData>() {
           @Override
           public void write(JsonWriter out, CombinedSubmissionData value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public CombinedSubmissionData read(JsonReader in) throws IOException {
             JsonObject jsonObj = elementAdapter.read(in).getAsJsonObject();
             validateJsonObject(jsonObj);
             return thisAdapter.fromJsonTree(jsonObj);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of CombinedSubmissionData given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of CombinedSubmissionData
  * @throws IOException if the JSON string is invalid with respect to CombinedSubmissionData
  */
  public static CombinedSubmissionData fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, CombinedSubmissionData.class);
  }

 /**
  * Convert an instance of CombinedSubmissionData to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

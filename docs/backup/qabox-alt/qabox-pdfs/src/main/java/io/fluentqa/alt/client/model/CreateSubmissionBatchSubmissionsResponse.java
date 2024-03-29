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

import io.fluentqa.alt.client.model.Submission;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
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
 * CreateSubmissionBatchSubmissionsResponse
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2022-03-07T00:51:44.596816+08:00[Asia/Hong_Kong]")
public class CreateSubmissionBatchSubmissionsResponse {
  /**
   * Gets or Sets status
   */
  @JsonAdapter(StatusEnum.Adapter.class)
  public enum StatusEnum {
    SUCCESS("success"),
    
    ERROR("error"),
    
    VALID_BUT_NOT_SAVED("valid_but_not_saved");

    private String value;

    StatusEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static StatusEnum fromValue(String value) {
      for (StatusEnum b : StatusEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<StatusEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final StatusEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public StatusEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return StatusEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_STATUS = "status";
  @SerializedName(SERIALIZED_NAME_STATUS)
  private StatusEnum status;

  public static final String SERIALIZED_NAME_ERRORS = "errors";
  @SerializedName(SERIALIZED_NAME_ERRORS)
  private List<String> errors = null;

  public static final String SERIALIZED_NAME_SUBMISSION = "submission";
  @SerializedName(SERIALIZED_NAME_SUBMISSION)
  private Submission submission;

  public CreateSubmissionBatchSubmissionsResponse() { 
  }

  public CreateSubmissionBatchSubmissionsResponse status(StatusEnum status) {
    
    this.status = status;
    return this;
  }

   /**
   * Get status
   * @return status
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")

  public StatusEnum getStatus() {
    return status;
  }


  public void setStatus(StatusEnum status) {
    this.status = status;
  }


  public CreateSubmissionBatchSubmissionsResponse errors(List<String> errors) {
    
    this.errors = errors;
    return this;
  }

  public CreateSubmissionBatchSubmissionsResponse addErrorsItem(String errorsItem) {
    if (this.errors == null) {
      this.errors = new ArrayList<>();
    }
    this.errors.add(errorsItem);
    return this;
  }

   /**
   * Get errors
   * @return errors
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<String> getErrors() {
    return errors;
  }


  public void setErrors(List<String> errors) {
    this.errors = errors;
  }


  public CreateSubmissionBatchSubmissionsResponse submission(Submission submission) {
    
    this.submission = submission;
    return this;
  }

   /**
   * Get submission
   * @return submission
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Submission getSubmission() {
    return submission;
  }


  public void setSubmission(Submission submission) {
    this.submission = submission;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateSubmissionBatchSubmissionsResponse createSubmissionBatchSubmissionsResponse = (CreateSubmissionBatchSubmissionsResponse) o;
    return Objects.equals(this.status, createSubmissionBatchSubmissionsResponse.status) &&
        Objects.equals(this.errors, createSubmissionBatchSubmissionsResponse.errors) &&
        Objects.equals(this.submission, createSubmissionBatchSubmissionsResponse.submission);
  }

  @Override
  public int hashCode() {
    return Objects.hash(status, errors, submission);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateSubmissionBatchSubmissionsResponse {\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    errors: ").append(toIndentedString(errors)).append("\n");
    sb.append("    submission: ").append(toIndentedString(submission)).append("\n");
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
    openapiFields.add("status");
    openapiFields.add("errors");
    openapiFields.add("submission");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("status");
  }

 /**
  * Validates the JSON Object and throws an exception if issues found
  *
  * @param jsonObj JSON Object
  * @throws IOException if the JSON Object is invalid with respect to CreateSubmissionBatchSubmissionsResponse
  */
  public static void validateJsonObject(JsonObject jsonObj) throws IOException {
      if (jsonObj == null) {
        if (CreateSubmissionBatchSubmissionsResponse.openapiRequiredFields.isEmpty()) {
          return;
        } else { // has required fields
          throw new IllegalArgumentException(String.format("The required field(s) %s in CreateSubmissionBatchSubmissionsResponse is not found in the empty JSON string", CreateSubmissionBatchSubmissionsResponse.openapiRequiredFields.toString()));
        }
      }
      Set<Entry<String, JsonElement>> entries = jsonObj.entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!CreateSubmissionBatchSubmissionsResponse.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `CreateSubmissionBatchSubmissionsResponse` properties. JSON: %s", entry.getKey(), jsonObj.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : CreateSubmissionBatchSubmissionsResponse.openapiRequiredFields) {
        if (jsonObj.get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonObj.toString()));
        }
      }
      // validate the optional field `submission`
      if (jsonObj.getAsJsonObject("submission") != null) {
        Submission.validateJsonObject(jsonObj.getAsJsonObject("submission"));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!CreateSubmissionBatchSubmissionsResponse.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'CreateSubmissionBatchSubmissionsResponse' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<CreateSubmissionBatchSubmissionsResponse> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(CreateSubmissionBatchSubmissionsResponse.class));

       return (TypeAdapter<T>) new TypeAdapter<CreateSubmissionBatchSubmissionsResponse>() {
           @Override
           public void write(JsonWriter out, CreateSubmissionBatchSubmissionsResponse value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public CreateSubmissionBatchSubmissionsResponse read(JsonReader in) throws IOException {
             JsonObject jsonObj = elementAdapter.read(in).getAsJsonObject();
             validateJsonObject(jsonObj);
             return thisAdapter.fromJsonTree(jsonObj);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of CreateSubmissionBatchSubmissionsResponse given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of CreateSubmissionBatchSubmissionsResponse
  * @throws IOException if the JSON string is invalid with respect to CreateSubmissionBatchSubmissionsResponse
  */
  public static CreateSubmissionBatchSubmissionsResponse fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, CreateSubmissionBatchSubmissionsResponse.class);
  }

 /**
  * Convert an instance of CreateSubmissionBatchSubmissionsResponse to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}


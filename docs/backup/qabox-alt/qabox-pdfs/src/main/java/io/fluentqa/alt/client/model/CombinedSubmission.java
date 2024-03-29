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
import java.util.Arrays;
import io.fluentqa.alt.client.model.CombinedSubmissionAction;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.fluentqa.alt.client.JSON;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import io.fluentqa.alt.client.JSON;

/**
 * CombinedSubmission
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2022-03-07T00:51:44.596816+08:00[Asia/Hong_Kong]")
public class CombinedSubmission {
  public static final String SERIALIZED_NAME_ID = "id";
  @SerializedName(SERIALIZED_NAME_ID)
  private String id;

  public static final String SERIALIZED_NAME_EXPIRED = "expired";
  @SerializedName(SERIALIZED_NAME_EXPIRED)
  private Boolean expired;

  public static final String SERIALIZED_NAME_EXPIRES_AT = "expires_at";
  @SerializedName(SERIALIZED_NAME_EXPIRES_AT)
  private String expiresAt;

  /**
   * Gets or Sets state
   */
  @JsonAdapter(StateEnum.Adapter.class)
  public enum StateEnum {
    PENDING("pending"),
    
    PROCESSED("processed"),
    
    ERROR("error");

    private String value;

    StateEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static StateEnum fromValue(String value) {
      for (StateEnum b : StateEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<StateEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final StateEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public StateEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return StateEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_STATE = "state";
  @SerializedName(SERIALIZED_NAME_STATE)
  private StateEnum state;

  public static final String SERIALIZED_NAME_METADATA = "metadata";
  @SerializedName(SERIALIZED_NAME_METADATA)
  private Object metadata;

  public static final String SERIALIZED_NAME_PASSWORD = "password";
  @SerializedName(SERIALIZED_NAME_PASSWORD)
  private String password;

  public static final String SERIALIZED_NAME_SUBMISSION_IDS = "submission_ids";
  @SerializedName(SERIALIZED_NAME_SUBMISSION_IDS)
  private List<String> submissionIds = new ArrayList<>();

  public static final String SERIALIZED_NAME_SOURCE_PDFS = "source_pdfs";
  @SerializedName(SERIALIZED_NAME_SOURCE_PDFS)
  private List<Object> sourcePdfs = new ArrayList<>();

  public static final String SERIALIZED_NAME_DOWNLOAD_URL = "download_url";
  @SerializedName(SERIALIZED_NAME_DOWNLOAD_URL)
  private String downloadUrl;

  public static final String SERIALIZED_NAME_PDF_HASH = "pdf_hash";
  @SerializedName(SERIALIZED_NAME_PDF_HASH)
  private String pdfHash;

  public static final String SERIALIZED_NAME_ACTIONS = "actions";
  @SerializedName(SERIALIZED_NAME_ACTIONS)
  private List<CombinedSubmissionAction> actions = null;

  public CombinedSubmission() { 
  }

  public CombinedSubmission id(String id) {
    
    this.id = id;
    return this;
  }

   /**
   * Get id
   * @return id
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")

  public String getId() {
    return id;
  }


  public void setId(String id) {
    this.id = id;
  }


  public CombinedSubmission expired(Boolean expired) {
    
    this.expired = expired;
    return this;
  }

   /**
   * Get expired
   * @return expired
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")

  public Boolean getExpired() {
    return expired;
  }


  public void setExpired(Boolean expired) {
    this.expired = expired;
  }


  public CombinedSubmission expiresAt(String expiresAt) {
    
    this.expiresAt = expiresAt;
    return this;
  }

   /**
   * Get expiresAt
   * @return expiresAt
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getExpiresAt() {
    return expiresAt;
  }


  public void setExpiresAt(String expiresAt) {
    this.expiresAt = expiresAt;
  }


  public CombinedSubmission state(StateEnum state) {
    
    this.state = state;
    return this;
  }

   /**
   * Get state
   * @return state
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")

  public StateEnum getState() {
    return state;
  }


  public void setState(StateEnum state) {
    this.state = state;
  }


  public CombinedSubmission metadata(Object metadata) {
    
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


  public CombinedSubmission password(String password) {
    
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


  public CombinedSubmission submissionIds(List<String> submissionIds) {
    
    this.submissionIds = submissionIds;
    return this;
  }

  public CombinedSubmission addSubmissionIdsItem(String submissionIdsItem) {
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


  public CombinedSubmission sourcePdfs(List<Object> sourcePdfs) {
    
    this.sourcePdfs = sourcePdfs;
    return this;
  }

  public CombinedSubmission addSourcePdfsItem(Object sourcePdfsItem) {
    this.sourcePdfs.add(sourcePdfsItem);
    return this;
  }

   /**
   * Get sourcePdfs
   * @return sourcePdfs
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")

  public List<Object> getSourcePdfs() {
    return sourcePdfs;
  }


  public void setSourcePdfs(List<Object> sourcePdfs) {
    this.sourcePdfs = sourcePdfs;
  }


  public CombinedSubmission downloadUrl(String downloadUrl) {
    
    this.downloadUrl = downloadUrl;
    return this;
  }

   /**
   * Get downloadUrl
   * @return downloadUrl
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getDownloadUrl() {
    return downloadUrl;
  }


  public void setDownloadUrl(String downloadUrl) {
    this.downloadUrl = downloadUrl;
  }


  public CombinedSubmission pdfHash(String pdfHash) {
    
    this.pdfHash = pdfHash;
    return this;
  }

   /**
   * Get pdfHash
   * @return pdfHash
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getPdfHash() {
    return pdfHash;
  }


  public void setPdfHash(String pdfHash) {
    this.pdfHash = pdfHash;
  }


  public CombinedSubmission actions(List<CombinedSubmissionAction> actions) {
    
    this.actions = actions;
    return this;
  }

  public CombinedSubmission addActionsItem(CombinedSubmissionAction actionsItem) {
    if (this.actions == null) {
      this.actions = new ArrayList<>();
    }
    this.actions.add(actionsItem);
    return this;
  }

   /**
   * Get actions
   * @return actions
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<CombinedSubmissionAction> getActions() {
    return actions;
  }


  public void setActions(List<CombinedSubmissionAction> actions) {
    this.actions = actions;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CombinedSubmission combinedSubmission = (CombinedSubmission) o;
    return Objects.equals(this.id, combinedSubmission.id) &&
        Objects.equals(this.expired, combinedSubmission.expired) &&
        Objects.equals(this.expiresAt, combinedSubmission.expiresAt) &&
        Objects.equals(this.state, combinedSubmission.state) &&
        Objects.equals(this.metadata, combinedSubmission.metadata) &&
        Objects.equals(this.password, combinedSubmission.password) &&
        Objects.equals(this.submissionIds, combinedSubmission.submissionIds) &&
        Objects.equals(this.sourcePdfs, combinedSubmission.sourcePdfs) &&
        Objects.equals(this.downloadUrl, combinedSubmission.downloadUrl) &&
        Objects.equals(this.pdfHash, combinedSubmission.pdfHash) &&
        Objects.equals(this.actions, combinedSubmission.actions);
  }

  private static <T> boolean equalsNullable(JsonNullable<T> a, JsonNullable<T> b) {
    return a == b || (a != null && b != null && a.isPresent() && b.isPresent() && Objects.deepEquals(a.get(), b.get()));
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, expired, expiresAt, state, metadata, password, submissionIds, sourcePdfs, downloadUrl, pdfHash, actions);
  }

  private static <T> int hashCodeNullable(JsonNullable<T> a) {
    if (a == null) {
      return 1;
    }
    return a.isPresent() ? Arrays.deepHashCode(new Object[]{a.get()}) : 31;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CombinedSubmission {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    expired: ").append(toIndentedString(expired)).append("\n");
    sb.append("    expiresAt: ").append(toIndentedString(expiresAt)).append("\n");
    sb.append("    state: ").append(toIndentedString(state)).append("\n");
    sb.append("    metadata: ").append(toIndentedString(metadata)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
    sb.append("    submissionIds: ").append(toIndentedString(submissionIds)).append("\n");
    sb.append("    sourcePdfs: ").append(toIndentedString(sourcePdfs)).append("\n");
    sb.append("    downloadUrl: ").append(toIndentedString(downloadUrl)).append("\n");
    sb.append("    pdfHash: ").append(toIndentedString(pdfHash)).append("\n");
    sb.append("    actions: ").append(toIndentedString(actions)).append("\n");
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
    openapiFields.add("id");
    openapiFields.add("expired");
    openapiFields.add("expires_at");
    openapiFields.add("state");
    openapiFields.add("metadata");
    openapiFields.add("password");
    openapiFields.add("submission_ids");
    openapiFields.add("source_pdfs");
    openapiFields.add("download_url");
    openapiFields.add("pdf_hash");
    openapiFields.add("actions");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("id");
    openapiRequiredFields.add("expired");
    openapiRequiredFields.add("state");
    openapiRequiredFields.add("submission_ids");
    openapiRequiredFields.add("source_pdfs");
  }

 /**
  * Validates the JSON Object and throws an exception if issues found
  *
  * @param jsonObj JSON Object
  * @throws IOException if the JSON Object is invalid with respect to CombinedSubmission
  */
  public static void validateJsonObject(JsonObject jsonObj) throws IOException {
      if (jsonObj == null) {
        if (CombinedSubmission.openapiRequiredFields.isEmpty()) {
          return;
        } else { // has required fields
          throw new IllegalArgumentException(String.format("The required field(s) %s in CombinedSubmission is not found in the empty JSON string", CombinedSubmission.openapiRequiredFields.toString()));
        }
      }
      Set<Entry<String, JsonElement>> entries = jsonObj.entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!CombinedSubmission.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `CombinedSubmission` properties. JSON: %s", entry.getKey(), jsonObj.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : CombinedSubmission.openapiRequiredFields) {
        if (jsonObj.get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonObj.toString()));
        }
      }
      JsonArray jsonArrayactions = jsonObj.getAsJsonArray("actions");
      // validate the optional field `actions` (array)
      if (jsonArrayactions != null) {
        for (int i = 0; i < jsonArrayactions.size(); i++) {
          CombinedSubmissionAction.validateJsonObject(jsonArrayactions.get(i).getAsJsonObject());
        };
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!CombinedSubmission.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'CombinedSubmission' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<CombinedSubmission> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(CombinedSubmission.class));

       return (TypeAdapter<T>) new TypeAdapter<CombinedSubmission>() {
           @Override
           public void write(JsonWriter out, CombinedSubmission value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public CombinedSubmission read(JsonReader in) throws IOException {
             JsonObject jsonObj = elementAdapter.read(in).getAsJsonObject();
             validateJsonObject(jsonObj);
             return thisAdapter.fromJsonTree(jsonObj);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of CombinedSubmission given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of CombinedSubmission
  * @throws IOException if the JSON string is invalid with respect to CombinedSubmission
  */
  public static CombinedSubmission fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, CombinedSubmission.class);
  }

 /**
  * Convert an instance of CombinedSubmission to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}


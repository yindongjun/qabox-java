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
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.fluentqa.alt.client.JSON;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.math.BigDecimal;
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
 * Template
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2022-03-07T00:51:44.596816+08:00[Asia/Hong_Kong]")
public class Template {
  public static final String SERIALIZED_NAME_NAME = "name";
  @SerializedName(SERIALIZED_NAME_NAME)
  private String name;

  public static final String SERIALIZED_NAME_DESCRIPTION = "description";
  @SerializedName(SERIALIZED_NAME_DESCRIPTION)
  private String description;

  public static final String SERIALIZED_NAME_PUBLIC_WEB_FORM = "public_web_form";
  @SerializedName(SERIALIZED_NAME_PUBLIC_WEB_FORM)
  private Boolean publicWebForm;

  public static final String SERIALIZED_NAME_PUBLIC_SUBMISSIONS = "public_submissions";
  @SerializedName(SERIALIZED_NAME_PUBLIC_SUBMISSIONS)
  private Boolean publicSubmissions;

  public static final String SERIALIZED_NAME_EXPIRE_SUBMISSIONS = "expire_submissions";
  @SerializedName(SERIALIZED_NAME_EXPIRE_SUBMISSIONS)
  private Boolean expireSubmissions;

  public static final String SERIALIZED_NAME_EXPIRE_AFTER = "expire_after";
  @SerializedName(SERIALIZED_NAME_EXPIRE_AFTER)
  private BigDecimal expireAfter;

  /**
   * Gets or Sets expirationInterval
   */
  @JsonAdapter(ExpirationIntervalEnum.Adapter.class)
  public enum ExpirationIntervalEnum {
    MINUTES("minutes"),
    
    HOURS("hours"),
    
    DAYS("days");

    private String value;

    ExpirationIntervalEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static ExpirationIntervalEnum fromValue(String value) {
      for (ExpirationIntervalEnum b : ExpirationIntervalEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<ExpirationIntervalEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final ExpirationIntervalEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public ExpirationIntervalEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return ExpirationIntervalEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_EXPIRATION_INTERVAL = "expiration_interval";
  @SerializedName(SERIALIZED_NAME_EXPIRATION_INTERVAL)
  private ExpirationIntervalEnum expirationInterval;

  public static final String SERIALIZED_NAME_ALLOW_ADDITIONAL_PROPERTIES = "allow_additional_properties";
  @SerializedName(SERIALIZED_NAME_ALLOW_ADDITIONAL_PROPERTIES)
  private Boolean allowAdditionalProperties;

  public static final String SERIALIZED_NAME_EDITABLE_SUBMISSIONS = "editable_submissions";
  @SerializedName(SERIALIZED_NAME_EDITABLE_SUBMISSIONS)
  private Boolean editableSubmissions;

  public static final String SERIALIZED_NAME_LOCKED = "locked";
  @SerializedName(SERIALIZED_NAME_LOCKED)
  private Boolean locked;

  public static final String SERIALIZED_NAME_WEBHOOK_URL = "webhook_url";
  @SerializedName(SERIALIZED_NAME_WEBHOOK_URL)
  private String webhookUrl;

  public static final String SERIALIZED_NAME_SLACK_WEBHOOK_URL = "slack_webhook_url";
  @SerializedName(SERIALIZED_NAME_SLACK_WEBHOOK_URL)
  private String slackWebhookUrl;

  public static final String SERIALIZED_NAME_REDIRECT_URL = "redirect_url";
  @SerializedName(SERIALIZED_NAME_REDIRECT_URL)
  private String redirectUrl;

  public static final String SERIALIZED_NAME_ID = "id";
  @SerializedName(SERIALIZED_NAME_ID)
  private String id;

  public static final String SERIALIZED_NAME_TEMPLATE_TYPE = "template_type";
  @SerializedName(SERIALIZED_NAME_TEMPLATE_TYPE)
  private String templateType;

  public static final String SERIALIZED_NAME_PAGE_DIMENSIONS = "page_dimensions";
  @SerializedName(SERIALIZED_NAME_PAGE_DIMENSIONS)
  private List<List<BigDecimal>> pageDimensions = new ArrayList<>();

  public static final String SERIALIZED_NAME_DOCUMENT_URL = "document_url";
  @SerializedName(SERIALIZED_NAME_DOCUMENT_URL)
  private String documentUrl;

  public static final String SERIALIZED_NAME_PERMANENT_DOCUMENT_URL = "permanent_document_url";
  @SerializedName(SERIALIZED_NAME_PERMANENT_DOCUMENT_URL)
  private String permanentDocumentUrl;

  public static final String SERIALIZED_NAME_PATH = "path";
  @SerializedName(SERIALIZED_NAME_PATH)
  private String path;

  public static final String SERIALIZED_NAME_PARENT_FOLDER_ID = "parent_folder_id";
  @SerializedName(SERIALIZED_NAME_PARENT_FOLDER_ID)
  private String parentFolderId;

  public Template() { 
  }

  public Template name(String name) {
    
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(required = true, value = "")

  public String getName() {
    return name;
  }


  public void setName(String name) {
    this.name = name;
  }


  public Template description(String description) {
    
    this.description = description;
    return this;
  }

   /**
   * Get description
   * @return description
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(required = true, value = "")

  public String getDescription() {
    return description;
  }


  public void setDescription(String description) {
    this.description = description;
  }


  public Template publicWebForm(Boolean publicWebForm) {
    
    this.publicWebForm = publicWebForm;
    return this;
  }

   /**
   * Get publicWebForm
   * @return publicWebForm
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")

  public Boolean getPublicWebForm() {
    return publicWebForm;
  }


  public void setPublicWebForm(Boolean publicWebForm) {
    this.publicWebForm = publicWebForm;
  }


  public Template publicSubmissions(Boolean publicSubmissions) {
    
    this.publicSubmissions = publicSubmissions;
    return this;
  }

   /**
   * Get publicSubmissions
   * @return publicSubmissions
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")

  public Boolean getPublicSubmissions() {
    return publicSubmissions;
  }


  public void setPublicSubmissions(Boolean publicSubmissions) {
    this.publicSubmissions = publicSubmissions;
  }


  public Template expireSubmissions(Boolean expireSubmissions) {
    
    this.expireSubmissions = expireSubmissions;
    return this;
  }

   /**
   * Get expireSubmissions
   * @return expireSubmissions
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")

  public Boolean getExpireSubmissions() {
    return expireSubmissions;
  }


  public void setExpireSubmissions(Boolean expireSubmissions) {
    this.expireSubmissions = expireSubmissions;
  }


  public Template expireAfter(BigDecimal expireAfter) {
    
    this.expireAfter = expireAfter;
    return this;
  }

   /**
   * Get expireAfter
   * @return expireAfter
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")

  public BigDecimal getExpireAfter() {
    return expireAfter;
  }


  public void setExpireAfter(BigDecimal expireAfter) {
    this.expireAfter = expireAfter;
  }


  public Template expirationInterval(ExpirationIntervalEnum expirationInterval) {
    
    this.expirationInterval = expirationInterval;
    return this;
  }

   /**
   * Get expirationInterval
   * @return expirationInterval
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")

  public ExpirationIntervalEnum getExpirationInterval() {
    return expirationInterval;
  }


  public void setExpirationInterval(ExpirationIntervalEnum expirationInterval) {
    this.expirationInterval = expirationInterval;
  }


  public Template allowAdditionalProperties(Boolean allowAdditionalProperties) {
    
    this.allowAdditionalProperties = allowAdditionalProperties;
    return this;
  }

   /**
   * Get allowAdditionalProperties
   * @return allowAdditionalProperties
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")

  public Boolean getAllowAdditionalProperties() {
    return allowAdditionalProperties;
  }


  public void setAllowAdditionalProperties(Boolean allowAdditionalProperties) {
    this.allowAdditionalProperties = allowAdditionalProperties;
  }


  public Template editableSubmissions(Boolean editableSubmissions) {
    
    this.editableSubmissions = editableSubmissions;
    return this;
  }

   /**
   * Get editableSubmissions
   * @return editableSubmissions
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")

  public Boolean getEditableSubmissions() {
    return editableSubmissions;
  }


  public void setEditableSubmissions(Boolean editableSubmissions) {
    this.editableSubmissions = editableSubmissions;
  }


  public Template locked(Boolean locked) {
    
    this.locked = locked;
    return this;
  }

   /**
   * Get locked
   * @return locked
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")

  public Boolean getLocked() {
    return locked;
  }


  public void setLocked(Boolean locked) {
    this.locked = locked;
  }


  public Template webhookUrl(String webhookUrl) {
    
    this.webhookUrl = webhookUrl;
    return this;
  }

   /**
   * Get webhookUrl
   * @return webhookUrl
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(required = true, value = "")

  public String getWebhookUrl() {
    return webhookUrl;
  }


  public void setWebhookUrl(String webhookUrl) {
    this.webhookUrl = webhookUrl;
  }


  public Template slackWebhookUrl(String slackWebhookUrl) {
    
    this.slackWebhookUrl = slackWebhookUrl;
    return this;
  }

   /**
   * Get slackWebhookUrl
   * @return slackWebhookUrl
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(required = true, value = "")

  public String getSlackWebhookUrl() {
    return slackWebhookUrl;
  }


  public void setSlackWebhookUrl(String slackWebhookUrl) {
    this.slackWebhookUrl = slackWebhookUrl;
  }


  public Template redirectUrl(String redirectUrl) {
    
    this.redirectUrl = redirectUrl;
    return this;
  }

   /**
   * Get redirectUrl
   * @return redirectUrl
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(required = true, value = "")

  public String getRedirectUrl() {
    return redirectUrl;
  }


  public void setRedirectUrl(String redirectUrl) {
    this.redirectUrl = redirectUrl;
  }


  public Template id(String id) {
    
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


  public Template templateType(String templateType) {
    
    this.templateType = templateType;
    return this;
  }

   /**
   * Get templateType
   * @return templateType
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")

  public String getTemplateType() {
    return templateType;
  }


  public void setTemplateType(String templateType) {
    this.templateType = templateType;
  }


  public Template pageDimensions(List<List<BigDecimal>> pageDimensions) {
    
    this.pageDimensions = pageDimensions;
    return this;
  }

  public Template addPageDimensionsItem(List<BigDecimal> pageDimensionsItem) {
    this.pageDimensions.add(pageDimensionsItem);
    return this;
  }

   /**
   * Get pageDimensions
   * @return pageDimensions
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(required = true, value = "")

  public List<List<BigDecimal>> getPageDimensions() {
    return pageDimensions;
  }


  public void setPageDimensions(List<List<BigDecimal>> pageDimensions) {
    this.pageDimensions = pageDimensions;
  }


  public Template documentUrl(String documentUrl) {
    
    this.documentUrl = documentUrl;
    return this;
  }

   /**
   * Get documentUrl
   * @return documentUrl
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(required = true, value = "")

  public String getDocumentUrl() {
    return documentUrl;
  }


  public void setDocumentUrl(String documentUrl) {
    this.documentUrl = documentUrl;
  }


  public Template permanentDocumentUrl(String permanentDocumentUrl) {
    
    this.permanentDocumentUrl = permanentDocumentUrl;
    return this;
  }

   /**
   * Get permanentDocumentUrl
   * @return permanentDocumentUrl
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(required = true, value = "")

  public String getPermanentDocumentUrl() {
    return permanentDocumentUrl;
  }


  public void setPermanentDocumentUrl(String permanentDocumentUrl) {
    this.permanentDocumentUrl = permanentDocumentUrl;
  }


  public Template path(String path) {
    
    this.path = path;
    return this;
  }

   /**
   * Get path
   * @return path
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")

  public String getPath() {
    return path;
  }


  public void setPath(String path) {
    this.path = path;
  }


  public Template parentFolderId(String parentFolderId) {
    
    this.parentFolderId = parentFolderId;
    return this;
  }

   /**
   * Get parentFolderId
   * @return parentFolderId
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(required = true, value = "")

  public String getParentFolderId() {
    return parentFolderId;
  }


  public void setParentFolderId(String parentFolderId) {
    this.parentFolderId = parentFolderId;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Template template = (Template) o;
    return Objects.equals(this.name, template.name) &&
        Objects.equals(this.description, template.description) &&
        Objects.equals(this.publicWebForm, template.publicWebForm) &&
        Objects.equals(this.publicSubmissions, template.publicSubmissions) &&
        Objects.equals(this.expireSubmissions, template.expireSubmissions) &&
        Objects.equals(this.expireAfter, template.expireAfter) &&
        Objects.equals(this.expirationInterval, template.expirationInterval) &&
        Objects.equals(this.allowAdditionalProperties, template.allowAdditionalProperties) &&
        Objects.equals(this.editableSubmissions, template.editableSubmissions) &&
        Objects.equals(this.locked, template.locked) &&
        Objects.equals(this.webhookUrl, template.webhookUrl) &&
        Objects.equals(this.slackWebhookUrl, template.slackWebhookUrl) &&
        Objects.equals(this.redirectUrl, template.redirectUrl) &&
        Objects.equals(this.id, template.id) &&
        Objects.equals(this.templateType, template.templateType) &&
        Objects.equals(this.pageDimensions, template.pageDimensions) &&
        Objects.equals(this.documentUrl, template.documentUrl) &&
        Objects.equals(this.permanentDocumentUrl, template.permanentDocumentUrl) &&
        Objects.equals(this.path, template.path) &&
        Objects.equals(this.parentFolderId, template.parentFolderId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, description, publicWebForm, publicSubmissions, expireSubmissions, expireAfter, expirationInterval, allowAdditionalProperties, editableSubmissions, locked, webhookUrl, slackWebhookUrl, redirectUrl, id, templateType, pageDimensions, documentUrl, permanentDocumentUrl, path, parentFolderId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Template {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    publicWebForm: ").append(toIndentedString(publicWebForm)).append("\n");
    sb.append("    publicSubmissions: ").append(toIndentedString(publicSubmissions)).append("\n");
    sb.append("    expireSubmissions: ").append(toIndentedString(expireSubmissions)).append("\n");
    sb.append("    expireAfter: ").append(toIndentedString(expireAfter)).append("\n");
    sb.append("    expirationInterval: ").append(toIndentedString(expirationInterval)).append("\n");
    sb.append("    allowAdditionalProperties: ").append(toIndentedString(allowAdditionalProperties)).append("\n");
    sb.append("    editableSubmissions: ").append(toIndentedString(editableSubmissions)).append("\n");
    sb.append("    locked: ").append(toIndentedString(locked)).append("\n");
    sb.append("    webhookUrl: ").append(toIndentedString(webhookUrl)).append("\n");
    sb.append("    slackWebhookUrl: ").append(toIndentedString(slackWebhookUrl)).append("\n");
    sb.append("    redirectUrl: ").append(toIndentedString(redirectUrl)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    templateType: ").append(toIndentedString(templateType)).append("\n");
    sb.append("    pageDimensions: ").append(toIndentedString(pageDimensions)).append("\n");
    sb.append("    documentUrl: ").append(toIndentedString(documentUrl)).append("\n");
    sb.append("    permanentDocumentUrl: ").append(toIndentedString(permanentDocumentUrl)).append("\n");
    sb.append("    path: ").append(toIndentedString(path)).append("\n");
    sb.append("    parentFolderId: ").append(toIndentedString(parentFolderId)).append("\n");
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
    openapiFields.add("name");
    openapiFields.add("description");
    openapiFields.add("public_web_form");
    openapiFields.add("public_submissions");
    openapiFields.add("expire_submissions");
    openapiFields.add("expire_after");
    openapiFields.add("expiration_interval");
    openapiFields.add("allow_additional_properties");
    openapiFields.add("editable_submissions");
    openapiFields.add("locked");
    openapiFields.add("webhook_url");
    openapiFields.add("slack_webhook_url");
    openapiFields.add("redirect_url");
    openapiFields.add("id");
    openapiFields.add("template_type");
    openapiFields.add("page_dimensions");
    openapiFields.add("document_url");
    openapiFields.add("permanent_document_url");
    openapiFields.add("path");
    openapiFields.add("parent_folder_id");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("name");
    openapiRequiredFields.add("description");
    openapiRequiredFields.add("public_web_form");
    openapiRequiredFields.add("public_submissions");
    openapiRequiredFields.add("expire_submissions");
    openapiRequiredFields.add("expire_after");
    openapiRequiredFields.add("expiration_interval");
    openapiRequiredFields.add("allow_additional_properties");
    openapiRequiredFields.add("editable_submissions");
    openapiRequiredFields.add("locked");
    openapiRequiredFields.add("webhook_url");
    openapiRequiredFields.add("slack_webhook_url");
    openapiRequiredFields.add("redirect_url");
    openapiRequiredFields.add("id");
    openapiRequiredFields.add("template_type");
    openapiRequiredFields.add("page_dimensions");
    openapiRequiredFields.add("document_url");
    openapiRequiredFields.add("permanent_document_url");
    openapiRequiredFields.add("path");
    openapiRequiredFields.add("parent_folder_id");
  }

 /**
  * Validates the JSON Object and throws an exception if issues found
  *
  * @param jsonObj JSON Object
  * @throws IOException if the JSON Object is invalid with respect to Template
  */
  public static void validateJsonObject(JsonObject jsonObj) throws IOException {
      if (jsonObj == null) {
        if (Template.openapiRequiredFields.isEmpty()) {
          return;
        } else { // has required fields
          throw new IllegalArgumentException(String.format("The required field(s) %s in Template is not found in the empty JSON string", Template.openapiRequiredFields.toString()));
        }
      }
      Set<Entry<String, JsonElement>> entries = jsonObj.entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!Template.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `Template` properties. JSON: %s", entry.getKey(), jsonObj.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : Template.openapiRequiredFields) {
        if (jsonObj.get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonObj.toString()));
        }
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!Template.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'Template' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<Template> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(Template.class));

       return (TypeAdapter<T>) new TypeAdapter<Template>() {
           @Override
           public void write(JsonWriter out, Template value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public Template read(JsonReader in) throws IOException {
             JsonObject jsonObj = elementAdapter.read(in).getAsJsonObject();
             validateJsonObject(jsonObj);
             return thisAdapter.fromJsonTree(jsonObj);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of Template given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of Template
  * @throws IOException if the JSON string is invalid with respect to Template
  */
  public static Template fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, Template.class);
  }

 /**
  * Convert an instance of Template to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

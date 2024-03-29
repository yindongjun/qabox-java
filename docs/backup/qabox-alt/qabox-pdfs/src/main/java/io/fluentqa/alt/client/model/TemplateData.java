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
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.fluentqa.alt.client.JSON;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.math.BigDecimal;
import org.openapitools.jackson.nullable.JsonNullable;

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
 * TemplateData
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2022-03-07T00:51:44.596816+08:00[Asia/Hong_Kong]")
public class TemplateData {
  public static final String SERIALIZED_NAME_ALLOW_ADDITIONAL_PROPERTIES = "allow_additional_properties";
  @SerializedName(SERIALIZED_NAME_ALLOW_ADDITIONAL_PROPERTIES)
  private Boolean allowAdditionalProperties;

  public static final String SERIALIZED_NAME_DESCRIPTION = "description";
  @SerializedName(SERIALIZED_NAME_DESCRIPTION)
  private String description;

  public static final String SERIALIZED_NAME_EDITABLE_SUBMISSIONS = "editable_submissions";
  @SerializedName(SERIALIZED_NAME_EDITABLE_SUBMISSIONS)
  private Boolean editableSubmissions;

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

  public static final String SERIALIZED_NAME_EXPIRE_AFTER = "expire_after";
  @SerializedName(SERIALIZED_NAME_EXPIRE_AFTER)
  private BigDecimal expireAfter;

  public static final String SERIALIZED_NAME_EXPIRE_SUBMISSIONS = "expire_submissions";
  @SerializedName(SERIALIZED_NAME_EXPIRE_SUBMISSIONS)
  private Boolean expireSubmissions;

  public static final String SERIALIZED_NAME_FOOTER_HTML = "footer_html";
  @SerializedName(SERIALIZED_NAME_FOOTER_HTML)
  private String footerHtml;

  public static final String SERIALIZED_NAME_HEADER_HTML = "header_html";
  @SerializedName(SERIALIZED_NAME_HEADER_HTML)
  private String headerHtml;

  public static final String SERIALIZED_NAME_HTML = "html";
  @SerializedName(SERIALIZED_NAME_HTML)
  private String html;

  public static final String SERIALIZED_NAME_NAME = "name";
  @SerializedName(SERIALIZED_NAME_NAME)
  private String name;

  public static final String SERIALIZED_NAME_PUBLIC_SUBMISSIONS = "public_submissions";
  @SerializedName(SERIALIZED_NAME_PUBLIC_SUBMISSIONS)
  private Boolean publicSubmissions;

  public static final String SERIALIZED_NAME_PUBLIC_WEB_FORM = "public_web_form";
  @SerializedName(SERIALIZED_NAME_PUBLIC_WEB_FORM)
  private Boolean publicWebForm;

  public static final String SERIALIZED_NAME_REDIRECT_URL = "redirect_url";
  @SerializedName(SERIALIZED_NAME_REDIRECT_URL)
  private String redirectUrl;

  public static final String SERIALIZED_NAME_SCSS = "scss";
  @SerializedName(SERIALIZED_NAME_SCSS)
  private String scss;

  public static final String SERIALIZED_NAME_SLACK_WEBHOOK_URL = "slack_webhook_url";
  @SerializedName(SERIALIZED_NAME_SLACK_WEBHOOK_URL)
  private String slackWebhookUrl;

  public static final String SERIALIZED_NAME_WEBHOOK_URL = "webhook_url";
  @SerializedName(SERIALIZED_NAME_WEBHOOK_URL)
  private String webhookUrl;

  public TemplateData() { 
  }

  public TemplateData allowAdditionalProperties(Boolean allowAdditionalProperties) {
    
    this.allowAdditionalProperties = allowAdditionalProperties;
    return this;
  }

   /**
   * Get allowAdditionalProperties
   * @return allowAdditionalProperties
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getAllowAdditionalProperties() {
    return allowAdditionalProperties;
  }


  public void setAllowAdditionalProperties(Boolean allowAdditionalProperties) {
    this.allowAdditionalProperties = allowAdditionalProperties;
  }


  public TemplateData description(String description) {
    
    this.description = description;
    return this;
  }

   /**
   * Get description
   * @return description
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getDescription() {
    return description;
  }


  public void setDescription(String description) {
    this.description = description;
  }


  public TemplateData editableSubmissions(Boolean editableSubmissions) {
    
    this.editableSubmissions = editableSubmissions;
    return this;
  }

   /**
   * Get editableSubmissions
   * @return editableSubmissions
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getEditableSubmissions() {
    return editableSubmissions;
  }


  public void setEditableSubmissions(Boolean editableSubmissions) {
    this.editableSubmissions = editableSubmissions;
  }


  public TemplateData expirationInterval(ExpirationIntervalEnum expirationInterval) {
    
    this.expirationInterval = expirationInterval;
    return this;
  }

   /**
   * Get expirationInterval
   * @return expirationInterval
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public ExpirationIntervalEnum getExpirationInterval() {
    return expirationInterval;
  }


  public void setExpirationInterval(ExpirationIntervalEnum expirationInterval) {
    this.expirationInterval = expirationInterval;
  }


  public TemplateData expireAfter(BigDecimal expireAfter) {
    
    this.expireAfter = expireAfter;
    return this;
  }

   /**
   * Get expireAfter
   * @return expireAfter
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public BigDecimal getExpireAfter() {
    return expireAfter;
  }


  public void setExpireAfter(BigDecimal expireAfter) {
    this.expireAfter = expireAfter;
  }


  public TemplateData expireSubmissions(Boolean expireSubmissions) {
    
    this.expireSubmissions = expireSubmissions;
    return this;
  }

   /**
   * Get expireSubmissions
   * @return expireSubmissions
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getExpireSubmissions() {
    return expireSubmissions;
  }


  public void setExpireSubmissions(Boolean expireSubmissions) {
    this.expireSubmissions = expireSubmissions;
  }


  public TemplateData footerHtml(String footerHtml) {
    
    this.footerHtml = footerHtml;
    return this;
  }

   /**
   * Get footerHtml
   * @return footerHtml
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getFooterHtml() {
    return footerHtml;
  }


  public void setFooterHtml(String footerHtml) {
    this.footerHtml = footerHtml;
  }


  public TemplateData headerHtml(String headerHtml) {
    
    this.headerHtml = headerHtml;
    return this;
  }

   /**
   * Get headerHtml
   * @return headerHtml
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getHeaderHtml() {
    return headerHtml;
  }


  public void setHeaderHtml(String headerHtml) {
    this.headerHtml = headerHtml;
  }


  public TemplateData html(String html) {
    
    this.html = html;
    return this;
  }

   /**
   * Get html
   * @return html
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getHtml() {
    return html;
  }


  public void setHtml(String html) {
    this.html = html;
  }


  public TemplateData name(String name) {
    
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getName() {
    return name;
  }


  public void setName(String name) {
    this.name = name;
  }


  public TemplateData publicSubmissions(Boolean publicSubmissions) {
    
    this.publicSubmissions = publicSubmissions;
    return this;
  }

   /**
   * Get publicSubmissions
   * @return publicSubmissions
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getPublicSubmissions() {
    return publicSubmissions;
  }


  public void setPublicSubmissions(Boolean publicSubmissions) {
    this.publicSubmissions = publicSubmissions;
  }


  public TemplateData publicWebForm(Boolean publicWebForm) {
    
    this.publicWebForm = publicWebForm;
    return this;
  }

   /**
   * Get publicWebForm
   * @return publicWebForm
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Boolean getPublicWebForm() {
    return publicWebForm;
  }


  public void setPublicWebForm(Boolean publicWebForm) {
    this.publicWebForm = publicWebForm;
  }


  public TemplateData redirectUrl(String redirectUrl) {
    
    this.redirectUrl = redirectUrl;
    return this;
  }

   /**
   * Get redirectUrl
   * @return redirectUrl
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getRedirectUrl() {
    return redirectUrl;
  }


  public void setRedirectUrl(String redirectUrl) {
    this.redirectUrl = redirectUrl;
  }


  public TemplateData scss(String scss) {
    
    this.scss = scss;
    return this;
  }

   /**
   * Get scss
   * @return scss
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getScss() {
    return scss;
  }


  public void setScss(String scss) {
    this.scss = scss;
  }


  public TemplateData slackWebhookUrl(String slackWebhookUrl) {
    
    this.slackWebhookUrl = slackWebhookUrl;
    return this;
  }

   /**
   * Get slackWebhookUrl
   * @return slackWebhookUrl
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getSlackWebhookUrl() {
    return slackWebhookUrl;
  }


  public void setSlackWebhookUrl(String slackWebhookUrl) {
    this.slackWebhookUrl = slackWebhookUrl;
  }


  public TemplateData webhookUrl(String webhookUrl) {
    
    this.webhookUrl = webhookUrl;
    return this;
  }

   /**
   * Get webhookUrl
   * @return webhookUrl
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getWebhookUrl() {
    return webhookUrl;
  }


  public void setWebhookUrl(String webhookUrl) {
    this.webhookUrl = webhookUrl;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TemplateData templateData = (TemplateData) o;
    return Objects.equals(this.allowAdditionalProperties, templateData.allowAdditionalProperties) &&
        Objects.equals(this.description, templateData.description) &&
        Objects.equals(this.editableSubmissions, templateData.editableSubmissions) &&
        Objects.equals(this.expirationInterval, templateData.expirationInterval) &&
        Objects.equals(this.expireAfter, templateData.expireAfter) &&
        Objects.equals(this.expireSubmissions, templateData.expireSubmissions) &&
        Objects.equals(this.footerHtml, templateData.footerHtml) &&
        Objects.equals(this.headerHtml, templateData.headerHtml) &&
        Objects.equals(this.html, templateData.html) &&
        Objects.equals(this.name, templateData.name) &&
        Objects.equals(this.publicSubmissions, templateData.publicSubmissions) &&
        Objects.equals(this.publicWebForm, templateData.publicWebForm) &&
        Objects.equals(this.redirectUrl, templateData.redirectUrl) &&
        Objects.equals(this.scss, templateData.scss) &&
        Objects.equals(this.slackWebhookUrl, templateData.slackWebhookUrl) &&
        Objects.equals(this.webhookUrl, templateData.webhookUrl);
  }

  private static <T> boolean equalsNullable(JsonNullable<T> a, JsonNullable<T> b) {
    return a == b || (a != null && b != null && a.isPresent() && b.isPresent() && Objects.deepEquals(a.get(), b.get()));
  }

  @Override
  public int hashCode() {
    return Objects.hash(allowAdditionalProperties, description, editableSubmissions, expirationInterval, expireAfter, expireSubmissions, footerHtml, headerHtml, html, name, publicSubmissions, publicWebForm, redirectUrl, scss, slackWebhookUrl, webhookUrl);
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
    sb.append("class TemplateData {\n");
    sb.append("    allowAdditionalProperties: ").append(toIndentedString(allowAdditionalProperties)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    editableSubmissions: ").append(toIndentedString(editableSubmissions)).append("\n");
    sb.append("    expirationInterval: ").append(toIndentedString(expirationInterval)).append("\n");
    sb.append("    expireAfter: ").append(toIndentedString(expireAfter)).append("\n");
    sb.append("    expireSubmissions: ").append(toIndentedString(expireSubmissions)).append("\n");
    sb.append("    footerHtml: ").append(toIndentedString(footerHtml)).append("\n");
    sb.append("    headerHtml: ").append(toIndentedString(headerHtml)).append("\n");
    sb.append("    html: ").append(toIndentedString(html)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    publicSubmissions: ").append(toIndentedString(publicSubmissions)).append("\n");
    sb.append("    publicWebForm: ").append(toIndentedString(publicWebForm)).append("\n");
    sb.append("    redirectUrl: ").append(toIndentedString(redirectUrl)).append("\n");
    sb.append("    scss: ").append(toIndentedString(scss)).append("\n");
    sb.append("    slackWebhookUrl: ").append(toIndentedString(slackWebhookUrl)).append("\n");
    sb.append("    webhookUrl: ").append(toIndentedString(webhookUrl)).append("\n");
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
    openapiFields.add("allow_additional_properties");
    openapiFields.add("description");
    openapiFields.add("editable_submissions");
    openapiFields.add("expiration_interval");
    openapiFields.add("expire_after");
    openapiFields.add("expire_submissions");
    openapiFields.add("footer_html");
    openapiFields.add("header_html");
    openapiFields.add("html");
    openapiFields.add("name");
    openapiFields.add("public_submissions");
    openapiFields.add("public_web_form");
    openapiFields.add("redirect_url");
    openapiFields.add("scss");
    openapiFields.add("slack_webhook_url");
    openapiFields.add("webhook_url");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

 /**
  * Validates the JSON Object and throws an exception if issues found
  *
  * @param jsonObj JSON Object
  * @throws IOException if the JSON Object is invalid with respect to TemplateData
  */
  public static void validateJsonObject(JsonObject jsonObj) throws IOException {
      if (jsonObj == null) {
        if (TemplateData.openapiRequiredFields.isEmpty()) {
          return;
        } else { // has required fields
          throw new IllegalArgumentException(String.format("The required field(s) %s in TemplateData is not found in the empty JSON string", TemplateData.openapiRequiredFields.toString()));
        }
      }
      Set<Entry<String, JsonElement>> entries = jsonObj.entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!TemplateData.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `TemplateData` properties. JSON: %s", entry.getKey(), jsonObj.toString()));
        }
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!TemplateData.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'TemplateData' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<TemplateData> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(TemplateData.class));

       return (TypeAdapter<T>) new TypeAdapter<TemplateData>() {
           @Override
           public void write(JsonWriter out, TemplateData value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public TemplateData read(JsonReader in) throws IOException {
             JsonObject jsonObj = elementAdapter.read(in).getAsJsonObject();
             validateJsonObject(jsonObj);
             return thisAdapter.fromJsonTree(jsonObj);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of TemplateData given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of TemplateData
  * @throws IOException if the JSON string is invalid with respect to TemplateData
  */
  public static TemplateData fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, TemplateData.class);
  }

 /**
  * Convert an instance of TemplateData to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}


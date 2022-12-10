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

import io.fluentqa.alt.client.model.HtmlTemplateData;
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
 * CreateHtmlTemplateData
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2022-03-07T00:51:44.596816+08:00[Asia/Hong_Kong]")
public class CreateHtmlTemplateData {
  public static final String SERIALIZED_NAME_TEMPLATE = "template";
  @SerializedName(SERIALIZED_NAME_TEMPLATE)
  private HtmlTemplateData template;

  public CreateHtmlTemplateData() { 
  }

  public CreateHtmlTemplateData template(HtmlTemplateData template) {
    
    this.template = template;
    return this;
  }

   /**
   * Get template
   * @return template
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")

  public HtmlTemplateData getTemplate() {
    return template;
  }


  public void setTemplate(HtmlTemplateData template) {
    this.template = template;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateHtmlTemplateData createHtmlTemplateData = (CreateHtmlTemplateData) o;
    return Objects.equals(this.template, createHtmlTemplateData.template);
  }

  @Override
  public int hashCode() {
    return Objects.hash(template);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateHtmlTemplateData {\n");
    sb.append("    template: ").append(toIndentedString(template)).append("\n");
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
    openapiFields.add("template");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("template");
  }

 /**
  * Validates the JSON Object and throws an exception if issues found
  *
  * @param jsonObj JSON Object
  * @throws IOException if the JSON Object is invalid with respect to CreateHtmlTemplateData
  */
  public static void validateJsonObject(JsonObject jsonObj) throws IOException {
      if (jsonObj == null) {
        if (CreateHtmlTemplateData.openapiRequiredFields.isEmpty()) {
          return;
        } else { // has required fields
          throw new IllegalArgumentException(String.format("The required field(s) %s in CreateHtmlTemplateData is not found in the empty JSON string", CreateHtmlTemplateData.openapiRequiredFields.toString()));
        }
      }
      Set<Entry<String, JsonElement>> entries = jsonObj.entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!CreateHtmlTemplateData.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `CreateHtmlTemplateData` properties. JSON: %s", entry.getKey(), jsonObj.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : CreateHtmlTemplateData.openapiRequiredFields) {
        if (jsonObj.get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonObj.toString()));
        }
      }
      // validate the optional field `template`
      if (jsonObj.getAsJsonObject("template") != null) {
        HtmlTemplateData.validateJsonObject(jsonObj.getAsJsonObject("template"));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!CreateHtmlTemplateData.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'CreateHtmlTemplateData' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<CreateHtmlTemplateData> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(CreateHtmlTemplateData.class));

       return (TypeAdapter<T>) new TypeAdapter<CreateHtmlTemplateData>() {
           @Override
           public void write(JsonWriter out, CreateHtmlTemplateData value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public CreateHtmlTemplateData read(JsonReader in) throws IOException {
             JsonObject jsonObj = elementAdapter.read(in).getAsJsonObject();
             validateJsonObject(jsonObj);
             return thisAdapter.fromJsonTree(jsonObj);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of CreateHtmlTemplateData given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of CreateHtmlTemplateData
  * @throws IOException if the JSON string is invalid with respect to CreateHtmlTemplateData
  */
  public static CreateHtmlTemplateData fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, CreateHtmlTemplateData.class);
  }

 /**
  * Convert an instance of CreateHtmlTemplateData to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}


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
import java.math.BigDecimal;

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
 * Template1Defaults
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2022-03-07T00:51:44.596816+08:00[Asia/Hong_Kong]")
public class Template1Defaults {
  public static final String SERIALIZED_NAME_COLOR = "color";
  @SerializedName(SERIALIZED_NAME_COLOR)
  private String color;

  public static final String SERIALIZED_NAME_FONT_SIZE = "fontSize";
  @SerializedName(SERIALIZED_NAME_FONT_SIZE)
  private BigDecimal fontSize;

  public static final String SERIALIZED_NAME_TYPEFACE = "typeface";
  @SerializedName(SERIALIZED_NAME_TYPEFACE)
  private String typeface;

  public Template1Defaults() { 
  }

  public Template1Defaults color(String color) {
    
    this.color = color;
    return this;
  }

   /**
   * Get color
   * @return color
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(required = true, value = "")

  public String getColor() {
    return color;
  }


  public void setColor(String color) {
    this.color = color;
  }


  public Template1Defaults fontSize(BigDecimal fontSize) {
    
    this.fontSize = fontSize;
    return this;
  }

   /**
   * Get fontSize
   * @return fontSize
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")

  public BigDecimal getFontSize() {
    return fontSize;
  }


  public void setFontSize(BigDecimal fontSize) {
    this.fontSize = fontSize;
  }


  public Template1Defaults typeface(String typeface) {
    
    this.typeface = typeface;
    return this;
  }

   /**
   * Get typeface
   * @return typeface
  **/
  @javax.annotation.Nonnull
  @ApiModelProperty(required = true, value = "")

  public String getTypeface() {
    return typeface;
  }


  public void setTypeface(String typeface) {
    this.typeface = typeface;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Template1Defaults template1Defaults = (Template1Defaults) o;
    return Objects.equals(this.color, template1Defaults.color) &&
        Objects.equals(this.fontSize, template1Defaults.fontSize) &&
        Objects.equals(this.typeface, template1Defaults.typeface);
  }

  @Override
  public int hashCode() {
    return Objects.hash(color, fontSize, typeface);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Template1Defaults {\n");
    sb.append("    color: ").append(toIndentedString(color)).append("\n");
    sb.append("    fontSize: ").append(toIndentedString(fontSize)).append("\n");
    sb.append("    typeface: ").append(toIndentedString(typeface)).append("\n");
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
    openapiFields.add("color");
    openapiFields.add("fontSize");
    openapiFields.add("typeface");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("color");
    openapiRequiredFields.add("fontSize");
    openapiRequiredFields.add("typeface");
  }

 /**
  * Validates the JSON Object and throws an exception if issues found
  *
  * @param jsonObj JSON Object
  * @throws IOException if the JSON Object is invalid with respect to Template1Defaults
  */
  public static void validateJsonObject(JsonObject jsonObj) throws IOException {
      if (jsonObj == null) {
        if (Template1Defaults.openapiRequiredFields.isEmpty()) {
          return;
        } else { // has required fields
          throw new IllegalArgumentException(String.format("The required field(s) %s in Template1Defaults is not found in the empty JSON string", Template1Defaults.openapiRequiredFields.toString()));
        }
      }
      Set<Entry<String, JsonElement>> entries = jsonObj.entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!Template1Defaults.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `Template1Defaults` properties. JSON: %s", entry.getKey(), jsonObj.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : Template1Defaults.openapiRequiredFields) {
        if (jsonObj.get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonObj.toString()));
        }
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!Template1Defaults.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'Template1Defaults' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<Template1Defaults> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(Template1Defaults.class));

       return (TypeAdapter<T>) new TypeAdapter<Template1Defaults>() {
           @Override
           public void write(JsonWriter out, Template1Defaults value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public Template1Defaults read(JsonReader in) throws IOException {
             JsonObject jsonObj = elementAdapter.read(in).getAsJsonObject();
             validateJsonObject(jsonObj);
             return thisAdapter.fromJsonTree(jsonObj);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of Template1Defaults given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of Template1Defaults
  * @throws IOException if the JSON string is invalid with respect to Template1Defaults
  */
  public static Template1Defaults fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, Template1Defaults.class);
  }

 /**
  * Convert an instance of Template1Defaults to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}


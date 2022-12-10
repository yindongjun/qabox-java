

# HtmlTemplateData


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**allowAdditionalProperties** | **Boolean** |  |  [optional]
**description** | **String** |  |  [optional]
**editableSubmissions** | **Boolean** |  |  [optional]
**expirationInterval** | [**ExpirationIntervalEnum**](#ExpirationIntervalEnum) |  |  [optional]
**expireAfter** | **BigDecimal** |  |  [optional]
**expireSubmissions** | **Boolean** |  |  [optional]
**footerHtml** | **String** |  |  [optional]
**headerHtml** | **String** |  |  [optional]
**html** | **String** |  |  [optional]
**name** | **String** |  | 
**publicSubmissions** | **Boolean** |  |  [optional]
**publicWebForm** | **Boolean** |  |  [optional]
**redirectUrl** | **String** |  |  [optional]
**scss** | **String** |  |  [optional]
**slackWebhookUrl** | **String** |  |  [optional]
**templateType** | [**TemplateTypeEnum**](#TemplateTypeEnum) |  |  [optional]
**webhookUrl** | **String** |  |  [optional]



## Enum: ExpirationIntervalEnum

Name | Value
---- | -----
MINUTES | &quot;minutes&quot;
HOURS | &quot;hours&quot;
DAYS | &quot;days&quot;



## Enum: TemplateTypeEnum

Name | Value
---- | -----
PDF | &quot;pdf&quot;
HTML | &quot;html&quot;




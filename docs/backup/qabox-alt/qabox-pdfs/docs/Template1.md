

# Template1


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**name** | **String** |  | 
**description** | **String** |  | 
**publicWebForm** | **Boolean** |  | 
**publicSubmissions** | **Boolean** |  | 
**expireSubmissions** | **Boolean** |  | 
**expireAfter** | **BigDecimal** |  | 
**expirationInterval** | [**ExpirationIntervalEnum**](#ExpirationIntervalEnum) |  | 
**allowAdditionalProperties** | **Boolean** |  | 
**editableSubmissions** | **Boolean** |  | 
**locked** | **Boolean** |  | 
**webhookUrl** | **String** |  | 
**slackWebhookUrl** | **String** |  | 
**redirectUrl** | **String** |  | 
**firstTemplate** | **Boolean** |  | 
**html** | **String** |  | 
**headerHtml** | **String** |  | 
**footerHtml** | **String** |  | 
**scss** | **String** |  | 
**encryptPdfs** | **Boolean** |  | 
**encryptPdfsPassword** | **String** |  | 
**defaults** | [**Template1Defaults**](Template1Defaults.md) |  | 
**fields** | **Object** |  | 
**sharedFieldData** | **Object** |  | 
**fieldOrder** | **List&lt;List&lt;BigDecimal&gt;&gt;** |  | 
**documentMd5** | **String** |  | 
**documentFilename** | **String** |  | 
**documentParseError** | **Boolean** |  | 
**documentState** | **String** |  | 
**embedDomains** | **List&lt;String&gt;** |  | 
**pageCount** | **BigDecimal** |  | 
**documentProcessed** | **Boolean** |  | 
**demo** | **Boolean** |  | 
**id** | **String** |  | 
**templateType** | **String** |  | 
**pageDimensions** | **List&lt;List&lt;BigDecimal&gt;&gt;** |  | 
**documentUrl** | **String** |  | 
**permanentDocumentUrl** | **String** |  | 
**path** | **String** |  | 
**parentFolderId** | **String** |  | 



## Enum: ExpirationIntervalEnum

Name | Value
---- | -----
MINUTES | &quot;minutes&quot;
HOURS | &quot;hours&quot;
DAYS | &quot;days&quot;




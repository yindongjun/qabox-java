

# Submission


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **String** |  | 
**templateId** | **String** |  |  [optional]
**test** | **Boolean** |  | 
**editable** | **Boolean** |  |  [optional]
**expired** | **Boolean** |  | 
**expiresAt** | **String** |  |  [optional]
**processedAt** | **String** |  |  [optional]
**state** | [**StateEnum**](#StateEnum) |  | 
**data** | **Object** |  |  [optional]
**metadata** | **Object** |  |  [optional]
**truncatedText** | **Object** |  |  [optional]
**pdfHash** | **String** |  |  [optional]
**downloadUrl** | **String** |  |  [optional]
**permanentDownloadUrl** | **String** |  |  [optional]
**batchId** | **String** |  |  [optional]
**dataRequests** | [**List&lt;SubmissionDataRequest&gt;**](SubmissionDataRequest.md) |  |  [optional]
**actions** | [**List&lt;SubmissionAction&gt;**](SubmissionAction.md) |  |  [optional]
**source** | **String** |  |  [optional]
**referrer** | **String** |  |  [optional]



## Enum: StateEnum

Name | Value
---- | -----
PENDING | &quot;pending&quot;
PROCESSED | &quot;processed&quot;
INVALID_DATA | &quot;invalid_data&quot;
ERROR | &quot;error&quot;
IMAGE_DOWNLOAD_FAILED | &quot;image_download_failed&quot;
IMAGE_PROCESSING_FAILED | &quot;image_processing_failed&quot;
WAITING_FOR_DATA_REQUESTS | &quot;waiting_for_data_requests&quot;
SYNTAX_ERROR | &quot;syntax_error&quot;
ACCOUNT_SUSPENDED | &quot;account_suspended&quot;
LICENSE_REVOKED | &quot;license_revoked&quot;
ACCIDENTAL | &quot;accidental&quot;




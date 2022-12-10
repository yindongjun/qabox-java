

# CombinedSubmission


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **String** |  | 
**expired** | **Boolean** |  | 
**expiresAt** | **String** |  |  [optional]
**state** | [**StateEnum**](#StateEnum) |  | 
**metadata** | **Object** |  |  [optional]
**password** | **String** |  |  [optional]
**submissionIds** | **List&lt;String&gt;** |  | 
**sourcePdfs** | **List&lt;Object&gt;** |  | 
**downloadUrl** | **String** |  |  [optional]
**pdfHash** | **String** |  |  [optional]
**actions** | [**List&lt;CombinedSubmissionAction&gt;**](CombinedSubmissionAction.md) |  |  [optional]



## Enum: StateEnum

Name | Value
---- | -----
PENDING | &quot;pending&quot;
PROCESSED | &quot;processed&quot;
ERROR | &quot;error&quot;




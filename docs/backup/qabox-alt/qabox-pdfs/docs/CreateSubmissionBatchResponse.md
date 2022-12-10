

# CreateSubmissionBatchResponse


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**status** | [**StatusEnum**](#StatusEnum) |  | 
**error** | **String** |  |  [optional]
**errors** | **List&lt;String&gt;** |  |  [optional]
**submissionBatch** | [**SubmissionBatch**](SubmissionBatch.md) |  | 
**submissions** | [**List&lt;CreateSubmissionBatchSubmissionsResponse&gt;**](CreateSubmissionBatchSubmissionsResponse.md) |  | 



## Enum: StatusEnum

Name | Value
---- | -----
SUCCESS | &quot;success&quot;
ERROR | &quot;error&quot;




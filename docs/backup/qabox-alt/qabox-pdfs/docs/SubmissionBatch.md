

# SubmissionBatch


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **String** |  | 
**totalCount** | **Integer** |  | 
**pendingCount** | **Integer** |  | 
**errorCount** | **Integer** |  | 
**completionPercentage** | **Integer** |  | 
**state** | [**StateEnum**](#StateEnum) |  | 
**processedAt** | **String** |  | 
**metadata** | **Object** |  | 
**submissions** | [**List&lt;Submission&gt;**](Submission.md) |  |  [optional]



## Enum: StateEnum

Name | Value
---- | -----
PENDING | &quot;pending&quot;
PROCESSED | &quot;processed&quot;
ERROR | &quot;error&quot;




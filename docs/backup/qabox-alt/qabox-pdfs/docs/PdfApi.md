# PdfApi

All URIs are relative to *https://api.docspring.com/api/v1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**addFieldsToTemplate**](PdfApi.md#addFieldsToTemplate) | **PUT** /templates/{template_id}/add_fields | Add new fields to a Template
[**batchGeneratePdfV1**](PdfApi.md#batchGeneratePdfV1) | **POST** /templates/{template_id}/submissions/batch | Generates multiple PDFs
[**batchGeneratePdfs**](PdfApi.md#batchGeneratePdfs) | **POST** /submissions/batches | Generates multiple PDFs
[**combinePdfs**](PdfApi.md#combinePdfs) | **POST** /combined_submissions?v&#x3D;2 | Merge submission PDFs, template PDFs, or custom files
[**combineSubmissions**](PdfApi.md#combineSubmissions) | **POST** /combined_submissions | Merge generated PDFs together
[**copyTemplate**](PdfApi.md#copyTemplate) | **POST** /templates/{template_id}/copy | Copy a Template
[**createCustomFileFromUpload**](PdfApi.md#createCustomFileFromUpload) | **POST** /custom_files | Create a new custom file from a cached presign upload
[**createDataRequestToken**](PdfApi.md#createDataRequestToken) | **POST** /data_requests/{data_request_id}/tokens | Creates a new data request token for form authentication
[**createFolder**](PdfApi.md#createFolder) | **POST** /folders/ | Create a folder
[**createHTMLTemplate**](PdfApi.md#createHTMLTemplate) | **POST** /templates?desc&#x3D;html | Create a new HTML template
[**createPDFTemplate**](PdfApi.md#createPDFTemplate) | **POST** /templates | Create a new PDF template with a form POST file upload
[**createPDFTemplateFromUpload**](PdfApi.md#createPDFTemplateFromUpload) | **POST** /templates?desc&#x3D;cached_upload | Create a new PDF template from a cached presign upload
[**deleteFolder**](PdfApi.md#deleteFolder) | **DELETE** /folders/{folder_id} | Delete a folder
[**expireCombinedSubmission**](PdfApi.md#expireCombinedSubmission) | **DELETE** /combined_submissions/{combined_submission_id} | Expire a combined submission
[**expireSubmission**](PdfApi.md#expireSubmission) | **DELETE** /submissions/{submission_id} | Expire a PDF submission
[**generatePDF**](PdfApi.md#generatePDF) | **POST** /templates/{template_id}/submissions | Generates a new PDF
[**getCombinedSubmission**](PdfApi.md#getCombinedSubmission) | **GET** /combined_submissions/{combined_submission_id} | Check the status of a combined submission (merged PDFs)
[**getDataRequest**](PdfApi.md#getDataRequest) | **GET** /data_requests/{data_request_id} | Look up a submission data request
[**getFullTemplate**](PdfApi.md#getFullTemplate) | **GET** /templates/{template_id}?full&#x3D;true | Fetch the full template attributes
[**getPresignUrl**](PdfApi.md#getPresignUrl) | **GET** /uploads/presign | Get a presigned URL so that you can upload a file to our AWS S3 bucket
[**getSubmission**](PdfApi.md#getSubmission) | **GET** /submissions/{submission_id} | Check the status of a PDF
[**getSubmissionBatch**](PdfApi.md#getSubmissionBatch) | **GET** /submissions/batches/{submission_batch_id} | Check the status of a submission batch job
[**getTemplate**](PdfApi.md#getTemplate) | **GET** /templates/{template_id} | Check the status of an uploaded template
[**getTemplateSchema**](PdfApi.md#getTemplateSchema) | **GET** /templates/{template_id}/schema | Fetch the JSON schema for a template
[**listFolders**](PdfApi.md#listFolders) | **GET** /folders/ | Get a list of all folders
[**listSubmissions**](PdfApi.md#listSubmissions) | **GET** /submissions | List all submissions
[**listSubmissionsForTemplate**](PdfApi.md#listSubmissionsForTemplate) | **GET** /templates/{template_id}/submissions | List all submissions for a given template
[**listTemplates**](PdfApi.md#listTemplates) | **GET** /templates | Get a list of all templates
[**moveFolderToFolder**](PdfApi.md#moveFolderToFolder) | **POST** /folders/{folder_id}/move | Move a folder
[**moveTemplateToFolder**](PdfApi.md#moveTemplateToFolder) | **POST** /templates/{template_id}/move | Move Template to folder
[**renameFolder**](PdfApi.md#renameFolder) | **POST** /folders/{folder_id}/rename | Rename a folder
[**testAuthentication**](PdfApi.md#testAuthentication) | **GET** /authentication | Test Authentication
[**updateDataRequest**](PdfApi.md#updateDataRequest) | **PUT** /data_requests/{data_request_id} | Update a submission data request
[**updateTemplate**](PdfApi.md#updateTemplate) | **PUT** /templates/{template_id} | Update a Template


<a name="addFieldsToTemplate"></a>
# **addFieldsToTemplate**
> AddFieldsTemplateResponse addFieldsToTemplate(templateId, data)

Add new fields to a Template

### Example
```java
// Import classes:
import com.docspring.client.ApiClient;
import com.docspring.client.ApiException;
import com.docspring.client.Configuration;
import com.docspring.client.auth.*;
import com.docspring.client.models.*;
import com.docspring.client.api.PdfApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.docspring.com/api/v1");
    
    // Configure HTTP basic authorization: api_token_basic
    HttpBasicAuth api_token_basic = (HttpBasicAuth) defaultClient.getAuthentication("api_token_basic");
    api_token_basic.setUsername("YOUR USERNAME");
    api_token_basic.setPassword("YOUR PASSWORD");

    PdfApi apiInstance = new PdfApi(defaultClient);
    String templateId = "tpl_000000000000000002"; // String | 
    AddFieldsData data = new AddFieldsData(); // AddFieldsData | 
    try {
      AddFieldsTemplateResponse result = apiInstance.addFieldsToTemplate(templateId, data);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling PdfApi#addFieldsToTemplate");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **templateId** | **String**|  |
 **data** | [**AddFieldsData**](AddFieldsData.md)|  |

### Return type

[**AddFieldsTemplateResponse**](AddFieldsTemplateResponse.md)

### Authorization

[api_token_basic](../README.md#api_token_basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | add fields success |  -  |
**422** | add fields error |  -  |

<a name="batchGeneratePdfV1"></a>
# **batchGeneratePdfV1**
> List&lt;CreateSubmissionResponse&gt; batchGeneratePdfV1(templateId, data)

Generates multiple PDFs

### Example
```java
// Import classes:
import com.docspring.client.ApiClient;
import com.docspring.client.ApiException;
import com.docspring.client.Configuration;
import com.docspring.client.auth.*;
import com.docspring.client.models.*;
import com.docspring.client.api.PdfApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.docspring.com/api/v1");
    
    // Configure HTTP basic authorization: api_token_basic
    HttpBasicAuth api_token_basic = (HttpBasicAuth) defaultClient.getAuthentication("api_token_basic");
    api_token_basic.setUsername("YOUR USERNAME");
    api_token_basic.setPassword("YOUR PASSWORD");

    PdfApi apiInstance = new PdfApi(defaultClient);
    String templateId = "tpl_000000000000000001"; // String | 
    List<Object> data = null; // List<Object> | 
    try {
      List<CreateSubmissionResponse> result = apiInstance.batchGeneratePdfV1(templateId, data);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling PdfApi#batchGeneratePdfV1");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **templateId** | **String**|  |
 **data** | [**List&lt;Object&gt;**](Object.md)|  |

### Return type

[**List&lt;CreateSubmissionResponse&gt;**](CreateSubmissionResponse.md)

### Authorization

[api_token_basic](../README.md#api_token_basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**201** | submissions created |  -  |
**422** | invalid requests |  -  |
**401** | authentication failed |  -  |
**400** | invalid JSON |  -  |

<a name="batchGeneratePdfs"></a>
# **batchGeneratePdfs**
> CreateSubmissionBatchResponse batchGeneratePdfs(data)

Generates multiple PDFs

### Example
```java
// Import classes:
import com.docspring.client.ApiClient;
import com.docspring.client.ApiException;
import com.docspring.client.Configuration;
import com.docspring.client.auth.*;
import com.docspring.client.models.*;
import com.docspring.client.api.PdfApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.docspring.com/api/v1");
    
    // Configure HTTP basic authorization: api_token_basic
    HttpBasicAuth api_token_basic = (HttpBasicAuth) defaultClient.getAuthentication("api_token_basic");
    api_token_basic.setUsername("YOUR USERNAME");
    api_token_basic.setPassword("YOUR PASSWORD");

    PdfApi apiInstance = new PdfApi(defaultClient);
    SubmissionBatchData data = new SubmissionBatchData(); // SubmissionBatchData | 
    try {
      CreateSubmissionBatchResponse result = apiInstance.batchGeneratePdfs(data);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling PdfApi#batchGeneratePdfs");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **data** | [**SubmissionBatchData**](SubmissionBatchData.md)|  |

### Return type

[**CreateSubmissionBatchResponse**](CreateSubmissionBatchResponse.md)

### Authorization

[api_token_basic](../README.md#api_token_basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**201** | submissions created |  -  |
**200** | some PDFs with invalid data |  -  |
**401** | authentication failed |  -  |
**400** | invalid JSON |  -  |

<a name="combinePdfs"></a>
# **combinePdfs**
> CreateCombinedSubmissionResponse combinePdfs(data)

Merge submission PDFs, template PDFs, or custom files

### Example
```java
// Import classes:
import com.docspring.client.ApiClient;
import com.docspring.client.ApiException;
import com.docspring.client.Configuration;
import com.docspring.client.auth.*;
import com.docspring.client.models.*;
import com.docspring.client.api.PdfApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.docspring.com/api/v1");
    
    // Configure HTTP basic authorization: api_token_basic
    HttpBasicAuth api_token_basic = (HttpBasicAuth) defaultClient.getAuthentication("api_token_basic");
    api_token_basic.setUsername("YOUR USERNAME");
    api_token_basic.setPassword("YOUR PASSWORD");

    PdfApi apiInstance = new PdfApi(defaultClient);
    CombinePdfsData data = new CombinePdfsData(); // CombinePdfsData | 
    try {
      CreateCombinedSubmissionResponse result = apiInstance.combinePdfs(data);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling PdfApi#combinePdfs");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **data** | [**CombinePdfsData**](CombinePdfsData.md)|  |

### Return type

[**CreateCombinedSubmissionResponse**](CreateCombinedSubmissionResponse.md)

### Authorization

[api_token_basic](../README.md#api_token_basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**201** | combined submission created |  -  |
**422** | invalid request |  -  |
**400** | invalid JSON |  -  |
**401** | authentication failed |  -  |

<a name="combineSubmissions"></a>
# **combineSubmissions**
> CreateCombinedSubmissionResponse combineSubmissions(data)

Merge generated PDFs together

### Example
```java
// Import classes:
import com.docspring.client.ApiClient;
import com.docspring.client.ApiException;
import com.docspring.client.Configuration;
import com.docspring.client.auth.*;
import com.docspring.client.models.*;
import com.docspring.client.api.PdfApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.docspring.com/api/v1");
    
    // Configure HTTP basic authorization: api_token_basic
    HttpBasicAuth api_token_basic = (HttpBasicAuth) defaultClient.getAuthentication("api_token_basic");
    api_token_basic.setUsername("YOUR USERNAME");
    api_token_basic.setPassword("YOUR PASSWORD");

    PdfApi apiInstance = new PdfApi(defaultClient);
    CombinedSubmissionData data = new CombinedSubmissionData(); // CombinedSubmissionData | 
    try {
      CreateCombinedSubmissionResponse result = apiInstance.combineSubmissions(data);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling PdfApi#combineSubmissions");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **data** | [**CombinedSubmissionData**](CombinedSubmissionData.md)|  |

### Return type

[**CreateCombinedSubmissionResponse**](CreateCombinedSubmissionResponse.md)

### Authorization

[api_token_basic](../README.md#api_token_basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**201** | combined submission created |  -  |
**422** | invalid request |  -  |
**400** | invalid JSON |  -  |
**401** | authentication failed |  -  |

<a name="copyTemplate"></a>
# **copyTemplate**
> Template copyTemplate(templateId, data)

Copy a Template

### Example
```java
// Import classes:
import com.docspring.client.ApiClient;
import com.docspring.client.ApiException;
import com.docspring.client.Configuration;
import com.docspring.client.auth.*;
import com.docspring.client.models.*;
import com.docspring.client.api.PdfApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.docspring.com/api/v1");
    
    // Configure HTTP basic authorization: api_token_basic
    HttpBasicAuth api_token_basic = (HttpBasicAuth) defaultClient.getAuthentication("api_token_basic");
    api_token_basic.setUsername("YOUR USERNAME");
    api_token_basic.setPassword("YOUR PASSWORD");

    PdfApi apiInstance = new PdfApi(defaultClient);
    String templateId = "tpl_000000000000000001"; // String | 
    CopyTemplateData data = new CopyTemplateData(); // CopyTemplateData | 
    try {
      Template result = apiInstance.copyTemplate(templateId, data);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling PdfApi#copyTemplate");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **templateId** | **String**|  |
 **data** | [**CopyTemplateData**](CopyTemplateData.md)|  | [optional]

### Return type

[**Template**](Template.md)

### Authorization

[api_token_basic](../README.md#api_token_basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | copy template success |  -  |
**404** | folder not found |  -  |

<a name="createCustomFileFromUpload"></a>
# **createCustomFileFromUpload**
> CreateCustomFileResponse createCustomFileFromUpload(data)

Create a new custom file from a cached presign upload

### Example
```java
// Import classes:
import com.docspring.client.ApiClient;
import com.docspring.client.ApiException;
import com.docspring.client.Configuration;
import com.docspring.client.auth.*;
import com.docspring.client.models.*;
import com.docspring.client.api.PdfApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.docspring.com/api/v1");
    
    // Configure HTTP basic authorization: api_token_basic
    HttpBasicAuth api_token_basic = (HttpBasicAuth) defaultClient.getAuthentication("api_token_basic");
    api_token_basic.setUsername("YOUR USERNAME");
    api_token_basic.setPassword("YOUR PASSWORD");

    PdfApi apiInstance = new PdfApi(defaultClient);
    CreateCustomFileData data = new CreateCustomFileData(); // CreateCustomFileData | 
    try {
      CreateCustomFileResponse result = apiInstance.createCustomFileFromUpload(data);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling PdfApi#createCustomFileFromUpload");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **data** | [**CreateCustomFileData**](CreateCustomFileData.md)|  |

### Return type

[**CreateCustomFileResponse**](CreateCustomFileResponse.md)

### Authorization

[api_token_basic](../README.md#api_token_basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**201** | returns the custom file |  -  |
**401** | authentication failed |  -  |

<a name="createDataRequestToken"></a>
# **createDataRequestToken**
> CreateSubmissionDataRequestTokenResponse createDataRequestToken(dataRequestId)

Creates a new data request token for form authentication

### Example
```java
// Import classes:
import com.docspring.client.ApiClient;
import com.docspring.client.ApiException;
import com.docspring.client.Configuration;
import com.docspring.client.auth.*;
import com.docspring.client.models.*;
import com.docspring.client.api.PdfApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.docspring.com/api/v1");
    
    // Configure HTTP basic authorization: api_token_basic
    HttpBasicAuth api_token_basic = (HttpBasicAuth) defaultClient.getAuthentication("api_token_basic");
    api_token_basic.setUsername("YOUR USERNAME");
    api_token_basic.setPassword("YOUR PASSWORD");

    PdfApi apiInstance = new PdfApi(defaultClient);
    String dataRequestId = "drq_000000000000000001"; // String | 
    try {
      CreateSubmissionDataRequestTokenResponse result = apiInstance.createDataRequestToken(dataRequestId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling PdfApi#createDataRequestToken");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **dataRequestId** | **String**|  |

### Return type

[**CreateSubmissionDataRequestTokenResponse**](CreateSubmissionDataRequestTokenResponse.md)

### Authorization

[api_token_basic](../README.md#api_token_basic)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**201** | token created |  -  |
**401** | authentication failed |  -  |

<a name="createFolder"></a>
# **createFolder**
> Folder createFolder(data)

Create a folder

### Example
```java
// Import classes:
import com.docspring.client.ApiClient;
import com.docspring.client.ApiException;
import com.docspring.client.Configuration;
import com.docspring.client.auth.*;
import com.docspring.client.models.*;
import com.docspring.client.api.PdfApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.docspring.com/api/v1");
    
    // Configure HTTP basic authorization: api_token_basic
    HttpBasicAuth api_token_basic = (HttpBasicAuth) defaultClient.getAuthentication("api_token_basic");
    api_token_basic.setUsername("YOUR USERNAME");
    api_token_basic.setPassword("YOUR PASSWORD");

    PdfApi apiInstance = new PdfApi(defaultClient);
    CreateFolderData data = new CreateFolderData(); // CreateFolderData | 
    try {
      Folder result = apiInstance.createFolder(data);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling PdfApi#createFolder");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **data** | [**CreateFolderData**](CreateFolderData.md)|  |

### Return type

[**Folder**](Folder.md)

### Authorization

[api_token_basic](../README.md#api_token_basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**422** | name already exist |  -  |
**404** | parent folder doesnt exist |  -  |
**200** | folder created inside another folder |  -  |
**401** | authentication failed |  -  |

<a name="createHTMLTemplate"></a>
# **createHTMLTemplate**
> PendingTemplate createHTMLTemplate(data)

Create a new HTML template

### Example
```java
// Import classes:
import com.docspring.client.ApiClient;
import com.docspring.client.ApiException;
import com.docspring.client.Configuration;
import com.docspring.client.auth.*;
import com.docspring.client.models.*;
import com.docspring.client.api.PdfApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.docspring.com/api/v1");
    
    // Configure HTTP basic authorization: api_token_basic
    HttpBasicAuth api_token_basic = (HttpBasicAuth) defaultClient.getAuthentication("api_token_basic");
    api_token_basic.setUsername("YOUR USERNAME");
    api_token_basic.setPassword("YOUR PASSWORD");

    PdfApi apiInstance = new PdfApi(defaultClient);
    CreateHtmlTemplateData data = new CreateHtmlTemplateData(); // CreateHtmlTemplateData | 
    try {
      PendingTemplate result = apiInstance.createHTMLTemplate(data);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling PdfApi#createHTMLTemplate");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **data** | [**CreateHtmlTemplateData**](CreateHtmlTemplateData.md)|  |

### Return type

[**PendingTemplate**](PendingTemplate.md)

### Authorization

[api_token_basic](../README.md#api_token_basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**201** | returns a created template |  -  |
**401** | authentication failed |  -  |

<a name="createPDFTemplate"></a>
# **createPDFTemplate**
> PendingTemplate createPDFTemplate(templateDocument, templateName, templateParentFolderId)

Create a new PDF template with a form POST file upload

### Example
```java
// Import classes:
import com.docspring.client.ApiClient;
import com.docspring.client.ApiException;
import com.docspring.client.Configuration;
import com.docspring.client.auth.*;
import com.docspring.client.models.*;
import com.docspring.client.api.PdfApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.docspring.com/api/v1");
    
    // Configure HTTP basic authorization: api_token_basic
    HttpBasicAuth api_token_basic = (HttpBasicAuth) defaultClient.getAuthentication("api_token_basic");
    api_token_basic.setUsername("YOUR USERNAME");
    api_token_basic.setPassword("YOUR PASSWORD");

    PdfApi apiInstance = new PdfApi(defaultClient);
    File templateDocument = new File("/path/to/file"); // File | 
    String templateName = "templateName_example"; // String | 
    String templateParentFolderId = "templateParentFolderId_example"; // String | 
    try {
      PendingTemplate result = apiInstance.createPDFTemplate(templateDocument, templateName, templateParentFolderId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling PdfApi#createPDFTemplate");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **templateDocument** | **File**|  |
 **templateName** | **String**|  |
 **templateParentFolderId** | **String**|  | [optional]

### Return type

[**PendingTemplate**](PendingTemplate.md)

### Authorization

[api_token_basic](../README.md#api_token_basic)

### HTTP request headers

 - **Content-Type**: multipart/form-data
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**201** | returns a pending template |  -  |
**401** | authentication failed |  -  |

<a name="createPDFTemplateFromUpload"></a>
# **createPDFTemplateFromUpload**
> PendingTemplate createPDFTemplateFromUpload(data)

Create a new PDF template from a cached presign upload

### Example
```java
// Import classes:
import com.docspring.client.ApiClient;
import com.docspring.client.ApiException;
import com.docspring.client.Configuration;
import com.docspring.client.auth.*;
import com.docspring.client.models.*;
import com.docspring.client.api.PdfApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.docspring.com/api/v1");
    
    // Configure HTTP basic authorization: api_token_basic
    HttpBasicAuth api_token_basic = (HttpBasicAuth) defaultClient.getAuthentication("api_token_basic");
    api_token_basic.setUsername("YOUR USERNAME");
    api_token_basic.setPassword("YOUR PASSWORD");

    PdfApi apiInstance = new PdfApi(defaultClient);
    CreateTemplateFromUploadData data = new CreateTemplateFromUploadData(); // CreateTemplateFromUploadData | 
    try {
      PendingTemplate result = apiInstance.createPDFTemplateFromUpload(data);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling PdfApi#createPDFTemplateFromUpload");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **data** | [**CreateTemplateFromUploadData**](CreateTemplateFromUploadData.md)|  |

### Return type

[**PendingTemplate**](PendingTemplate.md)

### Authorization

[api_token_basic](../README.md#api_token_basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**201** | returns a pending template |  -  |
**401** | authentication failed |  -  |

<a name="deleteFolder"></a>
# **deleteFolder**
> Folder deleteFolder(folderId)

Delete a folder

### Example
```java
// Import classes:
import com.docspring.client.ApiClient;
import com.docspring.client.ApiException;
import com.docspring.client.Configuration;
import com.docspring.client.auth.*;
import com.docspring.client.models.*;
import com.docspring.client.api.PdfApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.docspring.com/api/v1");
    
    // Configure HTTP basic authorization: api_token_basic
    HttpBasicAuth api_token_basic = (HttpBasicAuth) defaultClient.getAuthentication("api_token_basic");
    api_token_basic.setUsername("YOUR USERNAME");
    api_token_basic.setPassword("YOUR PASSWORD");

    PdfApi apiInstance = new PdfApi(defaultClient);
    String folderId = "fld_000000000000000001"; // String | 
    try {
      Folder result = apiInstance.deleteFolder(folderId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling PdfApi#deleteFolder");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **folderId** | **String**|  |

### Return type

[**Folder**](Folder.md)

### Authorization

[api_token_basic](../README.md#api_token_basic)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**404** | folder doesnt exist |  -  |
**422** | folder has contents |  -  |
**200** | folder is empty |  -  |
**401** | authentication failed |  -  |

<a name="expireCombinedSubmission"></a>
# **expireCombinedSubmission**
> CombinedSubmission expireCombinedSubmission(combinedSubmissionId)

Expire a combined submission

### Example
```java
// Import classes:
import com.docspring.client.ApiClient;
import com.docspring.client.ApiException;
import com.docspring.client.Configuration;
import com.docspring.client.auth.*;
import com.docspring.client.models.*;
import com.docspring.client.api.PdfApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.docspring.com/api/v1");
    
    // Configure HTTP basic authorization: api_token_basic
    HttpBasicAuth api_token_basic = (HttpBasicAuth) defaultClient.getAuthentication("api_token_basic");
    api_token_basic.setUsername("YOUR USERNAME");
    api_token_basic.setPassword("YOUR PASSWORD");

    PdfApi apiInstance = new PdfApi(defaultClient);
    String combinedSubmissionId = "com_000000000000000001"; // String | 
    try {
      CombinedSubmission result = apiInstance.expireCombinedSubmission(combinedSubmissionId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling PdfApi#expireCombinedSubmission");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **combinedSubmissionId** | **String**|  |

### Return type

[**CombinedSubmission**](CombinedSubmission.md)

### Authorization

[api_token_basic](../README.md#api_token_basic)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | submission was expired |  -  |
**404** | combined submission not found |  -  |
**403** | test API token used |  -  |
**401** | authentication failed |  -  |

<a name="expireSubmission"></a>
# **expireSubmission**
> Submission expireSubmission(submissionId)

Expire a PDF submission

### Example
```java
// Import classes:
import com.docspring.client.ApiClient;
import com.docspring.client.ApiException;
import com.docspring.client.Configuration;
import com.docspring.client.auth.*;
import com.docspring.client.models.*;
import com.docspring.client.api.PdfApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.docspring.com/api/v1");
    
    // Configure HTTP basic authorization: api_token_basic
    HttpBasicAuth api_token_basic = (HttpBasicAuth) defaultClient.getAuthentication("api_token_basic");
    api_token_basic.setUsername("YOUR USERNAME");
    api_token_basic.setPassword("YOUR PASSWORD");

    PdfApi apiInstance = new PdfApi(defaultClient);
    String submissionId = "sub_000000000000000001"; // String | 
    try {
      Submission result = apiInstance.expireSubmission(submissionId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling PdfApi#expireSubmission");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **submissionId** | **String**|  |

### Return type

[**Submission**](Submission.md)

### Authorization

[api_token_basic](../README.md#api_token_basic)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | submission was expired |  -  |
**404** | submission not found |  -  |
**401** | authentication failed |  -  |
**403** | test API token used |  -  |

<a name="generatePDF"></a>
# **generatePDF**
> CreateSubmissionResponse generatePDF(templateId, data)

Generates a new PDF

### Example
```java
// Import classes:
import com.docspring.client.ApiClient;
import com.docspring.client.ApiException;
import com.docspring.client.Configuration;
import com.docspring.client.auth.*;
import com.docspring.client.models.*;
import com.docspring.client.api.PdfApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.docspring.com/api/v1");
    
    // Configure HTTP basic authorization: api_token_basic
    HttpBasicAuth api_token_basic = (HttpBasicAuth) defaultClient.getAuthentication("api_token_basic");
    api_token_basic.setUsername("YOUR USERNAME");
    api_token_basic.setPassword("YOUR PASSWORD");

    PdfApi apiInstance = new PdfApi(defaultClient);
    String templateId = "tpl_000000000000000001"; // String | 
    SubmissionData data = new SubmissionData(); // SubmissionData | 
    try {
      CreateSubmissionResponse result = apiInstance.generatePDF(templateId, data);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling PdfApi#generatePDF");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **templateId** | **String**|  |
 **data** | [**SubmissionData**](SubmissionData.md)|  |

### Return type

[**CreateSubmissionResponse**](CreateSubmissionResponse.md)

### Authorization

[api_token_basic](../README.md#api_token_basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**201** | submission created |  -  |
**400** | invalid JSON |  -  |
**422** | invalid request |  -  |
**401** | authentication failed |  -  |

<a name="getCombinedSubmission"></a>
# **getCombinedSubmission**
> CombinedSubmission getCombinedSubmission(combinedSubmissionId)

Check the status of a combined submission (merged PDFs)

### Example
```java
// Import classes:
import com.docspring.client.ApiClient;
import com.docspring.client.ApiException;
import com.docspring.client.Configuration;
import com.docspring.client.auth.*;
import com.docspring.client.models.*;
import com.docspring.client.api.PdfApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.docspring.com/api/v1");
    
    // Configure HTTP basic authorization: api_token_basic
    HttpBasicAuth api_token_basic = (HttpBasicAuth) defaultClient.getAuthentication("api_token_basic");
    api_token_basic.setUsername("YOUR USERNAME");
    api_token_basic.setPassword("YOUR PASSWORD");

    PdfApi apiInstance = new PdfApi(defaultClient);
    String combinedSubmissionId = "com_000000000000000001"; // String | 
    try {
      CombinedSubmission result = apiInstance.getCombinedSubmission(combinedSubmissionId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling PdfApi#getCombinedSubmission");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **combinedSubmissionId** | **String**|  |

### Return type

[**CombinedSubmission**](CombinedSubmission.md)

### Authorization

[api_token_basic](../README.md#api_token_basic)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | processed combined submission found |  -  |
**404** | combined submission not found |  -  |
**401** | authentication failed |  -  |

<a name="getDataRequest"></a>
# **getDataRequest**
> SubmissionDataRequest getDataRequest(dataRequestId)

Look up a submission data request

### Example
```java
// Import classes:
import com.docspring.client.ApiClient;
import com.docspring.client.ApiException;
import com.docspring.client.Configuration;
import com.docspring.client.auth.*;
import com.docspring.client.models.*;
import com.docspring.client.api.PdfApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.docspring.com/api/v1");
    
    // Configure HTTP basic authorization: api_token_basic
    HttpBasicAuth api_token_basic = (HttpBasicAuth) defaultClient.getAuthentication("api_token_basic");
    api_token_basic.setUsername("YOUR USERNAME");
    api_token_basic.setPassword("YOUR PASSWORD");

    PdfApi apiInstance = new PdfApi(defaultClient);
    String dataRequestId = "drq_000000000000000001"; // String | 
    try {
      SubmissionDataRequest result = apiInstance.getDataRequest(dataRequestId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling PdfApi#getDataRequest");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **dataRequestId** | **String**|  |

### Return type

[**SubmissionDataRequest**](SubmissionDataRequest.md)

### Authorization

[api_token_basic](../README.md#api_token_basic)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | completed submission data request found |  -  |
**404** | submission data request not found |  -  |
**401** | authentication failed |  -  |

<a name="getFullTemplate"></a>
# **getFullTemplate**
> Template1 getFullTemplate(templateId)

Fetch the full template attributes

### Example
```java
// Import classes:
import com.docspring.client.ApiClient;
import com.docspring.client.ApiException;
import com.docspring.client.Configuration;
import com.docspring.client.auth.*;
import com.docspring.client.models.*;
import com.docspring.client.api.PdfApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.docspring.com/api/v1");
    
    // Configure HTTP basic authorization: api_token_basic
    HttpBasicAuth api_token_basic = (HttpBasicAuth) defaultClient.getAuthentication("api_token_basic");
    api_token_basic.setUsername("YOUR USERNAME");
    api_token_basic.setPassword("YOUR PASSWORD");

    PdfApi apiInstance = new PdfApi(defaultClient);
    String templateId = "tpl_000000000000000001"; // String | 
    try {
      Template1 result = apiInstance.getFullTemplate(templateId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling PdfApi#getFullTemplate");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **templateId** | **String**|  |

### Return type

[**Template1**](Template1.md)

### Authorization

[api_token_basic](../README.md#api_token_basic)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | template found |  -  |
**404** | template not found |  -  |
**401** | authentication failed |  -  |

<a name="getPresignUrl"></a>
# **getPresignUrl**
> UploadPresign getPresignUrl()

Get a presigned URL so that you can upload a file to our AWS S3 bucket

### Example
```java
// Import classes:
import com.docspring.client.ApiClient;
import com.docspring.client.ApiException;
import com.docspring.client.Configuration;
import com.docspring.client.auth.*;
import com.docspring.client.models.*;
import com.docspring.client.api.PdfApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.docspring.com/api/v1");
    
    // Configure HTTP basic authorization: api_token_basic
    HttpBasicAuth api_token_basic = (HttpBasicAuth) defaultClient.getAuthentication("api_token_basic");
    api_token_basic.setUsername("YOUR USERNAME");
    api_token_basic.setPassword("YOUR PASSWORD");

    PdfApi apiInstance = new PdfApi(defaultClient);
    try {
      UploadPresign result = apiInstance.getPresignUrl();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling PdfApi#getPresignUrl");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**UploadPresign**](UploadPresign.md)

### Authorization

[api_token_basic](../README.md#api_token_basic)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | presign URL generated |  -  |
**401** | authentication failed |  -  |

<a name="getSubmission"></a>
# **getSubmission**
> Submission getSubmission(submissionId, includeData)

Check the status of a PDF

### Example
```java
// Import classes:
import com.docspring.client.ApiClient;
import com.docspring.client.ApiException;
import com.docspring.client.Configuration;
import com.docspring.client.auth.*;
import com.docspring.client.models.*;
import com.docspring.client.api.PdfApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.docspring.com/api/v1");
    
    // Configure HTTP basic authorization: api_token_basic
    HttpBasicAuth api_token_basic = (HttpBasicAuth) defaultClient.getAuthentication("api_token_basic");
    api_token_basic.setUsername("YOUR USERNAME");
    api_token_basic.setPassword("YOUR PASSWORD");

    PdfApi apiInstance = new PdfApi(defaultClient);
    String submissionId = "sub_000000000000000001"; // String | 
    Boolean includeData = true; // Boolean | 
    try {
      Submission result = apiInstance.getSubmission(submissionId, includeData);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling PdfApi#getSubmission");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **submissionId** | **String**|  |
 **includeData** | **Boolean**|  | [optional]

### Return type

[**Submission**](Submission.md)

### Authorization

[api_token_basic](../README.md#api_token_basic)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | processed submission found |  -  |
**404** | submission not found |  -  |
**401** | authentication failed |  -  |

<a name="getSubmissionBatch"></a>
# **getSubmissionBatch**
> SubmissionBatch getSubmissionBatch(submissionBatchId, includeSubmissions)

Check the status of a submission batch job

### Example
```java
// Import classes:
import com.docspring.client.ApiClient;
import com.docspring.client.ApiException;
import com.docspring.client.Configuration;
import com.docspring.client.auth.*;
import com.docspring.client.models.*;
import com.docspring.client.api.PdfApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.docspring.com/api/v1");
    
    // Configure HTTP basic authorization: api_token_basic
    HttpBasicAuth api_token_basic = (HttpBasicAuth) defaultClient.getAuthentication("api_token_basic");
    api_token_basic.setUsername("YOUR USERNAME");
    api_token_basic.setPassword("YOUR PASSWORD");

    PdfApi apiInstance = new PdfApi(defaultClient);
    String submissionBatchId = "sbb_000000000000000001"; // String | 
    Boolean includeSubmissions = true; // Boolean | 
    try {
      SubmissionBatch result = apiInstance.getSubmissionBatch(submissionBatchId, includeSubmissions);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling PdfApi#getSubmissionBatch");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **submissionBatchId** | **String**|  |
 **includeSubmissions** | **Boolean**|  | [optional]

### Return type

[**SubmissionBatch**](SubmissionBatch.md)

### Authorization

[api_token_basic](../README.md#api_token_basic)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | processed submission batch found |  -  |
**404** | submission batch not found |  -  |
**401** | authentication failed |  -  |

<a name="getTemplate"></a>
# **getTemplate**
> Template getTemplate(templateId)

Check the status of an uploaded template

### Example
```java
// Import classes:
import com.docspring.client.ApiClient;
import com.docspring.client.ApiException;
import com.docspring.client.Configuration;
import com.docspring.client.auth.*;
import com.docspring.client.models.*;
import com.docspring.client.api.PdfApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.docspring.com/api/v1");
    
    // Configure HTTP basic authorization: api_token_basic
    HttpBasicAuth api_token_basic = (HttpBasicAuth) defaultClient.getAuthentication("api_token_basic");
    api_token_basic.setUsername("YOUR USERNAME");
    api_token_basic.setPassword("YOUR PASSWORD");

    PdfApi apiInstance = new PdfApi(defaultClient);
    String templateId = "tpl_000000000000000001"; // String | 
    try {
      Template result = apiInstance.getTemplate(templateId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling PdfApi#getTemplate");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **templateId** | **String**|  |

### Return type

[**Template**](Template.md)

### Authorization

[api_token_basic](../README.md#api_token_basic)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | template found |  -  |
**404** | template not found |  -  |
**401** | authentication failed |  -  |

<a name="getTemplateSchema"></a>
# **getTemplateSchema**
> TemplateSchema getTemplateSchema(templateId)

Fetch the JSON schema for a template

### Example
```java
// Import classes:
import com.docspring.client.ApiClient;
import com.docspring.client.ApiException;
import com.docspring.client.Configuration;
import com.docspring.client.auth.*;
import com.docspring.client.models.*;
import com.docspring.client.api.PdfApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.docspring.com/api/v1");
    
    // Configure HTTP basic authorization: api_token_basic
    HttpBasicAuth api_token_basic = (HttpBasicAuth) defaultClient.getAuthentication("api_token_basic");
    api_token_basic.setUsername("YOUR USERNAME");
    api_token_basic.setPassword("YOUR PASSWORD");

    PdfApi apiInstance = new PdfApi(defaultClient);
    String templateId = "tpl_000000000000000001"; // String | 
    try {
      TemplateSchema result = apiInstance.getTemplateSchema(templateId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling PdfApi#getTemplateSchema");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **templateId** | **String**|  |

### Return type

[**TemplateSchema**](TemplateSchema.md)

### Authorization

[api_token_basic](../README.md#api_token_basic)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | template found |  -  |
**404** | template not found |  -  |
**401** | authentication failed |  -  |

<a name="listFolders"></a>
# **listFolders**
> List&lt;Folder&gt; listFolders(parentFolderId)

Get a list of all folders

### Example
```java
// Import classes:
import com.docspring.client.ApiClient;
import com.docspring.client.ApiException;
import com.docspring.client.Configuration;
import com.docspring.client.auth.*;
import com.docspring.client.models.*;
import com.docspring.client.api.PdfApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.docspring.com/api/v1");
    
    // Configure HTTP basic authorization: api_token_basic
    HttpBasicAuth api_token_basic = (HttpBasicAuth) defaultClient.getAuthentication("api_token_basic");
    api_token_basic.setUsername("YOUR USERNAME");
    api_token_basic.setPassword("YOUR PASSWORD");

    PdfApi apiInstance = new PdfApi(defaultClient);
    String parentFolderId = "fld_000000000000000002"; // String | Filter By Folder Id
    try {
      List<Folder> result = apiInstance.listFolders(parentFolderId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling PdfApi#listFolders");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **parentFolderId** | **String**| Filter By Folder Id | [optional]

### Return type

[**List&lt;Folder&gt;**](Folder.md)

### Authorization

[api_token_basic](../README.md#api_token_basic)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | enumerate all folders |  -  |
**401** | authentication failed |  -  |

<a name="listSubmissions"></a>
# **listSubmissions**
> ListSubmissionsResponse listSubmissions(cursor, limit, createdAfter, createdBefore, type, includeData)

List all submissions

### Example
```java
// Import classes:
import com.docspring.client.ApiClient;
import com.docspring.client.ApiException;
import com.docspring.client.Configuration;
import com.docspring.client.auth.*;
import com.docspring.client.models.*;
import com.docspring.client.api.PdfApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.docspring.com/api/v1");
    
    // Configure HTTP basic authorization: api_token_basic
    HttpBasicAuth api_token_basic = (HttpBasicAuth) defaultClient.getAuthentication("api_token_basic");
    api_token_basic.setUsername("YOUR USERNAME");
    api_token_basic.setPassword("YOUR PASSWORD");

    PdfApi apiInstance = new PdfApi(defaultClient);
    String cursor = "sub_list_000012"; // String | 
    BigDecimal limit = new BigDecimal("3"); // BigDecimal | 
    String createdAfter = "2019-01-01T09:00:00-05:00"; // String | 
    String createdBefore = "2020-01-01T09:00:00-05:00"; // String | 
    String type = "test"; // String | 
    Boolean includeData = true; // Boolean | 
    try {
      ListSubmissionsResponse result = apiInstance.listSubmissions(cursor, limit, createdAfter, createdBefore, type, includeData);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling PdfApi#listSubmissions");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **cursor** | **String**|  | [optional]
 **limit** | **BigDecimal**|  | [optional]
 **createdAfter** | **String**|  | [optional]
 **createdBefore** | **String**|  | [optional]
 **type** | **String**|  | [optional]
 **includeData** | **Boolean**|  | [optional]

### Return type

[**ListSubmissionsResponse**](ListSubmissionsResponse.md)

### Authorization

[api_token_basic](../README.md#api_token_basic)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | listing submissions |  -  |
**400** | invalid type |  -  |
**401** | authentication failed |  -  |

<a name="listSubmissionsForTemplate"></a>
# **listSubmissionsForTemplate**
> ListSubmissionsResponse listSubmissionsForTemplate(templateId, cursor, limit, createdAfter, createdBefore, type, includeData)

List all submissions for a given template

### Example
```java
// Import classes:
import com.docspring.client.ApiClient;
import com.docspring.client.ApiException;
import com.docspring.client.Configuration;
import com.docspring.client.auth.*;
import com.docspring.client.models.*;
import com.docspring.client.api.PdfApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.docspring.com/api/v1");
    
    // Configure HTTP basic authorization: api_token_basic
    HttpBasicAuth api_token_basic = (HttpBasicAuth) defaultClient.getAuthentication("api_token_basic");
    api_token_basic.setUsername("YOUR USERNAME");
    api_token_basic.setPassword("YOUR PASSWORD");

    PdfApi apiInstance = new PdfApi(defaultClient);
    String templateId = "tpl_000000000000000002"; // String | 
    String cursor = "cursor_example"; // String | 
    BigDecimal limit = new BigDecimal(78); // BigDecimal | 
    String createdAfter = "createdAfter_example"; // String | 
    String createdBefore = "createdBefore_example"; // String | 
    String type = "type_example"; // String | 
    Boolean includeData = true; // Boolean | 
    try {
      ListSubmissionsResponse result = apiInstance.listSubmissionsForTemplate(templateId, cursor, limit, createdAfter, createdBefore, type, includeData);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling PdfApi#listSubmissionsForTemplate");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **templateId** | **String**|  |
 **cursor** | **String**|  | [optional]
 **limit** | **BigDecimal**|  | [optional]
 **createdAfter** | **String**|  | [optional]
 **createdBefore** | **String**|  | [optional]
 **type** | **String**|  | [optional]
 **includeData** | **Boolean**|  | [optional]

### Return type

[**ListSubmissionsResponse**](ListSubmissionsResponse.md)

### Authorization

[api_token_basic](../README.md#api_token_basic)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | listing submissions |  -  |
**404** | invalid template id |  -  |

<a name="listTemplates"></a>
# **listTemplates**
> List&lt;Template&gt; listTemplates(query, parentFolderId, page, perPage)

Get a list of all templates

### Example
```java
// Import classes:
import com.docspring.client.ApiClient;
import com.docspring.client.ApiException;
import com.docspring.client.Configuration;
import com.docspring.client.auth.*;
import com.docspring.client.models.*;
import com.docspring.client.api.PdfApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.docspring.com/api/v1");
    
    // Configure HTTP basic authorization: api_token_basic
    HttpBasicAuth api_token_basic = (HttpBasicAuth) defaultClient.getAuthentication("api_token_basic");
    api_token_basic.setUsername("YOUR USERNAME");
    api_token_basic.setPassword("YOUR PASSWORD");

    PdfApi apiInstance = new PdfApi(defaultClient);
    String query = "2"; // String | Search By Name
    String parentFolderId = "fld_000000000000000001"; // String | Filter By Folder Id
    Integer page = 2; // Integer | Default: 1
    Integer perPage = 1; // Integer | Default: 50
    try {
      List<Template> result = apiInstance.listTemplates(query, parentFolderId, page, perPage);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling PdfApi#listTemplates");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **query** | **String**| Search By Name | [optional]
 **parentFolderId** | **String**| Filter By Folder Id | [optional]
 **page** | **Integer**| Default: 1 | [optional]
 **perPage** | **Integer**| Default: 50 | [optional]

### Return type

[**List&lt;Template&gt;**](Template.md)

### Authorization

[api_token_basic](../README.md#api_token_basic)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | enumerate all templates |  -  |
**404** | filter templates by invalid folder id |  -  |
**401** | authentication failed |  -  |

<a name="moveFolderToFolder"></a>
# **moveFolderToFolder**
> Folder moveFolderToFolder(folderId, data)

Move a folder

### Example
```java
// Import classes:
import com.docspring.client.ApiClient;
import com.docspring.client.ApiException;
import com.docspring.client.Configuration;
import com.docspring.client.auth.*;
import com.docspring.client.models.*;
import com.docspring.client.api.PdfApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.docspring.com/api/v1");
    
    // Configure HTTP basic authorization: api_token_basic
    HttpBasicAuth api_token_basic = (HttpBasicAuth) defaultClient.getAuthentication("api_token_basic");
    api_token_basic.setUsername("YOUR USERNAME");
    api_token_basic.setPassword("YOUR PASSWORD");

    PdfApi apiInstance = new PdfApi(defaultClient);
    String folderId = "fld_000000000000000001"; // String | 
    MoveFolderData data = new MoveFolderData(); // MoveFolderData | 
    try {
      Folder result = apiInstance.moveFolderToFolder(folderId, data);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling PdfApi#moveFolderToFolder");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **folderId** | **String**|  |
 **data** | [**MoveFolderData**](MoveFolderData.md)|  |

### Return type

[**Folder**](Folder.md)

### Authorization

[api_token_basic](../README.md#api_token_basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**404** | parent folder doesnt exist |  -  |
**200** | move to root folder |  -  |
**401** | authentication failed |  -  |

<a name="moveTemplateToFolder"></a>
# **moveTemplateToFolder**
> Template moveTemplateToFolder(templateId, data)

Move Template to folder

### Example
```java
// Import classes:
import com.docspring.client.ApiClient;
import com.docspring.client.ApiException;
import com.docspring.client.Configuration;
import com.docspring.client.auth.*;
import com.docspring.client.models.*;
import com.docspring.client.api.PdfApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.docspring.com/api/v1");
    
    // Configure HTTP basic authorization: api_token_basic
    HttpBasicAuth api_token_basic = (HttpBasicAuth) defaultClient.getAuthentication("api_token_basic");
    api_token_basic.setUsername("YOUR USERNAME");
    api_token_basic.setPassword("YOUR PASSWORD");

    PdfApi apiInstance = new PdfApi(defaultClient);
    String templateId = "tpl_000000000000000001"; // String | 
    MoveTemplateData data = new MoveTemplateData(); // MoveTemplateData | 
    try {
      Template result = apiInstance.moveTemplateToFolder(templateId, data);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling PdfApi#moveTemplateToFolder");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **templateId** | **String**|  |
 **data** | [**MoveTemplateData**](MoveTemplateData.md)|  |

### Return type

[**Template**](Template.md)

### Authorization

[api_token_basic](../README.md#api_token_basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | move template success |  -  |
**404** | folder not found |  -  |

<a name="renameFolder"></a>
# **renameFolder**
> renameFolder(folderId, data)

Rename a folder

### Example
```java
// Import classes:
import com.docspring.client.ApiClient;
import com.docspring.client.ApiException;
import com.docspring.client.Configuration;
import com.docspring.client.auth.*;
import com.docspring.client.models.*;
import com.docspring.client.api.PdfApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.docspring.com/api/v1");
    
    // Configure HTTP basic authorization: api_token_basic
    HttpBasicAuth api_token_basic = (HttpBasicAuth) defaultClient.getAuthentication("api_token_basic");
    api_token_basic.setUsername("YOUR USERNAME");
    api_token_basic.setPassword("YOUR PASSWORD");

    PdfApi apiInstance = new PdfApi(defaultClient);
    String folderId = "fld_000000000000000001"; // String | 
    RenameFolderData data = new RenameFolderData(); // RenameFolderData | 
    try {
      apiInstance.renameFolder(folderId, data);
    } catch (ApiException e) {
      System.err.println("Exception when calling PdfApi#renameFolder");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **folderId** | **String**|  |
 **data** | [**RenameFolderData**](RenameFolderData.md)|  |

### Return type

null (empty response body)

### Authorization

[api_token_basic](../README.md#api_token_basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**422** | name already exist |  -  |
**404** | folder doesnt belong to me |  -  |
**200** | successful rename |  -  |
**401** | authentication failed |  -  |

<a name="testAuthentication"></a>
# **testAuthentication**
> AuthenticationSuccessResponse testAuthentication()

Test Authentication

### Example
```java
// Import classes:
import com.docspring.client.ApiClient;
import com.docspring.client.ApiException;
import com.docspring.client.Configuration;
import com.docspring.client.auth.*;
import com.docspring.client.models.*;
import com.docspring.client.api.PdfApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.docspring.com/api/v1");
    
    // Configure HTTP basic authorization: api_token_basic
    HttpBasicAuth api_token_basic = (HttpBasicAuth) defaultClient.getAuthentication("api_token_basic");
    api_token_basic.setUsername("YOUR USERNAME");
    api_token_basic.setPassword("YOUR PASSWORD");

    PdfApi apiInstance = new PdfApi(defaultClient);
    try {
      AuthenticationSuccessResponse result = apiInstance.testAuthentication();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling PdfApi#testAuthentication");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**AuthenticationSuccessResponse**](AuthenticationSuccessResponse.md)

### Authorization

[api_token_basic](../README.md#api_token_basic)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | authentication succeeded |  -  |
**401** | authentication failed |  -  |

<a name="updateDataRequest"></a>
# **updateDataRequest**
> UpdateDataRequestResponse updateDataRequest(dataRequestId, data)

Update a submission data request

### Example
```java
// Import classes:
import com.docspring.client.ApiClient;
import com.docspring.client.ApiException;
import com.docspring.client.Configuration;
import com.docspring.client.auth.*;
import com.docspring.client.models.*;
import com.docspring.client.api.PdfApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.docspring.com/api/v1");
    
    // Configure HTTP basic authorization: api_token_basic
    HttpBasicAuth api_token_basic = (HttpBasicAuth) defaultClient.getAuthentication("api_token_basic");
    api_token_basic.setUsername("YOUR USERNAME");
    api_token_basic.setPassword("YOUR PASSWORD");

    PdfApi apiInstance = new PdfApi(defaultClient);
    String dataRequestId = "drq_000000000000000001"; // String | 
    UpdateSubmissionDataRequestData data = new UpdateSubmissionDataRequestData(); // UpdateSubmissionDataRequestData | 
    try {
      UpdateDataRequestResponse result = apiInstance.updateDataRequest(dataRequestId, data);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling PdfApi#updateDataRequest");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **dataRequestId** | **String**|  |
 **data** | [**UpdateSubmissionDataRequestData**](UpdateSubmissionDataRequestData.md)|  |

### Return type

[**UpdateDataRequestResponse**](UpdateDataRequestResponse.md)

### Authorization

[api_token_basic](../README.md#api_token_basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | submission data request updated |  -  |
**422** | invalid request |  -  |
**404** | submission data request not found |  -  |
**401** | authentication failed |  -  |

<a name="updateTemplate"></a>
# **updateTemplate**
> UpdateTemplateResponse updateTemplate(templateId, data)

Update a Template

### Example
```java
// Import classes:
import com.docspring.client.ApiClient;
import com.docspring.client.ApiException;
import com.docspring.client.Configuration;
import com.docspring.client.auth.*;
import com.docspring.client.models.*;
import com.docspring.client.api.PdfApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.docspring.com/api/v1");
    
    // Configure HTTP basic authorization: api_token_basic
    HttpBasicAuth api_token_basic = (HttpBasicAuth) defaultClient.getAuthentication("api_token_basic");
    api_token_basic.setUsername("YOUR USERNAME");
    api_token_basic.setPassword("YOUR PASSWORD");

    PdfApi apiInstance = new PdfApi(defaultClient);
    String templateId = "tpl_000000000000000003"; // String | 
    UpdateTemplateData data = new UpdateTemplateData(); // UpdateTemplateData | 
    try {
      UpdateTemplateResponse result = apiInstance.updateTemplate(templateId, data);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling PdfApi#updateTemplate");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **templateId** | **String**|  |
 **data** | [**UpdateTemplateData**](UpdateTemplateData.md)|  |

### Return type

[**UpdateTemplateResponse**](UpdateTemplateResponse.md)

### Authorization

[api_token_basic](../README.md#api_token_basic)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | update template success |  -  |


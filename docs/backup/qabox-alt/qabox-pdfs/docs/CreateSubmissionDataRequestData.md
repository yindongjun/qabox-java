

# CreateSubmissionDataRequestData


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**authPhoneNumberHash** | **String** |  |  [optional]
**authProvider** | **String** |  |  [optional]
**authSecondFactorType** | [**AuthSecondFactorTypeEnum**](#AuthSecondFactorTypeEnum) |  |  [optional]
**authSessionIdHash** | **String** |  |  [optional]
**authSessionStartedAt** | **String** |  |  [optional]
**authType** | [**AuthTypeEnum**](#AuthTypeEnum) |  | 
**authUserIdHash** | **String** |  |  [optional]
**authUsernameHash** | **String** |  |  [optional]
**email** | **String** |  | 
**fields** | **List&lt;String&gt;** |  |  [optional]
**metadata** | **Object** |  |  [optional]
**name** | **String** |  |  [optional]
**order** | **Integer** |  |  [optional]



## Enum: AuthSecondFactorTypeEnum

Name | Value
---- | -----
NONE | &quot;none&quot;
PHONE_NUMBER | &quot;phone_number&quot;
TOTP | &quot;totp&quot;
MOBILE_PUSH | &quot;mobile_push&quot;
SECURITY_KEY | &quot;security_key&quot;
FINGERPRINT | &quot;fingerprint&quot;



## Enum: AuthTypeEnum

Name | Value
---- | -----
NONE | &quot;none&quot;
PASSWORD | &quot;password&quot;
OAUTH | &quot;oauth&quot;
EMAIL_LINK | &quot;email_link&quot;
PHONE_NUMBER | &quot;phone_number&quot;
LDAP | &quot;ldap&quot;
SAML | &quot;saml&quot;




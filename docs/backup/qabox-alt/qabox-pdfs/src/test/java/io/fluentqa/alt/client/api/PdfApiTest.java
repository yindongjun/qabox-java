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


package io.fluentqa.alt.client.api;

import io.fluentqa.alt.client.ApiException;
import io.fluentqa.alt.client.model.AddFieldsData;
import io.fluentqa.alt.client.model.AddFieldsTemplateResponse;
import io.fluentqa.alt.client.model.AuthenticationError;
import io.fluentqa.alt.client.model.AuthenticationSuccessResponse;
import java.math.BigDecimal;
import io.fluentqa.alt.client.model.CombinePdfsData;
import io.fluentqa.alt.client.model.CombinedSubmission;
import io.fluentqa.alt.client.model.CombinedSubmissionData;
import io.fluentqa.alt.client.model.CopyTemplateData;
import io.fluentqa.alt.client.model.CreateCombinedSubmissionResponse;
import io.fluentqa.alt.client.model.CreateCustomFileData;
import io.fluentqa.alt.client.model.CreateCustomFileResponse;
import io.fluentqa.alt.client.model.CreateFolderData;
import io.fluentqa.alt.client.model.CreateHtmlTemplateData;
import io.fluentqa.alt.client.model.CreateSubmissionBatchResponse;
import io.fluentqa.alt.client.model.CreateSubmissionDataRequestTokenResponse;
import io.fluentqa.alt.client.model.CreateSubmissionResponse;
import io.fluentqa.alt.client.model.CreateTemplateFromUploadData;
import io.fluentqa.alt.client.model.Error;
import java.io.File;
import io.fluentqa.alt.client.model.Folder;
import io.fluentqa.alt.client.model.InvalidRequest;
import io.fluentqa.alt.client.model.ListSubmissionsResponse;
import io.fluentqa.alt.client.model.MoveFolderData;
import io.fluentqa.alt.client.model.MoveTemplateData;
import io.fluentqa.alt.client.model.PendingTemplate;
import io.fluentqa.alt.client.model.RenameFolderData;
import io.fluentqa.alt.client.model.Submission;
import io.fluentqa.alt.client.model.SubmissionBatch;
import io.fluentqa.alt.client.model.SubmissionBatchData;
import io.fluentqa.alt.client.model.SubmissionData;
import io.fluentqa.alt.client.model.SubmissionDataRequest;
import io.fluentqa.alt.client.model.Template;
import io.fluentqa.alt.client.model.Template1;
import io.fluentqa.alt.client.model.TemplateSchema;
import io.fluentqa.alt.client.model.UpdateDataRequestResponse;
import io.fluentqa.alt.client.model.UpdateSubmissionDataRequestData;
import io.fluentqa.alt.client.model.UpdateTemplateData;
import io.fluentqa.alt.client.model.UpdateTemplateResponse;
import io.fluentqa.alt.client.model.UploadPresign;
import org.junit.Test;
import org.junit.Ignore;

import java.util.List;

/**
 * API tests for PdfApi
 */
@Ignore
public class PdfApiTest {

    private final PdfApi api = new PdfApi();

    
    /**
     * Add new fields to a Template
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void addFieldsToTemplateTest() throws ApiException {
        String templateId = null;
        AddFieldsData data = null;
                AddFieldsTemplateResponse response = api.addFieldsToTemplate(templateId, data);
        // TODO: test validations
    }
    
    /**
     * Generates multiple PDFs
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void batchGeneratePdfV1Test() throws ApiException {
        String templateId = null;
        List<Object> data = null;
                List<CreateSubmissionResponse> response = api.batchGeneratePdfV1(templateId, data);
        // TODO: test validations
    }
    
    /**
     * Generates multiple PDFs
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void batchGeneratePdfsTest() throws ApiException {
        SubmissionBatchData data = null;
                CreateSubmissionBatchResponse response = api.batchGeneratePdfs(data);
        // TODO: test validations
    }
    
    /**
     * Merge submission PDFs, template PDFs, or custom files
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void combinePdfsTest() throws ApiException {
        CombinePdfsData data = null;
                CreateCombinedSubmissionResponse response = api.combinePdfs(data);
        // TODO: test validations
    }
    
    /**
     * Merge generated PDFs together
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void combineSubmissionsTest() throws ApiException {
        CombinedSubmissionData data = null;
                CreateCombinedSubmissionResponse response = api.combineSubmissions(data);
        // TODO: test validations
    }
    
    /**
     * Copy a Template
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void copyTemplateTest() throws ApiException {
        String templateId = null;
        CopyTemplateData data = null;
                Template response = api.copyTemplate(templateId, data);
        // TODO: test validations
    }
    
    /**
     * Create a new custom file from a cached presign upload
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void createCustomFileFromUploadTest() throws ApiException {
        CreateCustomFileData data = null;
                CreateCustomFileResponse response = api.createCustomFileFromUpload(data);
        // TODO: test validations
    }
    
    /**
     * Creates a new data request token for form authentication
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void createDataRequestTokenTest() throws ApiException {
        String dataRequestId = null;
                CreateSubmissionDataRequestTokenResponse response = api.createDataRequestToken(dataRequestId);
        // TODO: test validations
    }
    
    /**
     * Create a folder
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void createFolderTest() throws ApiException {
        CreateFolderData data = null;
                Folder response = api.createFolder(data);
        // TODO: test validations
    }
    
    /**
     * Create a new HTML template
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void createHTMLTemplateTest() throws ApiException {
        CreateHtmlTemplateData data = null;
                PendingTemplate response = api.createHTMLTemplate(data);
        // TODO: test validations
    }
    
    /**
     * Create a new PDF template with a form POST file upload
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void createPDFTemplateTest() throws ApiException {
        File templateDocument = null;
        String templateName = null;
        String templateParentFolderId = null;
                PendingTemplate response = api.createPDFTemplate(templateDocument, templateName, templateParentFolderId);
        // TODO: test validations
    }
    
    /**
     * Create a new PDF template from a cached presign upload
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void createPDFTemplateFromUploadTest() throws ApiException {
        CreateTemplateFromUploadData data = null;
                PendingTemplate response = api.createPDFTemplateFromUpload(data);
        // TODO: test validations
    }
    
    /**
     * Delete a folder
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void deleteFolderTest() throws ApiException {
        String folderId = null;
                Folder response = api.deleteFolder(folderId);
        // TODO: test validations
    }
    
    /**
     * Expire a combined submission
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void expireCombinedSubmissionTest() throws ApiException {
        String combinedSubmissionId = null;
                CombinedSubmission response = api.expireCombinedSubmission(combinedSubmissionId);
        // TODO: test validations
    }
    
    /**
     * Expire a PDF submission
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void expireSubmissionTest() throws ApiException {
        String submissionId = null;
                Submission response = api.expireSubmission(submissionId);
        // TODO: test validations
    }
    
    /**
     * Generates a new PDF
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void generatePDFTest() throws ApiException {
        String templateId = null;
        SubmissionData data = null;
                CreateSubmissionResponse response = api.generatePDF(templateId, data);
        // TODO: test validations
    }
    
    /**
     * Check the status of a combined submission (merged PDFs)
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getCombinedSubmissionTest() throws ApiException {
        String combinedSubmissionId = null;
                CombinedSubmission response = api.getCombinedSubmission(combinedSubmissionId);
        // TODO: test validations
    }
    
    /**
     * Look up a submission data request
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getDataRequestTest() throws ApiException {
        String dataRequestId = null;
                SubmissionDataRequest response = api.getDataRequest(dataRequestId);
        // TODO: test validations
    }
    
    /**
     * Fetch the full template attributes
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getFullTemplateTest() throws ApiException {
        String templateId = null;
                Template1 response = api.getFullTemplate(templateId);
        // TODO: test validations
    }
    
    /**
     * Get a presigned URL so that you can upload a file to our AWS S3 bucket
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getPresignUrlTest() throws ApiException {
                UploadPresign response = api.getPresignUrl();
        // TODO: test validations
    }
    
    /**
     * Check the status of a PDF
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getSubmissionTest() throws ApiException {
        String submissionId = null;
        Boolean includeData = null;
                Submission response = api.getSubmission(submissionId, includeData);
        // TODO: test validations
    }
    
    /**
     * Check the status of a submission batch job
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getSubmissionBatchTest() throws ApiException {
        String submissionBatchId = null;
        Boolean includeSubmissions = null;
                SubmissionBatch response = api.getSubmissionBatch(submissionBatchId, includeSubmissions);
        // TODO: test validations
    }
    
    /**
     * Check the status of an uploaded template
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getTemplateTest() throws ApiException {
        String templateId = null;
                Template response = api.getTemplate(templateId);
        // TODO: test validations
    }
    
    /**
     * Fetch the JSON schema for a template
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getTemplateSchemaTest() throws ApiException {
        String templateId = null;
                TemplateSchema response = api.getTemplateSchema(templateId);
        // TODO: test validations
    }
    
    /**
     * Get a list of all folders
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void listFoldersTest() throws ApiException {
        String parentFolderId = null;
                List<Folder> response = api.listFolders(parentFolderId);
        // TODO: test validations
    }
    
    /**
     * List all submissions
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void listSubmissionsTest() throws ApiException {
        String cursor = null;
        BigDecimal limit = null;
        String createdAfter = null;
        String createdBefore = null;
        String type = null;
        Boolean includeData = null;
                ListSubmissionsResponse response = api.listSubmissions(cursor, limit, createdAfter, createdBefore, type, includeData);
        // TODO: test validations
    }
    
    /**
     * List all submissions for a given template
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void listSubmissionsForTemplateTest() throws ApiException {
        String templateId = null;
        String cursor = null;
        BigDecimal limit = null;
        String createdAfter = null;
        String createdBefore = null;
        String type = null;
        Boolean includeData = null;
                ListSubmissionsResponse response = api.listSubmissionsForTemplate(templateId, cursor, limit, createdAfter, createdBefore, type, includeData);
        // TODO: test validations
    }
    
    /**
     * Get a list of all templates
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void listTemplatesTest() throws ApiException {
        String query = null;
        String parentFolderId = null;
        Integer page = null;
        Integer perPage = null;
                List<Template> response = api.listTemplates(query, parentFolderId, page, perPage);
        // TODO: test validations
    }
    
    /**
     * Move a folder
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void moveFolderToFolderTest() throws ApiException {
        String folderId = null;
        MoveFolderData data = null;
                Folder response = api.moveFolderToFolder(folderId, data);
        // TODO: test validations
    }
    
    /**
     * Move Template to folder
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void moveTemplateToFolderTest() throws ApiException {
        String templateId = null;
        MoveTemplateData data = null;
                Template response = api.moveTemplateToFolder(templateId, data);
        // TODO: test validations
    }
    
    /**
     * Rename a folder
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void renameFolderTest() throws ApiException {
        String folderId = null;
        RenameFolderData data = null;
                api.renameFolder(folderId, data);
        // TODO: test validations
    }
    
    /**
     * Test Authentication
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void testAuthenticationTest() throws ApiException {
                AuthenticationSuccessResponse response = api.testAuthentication();
        // TODO: test validations
    }
    
    /**
     * Update a submission data request
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void updateDataRequestTest() throws ApiException {
        String dataRequestId = null;
        UpdateSubmissionDataRequestData data = null;
                UpdateDataRequestResponse response = api.updateDataRequest(dataRequestId, data);
        // TODO: test validations
    }
    
    /**
     * Update a Template
     *
     * 
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void updateTemplateTest() throws ApiException {
        String templateId = null;
        UpdateTemplateData data = null;
                UpdateTemplateResponse response = api.updateTemplate(templateId, data);
        // TODO: test validations
    }
    
}
package com.cividas.web.common;

public final class FieldNamesWeb {

	public static final String	SESSIONID									= "sessionid";

	public static final String	REFERENCE_LOCATOR							= "ReferenceLocator";

	public static final String	PUBLIC_SESSION_ID							= "PublicSessionID";
	public static final String	USER										= "User";
	public static final String	PASSWORD									= "Password";

	public static final String	ONTIMIZE_CONNECTION_BEAN					= "OntimizeConnectionBean";
	public static final String	ONTIMIZE_CONNECTION_PRIVATE_BEAN			= "OntimizeConnectionPrivateBean";
	
	public static final String	ONTIMIZE_USER								= "OntimizeUser";
	public static final String	ONTIMIZE_ADMIN_USER							= "OntimizeAdminUser";
	public static final String	ONTIMIZE_WEB_BEAN							= "OntimizeWebBean";

	public static final String	CONTAINER_PATH								= "ContainerPath";
	static final String			PROPERTIES_EXTENSION						= "properties";

	public static final String	M_APP										= "m_app";
	public static final String	M_ONT										= "m_ont";

	public static final String	HOSTNAME									= "Hostname";
	public static final String	PORT										= "Port";
	public static final String	REMOTE_LOCATOR_NAME							= "RemoteLocatorName";

	public static final String	TUNNELING									= "Tunneling";
	public static final String	TUNNELING_PORT								= "TunnelingPort";
	public static final String	TUNNELING_CGI								= "TunnelingCGI";
	public static final String	TUNNELING_DEBUG								= "TunnelingDebug";

	// USER ENTITY
	public static final String	USER_ENTITY									= "interestedparty.EIndividualsMasterCividas";

	public static final String	INDIVIDUAL_MASTER_ID						= "idindividualsmaster";
	public static final String	IDINDIVIDUALMASTER							= "idindividualmaster";
	public static final String	INDIVIDUAL_DATA								= "individualdata";
	public static final String	REPRESENTATION_DATA							= "representationinfo";
	public static final String	ID_COMPLETE_NAME							= "completename";
	public static final String	ID_NUMBER_ID								= "identificationnumber";
	public static final String	ID_EMAIL									= "email";
	public static final String	ID_TELEPHONE								= "telephone";
	public static final String	USERNAME									= "username";
	public static final String	USER_PASSWORD								= "password";

	// USER_ADDRESS ENTITY
	public static final String	USER_ADDRESS_ENTITY_NAME					= "interestedparty.EIndividualsMasterAddresses";
	public static final String	USER_ADDRESS_PROVINCE_CODE					= "provincecode";
	public static final String	USER_ADDRESS_PROVINCE_DESC					= "provincedesc";
	public static final String	USER_ADDRESS_TOWN_CODE						= "towncode";
	public static final String	USER_ADDRESS_TOWN_DESC						= "towndesc";
	public static final String	USER_ADDRESS_COUNTRY_DESC					= "countrydesc";
	public static final String	USER_ADDRESS_COUNTRY_ID						= "idcountry";
	public static final String	USER_ADDRESS_COMPLETE_ADDRESS				= "completeaddress";
	public static final String	USER_ADDRESS_ADDRESS						= "address";
	public static final String	USER_ADDRESS_USER_ID						= INDIVIDUAL_MASTER_ID;
	public static final String	USER_ADDRESS_PRINCIPAL_ADDRESS				= "principaladdress";
	public static final String	USER_ADDRESS_ID								= "idindividualsmasteraddress";
	public static final String	USER_ADDRESS_POSTAL_CODE					= "postalcode";
	public static final String	USER_ADDRESS_BUILDING_NUMBER				= "buildingnumber";

	// COUNTRY ENTITY
	public static final String	COUNTRY_ENTITY_NAME							= "maintenance.ECountries";
	public static final String	COUNTRY_ID									= USER_ADDRESS_COUNTRY_ID;
	public static final String	COUNTRY_DESCRIPTION							= "countrydesc";

	// PROVINCE ENTITY
	public static final String	PROVINCE_ENTITY_NAME						= "maintenance.EProvinces";
	public static final String	PROVINCE_CODE								= USER_ADDRESS_PROVINCE_CODE;
	public static final String	PROVINCE_DESCRIPTION						= "provincedesc";
	public static final String	PROVINCE_COUNTRY_ID							= USER_ADDRESS_COUNTRY_ID;

	// TOWN ENTITY
	public static final String	TOWN_ENTITY_NAME							= "maintenance.ETowns";
	public static final String	TOWN_CODE									= "towncode";
	public static final String	TOWN_DESCRIPTION							= "towndesc";
	public static final String	TOWN_PROVINCE_CODE							= USER_ADDRESS_PROVINCE_CODE;

	public static final String	DEFAULT_LANGUAGE							= "gl";														/*
																																		 * Move to BDD (Cividas server configuration
																																		 * parameter)
																																		 */
	public static final String	LOCALE_LANGUAGE_LG1							= "es";														/*
																																		 * Move to BDD (Cividas server configuration
																																		 * parameter)
																																		 */
	public static final String	LOCALE_LANGUAGE_LG2							= "gl";														/*
																																		 * Move to BDD (Cividas server configuration
																																		 * parameter)
																																		 */
	public static final String	LOCALE_LANGUAGE_LG3							= "en";														/*
																																		 * Move to BDD (Cividas server configuration
																																		 * parameter)
																																		 */
	
	public static final String RGPD_TEXTO_LG1                               = "Web.rgpd.texto_lg1";
	public static final String RGPD_TEXTO_LG2                               = "Web.rgpd.texto_lg2";
	public static final String RGPD_TEXTO_LG3                               = "Web.rgpd.texto_lg3";
	
	
	public static final String	DEFAULT_HOME_PAGE							= "index.xhtml";
	public static final String	LOGIN_PAGE									= "/public/login/login.xhtml";

	public static final String	PRIVATE_SECTION_PATTERN						= "private";

	public static final String	VALUE										= "value";

	public static final String	INPUT_DATE_TEXT_TABLE_STYLE					= "tableStyle";
	public static final String	INPUT_DATE_TEXT_BUTTON_STYLE				= "buttonStyle";
	public static final String	INPUT_DATE_TEXT_FORMAT						= "dateFormat";

	public static final String	ENTITY_KEY_NAME								= "entityKeyName";
	public static final String	ENTITY_KEY_VALUE							= "entityKeyValue";
	public static final String	ENTITY_KEYS_VALUES							= "entityKeysValues";
	public static final String	COLUMN_TO_DISPLAY_HTML						= "columnToDisplayHTML";
	public static final String	COLUMN_TO_DISPLAY							= "columnToDisplay";
	public static final String	FILE_TO_DISPLAY								= "fileToDisplay";
	public static final String	VALUE_TO_RENDER								= "valueToRender";
	public static final String	XSLSHEET_NAME								= "xslsheetName";
	public static final String	XSL_PROCESSOR								= "xslProcessor";
	public static final String	BUNDLE_BASENAME								= "bundleBasename";
	public static final String	BUNDLE_SESSION_KEY							= "bundleSessionKey";
	public static final String	COMPONENT_PREFIX							= "componentsPrefix";
	public static final String	ERROR_STYLE									= "errorStyle";
	public static final String	FORM_FIELDS_INPUT_NAME						= "formFieldsInputName";

	public static final String	ENTITY										= "entity";
	public static final String	KEY											= "key";
	public static final String	KEYS										= "keys";
	public static final String	COLS										= "cols";
	public static final String	PARENT_KEYS									= "parentKeys";
	public static final String	PARENT_KEYS_VALUES							= "parentKeysValues";
	public static final String	ROW_CLASS									= "rowClass";

	public static final String	FILE										= "file";
	public static final String	LOCALE										= "locale";

	public static final String	SEPARATOR									= ";";

	public static final String	INSCRFISICO									= "inscrfisico";
	public static final String	IDFISICO									= "idfisico";

	// download bean
	public static final String	DOWNLOAD_BEAN_ATTACHMENT_ID_PARAMETER		= "attachId";

	// cividas-core-restful
	public static final String	CIVIDAS_CORE_RESTFUL_BASE_URI				= "CividasCoreRestfulBaseUri";

	// Catalog
	public static final String	CATALOG_ATTACHMENT_ID_NAME					= "attrcividasattachidbduacdocumentattach";
	public static final String	CATALOG_HTML_FORM							= "taskformhtml";

	public static final String	IWEBCONNECTIONIMPLEMENTATION				= "IWebConnectionImplementation";
	public static final String	CIVIDASCORERESTFULBASEURI					= "CividasCoreRestfulBaseUri";

	public static final String	ROADTYPE_DESCRIPTION						= "roadtypedesc";

	public static final String	ROADTYPE_CODE								= "roadtypecode";

	public static final String	USER_ADDRESS_BUILDING_LETTER				= "letter";

	public static final String	USER_ADDRESS_BUILDING_STAIR					= "stair";

	public static final String	USER_ADDRESS_BUILDING_FLOOR					= "floor";

	public static final String	USER_ADDRESS_BUILDING_DOOR					= "door";

	public static final String	ROADTYPE_ENTITY_NAME						= "maintenance.ERoadTypes";

	public static final String	USER_ADDRESS_TOWN							= "town";

	public static final String	ADMIN_CIDR_MASKS							= "CommaSeparatedCIDRAllowedSubnets";

	public static final String	SPTURL										= "SPTUrl";

	public static final String	SPTRESPONSEVIEW								= "SPTResponseView";

	public static final String	RECEIPTJASPERSERVERURL						= "ReceiptJasperServerUrl";

	public static final String	RECEIPTJASPERSERVERURL2						= "ReceiptJasperServerUrl2";

	public static final String	RECEIPTJASPERSERVERURLPARAMNAME				= "ReceiptJasperServerUrlParamName";

	public static final String	DELAYBETWEENREFRESHESINMINUTES				= "DelayBetweenRefreshesInMinutes";

	public static final String	RANGEMILLI									= "rangeMilli";

	public static final String	MAXINSERT									= "maxInsert";

	public static final String	MAXQUERY									= "maxQuery";

	public static final String	BANTIME										= "banTime";

	public static final String	CHECKTIME									= "checkTime";

	public static final String	SPT_ISSUER_ENTITY_CODE						= "SPTIssuerEntityCode";

	public static final String	SPT_REQUEST_TYPE_CODE						= "SPTRequestTypeCode";

	public static final String	SPT_PLATFORM_CODE							= "SPTPlatformCode";

	public static final String	IDINDIVIDUALSMASTER							= INDIVIDUAL_MASTER_ID;

	public static final String	INDIVIDUALDATA								= "individualdata";

	public static final String	IDSEDE										= "idsede";

	public static final String	TAXPERCENTAGE								= "taxpercentage";

	public static final String	TAXFIXEDAMOUNT								= "taxfixedamount";

	public static final String	TAXPERCENTAGEDESC							= "taxpercentagedesc";

	public static final String	TAXTOTALAMOUNT								= "taxtotalamount";

	public static final String	TELEPHONE									= "telephone";

	public static final String	CELLPHONE									= "cellphone";

	public static final String	PASSWORDL									= "password";

	public static final String	WEBTRAMITABLE_STARTDATE						= "webtramitablestartdate";
	public static final String	WEBTRAMITABLE_ENDDATE						= "webtramitableenddate";
	public static final String	PROCEDUREWEBDESTINATION						= "procedurewebdestination";
	public static final String	PROCEDURETYPECODE							= "proceduretypecode";
	public static final String	PROCEDUREWEBRESPONSIBLE						= "procedurewebresponsible";
	public static final String	CREATEPROCEDUREFROMWEB						= "createprocedurefromweb";
	public static final String	GOOGLE_CAPTCHA_PRIVATE_KEY					= "googlecaptchaprivatekey";
	public static final String	GOOGLE_CAPTCHA_PUBLIC_KEY					= "googlecaptchapublickey";
	public static final String	JSON_ENCRYPT_AES_128_KEY					= "jsonencryptaes128key";

	public static final String	REGISTRYTYPECODE							= "registrytypecode";

	// GENERAL E-BRANCH CONFIGURATION CIVIDAS PARAMETERS
	public static final String	DEFAULT_COUNTRY								= "Third.Location.Country";
	public static final String	DEFAULT_PROVINCE							= "Third.Location.ProvinceCode";
	public static final String	DEFAULT_TOWN								= "Third.Location.TownCode";
	public static final String	MAIL_URL									= "General.Notice.Mail.Url";
	public static final String	MAIL_PORT									= "General.Notice.Mail.Port";
	public static final String	MAIL_FROM									= "General.Notice.Mail.From";
	public static final String	MAIL_STARTTLS								= "General.Notice.Mail.TLS";
	public static final String	MAIL_PASSWORD								= "General.Notice.Mail.Password";
	public static final String	MAIL_USER									= "General.Notice.Mail.User";
	public static final String	MAIL_SSL_ENABLE								= "General.Notice.Mail.SSL";
	public static final String	BASE_URL									= "Web.General.URLBase";
	public static final String	ATTACHMENT_MAX_SIZE							= "Web.Documentation.Upload.MaxFileSize";
	public static final String	ATTACHMENT_VALID_EXTENSIONS					= "Web.Documentation.Upload.AllowedFileExtensions";
	public static final String	ATTACHMENT_SIGN_ACTIVE						= "Web.Documentation.WebForms.Sign.Active";

	public static final String	CONTACT_EMAIL								= "General.Notice.Mail.ContactMail";
	public static final String	SUPPORT_EMAIL								= "General.Notice.Mail.SupportMail";

	public static final String	COMMON_LAYOUT								= "Web.Layout.CommonLayout";
	public static final String	COMMON_LAYOUT_LOGIN							= "Web.Layout.CommonLayoutLogin";

	public static final String	DEFAULT_REGISTRY_STATUS_LG1					= "Web.Registry.DefaultRegistryStatusLg1";
	public static final String	DEFAULT_REGISTRY_STATUS_LG2					= "Web.Registry.DefaultRegistryStatusLg2";
	public static final String	DEFAULT_REGISTRY_STATUS_LG3					= "Web.Registry.DefaultRegistryStatusLg3";

	// Maximum number of attachments per registry input
	public static final String	FILELIMIT									= "Web.Documentation.Upload.FileLimit";

	// New e-branch user credentials registration procedure id
	public static final String	NEW_USER_REGISTRATION_IDBDUACPROCEDURETYPE	= "Web.Registry.NewUserRegistrationIdBduacProcedureType";

	// Receipts query and payment
	public static final String	SPT_URL										= "Web.Receipts.Spt.Url";
	public static final String	SPT_RESPONSE_VIEW							= "Web.Receipts.Spt.ResponseView";
	public static final String	SPT_PLATFORM_CODE_PARAM_NAME				= "Web.Receipts.Spt.PlatformCodeParamName";
	public static final String	SPT_ISSUER_ENTITY_CODE_PARAM_NAME			= "Web.Receipts.Spt.IssuerEntityCodeParamName";
	public static final String	OVIRTUAL_RECEIPT_PAYMENT_URL				= "Web.Receipts.oVirtualReceiptPaymentUrl";
	public static final String	RECEIPT_JASPER_SERVER_URL					= "Web.Receipts.ReceiptJasperServerUrl";
	public static final String	RECEIPT_JASPER_SERVER_URL_2					= "Web.Receipts.ReceiptJasperServerUrl2";
	public static final String	RECEIPT_JASPER_SERVER_URL_PARAM_NAME		= "Web.Receipts.ReceiptJasperServerUrlParamName";
	public static final String	RECEIPT_ISSUER_ENTITY_CODE					= "Web.Receipts.ReceiptIssuerEntityCode";

	// Restful service connection
	public static final String	REST_BASE_URI								= "Web.Connection.RestBaseUri";
	public static final String	CIDR_MASKS_STRING							= "Web.Connection.CidrMasks";

	// Cache refresh
	public static final String	DELAY_BETWEEN_REFRESHES_IN_MINUTES			= "Web.Connection.DelayBetweenRefreshesInMinutes";

	// Cividas request limits (also apply to shared anonymous connection!)
	public static final String	RANGE_MILLI									= "Web.Connection.RangeMilli";
	public static final String	MAX_INSERT									= "Web.Connection.MaxInsert";
	public static final String	MAX_QUERY									= "Web.Connection.MaxQuery";
	public static final String	BAN_TIME									= "Web.Connection.BanTime";
	public static final String	CHECK_TIME									= "Web.Connection.CheckTime";

	public static final String	ANONYMOUS_USERS								= "Web.Connection.AnonymousUsers";

	public static final String	NO_SSL_LOCAL_BASE_URI						= "Web.Connection.NoSSLLocalBaseUri";

	public static final String	NO_SSL_LOCAL_BASE_URI_PROP_NAME				= "CividasCoreWebNoSSLLocalBaseUri";

	public static final String	OVIRTUAL_RECEIPT_PAYMENT_URL_PROP_NAME		= "oVirtualReceiptPaymentUrl";

	public static final String	EBRANCH_GCSE_CODE							= "Web.Connection.eBranchGCSECode";

	public static final String	EBRANCH_GCSE_CODE_PROP_NAME					= "eBranchGCSECode";

	public static final String	EBRANCH_ANALYTICS_CODE_PROP_NAME			= "eBranchAnalyticsCode";

	public static final String	EBRANCH_ANALYTICS_CODE						= "Web.Connection.eBranchAnalyticsCode";

	public static final String	EBRANCH_SSL_PORT_PROP_NAME					= "SSLPort";

	public static final String	EBRANCH_SSL_PORT							= "Web.Connection.SSLPort";

	public static final String	EBRANCH_HTTP_PORT_PROP_NAME					= "HTTPPort";

	public static final String	EBRANCH_HTTP_PORT							= "Web.Connection.HTTPPort";

	public static final String	ANONYMOUS_USERS_PARAM_NAME					= "anonymousUsers";

	public static final String	PDF_EXTENSION								= "pdf";
	public static final String	XSIG_EXTENSON								= "xsig";

	public static final String	SIGNATURE_VERIFICATION						= "signatureverification";

	// ========== CL@VE ========== //
	public static final String	CLAVE_URL									= "clave.url";
	public static final String	CLAVE_URL_LOGOUT							= "clave.url.logout";
	public static final String	PROVIDER_NAME								= "provider.name";
	public static final String	PROVIDER_SECTOR								= "provider.sector";
	public static final String	PROVIDER_APPLICATION						= "provider.application";
	public static final String	PROVIDER_COUNTRY							= "provider.country";
	public static final String	LOGIN_RESPONSE_URL							= "login.response.url";
	public static final String	LOGOUT_RESPONSE_URL							= "logout.response.url";
	public static final String	QAA_LEVEL									= "qaalevel";
	public static final String	FORCE_AUTH									= "force.auth";
	public static final String	ALLOW_LEGAL_PERSON							= "allow.legal.person";
	public static final String	EXCLUDED_IDP_LIST							= "excluded.idp.list";
	public static final String	FORCE_IDP									= "force.idp";

	public static class ClaveParameters {
		public static final String	IDENTIFICATIONNUMBER	= "eIdentifier";
		public static final String	NAME					= "givenName";
		public static final String	SURNAME					= "surname";
		public static final String	INHERITEDFAMILYNAME		= "inheritedFamilyName";
	}

	public static final String	SIA_CODE			= "siacode";

	// API REST MODEL
	// Attachments
	public static final String	ATTACH_BYTE_ARRAY	= "attachmentbytearray";
	public static final String	ATTACH_FILE_NAME	= "attachmentfilename";
	public static final String	ATTACH_DATA_DESC	= "attachmentdatadesc";
	public static final String	ID_DOC_MASTER_TYPE	= "iddocumentmastertype";
	public static final String	ATTACH_OBSERVATIONS	= "observations";
	public static final String	WEB_PUBLISHABLE		= "webpublishabledoc";
	public static final String	BLOCK_TASK			= "blocktaskending";
	public static final String	ATTACH_ORIGIN		= "origin";
	public static final String	ID_DOC_NATURE		= "iddocumentalnature";
	public static final String	ID_DOCUMENTAL_TYPE	= "iddocumentaltype";
	public static final String	DOC_DATE			= "documentationdate";
	public static final String	DOC_EXPIRY_DATE		= "expirydate";

	// REPRESENTATION INFO
	public static final String	IS_REPRESENTATION					= "isrepresentation";
	public static final String	REPRESENTED_ID						= "idrepresented";
	public static final String	REPRESENTED_NAME					= "representedname";
	public static final String	REPRESENTED_IDENTIFICATIONNUMBER	= "representedidentificationnumber";
	
	public static final String	SIACODE								= "siacode";
	
	private FieldNamesWeb() {}

	
	
	public static final String IDPROCEDURETYPE="idproceduretype";
	public static final String IDENTIFICATIONNUMBER="identificationnumber";
	public static final String NAME="name";
	public static final String SURNAME1="surname1";
	public static final String SURNAME2="surname2";
	public static final String EMAIL="email";
	public static final String IDREPRESENTED="idrepresented";
	public static final String REPRESENTEDIDENTIFICATIONNUMBER="representedidentificationnumber";
	public static final String REPRESENTEDNAME="representedname";
	public static final String LANG="lang";
	public static final String EINDIVIDUALSMASTER="interestedparty.EIndividualsMaster";
	public static final String EATTACHMENTDATADOCUMENTS="documentation.EAttachmentDataDocuments";
	public static final String ECIVIDASPARAMETERS="admin.ECividasParameters";
	public static final String FREETOWN="freetown";
	
	
	public static final String	IDATTACHMENTDATA							= "idattachmentdata";
	public static final String	ATTACHMENTDATADESC							= "attachmentdatadesc";
	public static final String	ATTACHMENTFILENAME							= "attachmentfilename";
	public static final String	CSV											= "csv";
	public static final String	ATTACHMENTFILESIZE 							= "attachmentfilesize";

	public static final String	PARAMETER							= "parameter";
	public static final String	STRINGVALUE							= "stringvalue";
	public static final String EREGISTRYINPUT="registry.ERegistryInput";
	public static final String REGISTRYCODE="registrycode";
	public static final String IDREGISTRY="idregistry";
	
	
	public static final String IDBDUACPROCEDURETYPE="idbduacproceduretype";
	public static final String EBDUACPROCEDURETYPES="bduac.procedures.EBduacProcedureTypes";
	public static final String TITLE_LG1="title_lg1";
	public static final String TITLE_LG2="title_lg2";
	public static final String TITLE_LG3="title_lg3";
	
	public static final String ID_PROCEDURE = "idprocedure";
	public static final String PROCEDURECODE = "procedurecode";
	public static final String PROCEDURETYPEFRAMEURL = "proceduretypeframeurl";
	public static final String PROCEDURETYPEFRAMEENTITY = "proceduretypeframeentity";
	public static final String PROCEDURETYPEFRAMEENTITYNAME = "proceduretypeframeentityname";
	public static final String PROCEDURETYPE_CODE = "proceduretypecode";
	public static final String CREATIONDATE = "creationdate";
	public static final String APPLICANTSUBJECT = "applicantsubject";
	public static final String SERVICEGROUPCODE = "servicegroupcode";
	public static final String PROCEDURETYPE_DESC = "proceduretypedesc";
	public static final String IDSERVICEGROUP = "idservicegroup";
	public static final String SERVICEGROUPNAME = "servicegroupname";
	public static final String STATEDESCRIPTION = "statedescription";
	public static final String IDSERVICEGROUP_RESPONSIBLE = "idservicegroupresponsible";
	public static final String PROCEDURETYPEDESCWEB_LG1 = "proceduretypedescweb_lg1";
	public static final String PROCEDURETYPEDESCWEB_LG2 = "proceduretypedescweb_lg2";
	public static final String LANGUAGE	= "language";
	public static final String DOSSIER_STATUS_DESC_WEB_ES = "dossierstatusdescweb_lg1";
	public static final String DOSSIER_STATUS_DESC_WEB_GL = "dossierstatusdescweb_lg2";
	
	public static final String EATTACHMENTDATAWEB = "web.entities.EAttachmentDataWeb";
	public static final String ETASKSWEB = "web.entities.ETasksWeb";
	public static final String IDTASK = "idtask";
	public static final String TASKTYPEDESCWEB_LG1 = "tasktypedescweb_lg1";
	public static final String TASKTYPEDESCWEB_LG2 = "tasktypedescweb_lg2";
	public static final String TASKDESCRIPTION = "taskdescription";
	public static final String TASKDATE = "taskdate";
	public static final String IDTASKTYPE = "idtasktype";
	
	//DOCUMENT VERIFICATION
	public static final String EDOCUMENTVERIFICATION = "documentation.EDocumentVerification";
	public static final String ISNOTMANDATORY = "isnotmandatory";
	public static final String SEDEREQUESTED = "sederequested";
	public static final String SEDETEXT = "sedetext";
	public static final String SEDETEXTGL = "sedetextgl";
	public static final String DOCUMENTMASTERTYPENAME = "documentmastertypename";
	public static final String IDDOCUMENTMASTERTYPE = "iddocumentmastertype";

	public static final String INTERESTEDAS_ENTITY_NAME = "interestedparty.EInterestedAs";
	public static final String IDINTERESTEDAS = "idinterestedas";
	public static final String INTERESTEDASDESC = "interestedasdesc";

	//@firma
	public static final String AFIRMA_PROPERTIES_URL				= "afirma.properties.url";
	public static final String ISTAXE = "istaxe";

	//TAX
	public static final String REPORT_TAX_PAID_SUCCESS = "tax.EReportTaxPaidSuccess";
	public static final String IDTRANSACTION = "idtransaction";
	public static final String DATE_PAYMENT ="date_payment";
	public static final String IDTTAXESPAID = "idttaxespaid";
}

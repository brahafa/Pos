package com.pos.bringit.models.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InvoiceResponse {

    @SerializedName("status")
    private boolean mStatus;
    private InvoiceBean invoice;

    /**
     * invoice : {"Errors":{},"Info":{},"OpenInfo":{},"ActualCreationDate":null,"AllowPaymentsOnDoc":false,"Amount":0,"ApiDuplicityTimeValidation":60,"ApiIdentifier":"","AssociatedEmails":null,"AttachmentFileName":null,"AttachmentOriginalFileName":null,"AutoFixMismatchItemName":null,"AutoFixPaymentsMismatchItems":null,"Balance":0,"BankAccount":null,"BaseDocId":null,"BranchID":77,"CancelDocument":null,"ClearingLogId":null,"ClientEmail":null,"ClientID":0,"ClientName":null,"CloseReceipt":false,"ConversionRate":1,"ConversionRateDecimal":"0","ConversionRateToILS":0,"ConvertToILS":false,"Credit":0,"CreditAmount":0,"Currency":"ILS","CurrencyName":null,"CurrentDate":null,"Debit":0,"DebitTransaction":0,"Deduction":0,"DepartureHour":0,"DepartureMinute":0,"Discount":{"Errors":{},"Info":{},"OpenInfo":{},"BeforeTax":false,"IsNominal":false,"Value":0,"ValueDecimal":"0"},"DocumentNumber":13180,"DocumentReffType":0,"DocumentType":1,"DocumentTypeOldSys":0,"DraftId":"00000000-0000-0000-0000-000000000000","EmailCustomComment":null,"ExternalComments":"","First":false,"GeneralClientId":0,"GeneralCustomer":{"Errors":{},"Info":{},"OpenInfo":{},"ID":0,"Identifier":"61458","Name":"ee dd"},"ID":"13034a00-88ec-4347-978d-d919dc41e01b","InternalComments":"","Invoices":null,"Is2SignDoc":false,"IsBillingDemand":false,"IsDiscountNominal":false,"IsOldSystemDocument":false,"IsPreviewDocument":false,"IsSendUserMail":null,"IsSignedBy2Sign":false,"IssueDate":"2021-05-17T00:00:00","Items":{"DocumentItem":[{"Errors":{},"Info":{},"OpenInfo":{},"Avrage":0,"Code":"1","ConversionRate":0,"Currency":null,"Description":null,"Discount":{"Errors":{},"Info":{},"OpenInfo":{},"BeforeTax":false,"IsNominal":false,"Value":0,"ValueDecimal":"0"},"ID":3562260,"IsCalcBeforeTax":false,"IsDiscountNominal":false,"LawyerIdentifier":null,"Name":"nn","Price":1.7094017094,"PriceDecimal":"0","PriceFullInput":0,"PriceFullInputDecimal":"0","PriceIncludeTax":0,"Quantity":1,"QuantityDecimal":"0","TaxPercentage":17,"TaxPercentageDecimal":null,"Total":2,"TotalDecimal":"0","TotalTax":0.290598290598,"TotalTaxDecimal":"0","TotalWithoutTax":1.7094017094,"TotalWithoutTaxDecimal":"0","UserCatalogID":0,"UserCategoryID":0},{"Errors":{},"Info":{},"OpenInfo":{},"Avrage":0,"Code":"2","ConversionRate":0,"Currency":null,"Description":null,"Discount":{"Errors":{},"Info":{},"OpenInfo":{},"BeforeTax":false,"IsNominal":false,"Value":0,"ValueDecimal":"0"},"ID":3562261,"IsCalcBeforeTax":false,"IsDiscountNominal":false,"LawyerIdentifier":null,"Name":"קולה זירו","Price":4.2735042735,"PriceDecimal":"0","PriceFullInput":0,"PriceFullInputDecimal":"0","PriceIncludeTax":0,"Quantity":1,"QuantityDecimal":"0","TaxPercentage":17,"TaxPercentageDecimal":null,"Total":5,"TotalDecimal":"0","TotalTax":0.726495726496,"TotalTaxDecimal":"0","TotalWithoutTax":4.2735042735,"TotalWithoutTaxDecimal":"0","UserCatalogID":0,"UserCategoryID":0}]},"Language":1,"LawyerDocType":null,"MailsAttached":null,"OldSystemDocGuid":null,"OldSystemId":0,"OrganizationID":50,"OrganizationUniqueID":"","Paid":0,"PaymentDueDate":null,"Payments":null,"ReceiptAmount":0,"RootOrganizationId":0,"RoundAmount":2,"RoundAmountDecimal":"0","SmsMessages":null,"Status":null,"StatusID":1,"Subject":"Order invoice","TaxIncluded":false,"TaxPercentage":17,"TaxPercentageDecimal":null,"ToRoundAmount":false,"Total":7,"TotalDecimal":"0","TotalTaxAmount":1.01709401709,"TotalTaxAmountDecimal":"0","TotalTaxExempt":null,"TotalTaxExemptDecimal":null,"TotalWithoutTax":5.98290598291,"TotalWithoutTaxDecimal":"0","UniqueID":"13034a00-88ec-4347-978d-d919dc41e01b","UseDecimalValues":null,"UserID":50}
     */


    public InvoiceBean getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceBean invoice) {
        this.invoice = invoice;
    }

    public boolean isStatus() {
        return mStatus;
    }

    public void setStatus(boolean mStatus) {
        this.mStatus = mStatus;
    }

    public class InvoiceBean {
        /**
         * Errors : {}
         * Info : {}
         * OpenInfo : {}
         * ActualCreationDate : null
         * AllowPaymentsOnDoc : false
         * Amount : 0
         * ApiDuplicityTimeValidation : 60
         * ApiIdentifier :
         * AssociatedEmails : null
         * AttachmentFileName : null
         * AttachmentOriginalFileName : null
         * AutoFixMismatchItemName : null
         * AutoFixPaymentsMismatchItems : null
         * Balance : 0
         * BankAccount : null
         * BaseDocId : null
         * BranchID : 77
         * CancelDocument : null
         * ClearingLogId : null
         * ClientEmail : null
         * ClientID : 0
         * ClientName : null
         * CloseReceipt : false
         * ConversionRate : 1
         * ConversionRateDecimal : 0
         * ConversionRateToILS : 0
         * ConvertToILS : false
         * Credit : 0
         * CreditAmount : 0
         * Currency : ILS
         * CurrencyName : null
         * CurrentDate : null
         * Debit : 0
         * DebitTransaction : 0
         * Deduction : 0
         * DepartureHour : 0
         * DepartureMinute : 0
         * Discount : {"Errors":{},"Info":{},"OpenInfo":{},"BeforeTax":false,"IsNominal":false,"Value":0,"ValueDecimal":"0"}
         * DocumentNumber : 13180
         * DocumentReffType : 0
         * DocumentType : 1
         * DocumentTypeOldSys : 0
         * DraftId : 00000000-0000-0000-0000-000000000000
         * EmailCustomComment : null
         * ExternalComments :
         * First : false
         * GeneralClientId : 0
         * GeneralCustomer : {"Errors":{},"Info":{},"OpenInfo":{},"ID":0,"Identifier":"61458","Name":"ee dd"}
         * ID : 13034a00-88ec-4347-978d-d919dc41e01b
         * InternalComments :
         * Invoices : null
         * Is2SignDoc : false
         * IsBillingDemand : false
         * IsDiscountNominal : false
         * IsOldSystemDocument : false
         * IsPreviewDocument : false
         * IsSendUserMail : null
         * IsSignedBy2Sign : false
         * IssueDate : 2021-05-17T00:00:00
         * Items : {"DocumentItem":[{"Errors":{},"Info":{},"OpenInfo":{},"Avrage":0,"Code":"1","ConversionRate":0,"Currency":null,"Description":null,"Discount":{"Errors":{},"Info":{},"OpenInfo":{},"BeforeTax":false,"IsNominal":false,"Value":0,"ValueDecimal":"0"},"ID":3562260,"IsCalcBeforeTax":false,"IsDiscountNominal":false,"LawyerIdentifier":null,"Name":"nn","Price":1.7094017094,"PriceDecimal":"0","PriceFullInput":0,"PriceFullInputDecimal":"0","PriceIncludeTax":0,"Quantity":1,"QuantityDecimal":"0","TaxPercentage":17,"TaxPercentageDecimal":null,"Total":2,"TotalDecimal":"0","TotalTax":0.290598290598,"TotalTaxDecimal":"0","TotalWithoutTax":1.7094017094,"TotalWithoutTaxDecimal":"0","UserCatalogID":0,"UserCategoryID":0},{"Errors":{},"Info":{},"OpenInfo":{},"Avrage":0,"Code":"2","ConversionRate":0,"Currency":null,"Description":null,"Discount":{"Errors":{},"Info":{},"OpenInfo":{},"BeforeTax":false,"IsNominal":false,"Value":0,"ValueDecimal":"0"},"ID":3562261,"IsCalcBeforeTax":false,"IsDiscountNominal":false,"LawyerIdentifier":null,"Name":"קולה זירו","Price":4.2735042735,"PriceDecimal":"0","PriceFullInput":0,"PriceFullInputDecimal":"0","PriceIncludeTax":0,"Quantity":1,"QuantityDecimal":"0","TaxPercentage":17,"TaxPercentageDecimal":null,"Total":5,"TotalDecimal":"0","TotalTax":0.726495726496,"TotalTaxDecimal":"0","TotalWithoutTax":4.2735042735,"TotalWithoutTaxDecimal":"0","UserCatalogID":0,"UserCategoryID":0}]}
         * Language : 1
         * LawyerDocType : null
         * MailsAttached : null
         * OldSystemDocGuid : null
         * OldSystemId : 0
         * OrganizationID : 50
         * OrganizationUniqueID :
         * Paid : 0
         * PaymentDueDate : null
         * Payments : null
         * ReceiptAmount : 0
         * RootOrganizationId : 0
         * RoundAmount : 2
         * RoundAmountDecimal : 0
         * SmsMessages : null
         * Status : null
         * StatusID : 1
         * Subject : Order invoice
         * TaxIncluded : false
         * TaxPercentage : 17
         * TaxPercentageDecimal : null
         * ToRoundAmount : false
         * Total : 7
         * TotalDecimal : 0
         * TotalTaxAmount : 1.01709401709
         * TotalTaxAmountDecimal : 0
         * TotalTaxExempt : null
         * TotalTaxExemptDecimal : null
         * TotalWithoutTax : 5.98290598291
         * TotalWithoutTaxDecimal : 0
         * UniqueID : 13034a00-88ec-4347-978d-d919dc41e01b
         * UseDecimalValues : null
         * UserID : 50
         */

        private ErrorsBean Errors;
        private InfoBean Info;
        private OpenInfoBean OpenInfo;
        private Object ActualCreationDate;
        private boolean AllowPaymentsOnDoc;
        private int Amount;
        private int ApiDuplicityTimeValidation;
        private String ApiIdentifier;
        private Object AssociatedEmails;
        private Object AttachmentFileName;
        private Object AttachmentOriginalFileName;
        private Object AutoFixMismatchItemName;
        private Object AutoFixPaymentsMismatchItems;
        private int Balance;
        private Object BankAccount;
        private Object BaseDocId;
        private int BranchID;
        private Object CancelDocument;
        private Object ClearingLogId;
        private Object ClientEmail;
        private int ClientID;
        private Object ClientName;
        private boolean CloseReceipt;
        private int ConversionRate;
        private String ConversionRateDecimal;
        private int ConversionRateToILS;
        private boolean ConvertToILS;
        private int Credit;
        private int CreditAmount;
        private String Currency;
        private Object CurrencyName;
        private Object CurrentDate;
        private int Debit;
        private int DebitTransaction;
        private int Deduction;
        private int DepartureHour;
        private int DepartureMinute;
        private DiscountBean Discount;
        private int DocumentNumber;
        private int DocumentReffType;
        private int DocumentType;
        private int DocumentTypeOldSys;
        private String DraftId;
        private Object EmailCustomComment;
        private String ExternalComments;
        private boolean First;
        private int GeneralClientId;
        private GeneralCustomerBean GeneralCustomer;
        private String ID;
        private String InternalComments;
        private Object Invoices;
        private boolean Is2SignDoc;
        private boolean IsBillingDemand;
        private boolean IsDiscountNominal;
        private boolean IsOldSystemDocument;
        private boolean IsPreviewDocument;
        private boolean IsSendUserMail;
        private boolean IsSignedBy2Sign;
        private String IssueDate;
        private ItemsBean Items;
        private int Language;
        private Object LawyerDocType;
        private Object MailsAttached;
        private Object OldSystemDocGuid;
        private int OldSystemId;
        private int OrganizationID;
        private String OrganizationUniqueID;
        private int Paid;
        private Object PaymentDueDate;
        private PaymentsBean Payments;
        private int ReceiptAmount;
        private int RootOrganizationId;
        private int RoundAmount;
        private String RoundAmountDecimal;
        private Object SmsMessages;
        private Object Status;
        private int StatusID;
        private String Subject;
        private boolean TaxIncluded;
        private int TaxPercentage;
        private Object TaxPercentageDecimal;
        private boolean ToRoundAmount;
        private int Total;
        private String TotalDecimal;
        private double TotalTaxAmount;
        private String TotalTaxAmountDecimal;
        private Object TotalTaxExempt;
        private Object TotalTaxExemptDecimal;
        private double TotalWithoutTax;
        private String TotalWithoutTaxDecimal;
        private String UniqueID;
        private Object UseDecimalValues;
        private int UserID;

        public ErrorsBean getErrors() {
            return Errors;
        }

        public void setErrors(ErrorsBean Errors) {
            this.Errors = Errors;
        }

        public InfoBean getInfo() {
            return Info;
        }

        public void setInfo(InfoBean Info) {
            this.Info = Info;
        }

        public OpenInfoBean getOpenInfo() {
            return OpenInfo;
        }

        public void setOpenInfo(OpenInfoBean OpenInfo) {
            this.OpenInfo = OpenInfo;
        }

        public Object getActualCreationDate() {
            return ActualCreationDate;
        }

        public void setActualCreationDate(Object ActualCreationDate) {
            this.ActualCreationDate = ActualCreationDate;
        }

        public boolean isAllowPaymentsOnDoc() {
            return AllowPaymentsOnDoc;
        }

        public void setAllowPaymentsOnDoc(boolean AllowPaymentsOnDoc) {
            this.AllowPaymentsOnDoc = AllowPaymentsOnDoc;
        }

        public int getAmount() {
            return Amount;
        }

        public void setAmount(int Amount) {
            this.Amount = Amount;
        }

        public int getApiDuplicityTimeValidation() {
            return ApiDuplicityTimeValidation;
        }

        public void setApiDuplicityTimeValidation(int ApiDuplicityTimeValidation) {
            this.ApiDuplicityTimeValidation = ApiDuplicityTimeValidation;
        }

        public String getApiIdentifier() {
            return ApiIdentifier;
        }

        public void setApiIdentifier(String ApiIdentifier) {
            this.ApiIdentifier = ApiIdentifier;
        }

        public Object getAssociatedEmails() {
            return AssociatedEmails;
        }

        public void setAssociatedEmails(Object AssociatedEmails) {
            this.AssociatedEmails = AssociatedEmails;
        }

        public Object getAttachmentFileName() {
            return AttachmentFileName;
        }

        public void setAttachmentFileName(Object AttachmentFileName) {
            this.AttachmentFileName = AttachmentFileName;
        }

        public Object getAttachmentOriginalFileName() {
            return AttachmentOriginalFileName;
        }

        public void setAttachmentOriginalFileName(Object AttachmentOriginalFileName) {
            this.AttachmentOriginalFileName = AttachmentOriginalFileName;
        }

        public Object getAutoFixMismatchItemName() {
            return AutoFixMismatchItemName;
        }

        public void setAutoFixMismatchItemName(Object AutoFixMismatchItemName) {
            this.AutoFixMismatchItemName = AutoFixMismatchItemName;
        }

        public Object getAutoFixPaymentsMismatchItems() {
            return AutoFixPaymentsMismatchItems;
        }

        public void setAutoFixPaymentsMismatchItems(Object AutoFixPaymentsMismatchItems) {
            this.AutoFixPaymentsMismatchItems = AutoFixPaymentsMismatchItems;
        }

        public int getBalance() {
            return Balance;
        }

        public void setBalance(int Balance) {
            this.Balance = Balance;
        }

        public Object getBankAccount() {
            return BankAccount;
        }

        public void setBankAccount(Object BankAccount) {
            this.BankAccount = BankAccount;
        }

        public Object getBaseDocId() {
            return BaseDocId;
        }

        public void setBaseDocId(Object BaseDocId) {
            this.BaseDocId = BaseDocId;
        }

        public int getBranchID() {
            return BranchID;
        }

        public void setBranchID(int BranchID) {
            this.BranchID = BranchID;
        }

        public Object getCancelDocument() {
            return CancelDocument;
        }

        public void setCancelDocument(Object CancelDocument) {
            this.CancelDocument = CancelDocument;
        }

        public Object getClearingLogId() {
            return ClearingLogId;
        }

        public void setClearingLogId(Object ClearingLogId) {
            this.ClearingLogId = ClearingLogId;
        }

        public Object getClientEmail() {
            return ClientEmail;
        }

        public void setClientEmail(Object ClientEmail) {
            this.ClientEmail = ClientEmail;
        }

        public int getClientID() {
            return ClientID;
        }

        public void setClientID(int ClientID) {
            this.ClientID = ClientID;
        }

        public Object getClientName() {
            return ClientName;
        }

        public void setClientName(Object ClientName) {
            this.ClientName = ClientName;
        }

        public boolean isCloseReceipt() {
            return CloseReceipt;
        }

        public void setCloseReceipt(boolean CloseReceipt) {
            this.CloseReceipt = CloseReceipt;
        }

        public int getConversionRate() {
            return ConversionRate;
        }

        public void setConversionRate(int ConversionRate) {
            this.ConversionRate = ConversionRate;
        }

        public String getConversionRateDecimal() {
            return ConversionRateDecimal;
        }

        public void setConversionRateDecimal(String ConversionRateDecimal) {
            this.ConversionRateDecimal = ConversionRateDecimal;
        }

        public int getConversionRateToILS() {
            return ConversionRateToILS;
        }

        public void setConversionRateToILS(int ConversionRateToILS) {
            this.ConversionRateToILS = ConversionRateToILS;
        }

        public boolean isConvertToILS() {
            return ConvertToILS;
        }

        public void setConvertToILS(boolean ConvertToILS) {
            this.ConvertToILS = ConvertToILS;
        }

        public int getCredit() {
            return Credit;
        }

        public void setCredit(int Credit) {
            this.Credit = Credit;
        }

        public int getCreditAmount() {
            return CreditAmount;
        }

        public void setCreditAmount(int CreditAmount) {
            this.CreditAmount = CreditAmount;
        }

        public String getCurrency() {
            return Currency;
        }

        public void setCurrency(String Currency) {
            this.Currency = Currency;
        }

        public Object getCurrencyName() {
            return CurrencyName;
        }

        public void setCurrencyName(Object CurrencyName) {
            this.CurrencyName = CurrencyName;
        }

        public Object getCurrentDate() {
            return CurrentDate;
        }

        public void setCurrentDate(Object CurrentDate) {
            this.CurrentDate = CurrentDate;
        }

        public int getDebit() {
            return Debit;
        }

        public void setDebit(int Debit) {
            this.Debit = Debit;
        }

        public int getDebitTransaction() {
            return DebitTransaction;
        }

        public void setDebitTransaction(int DebitTransaction) {
            this.DebitTransaction = DebitTransaction;
        }

        public int getDeduction() {
            return Deduction;
        }

        public void setDeduction(int Deduction) {
            this.Deduction = Deduction;
        }

        public int getDepartureHour() {
            return DepartureHour;
        }

        public void setDepartureHour(int DepartureHour) {
            this.DepartureHour = DepartureHour;
        }

        public int getDepartureMinute() {
            return DepartureMinute;
        }

        public void setDepartureMinute(int DepartureMinute) {
            this.DepartureMinute = DepartureMinute;
        }

        public DiscountBean getDiscount() {
            return Discount;
        }

        public void setDiscount(DiscountBean Discount) {
            this.Discount = Discount;
        }

        public int getDocumentNumber() {
            return DocumentNumber;
        }

        public void setDocumentNumber(int DocumentNumber) {
            this.DocumentNumber = DocumentNumber;
        }

        public int getDocumentReffType() {
            return DocumentReffType;
        }

        public void setDocumentReffType(int DocumentReffType) {
            this.DocumentReffType = DocumentReffType;
        }

        public int getDocumentType() {
            return DocumentType;
        }

        public void setDocumentType(int DocumentType) {
            this.DocumentType = DocumentType;
        }

        public int getDocumentTypeOldSys() {
            return DocumentTypeOldSys;
        }

        public void setDocumentTypeOldSys(int DocumentTypeOldSys) {
            this.DocumentTypeOldSys = DocumentTypeOldSys;
        }

        public String getDraftId() {
            return DraftId;
        }

        public void setDraftId(String DraftId) {
            this.DraftId = DraftId;
        }

        public Object getEmailCustomComment() {
            return EmailCustomComment;
        }

        public void setEmailCustomComment(Object EmailCustomComment) {
            this.EmailCustomComment = EmailCustomComment;
        }

        public String getExternalComments() {
            return ExternalComments;
        }

        public void setExternalComments(String ExternalComments) {
            this.ExternalComments = ExternalComments;
        }

        public boolean isFirst() {
            return First;
        }

        public void setFirst(boolean First) {
            this.First = First;
        }

        public int getGeneralClientId() {
            return GeneralClientId;
        }

        public void setGeneralClientId(int GeneralClientId) {
            this.GeneralClientId = GeneralClientId;
        }

        public GeneralCustomerBean getGeneralCustomer() {
            return GeneralCustomer;
        }

        public void setGeneralCustomer(GeneralCustomerBean GeneralCustomer) {
            this.GeneralCustomer = GeneralCustomer;
        }

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getInternalComments() {
            return InternalComments;
        }

        public void setInternalComments(String InternalComments) {
            this.InternalComments = InternalComments;
        }

        public Object getInvoices() {
            return Invoices;
        }

        public void setInvoices(Object Invoices) {
            this.Invoices = Invoices;
        }

        public boolean isIs2SignDoc() {
            return Is2SignDoc;
        }

        public void setIs2SignDoc(boolean Is2SignDoc) {
            this.Is2SignDoc = Is2SignDoc;
        }

        public boolean isIsBillingDemand() {
            return IsBillingDemand;
        }

        public void setIsBillingDemand(boolean IsBillingDemand) {
            this.IsBillingDemand = IsBillingDemand;
        }

        public boolean isIsDiscountNominal() {
            return IsDiscountNominal;
        }

        public void setIsDiscountNominal(boolean IsDiscountNominal) {
            this.IsDiscountNominal = IsDiscountNominal;
        }

        public boolean isIsOldSystemDocument() {
            return IsOldSystemDocument;
        }

        public void setIsOldSystemDocument(boolean IsOldSystemDocument) {
            this.IsOldSystemDocument = IsOldSystemDocument;
        }

        public boolean isIsPreviewDocument() {
            return IsPreviewDocument;
        }

        public void setIsPreviewDocument(boolean IsPreviewDocument) {
            this.IsPreviewDocument = IsPreviewDocument;
        }

        public boolean getIsSendUserMail() {
            return IsSendUserMail;
        }

        public void setIsSendUserMail(boolean IsSendUserMail) {
            this.IsSendUserMail = IsSendUserMail;
        }

        public boolean isIsSignedBy2Sign() {
            return IsSignedBy2Sign;
        }

        public void setIsSignedBy2Sign(boolean IsSignedBy2Sign) {
            this.IsSignedBy2Sign = IsSignedBy2Sign;
        }

        public String getIssueDate() {
            return IssueDate;
        }

        public void setIssueDate(String IssueDate) {
            this.IssueDate = IssueDate;
        }

        public ItemsBean getItems() {
            return Items;
        }

        public void setItems(ItemsBean Items) {
            this.Items = Items;
        }

        public int getLanguage() {
            return Language;
        }

        public void setLanguage(int Language) {
            this.Language = Language;
        }

        public Object getLawyerDocType() {
            return LawyerDocType;
        }

        public void setLawyerDocType(Object LawyerDocType) {
            this.LawyerDocType = LawyerDocType;
        }

        public Object getMailsAttached() {
            return MailsAttached;
        }

        public void setMailsAttached(Object MailsAttached) {
            this.MailsAttached = MailsAttached;
        }

        public Object getOldSystemDocGuid() {
            return OldSystemDocGuid;
        }

        public void setOldSystemDocGuid(Object OldSystemDocGuid) {
            this.OldSystemDocGuid = OldSystemDocGuid;
        }

        public int getOldSystemId() {
            return OldSystemId;
        }

        public void setOldSystemId(int OldSystemId) {
            this.OldSystemId = OldSystemId;
        }

        public int getOrganizationID() {
            return OrganizationID;
        }

        public void setOrganizationID(int OrganizationID) {
            this.OrganizationID = OrganizationID;
        }

        public String getOrganizationUniqueID() {
            return OrganizationUniqueID;
        }

        public void setOrganizationUniqueID(String OrganizationUniqueID) {
            this.OrganizationUniqueID = OrganizationUniqueID;
        }

        public int getPaid() {
            return Paid;
        }

        public void setPaid(int Paid) {
            this.Paid = Paid;
        }

        public Object getPaymentDueDate() {
            return PaymentDueDate;
        }

        public void setPaymentDueDate(Object PaymentDueDate) {
            this.PaymentDueDate = PaymentDueDate;
        }

        public PaymentsBean getPayments() {
            return Payments;
        }

        public void setPayments(PaymentsBean Payments) {
            this.Payments = Payments;
        }

        public int getReceiptAmount() {
            return ReceiptAmount;
        }

        public void setReceiptAmount(int ReceiptAmount) {
            this.ReceiptAmount = ReceiptAmount;
        }

        public int getRootOrganizationId() {
            return RootOrganizationId;
        }

        public void setRootOrganizationId(int RootOrganizationId) {
            this.RootOrganizationId = RootOrganizationId;
        }

        public int getRoundAmount() {
            return RoundAmount;
        }

        public void setRoundAmount(int RoundAmount) {
            this.RoundAmount = RoundAmount;
        }

        public String getRoundAmountDecimal() {
            return RoundAmountDecimal;
        }

        public void setRoundAmountDecimal(String RoundAmountDecimal) {
            this.RoundAmountDecimal = RoundAmountDecimal;
        }

        public Object getSmsMessages() {
            return SmsMessages;
        }

        public void setSmsMessages(Object SmsMessages) {
            this.SmsMessages = SmsMessages;
        }

        public Object getStatus() {
            return Status;
        }

        public void setStatus(Object Status) {
            this.Status = Status;
        }

        public int getStatusID() {
            return StatusID;
        }

        public void setStatusID(int StatusID) {
            this.StatusID = StatusID;
        }

        public String getSubject() {
            return Subject;
        }

        public void setSubject(String Subject) {
            this.Subject = Subject;
        }

        public boolean isTaxIncluded() {
            return TaxIncluded;
        }

        public void setTaxIncluded(boolean TaxIncluded) {
            this.TaxIncluded = TaxIncluded;
        }

        public int getTaxPercentage() {
            return TaxPercentage;
        }

        public void setTaxPercentage(int TaxPercentage) {
            this.TaxPercentage = TaxPercentage;
        }

        public Object getTaxPercentageDecimal() {
            return TaxPercentageDecimal;
        }

        public void setTaxPercentageDecimal(Object TaxPercentageDecimal) {
            this.TaxPercentageDecimal = TaxPercentageDecimal;
        }

        public boolean isToRoundAmount() {
            return ToRoundAmount;
        }

        public void setToRoundAmount(boolean ToRoundAmount) {
            this.ToRoundAmount = ToRoundAmount;
        }

        public int getTotal() {
            return Total;
        }

        public void setTotal(int Total) {
            this.Total = Total;
        }

        public String getTotalDecimal() {
            return TotalDecimal;
        }

        public void setTotalDecimal(String TotalDecimal) {
            this.TotalDecimal = TotalDecimal;
        }

        public double getTotalTaxAmount() {
            return TotalTaxAmount;
        }

        public void setTotalTaxAmount(double TotalTaxAmount) {
            this.TotalTaxAmount = TotalTaxAmount;
        }

        public String getTotalTaxAmountDecimal() {
            return TotalTaxAmountDecimal;
        }

        public void setTotalTaxAmountDecimal(String TotalTaxAmountDecimal) {
            this.TotalTaxAmountDecimal = TotalTaxAmountDecimal;
        }

        public Object getTotalTaxExempt() {
            return TotalTaxExempt;
        }

        public void setTotalTaxExempt(Object TotalTaxExempt) {
            this.TotalTaxExempt = TotalTaxExempt;
        }

        public Object getTotalTaxExemptDecimal() {
            return TotalTaxExemptDecimal;
        }

        public void setTotalTaxExemptDecimal(Object TotalTaxExemptDecimal) {
            this.TotalTaxExemptDecimal = TotalTaxExemptDecimal;
        }

        public double getTotalWithoutTax() {
            return TotalWithoutTax;
        }

        public void setTotalWithoutTax(double TotalWithoutTax) {
            this.TotalWithoutTax = TotalWithoutTax;
        }

        public String getTotalWithoutTaxDecimal() {
            return TotalWithoutTaxDecimal;
        }

        public void setTotalWithoutTaxDecimal(String TotalWithoutTaxDecimal) {
            this.TotalWithoutTaxDecimal = TotalWithoutTaxDecimal;
        }

        public String getUniqueID() {
            return UniqueID;
        }

        public void setUniqueID(String UniqueID) {
            this.UniqueID = UniqueID;
        }

        public Object getUseDecimalValues() {
            return UseDecimalValues;
        }

        public void setUseDecimalValues(Object UseDecimalValues) {
            this.UseDecimalValues = UseDecimalValues;
        }

        public int getUserID() {
            return UserID;
        }

        public void setUserID(int UserID) {
            this.UserID = UserID;
        }

        public class ErrorsBean {
        }

        public class InfoBean {
        }

        public class OpenInfoBean {
        }

        public class DiscountBean {
            /**
             * Errors : {}
             * Info : {}
             * OpenInfo : {}
             * BeforeTax : false
             * IsNominal : false
             * Value : 0
             * ValueDecimal : 0
             */

            private ErrorsBean Errors;
            private InfoBean Info;
            private OpenInfoBean OpenInfo;
            private boolean BeforeTax;
            private boolean IsNominal;
            private int Value;
            private String ValueDecimal;

            public ErrorsBean getErrors() {
                return Errors;
            }

            public void setErrors(ErrorsBean Errors) {
                this.Errors = Errors;
            }

            public InfoBean getInfo() {
                return Info;
            }

            public void setInfo(InfoBean Info) {
                this.Info = Info;
            }

            public OpenInfoBean getOpenInfo() {
                return OpenInfo;
            }

            public void setOpenInfo(OpenInfoBean OpenInfo) {
                this.OpenInfo = OpenInfo;
            }

            public boolean isBeforeTax() {
                return BeforeTax;
            }

            public void setBeforeTax(boolean BeforeTax) {
                this.BeforeTax = BeforeTax;
            }

            public boolean isIsNominal() {
                return IsNominal;
            }

            public void setIsNominal(boolean IsNominal) {
                this.IsNominal = IsNominal;
            }

            public int getValue() {
                return Value;
            }

            public void setValue(int Value) {
                this.Value = Value;
            }

            public String getValueDecimal() {
                return ValueDecimal;
            }

            public void setValueDecimal(String ValueDecimal) {
                this.ValueDecimal = ValueDecimal;
            }

        }

        public class GeneralCustomerBean {
            /**
             * Errors : {}
             * Info : {}
             * OpenInfo : {}
             * ID : 0
             * Identifier : 61458
             * Name : ee dd
             */

            private ErrorsBean Errors;
            private InfoBean Info;
            private OpenInfoBean OpenInfo;
            private int ID;
            private String Identifier;
            private String Name;

            public ErrorsBean getErrors() {
                return Errors;
            }

            public void setErrors(ErrorsBean Errors) {
                this.Errors = Errors;
            }

            public InfoBean getInfo() {
                return Info;
            }

            public void setInfo(InfoBean Info) {
                this.Info = Info;
            }

            public OpenInfoBean getOpenInfo() {
                return OpenInfo;
            }

            public void setOpenInfo(OpenInfoBean OpenInfo) {
                this.OpenInfo = OpenInfo;
            }

            public int getID() {
                return ID;
            }

            public void setID(int ID) {
                this.ID = ID;
            }

            public String getIdentifier() {
                return Identifier;
            }

            public void setIdentifier(String Identifier) {
                this.Identifier = Identifier;
            }

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }

        }

        public class PaymentsBean {

            /**
             * Payment : {"Errors":{},"Info":{},"OpenInfo":{},"AccountNumber":"","Amount":4,"BankName":"","BranchName":"","ConversionRate":0,"CreditCardName":"","CreditCardType":null,"Date":"2021-05-17T00:00:00","DateStr":null,"DepositID":"00000000-0000-0000-0000-000000000000","Document":null,"DocumentID":"00000000-0000-0000-0000-000000000000","ExpirationDate":"     ","ID":2204535,"IsCanceledDoc":false,"NumberOfPayments":0,"PayerID":"","PaymentNumber":"","PaymentNumberInDoc":0,"PaymentType":4,"PaymentTypeLiteral":"","PaymentTypeOtherId":0,"Ucan2CreditCompanyId":null}
             */

            private PaymentBean Payment;

            public PaymentBean getPayment() {
                return Payment;
            }

            public void setPayment(PaymentBean Payment) {
                this.Payment = Payment;
            }

            public class PaymentBean {
                /**
                 * Errors : {}
                 * Info : {}
                 * OpenInfo : {}
                 * AccountNumber :
                 * Amount : 4
                 * BankName :
                 * BranchName :
                 * ConversionRate : 0
                 * CreditCardName :
                 * CreditCardType : null
                 * Date : 2021-05-17T00:00:00
                 * DateStr : null
                 * DepositID : 00000000-0000-0000-0000-000000000000
                 * Document : null
                 * DocumentID : 00000000-0000-0000-0000-000000000000
                 * ExpirationDate :
                 * ID : 2204535
                 * IsCanceledDoc : false
                 * NumberOfPayments : 0
                 * PayerID :
                 * PaymentNumber :
                 * PaymentNumberInDoc : 0
                 * PaymentType : 4
                 * PaymentTypeLiteral :
                 * PaymentTypeOtherId : 0
                 * Ucan2CreditCompanyId : null
                 */

                private ErrorsBean Errors;
                private InfoBean Info;
                private OpenInfoBean OpenInfo;
                private String AccountNumber;
                private int Amount;
                private String BankName;
                private String BranchName;
                private int ConversionRate;
                private String CreditCardName;
                private Object CreditCardType;
                private String Date;
                private Object DateStr;
                private String DepositID;
                private Object Document;
                private String DocumentID;
                private String ExpirationDate;
                private int ID;
                private boolean IsCanceledDoc;
                private int NumberOfPayments;
                private String PayerID;
                private String PaymentNumber;
                private int PaymentNumberInDoc;
                private int PaymentType;
                private String PaymentTypeLiteral;
                private int PaymentTypeOtherId;
                private Object Ucan2CreditCompanyId;

                public ErrorsBean getErrors() {
                    return Errors;
                }

                public void setErrors(ErrorsBean Errors) {
                    this.Errors = Errors;
                }

                public InfoBean getInfo() {
                    return Info;
                }

                public void setInfo(InfoBean Info) {
                    this.Info = Info;
                }

                public OpenInfoBean getOpenInfo() {
                    return OpenInfo;
                }

                public void setOpenInfo(OpenInfoBean OpenInfo) {
                    this.OpenInfo = OpenInfo;
                }

                public String getAccountNumber() {
                    return AccountNumber;
                }

                public void setAccountNumber(String AccountNumber) {
                    this.AccountNumber = AccountNumber;
                }

                public int getAmount() {
                    return Amount;
                }

                public void setAmount(int Amount) {
                    this.Amount = Amount;
                }

                public String getBankName() {
                    return BankName;
                }

                public void setBankName(String BankName) {
                    this.BankName = BankName;
                }

                public String getBranchName() {
                    return BranchName;
                }

                public void setBranchName(String BranchName) {
                    this.BranchName = BranchName;
                }

                public int getConversionRate() {
                    return ConversionRate;
                }

                public void setConversionRate(int ConversionRate) {
                    this.ConversionRate = ConversionRate;
                }

                public String getCreditCardName() {
                    return CreditCardName;
                }

                public void setCreditCardName(String CreditCardName) {
                    this.CreditCardName = CreditCardName;
                }

                public Object getCreditCardType() {
                    return CreditCardType;
                }

                public void setCreditCardType(Object CreditCardType) {
                    this.CreditCardType = CreditCardType;
                }

                public String getDate() {
                    return Date;
                }

                public void setDate(String Date) {
                    this.Date = Date;
                }

                public Object getDateStr() {
                    return DateStr;
                }

                public void setDateStr(Object DateStr) {
                    this.DateStr = DateStr;
                }

                public String getDepositID() {
                    return DepositID;
                }

                public void setDepositID(String DepositID) {
                    this.DepositID = DepositID;
                }

                public Object getDocument() {
                    return Document;
                }

                public void setDocument(Object Document) {
                    this.Document = Document;
                }

                public String getDocumentID() {
                    return DocumentID;
                }

                public void setDocumentID(String DocumentID) {
                    this.DocumentID = DocumentID;
                }

                public String getExpirationDate() {
                    return ExpirationDate;
                }

                public void setExpirationDate(String ExpirationDate) {
                    this.ExpirationDate = ExpirationDate;
                }

                public int getID() {
                    return ID;
                }

                public void setID(int ID) {
                    this.ID = ID;
                }

                public boolean isIsCanceledDoc() {
                    return IsCanceledDoc;
                }

                public void setIsCanceledDoc(boolean IsCanceledDoc) {
                    this.IsCanceledDoc = IsCanceledDoc;
                }

                public int getNumberOfPayments() {
                    return NumberOfPayments;
                }

                public void setNumberOfPayments(int NumberOfPayments) {
                    this.NumberOfPayments = NumberOfPayments;
                }

                public String getPayerID() {
                    return PayerID;
                }

                public void setPayerID(String PayerID) {
                    this.PayerID = PayerID;
                }

                public String getPaymentNumber() {
                    return PaymentNumber;
                }

                public void setPaymentNumber(String PaymentNumber) {
                    this.PaymentNumber = PaymentNumber;
                }

                public int getPaymentNumberInDoc() {
                    return PaymentNumberInDoc;
                }

                public void setPaymentNumberInDoc(int PaymentNumberInDoc) {
                    this.PaymentNumberInDoc = PaymentNumberInDoc;
                }

                public int getPaymentType() {
                    return PaymentType;
                }

                public void setPaymentType(int PaymentType) {
                    this.PaymentType = PaymentType;
                }

                public String getPaymentTypeLiteral() {
                    return PaymentTypeLiteral;
                }

                public void setPaymentTypeLiteral(String PaymentTypeLiteral) {
                    this.PaymentTypeLiteral = PaymentTypeLiteral;
                }

                public int getPaymentTypeOtherId() {
                    return PaymentTypeOtherId;
                }

                public void setPaymentTypeOtherId(int PaymentTypeOtherId) {
                    this.PaymentTypeOtherId = PaymentTypeOtherId;
                }

                public Object getUcan2CreditCompanyId() {
                    return Ucan2CreditCompanyId;
                }

                public void setUcan2CreditCompanyId(Object Ucan2CreditCompanyId) {
                    this.Ucan2CreditCompanyId = Ucan2CreditCompanyId;
                }

            }
        }

        public class ItemsBean {
            private List<DocumentItemBean> DocumentItem;

            public List<DocumentItemBean> getDocumentItem() {
                return DocumentItem;
            }

            public void setDocumentItem(List<DocumentItemBean> DocumentItem) {
                this.DocumentItem = DocumentItem;
            }

            public class DocumentItemBean {
                /**
                 * Errors : {}
                 * Info : {}
                 * OpenInfo : {}
                 * Avrage : 0
                 * Code : 1
                 * ConversionRate : 0
                 * Currency : null
                 * Description : null
                 * Discount : {"Errors":{},"Info":{},"OpenInfo":{},"BeforeTax":false,"IsNominal":false,"Value":0,"ValueDecimal":"0"}
                 * ID : 3562260
                 * IsCalcBeforeTax : false
                 * IsDiscountNominal : false
                 * LawyerIdentifier : null
                 * Name : nn
                 * Price : 1.7094017094
                 * PriceDecimal : 0
                 * PriceFullInput : 0
                 * PriceFullInputDecimal : 0
                 * PriceIncludeTax : 0
                 * Quantity : 1
                 * QuantityDecimal : 0
                 * TaxPercentage : 17
                 * TaxPercentageDecimal : null
                 * Total : 2
                 * TotalDecimal : 0
                 * TotalTax : 0.290598290598
                 * TotalTaxDecimal : 0
                 * TotalWithoutTax : 1.7094017094
                 * TotalWithoutTaxDecimal : 0
                 * UserCatalogID : 0
                 * UserCategoryID : 0
                 */

                private ErrorsBean Errors;
                private InfoBean Info;
                private OpenInfoBean OpenInfo;
                private int Avrage;
                private String Code;
                private int ConversionRate;
                private Object Currency;
                private Object Description;
                private DiscountBeanX Discount;
                private int ID;
                private boolean IsCalcBeforeTax;
                private boolean IsDiscountNominal;
                private Object LawyerIdentifier;
                private String Name;
                private double Price;
                private String PriceDecimal;
                private int PriceFullInput;
                private String PriceFullInputDecimal;
                private int PriceIncludeTax;
                private int Quantity;
                private String QuantityDecimal;
                private int TaxPercentage;
                private Object TaxPercentageDecimal;
                private int Total;
                private String TotalDecimal;
                private double TotalTax;
                private String TotalTaxDecimal;
                private double TotalWithoutTax;
                private String TotalWithoutTaxDecimal;
                private int UserCatalogID;
                private int UserCategoryID;

                public ErrorsBean getErrors() {
                    return Errors;
                }

                public void setErrors(ErrorsBean Errors) {
                    this.Errors = Errors;
                }

                public InfoBean getInfo() {
                    return Info;
                }

                public void setInfo(InfoBean Info) {
                    this.Info = Info;
                }

                public OpenInfoBean getOpenInfo() {
                    return OpenInfo;
                }

                public void setOpenInfo(OpenInfoBean OpenInfo) {
                    this.OpenInfo = OpenInfo;
                }

                public int getAvrage() {
                    return Avrage;
                }

                public void setAvrage(int Avrage) {
                    this.Avrage = Avrage;
                }

                public String getCode() {
                    return Code;
                }

                public void setCode(String Code) {
                    this.Code = Code;
                }

                public int getConversionRate() {
                    return ConversionRate;
                }

                public void setConversionRate(int ConversionRate) {
                    this.ConversionRate = ConversionRate;
                }

                public Object getCurrency() {
                    return Currency;
                }

                public void setCurrency(Object Currency) {
                    this.Currency = Currency;
                }

                public Object getDescription() {
                    return Description;
                }

                public void setDescription(Object Description) {
                    this.Description = Description;
                }

                public DiscountBeanX getDiscount() {
                    return Discount;
                }

                public void setDiscount(DiscountBeanX Discount) {
                    this.Discount = Discount;
                }

                public int getID() {
                    return ID;
                }

                public void setID(int ID) {
                    this.ID = ID;
                }

                public boolean isIsCalcBeforeTax() {
                    return IsCalcBeforeTax;
                }

                public void setIsCalcBeforeTax(boolean IsCalcBeforeTax) {
                    this.IsCalcBeforeTax = IsCalcBeforeTax;
                }

                public boolean isIsDiscountNominal() {
                    return IsDiscountNominal;
                }

                public void setIsDiscountNominal(boolean IsDiscountNominal) {
                    this.IsDiscountNominal = IsDiscountNominal;
                }

                public Object getLawyerIdentifier() {
                    return LawyerIdentifier;
                }

                public void setLawyerIdentifier(Object LawyerIdentifier) {
                    this.LawyerIdentifier = LawyerIdentifier;
                }

                public String getName() {
                    return Name;
                }

                public void setName(String Name) {
                    this.Name = Name;
                }

                public double getPrice() {
                    return Price;
                }

                public void setPrice(double Price) {
                    this.Price = Price;
                }

                public String getPriceDecimal() {
                    return PriceDecimal;
                }

                public void setPriceDecimal(String PriceDecimal) {
                    this.PriceDecimal = PriceDecimal;
                }

                public int getPriceFullInput() {
                    return PriceFullInput;
                }

                public void setPriceFullInput(int PriceFullInput) {
                    this.PriceFullInput = PriceFullInput;
                }

                public String getPriceFullInputDecimal() {
                    return PriceFullInputDecimal;
                }

                public void setPriceFullInputDecimal(String PriceFullInputDecimal) {
                    this.PriceFullInputDecimal = PriceFullInputDecimal;
                }

                public int getPriceIncludeTax() {
                    return PriceIncludeTax;
                }

                public void setPriceIncludeTax(int PriceIncludeTax) {
                    this.PriceIncludeTax = PriceIncludeTax;
                }

                public int getQuantity() {
                    return Quantity;
                }

                public void setQuantity(int Quantity) {
                    this.Quantity = Quantity;
                }

                public String getQuantityDecimal() {
                    return QuantityDecimal;
                }

                public void setQuantityDecimal(String QuantityDecimal) {
                    this.QuantityDecimal = QuantityDecimal;
                }

                public int getTaxPercentage() {
                    return TaxPercentage;
                }

                public void setTaxPercentage(int TaxPercentage) {
                    this.TaxPercentage = TaxPercentage;
                }

                public Object getTaxPercentageDecimal() {
                    return TaxPercentageDecimal;
                }

                public void setTaxPercentageDecimal(Object TaxPercentageDecimal) {
                    this.TaxPercentageDecimal = TaxPercentageDecimal;
                }

                public int getTotal() {
                    return Total;
                }

                public void setTotal(int Total) {
                    this.Total = Total;
                }

                public String getTotalDecimal() {
                    return TotalDecimal;
                }

                public void setTotalDecimal(String TotalDecimal) {
                    this.TotalDecimal = TotalDecimal;
                }

                public double getTotalTax() {
                    return TotalTax;
                }

                public void setTotalTax(double TotalTax) {
                    this.TotalTax = TotalTax;
                }

                public String getTotalTaxDecimal() {
                    return TotalTaxDecimal;
                }

                public void setTotalTaxDecimal(String TotalTaxDecimal) {
                    this.TotalTaxDecimal = TotalTaxDecimal;
                }

                public double getTotalWithoutTax() {
                    return TotalWithoutTax;
                }

                public void setTotalWithoutTax(double TotalWithoutTax) {
                    this.TotalWithoutTax = TotalWithoutTax;
                }

                public String getTotalWithoutTaxDecimal() {
                    return TotalWithoutTaxDecimal;
                }

                public void setTotalWithoutTaxDecimal(String TotalWithoutTaxDecimal) {
                    this.TotalWithoutTaxDecimal = TotalWithoutTaxDecimal;
                }

                public int getUserCatalogID() {
                    return UserCatalogID;
                }

                public void setUserCatalogID(int UserCatalogID) {
                    this.UserCatalogID = UserCatalogID;
                }

                public int getUserCategoryID() {
                    return UserCategoryID;
                }

                public void setUserCategoryID(int UserCategoryID) {
                    this.UserCategoryID = UserCategoryID;
                }

                public class DiscountBeanX {
                    /**
                     * Errors : {}
                     * Info : {}
                     * OpenInfo : {}
                     * BeforeTax : false
                     * IsNominal : false
                     * Value : 0
                     * ValueDecimal : 0
                     */

                    private ErrorsBean Errors;
                    private InfoBean Info;
                    private OpenInfoBean OpenInfo;
                    private boolean BeforeTax;
                    private boolean IsNominal;
                    private int Value;
                    private String ValueDecimal;

                    public ErrorsBean getErrors() {
                        return Errors;
                    }

                    public void setErrors(ErrorsBean Errors) {
                        this.Errors = Errors;
                    }

                    public InfoBean getInfo() {
                        return Info;
                    }

                    public void setInfo(InfoBean Info) {
                        this.Info = Info;
                    }

                    public OpenInfoBean getOpenInfo() {
                        return OpenInfo;
                    }

                    public void setOpenInfo(OpenInfoBean OpenInfo) {
                        this.OpenInfo = OpenInfo;
                    }

                    public boolean isBeforeTax() {
                        return BeforeTax;
                    }

                    public void setBeforeTax(boolean BeforeTax) {
                        this.BeforeTax = BeforeTax;
                    }

                    public boolean isIsNominal() {
                        return IsNominal;
                    }

                    public void setIsNominal(boolean IsNominal) {
                        this.IsNominal = IsNominal;
                    }

                    public int getValue() {
                        return Value;
                    }

                    public void setValue(int Value) {
                        this.Value = Value;
                    }

                    public String getValueDecimal() {
                        return ValueDecimal;
                    }

                    public void setValueDecimal(String ValueDecimal) {
                        this.ValueDecimal = ValueDecimal;
                    }
                }
            }
        }
    }
}

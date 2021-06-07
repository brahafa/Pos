package com.pos.bringit.models.response;

import com.google.gson.annotations.SerializedName;

public class InvoiceResponse {

    @SerializedName("status")
    private boolean mStatus;
    @SerializedName("document")
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
        private Boolean AllowPaymentsOnDoc;
        private Integer Amount;
        private Integer ApiDuplicityTimeValidation;
        private String ApiIdentifier;
        private Object AssociatedEmails;
        private Object AttachmentFileName;
        private Object AttachmentOriginalFileName;
        private Object AutoFixMismatchItemName;
        private Object AutoFixPaymentsMismatchItems;
        private Integer Balance;
        private Object BankAccount;
        private Object BaseDocId;
        private Integer BranchID;
        private Object CancelDocument;
        private Object ClearingLogId;
        private Object ClientEmail;
        private Integer ClientID;
        private Object ClientName;
        private Boolean CloseReceipt;
        private Integer ConversionRate;
        private String ConversionRateDecimal;
        private Integer ConversionRateToILS;
        private Boolean ConvertToILS;
        private Integer Credit;
        private Integer CreditAmount;
        private String Currency;
        private Object CurrencyName;
        private Object CurrentDate;
        private Integer Debit;
        private Integer DebitTransaction;
        private Integer Deduction;
        private Integer DepartureHour;
        private Integer DepartureMinute;
        private DiscountBean Discount;
        private Integer DocumentNumber;
        private Integer DocumentReffType;
        private Integer DocumentType;
        private Integer DocumentTypeOldSys;
        private String DraftId;
        private Object EmailCustomComment;
        private String ExternalComments;
        private Boolean First;
        private Integer GeneralClientId;
        private GeneralCustomerBean GeneralCustomer;
        private String ID;
        private String InternalComments;
        private Object Invoices;
        private Boolean Is2SignDoc;
        private Boolean IsBillingDemand;
        private Boolean IsDiscountNominal;
        private Boolean IsOldSystemDocument;
        private Boolean IsPreviewDocument;
        private Boolean IsSendUserMail;
        private Boolean IsSignedBy2Sign;
        private String IssueDate;
        private ItemsBean Items;
        private Integer Language;
        private Object LawyerDocType;
        private Object MailsAttached;
        private Object OldSystemDocGuid;
        private Integer OldSystemId;
        private Integer OrganizationID;
        private String OrganizationUniqueID;
        private Integer Paid;
        private Object PaymentDueDate;
        private PaymentsBean Payments;
        private Integer ReceiptAmount;
        private Integer RootOrganizationId;
        private Integer RoundAmount;
        private String RoundAmountDecimal;
        private Object SmsMessages;
        private Object Status;
        private Integer StatusID;
        private String Subject;
        private Boolean TaxIncluded;
        private Integer TaxPercentage;
        private Object TaxPercentageDecimal;
        private Boolean ToRoundAmount;
        private Integer Total;
        private String TotalDecimal;
        private Double TotalTaxAmount;
        private String TotalTaxAmountDecimal;
        private Object TotalTaxExempt;
        private Object TotalTaxExemptDecimal;
        private Double TotalWithoutTax;
        private String TotalWithoutTaxDecimal;
        private String UniqueID;
        private Object UseDecimalValues;
        private Integer UserID;

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

        public Boolean isAllowPaymentsOnDoc() {
            return AllowPaymentsOnDoc;
        }

        public void setAllowPaymentsOnDoc(Boolean AllowPaymentsOnDoc) {
            this.AllowPaymentsOnDoc = AllowPaymentsOnDoc;
        }

        public Integer getAmount() {
            return Amount;
        }

        public void setAmount(Integer Amount) {
            this.Amount = Amount;
        }

        public Integer getApiDuplicityTimeValidation() {
            return ApiDuplicityTimeValidation;
        }

        public void setApiDuplicityTimeValidation(Integer ApiDuplicityTimeValidation) {
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

        public Integer getBalance() {
            return Balance;
        }

        public void setBalance(Integer Balance) {
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

        public Integer getBranchID() {
            return BranchID;
        }

        public void setBranchID(Integer BranchID) {
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

        public Integer getClientID() {
            return ClientID;
        }

        public void setClientID(Integer ClientID) {
            this.ClientID = ClientID;
        }

        public Object getClientName() {
            return ClientName;
        }

        public void setClientName(Object ClientName) {
            this.ClientName = ClientName;
        }

        public Boolean isCloseReceipt() {
            return CloseReceipt;
        }

        public void setCloseReceipt(Boolean CloseReceipt) {
            this.CloseReceipt = CloseReceipt;
        }

        public Integer getConversionRate() {
            return ConversionRate;
        }

        public void setConversionRate(Integer ConversionRate) {
            this.ConversionRate = ConversionRate;
        }

        public String getConversionRateDecimal() {
            return ConversionRateDecimal;
        }

        public void setConversionRateDecimal(String ConversionRateDecimal) {
            this.ConversionRateDecimal = ConversionRateDecimal;
        }

        public Integer getConversionRateToILS() {
            return ConversionRateToILS;
        }

        public void setConversionRateToILS(Integer ConversionRateToILS) {
            this.ConversionRateToILS = ConversionRateToILS;
        }

        public Boolean isConvertToILS() {
            return ConvertToILS;
        }

        public void setConvertToILS(Boolean ConvertToILS) {
            this.ConvertToILS = ConvertToILS;
        }

        public Integer getCredit() {
            return Credit;
        }

        public void setCredit(Integer Credit) {
            this.Credit = Credit;
        }

        public Integer getCreditAmount() {
            return CreditAmount;
        }

        public void setCreditAmount(Integer CreditAmount) {
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

        public Integer getDebit() {
            return Debit;
        }

        public void setDebit(Integer Debit) {
            this.Debit = Debit;
        }

        public Integer getDebitTransaction() {
            return DebitTransaction;
        }

        public void setDebitTransaction(Integer DebitTransaction) {
            this.DebitTransaction = DebitTransaction;
        }

        public Integer getDeduction() {
            return Deduction;
        }

        public void setDeduction(Integer Deduction) {
            this.Deduction = Deduction;
        }

        public Integer getDepartureHour() {
            return DepartureHour;
        }

        public void setDepartureHour(Integer DepartureHour) {
            this.DepartureHour = DepartureHour;
        }

        public Integer getDepartureMinute() {
            return DepartureMinute;
        }

        public void setDepartureMinute(Integer DepartureMinute) {
            this.DepartureMinute = DepartureMinute;
        }

        public DiscountBean getDiscount() {
            return Discount;
        }

        public void setDiscount(DiscountBean Discount) {
            this.Discount = Discount;
        }

        public Integer getDocumentNumber() {
            return DocumentNumber;
        }

        public void setDocumentNumber(Integer DocumentNumber) {
            this.DocumentNumber = DocumentNumber;
        }

        public Integer getDocumentReffType() {
            return DocumentReffType;
        }

        public void setDocumentReffType(Integer DocumentReffType) {
            this.DocumentReffType = DocumentReffType;
        }

        public Integer getDocumentType() {
            return DocumentType;
        }

        public void setDocumentType(Integer DocumentType) {
            this.DocumentType = DocumentType;
        }

        public Integer getDocumentTypeOldSys() {
            return DocumentTypeOldSys;
        }

        public void setDocumentTypeOldSys(Integer DocumentTypeOldSys) {
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

        public Boolean isFirst() {
            return First;
        }

        public void setFirst(Boolean First) {
            this.First = First;
        }

        public Integer getGeneralClientId() {
            return GeneralClientId;
        }

        public void setGeneralClientId(Integer GeneralClientId) {
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

        public Boolean isIs2SignDoc() {
            return Is2SignDoc;
        }

        public void setIs2SignDoc(Boolean Is2SignDoc) {
            this.Is2SignDoc = Is2SignDoc;
        }

        public Boolean isIsBillingDemand() {
            return IsBillingDemand;
        }

        public void setIsBillingDemand(Boolean IsBillingDemand) {
            this.IsBillingDemand = IsBillingDemand;
        }

        public Boolean isIsDiscountNominal() {
            return IsDiscountNominal;
        }

        public void setIsDiscountNominal(Boolean IsDiscountNominal) {
            this.IsDiscountNominal = IsDiscountNominal;
        }

        public Boolean isIsOldSystemDocument() {
            return IsOldSystemDocument;
        }

        public void setIsOldSystemDocument(Boolean IsOldSystemDocument) {
            this.IsOldSystemDocument = IsOldSystemDocument;
        }

        public Boolean isIsPreviewDocument() {
            return IsPreviewDocument;
        }

        public void setIsPreviewDocument(Boolean IsPreviewDocument) {
            this.IsPreviewDocument = IsPreviewDocument;
        }

        public Boolean getIsSendUserMail() {
            return IsSendUserMail;
        }

        public void setIsSendUserMail(Boolean IsSendUserMail) {
            this.IsSendUserMail = IsSendUserMail;
        }

        public Boolean isIsSignedBy2Sign() {
            return IsSignedBy2Sign;
        }

        public void setIsSignedBy2Sign(Boolean IsSignedBy2Sign) {
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

        public Integer getLanguage() {
            return Language;
        }

        public void setLanguage(Integer Language) {
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

        public Integer getOldSystemId() {
            return OldSystemId;
        }

        public void setOldSystemId(Integer OldSystemId) {
            this.OldSystemId = OldSystemId;
        }

        public Integer getOrganizationID() {
            return OrganizationID;
        }

        public void setOrganizationID(Integer OrganizationID) {
            this.OrganizationID = OrganizationID;
        }

        public String getOrganizationUniqueID() {
            return OrganizationUniqueID;
        }

        public void setOrganizationUniqueID(String OrganizationUniqueID) {
            this.OrganizationUniqueID = OrganizationUniqueID;
        }

        public Integer getPaid() {
            return Paid;
        }

        public void setPaid(Integer Paid) {
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

        public Integer getReceiptAmount() {
            return ReceiptAmount;
        }

        public void setReceiptAmount(Integer ReceiptAmount) {
            this.ReceiptAmount = ReceiptAmount;
        }

        public Integer getRootOrganizationId() {
            return RootOrganizationId;
        }

        public void setRootOrganizationId(Integer RootOrganizationId) {
            this.RootOrganizationId = RootOrganizationId;
        }

        public Integer getRoundAmount() {
            return RoundAmount;
        }

        public void setRoundAmount(Integer RoundAmount) {
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

        public Integer getStatusID() {
            return StatusID;
        }

        public void setStatusID(Integer StatusID) {
            this.StatusID = StatusID;
        }

        public String getSubject() {
            return Subject;
        }

        public void setSubject(String Subject) {
            this.Subject = Subject;
        }

        public Boolean isTaxIncluded() {
            return TaxIncluded;
        }

        public void setTaxIncluded(Boolean TaxIncluded) {
            this.TaxIncluded = TaxIncluded;
        }

        public Integer getTaxPercentage() {
            return TaxPercentage;
        }

        public void setTaxPercentage(Integer TaxPercentage) {
            this.TaxPercentage = TaxPercentage;
        }

        public Object getTaxPercentageDecimal() {
            return TaxPercentageDecimal;
        }

        public void setTaxPercentageDecimal(Object TaxPercentageDecimal) {
            this.TaxPercentageDecimal = TaxPercentageDecimal;
        }

        public Boolean isToRoundAmount() {
            return ToRoundAmount;
        }

        public void setToRoundAmount(Boolean ToRoundAmount) {
            this.ToRoundAmount = ToRoundAmount;
        }

        public Integer getTotal() {
            return Total;
        }

        public void setTotal(Integer Total) {
            this.Total = Total;
        }

        public String getTotalDecimal() {
            return TotalDecimal;
        }

        public void setTotalDecimal(String TotalDecimal) {
            this.TotalDecimal = TotalDecimal;
        }

        public Double getTotalTaxAmount() {
            return TotalTaxAmount;
        }

        public void setTotalTaxAmount(Double TotalTaxAmount) {
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

        public Double getTotalWithoutTax() {
            return TotalWithoutTax;
        }

        public void setTotalWithoutTax(Double TotalWithoutTax) {
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

        public Integer getUserID() {
            return UserID;
        }

        public void setUserID(Integer UserID) {
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
            private Boolean BeforeTax;
            private Boolean IsNominal;
            private Integer Value;
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

            public Boolean isBeforeTax() {
                return BeforeTax;
            }

            public void setBeforeTax(Boolean BeforeTax) {
                this.BeforeTax = BeforeTax;
            }

            public Boolean isIsNominal() {
                return IsNominal;
            }

            public void setIsNominal(Boolean IsNominal) {
                this.IsNominal = IsNominal;
            }

            public Integer getValue() {
                return Value;
            }

            public void setValue(Integer Value) {
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
            private Integer ID;
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

            public Integer getID() {
                return ID;
            }

            public void setID(Integer ID) {
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
                private Integer Amount;
                private String BankName;
                private String BranchName;
                private Integer ConversionRate;
                private String CreditCardName;
                private Object CreditCardType;
                private String Date;
                private Object DateStr;
                private String DepositID;
                private Object Document;
                private String DocumentID;
                private String ExpirationDate;
                private Integer ID;
                private Boolean IsCanceledDoc;
                private Integer NumberOfPayments;
                private String PayerID;
                private String PaymentNumber;
                private Integer PaymentNumberInDoc;
                private Integer PaymentType;
                private String PaymentTypeLiteral;
                private Integer PaymentTypeOtherId;
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

                public Integer getAmount() {
                    return Amount;
                }

                public void setAmount(Integer Amount) {
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

                public Integer getConversionRate() {
                    return ConversionRate;
                }

                public void setConversionRate(Integer ConversionRate) {
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

                public Integer getID() {
                    return ID;
                }

                public void setID(Integer ID) {
                    this.ID = ID;
                }

                public Boolean isIsCanceledDoc() {
                    return IsCanceledDoc;
                }

                public void setIsCanceledDoc(Boolean IsCanceledDoc) {
                    this.IsCanceledDoc = IsCanceledDoc;
                }

                public Integer getNumberOfPayments() {
                    return NumberOfPayments;
                }

                public void setNumberOfPayments(Integer NumberOfPayments) {
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

                public Integer getPaymentNumberInDoc() {
                    return PaymentNumberInDoc;
                }

                public void setPaymentNumberInDoc(Integer PaymentNumberInDoc) {
                    this.PaymentNumberInDoc = PaymentNumberInDoc;
                }

                public Integer getPaymentType() {
                    return PaymentType;
                }

                public void setPaymentType(Integer PaymentType) {
                    this.PaymentType = PaymentType;
                }

                public String getPaymentTypeLiteral() {
                    return PaymentTypeLiteral;
                }

                public void setPaymentTypeLiteral(String PaymentTypeLiteral) {
                    this.PaymentTypeLiteral = PaymentTypeLiteral;
                }

                public Integer getPaymentTypeOtherId() {
                    return PaymentTypeOtherId;
                }

                public void setPaymentTypeOtherId(Integer PaymentTypeOtherId) {
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
            private Object DocumentItem; // todo if this field needed parse it aarray or object after checking

            public Object getDocumentItem() {
                return DocumentItem;
            }

            public void setDocumentItem(Object documentItem) {
                DocumentItem = documentItem;
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
                private Integer Avrage;
                private String Code;
                private Integer ConversionRate;
                private Object Currency;
                private Object Description;
                private DiscountBeanX Discount;
                private Integer ID;
                private Boolean IsCalcBeforeTax;
                private Boolean IsDiscountNominal;
                private Object LawyerIdentifier;
                private String Name;
                private Double Price;
                private String PriceDecimal;
                private Integer PriceFullInput;
                private String PriceFullInputDecimal;
                private Integer PriceIncludeTax;
                private Integer Quantity;
                private String QuantityDecimal;
                private Integer TaxPercentage;
                private Object TaxPercentageDecimal;
                private Integer Total;
                private String TotalDecimal;
                private Double TotalTax;
                private String TotalTaxDecimal;
                private Double TotalWithoutTax;
                private String TotalWithoutTaxDecimal;
                private Integer UserCatalogID;
                private Integer UserCategoryID;

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

                public Integer getAvrage() {
                    return Avrage;
                }

                public void setAvrage(Integer Avrage) {
                    this.Avrage = Avrage;
                }

                public String getCode() {
                    return Code;
                }

                public void setCode(String Code) {
                    this.Code = Code;
                }

                public Integer getConversionRate() {
                    return ConversionRate;
                }

                public void setConversionRate(Integer ConversionRate) {
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

                public Integer getID() {
                    return ID;
                }

                public void setID(Integer ID) {
                    this.ID = ID;
                }

                public Boolean isIsCalcBeforeTax() {
                    return IsCalcBeforeTax;
                }

                public void setIsCalcBeforeTax(Boolean IsCalcBeforeTax) {
                    this.IsCalcBeforeTax = IsCalcBeforeTax;
                }

                public Boolean isIsDiscountNominal() {
                    return IsDiscountNominal;
                }

                public void setIsDiscountNominal(Boolean IsDiscountNominal) {
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

                public Double getPrice() {
                    return Price;
                }

                public void setPrice(Double Price) {
                    this.Price = Price;
                }

                public String getPriceDecimal() {
                    return PriceDecimal;
                }

                public void setPriceDecimal(String PriceDecimal) {
                    this.PriceDecimal = PriceDecimal;
                }

                public Integer getPriceFullInput() {
                    return PriceFullInput;
                }

                public void setPriceFullInput(Integer PriceFullInput) {
                    this.PriceFullInput = PriceFullInput;
                }

                public String getPriceFullInputDecimal() {
                    return PriceFullInputDecimal;
                }

                public void setPriceFullInputDecimal(String PriceFullInputDecimal) {
                    this.PriceFullInputDecimal = PriceFullInputDecimal;
                }

                public Integer getPriceIncludeTax() {
                    return PriceIncludeTax;
                }

                public void setPriceIncludeTax(Integer PriceIncludeTax) {
                    this.PriceIncludeTax = PriceIncludeTax;
                }

                public Integer getQuantity() {
                    return Quantity;
                }

                public void setQuantity(Integer Quantity) {
                    this.Quantity = Quantity;
                }

                public String getQuantityDecimal() {
                    return QuantityDecimal;
                }

                public void setQuantityDecimal(String QuantityDecimal) {
                    this.QuantityDecimal = QuantityDecimal;
                }

                public Integer getTaxPercentage() {
                    return TaxPercentage;
                }

                public void setTaxPercentage(Integer TaxPercentage) {
                    this.TaxPercentage = TaxPercentage;
                }

                public Object getTaxPercentageDecimal() {
                    return TaxPercentageDecimal;
                }

                public void setTaxPercentageDecimal(Object TaxPercentageDecimal) {
                    this.TaxPercentageDecimal = TaxPercentageDecimal;
                }

                public Integer getTotal() {
                    return Total;
                }

                public void setTotal(Integer Total) {
                    this.Total = Total;
                }

                public String getTotalDecimal() {
                    return TotalDecimal;
                }

                public void setTotalDecimal(String TotalDecimal) {
                    this.TotalDecimal = TotalDecimal;
                }

                public Double getTotalTax() {
                    return TotalTax;
                }

                public void setTotalTax(Double TotalTax) {
                    this.TotalTax = TotalTax;
                }

                public String getTotalTaxDecimal() {
                    return TotalTaxDecimal;
                }

                public void setTotalTaxDecimal(String TotalTaxDecimal) {
                    this.TotalTaxDecimal = TotalTaxDecimal;
                }

                public Double getTotalWithoutTax() {
                    return TotalWithoutTax;
                }

                public void setTotalWithoutTax(Double TotalWithoutTax) {
                    this.TotalWithoutTax = TotalWithoutTax;
                }

                public String getTotalWithoutTaxDecimal() {
                    return TotalWithoutTaxDecimal;
                }

                public void setTotalWithoutTaxDecimal(String TotalWithoutTaxDecimal) {
                    this.TotalWithoutTaxDecimal = TotalWithoutTaxDecimal;
                }

                public Integer getUserCatalogID() {
                    return UserCatalogID;
                }

                public void setUserCatalogID(Integer UserCatalogID) {
                    this.UserCatalogID = UserCatalogID;
                }

                public Integer getUserCategoryID() {
                    return UserCategoryID;
                }

                public void setUserCategoryID(Integer UserCategoryID) {
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
                    private Boolean BeforeTax;
                    private Boolean IsNominal;
                    private Integer Value;
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

                    public Boolean isBeforeTax() {
                        return BeforeTax;
                    }

                    public void setBeforeTax(Boolean BeforeTax) {
                        this.BeforeTax = BeforeTax;
                    }

                    public Boolean isIsNominal() {
                        return IsNominal;
                    }

                    public void setIsNominal(Boolean IsNominal) {
                        this.IsNominal = IsNominal;
                    }

                    public Integer getValue() {
                        return Value;
                    }

                    public void setValue(Integer Value) {
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

package com.alaa.alaagallo.view.my_accounts_and_sale.composable_screen.salesOptions.reports

const val REPORT_DEFAULT_AMOUNT = "112000 جنيه"

data class ReportSummaryRow(
    val label: String,
    val value: String = REPORT_DEFAULT_AMOUNT,
)

enum class ReportType(
    val id: String,
    val title: String,
    val summaryTitle: String,
    val requiresInvoiceNumber: Boolean = false,
    val summaryRows: List<ReportSummaryRow> = emptyList(),
    val amountText: String = REPORT_DEFAULT_AMOUNT,
) {
    Profit(
        id = "profit",
        title = "تقرير الارباح",
        summaryTitle = "اجمالى الارباح",
    ),
    Expenses(
        id = "expenses",
        title = "تقرير المصروفات",
        summaryTitle = "اجمالى المصروفات",
    ),
    CustomersDue(
        id = "customers_due",
        title = "تقرير بالمبالغ المتبقيه على العملاء",
        summaryTitle = "اجمالى بالمبالغ المتبقيه على العملاء",
    ),
    SuppliersDue(
        id = "suppliers_due",
        title = "تقرير بالمبالغ المتبقيه للموردين",
        summaryTitle = "اجمالى بالمبالغ المتبقيه للموردين",
    ),
    Damages(
        id = "damages",
        title = "تقرير الوالك",
        summaryTitle = "اجمالى الوالك",
    ),
    NetProfit(
        id = "net_profit",
        title = "تقرير صافى الارباح بعد المنصرف والهالك",
        summaryTitle = "تقرير صافى الارباح بعد المنصرف والهالك",
        summaryRows = listOf(
            ReportSummaryRow(label = "اجمالى الارباح"),
            ReportSummaryRow(label = "اجمالى المنصرف"),
            ReportSummaryRow(label = "اجمالى الهالك"),
            ReportSummaryRow(label = "اجمالى الصافى"),
        ),
    ),
    InvoiceProfit(
        id = "invoice_profit",
        title = "تقرير الارباح برقم الفاتورة",
        summaryTitle = "اجمالى الارباح",
        requiresInvoiceNumber = true,
    );

    companion object {
        fun fromId(id: String?): ReportType {
            return entries.firstOrNull { it.id == id } ?: Profit
        }
    }
}

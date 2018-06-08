package models;

public class Report {
    public enum Reports
    {
        REPORT_APPT_TYPES ("Monthly Appointment Types"),
        REPORT_SCHEDULE ("Schedule by Consultant"),
        REPORT_CONTACT_APPTS ("Monthly Appointments by Contact");

        private final String reportName;

        Reports (String reportName)
        {
            this.reportName = reportName;
        }

        public String getReportName()
        {
            return reportName;
        }
        @Override
        public String toString()
        {
            return getReportName();
        }
    }
}

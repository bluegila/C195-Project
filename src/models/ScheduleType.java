package models;

public class ScheduleType
{



    public enum ScheduleTypes
    {
        SYSTEM_ANALYSIS ("Performing Systems Analysis"),
        REVIEW_OF_wORK_DONE ("Review of Work Progress"),
        TESTING("Testing"),
        PHONE_CONSULT("Over the Phone Consultation"),
        STAKEHOLDERS("Meeting with Project Stakeholders"),
        WRAP_UP ("Project Wrap Up");

        private final String scheduleTypeName;

        ScheduleTypes (String scheduleTypeName)
        {
            this.scheduleTypeName = scheduleTypeName;
        }

        public String getScheduleTypeName()
        {
            return scheduleTypeName;
        }
        @Override
        public String toString()
        {
            return getScheduleTypeName();
        }
    }
}

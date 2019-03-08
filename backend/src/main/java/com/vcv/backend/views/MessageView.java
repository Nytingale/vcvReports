package com.vcv.backend.views;

import com.vcv.backend.entities.Claim;
import com.vcv.backend.entities.Job;
import com.vcv.backend.entities.Policy;
import com.vcv.backend.entities.Vehicle;

public class MessageView {
    private String message;

    public MessageView() {}
    public MessageView build(String message) {
        MessageView view = new MessageView();

        view.message = message;

        return view;
    }

    public static class WriteOff {
        private String vin;
        private Boolean writtenOff;
        private MessageView message;

        public WriteOff() {}
        public WriteOff build(Vehicle vehicle, String message) {
            WriteOff view = new WriteOff();

            view.vin = vehicle.getVin();
            view.writtenOff = vehicle.isWrittenOff();
            view.message = new MessageView().build(message);

            return view;
        }
    }
    public static class JobReport {
        private Long id;
        private MessageView message;

        public JobReport() {}
        public JobReport build(Job job, String message) {
            JobReport view = new JobReport();

            view.id = job.getJobId();
            view.message = new MessageView().build(message);

            return view;
        }
    }
    public static class StolenReport {
        private String vin;
        private Boolean stolen;
        private MessageView message;
        public StolenReport() {}

        public StolenReport build(Vehicle vehicle, String message) {
            StolenReport view = new StolenReport();

            view.vin = vehicle.getVin();
            view.stolen = vehicle.isStolen();
            view.message = new MessageView().build(message);

            return view;
        }
    }
    public static class Registration {
        private String vin;
        private MessageView message;
        public Registration() {}

        public Registration build(Vehicle vehicle, String message) {
            Registration view = new Registration();

            view.vin = vehicle.getVin();
            view.message = new MessageView().build(message);

            return view;
        }
    }
    public static class SalvageReport {
        private String vin;
        private Integer amount;
        private MessageView message;

        public SalvageReport() {}
        public SalvageReport build(Vehicle vehicle, String message) {
            SalvageReport view = new SalvageReport();

            view.vin = vehicle.getVin();
            view.amount = vehicle.getNumSalvages();
            view.message = new MessageView().build(message);

            return view;
        }

    }
    public static class AccidentReport {
        private String vin;
        private Boolean stolen;
        private MessageView message;
        public AccidentReport() {}

        public AccidentReport build(Vehicle vehicle, String message) {
            AccidentReport view = new AccidentReport();

            view.vin = vehicle.getVin();
            view.stolen = vehicle.isStolen();
            view.message = new MessageView().build(message);

            return view;
        }
    }
    public static class InsuranceReport {
        private String number;
        private String company;
        private MessageView message;

        public InsuranceReport() {}
        public InsuranceReport build(Claim claim, String message) {
            InsuranceReport view = new InsuranceReport();

            view.number = claim.getClaimNumber();
            view.company = claim.getCompanyName();
            view.message = new MessageView().build(message);

            return view;
        }
        public InsuranceReport build(Policy policy, String message) {
            InsuranceReport view = new InsuranceReport();

            view.number = policy.getPolicyNumber();
            view.company = policy.getCompanyName();
            view.message = new MessageView().build(message);

            return view;
        }
    }
}

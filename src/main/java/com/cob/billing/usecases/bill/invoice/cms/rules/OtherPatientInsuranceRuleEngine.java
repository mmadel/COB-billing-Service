package com.cob.billing.usecases.bill.invoice.cms.rules;

public class OtherPatientInsuranceRuleEngine {
    private static Integer[][] rules = new Integer[2][3];
    private static Integer action;

    static {
        rules[Submission.PRIMARY][Collection.ALL_PRIMARY] = Action.FIRST_PRIMARY;
        rules[Submission.PRIMARY][Collection.ALL_SECONDARY] = Action.FIRST_SECONDARY;
        rules[Submission.PRIMARY][Collection.PRIMARY_SECONDARY] = Action.FIRST_SECONDARY;

        rules[Submission.SECONDARY][Collection.ALL_PRIMARY] = Action.FIRST_PRIMARY;
        rules[Submission.SECONDARY][Collection.ALL_SECONDARY] = Action.FIRST_SECONDARY;
        rules[Submission.SECONDARY][Collection.PRIMARY_SECONDARY] = Action.FIRST_PRIMARY;
    }

    public static Integer fire(Integer...facts) {
        return rules[facts[0]][facts[1]];
    }
}

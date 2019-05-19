package com.emersun.rashin.utils.messages;

public enum Response {
    INTERNAL_SERVER_ERROR("error.internal.server.error"),
    SMS_VERIFICATION_FAILED("error.sms.verification.error"),
    SUCCESS("operation.successful"),
    MESSAGE_ERROR("vas.message.error"),
    MESSAGE_UNSUBSCRIBE("vas.message.unsubscribe"),
    USERNAME_NOT_FOUND("error.username.not.found"),
    ACCESS_DENIED("error.access.denied"),
    ACCOUNT_ALREADY_EXISTS("error.account.already.exists"),
    OLD_NEW_PASS_EQUALS("error.old.new.password.equal"),
    NEW_RETRY_PASS_NOTEQUAL("error.new.retry.pass.notequal"),
    OTP_NOT_VERIFIED("error.otp.is.not.verified"),
    PIN_ALREADY_EARNED("error.pin.has.already.earned"),
    YOU_ARE_ALLOWED_TO_REQUEST_AFTER_24_HOURS("error.you.are.allowed.to.request.after.24.hours"),
    YOU_ARE_ALLOWED_TO_REQUEST_AFTER_72_HOURS("error.you.are.allowed.to.request.after.72.hours"),
    SUBSCRIPTION_HAS_NOT_NOTIFIED("error.verified.subscription.has.not.notified"),
    EARNING_CHARGE_TIME_HAS_EXPIRED("error.earning.charge.time.has.expired"),
    DEVICE_ALREADY_REGISTERED("error.device.already.registered"),
    MOBILE_ALREADY_REGISTERED("error.mobile.already.registered"),
    USER_ALREADY_EARNED("error.device.mobile.already.earned.two.charges"),
    INVALID_OTP("error.invalid.otp"),
    TIME_HAS_NOT_ARRIVED("error.time.has.not.arrived"),
    EARN_SECOND_PIN_WITH_FIRST_NUMBER("error.earn.second.with.first.number"),
    EWAYS_CHARGE_ERROR("error.eways.charge"),
    IRANCELL_NOT_SUPPORTED("error.irancell.not.supported");

    Response(String message) {
        this.message = message;
    }

    private String message;

    public String getMessage() {
        return message;
    }
}

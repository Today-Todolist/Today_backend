package todolist.today.today.global.error.exception.mail;

import todolist.today.today.global.error.exception.BasicException;

import static todolist.today.today.global.error.ErrorCode.MAIL_SEND_FAILED;

public class MailSendFailedException extends BasicException {

    public MailSendFailedException() {
        super(MAIL_SEND_FAILED);
    }

}

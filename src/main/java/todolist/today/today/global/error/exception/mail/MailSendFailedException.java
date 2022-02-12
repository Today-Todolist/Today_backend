package todolist.today.today.global.error.exception.mail;

import todolist.today.today.global.error.ErrorCode;
import todolist.today.today.global.error.exception.BasicException;

public class MailSendFailedException extends BasicException {

    public MailSendFailedException() {
        super(ErrorCode.MAIL_SEND_FAILED);
    }

}

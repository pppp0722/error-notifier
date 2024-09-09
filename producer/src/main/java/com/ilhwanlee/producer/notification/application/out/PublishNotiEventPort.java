package com.ilhwanlee.producer.notification.application.out;

import com.ilhwanlee.producer.notification.domain.Severity;
import com.ilhwanlee.producer.notification.domain.User;
import java.util.List;

public interface PublishNotiEventPort {

    void publishNotiEvent(List<User> users, Severity severity, String message);
}

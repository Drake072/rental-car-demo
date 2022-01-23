package com.example.demorentalcar.intercept;

import lombok.extern.log4j.Log4j2;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import java.util.UUID;

import static com.example.demorentalcar.constant.ConstValue.REQUEST_ID;

@WebListener
@Component
@Log4j2
public class RequestIdListener implements ServletRequestListener {

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        ServletRequestListener.super.requestDestroyed(sre);
        MDC.clear();
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        ServletRequestListener.super.requestInitialized(sre);
        MDC.put(REQUEST_ID, UUID.randomUUID().toString());
    }
}

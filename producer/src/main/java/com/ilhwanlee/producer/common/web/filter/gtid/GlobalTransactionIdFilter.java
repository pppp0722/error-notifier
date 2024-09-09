package com.ilhwanlee.producer.common.web.filter.gtid;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.web.filter.OncePerRequestFilter;

public class GlobalTransactionIdFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String gtId = request.getHeader("X-Global-Transaction-Id");

        if (gtId == null || gtId.isBlank()) {
            gtId = UUID.randomUUID().toString();
        }

        ThreadContext.put("GT_ID", gtId);

        filterChain.doFilter(request, response);
    }
}

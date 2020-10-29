package com.fun.driven.development.fun.unified.payments.gateway.util;

import io.github.jhipster.config.JHipsterConstants;
import org.apache.commons.validator.routines.checkdigit.CheckDigitException;
import org.apache.commons.validator.routines.checkdigit.LuhnCheckDigit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Generates unique numeric references with the following format:
 *
 * x xxxx xxxxxxxxxx xxxx x
 * |   |     |        |   \
 * |   |     |        |    Luhn check digit
 * |   |     |        \
 * |   |     |         counter (0000-9999 sequential)
 * |   |      \
 * |   |       timestamp (seconds since 1-1-1970)
 * |    \
 * |     system id (1xx = development, 2xx = test, 3xx =  production)
 * \
 *  version of the reference
 *
 *  Spring defines the default scope as SINGLETON so as long as all clients use DI to inject this service
 *  there won't be any problems of duplicated references
 */
@Service
public class ReferenceGenerator {

    private static final Logger LOG = LoggerFactory.getLogger(ReferenceGenerator.class);
    private static final String DEFAULT_VERSION = "1";
    private static final LuhnCheckDigit luhnCheck = new LuhnCheckDigit();
    private AtomicLong second;
    private AtomicInteger counter;

    @Autowired
    private Environment env;

    public ReferenceGenerator() {
        second = new AtomicLong(Instant.now().getEpochSecond());
        counter = new AtomicInteger(-1); // Initiate to -1 so that we can use 0 as first value
    }

    public synchronized String generate() {
        increaseSequence(Instant.now().getEpochSecond());
        String baseRef = DEFAULT_VERSION +
                         fetchSystemReference() +
                         Instant.now().getEpochSecond() +
                         fetchSequence();
        String checksum = "X";
        try {
            checksum = luhnCheck.calculate(baseRef);
        } catch (CheckDigitException e) {
            LOG.error("Couldn't generate Luhn checksum for reference {}", baseRef, e);
        }
        return baseRef + checksum;
    }

    /**
     * Combines the current second with a counter to provide a sequence of integers in a particular second.
     * When called several times within the same second, atomically increments the counter.
     * The highest value within the current second is 9999.
     *
     * if second == currentSecond and current value == 10000, 0
     * if second != currentSecond, 0
     * otherwise counter++
     *
     * @param currentSecond moment in which are trying to increment the counter
     */
    private void increaseSequence(long currentSecond) {
        if (second.get() == currentSecond) {
            counter.set(counter.incrementAndGet());

            if (counter.compareAndSet(10000, 0)) {
                waitUntilNextSecond();
                second.set(Instant.now().getEpochSecond());
                counter.set(0);
            }
        } else {
            second.set(currentSecond);
            counter.set(0);
        }
    }

    private String fetchSequence() {
        return String.format ("%04d", counter.get());
    }

    private void waitUntilNextSecond() {
        int sleepCounter = 0;
        int sleepTime = 5;

        do {
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                // We can safely ignore the IE as we will just wait for a split of a second
            }
            sleepCounter++;
        } while (second.get() == Instant.now().getEpochSecond());

        LOG.warn("Reached max of 10000 increments per second and waited {} ms.", (sleepCounter * sleepTime));
    }

    private String fetchSystemReference() {
        if (env.acceptsProfiles(Profiles.of(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT))) {
            return "1000";
        }
        if (env.acceptsProfiles(Profiles.of(JHipsterConstants.SPRING_PROFILE_TEST))) {
            return "2000";
        }
        if (env.acceptsProfiles(Profiles.of(JHipsterConstants.SPRING_PROFILE_PRODUCTION))) {
            return "3000";
        }
        return "9999";
    }
}

package com.fun.driven.development.fun.unified.payments.api.config;

import io.github.jhipster.config.JHipsterProperties;
import io.github.jhipster.config.cache.PrefixedKeyGenerator;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfiguration {
    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.fun.driven.development.fun.unified.payments.api.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.fun.driven.development.fun.unified.payments.api.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.fun.driven.development.fun.unified.payments.api.domain.User.class.getName());
            createCache(cm, com.fun.driven.development.fun.unified.payments.api.domain.Authority.class.getName());
            createCache(cm, com.fun.driven.development.fun.unified.payments.api.domain.User.class.getName() + ".authorities");
            createCache(cm, com.fun.driven.development.fun.unified.payments.api.domain.Merchant.class.getName());
            createCache(cm, com.fun.driven.development.fun.unified.payments.api.domain.Merchant.class.getName() + ".users");
            createCache(cm, com.fun.driven.development.fun.unified.payments.api.domain.Merchant.class.getName() + ".paymentMethods");
            createCache(cm, com.fun.driven.development.fun.unified.payments.api.domain.Merchant.class.getName() + ".paymentMethodCredentials");
            createCache(cm, com.fun.driven.development.fun.unified.payments.api.domain.Merchant.class.getName() + ".paymentMethodTokens");
            createCache(cm, com.fun.driven.development.fun.unified.payments.api.domain.Merchant.class.getName() + ".unifiedPaymentTokens");
            createCache(cm, com.fun.driven.development.fun.unified.payments.api.domain.PaymentMethod.class.getName());
            createCache(cm, com.fun.driven.development.fun.unified.payments.api.domain.PaymentMethod.class.getName() + ".paymentMethodUnavailables");
            createCache(cm, com.fun.driven.development.fun.unified.payments.api.domain.PaymentMethod.class.getName() + ".paymentMethodCredentials");
            createCache(cm, com.fun.driven.development.fun.unified.payments.api.domain.PaymentMethodCredential.class.getName());
            createCache(cm, com.fun.driven.development.fun.unified.payments.api.domain.PaymentMethodUnavailable.class.getName());
            createCache(cm, com.fun.driven.development.fun.unified.payments.api.domain.PaymentMethodToken.class.getName());
            createCache(cm, com.fun.driven.development.fun.unified.payments.api.domain.UnifiedPaymentToken.class.getName());
            createCache(cm, com.fun.driven.development.fun.unified.payments.api.domain.Transaction.class.getName());
            createCache(cm, com.fun.driven.development.fun.unified.payments.api.domain.PaymentMethod.class.getName() + ".merchants");
            createCache(cm, com.fun.driven.development.fun.unified.payments.api.domain.Currency.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}

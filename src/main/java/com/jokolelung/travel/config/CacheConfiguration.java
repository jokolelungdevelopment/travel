package com.jokolelung.travel.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.jokolelung.travel.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.jokolelung.travel.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.jokolelung.travel.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.jokolelung.travel.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.jokolelung.travel.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.jokolelung.travel.domain.UserInfo.class.getName(), jcacheConfiguration);
            cm.createCache(com.jokolelung.travel.domain.Country.class.getName(), jcacheConfiguration);
            cm.createCache(com.jokolelung.travel.domain.Country.class.getName() + ".cities", jcacheConfiguration);
            cm.createCache(com.jokolelung.travel.domain.Country.class.getName() + ".journeys", jcacheConfiguration);
            cm.createCache(com.jokolelung.travel.domain.Country.class.getName() + ".products", jcacheConfiguration);
            cm.createCache(com.jokolelung.travel.domain.City.class.getName(), jcacheConfiguration);
            cm.createCache(com.jokolelung.travel.domain.City.class.getName() + ".journeys", jcacheConfiguration);
            cm.createCache(com.jokolelung.travel.domain.City.class.getName() + ".products", jcacheConfiguration);
            cm.createCache(com.jokolelung.travel.domain.Journey.class.getName(), jcacheConfiguration);
            cm.createCache(com.jokolelung.travel.domain.Trip.class.getName(), jcacheConfiguration);
            cm.createCache(com.jokolelung.travel.domain.Trip.class.getName() + ".journeys", jcacheConfiguration);
            cm.createCache(com.jokolelung.travel.domain.Trip.class.getName() + ".offers", jcacheConfiguration);
            cm.createCache(com.jokolelung.travel.domain.Trip.class.getName() + ".preOrders", jcacheConfiguration);
            cm.createCache(com.jokolelung.travel.domain.ProductImage.class.getName(), jcacheConfiguration);
            cm.createCache(com.jokolelung.travel.domain.Category.class.getName(), jcacheConfiguration);
            cm.createCache(com.jokolelung.travel.domain.Category.class.getName() + ".products", jcacheConfiguration);
            cm.createCache(com.jokolelung.travel.domain.Currency.class.getName(), jcacheConfiguration);
            cm.createCache(com.jokolelung.travel.domain.Currency.class.getName() + ".products", jcacheConfiguration);
            cm.createCache(com.jokolelung.travel.domain.Currency.class.getName() + ".transactions", jcacheConfiguration);
            cm.createCache(com.jokolelung.travel.domain.Product.class.getName(), jcacheConfiguration);
            cm.createCache(com.jokolelung.travel.domain.Product.class.getName() + ".productImages", jcacheConfiguration);
            cm.createCache(com.jokolelung.travel.domain.Product.class.getName() + ".requests", jcacheConfiguration);
            cm.createCache(com.jokolelung.travel.domain.Product.class.getName() + ".preOrders", jcacheConfiguration);
            cm.createCache(com.jokolelung.travel.domain.Offer.class.getName(), jcacheConfiguration);
            cm.createCache(com.jokolelung.travel.domain.Request.class.getName(), jcacheConfiguration);
            cm.createCache(com.jokolelung.travel.domain.Request.class.getName() + ".offers", jcacheConfiguration);
            cm.createCache(com.jokolelung.travel.domain.Request.class.getName() + ".notifications", jcacheConfiguration);
            cm.createCache(com.jokolelung.travel.domain.PreOrder.class.getName(), jcacheConfiguration);
            cm.createCache(com.jokolelung.travel.domain.Transaction.class.getName(), jcacheConfiguration);
            cm.createCache(com.jokolelung.travel.domain.Notification.class.getName(), jcacheConfiguration);
            cm.createCache(com.jokolelung.travel.domain.Discussion.class.getName(), jcacheConfiguration);
            cm.createCache(com.jokolelung.travel.domain.Inbox.class.getName(), jcacheConfiguration);
            cm.createCache(com.jokolelung.travel.domain.Inbox.class.getName() + ".messages", jcacheConfiguration);
            cm.createCache(com.jokolelung.travel.domain.Messages.class.getName(), jcacheConfiguration);
            cm.createCache(com.jokolelung.travel.domain.Favorite.class.getName(), jcacheConfiguration);
            cm.createCache(com.jokolelung.travel.domain.Slide.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
